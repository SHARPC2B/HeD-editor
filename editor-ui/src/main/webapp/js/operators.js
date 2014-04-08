
Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MaxExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Max');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#path_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'path_string' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Max \n Return type : [Num] \n Allowed types :  path_string : [string] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Is');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#isType' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainClass' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'isType' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Is \n Return type : [Boo] \n Allowed types :  firstOperand : [any] \n  isType : [Dom] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LessExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Less');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Less \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  secondOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SetSubsumesExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('SetSubsumes');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#ancestors' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'ancestors' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#descendents' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'descendents' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : SetSubsumes \n Return type : [Boo] \n Allowed types :  ancestors : [Int, Lis, Tim] \n  descendents : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PeriodLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('PeriodLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#isFlexible' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'isFlexible' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#count' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#int' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'count' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#alignment' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'alignment' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#frequency' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#RatioType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'frequency' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#period' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PhysicalQuantityType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'period' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#phase' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'phase' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : PeriodLiteral \n Return type : [Col, Int, Lis] \n Allowed types :  isFlexible : [boolean] \n  count : [int] \n  alignment : [string] \n  frequency : [Rat] \n  period : [Phy] \n  phase : [Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ContainsExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Contains');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#element' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'element' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Contains \n Return type : [Boo] \n Allowed types :  element : [any] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SortExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Sort');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#orderBy' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'orderBy' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Sort \n Return type : [Col, Lis] \n Allowed types :  orderBy : [string] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AsExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('As');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#strict' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'strict' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#asType' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainClass' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'asType' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : As \n Return type : [any] \n Allowed types :  strict : [boolean] \n  firstOperand : [any] \n  asType : [Dom] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LnExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Ln');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Ln \n Return type : [Num] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AllTrueExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('AllTrue');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#path_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'path_string' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : AllTrue \n Return type : [Boo] \n Allowed types :  path_string : [string] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IntegerIntervalLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('IntegerIntervalLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#lowClosed' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'lowClosed' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#low' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#int' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'low' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#highClosed' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'highClosed' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#high' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#int' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'high' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : IntegerIntervalLiteral \n Return type : [Col, Int, Lis] \n Allowed types :  lowClosed : [boolean] \n  low : [int] \n  highClosed : [boolean] \n  high : [int] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AddExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Add');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Add \n Return type : [Num] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  secondOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SubsumesExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Subsumes');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#ancestor' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CodeType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'ancestor' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#descendent' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CodeType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'descendent' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Subsumes \n Return type : [Boo] \n Allowed types :  ancestor : [Cod] \n  descendent : [Cod] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CodeLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('CodeLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#displayName' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'displayName' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#codeSystemName' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'codeSystemName' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#codeSystem' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'codeSystem' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#code' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'code' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CodeType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : CodeLiteral \n Return type : [Cod] \n Allowed types :  displayName : [string] \n  codeSystemName : [string] \n  codeSystem : [string] \n  code : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CoalesceExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Coalesce');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#hasOperand_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand' );
    
        this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
        this.itemCount_ = 1;
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Coalesce \n Return type : [any] \n Allowed types :  hasOperand : [any] \n  ' );
  }
  ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Coalesce_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x)
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [any]' );
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
           this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + this.itemCount_)
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [any]' );

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
      var input = this.getInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
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

    this.appendDummyInput().appendField('And');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#hasOperand_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand' );
    
        this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
        this.itemCount_ = 1;
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : And \n Return type : [Boo] \n Allowed types :  hasOperand : [Boo] \n  ' );
  }
  ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'And_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [Boo]' );
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
           this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + this.itemCount_)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [Boo]' );

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
      var input = this.getInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PhysicalQuantityLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('PhysicalQuantityLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value_double' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#double' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value_double' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#unit' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'unit' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#PhysicalQuantityType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : PhysicalQuantityLiteral \n Return type : [Phy] \n Allowed types :  value_double : [double] \n  unit : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AfterExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('After');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : After \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Tim] \n  secondOperand : [Int, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MultiplyExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Multiply');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Multiply \n Return type : [Num] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  secondOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CeilingExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Ceiling');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Ceiling \n Return type : [Rea, Num, Int] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#OverlapsExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Overlaps');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Overlaps \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Tim] \n  secondOperand : [Int, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PhysicalQuantityIntervalLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('PhysicalQuantityIntervalLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#lowClosed' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'lowClosed' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#highClosed' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'highClosed' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#high_PhysicalQuantity' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PhysicalQuantityType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'high_PhysicalQuantity' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#low_PhysicalQuantity' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PhysicalQuantityType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'low_PhysicalQuantity' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : PhysicalQuantityIntervalLiteral \n Return type : [Col, Int, Lis] \n Allowed types :  lowClosed : [boolean] \n  highClosed : [boolean] \n  high_PhysicalQuantity : [Phy] \n  low_PhysicalQuantity : [Phy] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PredExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Pred');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DateType','http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#StringType','http://asu.edu/sharpc2b/ops#TimestampType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Pred \n Return type : [any] \n Allowed types :  firstOperand : [Dat, Int, Num, Rat, Rea, Str, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CodedOrdinalLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('CodedOrdinalLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value_double' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#double' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value_double' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#displayName' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'displayName' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#codeSystemName' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'codeSystemName' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#codeSystem' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'codeSystem' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#code' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'code' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CodeType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : CodedOrdinalLiteral \n Return type : [Cod] \n Allowed types :  value_double : [double] \n  displayName : [string] \n  codeSystemName : [string] \n  codeSystem : [string] \n  code : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ModuloExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Modulo');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Modulo \n Return type : [Num] \n Allowed types :  firstOperand : [Int] \n  secondOperand : [Int] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TimestampLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('TimestampLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value_dateTime' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#dateTime' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value_dateTime' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#DateType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : TimestampLiteral \n Return type : [Dat] \n Allowed types :  value_dateTime : [dateTime] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IntervalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Interval');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#endOpen' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'endOpen' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#beginOpen' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'beginOpen' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#begin' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DateType','http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#StringType','http://asu.edu/sharpc2b/ops#TimestampType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'begin' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#end' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DateType','http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#StringType','http://asu.edu/sharpc2b/ops#TimestampType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'end' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Interval \n Return type : [Col, Int, Lis] \n Allowed types :  endOpen : [boolean] \n  beginOpen : [boolean] \n  begin : [Dat, Int, Num, Rat, Rea, Str, Tim] \n  end : [Dat, Int, Num, Rat, Rea, Str, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ProperContainsExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ProperContains');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ProperContains \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Lis, Tim] \n  secondOperand : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ComplexLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ComplexLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value_string' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ComplexLiteral \n Return type : [any] \n Allowed types :  value_string : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#GreaterOrEqualExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('GreaterOrEqual');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : GreaterOrEqual \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  secondOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NowExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Now');

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#DateType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Now \n Return type : [Dat] \n Allowed types :  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DistinctExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Distinct');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Distinct \n Return type : [Col, Lis] \n Allowed types :  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#OrExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Or');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#hasOperand_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand' );
    
        this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
        this.itemCount_ = 1;
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Or \n Return type : [Boo] \n Allowed types :  hasOperand : [Boo] \n  ' );
  }
  ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Or_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [Boo]' );
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
           this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + this.itemCount_)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [Boo]' );

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
      var input = this.getInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DivideExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Divide');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Divide \n Return type : [Num] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  secondOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#BooleanLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('BooleanLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value_boolean' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value_boolean' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : BooleanLiteral \n Return type : [Boo] \n Allowed types :  value_boolean : [boolean] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ClinicalRequestExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ClinicalRequest');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#useValueSets' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'useValueSets' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#useSubsumption' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'useSubsumption' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#triggerType' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'triggerType' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#templateId' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'templateId' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#scope' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'scope' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#isInitial' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'isInitial' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#cardinality' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'cardinality' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#codeProperty' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainProperty' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'codeProperty' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#codes' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'codes' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#dataType' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainClass' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'dataType' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#dateProperty' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainProperty' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'dateProperty' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#dateRange' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'dateRange' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#idProperty' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainProperty' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'idProperty' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#subject' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'subject' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#subjectProperty' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainProperty' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'subjectProperty' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#timeOffset' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'timeOffset' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ClinicalRequest \n Return type : [Col] \n Allowed types :  useValueSets : [boolean] \n  useSubsumption : [boolean] \n  triggerType : [string] \n  templateId : [string] \n  scope : [string] \n  isInitial : [boolean] \n  cardinality : [string] \n  codeProperty : [Dom] \n  codes : [Int, Lis, Tim] \n  dataType : [Dom] \n  dateProperty : [Dom] \n  dateRange : [Int, Tim] \n  idProperty : [Dom] \n  subject : [any] \n  subjectProperty : [Dom] \n  timeOffset : [Int] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ObjectExpressionExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ObjectExpression');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#objectType' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainClass' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'objectType' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#property_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'property' );
    
        this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
        this.itemCount_ = 1;
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#ObjectType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ObjectExpression \n Return type : [Obj] \n Allowed types :  objectType : [Dom] \n  property : [Pro] \n  ' );
  }
  ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'ObjectExpression_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('http://asu.edu/sharpc2b/ops-set#property_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('http://asu.edu/sharpc2b/ops-set#property_' + x)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'property [Pro]' );
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
           this.removeInput('http://asu.edu/sharpc2b/ops-set#property_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('http://asu.edu/sharpc2b/ops-set#property_' + this.itemCount_)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'property [Pro]' );

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
      var input = this.getInput('http://asu.edu/sharpc2b/ops-set#property_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LastExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Last');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#orderBy' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'orderBy' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Last \n Return type : [any] \n Allowed types :  orderBy : [string] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CountExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Count');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#path_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'path_string' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Count \n Return type : [Rea, Num, Int] \n Allowed types :  path_string : [string] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LessOrEqualExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('LessOrEqual');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : LessOrEqual \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  secondOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops#PropertySetExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Set');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType','http://asu.edu/sharpc2b/ops#ClassType','http://asu.edu/sharpc2b/ops#CodeType','http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#DateType','http://asu.edu/sharpc2b/ops#DomainClass','http://asu.edu/sharpc2b/ops#DomainProperty','http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#ObjectType','http://asu.edu/sharpc2b/ops#PhysicalQuantityType','http://asu.edu/sharpc2b/ops#PropertyType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#Set','http://asu.edu/sharpc2b/ops#StringType','http://asu.edu/sharpc2b/ops#TimeIntervalType','http://asu.edu/sharpc2b/ops#TimestampType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainProperty' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#PropertyType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Set \n Return type : [Pro] \n Allowed types :  firstOperand : [Boo, Cla, Cod, Col, Dat, Dom, Dom, Int, Int, Lis, Num, Obj, Phy, Pro, Rat, Rea, Set, Str, Tim, Tim] \n  secondOperand : [Dom] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#BeginExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Begin');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Begin \n Return type : [any] \n Allowed types :  firstOperand : [Int, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LowerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Lower');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Lower \n Return type : [Str] \n Allowed types :  firstOperand : [Str] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#RealIntervalLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('RealIntervalLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#low_double' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#double' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'low_double' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#lowClosed' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'lowClosed' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#high_double' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#double' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'high_double' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#highClosed' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'highClosed' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : RealIntervalLiteral \n Return type : [Col, Int, Lis] \n Allowed types :  low_double : [double] \n  lowClosed : [boolean] \n  high_double : [double] \n  highClosed : [boolean] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IdentifierLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('IdentifierLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#root' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'root' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#extension' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'extension' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : IdentifierLiteral \n Return type : [Str] \n Allowed types :  root : [string] \n  extension : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ObjectRedefineExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ObjectRedefine');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#scope' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'scope' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#property_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'property' );
    
        this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
        this.itemCount_ = 1;
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_Object' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#ObjectType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_Object' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#ObjectType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ObjectRedefine \n Return type : [Obj] \n Allowed types :  scope : [string] \n  property : [Pro] \n  source_Object : [Obj] \n  ' );
  }
  ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'ObjectRedefine_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('http://asu.edu/sharpc2b/ops-set#property_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('http://asu.edu/sharpc2b/ops-set#property_' + x)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'property [Pro]' );
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
           this.removeInput('http://asu.edu/sharpc2b/ops-set#property_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('http://asu.edu/sharpc2b/ops-set#property_' + this.itemCount_)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'property [Pro]' );

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
      var input = this.getInput('http://asu.edu/sharpc2b/ops-set#property_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MeetsExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Meets');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Meets \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Tim] \n  secondOperand : [Int, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IntersectExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Intersect');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#hasOperand_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand' );
    
        this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
        this.itemCount_ = 1;
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Intersect \n Return type : [Col, Lis] \n Allowed types :  hasOperand : [Int, Tim] \n  ' );
  }
  ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Intersect_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [Int, Tim]' );
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
           this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + this.itemCount_)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [Int, Tim]' );

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
      var input = this.getInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsNotEmptyExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('IsNotEmpty');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : IsNotEmpty \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#RealLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('RealLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value_double' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#double' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value_double' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : RealLiteral \n Return type : [Rea, Num] \n Allowed types :  value_double : [double] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IfNullExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('IfNull');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : IfNull \n Return type : [any] \n Allowed types :  firstOperand : [any] \n  secondOperand : [any] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ExpressionRefExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ExpressionRef');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#name' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'name' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ExpressionRef \n Return type : [any] \n Allowed types :  name : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Literal');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value_string' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#valueType' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#anyURI' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'valueType' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Literal \n Return type : [any] \n Allowed types :  value_string : [string] \n  valueType : [anyURI] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PropertyExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Property');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#scope' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'scope' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#path' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainProperty' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'path' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_Object' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#ObjectType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_Object' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Property \n Return type : [any] \n Allowed types :  scope : [string] \n  path : [Dom] \n  source_Object : [Obj] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#WithinExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Within');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#element_Ordinal' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DateType','http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#StringType','http://asu.edu/sharpc2b/ops#TimestampType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'element_Ordinal' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#interval' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'interval' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Within \n Return type : [Boo] \n Allowed types :  element_Ordinal : [Dat, Int, Num, Rat, Rea, Str, Tim] \n  interval : [Int, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TruncatedDivideExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('TruncatedDivide');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : TruncatedDivide \n Return type : [Rea, Num, Int] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  secondOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ValueSetExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ValueSet');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#version' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'version' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#id' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'id' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#authority' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'authority' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ValueSet \n Return type : [Col, Lis] \n Allowed types :  version : [string] \n  id : [string] \n  authority : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ListExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('List');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#key' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'key' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#element_0' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'element' );
    
        this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
        this.itemCount_ = 1;
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : List \n Return type : [Col, Lis] \n Allowed types :  key : [string] \n  element : [any] \n  ' );
  }
  ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'List_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('http://asu.edu/sharpc2b/ops-set#element_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('http://asu.edu/sharpc2b/ops-set#element_' + x)
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'element [any]' );
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
           this.removeInput('http://asu.edu/sharpc2b/ops-set#element_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('http://asu.edu/sharpc2b/ops-set#element_' + this.itemCount_)
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'element [any]' );

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
      var input = this.getInput('http://asu.edu/sharpc2b/ops-set#element_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Indexer');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#index' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'index' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Indexer \n Return type : [any] \n Allowed types :  firstOperand : [Int, Lis, Tim] \n  index : [Int] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SumExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Sum');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#path_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'path_string' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Sum \n Return type : [Num] \n Allowed types :  path_string : [string] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Literal');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value_string' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#valueType' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#anyURI' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'valueType' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Literal \n Return type : [any] \n Allowed types :  value_string : [string] \n  valueType : [anyURI] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EqualExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Equal');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Equal \n Return type : [Boo] \n Allowed types :  firstOperand : [any] \n  secondOperand : [any] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CaseExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Case');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#caseItem' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'caseItem' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#comparand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'comparand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#else' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'else' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Case \n Return type : [any] \n Allowed types :  caseItem : [any] \n  comparand : [any] \n  else : [any] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EntityNameLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('EntityNameLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#use' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'use' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#part' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'part' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : EntityNameLiteral \n Return type : [Str] \n Allowed types :  use : [string] \n  part : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DateDiffExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('DateDiff');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#endDate' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DateType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'endDate' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#granularity_String' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'granularity_String' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#startDate' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DateType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'startDate' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : DateDiff \n Return type : [Rea, Num, Int] \n Allowed types :  endDate : [Dat] \n  granularity_String : [Str] \n  startDate : [Dat] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LogExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Log');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Log \n Return type : [Rea, Num] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  secondOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#FirstExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('First');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#orderBy' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'orderBy' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : First \n Return type : [any] \n Allowed types :  orderBy : [string] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#LengthExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Length');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Length \n Return type : [Rea, Num, Int] \n Allowed types :  firstOperand : [any] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#QuantityIntervalLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('QuantityIntervalLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#low_double' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#double' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'low_double' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#lowClosed' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'lowClosed' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#high_double' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#double' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'high_double' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#highClosed' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'highClosed' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : QuantityIntervalLiteral \n Return type : [Col, Int, Lis] \n Allowed types :  low_double : [double] \n  lowClosed : [boolean] \n  high_double : [double] \n  highClosed : [boolean] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#EndExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('End');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : End \n Return type : [any] \n Allowed types :  firstOperand : [Int, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#FilterExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Filter');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#scope' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'scope' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#condition' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'condition' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Filter \n Return type : [Col, Lis] \n Allowed types :  scope : [string] \n  condition : [Boo] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AbsExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Abs');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Abs \n Return type : [Num] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#RatioLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('RatioLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#numerator' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#double' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'numerator' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#denominator' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#double' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'denominator' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : RatioLiteral \n Return type : [Rea, Num, Rat] \n Allowed types :  numerator : [double] \n  denominator : [double] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NotEqualExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('NotEqual');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : NotEqual \n Return type : [Boo] \n Allowed types :  firstOperand : [any] \n  secondOperand : [any] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CurrentExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Current');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#scope' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'scope' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#ObjectType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Current \n Return type : [Obj] \n Allowed types :  scope : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ConcatExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Concat');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#hasOperand_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand' );
    
        this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
        this.itemCount_ = 1;
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Concat \n Return type : [Str] \n Allowed types :  hasOperand : [Str] \n  ' );
  }
  ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Concat_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [Str]' );
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
           this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + this.itemCount_)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [Str]' );

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
      var input = this.getInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#UrlLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('UrlLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value_anyURI' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#anyURI' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value_anyURI' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#use' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'use' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#capabilities' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'capabilities' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : UrlLiteral \n Return type : [Str] \n Allowed types :  value_anyURI : [anyURI] \n  use : [string] \n  capabilities : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NullExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Null');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#valueType' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#anyURI' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'valueType' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Null \n Return type : [any] \n Allowed types :  valueType : [anyURI] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#UpperExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Upper');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Upper \n Return type : [Str] \n Allowed types :  firstOperand : [Str] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IndexOfExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('IndexOf');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#element' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'element' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : IndexOf \n Return type : [Rea, Num, Int] \n Allowed types :  element : [any] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#RoundExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Round');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#precision' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'precision' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Round \n Return type : [Rea, Num, Int] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  precision : [Int] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AddressLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('AddressLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#use' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'use' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#part' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'part' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : AddressLiteral \n Return type : [Str] \n Allowed types :  use : [string] \n  part : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#CombineExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Combine');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#separator' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'separator' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_String' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_String' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Combine \n Return type : [Str] \n Allowed types :  separator : [Str] \n  source_String : [Str] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IntegerLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('IntegerLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#int' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : IntegerLiteral \n Return type : [Rea, Num, Int] \n Allowed types :  value : [int] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TodayExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Today');

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#DateType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Today \n Return type : [Dat] \n Allowed types :  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#FloorExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Floor');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Floor \n Return type : [Rea, Num, Int] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PosExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Pos');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#pattern' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'pattern' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#string' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'string' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Pos \n Return type : [Rea, Num, Int] \n Allowed types :  pattern : [string] \n  string : [Str] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ParameterRefExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ParameterRef');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#name' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'name' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ParameterRef \n Return type : [any] \n Allowed types :  name : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SubtractExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Subtract');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Subtract \n Return type : [Num] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  secondOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#TimestampIntervalLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('TimestampIntervalLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#low_dateTime' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#dateTime' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'low_dateTime' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#lowClosed' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'lowClosed' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#high_dateTime' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#dateTime' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'high_dateTime' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#highClosed' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'highClosed' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#TimeIntervalType','http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : TimestampIntervalLiteral \n Return type : [Col, Tim, Int, Lis] \n Allowed types :  low_dateTime : [dateTime] \n  lowClosed : [boolean] \n  high_dateTime : [dateTime] \n  highClosed : [boolean] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ComplexLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ComplexLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value_string' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ComplexLiteral \n Return type : [any] \n Allowed types :  value_string : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NegateExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Negate');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Negate \n Return type : [Num] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AvgExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Avg');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#path_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'path_string' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Avg \n Return type : [Num] \n Allowed types :  path_string : [string] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SplitExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Split');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#separator' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'separator' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#stringToSplit' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'stringToSplit' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Split \n Return type : [Col, Lis] \n Allowed types :  separator : [Str] \n  stringToSplit : [Str] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#BeforeExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Before');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Before \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Tim] \n  secondOperand : [Int, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#InValueSetExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('InValueSet');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#version' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'version' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#id' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'id' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#authority' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'authority' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#CodeType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : InValueSet \n Return type : [Boo] \n Allowed types :  version : [string] \n  id : [string] \n  authority : [string] \n  firstOperand : [Cod] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#AnyTrueExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('AnyTrue');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#path_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'path_string' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : AnyTrue \n Return type : [Boo] \n Allowed types :  path_string : [string] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DateExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Date');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#year' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#int' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'year' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#second' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#int' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'second' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#month' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#int' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'month' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#minute' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#int' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'minute' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#millisecond' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#double' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'millisecond' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#hour' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#int' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hour' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#day' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#int' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'day' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#DateType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Date \n Return type : [Dat] \n Allowed types :  year : [int] \n  second : [int] \n  month : [int] \n  minute : [int] \n  millisecond : [double] \n  hour : [int] \n  day : [int] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ConditionalExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Conditional');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#condition' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'condition' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#else' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'else' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#then' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'then' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Conditional \n Return type : [any] \n Allowed types :  condition : [Boo] \n  else : [any] \n  then : [any] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ObjectDescriptorExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ObjectDescriptor');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#objectType' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainClass' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'objectType' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#property_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'property' );
    
        this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
        this.itemCount_ = 1;
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#ObjectType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ObjectDescriptor \n Return type : [Obj] \n Allowed types :  objectType : [Dom] \n  property : [Pro] \n  ' );
  }
  ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'ObjectDescriptor_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('http://asu.edu/sharpc2b/ops-set#property_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('http://asu.edu/sharpc2b/ops-set#property_' + x)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'property [Pro]' );
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
           this.removeInput('http://asu.edu/sharpc2b/ops-set#property_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('http://asu.edu/sharpc2b/ops-set#property_' + this.itemCount_)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#PropertyType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'property [Pro]' );

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
      var input = this.getInput('http://asu.edu/sharpc2b/ops-set#property_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#MinExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Min');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#path_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'path_string' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Min \n Return type : [Num] \n Allowed types :  path_string : [string] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#NotExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Not');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#BooleanType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Not \n Return type : [Boo] \n Allowed types :  firstOperand : [Boo] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SubstringExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Substring');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#length' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'length' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#startIndex' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'startIndex' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#stringToSub' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#StringType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'stringToSub' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Substring \n Return type : [Str] \n Allowed types :  length : [Int] \n  startIndex : [Int] \n  stringToSub : [Str] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DataRequestExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('DataRequest');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#triggerType' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'triggerType' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#templateId' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'templateId' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#scope' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'scope' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#isInitial' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#boolean' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'isInitial' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#cardinality' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'cardinality' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#dataType' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainClass' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'dataType' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#idProperty' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DomainProperty' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'idProperty' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#timeOffset' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'timeOffset' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : DataRequest \n Return type : [Col] \n Allowed types :  triggerType : [string] \n  templateId : [string] \n  scope : [string] \n  isInitial : [boolean] \n  cardinality : [string] \n  dataType : [Dom] \n  idProperty : [Dom] \n  timeOffset : [Int] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SimpleCodeLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('SimpleCodeLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#code' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'code' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CodeType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : SimpleCodeLiteral \n Return type : [Cod] \n Allowed types :  code : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#PowerExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Power');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Power \n Return type : [Num] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  secondOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ForEachExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ForEach');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#scope' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'scope' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#element' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'element' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#source_List' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'source_List' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ForEach \n Return type : [Col, Lis] \n Allowed types :  scope : [string] \n  element : [any] \n  source_List : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DatePartExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('DatePart');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#granularity' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'granularity' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#date' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DateType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'date' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#IntType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : DatePart \n Return type : [Rea, Num, Int] \n Allowed types :  granularity : [string] \n  date : [Dat] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#InExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('In');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#collection' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'collection' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#element' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'element' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : In \n Return type : [Boo] \n Allowed types :  collection : [Int, Lis, Tim] \n  element : [any] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DifferenceExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Difference');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#NumberType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Difference \n Return type : [Num] \n Allowed types :  firstOperand : [Int, Lis, Tim] \n  secondOperand : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#UnionExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Union');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#hasOperand_0' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand' );
    
        this.setMutator( new Blockly.Mutator( ['argument_group_item'] ) );
        this.itemCount_ = 1;
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#CollectionType','http://asu.edu/sharpc2b/ops#List'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Union \n Return type : [Col, Lis] \n Allowed types :  hasOperand : [Int, Lis, Tim] \n  ' );
  }
  ,
        

  mutationToDom: function(workspace) {
    var container = document.createElement( 'Union_mutation' );
    container.setAttribute( 'items', this.itemCount_ );
    return container;
  },

  domToMutation: function(container) {
    for (var x = 0; x < this.itemCount_; x++) {
      this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
    }
    this.itemCount_ = parseInt(container.getAttribute('items'), 10);
    for (var x = 0; x < this.itemCount_; x++) {
      var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [Int, Lis, Tim]' );
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
           this.removeInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
         }
       this.itemCount_ = 0;
       // Rebuild the block's inputs.
       var itemBlock = containerBlock.getInputTargetBlock('STACK');
       while (itemBlock) {
         var input = this.appendValueInput('http://asu.edu/sharpc2b/ops#hasOperand_' + this.itemCount_)
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'hasOperand [Int, Lis, Tim]' );

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
      var input = this.getInput('http://asu.edu/sharpc2b/ops#hasOperand_' + x);
      itemBlock.valueConnection_ = input && input.connection.targetConnection;
      x++;
      itemBlock = itemBlock.nextConnection &&
          itemBlock.nextConnection.targetBlock();
    }
  }


  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#SuccExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Succ');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DateType','http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType','http://asu.edu/sharpc2b/ops#StringType','http://asu.edu/sharpc2b/ops#TimestampType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Succ \n Return type : [any] \n Allowed types :  firstOperand : [Dat, Int, Num, Rat, Rea, Str, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#StringLiteralExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('StringLiteral');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#value_string' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'value_string' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#StringType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : StringLiteral \n Return type : [Str] \n Allowed types :  value_string : [string] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#GreaterExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Greater');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType','http://asu.edu/sharpc2b/ops#NumberType','http://asu.edu/sharpc2b/ops#RatioType','http://asu.edu/sharpc2b/ops#RealType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Greater \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Num, Rat, Rea] \n  secondOperand : [Int, Num, Rat, Rea] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#DateAddExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('DateAdd');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#granularity' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#string' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'granularity' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#date' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#DateType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'date' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#numberOfPeriods' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'numberOfPeriods' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#DateType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : DateAdd \n Return type : [Dat] \n Allowed types :  granularity : [string] \n  date : [Dat] \n  numberOfPeriods : [Int] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsEmptyExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('IsEmpty');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : IsEmpty \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ProperInExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('ProperIn');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#secondOperand' )
                         .setCheck( [ 'http://asu.edu/sharpc2b/ops#IntervalType','http://asu.edu/sharpc2b/ops#List','http://asu.edu/sharpc2b/ops#TimeIntervalType' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'secondOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : ProperIn \n Return type : [Boo] \n Allowed types :  firstOperand : [Int, Lis, Tim] \n  secondOperand : [Int, Lis, Tim] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#IsNullExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('IsNull');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, ['http://asu.edu/sharpc2b/ops#BooleanType'] );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : IsNull \n Return type : [Boo] \n Allowed types :  firstOperand : [any] \n  ' );
  }
  
};

