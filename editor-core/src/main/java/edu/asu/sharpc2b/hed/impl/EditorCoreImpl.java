package edu.asu.sharpc2b.hed.impl;

import edu.asu.sharpc2b.hed.api.DomainModel;
import edu.asu.sharpc2b.hed.api.EditorCore;
import edu.asu.sharpc2b.hed.api.ExpressionSource;
import edu.emory.mathcs.backport.java.util.Collections;
import org.dom4j.DocumentFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.w3c.dom.Document;
import org.w3c.dom.ElementTraversal;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EditorCoreImpl implements EditorCore, DomainModel {

    private ConcurrentMap<String,HeDArtifactData> artifacts;
    private String currentArtifactId;
    

    private static EditorCoreImpl instance = new EditorCoreImpl();

    public static EditorCore getInstance() {
        return instance;
    }

    protected EditorCoreImpl() {
        artifacts = new ConcurrentHashMap<String,HeDArtifactData>();
        
        createNew();
    }

    
    public String getCurrentArtifactId() {
        return currentArtifactId;
    }
    
    public HeDArtifactData getCurrentArtifact() {
        return artifacts.get( currentArtifactId ); 
    }






    @Override
    public Map<String,String> getExpressions() {
        return getCurrentArtifact().getNamedExpressions();
    }

    @Override
    public byte[] getExpression( String exprId ) {
        return getCurrentArtifact().getNamedExpression( exprId ).getDoxBytes();
    }

    @Override
    public boolean updateNamedExpression( String exprId, String exprName, byte[] doxBytes ) {
//        Document dox = null;
//        try {
//            DocumentBuilder doxBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            doxBuilder.parse( new ByteArrayInputStream( doxBytes ) );
//        } catch ( Exception e ) {
//            e.printStackTrace();
//            return false;
//        }

        return getCurrentArtifact().updateNamedExpression( exprId, exprName, doxBytes );
    }


    
    
    
    
    @Override
    public String createNew() {
        currentArtifactId = UUID.randomUUID().toString();
        artifacts.putIfAbsent( currentArtifactId, new HeDArtifactData( currentArtifactId ) );
        return currentArtifactId;
    }

    @Override
    public String open( String artifactId ) {
        currentArtifactId = artifactId;
        return currentArtifactId;
    }

    @Override
    public boolean save( String artifactId ) {
        return true;
    }

    @Override
    public void delete( String artifactId ) {
        artifacts.remove( artifactId );
    }



    private Map<String,String> domKlasses;
    private Map<String,String> domProptis;

    @Override
    public Map<String, String> getDomainClasses() {
        if ( domKlasses == null ) {
            domKlasses = new HashMap<String,String>();
            try {
                OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
                manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-model/src/main/resources/ontologies/editor_models/skos-core.owl" ) );
                manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-model/src/main/resources/ontologies/editor_models/skos-ext.owl" ) );
                manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-model/src/main/resources/ontologies/editor_models/expr-core.owl" ) );
                OWLOntology domain = manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-model/src/main/resources/ontologies/domain_models/domain-vmr.ofn" ) );
                OWLDataFactory odf = domain.getOWLOntologyManager().getOWLDataFactory();
                for ( OWLNamedIndividual ind : domain.getIndividualsInSignature() ) {
                    if ( ind.getTypes( domain ).contains( odf.getOWLClass( IRI.create( "http://asu.edu/sharpc2b/ops#DomainClass" ) ) ) )  {
                        if ( ! isDatatype( ind.getIRI() ) && ! isAbstract( ind.getIRI() ) ) {
                            domKlasses.put( ind.getIRI().toString(), ind.getIRI().getFragment() );
                        }
                    }
                }
            } catch ( OWLOntologyCreationException e ) {
                e.printStackTrace();
            }
            return domKlasses;

        } else {
            return domKlasses;
        }
    }

    private boolean isAbstract( IRI iri ) {
        //TODO hack!
//        return iri.getFragment().endsWith( "Base" );
        return false;
    }

    private boolean isDatatype( IRI iri ) {
        //TODO hack!
        return iri.getFragment().toUpperCase().equals( iri.getFragment() ) || iri.getFragment().contains( "." );
    }

    @Override
    public Map<String, String> getDomainProperties() {
        if ( domProptis == null ) {
            domProptis = new HashMap<String,String>();
            try {
                OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
                manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-model/src/main/resources/ontologies/editor_models/skos-core.owl" ) );
                manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-model/src/main/resources/ontologies/editor_models/skos-ext.owl" ) );
                manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-model/src/main/resources/ontologies/editor_models/expr-core.owl" ) );
                OWLOntology domain = manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-model/src/main/resources/ontologies/domain_models/domain-vmr.ofn" ) );
                OWLDataFactory odf = domain.getOWLOntologyManager().getOWLDataFactory();
                for ( OWLNamedIndividual ind : domain.getIndividualsInSignature() ) {
                    if ( ind.getTypes( domain ).contains( odf.getOWLClass( IRI.create( "http://asu.edu/sharpc2b/ops#DomainProperty"  ) ) ) ) {
                        Set<OWLIndividual> values = ind.getObjectPropertyValues( odf.getOWLObjectProperty( IRI.create( "http://asu.edu/sharpc2b/skos-ext#partOf" ) ), domain );
                        for ( OWLIndividual val : values ) {
                            if ( val.isNamed() && getDomainClasses().containsKey( ((OWLNamedIndividual) val ).getIRI().toString()) ) {
                                domProptis.put( ind.getIRI().toString(), ind.getIRI().getFragment() );
                            }
                        }
                    }
                }
            } catch ( OWLOntologyCreationException e ) {
                e.printStackTrace();
            }
            return domProptis;

        } else {
            return domProptis;
        }
    }

    @Override
    public Map<String, String> getDomainProperties( String klassId ) {
        return Collections.emptyMap();
    }




    private OWLOntology templates;

    private void loadTemplateOntology() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        try {
            templates = manager.createOntology( IRI.create( "http://asu.edu/sharpc2b/templates/data" ) );
            manager = templates.getOWLOntologyManager();
            manager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-model/src/main/resources/ontologies/templates/template_schema.owl" ) );
            File dataDir = new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-model/src/main/resources/ontologies/templates/data" );
            for ( File tempFile : dataDir.listFiles() ) {
                OWLOntologyManager subManager = OWLManager.createOWLOntologyManager();
                subManager.loadOntologyFromOntologyDocument( new File( "/home/davide/Projects/Git/HeD-editor/sharp-editor/editor-models/hed-model/src/main/resources/ontologies/templates/template_schema.owl" ) );
                OWLOntology temp = subManager.loadOntologyFromOntologyDocument( tempFile );
                manager.addAxioms( templates, temp.getAxioms() );
            }

        } catch ( OWLOntologyCreationException e ) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    @Override
    public Set<String> getTemplateIds( String category ) {
        System.out.println( "Called core getTemplateIds with " + category );
        if ( templates == null ) {
            loadTemplateOntology();
        }
        OWLDataFactory odf = templates.getOWLOntologyManager().getOWLDataFactory();
        Set<OWLNamedIndividual> individuals = templates.getIndividualsInSignature( true );
        Set<String> individualIds = new HashSet<String>();
        for ( OWLNamedIndividual ind : individuals ) {
            if ( ind.getTypes( templates.getImportsClosure() ).contains( odf.getOWLClass( templateIRI( "Template" ) ) ) ) {
                Set<OWLLiteral> cats = ind.getDataPropertyValues( odf.getOWLDataProperty( templateIRI( "category" ) ), templates );
                if ( category != null && ! category.isEmpty() ) {
                    if ( cats.contains( odf.getOWLLiteral( category ) ) ) {
                        individualIds.add( ind.getIRI().toString() );
                    }
                } else {
                    individualIds.add( ind.getIRI().toString() );
                }
            }
        }
        System.out.println( "Retrieved individual ids " + individualIds );
        return individualIds;
    }

    private IRI templateIRI( String s ) {
        return IRI.create( "http://asu.edu/sharpc2b/templates#" + s );
    }

    private IRI templateDataIRI( String s ) {
        return IRI.create( "http://asu.edu/sharpc2b/templates/data#" + s );
    }


    @Override
    public Map<String, Object> getTemplateInfo( String templateId ) {
        if ( templates == null ) {
            loadTemplateOntology();
        }
        System.out.println( "Requesting info about " + templateId );
        OWLDataFactory odf = templates.getOWLOntologyManager().getOWLDataFactory();
        Map<String,Object> info = new HashMap<String,Object>();

        OWLNamedIndividual ind = odf.getOWLNamedIndividual( IRI.create( templateId ) );

        info.put( "templateId", templateId );
        info.put( "category", getDataPropertyValue( ind, "category", odf ) );
        info.put( "name", getDataPropertyValue( ind, "name", odf ) );
        info.put( "group", getDataPropertyValue( ind, "group", odf ) );
        info.put( "description", getDataPropertyValue( ind, "description", odf ) );
        info.put( "example", getDataPropertyValue( ind, "example", odf ) );
        info.put( "parameterIds", getObjectPropertyValues( ind, "hasParameter", odf ) );

        System.out.println( "Retrieved info " + info );
        return info;
    }

    private String getDataPropertyValue( OWLNamedIndividual ind, String propName, OWLDataFactory odf ) {
        Set<OWLLiteral> values = ind.getDataPropertyValues( odf.getOWLDataProperty( templateIRI( propName ) ), templates );
        if ( values.isEmpty() ) {
            return null;
        } else {
            return values.iterator().next().getLiteral();
        }
    }

    private List<String> getDataPropertyValues( OWLNamedIndividual ind, String propName, OWLDataFactory odf ) {
        Set<OWLLiteral> values = ind.getDataPropertyValues( odf.getOWLDataProperty( templateIRI( propName ) ), templates );
        if ( values.isEmpty() ) {
            return null;
        } else {
            ArrayList<String> literals = new ArrayList<String>( values.size() );
            for ( OWLLiteral lit : values ) {
                literals.add( lit.getLiteral() );
            }
            return literals;
        }
    }

    private List<String> getObjectPropertyValues( OWLNamedIndividual ind, String propName, OWLDataFactory odf ) {
        Set<OWLIndividual> values = ind.getObjectPropertyValues( odf.getOWLObjectProperty( templateIRI( propName ) ), templates );
        if ( values.isEmpty() ) {
            return null;
        } else {
            ArrayList<String> targets = new ArrayList<String>( values.size() );
            for ( OWLIndividual tgt : values ) {
                targets.add( tgt.toStringID() );
            }
            return targets;
        }
    }


    public static void main( String... args ) {
        System.out.println( new EditorCoreImpl().getTemplateIds( "Condition" ) );
    }

}
