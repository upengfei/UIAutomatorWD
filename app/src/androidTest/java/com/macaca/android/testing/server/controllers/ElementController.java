package com.macaca.android.testing.server.controllers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.macaca.android.testing.server.models.Response;
import com.macaca.android.testing.server.models.Status;
import com.macaca.android.testing.server.common.Element;
import com.macaca.android.testing.server.common.Elements;
import com.macaca.android.testing.server.xmlUtils.XmlUtils;

import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.json.JSONException;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;

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
    public static ElementController getProperty;
    public static ElementController getComputedCss;
    public static ElementController getRect;

    private static Elements elements = Elements.getGlobal();

    static {
        click = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        findElement = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                Map<String, String> body = new HashMap<String, String>();
                JSONObject result = null;
                JSONObject a = new JSONObject();
                try {
                    session.parseBody(body);
                    String value = body.get("postData");
                    JSONObject postData = JSON.parseObject(value);

                    String strategy = (String)postData.get("strategy");
                    String selector = (String) postData.get("selector");

                    a.put("strategy", postData.get("strategy"));
                    a.put("selector", postData.get("selector"));

                    strategy = strategy.trim().replace(" ", "_").toUpperCase();
                    List<UiSelector> selectors = new ArrayList<UiSelector>();
                    try {
                        selectors = getSelectors(strategy, selector, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //return failed(Status.InvalidSelector);
                    }
                    boolean found = false;

                    for (int i = 0; i < selectors.size() && !found; i++) {
                        try {
                            result = getElement(selectors.get(i));
                            found = result != null;
                        } catch (Exception ignored) {
                            //Utils.output("ignored selector");
                        }
                    }

//                    a.put("body", body);
                } catch (Exception e) {
                    //Log.i("=================");
                }
                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
            }
        };

        findElements = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        setValue = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        getText = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        clearText = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        isDisplayed = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        isDisplayed = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        getAttribute = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        getProperty = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        getComputedCss = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        getRect = new ElementController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
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

//    private static ArrayList<Element> getElements(final UiSelector sel)
//            throws UiObjectNotFoundException {
//        return elements.getElements(sel);
//    }

    private static  JSONObject getElement(final UiSelector sel) throws Exception {
        final JSONObject res = new JSONObject();
        final Element element = getElements().getElement(sel);
        return (JSONObject) res.put("ELEMENT", element.getId());
    }


    private static List<UiSelector> getSelectors(String strategy, String text, boolean multiple) throws Exception {

        final List<UiSelector> list = new ArrayList<UiSelector>();

        UiSelector selectors = new UiSelector();

        if (strategy.equals("CLASS_NAME")) {
            selectors = selectors.className(text);

            if (!multiple) {
                selectors = selectors.instance(0);
            }
            list.add(selectors);
        } else if (strategy.equals("NAME")) {
            selectors = new UiSelector().description(text);
            if (!multiple) {
                selectors = selectors.instance(0);
            }
            list.add(selectors);

            selectors = new UiSelector().text(text);
            if (!multiple) {
                selectors = selectors.instance(0);
            }
            list.add(selectors);
        } else if (strategy.equals("ID")) {
            selectors = selectors.resourceId(text);

            if (!multiple) {
                selectors = selectors.instance(0);
            }
            list.add(selectors);
        } else if (strategy.equals("XPATH")) {
            final ArrayList<UiSelector> pairs = XmlUtils.getSelectors(text);

            if (!multiple) {
                if (pairs.size() != 0) {
                    list.add(pairs.get(0));
                }
            } else {
                for (final UiSelector pair : pairs) {
                    list.add(pair);
                }
            }
        } else if (strategy.equals("LINK_TEXT")) {
            selectors = selectors.text(text);
            if (!multiple) {
                selectors = selectors.instance(0);
            }
            list.add(selectors);
        } else if (strategy.equals("PARTIAL_LINK_TEXT")) {
            selectors = selectors.textContains(text);
            if (!multiple) {
                selectors = selectors.instance(0);
            }
            list.add(selectors);
        }

        return list;
    }

    public static Elements getElements() {
        return elements;
    }
}