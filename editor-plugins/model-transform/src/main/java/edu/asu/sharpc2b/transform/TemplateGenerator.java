package edu.asu.sharpc2b.transform;

import edu.asu.sharpc2b.ClassPathResource;
import edu.asu.sharpc2b.OwlHelper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomChange;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeVisitor;
import org.semanticweb.owlapi.model.OWLOntologyChangeVisitorEx;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;


public class TemplateGenerator {

    public static final int TEMPLATE_INDEX = 0;
    public static final int TEMPLATE_NAME = 5;
    public static final int TEMPLATE_CATEGORY = 4;
    public static final int PARAM_VMR_DATA_ELEMENT = 9;
    public static final int PARAM_DISPLAY_NAME = 7;
    public static final int TEMPLATE_ROOT = 8;
    public static final int PARAM_DATATYPE = 14;
    public static final int PARAM_CARD = 10;
    public static final int PARAM_DEFAULT = 12;
    public static final int PARAM_CONSTRAINTS = 15;


    private Workbook workbook;
    private String tns;
    private String templatesIRI;
    
    public TemplateGenerator( String templateOntologyIRI, File inputTemplateFile ) throws IOException, InvalidFormatException {
        workbook = WorkbookFactory.create( inputTemplateFile );
        templatesIRI = templateOntologyIRI;
        tns = templateOntologyIRI + "#";
    }

