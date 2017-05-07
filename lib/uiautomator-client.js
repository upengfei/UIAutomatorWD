'use strict';

const net = require('net');
const path = require('path');

const _ = require('./helper');
const logger = require('./logger');
const WDProxy = require('./proxy');

const READY_FLAG = _.uuid();
const CLASS_NAME = 'com.android.uiautomator.client.Initialize';

function UIAutomator(options) {
  this.adb = null;
  this.proxy = null;
  Object.assign(this, {
    proxyHost: '127.0.0.1',
    proxyPort: process.env.UIAUTOMATOR_PORT || 9001,
    urlBase: 'wd/hub'
  }, options || {});
}

UIAutomator.prototype.init = function *(adb) {
  this.adb = adb;
  this.initProxy();
  yield this.adb.forward(this.proxyPort, this.proxyPort);
  
  yield this.adb.push('/Users/xdf/prjs/macaca/UIAutomatorWD/app/build/outputs/apk/app-debug.apk', '/data/local/tmp/com.example.android.testing.uiautomator');
  yield this.adb.shell('pm install -r "/data/local/tmp/com.example.android.testing.uiautomator"');
  yield this.adb.shell('am force-stop com.example.android.testing.uiautomator');
  yield this.adb.shell('am force-stop com.example.android.testing.uiautomator.test');
  
  yield this.startServer();
};

UIAutomator.prototype.initProxy = function() {
  console.log({
    proxyHost: this.proxyHost,
    proxyPort: this.proxyPort,
    urlBase: this.urlBase
  })
  this.proxy = new WDProxy({
    proxyHost: this.proxyHost,
    proxyPort: this.proxyPort,
    urlBase: this.urlBase
  });
};

UIAutomator.prototype.startServer = function() {
  return new Promise(resolve => {
    const ANDROID_TMP_DIR = this.adb.getTmpDir();

    let args = 'shell am instrument -w -r -e class com.macaca.android.testing.UIAutomatorWD com.example.android.testing.uiautomator.test/android.support.test.runner.AndroidJUnitRunner'.split(' ');

    var proc = this.adb.spawn(args, {
      path: process.cwd(),
      env: process.env
    });

    proc.stderr.setEncoding('utf8');
    proc.stdout.setEncoding('utf8');
    proc.stdout.on('data', data => {
      let match = /XCTestWDSetup->(.*)<-XCTestWDSetup/.exec(data);
      if (match) {
        const url = match[1];
        if (url.startsWith('http://')) {
          logger.info('http server ready');
          resolve();
        }
      }
    });
    proc.stderr.on('data', data => {
      console.log(data);
    });
  });
};

UIAutomator.prototype.sendCommand = function(url, method, body) {
  return this.proxy.send(url, method, body);
};

module.exports = UIAutomator;
