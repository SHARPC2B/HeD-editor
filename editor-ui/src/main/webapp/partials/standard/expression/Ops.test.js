Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Concat'] = {
  init: function() {

    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
    	.setCheck('http://asu.edu/sharpc2b/ops#stringType')
    	.setAlign(Blockly.ALIGN_RIGHT)
    	.appendTitle( 'Concat' );
	this.appendValueInput("ARG_1")
    	.setCheck('http://asu.edu/sharpc2b/ops#stringType')
    	.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#stringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Concat' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'Concat' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
        if (this.itemCount_ == 0)
      	  input.appendTitle( 'Concat' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};
Blockly.Blocks.argument_group_container = {
	init: function() {
		this.setColour(230);
		this.appendDummyInput().appendTitle('Add arguments');
		this.appendStatementInput('STACK');
		this.setTooltip('Add, or remove arguments');
		this.contextMenu = false;
	}
};
Blockly.Blocks.argument_group_item = {
	init: function() {
		this.setColour(230);
		this.appendDummyInput().appendTitle('Argument');
		this.setPreviousStatement(true);
		this.setNextStatement(true);
		this.setTooltip('Add a new argument');
		this.contextMenu = false;
	}
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MultiplyInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Multiply' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT );
    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Multiply' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Log10'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Log10' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Log10' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SubtractReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Subtract' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Subtract' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Floor'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Floor' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Floor' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#UnionInterval'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#intervalType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'Union' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#intervalType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#intervalType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Union' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'Union' );
      }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
        if (this.itemCount_ == 0)
        	input.appendTitle( 'Union' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Succ'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Succ' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Succ' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Max'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#anyType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'Max' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#anyType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Max' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'Max' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                         	  input.appendTitle( 'Concat' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualList'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Equal' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Equal' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Convert'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Convert' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Convert' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualBoolean'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Equal' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Equal' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Overlaps'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Overlaps' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Overlaps' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DifferenceList'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Difference' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Difference' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MultiplyReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Multiply' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Multiply' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexMin'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#orderedType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'IndexMin' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#orderedType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexMin' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#orderedType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'IndexMin' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#orderedType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                         	  input.appendTitle( 'IndexMin' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsEmptyCollection'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#collectionType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'IsEmpty' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IsEmpty' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AddInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Add' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Add' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Xor'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#booleanType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'Xor' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#booleanType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Xor' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'Xor' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'Xor' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ExtractCharacters'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#stringType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'ExtractCharacters' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#stringType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ExtractCharacters' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'ExtractCharacters' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'ExtractCharacters' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#UnionList'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#listType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'Union' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#listType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Union' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'Union' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_container');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'Union' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ObjectRedefine'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#objectType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'ObjectRedefine' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#objectType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ObjectRedefine' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NegateReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Negate' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Negate' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AvgReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#realType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'Avg' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#realType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Avg' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'Avg' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'Avg' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IntersectList'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Intersect' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Intersect' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CosInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Cos' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Cos' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ProperContainsList'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'ProperContains' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ProperContains' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AbsInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Abs' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Abs' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Round'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Round' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Round' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Last'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Last' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Last' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ExpInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Exp' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Exp' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualString'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Equal' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Equal' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Median'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#realType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'Median' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#realType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Median' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'Median' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'Median' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Begin'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Begin' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Begin' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AvgInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#integerType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'Avg' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#integerType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Avg' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'Avg' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'Avg' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Earliest'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#anyType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'Earliest' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#anyType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Earliest' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'Earliest' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'Earliest' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexLatest'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#anyType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'IndexLatest' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#anyType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexLatest' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'IndexLatest' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'IndexMin' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Less'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#scalarType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Less' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#scalarType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Less' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TanInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Tan' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Tan' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MinReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Min' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Min' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ContainsList'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Contains' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Contains' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ToString'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'ToString' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#stringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ToString' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Trim'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Trim' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#stringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Trim' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Combine'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Combine' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#stringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Combine' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#List'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#anyType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'List' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#anyType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : List' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'List' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'List' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TruncatedDivide'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'TruncatedDivide' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : TruncatedDivide' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#WithinAny'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Within' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Within' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#StdDev'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#numberType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'StdDev' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#numberType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#numberType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : StdDev' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#numberType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'StdDev' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#numberType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'StdDev' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AcosInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Acos' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Acos' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexerList'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Indexer' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Indexer' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MinInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Min' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Min' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexOf'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'IndexOf' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexOf' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ModuloInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Modulo' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Modulo' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AllFalse'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#booleanType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'AllFalse' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#booleanType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AllFalse' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'AllFalse' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'AllFalse' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AbsReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Abs' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Abs' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#GreaterOrEqual'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#scalarType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'GreaterOrEqual' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#scalarType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : GreaterOrEqual' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NotEqual'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'NotEqual' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : NotEqual' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ModuloReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Modulo' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Modulo' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NegateInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Negate' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Negate' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Exist'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#anyType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'Exist' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#anyType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Exist' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'Exist' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'Exist' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexMax'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );
    this.appendValueInput("ARG_0")
		.setCheck('http://asu.edu/sharpc2b/ops#orderedType')
		.setAlign(Blockly.ALIGN_RIGHT)
		.appendTitle( 'IndexMax' );
	this.appendValueInput("ARG_1")
		.setCheck('http://asu.edu/sharpc2b/ops#orderedType')
		.setAlign(Blockly.ALIGN_RIGHT);
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexMax' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#orderedType' )
                                       .setAlign( Blockly.ALIGN_RIGHT );
      if (x == 0)
    	  input.appendTitle( 'IndexMax' );
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'argument_group_container');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'argument_group_item');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#orderedType' )
                                         .setAlign( Blockly.ALIGN_RIGHT );
                                         if (this.itemCount_ == 0)
                                        	  input.appendTitle( 'IndexMax' );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MaxInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Max' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Max' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MaxReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Max' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Max' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AddReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'Add' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Add' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AllTrue'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#AllTrue'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AllTrue' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#AllTrue>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#AllTrue>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Min'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#Min'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Min' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#Min>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#Min>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SinReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Sin' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Latest'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#Latest'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Latest' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#Latest>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#Latest>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TrimLeft'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#stringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : TrimLeft' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ProperContainsInterval'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ProperContains' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Lower'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#stringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Lower' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PowerReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Power' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DatePart'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#timestampType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#dateGranularityType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : DatePart' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsNotEmpty'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IsNotEmpty' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SinInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Sin' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Ln'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Ln' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DifferenceInterval'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#intervalType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Difference' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Property'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#objectType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Property' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LengthInterval'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Length' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SubtractInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Subtract' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Reverse'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#Reverse'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Reverse' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#Reverse>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#Reverse>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TrimRight'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#stringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : TrimRight' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ProperInList'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ProperIn' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Interval'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#intervalType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Interval' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AnyTrue'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#AnyTrue'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AnyTrue' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#AnyTrue>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#AnyTrue>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TanReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Tan' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Ceiling'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Ceiling' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Not'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Not' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Greater'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#scalarType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#scalarType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Greater' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AsinReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Asin' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#RoundReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Round' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DateDiff'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#timestampType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#timestampType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );
    this.appendValueInput( 'ARG_2' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#dateGranularityType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_3' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : DateDiff' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Substring'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#stringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Substring' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IntersectInterval'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#intervalType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Intersect' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Flatten'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#Flatten'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Flatten' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#Flatten>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#Flatten>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualObject'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#objectType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#objectType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Equal' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#WithinInterval'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Within' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AvgTimeDuration'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#AvgTimeDuration'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#timeDurationType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Avg' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#timeDurationType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#AvgTimeDuration>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#AvgTimeDuration>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#timeDurationType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SeqTo'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : SeqTo' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Log'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Log' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AcosReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Acos' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#InAny'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : In' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AsinInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Asin' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ExpReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Exp' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Pos'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Pos' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SumInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#SumInteger'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Sum' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#SumInteger>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#SumInteger>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsEmptyList'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IsEmpty' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualScalar'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#scalarType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#scalarType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Equal' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Split'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Split' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexerString'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#stringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Indexer' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ForEach'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#expressionType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ForEach' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CosReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Cos' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Sort'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Sort' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#And'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#And'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : And' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#And>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#And>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AtanReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Atan' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Upper'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#stringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Upper' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#InList'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : In' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DateAdd'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#timestampType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#dateGranularityType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );
    this.appendValueInput( 'ARG_2' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_3' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#timestampType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : DateAdd' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LessOrEqual'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#scalarType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#scalarType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : LessOrEqual' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IfNull'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IfNull' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Or'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#Or'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Or' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#Or>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#Or>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Meets'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Meets' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsNull'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IsNull' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexEarliest'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#IndexEarliest'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexEarliest' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#IndexEarliest>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#IndexEarliest>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Filter'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#listType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Filter' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#String'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#stringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : String' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ContainsInterval'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Contains' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AtanInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Atan' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SqrtReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Sqrt' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PowerInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Power' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Conditional'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#booleanType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );
    this.appendValueInput( 'ARG_2' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_3' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Conditional' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Coalesce'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#Coalesce'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Coalesce' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#Coalesce>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#Coalesce>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Pred'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Pred' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DivideInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Divide' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Variance'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#Variance'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#numberType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Variance' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#numberType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#Variance>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#Variance>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#numberType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SumReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#SumReal'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Sum' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#SumReal>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#SumReal>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualInterval'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Equal' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Count'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.setMutator( new Blockly.Mutator( ['http://asu.edu/sharpc2b/ops-set#Count'] ) );
    this.itemCount_ = 1;


    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#integerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Count' );
  } ,


  mutationToDom: function(workspace) {
    var container = document.createElement( 'mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARG_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARG_' + x)
                                       .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( 'arg_' + x );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
    }
  },

  decompose: function(workspace) {
      var containerBlock = new Blockly.Block(workspace,
                                             'http://asu.edu/sharpc2b/ops-set#Count>');
      containerBlock.initSvg();
      var connection = containerBlock.getInput('STACK').connection;
      for (var x = 0; x < this.itemCount_; x++) {
        var itemBlock = new Blockly.Block(workspace, 'http://asu.edu/sharpc2b/ops-set#Count>');
        itemBlock.initSvg();
        connection.connect(itemBlock.previousConnection);
        connection = itemBlock.nextConnection;
      }
      return containerBlock;
    },

  compose: function(containerBlock) {
      if (this.itemCount_ == 0) {
        this.removeInput('EMPTY');
      } else {
        for (var x = this.itemCount_ - 1; x >= 0; x--) {
          this.removeInput('ARG_' + x);
        }
      }
      this.itemCount_ = 0;
      // Rebuild the block's inputs.
      var itemBlock = containerBlock.getInputTargetBlock('STACK');
      while (itemBlock) {
        var input = this.appendValueInput('ARG_' + this.itemCount_)
                                         .setCheck( 'http://asu.edu/sharpc2b/ops#anyType' )
                                         .setAlign( Blockly.ALIGN_RIGHT )
                                         .appendTitle( 'arg_' + this.itemCount_ );
        // Reconnect any child blocks.
        if (itemBlock.valueConnection_) {
          input.connection.connect(itemBlock.valueConnection_);
        }
        this.itemCount_++;
        itemBlock = itemBlock.nextConnection &&
            itemBlock.nextConnection.targetBlock();
      }
      if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
      }

    },

  saveConnections: function(containerBlock) {
    var itemBlock = containerBlock.getInputTargetBlock('STACK');
    var x = 0;
    while (itemBlock) {
      var input = this.getInput('ARG_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }



};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SqrtInteger'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#integerType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Sqrt' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#First'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#listType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : First' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ProperInInterval'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ProperIn' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Before'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Before' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DivideReal'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#realType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Divide' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#After'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_1' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg_2' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#booleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : After' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#End'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#intervalType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#anyType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : End' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LengthString'] = {
  init: function() {
    //this.setHelpUrl( 'TBD HeD wiki / IG ' );

    this.setColour( 42 );


    this.appendValueInput( 'ARG_0' )
                         .setCheck( 'http://asu.edu/sharpc2b/ops#stringType' )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( 'arg' );



    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#realType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Length' );
  }
};

