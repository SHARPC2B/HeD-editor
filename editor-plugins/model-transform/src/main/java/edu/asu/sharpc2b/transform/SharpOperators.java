package edu.asu.sharpc2b.transform;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: rk
 */
public class SharpOperators {

    public static enum CompilationTarget { SPREADSHEET, ONTOLOGY }

    static String typesBaseIRI = IriUtil.sharpEditorIRI( "ops" ).toString() + "#";

    static Map<String, IRI> typeNameIriMap = new HashMap<String, IRI>();

    static Map<String, String> typeNameMap = new HashMap<String, String>();

    static Map<String, IRI> typeExpressionNameMap = new HashMap<String, IRI>();

    private static Map<String, String> primitiveTypeMap = new HashMap<>();

    static
    {
        initTypeNameMap();
        initTypeNameIriMap();
        initTypeExpressionNameMap();
        initPrimitiveTypeMap();
    }

    static final int ARITY_NARY = Integer.MAX_VALUE;

    static final int ARITY_LIST = -1;

    static final int ARITY_NULLARY = 0;

    static final int ARITY_UNARY = 1;

    static final int ARITY_BINARY = 2;

    static final int ARITY_TERNARY = 3;

    //===============================================================================================

    private OWLOntologyManager oom;

    private OWLDataFactory odf;

//    private PrefixOWLOntologyFormat oFormat;

    private OWLOntology ont;

    private String outputOntBaseIRI;

    //===============================================================================================

    public SharpOperators () {
        super();
    }

    //===============================================================================================

    public void addSharpOperators( File operatorDefinitionFile, OWLOntology hedOntology, OWLOntology operatorOntology, String generationTarget ) {
        CompilationTarget target = generationTarget != null ? CompilationTarget.valueOf( generationTarget.toUpperCase() ) : CompilationTarget.ONTOLOGY;
        switch ( target ) {
            case SPREADSHEET:
                prepareSharpOperators( operatorDefinitionFile, hedOntology );
                break;
            case ONTOLOGY:
            default:
                addSharpOperators( operatorDefinitionFile, operatorOntology );
                break;
        }
    }





    /*******************************************************************************************************************************************/





    private void prepareSharpOperators( File operatorDefinitionFile, OWLOntology hedOntology ) {
        OWLDataFactory factory = hedOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass root = factory.getOWLClass( IRI.create( "urn:hl7-org:knowledgeartifact:r1#Expression" ) );

        List<ExprInfo> expressions = new ArrayList<ExprInfo>();
        prepareExpressionType( root, root, hedOntology, expressions  );

        try {
            FileInputStream fis = new FileInputStream( operatorDefinitionFile );
            Workbook workbook = WorkbookFactory.create( fis );
            Sheet blank = workbook.getSheet( "Operand_Blank" );
            if ( blank != null ) {
                workbook.removeSheetAt( workbook.getSheetIndex( blank ) );
            }
            blank = workbook.createSheet( "Operand_Blank" );

            fillOperators( blank, expressions );

            workbook.write( new FileOutputStream( operatorDefinitionFile ) );
        } catch ( Exception e ) {
            e.printStackTrace();
        }



    }

    private void fillOperators( Sheet blank, List<ExprInfo> expressions ) {
        int maxArgs = 0;
        for ( ExprInfo xp : expressions ) {
            maxArgs = Math.max( maxArgs, xp.operands.size() );
        }
        createHeader( blank, maxArgs );
        int j = 1;
        for ( ExprInfo xp : expressions ) {
            Row row = blank.createRow( j++ );

            row.createCell( 0 ).setCellValue( xp.opName );
            row.createCell( 1 ).setCellValue( xp.arity );
            int xtraCounter = 0;
            for ( OpInfo op : xp.operands ) {
                if ( ! "operand".equals( op.name ) ) {
                    row.createCell( 6 + ( 2 * xtraCounter ) ).setCellValue( op.name );
                    if ( ! "Expression".equals( op.type ) ) {
                        row.createCell( 7 + ( 2 * xtraCounter ) ).setCellValue( op.type );
                    }
                    xtraCounter++;
                }

            }
        }
    }

    private void createHeader( Sheet blank, int maxArgs ) {
        Row header = blank.createRow( 0 );

        Cell ops = header.createCell( 0 );
        ops.setCellValue( "operator" );
        Cell nargs = header.createCell( 1 );
        nargs.setCellValue( "nArgs" );
        Cell res = header.createCell( 2 );
        res.setCellValue( "resultType" );
        Cell op1 = header.createCell( 3 );
        op1.setCellValue( "operand1Type" );
        Cell op2 = header.createCell( 4 );
        op2.setCellValue( "operand2Type" );
        Cell op3 = header.createCell( 5 );
        op3.setCellValue( "operand3Type" );
        Cell multi = header.createCell( 6 );
        multi.setCellValue( "multiField" );
        for ( int j = 0; j < maxArgs; j++ ) {
            Cell xop = header.createCell( 8 + ( 2 * j ) );
            xop.setCellValue( "extraOpName" );
            Cell xopT = header.createCell( 9 + ( 2 * j ) );
            xopT.setCellValue( "extraOpType" );
        }
    }

    private void prepareExpressionType( OWLClass klass, OWLClass root, OWLOntology hedOntology, List<ExprInfo> expressions ) {
        boolean concrete = ! hasSubTypes( klass, hedOntology );
        if ( ! concrete ) {
            for ( OWLSubClassOfAxiom sub : hedOntology.getSubClassAxiomsForSuperClass( klass ) ) {
                if ( ! sub.getSubClass().isAnonymous() ) {
                    OWLClass subKlass = sub.getSubClass().asOWLClass();
                    prepareExpressionType( subKlass, root, hedOntology, expressions );
                }
            }
        } else {
            ExprInfo expr = new ExprInfo();
            expr.opName = klass.asOWLClass().getIRI().getFragment();
            if ( isLiteral( klass ) ) {
                expressions.add( 0, expr );
            } else {
                expressions.add( expr );
            }

            Set<OWLClassExpression> descriptors = gatherParentInfo( klass, root, hedOntology );

            for ( OWLClassExpression info : descriptors ) {
                extractOperandInfo( info, expr, hedOntology );
            }

        }
    }

