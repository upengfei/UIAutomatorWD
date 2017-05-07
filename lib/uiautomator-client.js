'use strict';

const net = require('net');
const path = require('path');

const _ = require('./helper');
const logger = require('./logger');

const READY_FLAG = _.uuid();
const CLASS_NAME = 'com.android.uiautomator.client.Initialize';

function UIAutomator() {
}

UIAutomator.prototype.init = function *() {
  const ANDROID_TMP_DIR = this.adb.getTmpDir();
  yield this.adb.push(binPath, ANDROID_TMP_DIR);
  yield this.adb.forward(this.socketPort, this.socketPort);
  yield this.startServer();
};

UIAutomator.prototype.startServer = function() {
  return new Promise(resolve => {
    const ANDROID_TMP_DIR = this.adb.getTmpDir();
    let args = `shell uiautomator runtest ${ANDROID_TMP_DIR}/${FILE_NAME}.jar -c ${CLASS_NAME} -e port ${this.socketPort} -e flag ${READY_FLAG}`.split(' ');

    var proc = this.adb.spawn(args, {
      path: process.cwd(),
      env: process.env
    });

    proc.stderr.setEncoding('utf8');
    proc.stdout.setEncoding('utf8');
    proc.stdout.on('data', data => {
      if (!!~data.indexOf(READY_FLAG)) {
        logger.info('socket server ready');
        resolve();
      } else {
        console.log(data);
      }
    });
    proc.stderr.on('data', data => {
      console.log(data);
    });
  });
};

module.exports = UIAutomator;
