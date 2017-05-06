package com.macaca.android.testing.server.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import com.macaca.android.testing.server.models.Response;
import com.macaca.android.testing.server.models.Status;
import com.macaca.android.testing.server.common.Element;
import com.macaca.android.testing.server.common.Elements;
import com.macaca.android.testing.server.xmlUtils.InteractionController;
import com.macaca.android.testing.server.xmlUtils.UiAutomatorBridge;

import android.graphics.Rect;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.view.KeyEvent;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xdf on 02/05/2017.
 */

public class ElementController extends RouterNanoHTTPD.DefaultHandler {

    public static ElementController click;
    public static ElementController findElement;
    public static ElementController findElements;
    public static ElementController setValue;
    public static ElementController getText;
    public static ElementController clearText;
    public static ElementController isDisplayed;
    public static ElementController getAttribute;
    public static ElementController getComputedCss;
    public static ElementController getRect;

    private static Elements elements = Elements.getGlobal();

    static {
        click = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                String elementId = urlParams.get("elementId");
                JSONObject result = null;
                try {
                    if (!elementId.isEmpty()) {
                        Element el = getElements().getElement(elementId);
                        el.click();
                    } else {
                        Element el = getElements().getElement("1");
                        el.click();
                    }
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
                } catch (final Exception e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.UnknownError, sessionId).toString());
                }

            }
        };

        findElement = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                Map<String, String> body = new HashMap<String, String>();
                JSONObject result = null;
                try {
                    session.parseBody(body);
                    String value = body.get("postData");
                    JSONObject postData = JSON.parseObject(value);
                    String strategy = (String)postData.get("strategy");
                    String text = (String) postData.get("selector");
                    strategy = strategy.trim().replace(" ", "_").toUpperCase();
                    try {
                        BySelector selector = getSelector(strategy, text);
                        result = getOneElement(selector);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.InvalidSelector, sessionId).toString());
                    }
                } catch (Exception e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.UnknownError, sessionId).toString());
                }
                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
            }
        };

        findElements = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                Map<String, String> body = new HashMap<String, String>();
                JSONArray result = null;
                try {
                    session.parseBody(body);
                    String value = body.get("postData");
                    JSONObject postData = JSON.parseObject(value);
                    String strategy = (String)postData.get("strategy");
                    String text = (String) postData.get("selector");
                    strategy = strategy.trim().replace(" ", "_").toUpperCase();
                    try {
                        BySelector selector = getSelector(strategy, text);
                        result = getMultiElements(selector);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.InvalidSelector, sessionId).toString());
                    }
                } catch (Exception e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.UnknownError, sessionId).toString());
                }
                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
            }
        };

        setValue = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                String elementId = urlParams.get("elementId");
                Map<String, String> body = new HashMap<String, String>();
                JSONObject result = null;
                try {
                    session.parseBody(body);
                    String value = body.get("postData");
                    JSONObject postData = JSON.parseObject(value);
                    String text = (String)postData.get("text");
                    Element element = getElements().getElement(elementId);
                    element.setText(text);
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
                } catch (final UiObjectNotFoundException e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
                } catch (final Exception e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.UnknownError, sessionId).toString());
                }
            }
        };

        getText = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                try {
                    String elementId = urlParams.get("elementId");
                    JSONObject result = null;
                    Element element = Elements.getGlobal().getElement(elementId);
                    result.put("value", element.getText());
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
                } catch (final Exception e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.UnknownError, sessionId).toString());
                }
            }
        };

        clearText = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                String elementId = (String) urlParams.get("elementId");
                Element el = Elements.getGlobal().getElement(elementId);
                JSONObject result = null;
                try {
                    el.clearText();
                    if (el.getText().isEmpty()) {
                        return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
                    }
                    if (hasHintText(el)) {
                        return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
                    }
                    if (sendDeleteKeys(el)) {
                        return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
                    }
                    if (!el.getText().isEmpty()) {
                        if (hasHintText(el)) {
                            System.out.println("The text should be the hint text");
                        } else if (!el.getText().isEmpty()) {
                            System.out.println("oh my god. Can't clear the Text");
                        }
                        return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
                    }
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
                } catch (final UiObjectNotFoundException e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
                } catch (final Exception e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.UnknownError, sessionId).toString());
                }
            }
        };

        isDisplayed = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                String elementId = urlParams.get("elementId");
                JSONObject result = null;
                try {
                    Element el = Elements.getGlobal().getElement(elementId);
                    boolean isDisplayed = el.isDisplayed();
                    result.put("value", isDisplayed);
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
                } catch (final UiObjectNotFoundException e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
                } catch (final Exception e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.UnknownError, sessionId).toString());
                }
            }
        };

        getAttribute = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                String elementId = urlParams.get("elementId");
                try {
                    Element el = Elements.getGlobal().getElement(elementId);
                    JSONObject text = new JSONObject();
                    text.put("text", el.element.getText());
                    JSONObject description = new JSONObject();
                    description.put("description", el.element.getContentDescription());
                    JSONObject enabled = new JSONObject();
                    enabled.put("enabled", el.element.isEnabled());
                    JSONObject checkable = new JSONObject();
                    checkable.put("checkable", el.element.isCheckable());
                    JSONObject checked = new JSONObject();
                    checked.put("checked", el.element.isChecked());
                    JSONObject clickable = new JSONObject();
                    clickable.put("clickable", el.element.isClickable());
                    JSONObject focusable = new JSONObject();
                    focusable.put("focusable", el.element.isFocusable());
                    JSONObject focused = new JSONObject();
                    focused.put("focused", el.element.isFocused());
                    JSONObject longClickable = new JSONObject();
                    longClickable.put("longClickable", el.element.isLongClickable());
                    JSONObject scrollable = new JSONObject();
                    scrollable.put("scrollable", el.element.isScrollable());
                    JSONObject selected = new JSONObject();
                    selected.put("selected", el.element.isSelected());
                    JSONObject props = new JSONObject();
                    props.put("text", text);
                    props.put("description", description);
                    props.put("enabled", enabled);
                    props.put("checkable", checkable);
                    props.put("checked", checked);
                    props.put("clickable", clickable);
                    props.put("focusable", focusable);
                    props.put("focused", focused);
                    props.put("longClickable", longClickable);
                    props.put("scrollable", scrollable);
                    props.put("selected", selected);
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(props, sessionId).toString());
                } catch (final Exception e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.UnknownError, sessionId).toString());
                }
            }
        };

        getRect = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                String elementId = urlParams.get("elementId");
                try {
                    Element el = Elements.getGlobal().getElement(elementId);
                    final Rect rect = el.element.getVisibleBounds();
                    JSONObject res = new JSONObject();
                    res.put("x", rect.left);
                    res.put("y", rect.top);
                    res.put("height", rect.height());
                    res.put("width", rect.width());
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(res, sessionId).toString());
                } catch (final Exception e) {
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.UnknownError, sessionId).toString());
                }
            }
        };
    }


    @Override
    public String getText() {
//        Response a = new Response(Status.NoSuchElement);
        return "";
    }

    @Override
    public String getMimeType() {
        return "";
    }

    @Override
    public NanoHTTPD.Response.IStatus getStatus() {
        return NanoHTTPD.Response.Status.OK;
    }

    private static  JSONObject getOneElement(final BySelector sel) throws Exception {
        final JSONObject res = new JSONObject();
        final Element element = getElements().getElement(sel);
        res.put("ELEMENT", element.getId());
        return res;
    }

    private static JSONArray getMultiElements(final BySelector sel) throws Exception {
        JSONArray res;
        List<Element> foundElements = new ArrayList<Element>();
        final List<Element> elementsFromSelector = getElements().getMultiElement(sel);
        foundElements.addAll(elementsFromSelector);
        res = elementsToJSONArray(foundElements);
        return res;
    }

    private static BySelector getSelector(String strategy, String text) throws Exception {
        BySelector selector = null;
        switch (strategy) {
            case "CLASS_NAME":
                selector = By.clazz(text);
                break;
            case "NAME":
                selector = By.text(text);
                break;
            case "ID":
                break;
            case "XPATH":
                break;
            case "LINK_TEXT":
                break;
        }
        return selector;
    }

    private static JSONArray elementsToJSONArray(final List<Element> elems)
            throws JSONException {
        JSONArray resArray = new JSONArray();
        for (Element element : elems) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ELEMENT", element.getId());
            resArray.add(jsonObject);
        }
        return resArray;
    }

    private static boolean hasHintText(Element el)
            throws UiObjectNotFoundException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        // to test if the remaining text is hint text, try sending a single delete key and testing if there is any change.
        String currText = el.getText();

        try {
            if (!el.getUiObject().isFocused()) {
                System.out.println("Could not check for hint text because the element is not focused!");
                return false;
            }
        } catch (final Exception e) {
            System.out.println("Could not check for hint text: " + e.getMessage());
            return false;
        }

        try {
            InteractionController interactionController = UiAutomatorBridge.getInstance().getInteractionController();
            interactionController.sendKey(KeyEvent.KEYCODE_DEL, 0);
            interactionController.sendKey(KeyEvent.KEYCODE_FORWARD_DEL, 0);
        } catch (Exception e) {
            System.out.println("UiAutomatorBridge.getInteractionController error happen!");
        }

        return currText.equals(el.getText());
    }

    private static boolean sendDeleteKeys(Element el)
            throws UiObjectNotFoundException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        String tempTextHolder = "";

        while (!el.getText().isEmpty() && !tempTextHolder.equalsIgnoreCase(el.getText())) {
            // focus the textbox
            el.click();

            for (int key : new int[] { KeyEvent.KEYCODE_DEL, KeyEvent.KEYCODE_FORWARD_DEL }) {
                tempTextHolder = el.getText();
                final int length = tempTextHolder.length();
                for (int count = 0; count < length; count++) {
                    try {
                        InteractionController interactionController = UiAutomatorBridge.getInstance().getInteractionController();
                        interactionController.sendKey(key, 0);
                    } catch (Exception e) {
                        System.out.println("UiAutomatorBridge.getInteractionController error happen!");
                    }
                }
            }
        }

        return el.getText().isEmpty();
    }

    public static Elements getElements() {
        return elements;
    }
}