    public static void main( String... args ) throws IOException, InvalidFormatException, OWLOntologyCreationException, OWLOntologyStorageException {
        String iri = "http://asu.edu/sharpc2b/templates";
        File f = new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/generated-models/src/main/resources/HeD_Templates.xlsx" );

        TemplateGenerator gen = new TemplateGenerator( iri, f );

        OWLOntology onto = gen.createTemplateOntology();

        onto.getOWLOntologyManager().saveOntology( onto,
                                                   new OWLFunctionalSyntaxOntologyFormat(),
                                                   new FileOutputStream( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-ontologies/src/main/resources/ontologies/templates/sharp_templates.owl" ) );
    }





    public OWLOntology createTemplateOntology( ) throws IOException, OWLOntologyCreationException {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        manager.loadOntologyFromOntologyDocument( new ClassPathResource( "ontologies/editor_models/template_schema.owl" ).getInputStream() );
        OWLOntology ontT = manager.createOntology( IRI.create( templatesIRI + "_data" ) );
        manager.applyChange( new AddImport( ontT, manager.getOWLDataFactory().getOWLImportsDeclaration( IRI.create( templatesIRI ) ) ) );

        DefaultPrefixManager pManager = new DefaultPrefixManager();
        pManager.setPrefix( "tns:", tns );
        OwlHelper helper = new OwlHelper( ontT, pManager );


        for ( int j = 0; j < workbook.getNumberOfSheets(); j++ ) {
            Sheet sheet = workbook.getSheetAt( j );
            Map<String, Template> templates = processSheet( sheet );

            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            OWLClass templateKlass = helper.asClass( tns + "Template" );

            for ( Template t : templates.values() ) {
                OWLNamedIndividual templ = helper.asIndividual( "tns:" + t.id );

                axioms.add( helper.assertType( templ, "tns:Template" ) );

                String categories = t.category;
                StringTokenizer tok = new StringTokenizer( categories, "," );
                String mainCategory = null;
                while ( tok.hasMoreTokens() ) {
                    String name = tok.nextToken().trim();
                    if ( mainCategory == null ) {
                        mainCategory = name;
                    } else {
                        String catName = name.replace( " ", "_" ) + "Template";
                        OWLClass klass = helper.asClass( tns + catName );
                        axioms.add( helper.assertType( templ, "tns:" + catName ) );
                        axioms.add( helper.assertSubClass( klass, templateKlass ) );
                        axioms.add( helper.assertClass( klass ) );
                    }
                }

                String descr = split( t.name );
                axioms.add( helper.assertDataProperty( "tns:index", templ, t.index ) );
                axioms.add( helper.assertDataProperty( "tns:name", templ, t.name ) );
                axioms.add( helper.assertDataProperty( "tns:description", templ, descr ) );
                //TODO rootClass and paths should be DomainClasses and DomainProperties
                axioms.add( helper.assertDataProperty( "tns:rootClass", templ, t.root ) );
                axioms.add( helper.assertDataProperty( "tns:example", templ, descr ) );
                axioms.add( helper.assertDataProperty( "tns:group", templ, mainCategory ) );

                for ( Parameter p : t.params ) {
                    String paramName = p.name;
                    //paramName = paramName.substring( paramName.lastIndexOf( '/' ) + 1 );
                    paramName = paramName.replace( ".", "_" );
                    OWLNamedIndividual param = helper.asIndividual( "tns:" + t.id + "_" + paramName );

                    axioms.add( helper.assertType( param, "tns:" + "Parameter" ) );

                    axioms.add( helper.assertDataProperty( "tns:name", param, paramName ) );
                    axioms.add( helper.assertDataProperty( "tns:path", param, p.name ) );
                    axioms.add( helper.assertDataProperty( "tns:label", param, p.displayName ) );
                    axioms.add( helper.assertDataProperty( "tns:description", param, p.displayName ) );
                    axioms.add( helper.assertDataProperty( "tns:typeName", param, p.type ) );
                    axioms.add( helper.assertDataProperty( "tns:paramIndex", param, p.index) );
                    if ( p.defaultValue != null && ! "".equals( p.defaultValue ) ) {
                        axioms.add( helper.assertDataProperty( "tns:defaultValue", param, p.defaultValue ) );
                    }
                    for ( String constr : p.constraints ) {
                        axioms.add( helper.assertDataProperty( "tns:constraint", param, constr ) );
                    }
                    axioms.add( helper.assertDataProperty( "tns:optional", param, p.optional ) );
                    axioms.add( helper.assertDataProperty( "tns:multiple", param, p.multiple ) );


                    axioms.add( helper.assertObjectProperty( "tns:hasParameter", templ, param ) );
                }

                axioms.add( helper.assertIndividual( templ ) );
            }
            helper.buildOntology( axioms );
        }
        return ontT;
    }

    private static String camelCase( String name ) {
        String x = "";
        for ( int j = 0; j < name.length(); j++ ) {
            if ( Character.isUpperCase( name.charAt(j)) ) {
                x += "_" + name.charAt( j );
            } else {
                x += Character.toUpperCase( name.charAt( j ) );
            }
        }
        return x;
    }

    private static Map<String, Template> processSheet( Sheet sheet ) {
        int N = sheet.getPhysicalNumberOfRows();
        Row header = sheet.getRow( 0 );

        Map<String, Template> templates = new HashMap<String, Template>();
        System.out.println( "Visiting sheet " + sheet.getSheetName() + " with rows " + N );

        for ( int j = 1; j < N; j++ ) {
            Row row = sheet.getRow( j );

            if ( row == null ) {
                // sometimes the sheet contains empty rows ...
                continue;
            }
            Cell indexCell = row.getCell( TEMPLATE_INDEX );
            Cell templateCell = row.getCell( TEMPLATE_NAME );
            Cell templateCat = row.getCell( TEMPLATE_CATEGORY );
            Cell rootCell = row.getCell( TEMPLATE_ROOT );

            if ( templateCell == null || "".equals( templateCell.getStringCellValue() ) ) {
                // ... or empty cells
                continue;
            }

            String name = templateCell.getStringCellValue();
            Template templ = templates.get( name );

            if ( templ == null ) {
                templ = new Template();
                templ.id = templateCell.getStringCellValue().replace( " ", "_" );
                templ.name = split( templateCell.getStringCellValue() );
                templ.root = rootCell.getStringCellValue();
                templ.category = templateCat.getStringCellValue();
                templates.put( templ.id, templ );
            }

            // uses strikethrough to indicate deprecated templates!?
            boolean isDisabled = sheet.getWorkbook().getFontAt( templateCell.getCellStyle().getFontIndex() ).getStrikeout();

            if ( ! isEmpty( templ.name ) && ( ! isDisabled ) ) {

                if ( indexCell != null ) {
                    int index = (int) indexCell.getNumericCellValue();
                    templ.index = index;
                }

                String element = row.getCell( PARAM_VMR_DATA_ELEMENT ).getStringCellValue();
                if ( ! "templateId".equals( element ) ) {
                    String display = row.getCell( PARAM_DISPLAY_NAME ).getStringCellValue();
                    String datatype = row.getCell( PARAM_DATATYPE ).getStringCellValue();
                    Parameter p = new Parameter( element, display, element, datatype );
                    if ( p.isValid() ) {
                        String def = row.getCell( PARAM_DEFAULT ) != null ? row.getCell( PARAM_DEFAULT ).getStringCellValue() : null;
                        if ( def != null ) {
                            p.defaultValue = def;
                        }
                        String constraints = row.getCell( PARAM_CONSTRAINTS ) != null ? row.getCell( PARAM_CONSTRAINTS ).getStringCellValue() : null;
                        if ( constraints != null ) {
                            StringTokenizer tok = new StringTokenizer( constraints, ";" );
                            while( tok.hasMoreTokens() ) {
                                p.constraints.add( tok.nextToken() );
                            }
                        }
                        String card = row.getCell( PARAM_CARD ) != null ? row.getCell( PARAM_CARD ).getStringCellValue() : null;
                        if ( card != null ) {
                            if ( card.startsWith( "0" ) ) {
                                p.optional = true;
                            }
                            if ( card.contains( "*" ) ) {
                                p.multiple = true;
                            }
                        }
                        p.index = j;
                        templ.params.add( p );
                    }

                }

            }

        }

        return templates;
//        System.out.println( "\n\n " + sheet.getSheetName() );
//        System.out.println( "Found templates : " + templates.size() );
//        System.out.println( formatMap( templates ) );
//        System.out.println( "\n" );


    }



    private static class Parameter {
        public String name;
        public String property;
        public String displayName;
        public String type;
        public String defaultValue;
        public int index;
        public List<String> constraints;
        public boolean optional = false;
        public boolean multiple = false;

        public Parameter( String name, String displayName, String property, String type ) {
            this.name = isEmpty( name ) ? null : name;
            this.property = property;
            this.displayName = isEmpty( displayName ) ? name : displayName;
            this.type = isEmpty( type ) ? null : type;
            this.constraints = new ArrayList<String>( 1 );
        }

        public boolean isValid() {
            return property != null && name != null && type != null;
        }

        @Override
        public String toString() {
            return "Parameter{" +
                   "name='" + name + '\'' +
                   ", property='" + property + '\'' +
                   ", displayName='" + displayName + '\'' +
                   ", type='" + type + '\'' +
                   '}';
        }
    }

    private static class Template {
        public int index;

        public String id;
        public String name;
        public String category;
        public String root;
        public List<Parameter> params = new ArrayList<Parameter>();

        @Override
        public String toString() {
            return "Template{" +
                   "name='" + name + '\'' +
                   ", category='" + category + '\'' +
                   '}';
        }
    }


    private static String formatMap( Map<String, List<Parameter>> templates ) {
        String s = "";
        for ( String k : templates.keySet() ) {
            s += format( k, templates.get( k ) ) + "\n";
        }
        return s;
    }


    public static String format( String template, List<Parameter> params ) {
        String s = template;
        boolean first = true;
        for ( Parameter p : params ) {
            s += connective( p.type, first ) + p.displayName;
            first = false;
        }
        return s;
    }

    private static String connective( String type, boolean first ) {
        if ( "TS".equals( type ) || "IVL_TS".equals( type ) ) {
            return " ON ";
        } else {
            return first ? " WITH " : " AND ";
        }
    }


    public static boolean isEmpty( String s ) {
        return s == null || "\\w+".matches( s );
    }

    public static String split( String name ) {
        StringBuilder sb = new StringBuilder();
        for ( int j = 0; j < name.length(); j ++ ) {
            if ( j > 0 && Character.isUpperCase( name.charAt( j ) ) && name.charAt( j - 1 ) != ' ' ) {
                sb.append( " " );
            }
            sb.append( name.charAt( j ) );
        }
        return sb.toString();
    }


}