    private void extractOperandInfo( OWLClassExpression info, ExprInfo expr, OWLOntology hedOntology ) {

        boolean isNary = false;
        if ( info instanceof OWLObjectIntersectionOf ) {
            OWLObjectIntersectionOf intersectionOf = (OWLObjectIntersectionOf) info;
            for ( OWLClassExpression atom : intersectionOf.asConjunctSet() ) {
                OpInfo op = new OpInfo();
                if ( atom instanceof OWLObjectAllValuesFrom ) {
                    OWLObjectAllValuesFrom obj = (OWLObjectAllValuesFrom) atom;
                    op.name = obj.getProperty().asOWLObjectProperty().getIRI().getFragment();

                    OWLClassExpression range;
                        range = obj.getFiller();
                        op.type = adaptType( range.asOWLClass().getIRI(), hedOntology.getOntologyID().getOntologyIRI() );
                    expr.operands.add( 0, op );

                    if ( op.name.equals( "operand" ) ) {
                        isNary = true;
                        for ( OWLClassExpression inner : intersectionOf.asConjunctSet() ) {
                            if (inner instanceof OWLObjectMinCardinality ) {
                                // assume there is only one "arity" axiom
                                expr.arity = ((OWLObjectMinCardinality) inner).getCardinality();
                            } else if ( inner instanceof OWLObjectMaxCardinality ) {
                                isNary = false;
                            }
                        }
                    }
                } else if ( atom instanceof OWLDataAllValuesFrom ) {
                    OWLDataAllValuesFrom data = (OWLDataAllValuesFrom) atom;
                    op.name = data.getProperty().asOWLDataProperty().getIRI().getFragment();

                    OWLDataRange range;
                    try {
                        range = data.getFiller();
                        op.type = adaptType( range.asOWLDatatype().getIRI(), hedOntology.getOntologyID().getOntologyIRI() );
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                    expr.operands.add( op );
                } else if ( atom instanceof OWLDataMaxCardinality || atom instanceof OWLObjectMaxCardinality ) {
                    isNary = false;
                }
            }
        }
        if ( isNary ) {
            expr.arity = Integer.MAX_VALUE;
        }
        if ( ! expr.operands.isEmpty() && expr.arity == 0 ) {
            expr.arity = -1;
        }
        if ( expr.opName.contains( "Literal" ) ) {
            expr.arity = -99;
        }
    }

    private String adaptType( IRI propType, IRI ontologyIRI ) {

        if ( "http://www.w3.org/2001/XMLSchema#".equals( propType.getNamespace() ) ) {
            return "xsd:" + propType.getFragment();
        } else if ( ( ontologyIRI.toString() + "#" ).equals( propType.getNamespace() ) ) {
            return propType.getFragment();
        } else {
                String fragment = propType.getFragment();
                String chosenType = primitiveTypeMap.get( fragment );
                if ( chosenType != null ) {
                    return chosenType;
                } else {
                    System.out.println( "TODO Map primitive type " + propType );
                    return "xsd:string";
                }

        }

    }


    private Set<OWLClassExpression> gatherParentInfo( OWLClass klass, OWLClass root, OWLOntology hedOntology ) {
        Set<OWLClassExpression> supers = new HashSet<OWLClassExpression>();
        for ( OWLSubClassOfAxiom info : hedOntology.getSubClassAxiomsForSubClass( klass ) ) {
            OWLClassExpression sup = info.getSuperClass();
            if ( sup.isAnonymous() ) {
                supers.add( sup );
            } else {
                if ( ! sup.asOWLClass().getIRI().equals( root.getIRI() ) ) {
                    supers.addAll( gatherParentInfo( sup.asOWLClass(), root, hedOntology ) );
                }
            }
        }
        return supers;
    }

    private boolean hasSubTypes( OWLClass klass, OWLOntology hedOntology ) {
        for ( OWLSubClassOfAxiom sub : hedOntology.getSubClassAxiomsForSuperClass( klass ) ) {
            if ( ! sub.getSubClass().isAnonymous() ) {
                return true;
            }
        }
        return false;
    }

    private boolean isLiteral( OWLClassExpression owlClass ) {
        if ( ! owlClass.isAnonymous() ) {
            return owlClass.asOWLClass().getIRI().toString().endsWith( "LiteralExpression" );
        }
        return false;
    }

    private static class ExprInfo {
        public int arity = 0;
        public String opName;
        public List<OpInfo> operands = new ArrayList<OpInfo>();

        public String toString() {
            String s = opName + "\t" + arity + "\t";
            for ( OpInfo op : operands ) {
                s += op.name + "\t" + op.type + "\t";
            }
            s += "\n";
            return s;
        }
    }

    private static class OpInfo {
        public String name = "operand";
        public String type;
    }

















    /*******************************************************************************************************************************************/


    private Map<String,PropertyInfo> extraProperties = new HashMap<String, PropertyInfo>(  );
    private int extensionColumn = 8;

    private static class PropertyInfo {
        public Set<IRI> domains = new HashSet<IRI>();
        public IRI name;
        public IRI range;
        public Boolean object;

        public String toString() {
            String s = name.getFragment() + " {";
            for ( IRI d : domains ) {
                s += d.getFragment() + " ";
            }
            s += "} --> " + range.getFragment();
            return s;
        }
    }




    public void addSharpOperators (final File excelFile,
                                   final OWLOntology ontology) {
        this.ont = ontology;
        setupOntologyManager( ont );
        addImports();
        this.outputOntBaseIRI = ont.getOntologyID().getOntologyIRI().toString() + "#";

        addMissingCommonAxioms();

        Sheet sheet = getExcelOperatorsSheet( excelFile );

        int lastRowNum = sheet.getLastRowNum();

        /*
         * Stores the operator name from the previous row of the spreadsheet. Needed to determine if same
          * operator name is used in multiple rows, in which case it needs a unique name to be created
          * for it.
          */
        String lastOperatorName = "NONE";

        /* skip the first row -- contains column names */



        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
            discoverExtraProperties( sheet, sheet.getRow( rowNum ), ontology.getOntologyID().getOntologyIRI() );
        }

        declareExtraProperties( extraProperties, ontology );

        for (int rowNum = 1; rowNum <= lastRowNum; rowNum++)
        {
            Row row = sheet.getRow( rowNum );
            int colOperatorName = 0;

            String operatorName = row.getCell( colOperatorName ).getStringCellValue();

            String nextOperatorName;
            if (rowNum < lastRowNum)
            {
                nextOperatorName = sheet.getRow( rowNum + 1 ).getCell( colOperatorName )
                        .getStringCellValue();
            }
            else
            {
                nextOperatorName = "NONE";
            }

            boolean overloadedName = operatorName.equals( lastOperatorName ) ||
                                     operatorName.equals( nextOperatorName );

            processOneRow( row, overloadedName );

            /* setup for next iteration */
            lastOperatorName = operatorName;
        }
    }