Blockly.Blocks['http://asu.edu/sharpc2b/ops-set#ConvertExpression'] = {
  init: function() {
    this.setHelpUrl( 'https://code.google.com/p/health-e-decisions/source/browse/#svn' );

    this.setColour( 42 );

    this.appendDummyInput().appendField('Convert');

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops-set#toType' )
                         .setCheck( [ 'http://www.w3.org/2001/XMLSchema#anyURI' ] )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'toType' );
    

    
        
    this.appendValueInput( 'http://asu.edu/sharpc2b/ops#firstOperand' )
                         .setCheck( null )
                         .setAlign( Blockly.ALIGN_RIGHT )
                         .appendField( 'firstOperand' );
    

    

    this.setOutput( true, null );

    this.setInputsInline( false );

    this.setTooltip( ' HeD expression : Convert \n Return type : [any] \n Allowed types :  toType : [anyURI] \n  firstOperand : [any] \n  ' );
  }
  
};

Blockly.Blocks.argument_group_container = {
    init: function() {
        this.setColour(230);
        this.appendDummyInput().appendField('Add arguments');
        this.appendStatementInput('STACK');
        this.setTooltip('Add, or remove arguments');
        this.contextMenu = false;
    }
};

Blockly.Blocks.argument_group_item = {
    init: function() {
        this.setColour(230);
        this.appendDummyInput().appendField('Argument');
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
    this.appendDummyInput().appendField( '[string]' ).appendField( textInput, 'VALUE' );
    this.setOutput( true, 'xsd:string' );
  }
};

