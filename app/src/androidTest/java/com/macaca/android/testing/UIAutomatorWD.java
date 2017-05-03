package com.macaca.android.testing;

import android.os.Environment;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(AndroidJUnit4.class)
public class UIAutomatorWD {
    @Test
    public void useAppContext() throws Exception {
        UIAutomatorWDServer testa = UIAutomatorWDServer.getInstance();;
        String SDPATH = Environment.getExternalStorageDirectory() + "/";
        System.out.print("------===========");
        System.out.print(SDPATH);
        while (true) {
            SystemClock.sleep(1000);
        }
    }
}
