/**
 * @license
 * Visual Blocks Editor
 *
 * Copyright 2012 Google Inc.
 * https://blockly.googlecode.com/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @fileoverview Text input field that uses an external editor.
 * @author edinardo.potrich@imail.org (Edinardo Potrich)
 */
'use strict';

goog.provide('Blockly.FieldExternalInput');

goog.require('Blockly.Field');

/**
 * Class for a text field using an external editor.
 *
 * @param {string} text The initial content of the field.
 * @param {Function} externalInputHandler a mandatory function which
 *   creates the external editor. The function receives two arguments:
 *   the current text and a callback function which must be called
 *   by the external editor to provide the new value.
 * @extends {Blockly.Field}
 * @constructor
 */
Blockly.FieldExternalInput = function(content, externalInputHandler) {
	Blockly.FieldExternalInput.superClass_.constructor.call(this, 'Click to edit the content');

	this.externalInputHandler_ = externalInputHandler;
	// Set the initial state.
	this.setValue(content);
};
goog.inherits(Blockly.FieldExternalInput, Blockly.Field);

/**
 * Set the content in this field.
 * @param {string} content New content.
 */
Blockly.FieldExternalInput.prototype.setValue = function(content) {
	this.content_ = content;
};

/**
 * Return the content in this field.
 * @return {string} Current content.
 */
Blockly.FieldExternalInput.prototype.getValue = function() {
	return this.content_;
};

/**
 * Mouse cursor style when over the hotspot that initiates the editor.
 */
Blockly.FieldExternalInput.prototype.CURSOR = 'default';

/**
 * Show the external editor.
 *
 * @private
 */
Blockly.FieldExternalInput.prototype.showEditor_ = function() {
	/**
	 * Call the external function for the input and
     * provide the callback function to receive the new value.
     *
     * Note: Function.bind() requires ECMAScript 5.
     * See http://kangax.github.com/es5-compat-table/ for compatibility
     */
	if (!Function.bind) {
		alert("your browser does not support ECMAScript 5");
		return;
	}
	if (!this.externalInputHandler_)
		return;
	this.externalInputHandler_(this.getValue(), this.externalInputCallback_.bind(this));
};

Blockly.FieldExternalInput.prototype.externalInputCallback_ = function(text) {
	this.setValue(text);
};