    private void declareExtraProperties( Map<String, PropertyInfo> extraProperties, OWLOntology ontology ) {
        OWLDataFactory factory = ontology.getOWLOntologyManager().getOWLDataFactory();
        for ( PropertyInfo info : extraProperties.values() ) {
            OWLProperty property;
            if ( info.object ) {
                property = factory.getOWLObjectProperty( info.name );
            } else {
                property = factory.getOWLDataProperty( info.name );
            }

            OWLClassExpression domain;
            if ( info.domains.size() == 1 ) {
                domain = factory.getOWLClass( info.domains.iterator().next() );
            } else {
                // need to do this explicitly here, because the reasoner may not try to process this due to efficiency constraints
                Set<OWLClassExpression> doms = new HashSet<OWLClassExpression>();
                for ( IRI d : info.domains ) {
                    doms.add( factory.getOWLClass( d ) );
                }

                domain = factory.getOWLClass( outputIRI( info.name.getFragment().substring( 0, 1 ).toUpperCase() + info.name.getFragment().substring( 1 ) + "Domain" ) );
                addAxiom( factory.getOWLEquivalentClassesAxiom( domain, factory.getOWLObjectUnionOf( doms ) ) );
                for ( OWLClassExpression klass : doms ) {
                    addAxiom( factory.getOWLSubClassOfAxiom( klass, domain ) );
                }
            }

            OWLClassExpression range = factory.getOWLClass( info.range );


            addAxiom( factory.getOWLDeclarationAxiom( property ) );
            if ( info.object ) {
                addAxiom( factory.getOWLObjectPropertyDomainAxiom( (OWLObjectPropertyExpression) property, domain ) );
                addAxiom( factory.getOWLObjectPropertyRangeAxiom( (OWLObjectPropertyExpression) property, range ) );
            } else {
                addAxiom( factory.getOWLDataPropertyDomainAxiom( (OWLDataProperty) property, domain ) );
                IRI rangeIri = info.range;
                if ( rangeIri.toString().startsWith( "xsd:" ) ) {
                    // hack: using the short form
                    rangeIri = IRI.create( "http://www.w3.org/2001/XMLSchema#" + rangeIri.getFragment() );
                }
                addAxiom( factory.getOWLDataPropertyRangeAxiom( (OWLDataProperty) property, factory.getOWLDatatype( rangeIri) ) );
            }
        }
    }

    private void discoverExtraProperties( Sheet sheet, Row row, IRI ontoIri ) {
        String operationName = row.getCell( 0 ).getStringCellValue() + "Expression";
        for ( int j = extensionColumn; j < 50; j = j + 2 ) {
            if ( row.getCell( j ) == null ) {
                return;
            }
            String propName = row.getCell( j ).getStringCellValue();
            String propType = row.getCell( j + 1 ).getStringCellValue();
            if ( propName != null ) {
                PropertyInfo info = extraProperties.get( propName );
                if ( info == null ) {
                    info = new PropertyInfo();
                }

                IRI ranIri;
                boolean isObject;
                if ( propType.startsWith( "xsd:" ) ) {
                    ranIri = IRI.create( "http://www.w3.org/2001/XMLSchema#" + propType.substring( 4 ) );
                    isObject = false;
                } else {
                    boolean found = false;
                    if ( propType.contains( "Literal" ) ) {
                        for ( int k = 0; k < sheet.getLastRowNum(); k++ ) {
                            if ( propType.equals(  sheet.getRow( k ).getCell( 0 ).getStringCellValue() ) ) {
                                found = true;
                                break;
                            }
                        }
                    }
                    ranIri = found ? outputIRI( propType + "Expression" ) : IriUtil.opsIRI( propType + "Expression" );
                    isObject = true;
                }


                boolean needsOverride = ( "source".equals( propName )           //HACK to resolve a conflict
                                          || info.object != null && info.object.booleanValue() != isObject )
                                          || ( info.range != null && ! info.range.equals( ranIri ) );
                if ( needsOverride ) {
                    // the property already exists, but with a different type. We need to move up
                    propName = propName + "_" + ranIri.getFragment().replace( "Expression", "" );
                    if ( ! extraProperties.containsKey( propName ) ) {
                        info = new PropertyInfo();
                    } else {
                        info = extraProperties.get( propName );
                    }
                }

                info.object = isObject;
                info.range = ranIri;
                info.name = outputIRI( propName );
                info.domains.add( outputIRI( operationName ) );

                if ( ! extraProperties.containsKey( propName ) ) {
                    extraProperties.put( propName, info );
                }
            }
        }
    }

