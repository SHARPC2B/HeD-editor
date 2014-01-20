
Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AtanIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AtanInteger')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AtanInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AvgRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AvgReal')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AvgReal' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'AvgReal_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MaxExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Max')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Max' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Max_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#CodedOrdinalLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'CodedOrdinal' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'code [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'codeSystem [string]' );
        
    
        this.appendValueInput( 'ARG_2' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'codeSystemName [string]' );
        
    
        this.appendValueInput( 'ARG_3' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'displayName [string]' );
        
    
        this.appendValueInput( 'ARG_4' )
                             .setCheck( 'xsd:double' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'value [double]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#CodedOrdinalType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : CodedOrdinal' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EarliestExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Earliest')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Earliest' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Earliest_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexMaxExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IndexMax')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexMax' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'IndexMax_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualScalarExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('EqualScalar')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : EqualScalar' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops#PropertyGetExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Get')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#ObjectType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Obj]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainProperty' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Dom]' );
 
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Get' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LessExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Less')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Less' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SortExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Sort')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Sort' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#VarianceExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Variance')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Variance' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Variance_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#RealIntervalLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'RealInterval' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:double' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'high [double]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'highClosed [boolean]' );
        
    
        this.appendValueInput( 'ARG_2' )
                             .setCheck( 'xsd:double' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'low [double]' );
        
    
        this.appendValueInput( 'ARG_3' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'lowClosed [boolean]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#RealIntervalType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : RealInterval' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LnExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Ln')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Ln' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AllTrueExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AllTrue')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AllTrue' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'AllTrue_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('EqualList')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType','http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : EqualList' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CoalesceExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Coalesce')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Coalesce' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Coalesce_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AndExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('And')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : And' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'And_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ContainsListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ContainsList')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ContainsList' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PowerRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('PowerReal')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : PowerReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AfterExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('After')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : After' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CeilingExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Ceiling')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Ceiling' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#StdDevExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('StdDev')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : StdDev' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'StdDev_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SumRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('SumReal')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : SumReal' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'SumReal_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#OverlapsExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Overlaps')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Overlaps' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ExpIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ExpInteger')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ExpInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SumIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('SumInteger')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : SumInteger' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'SumInteger_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SinRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('SinReal')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : SinReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PredExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Pred')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Pred' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AsinIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AsinInteger')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AsinInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IntervalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Interval')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Interval' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ProperInIntervalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ProperInInterval')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ProperInInterval' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#GreaterOrEqualExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('GreaterOrEqual')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : GreaterOrEqual' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#ClinicalRequestExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'ClinicalRequestExpression' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:anyURI' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'datatype [anyURI]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:anyURI' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'dateProperty [anyURI]' );


        this.appendValueInput( 'ARG_2' )
                             .setCheck( 'xsd:anyURI' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'codeProperty [anyURI]' );


        this.appendValueInput( 'ARG_3' )
                             .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'codes [Col, Lis]' );


        this.appendValueInput( 'ARG_4' )
                             .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'dateRange [Col, Lis]' );


        this.appendValueInput( 'ARG_5' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'singleCardinality [boolean]' );


        this.appendValueInput( 'ARG_6' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'initial [boolean]' );

    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#CollectionType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : ClinicalRequestExpression' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsEmptyListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IsEmptyList')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IsEmptyList' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexLatestExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IndexLatest')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexLatest' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'IndexLatest_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SqrtRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('SqrtReal')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : SqrtReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#OrExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Or')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Or' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Or_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#UnionIntervalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('UnionInterval')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : UnionInterval' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'UnionInterval_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ExistExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Exist')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Exist' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Exist_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#DateLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Date' );


    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#DateType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Date' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SinIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('SinInteger')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : SinInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualBooleanExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('EqualBoolean')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Boo]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Boo]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : EqualBoolean' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#StringLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'String' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'value [string]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#StringType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : String' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ContainsIntervalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ContainsInterval')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ContainsInterval' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#QuantityIntervalLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'QuantityInterval' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'high [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'highClosed [boolean]' );
        
    
        this.appendValueInput( 'ARG_2' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'low [string]' );
        
    
        this.appendValueInput( 'ARG_3' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'lowClosed [boolean]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#QuantityIntervalType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : QuantityInterval' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LastExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Last')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Last' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CountExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Count')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Count' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Count_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AbsRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AbsReal')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AbsReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LessOrEqualExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('LessOrEqual')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : LessOrEqual' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SeqToExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('SeqTo')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : SeqTo' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops#PropertySetExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Set')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainProperty' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Dom]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#PropertyType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Set' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#ComplexLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Complex' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'value [string]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#ComplexType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Complex' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#BeginExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Begin')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Begin' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#WithinIntervalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('WithinInterval')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : WithinInterval' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AcosIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AcosInteger')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AcosInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TrimRightExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('TrimRight')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : TrimRight' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AtanRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AtanReal')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AtanReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#WithinAnyExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('WithinAny')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : WithinAny' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CosRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('CosReal')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : CosReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LowerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Lower')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Lower' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MultiplyRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('MultiplyReal')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : MultiplyReal' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#IntegerLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Integer' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:int' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'value [int]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#IntegerType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Integer' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ModuloRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ModuloReal')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ModuloReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TrimLeftExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('TrimLeft')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : TrimLeft' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#BooleanLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Boolean' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'value [boolean]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#BooleanType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Boolean' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#RatioLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Ratio' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'denominator [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'numerator [string]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#RatioType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Ratio' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ObjectRedefineExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ObjectRedefine')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#ObjectType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Obj]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#ObjectType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ObjectRedefine' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MaxIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('MaxInteger')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : MaxInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MeetsExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Meets')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Meets' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsNotEmptyExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IsNotEmpty')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IsNotEmpty' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#TimestampLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Timestamp' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:dateTimeStamp' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'value [dateTimeStamp]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#TimestampType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Timestamp' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LengthIntervalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('LengthInterval')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : LengthInterval' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ProperContainsListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ProperContainsList')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ProperContainsList' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IfNullExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IfNull')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IfNull' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#AddressLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Address' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'part [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'use [string]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#AddressType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Address' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IntersectListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IntersectList')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IntersectList' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#StringExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('String')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : String' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TruncatedDivideExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('TruncatedDivide')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : TruncatedDivide' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LatestExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Latest')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Latest' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Latest_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NegateIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('NegateInteger')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : NegateInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MultiplyIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('MultiplyInteger')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : MultiplyInteger' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#CodeLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Code' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'code [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'codeSystem [string]' );
        
    
        this.appendValueInput( 'ARG_2' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'codeSystemName [string]' );
        
    
        this.appendValueInput( 'ARG_3' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'displayName [string]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#CodeType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Code' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DifferenceListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('DifferenceList')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : DifferenceList' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('List')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : List' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'List_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MinRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('MinReal')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : MinReal' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#PhysicalQuantityIntervalLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'PhysicalQuantityInterval' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'high [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'highClosed [boolean]' );
        
    
        this.appendValueInput( 'ARG_2' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'low [string]' );
        
    
        this.appendValueInput( 'ARG_3' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'lowClosed [boolean]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#PhysicalQuantityIntervalType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : PhysicalQuantityInterval' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MedianExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Median')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Median' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Median_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ExpRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ExpReal')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ExpReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ExtractCharactersExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ExtractCharacters')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ExtractCharacters' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'ExtractCharacters_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TanRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('TanReal')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : TanReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops#CreateExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('New')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainClass' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Dom]' );
 
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Pro]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Pro]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#ObjectType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : New' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'New_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Pro]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Pro]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PowerIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('PowerInteger')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : PowerInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DateDiffExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('DateDiff')


    
    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#TimestampType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Tim]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#TimestampType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Tim]' );
    this.appendValueInput( 'ARG_2' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : DateDiff' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ReverseExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Reverse')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Reverse' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Reverse_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LogExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Log')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Log' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#FirstExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('First')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : First' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualStringExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('EqualString')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : EqualString' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EndExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('End')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : End' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#IdentifierLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Identifier' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'extension [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'root [string]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#IdentifierType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Identifier' );
  }
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#PeriodLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Period' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'alignment [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'count [string]' );
        
    
        this.appendValueInput( 'ARG_2' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'frequency [string]' );
        
    
        this.appendValueInput( 'ARG_3' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'isFlexible [boolean]' );
        
    
        this.appendValueInput( 'ARG_4' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'period [string]' );
        
    
        this.appendValueInput( 'ARG_5' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'phase [string]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#PeriodType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Period' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#FilterExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Filter')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Boo]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Filter' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualObjectExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('EqualObject')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#ObjectType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Obj]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#ObjectType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Obj]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : EqualObject' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AbsIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AbsInteger')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AbsInteger' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#TimestampIntervalLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'TimestampInterval' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'high [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'highClosed [boolean]' );
        
    
        this.appendValueInput( 'ARG_2' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'low [string]' );
        
    
        this.appendValueInput( 'ARG_3' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'lowClosed [boolean]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#TimestampIntervalType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : TimestampInterval' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NotEqualExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('NotEqual')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : NotEqual' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#UnionListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('UnionList')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : UnionList' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'UnionList_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ModuloIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ModuloInteger')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ModuloInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexMinExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IndexMin')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexMin' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'IndexMin_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsEmptyCollectionExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IsEmptyCollection')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IsEmptyCollection' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#RealLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Real' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:double' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'value [double]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#RealType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Real' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ConcatExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Concat')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Concat' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Concat_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#InListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('InList')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : InList' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#EntityNameLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'EntityName' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'part [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'use [string]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#EntityNameType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : EntityName' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#UpperExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Upper')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Upper' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexOfExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IndexOf')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexOf' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#FlattenExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Flatten')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Flatten' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Flatten_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#RoundExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Round')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Round' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#InAnyExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('InAny')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : InAny' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#IntegerIntervalLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'IntegerInterval' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:int' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'high [int]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'highClosed [boolean]' );
        
    
        this.appendValueInput( 'ARG_2' )
                             .setCheck( 'xsd:int' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'low [int]' );
        
    
        this.appendValueInput( 'ARG_3' )
                             .setCheck( 'xsd:boolean' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'lowClosed [boolean]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#IntegerIntervalType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : IntegerInterval' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#XorExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Xor')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Xor' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Xor_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CombineExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Combine')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Combine' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LengthStringExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('LengthString')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : LengthString' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops#IteratorExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('BUG')


    
    
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : BUG' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#FloorExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Floor')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Floor' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PosExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Pos')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Pos' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#PhysicalQuantityLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'PhysicalQuantity' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'unit [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:double' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'value [double]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#PhysicalQuantityType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : PhysicalQuantity' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SubtractIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('SubtractInteger')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : SubtractInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SplitExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Split')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Split' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#BeforeExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Before')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Before' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AcosRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AcosReal')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AcosReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualIntervalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('EqualInterval')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : EqualInterval' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DivideRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('DivideReal')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : DivideReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AsinRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AsinReal')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AsinReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AvgIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AvgInteger')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AvgInteger' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'AvgInteger_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DifferenceIntervalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('DifferenceInterval')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : DifferenceInterval' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ToStringExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ToString')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ToString' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AnyTrueExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AnyTrue')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AnyTrue' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'AnyTrue_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AvgTimeDurationExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AvgTimeDuration')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AvgTimeDuration' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'AvgTimeDuration_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexEarliestExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IndexEarliest')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexEarliest' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'IndexEarliest_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ConditionalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Conditional')


    
    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Boo]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_2' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Conditional' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CosIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('CosInteger')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : CosInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AddIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AddInteger')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AddInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MinExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Min')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Min' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Min_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NotExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Not')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Boo]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Not' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#Log10Expression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Log10')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Log10' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SubstringExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Substring')


    
    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
    this.appendValueInput( 'ARG_2' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Substring' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AllFalseExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AllFalse')


    
    
    
     
    this.appendValueInput( 'ARGS_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARGS_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
    this.itemCount_ = 2;
 

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AllFalse' );
  } ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'AllFalse_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('ARGS_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('ARGS_' + x)
                                       .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                       .setAlign( Blockly.ALIGN_RIGHT )
                                       .appendTitle( '[Col, Lis]' );
    }
    if (this.itemCount_ == 0) {
        this.appendDummyInput('EMPTY');
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
           this.removeInput('ARGS_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('ARGS_' + this.itemCount_)
                                          .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                                          .setAlign( Blockly.ALIGN_RIGHT )
                                          .appendTitle( '[Col, Lis]' );

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
      var input = this.getInput('ARGS_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ForEachExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ForEach')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ForEach' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ProperContainsIntervalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ProperContainsInterval')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ProperContainsInterval' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AddRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('AddReal')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : AddReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DatePartExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('DatePart')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#TimestampType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Tim]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : DatePart' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#RoundRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('RoundReal')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : RoundReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SubtractRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('SubtractReal')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : SubtractReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TanIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('TanInteger')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : TanInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SuccExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Succ')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Succ' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#SimpleCodeLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'SimpleCode' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'code [string]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#SimpleCodeType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : SimpleCode' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#GreaterExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Greater')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Greater' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexerStringExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IndexerString')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexerString' );
  } 
};


