package com.macaca.android.testing;

import com.macaca.android.testing.server.controllers.*;
import com.macaca.android.testing.server.models.Methods;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;

/**
 * Created by xdf on 02/05/2017.
 */

public class UIAutomatorWDServer extends RouterNanoHTTPD {

    private static volatile UIAutomatorWDServer singleton;

    private String prefix = "/wd/hub/session/:sessionId";

    private UIAutomatorWDServer() throws IOException {
        super(8080);

        //Window Router
        addRoute(prefix + "/window_handle", Methods.GET, WindowController.getWindow);
        addRoute(prefix + "/window_handles", Methods.GET, WindowController.getWindows);
        addRoute(prefix + "/window", Methods.POST, WindowController.setWindow);
        addRoute(prefix + "/window", Methods.DELETE, WindowController.deleteWindow);
        addRoute(prefix + "/window/:windowHandle/size", Methods.GET, WindowController.getWindowSize);
        addRoute(prefix + "/window/:windowHandle/size", Methods.POST, WindowController.setWindowSize);
        addRoute(prefix + "/window/:windowHandle/maximize", Methods.POST, WindowController.maximize);
        addRoute(prefix + "/frame", Methods.POST, WindowController.setFrame);

        //SessionRouter
        addRoute("/wd/hub/session", Methods.POST, SessionController.createSession);
        addRoute("/wd/hub/sessions", Methods.GET, SessionController.getSessions);
        addRoute("/wd/hub/session/:sessionId", Methods.DELETE, SessionController.delSession);

        //ContextRouter
        addRoute(prefix + "/context", Methods.GET, ContextController.getContext);
        addRoute(prefix + "/context", Methods.POST, ContextController.setContext);
        addRoute(prefix + "/contexts", Methods.GET, ContextController.getContexts);

        //AlertRouter
        addRoute(prefix + "/accept_alert", Methods.POST, AlertController.acceptAlert);
        addRoute(prefix + "/dismiss_alert", Methods.POST, AlertController.dismissAlert);
        addRoute(prefix + "/alert_text", Methods.GET, AlertController.alertText);
        addRoute(prefix + "/alert_text", Methods.POST, AlertController.alertKeys);

        //ElementRouter
        addRoute(prefix + "/click", Methods.POST, ElementController.click);
        addRoute(prefix + "/element", Methods.POST, ElementController.findElement);
        addRoute(prefix + "/elements", Methods.POST, ElementController.findElements);
        addRoute(prefix + "/element/:elementId/element", Methods.POST, ElementController.findElement);
        addRoute(prefix + "/element/:elementId/elements", Methods.POST, ElementController.findElements);
        addRoute(prefix + "/element/:elementId/value", Methods.POST, ElementController.setValue);
        addRoute(prefix + "/element/:elementId/click", Methods.POST, ElementController.click);
        addRoute(prefix + "/element/:elementId/text", Methods.GET, ElementController.getText);
        addRoute(prefix + "/element/:elementId/clear", Methods.POST, ElementController.clearText);
        addRoute(prefix + "/element/:elementId/displayed", Methods.GET, ElementController.isDisplayed);
        addRoute(prefix + "/element/:elementId/attribute/:name", Methods.GET, ElementController.getAttribute);
        addRoute(prefix + "/element/:elementId/property/:name", Methods.GET, ElementController.getProperty);
        addRoute(prefix + "/element/:elementId/css/:propertyName", Methods.GET, ElementController.getComputedCss);
        addRoute(prefix + "/element/:elementId/rect", Methods.GET, ElementController.getRect);

        //ScreenshotRouter
        addRoute(prefix + "/screenshot", Methods.GET, ScreenshotController.getScreenshot);

        //TimeoutsRouter
        addRoute(prefix + "/timeouts/implicit_wait", Methods.POST, TimeoutsController.implicitWait);

        //UrlRouter
        addRoute(prefix + "/url", Methods.POST, UrlController.getUrl);
        addRoute(prefix + "/url", Methods.GET, UrlController.url);
        addRoute(prefix + "/forward", Methods.POST, UrlController.forward);
        addRoute(prefix + "/back", Methods.POST, UrlController.back);
        addRoute(prefix + "/refresh", Methods.POST, UrlController.refresh);

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
    }

    public static UIAutomatorWDServer getInstance() {
        if (singleton == null) {
            synchronized (UIAutomatorWDServer.class) {
                if (singleton == null) {
                    try {
                        singleton = new UIAutomatorWDServer();
                    } catch (IOException ioe) {
                        System.err.println("Couldn't start server:\n" + ioe);
                    }
                }
            }
        }
        return singleton;
    }
}
