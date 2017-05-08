'use strict';

const path = require('path');

exports.APK_BUILD_PATH = path.join(__dirname, '..', 'app', 'build', 'outputs', 'apk', 'app-debug.apk');
exports.PACKAGE_NAME = 'com.macaca.android.testing.UIAutomatorWD';
exports.TEST_PACKAGE = 'com.example.android.testing.uiautomator';
exports.RUNNER_CLASS = 'android.support.test.runner.AndroidJUnitRunner';
