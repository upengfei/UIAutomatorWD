#!/usr/bin/env node

'use strict';

const fs = require('fs');
const path = require('path');
const spawn = require('win-spawn');
const JAVA_HOME = require('java-home');

const _ = require('../lib/helper');