    private void processOneRow (final Row row,
                                final boolean isOverloadedName)
    {
        /* set up column indexes for sheet columns we care about */
        int i = 0;
        int colOperatorName = i++;
        int colNumArgs = i++;
        int colResultType = i++;
        int colOperand1Type = i++;
        int colOperand2Type = i++;
        int colOperand3Type = i++;

        String opNameFromConfig = row.getCell( colOperatorName ).getStringCellValue();
        //TODO move int to ENUM Arity
        int arity = (int) row.getCell( colNumArgs ).getNumericCellValue();
        String resultTypeName = row.getCell( colResultType ).getStringCellValue();


            /* only needed for arity = 2 or 3 */

        String op1TypeName = 1 <= arity ? row.getCell( colOperand1Type ).getStringCellValue() : null;
        String op2TypeName = 2 <= arity && arity < Integer.MAX_VALUE ? row.getCell( colOperand2Type ).getStringCellValue() : null;
        String op3TypeName = 3 <= arity && arity < Integer.MAX_VALUE ? row.getCell( colOperand3Type ).getStringCellValue() : null;


            /* At this point, have all the values from the spreadsheet row. */

            /*
             * If this operator name is used in more than one row,
             * need to create a compound name, such as "PlusInteger" instead of just "Plus".
             */

//            boolean isOverloadedName = opNameFromConfig .equals(  lastOperatorName )||
//                    opNameFromConfig .equals(  nextOperatorName);
        final String opName;
        opName = isOverloadedName ? (opNameFromConfig + typeNameMap.get( op1TypeName )) : opNameFromConfig;

        if ( opName.contains( "Literal" ) ) {
            defineLiteralIndividual( opName, resultTypeName );
        } else {
            defineOperatorIndividual( opName, opNameFromConfig, resultTypeName, arity, op1TypeName, op2TypeName,
                                      op3TypeName );
        }


        defineOperatorExpressionClass( opName, opNameFromConfig, resultTypeName, arity,
                                       op1TypeName, op2TypeName, op3TypeName, row );
    }

    private void defineLiteralIndividual( String opName, String resultTypeName ) {
        OWLNamedIndividual literal = odf.getOWLNamedIndividual( outputIRI( opName ) );
    }

    /**
     * Create Individual to define the Operator.
     */
    private void defineOperatorIndividual (final String opName,
                                           final String opNameFromConfig,
                                           final String resultTypeName,
                                           final int arity,
                                           final String op1TypeName,
                                           final String op2TypeName,
                                           final String op3TypeName)
    {
    /* The OWL Individuals for the new Operator, and for the operand types and return type. */

        OWLNamedIndividual operator = odf.getOWLNamedIndividual( outputIRI( opName ) );
        OWLNamedIndividual operatorRepresentation = odf.getOWLNamedIndividual( outputIRI( opName + "Code" ) );
        OWLNamedIndividual resultType = getOpsTypeIndividual( resultTypeName );
        OWLNamedIndividual operand1Type = 1 <= arity ? getOpsTypeIndividual( op1TypeName ) : null;
        OWLNamedIndividual operand2Type = 2 <= arity ? getOpsTypeIndividual( op2TypeName ) : null;
        OWLNamedIndividual operand3Type = 3 <= arity ? getOpsTypeIndividual( op3TypeName ) : null;

        addAxiom( odf.getOWLDeclarationAxiom( operator ) );
        addAxiom( odf.getOWLDeclarationAxiom( operatorRepresentation ) );

            /* assert rdf:type */
        addOperatorType( operator, arity );
        addAxiom( odf.getOWLClassAssertionAxiom( odf.getOWLClass( IriUtil.opsIRI( "OperatorConceptCode" ) ), operatorRepresentation ) );

            /* assert skos:notation */
        OWLDataProperty skosNotation = odf
                .getOWLDataProperty( IRI.create( "http://www.w3.org/2004/02/skos/core#notation" ) );
        OWLObjectProperty denotedBy = odf
                .getOWLObjectProperty( IRI.create( "http://asu.edu/sharpc2b/skos-ext#conceptDenotedBy" ) );
        OWLObjectProperty denotes = odf
                .getOWLObjectProperty( IRI.create( "http://asu.edu/sharpc2b/skos-ext#denotesConcept" ) );

        addAxiom( odf.getOWLDataPropertyAssertionAxiom( skosNotation, operatorRepresentation, opNameFromConfig ) );

            /* assert ops:code */
        OWLDataProperty conceptCode = odf.getOWLDataProperty( IriUtil.skosExtIRI( "code" ) );

        addAxiom( odf.getOWLObjectPropertyAssertionAxiom( denotedBy, operator, operatorRepresentation ) );
        addAxiom( odf.getOWLObjectPropertyAssertionAxiom( denotes, operatorRepresentation, operator ) );
        addAxiom( odf.getOWLDataPropertyAssertionAxiom( conceptCode, operatorRepresentation, opNameFromConfig ) );

            /* assert operator return Type */
        OWLObjectProperty resultTypeRelationship = odf
                .getOWLObjectProperty(  IriUtil.opsIRI( "evaluatesAs" ) );
            addAxiom( odf.getOWLObjectPropertyAssertionAxiom( resultTypeRelationship, operator, resultType ) );

        assertOperandTypes( arity, operator, operand1Type, operand2Type, operand3Type );

    }

