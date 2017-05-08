package com.macaca.android.testing.server.controllers;

import android.app.Instrumentation;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import com.macaca.android.testing.server.models.Methods;
import com.macaca.android.testing.server.models.Response;
import com.macaca.android.testing.server.models.Status;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.util.EncodingUtils;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;

import java.io.File;
import java.io.FileInputStream;
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
                final File dump = new File(Environment.getExternalStorageDirectory() + File.separator + dumpFileName);
                dump.getParentFile().mkdirs();
                if (dump.exists()) {
                    dump.delete();
                }
                Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
                UiDevice mDevice = UiDevice.getInstance(instrumentation);
                try {
                    mDevice.dumpWindowHierarchy(dump);
                } catch (IOException e) {
                }
                dump.setReadable(true);
                String res = "";
                try {
                    FileInputStream fin = new FileInputStream(dump.getAbsolutePath());
                    int length = fin.available();
                    byte[] buffer = new byte[length];
                    fin.read(buffer);
                    res = EncodingUtils.getString(buffer, "UTF-8");
                    fin.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(res, sessionId).toString());
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