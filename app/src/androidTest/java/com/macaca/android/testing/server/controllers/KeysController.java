package com.macaca.android.testing.server.controllers;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.macaca.android.testing.server.models.Response;
import com.macaca.android.testing.server.models.Status;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by xdf on 02/05/2017.
 */

public class KeysController extends RouterNanoHTTPD.DefaultHandler {

    public static KeysController keys;

    static {
        keys = new KeysController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                Map<String, String> body = new HashMap<String, String>();
                Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
                UiDevice mDevice = UiDevice.getInstance(instrumentation);
                JSONObject result = null;
                try {
                    session.parseBody(body);
                    String postData = body.get("postData");
                    JSONObject jsonObj = JSON.parseObject(postData);
                    JSONArray keycodes = (JSONArray)jsonObj.get("value");
                    for (Iterator iterator = keycodes.iterator(); iterator.hasNext();) {
                        String keycode = (String) iterator.next();
                        mDevice.pressKeyCode(Integer.parseInt(keycode));
                    }
                    return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(result, sessionId).toString());
                } catch (Exception e) {
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
}