    /**
     * Create Expression Class
     */
    private void defineOperatorExpressionClass( final String opName,
                                                final String opNameFromConfig,
                                                final String resultTypeName,
                                                final int arity,
                                                final String op1TypeName,
                                                final String op2TypeName,
                                                final String op3TypeName,
                                                final Row row )
    {
//        final String opNameFromConfig = ; final boolean nary = ; final boolean listArg = ;
//
//        final boolean hasArity = ; final OWLDataProperty skosNotation = ;

        //            OWLClass exprClass = odf.getOWLClass( outputIRI( opName ) );
        OWLClass exprClass = odf.getOWLClass( outputIRI( opName + "Expression" ) );
        OWLClass superClass = getExpressionTypeClass( resultTypeName );

            /* point to superclass */
        addAxiom( odf.getOWLSubClassOfAxiom( exprClass, superClass ) );

            /*
             * Define the Expression OWLClass that defines the proper combination of operator and operand
             * types.
             */
        {
                /* Collect the conditions to AND together into a Collection. */

            final Set<OWLClassExpression> requirements = new HashSet<OWLClassExpression>();
            final Set<OWLClassExpression> closures = new HashSet<OWLClassExpression>();
            final Set<OWLClassExpression> disjoints = new HashSet<OWLClassExpression>();

            disjoints.add( odf.getOWLClass( IriUtil.skosIRI( "Collection" ) ) );
            disjoints.add( odf.getOWLClass( IriUtil.skosIRI( "ConceptScheme" ) ) );
            disjoints.add( odf.getOWLClass( IriUtil.skosIRI( "Concept" ) ) );
            disjoints.add( odf.getOWLClass( IriUtil.opsIRI( "Variable" ) ) );
            disjoints.add( exprClass );

            addAxiom( odf.getOWLDisjointClassesAxiom( disjoints ) );

            OWLObjectProperty hasOperator = odf.getOWLObjectProperty( IriUtil.opsIRI( "hasOperator" ) );
            OWLObjectProperty hasOperand = odf.getOWLObjectProperty( IriUtil.opsIRI( "hasOperand" ) );
            OWLObjectProperty hasOperand1 = odf.getOWLObjectProperty( IriUtil.opsIRI( "firstOperand" ) );
            OWLObjectProperty hasOperand2 = odf.getOWLObjectProperty( IriUtil.opsIRI( "secondOperand" ) );
            OWLObjectProperty hasOperand3 = odf.getOWLObjectProperty( IriUtil.opsIRI( "thirdOperand" ) );

            OWLClass masterParent;
            if ( isLiteral( exprClass ) ) {
                masterParent = odf.getOWLClass( IriUtil.opsIRI( "PrimitiveExpression" ) );
            } else {
                masterParent = odf.getOWLClass( IriUtil.opsIRI( "OperatorExpression" ) );
            }

            addAxiom( odf.getOWLSubClassOfAxiom( exprClass, masterParent ) );

                /* it is an Expression */
            requirements.add( masterParent );
//            requirements.add( odf.getOWLObjectHasValue( hasOperator, operator ) );

            /*  even "literals" are treated in the same way
            if ( ! isLiteral( exprClass ) ) {
            */
                OWLDataProperty skosNotation = odf
                        .getOWLDataProperty( IRI.create( "http://www.w3.org/2004/02/skos/core#notation" ) );
                OWLObjectProperty denotedBy = odf
                        .getOWLObjectProperty( IRI.create( "http://asu.edu/sharpc2b/skos-ext#conceptDenotedBy" ) );
                OWLObjectProperty opCode = odf
                        .getOWLObjectProperty(  IriUtil.opsIRI( "opCode" ) );

                OWLObjectIntersectionOf operatorCodeRestr = odf.getOWLObjectIntersectionOf( odf.getOWLClass( IriUtil.opsIRI( "OperatorConceptCode" ) ),
                                                                                            odf.getOWLDataHasValue( skosNotation,
                                                                                                                    odf.getOWLLiteral( opName ) ) );

                requirements.add( odf.getOWLObjectSomeValuesFrom( opCode, operatorCodeRestr ) );

                closures.add( odf.getOWLObjectAllValuesFrom( opCode, operatorCodeRestr ) );
  /*
            } else {
                OWLDataProperty skosNotation = odf
                        .getOWLDataProperty( IRI.create( "http://www.w3.org/2004/02/skos/core#notation" ) );
                OWLObjectProperty opCode = odf
                        .getOWLObjectProperty( IriUtil.opsIRI( "opCode" ) );

                OWLObjectIntersectionOf operatorCodeRestr = odf.getOWLObjectIntersectionOf( odf.getOWLClass( IriUtil.opsIRI( "OperatorConceptCode" ) ),
                                                                                            odf.getOWLDataHasValue( skosNotation,
                                                                                                                    odf.getOWLLiteral( opName ) ) );
                requirements.add( odf.getOWLObjectSomeValuesFrom( opCode, operatorCodeRestr ) );
                closures.add( odf.getOWLObjectAllValuesFrom( opCode, operatorCodeRestr ) );
            }
  */
                /* AND if n-Ary, all operands are of a particular type, and at least one. */

            boolean nary = arity == ARITY_NARY;



            if (nary) {
                closures.add( odf.getOWLObjectAllValuesFrom( hasOperand,
                                                                 getExpressionTypeClass( op1TypeName ) ) );
                requirements.add( odf.getOWLObjectSomeValuesFrom( hasOperand,
                                                                  getExpressionTypeClass( op1TypeName ) ) );
            } else // --->
                /* AND if aggregate type, only a single operand of type = ListExpression */
                /* AND if Arity type, add requirement for first operand */
                if (1 <= arity && arity < Integer.MAX_VALUE ) {
                    requirements.add( odf.getOWLObjectSomeValuesFrom( hasOperand1,
                                                                      getExpressionTypeClass( op1TypeName ) ) );
                    closures.add( odf.getOWLObjectAllValuesFrom( hasOperand1,
                                                                 getExpressionTypeClass( op1TypeName ) ) );
                }
                /* AND if Binary or Ternary, add requirement for second operand */
                if (2 <= arity && arity < Integer.MAX_VALUE) {
                    requirements.add( odf.getOWLObjectSomeValuesFrom( hasOperand2,
                                                                      getExpressionTypeClass( op2TypeName ) ) );
                    closures.add( odf.getOWLObjectAllValuesFrom( hasOperand2,
                                                                 getExpressionTypeClass( op2TypeName ) ) );

                }
                /* AND if Ternary, add requirement for third operand */
                if (3 <= arity && arity < Integer.MAX_VALUE) {
                    requirements.add( odf.getOWLObjectSomeValuesFrom( hasOperand3,
                                                                      getExpressionTypeClass( op3TypeName ) ) );
                    closures.add( odf.getOWLObjectAllValuesFrom( hasOperand3,
                                                                 getExpressionTypeClass( op3TypeName ) ) );

                }

            switch ( arity ) {
                case -1:
                    closures.add( odf.getOWLClass( IriUtil.opsIRI( "MiscExpression" ) ) );
                    break;
                case 0:
                    closures.add( odf.getOWLClass( IriUtil.opsIRI( "NullaryExpression" ) ) );
                    break;
                case 1:
                    closures.add( odf.getOWLClass( IriUtil.opsIRI( "UnaryExpression" ) ) );
                    break;
                case 2:
                    closures.add( odf.getOWLClass( IriUtil.opsIRI( "BinaryExpression" ) ) );
                    break;
                case 3:
                    closures.add( odf.getOWLClass( IriUtil.opsIRI( "TernaryExpression" ) ) );
                    break;
                case Integer.MAX_VALUE :
                    closures.add( odf.getOWLClass( IriUtil.opsIRI( "NAryExpression" ) ) );
                default:
                    break;
            }



            // now process extra properties
            for ( PropertyInfo info : extraProperties.values() ) {
                if ( info.domains.contains( exprClass.getIRI() ) ) {
                    if ( info.object ) {
                        requirements.add( odf.getOWLObjectSomeValuesFrom( odf.getOWLObjectProperty( info.name ),
                                                                          odf.getOWLClass( info.range ) ) );
                        String multiField = row.getCell( 6 ) != null ? row.getCell( 6 ).getStringCellValue() : null;
                        boolean multiple = info.name.getFragment().equals( multiField );
                        if ( ! multiple ) {
                            closures.add( odf.getOWLObjectMaxCardinality( 1,
                                                                              odf.getOWLObjectProperty( info.name ),
                                                                              odf.getOWLClass( info.range ) ) );
                        } else {
                            //System.out.println( "Found extra multiple " + info.name + " i n " + exprClass.asOWLClass().getIRI() );
                        }
                        closures.add( odf.getOWLObjectAllValuesFrom( odf.getOWLObjectProperty( info.name ),
                                                                     odf.getOWLClass( info.range ) ) );
                    } else {
                        requirements.add( odf.getOWLDataSomeValuesFrom( odf.getOWLDataProperty( info.name ),
                                                                        odf.getOWLDatatype( info.range ) ) );
                        closures.add( odf.getOWLDataMaxCardinality( 1,
                                                                        odf.getOWLDataProperty( info.name ),
                                                                        odf.getOWLDatatype( info.range ) ) );
                        closures.add( odf.getOWLDataAllValuesFrom( odf.getOWLDataProperty( info.name ),
                                                                   odf.getOWLDatatype( info.range ) ) );
                    }
                }
            }


            if ( ! requirements.isEmpty() ) {
                if ( requirements.size() > 1 ) {
                    OWLObjectIntersectionOf andExpression = odf.getOWLObjectIntersectionOf( requirements );
                    addAxiom( odf.getOWLEquivalentClassesAxiom( exprClass, andExpression ) );
                } else {
                    addAxiom( odf.getOWLSubClassOfAxiom( exprClass, requirements.iterator().next() ) );
                }
            }

            for ( OWLClassExpression closure : closures ) {
                addAxiom( odf.getOWLSubClassOfAxiom( exprClass, closure ) );
            }
//            addAxiom( odf.getOWLDeclarationAxiom( exprClass ) );
        }
    }

