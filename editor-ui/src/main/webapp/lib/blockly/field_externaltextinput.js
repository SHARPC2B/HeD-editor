/**
 * Visual Blocks Editor
 *
 * Copyright 2012 Google Inc.
 * http://code.google.com/p/blockly/
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
 * Copied from field_textinput.js
 *
 * Text input field which uses an external editor.
 *
 * @fileoverview Text input field.  Used for editable titles and variables.
 * @author fraser@google.com (Neil Fraser)
 * @author aabeling
 */

/**
 * Class for a text field using an external editor.
 *
 * @param {string} text The initial content of the field.
 * @param {Function} externalInputFunc a mandatory function which
 *   creates the external editor. The function receives two arguments:
 *   the current text and a callback function which must be called
 *   by the external editor to provide the new value.
 *
 * @constructor
 */
Blockly.FieldExternalTextInput = function(text, externalInputFunc) {
  // Call parent's constructor.
  Blockly.Field.call(this, text);
  this.externalInputFunc = externalInputFunc;
};

// FieldExternalTextInput is a subclass of Field.
Blockly.FieldExternalTextInput.prototype = new Blockly.Field(null);

/**
 * Set the text in this field.
 * @param {string} text New text.
 */
Blockly.FieldExternalTextInput.prototype.setText = function(text) {
  Blockly.Field.prototype.setText.call(this, text);
};

/**
 * Mouse cursor style when over the hotspot that initiates the editor.
 */
Blockly.FieldExternalTextInput.prototype.CURSOR = 'text';

/**
 * Show the external editor.
 *
 * @private
 */
Blockly.FieldExternalTextInput.prototype.showEditor_ = function()
{
        /*
         * Call the external function for the input and
         * provide the callback function to receive the new value.
         *
         * Note: Function.bind() requires ECMAScript 5.
         * See http://kangax.github.com/es5-compat-table/ for compatibility.
         */
        if ( !Function.bind ) {
                alert("your browser does not support ECMAScript 5");
                return;
        }

        if ( !this.externalInputFunc ) return;

        this.externalInputFunc(this.getText(),this.externalInputCallback_.bind(this));
};

Blockly.FieldExternalTextInput.prototype.externalInputCallback_ = function(text)
{
        this.setText(text);
};
