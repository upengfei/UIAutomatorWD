package com.macaca.android.testing.server.controllers;

import android.app.Instrumentation;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import com.macaca.android.testing.server.models.Methods;
import com.macaca.android.testing.server.models.Response;
import com.macaca.android.testing.server.models.Status;

import org.json.JSONObject;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by xdf on 02/05/2017.
 */

public class SourceController extends RouterNanoHTTPD.DefaultHandler {

    public static SourceController source;

    static {
        source = new SourceController() {
            private static final String dumpFileName = "macaca-dump.xml";
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");
                Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
                String SDPATH = Environment.getExternalStorageDirectory() + "/";
                System.out.print("------===========");
                System.out.print(SDPATH);
                UiDevice mDevice = UiDevice.getInstance(instrumentation);
                try {
                    File file = new File(SDPATH, dumpFileName);
                    if (file.createNewFile()) {
                        mDevice.dumpWindowHierarchy(file);
                    }
                } catch (IOException e) {

                }
                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(new JSONObject(), sessionId).toString());
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