    private void assertExpressionAxioms ()
    {

    }

    private void addMissingCommonAxioms ()
    {

    }

    private void assertOperandTypes (final int arity,
                                     final OWLNamedIndividual operator,
                                     final OWLNamedIndividual operand1Type,
                                     final OWLNamedIndividual operand2Type,
                                     final OWLNamedIndividual operand3Type)
    {
        OWLObjectProperty operandTypeRelationship = odf
                .getOWLObjectProperty(  IriUtil.opsIRI( "hasOperandType" ) );
        OWLObjectProperty firstOperandTypeRelationship = odf
                .getOWLObjectProperty(  IriUtil.opsIRI( "firstOperandType" ) );
        OWLObjectProperty secondOperandTypeRelationship = odf
                .getOWLObjectProperty(  IriUtil.opsIRI( "secondOperandType" ) );
        OWLObjectProperty thirdOperandTypeRelationship = odf
                .getOWLObjectProperty(  IriUtil.opsIRI( "thirdOperandType" ) );

            /* first operand type */
        // n-ary ops use Integer.MAXINT
        OWLObjectProperty opRelationship = arity < 10000
                                           ? firstOperandTypeRelationship
                                           : operandTypeRelationship;

        if ( 1 <= arity ) {
            addAxiom( odf.getOWLObjectPropertyAssertionAxiom( opRelationship,
                                                              operator,
                                                              operand1Type ) );
        }

            /* second operand type */
        if ( 2 <= arity && arity < Integer.MAX_VALUE ) {
            addAxiom( odf.getOWLObjectPropertyAssertionAxiom( secondOperandTypeRelationship, operator,
                                                              operand2Type ) );
        }

            /* third operand type */
        if ( 3 <= arity && arity < Integer.MAX_VALUE ) {
            addAxiom( odf.getOWLObjectPropertyAssertionAxiom( thirdOperandTypeRelationship, operator,
                                                              operand3Type ) );
        }

    }

    private void addOperatorType (final OWLNamedIndividual operator,
                                  int arity)
    {
        OWLClass unaryOperator = odf.getOWLClass(  IriUtil.opsIRI( "UnaryOperatorConcept" ) );
        OWLClass binaryOperator = odf.getOWLClass(  IriUtil.opsIRI( "BinaryOperatorConcept" ) );
        OWLClass ternaryOperator = odf.getOWLClass(  IriUtil.opsIRI( "TernaryOperatorConcept" ) );
        OWLClass naryOperator = odf.getOWLClass(  IriUtil.opsIRI( "NAryOperatorConcept" ) );
        OWLClass aggregateOperator = odf.getOWLClass(  IriUtil.opsIRI( "AggregateOperatorConcept" ) );

        if (arity == -1)
        {
            addAxiom( odf.getOWLClassAssertionAxiom( naryOperator, operator ) );
        }
        else if (arity == 0)
        {
            addAxiom( odf.getOWLClassAssertionAxiom( aggregateOperator, operator ) );
        }
        else if (arity == 1)
        {
            addAxiom( odf.getOWLClassAssertionAxiom( unaryOperator, operator ) );
        }
        else if (arity == 2)
        {
            addAxiom( odf.getOWLClassAssertionAxiom( binaryOperator, operator ) );
        }
        else if (arity == 3)
        {
            addAxiom( odf.getOWLClassAssertionAxiom( ternaryOperator, operator ) );
        }
    }

    //===============================================================================================

    IRI outputIRI (final String name)
    {
        return IRI.create( outputOntBaseIRI + name );
    }

    void addAxiom (final OWLAxiom axiom)
    {
        oom.addAxiom( ont, axiom );
//        newAxioms.add( axiom );
    }