Blockly.Blocks['http://asu.edu/sharpc2b/ops#UrlLiteral'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle( 'Url' );


    
        this.appendValueInput( 'ARG_0' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'capabilities [string]' );
        
    
        this.appendValueInput( 'ARG_1' )
                             .setCheck( 'xsd:string' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'use [string]' );
        
    
        this.appendValueInput( 'ARG_2' )
                             .setCheck( 'xsd:anyURI' )
                             .setAlign( Blockly.ALIGN_RIGHT )
                             .appendTitle( 'value [anyURI]' );
        
    

    this.setOutput( true, 'http://asu.edu/sharpc2b/ops#UrlType' );

    this.setInputsInline( false );

    this.setTooltip( 'HeD literal : Url' );
  }
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DateAddExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('DateAdd')


    
    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#TimestampType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Tim]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_2' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#TimestampType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : DateAdd' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TrimExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Trim')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Str]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Trim' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IntersectIntervalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IntersectInterval')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IntersectInterval' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MaxRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('MaxReal')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : MaxReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexerListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IndexerList')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IndexerList' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NegateRealExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('NegateReal')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : NegateReal' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MinIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('MinInteger')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : MinInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DivideIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('DivideInteger')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : DivideInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsNullExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('IsNull')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : IsNull' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SqrtIntegerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('SqrtInteger')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntegerType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Int, Num, Rea]' );
 
    
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntegerType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : SqrtInteger' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ProperInListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('ProperInList')


    
     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
    this.appendValueInput( 'ARG_1' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[Col, Lis]' );
 
    
    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : ProperInList' );
  } 
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ConvertExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendTitle('Convert')


     
    this.appendValueInput( 'ARG_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendTitle( '[any]' );
 
    
    
    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( 'HeD expression : Convert' );
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


Blockly.Blocks['xsd:string'] = {
  init: function() {
    this.setColour(152);
    var textInput = new Blockly.FieldTextInput('');
    this.appendDummyInput().appendTitle( '[string]' ).appendTitle( textInput, 'VALUE' );
    this.setOutput( true, 'xsd:string' );
  }
};

Blockly.Blocks['xsd:anyURI'] = {
  init: function() {
    this.setColour(152);
    var textInput = new Blockly.FieldTextInput('');
    this.appendDummyInput().appendTitle( '[URI]' ).appendTitle( textInput, 'VALUE' );
    this.setOutput( true, 'xsd:anyURI' );
  }
};

Blockly.Blocks['xsd:dateTimeStamp'] = {
  init: function() {
    this.setColour(152);
    var textInput = new Blockly.FieldTextInput('');
    this.appendDummyInput().appendTitle( '[dateTime]' ).appendTitle( textInput, 'VALUE' );
    this.setOutput( true, 'xsd:dateTimeStamp' );
  }
};

Blockly.Blocks['xsd:int'] = {
  init: function() {
    this.setColour(152);
    var numInput = new Blockly.FieldTextInput();
    this.appendDummyInput().appendTitle( '[int]' ).appendTitle( numInput, 'VALUE' );
    this.setOutput( true, 'xsd:int' );
  }
};

Blockly.Blocks['xsd:boolean'] = {
  init: function() {
    this.setColour(152);
    var boolInput = new Blockly.FieldCheckbox('TRUE');
    this.appendDummyInput().appendTitle( '[bool]' ).appendTitle( boolInput, 'VALUE' );
    this.setOutput( true, 'xsd:boolean' );
  }
};

Blockly.Blocks['xsd:double'] = {
  init: function() {
    this.setColour(152);
    var numInput = new Blockly.FieldTextInput();
    this.appendDummyInput().appendTitle( '[double]' ).appendTitle( numInput, 'VALUE' );
    this.setOutput( true, 'xsd:double' );
  }
};