Blockly.Blocks['xsd:anyURI'] = {
  init: function() {
    this.setColour(152);
    var textInput = new Blockly.FieldTextInput('');
    this.appendDummyInput().appendField( '[URI]' ).appendField( textInput, 'VALUE' );
    this.setOutput( true, 'xsd:anyURI' );
  }
};

Blockly.Blocks['xsd:dateTime'] = {
  init: function() {
    this.setColour(152);
    var textInput = new Blockly.FieldTextInput('');
    this.appendDummyInput().appendField( '[dateTime]' ).appendField( textInput, 'VALUE' );
    this.setOutput( true, 'xsd:dateTimeStamp' );
  }
};

Blockly.Blocks['xsd:int'] = {
  init: function() {
    this.setColour(152);
    var numInput = new Blockly.FieldTextInput();
    this.appendDummyInput().appendField( '[int]' ).appendField( numInput, 'VALUE' );
    this.setOutput( true, 'xsd:int' );
  }
};

Blockly.Blocks['xsd:boolean'] = {
  init: function() {
    this.setColour(152);
    var boolInput = new Blockly.FieldCheckbox('TRUE');
    this.appendDummyInput().appendField( '[bool]' ).appendField( boolInput, 'VALUE' );
    this.setOutput( true, 'xsd:boolean' );
  }
};

Blockly.Blocks['xsd:double'] = {
  init: function() {
    this.setColour(152);
    var numInput = new Blockly.FieldTextInput();
    this.appendDummyInput().appendField( '[double]' ).appendField( numInput, 'VALUE' );
    this.setOutput( true, 'xsd:double' );
  }
};


        Blockly.Blocks['xsd:text'] = {
        	init : function() {
        		// Assign 'this' to a variable for use in the tooltip closure.
        	    var thisBlock = this;
        		this.setColour(152);
        		this.appendDummyInput().appendField( '[text]' ).appendField(new Blockly.FieldExternalInput("Click to edit the text", openExternalInput), "TEXT");
        		this.setOutput(true, 'xsd:string');
        		this.setTooltip(function() {
        			return thisBlock.getFieldValue('TEXT');
        		});
        	}
        };