    void addImports ()
    {
        OWLImportsDeclaration importOps = odf.getOWLImportsDeclaration( IriUtil.sharpEditorIRI( "ops" ) );

        System.out.println( "add owl:imports: " + importOps );

        oom.applyChange( new AddImport( ont, importOps ) );
    }

    private void setupOntologyManager (final OWLOntology ont)
    {
        oom = ont.getOWLOntologyManager();
        odf = oom.getOWLDataFactory();
//        {
//            OWLOntologyFormat ontFormat = oom.getOntologyFormat( ont );
//            if (ontFormat instanceof PrefixOWLOntologyFormat)
//            {
//                this.oFormat = (PrefixOWLOntologyFormat) ontFormat;
//            }
//            else
//            {
//                throw new RuntimeException(
//                        "Current version of this method only works with Ontologies whose " +
//                                "OWLOntologyFormat is a PrefixOWLOntologyFormat.  The Ontology Format" +
//                                "of this Ontology is: " + ontFormat.getClass().getName() );
//            }
//        }
    }

    private Sheet getExcelOperatorsSheet (File excelFile)
    {
        FileInputStream fis = null;
        Workbook workbook;
        try
        {
            System.out.println( "Opening workbook [" + excelFile.getName() + "]" );

            fis = new FileInputStream( excelFile );

            // Open the workbook and then create the FormulaEvaluator and
            // DataFormatter instances that will be needed to, respectively,
            // force evaluation of forumlae found in cells and create a
            // formatted String encapsulating the cells contents.
            workbook = WorkbookFactory.create( fis );
//            this.evaluator = this.workbook.getCreationHelper().createFormulaEvaluator();
//            this.formatter = new DataFormatter(true);
        }
        catch (InvalidFormatException e)
        {
            e.printStackTrace();
            String msg = "Unable to find, load, or read Excel file or find correct Sheet." +
                         " Base Exception message = " + e.getMessage();
            throw new RuntimeException( msg, e );
        }
        catch (IOException e)
        {
            e.printStackTrace();
            String msg = "Unable to find, load, or read Excel file or find correct Sheet." +
                         " Base Exception message = " + e.getMessage();
            throw new RuntimeException( msg, e );
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e)
                {
                    //
                }
            }
        }

        Sheet sheet = workbook.getSheet( "Operators" );
        return sheet;
    }

    /**
     * The OWL Individual for the type, such as realType, stringType, ListType, etc.
     *
     * @param typeNameInSpreadsheet the name in the spreadsheet, e.g., "Real", "T", "Object<T>"
     */
    OWLNamedIndividual getOpsTypeIndividual (final String typeNameInSpreadsheet)
    {
        IRI typeIRI = typeNameIriMap.get( typeNameInSpreadsheet );
        OWLNamedIndividual typeInd = typeIRI == null ? null : odf.getOWLNamedIndividual( typeIRI );
        return typeInd;
    }

    /**
     * The OWL Class for the Expression value type, such as RealExpression, StringExpression,
     * ListExpression, etc.
     *
     * @param typeNameInSpreadsheet the name in the spreadsheet, e.g., "Real", "T", "Object<T>"
     */
    OWLClass getExpressionTypeClass (final String typeNameInSpreadsheet)
    {

        IRI typeIRI = typeExpressionNameMap.get( typeNameInSpreadsheet );
        OWLClass type = typeIRI == null
                        ? null
                        : odf.getOWLClass( IRI.create( typeIRI.toString() + "Expression" ) );
        return type;
    }

    static void initTypeNameIriMap ()
    {
        typeNameIriMap.put( "Any", IRI.create( typesBaseIRI + "anyType" ) );
        typeNameIriMap.put( "T", IRI.create( typesBaseIRI + "anyType" ) );
        typeNameIriMap.put( "C", IRI.create( typesBaseIRI + "anyType" ) );
        typeNameIriMap.put( "Object", IRI.create( typesBaseIRI + "objectType" ) );
        typeNameIriMap.put( "Object<T>", IRI.create( typesBaseIRI + "objectType" ) );
        typeNameIriMap.put( "Number", IRI.create( typesBaseIRI + "numberType" ) );
        typeNameIriMap.put( "Real", IRI.create( typesBaseIRI + "realType" ) );
        typeNameIriMap.put( "Boolean", IRI.create( typesBaseIRI + "booleanType" ) );
        typeNameIriMap.put( "String", IRI.create( typesBaseIRI + "stringType" ) );
        typeNameIriMap.put( "Time/Duration", IRI.create( typesBaseIRI + "timeDurationType" ) );
        typeNameIriMap.put( "Timestamp", IRI.create( typesBaseIRI + "timestampType" ) );
        typeNameIriMap.put( "DateGranularity", IRI.create( typesBaseIRI + "dateGranularityType" ) );
        typeNameIriMap.put( "Int", IRI.create( typesBaseIRI + "intType" ) );
        typeNameIriMap.put( "Interval<T>", IRI.create( typesBaseIRI + "intervalType" ) );
        typeNameIriMap.put( "Collection<T>", IRI.create( typesBaseIRI + "collectionType" ) );
        typeNameIriMap.put( "List", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "List<T>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "List<S>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "List<String>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "List<Boolean>", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "Ordered", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "Ordered Type", IRI.create( typesBaseIRI + "listType" ) );
        typeNameIriMap.put( "Scalar", IRI.create( typesBaseIRI + "scalarType" ) );
        typeNameIriMap.put( "Expression<T:S>", IRI.create( typesBaseIRI + "anyType" ) );
        typeNameIriMap.put( "Date", IRI.create( typesBaseIRI + "dateType" ) );
        typeNameIriMap.put( "Interval", IRI.create( typesBaseIRI + "intervalType" ) );
        typeNameIriMap.put( "Ordinal", IRI.create( typesBaseIRI + "ordinalType" ) );
        typeNameIriMap.put( "Code", IRI.create( typesBaseIRI + "codeType" ) );
        typeNameIriMap.put( "ClinicalRequest", IRI.create( typesBaseIRI + "listType" ) );
    }

    // This table maps the content of the "type" cell in the spreadsheet
    // to the "Expression" class in the expr-core ontology.
    // TODO it should not be necessary
    static void initTypeExpressionNameMap ()
    {
        typeExpressionNameMap.put( "Any", IRI.create( typesBaseIRI + "Operator" ) );
        typeExpressionNameMap.put( "T", IRI.create( typesBaseIRI + "Operator" ) );
        typeExpressionNameMap.put( "C", IRI.create( typesBaseIRI + "Operator" ) );
        typeExpressionNameMap.put( "Object", IRI.create( typesBaseIRI + "Object" ) );
        typeExpressionNameMap.put( "Object<T>", IRI.create( typesBaseIRI + "Object" ) );
        typeExpressionNameMap.put( "Number", IRI.create( typesBaseIRI + "Number" ) );
        typeExpressionNameMap.put( "Real", IRI.create( typesBaseIRI + "Real" ) );
        typeExpressionNameMap.put( "Boolean", IRI.create( typesBaseIRI + "Boolean" ) );
        typeExpressionNameMap.put( "String", IRI.create( typesBaseIRI + "String" ) );
        typeExpressionNameMap.put( "Time/Duration", IRI.create( typesBaseIRI + "TimeDuration" ) );
        typeExpressionNameMap.put( "Timestamp", IRI.create( typesBaseIRI + "Timestamp" ) );
        typeExpressionNameMap.put( "DateGranularity", IRI.create( typesBaseIRI + "DateGranularity" ) );
        typeExpressionNameMap.put( "Int", IRI.create( typesBaseIRI + "Int" ) );
        typeExpressionNameMap.put( "Interval<T>", IRI.create( typesBaseIRI + "Interval" ) );
        typeExpressionNameMap.put( "Interval", IRI.create( typesBaseIRI + "Interval" ) );
        typeExpressionNameMap.put( "Collection<T>", IRI.create( typesBaseIRI + "Collection" ) );
        typeExpressionNameMap.put( "List", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "List<T>", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "List<S>", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "List<String>", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "List<Boolean>", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "Ordered", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "Ordered Type", IRI.create( typesBaseIRI + "List" ) );
        typeExpressionNameMap.put( "Scalar", IRI.create( typesBaseIRI + "Scalar" ) );
        typeExpressionNameMap.put( "Expression<T:S>", IRI.create( typesBaseIRI + "Operator" ) );
        typeExpressionNameMap.put( "Date", IRI.create( typesBaseIRI + "Date" ) );
        typeExpressionNameMap.put( "Ordinal", IRI.create( typesBaseIRI + "Ordinal" ) );
        typeExpressionNameMap.put( "Code", IRI.create( typesBaseIRI + "Code" ) );
        typeExpressionNameMap.put( "ClinicalRequest", IRI.create( typesBaseIRI + "ClinicalRequest" ) );
        typeExpressionNameMap.put( "Ratio", IRI.create( typesBaseIRI + "Ratio" ) );
        typeExpressionNameMap.put( "TimeInterval", IRI.create( typesBaseIRI + "TimeInterval" ) );
        typeExpressionNameMap.put( "PhysicalQuantity", IRI.create( typesBaseIRI + "PhysicalQuantity" ) );
    }

    static void initTypeNameMap ()
    {
        typeNameMap.put( "Any", "Any" );
        typeNameMap.put( "T", "Any" );
        typeNameMap.put( "C", "Any" );
        typeNameMap.put( "Object", "Object" );
        typeNameMap.put( "Object<T>", "Object" );
        typeNameMap.put( "Number", "Number" );
        typeNameMap.put( "Real", "Real" );
        typeNameMap.put( "Boolean", "Boolean" );
        typeNameMap.put( "String", "String" );
        typeNameMap.put( "Time/Duration", "TimeDuration" );
        typeNameMap.put( "Date", "Date" );
        typeNameMap.put( "Timestamp", "Timestamp" );
        typeNameMap.put( "DateGranularity", "DateGranularity" );
        typeNameMap.put( "Int", "Int" );
        typeNameMap.put( "Interval<T>", "Interval" );
        typeNameMap.put( "Collection<T>", "Collection" );
        typeNameMap.put( "List", "List" );
        typeNameMap.put( "List<T>", "List" );
        typeNameMap.put( "List<S>", "List" );
        typeNameMap.put( "List<String>", "List" );
        typeNameMap.put( "List<Boolean>", "List" );
        typeNameMap.put( "Ordered", "List" );
        typeNameMap.put( "Ordered Type", "List" );
        typeNameMap.put( "Scalar", "Scalar" );
        typeNameMap.put( "Expression<T:S>", "Any" );
        typeNameMap.put( "Ordinal", "Ordinal" );
        typeNameMap.put( "Code", "Code" );
    }


    private  static void initPrimitiveTypeMap ()  {
        primitiveTypeMap.put( "Uid", "xsd:string" );
        primitiveTypeMap.put( "Decimal", "xsd:double" );
        primitiveTypeMap.put( "Code", "xsd:string" );
        primitiveTypeMap.put( "TimeStamp", "xsd:dateTime" );
        primitiveTypeMap.put( "Literal", "xsd:string" );
        primitiveTypeMap.put( "CalendarCycle", "xsd:string" );
        primitiveTypeMap.put( "TS", "xsd:dateTime" );
        primitiveTypeMap.put( "PQ", "PhysicalQuantityLiteral" );
        primitiveTypeMap.put( "INT", "xsd:integer" );
        primitiveTypeMap.put( "set_TelecommunicationAddressUse", "xsd:string" );
        primitiveTypeMap.put( "set_TelecommunicationCapability", "xsd:string" );
        primitiveTypeMap.put( "ENXP", "xsd:string" );
        primitiveTypeMap.put( "set_EntityNameUse", "xsd:string" );
        primitiveTypeMap.put( "set_PostalAddressUse", "xsd:string" );
        primitiveTypeMap.put( "ADXP", "xsd:string" );
        primitiveTypeMap.put( "QTY", "xsd:double" );
        primitiveTypeMap.put( "IVL_TS", "TimestampIntervalLiteral" );
        primitiveTypeMap.put( "RTO", "RatioLiteral" );
    }


}
