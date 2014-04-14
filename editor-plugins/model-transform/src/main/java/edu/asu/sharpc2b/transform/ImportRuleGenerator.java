package edu.asu.sharpc2b.transform;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.drools.semantics.utils.NameUtils;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.SimpleTemplateRegistry;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRegistry;
import org.mvel2.templates.TemplateRuntime;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ImportRuleGenerator {

    private static String template;
    private static TemplateRegistry registry;


    {
        try {
            InputStream fis = FileUtil.getResourceAsStream( "/ImportTemplate.mvel" );
            byte[] data = new byte[ fis.available() ];
            fis.read( data );
            template = new String( data );
            fis.close();
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        registry = new SimpleTemplateRegistry();
        CompiledTemplate compiled = TemplateCompiler.compileTemplate( template,
                                                                      (Map<String, Class<? extends org.mvel2.templates.res.Node >>) null );
        TemplateRuntime.execute( compiled,
                                 null,
                                 registry );

    }

    public ImportRuleGenerator() {

    }


    public void processOperators( File operatorDefinitionFile, OWLOntology operatorOntology, OWLOntology sourceOntology, File outputRuleDir ) {

        if ( ! outputRuleDir.exists() ) {
            outputRuleDir.mkdirs();
        }

        Sheet ops = getExcelOperatorsSheet( operatorDefinitionFile );
        StringBuilder builder = new StringBuilder( );

        createRules( ops, builder, operatorOntology, sourceOntology );

        System.err.println( builder.toString() );


        File operatorsOputput = new File( outputRuleDir.getPath() + File.separator + "hedExpressions2owl.drl" );
        try {
            if ( ! operatorsOputput.exists() ) {
                operatorsOputput.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream( operatorsOputput );
            fos.write( builder.toString().getBytes() );
            fos.flush();
            fos.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }

    private void createRules( Sheet ops, StringBuilder builder, OWLOntology operatorOntology, OWLOntology sourceOntology ) {

        String header = (String) TemplateRuntime.execute( registry.getNamedTemplate( "header" ), null, Collections.emptyMap(), registry );
        builder.append( header );

        String common = (String) TemplateRuntime.execute( registry.getNamedTemplate( "basicRules" ), null, Collections.emptyMap(), registry );
        builder.append( common );

        for ( int j = 1; j < ops.getLastRowNum(); j++ ) {
            createRule( ops.getRow( j ), builder, operatorOntology, sourceOntology );
        }
    }

    private void createRule( Row row, StringBuilder builder, OWLOntology operatorOntology, OWLOntology sourceOntology ) {
        String opName = row.getCell( 0 ).getStringCellValue();
        String packName = NameUtils.nameSpaceURIToPackage( URI.create( sourceOntology.getOntologyID().getOntologyIRI().toString() ) );

        Arity arity = Arity.parse( (int) row.getCell( 1 ).getNumericCellValue() );
        //if ( arity == Arity.LITERAL || arity == Arity.MIXED || true ) {
            Map<String,String> dataProperties = new HashMap<String,String>();
            Map<String,String> dataTypes = new HashMap<String,String>();
            Map<String,String> objectProperties = new HashMap<String,String>();
            Map<String,String> accessors = new HashMap<String,String>();

            for ( int j = 8; j < 50; j = j + 2 ) {
                Cell cell = row.getCell( j );
                if ( cell == null || cell.getStringCellValue() == null ) {
                    break;
                }
                String propName = row.getCell( j ).getStringCellValue();

                //TODO "use" does not behave accordingly.. and is probably unncessary at this point
                if ( opName.contains( "Literal" ) && "use".equals( propName ) ) {
                    continue;
                }


                String propType = row.getCell( j + 1 ).getStringCellValue();

                String actualName = checkPropertyOverride( opName, propName, propType, operatorOntology );
                if ( propType.startsWith( "xsd:" ) ) {
                    dataProperties.put( propName, actualName );
                    String accessor = checkAccessor( opName, propName, sourceOntology, false );
                    accessors.put( propName, accessor );
                    dataTypes.put( propName, propType );
                } else {
                    objectProperties.put( propName, actualName );
                    String accessor = checkAccessor( opName, propName, sourceOntology, true );
                    accessors.put( propName, accessor );
                }
            }

            Map map = new HashMap();
            map.put( "name", packName + "." + opName );
            map.put( "objectProperties", objectProperties );
            map.put( "dataProperties", dataProperties );
            map.put( "dataTypes", dataTypes );
            map.put( "accessors", accessors );
            String rules = (String) TemplateRuntime.execute( registry.getNamedTemplate( "rule" ), null, map, registry );
            builder.append( rules );
        //}

    }

    private String checkAccessor( String opName, String propName, OWLOntology sourceOntology, boolean isObject ) {
        OWLDataFactory factory = sourceOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass klass = factory.getOWLClass( IRI.create( sourceOntology.getOntologyID().getOntologyIRI().toString() + "#", opName ) );

        boolean multiple = true;
        for ( OWLClassExpression clax : superClosure( klass, sourceOntology ) ) {
            if ( clax instanceof OWLObjectIntersectionOf ) {
                for ( OWLClassExpression expr : clax.asConjunctSet() ) {
                    if ( expr instanceof OWLObjectMaxCardinality 
                         && ( (OWLObjectMaxCardinality) expr ).getProperty().asOWLObjectProperty().getIRI().getFragment().equals( propName )
                         && ( (OWLObjectMaxCardinality) expr ).getCardinality() == 1) {
                        multiple = false;
                    } else 
                    if ( expr instanceof OWLDataMaxCardinality 
                         && ( (OWLDataMaxCardinality) expr ).getProperty().asOWLDataProperty().getIRI().getFragment().equals( propName )
                         && ( (OWLDataMaxCardinality) expr ).getCardinality() == 1) {
                        multiple = false;
                    }
                }
            }
        }
        //TODO Check a bug in the HeD schema ontology
        if ( "Substring".equals( opName ) ) {
            multiple = false;
        }

        if ( multiple == true ) {
            System.out.println( "Pluralizing " + propName + " in " + opName );
            propName = makePlural( propName );
        }

        if ( ! isObject && multiple ) {
            propName = propName + "[ 0 ]";
        }

        return propName;
    }

    private Set<OWLClassExpression> superClosure( OWLClass klass, OWLOntology sourceOntology ) {
        Set<OWLClassExpression> supers = new HashSet<OWLClassExpression>();
        for (OWLSubClassOfAxiom subx : sourceOntology.getSubClassAxiomsForSubClass( klass ) ) {
            OWLClassExpression sup = subx.getSuperClass();
            if ( ! sup.isAnonymous() ) {
                supers.addAll( superClosure( sup.asOWLClass(), sourceOntology ) );
            }
            supers.add( sup );
        }
        return supers;
    }

    private String makePlural( String propName ) {
        if ( propName.endsWith( "y" ) ) {
            return propName.substring( 0, propName.length() - 1 ) + "ies";
        }
        if ( propName.endsWith( "f" ) ) {
            return propName.substring( 0, propName.length() - 1 ) + "ves";
        }
        return propName + "s";
    }

    private String checkPropertyOverride( String opName, String propName, String propType, OWLOntology operatorOntology ) {
        String candidate = propName;
        OWLOntologyID mainId = operatorOntology.getOntologyID();
        OWLDataFactory factory = operatorOntology.getOWLOntologyManager().getOWLDataFactory();
        OWLClass opClass = factory.getOWLClass( IRI.create( mainId.getOntologyIRI().toString() + "#", opName + "Expression" ) );

        for ( OWLSubClassOfAxiom sub : operatorOntology.getSubClassAxiomsForSubClass( opClass ) ) {
            OWLClassExpression sup = sub.getSuperClass();
            IRI propIri = null;
            if ( sup instanceof OWLObjectAllValuesFrom ) {
                propIri = ( (OWLObjectAllValuesFrom) sup ).getProperty().asOWLObjectProperty().getIRI();
            } else if ( sup instanceof OWLDataAllValuesFrom ) {
                propIri = ( (OWLDataAllValuesFrom) sup ).getProperty().asOWLDataProperty().getIRI();
            }
            if ( propIri != null ) {
                if ( propName.equals( propIri.getFragment() ) ) {
                    return propName;
                } else if ( propIri.getFragment().startsWith( propName + "_" ) ) {
                    candidate = propIri.getFragment();
                }
            }
        }
        return candidate;
    }



    private Sheet getExcelOperatorsSheet (File excelFile) {
        FileInputStream fis = null;
        Workbook workbook;
        try {
            fis = new FileInputStream( excelFile );
            workbook = WorkbookFactory.create( fis );
            Sheet sheet = workbook.getSheet( "Operators" );
            return sheet;

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (fis != null) {
                try  {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }









}
