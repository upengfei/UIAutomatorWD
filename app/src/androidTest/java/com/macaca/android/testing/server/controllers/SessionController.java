package com.macaca.android.testing.server.controllers;

import com.macaca.android.testing.server.models.Methods;
import com.macaca.android.testing.server.models.Response;
import com.macaca.android.testing.server.models.Status;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;

import java.util.Map;

/**
 * Created by xdf on 02/05/2017.
 */

public class SessionController extends RouterNanoHTTPD.DefaultHandler {

    public static SessionController sessionAvailable;
    public static SessionController createSession;
    public static SessionController getSessions;
    public static SessionController delSession;

    static {
        sessionAvailable = new SessionController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        createSession = new SessionController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        getSessions = new SessionController() {
            @Override
            public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
                String sessionId = urlParams.get("sessionId");

                return NanoHTTPD.newFixedLengthResponse(getStatus(), getMimeType(), new Response(Status.NoSuchElement, sessionId).toString());
            }
        };

        delSession = new SessionController() {
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
}