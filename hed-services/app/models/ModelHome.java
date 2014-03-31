package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import edu.asu.sharpc2b.hed.api.EditorCore;
import edu.asu.sharpc2b.hed.impl.EditorCoreImpl;
import edu.asu.sharpc2b.metadata.ClinicalCoverage;
import edu.asu.sharpc2b.metadata.ClinicalCoverageImpl;
import edu.asu.sharpc2b.metadata.Coverage;
import edu.asu.sharpc2b.metadata.CoverageImpl;
import edu.asu.sharpc2b.metadata.Evidence;
import edu.asu.sharpc2b.metadata.EvidenceImpl;
import edu.asu.sharpc2b.metadata.InlineResource;
import edu.asu.sharpc2b.metadata.KnowledgeResource;
import edu.asu.sharpc2b.metadata.KnowledgeResourceImpl;
import edu.asu.sharpc2b.metadata.RightsDeclaration;
import edu.asu.sharpc2b.metadata.RightsDeclarationImpl;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.skos_ext.CodedConcept;
import edu.asu.sharpc2b.skos_ext.CodedConceptImpl;
import edu.asu.sharpc2b.skos_ext.ConceptCode;
import edu.asu.sharpc2b.skos_ext.ConceptCodeImpl;
import edu.mayo.cts2.framework.core.client.Cts2RestClient;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntry;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntryDirectory;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntryList;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntrySummary;
import models.ex.ConvertJsonToJavaException;
import models.ex.ModelDataFileNotFoundException;
import models.metadata.Contributor;
import models.metadata.Resource;
import models.metadata.SupportingResource;
import models.metadata.UsageTerm;
import org.drools.semantics.Thing;
import org.ontologydesignpatterns.ont.dul.dul.Entity;
import org.ontologydesignpatterns.ont.dul.dul.OrganizationImpl;
import org.ontologydesignpatterns.ont.dul.dul.SocialPerson;
import org.ontologydesignpatterns.ont.dul.dul.SocialPersonImpl;
import org.purl.dc.terms.Agent;
import org.purl.dc.terms.AgentImpl;
import org.purl.dc.terms.Location;
import org.purl.dc.terms.LocationImpl;
import org.purl.dc.terms.RightsStatement;
import org.purl.dc.terms.RightsStatementImpl;
import play.libs.Json;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * User: rk Date: 8/19/13 Package: models
 */
public class ModelHome
{

    static int lastKey = 1;


    private static EditorCore core = EditorCoreImpl.getInstance();
    private static Map<String, String> domainClasses;


    /************************************************************************************************************/
    /* ARTIFACT APIs */
    /************************************************************************************************************/

    public static List<String> getAvailableArtifacts() {
        return core.getAvailableArtifacts();
    }

    public static Rule getArtifact( String id ) {
        return null;
    }

    public static String importFromStream( byte[] stream ) {
        return core.importFromStream( stream );
    }

    public static Rule createArtifact() {
        String ruleId = core.createArtifact();
        Rule rule = new Rule();
        rule.ruleId = ruleId;
        return rule;
    }

    public static String cloneArtifact( String id ) {
        return core.cloneArtifact( id );
    }

    public static String openArtifact( String id ) {
        return core.openArtifact( id );
    }

    public static String snapshotArtifact( String id ) {
        return core.snapshotArtifact( id );
    }

    public static String saveArtifact( String id ) {
        return core.saveArtifact( id );
    }

    public static byte[] exportArtifact( String id, String format ) {
        return core.exportArtifact( id, format );
    }

    public static String closeArtifact() {
        return core.closeArtifact();
    }

    public static String deleteArtifact( String id ) {
        return core.deleteArtifact( id );
    }




    /************************************************************************************************************/
    /* METADATA APIs */
    /************************************************************************************************************/

    //TODO This part needs serious improvement, at all levels
    public static List<String> getKeyTerms( String id ) {
        List<String> terms = new ArrayList<String>();
        return terms;
    }

    public static List<String> getCategories( String id ) {
        List<String> terms = new ArrayList<String>();
        return terms;
    }

    public static List<Contributor> getContributors( String id ) {
        List<Contributor> contris = new ArrayList<Contributor>();
        HeDKnowledgeDocument dok = core.getArtifact( id );


        if ( dok.getAuthor() != null ) {
            for ( Agent a : dok.getAuthor() ) {
                Contributor c = new Contributor();
                c.Type = "Author";
                fillAgent( a, c );
                contris.add( c );
            }
            for ( Agent a : dok.getEditor() ) {
                Contributor c = new Contributor();
                c.Type = "Editor";
                fillAgent( a, c );
                contris.add( c );
            }
            for ( Agent a : dok.getEndorser() ) {
                Contributor c = new Contributor();
                c.Type = "Endorser";
                fillAgent( a, c );
                contris.add( c );
            }
            for ( Agent a : dok.getReviewer() ) {
                Contributor c = new Contributor();
                c.Type = "Reviewer";
                fillAgent( a, c );
                contris.add( c );
            }

        }
        return contris;
    }

    public static void fillAgent( Agent agent, Contributor contributor ) {
        contributor.Id = agent.getRdfId().toString();

        if ( agent.getAgentName() != null && ! agent.getAgentName().isEmpty() ) {
            contributor.Name = agent.getAgentName().get( 0 );
        }
        if ( agent.getAgentRole() != null && ! agent.getAgentRole().isEmpty() ) {
            contributor.Role = agent.getAgentRole().get( 0 );
        }
        if ( agent.getAddress() != null && ! agent.getAddress().isEmpty() ) {
            contributor.Address = agent.getAddress().get( 0 );
        }
        if ( agent.getContactInformation() != null && ! agent.getContactInformation().isEmpty() ) {
            contributor.Contact = agent.getContactInformation().get( 0 );
        }
        if ( agent instanceof SocialPerson ) {
            contributor.Type = "Person";
        } else {
            contributor.Type = "Company";
        }
    }

    public static List<models.metadata.Coverage> getCoverage( String id ) {
        List<models.metadata.Coverage> covers = new ArrayList<models.metadata.Coverage>();
        HeDKnowledgeDocument dok = core.getArtifact( id );
        for ( Coverage cover : dok.getApplicability() ) {
            models.metadata.Coverage cov = new models.metadata.Coverage();
            cov.Id = cover.getRdfId().toString();
            if ( cover.getCoverageType() != null && ! cover.getCoverageType().isEmpty() ) {
                cov.Type = cover.getCoverageType().get( 0 );
            }
            if ( cover.getCoveredConcept() != null && ! cover.getCoveredConcept().isEmpty() ) {
                ConceptCode code = cover.getCoveredConcept().get( 0 );
                if ( code.getCode() != null && ! code.getCode().isEmpty() ) {
                    cov.Code = code.getCode().get( 0 );
                }
                if ( code.getCodeSystem() != null && ! code.getCodeSystem().isEmpty() ) {
                    cov.CodeSet = code.getCodeSystem().get( 0 );
                }
                if ( code.getLabel() != null && ! code.getLabel().isEmpty() ) {
                    cov.Description = code.getLabel().get( 0 );
                }
            }
            covers.add( cov );
        }
        return covers;
    }


    public static List<Contributor> getPublishers( String id ) {
        List<Contributor> pubs = new ArrayList<Contributor>();
        HeDKnowledgeDocument dok = core.getArtifact( id );
        for ( Agent agent : dok.getPublisher() ) {
            Contributor publisher = new Contributor();
            fillAgent( agent, publisher );
            pubs.add( publisher );
        }
        return pubs;
    }

    public static List<UsageTerm> getUsageRights( String id ) {
        List<UsageTerm> terms = new ArrayList<UsageTerm>();
        HeDKnowledgeDocument dok = core.getArtifact( id );
        for ( RightsDeclaration decl : dok.getUsageTerms() ) {
            UsageTerm term = new UsageTerm();
            term.Id = decl.getRdfId().toString();

            if ( decl.getAuthor() != null && ! decl.getAuthor().isEmpty() ) {
                Agent agent = decl.getAuthor().get( 0 );
                if ( ! agent.getAgentName().isEmpty() ) {
                    term.Name = agent.getAgentName().get( 0 );
                }
                if ( agent instanceof SocialPerson ) {
                    term.Type = "Person";
                } else {
                    term.Type = "Company";
                }
            }
            if ( decl.getAccessRights() != null && ! decl.getAccessRights().isEmpty() ) {
                RightsStatement stat = decl.getAccessRights().get( 0 );
                if ( stat.getLicenseTerms().isEmpty() ) {
                    term.Permissions = stat.getLicenseTerms().get( 0 );
                }
            }
            if ( decl.getRights() != null && ! decl.getRights().isEmpty() ) {
                RightsStatement stat = decl.getRights().get( 0 );
                if ( stat.getLicenseTerms().isEmpty() ) {
                    term.AssertedRights = stat.getLicenseTerms().get( 0 );
                }
            }
            terms.add( term );
        }
        return terms;
    }

    public static String getArtifactDocumentation( String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );
        if ( ! dok.getDocumentation().isEmpty() ) {
            InlineResource res = dok.getDocumentation().get( 0 );
            if ( ! res.getDescription().isEmpty() ) {
                return res.getDescription().get( 0 );
            }
        }
        return "";
    }

    public static List<Resource> getRelatedResources( String id ) {
        List<Resource> resources = new ArrayList<Resource>();
        HeDKnowledgeDocument dok = core.getArtifact( id );
        for ( KnowledgeResource res : dok.getAssociatedResource() ) {
            Resource r = new Resource();
            r.Type = "AssociatedResource";
            fillResource( res, r );
            resources.add( r );
        }
        for ( KnowledgeResource res : dok.getAdaptedFrom() ) {
            Resource r = new Resource();
            r.Type = "AdaptedFrom";
            fillResource( res, r );
            resources.add( r );
        }
        for ( KnowledgeResource res : dok.getDependsOn() ) {
            Resource r = new Resource();
            r.Type = "DependsOn";
            fillResource( res, r );
            resources.add( r );
        }
        for ( KnowledgeResource res : dok.getDerivedFrom() ) {
            Resource r = new Resource();
            r.Type = "DerivedFrom";
            fillResource( res, r );
            resources.add( r );
        }
        for ( KnowledgeResource res : dok.getSimilarTo() ) {
            Resource r = new Resource();
            r.Type = "SimilarTo";
            fillResource( res, r );
            resources.add( r );
        }
        //TODO fix missing range in ontology
        for ( Thing res : dok.getVersionOf() ) {
            Resource r = new Resource();
            r.Type = "VersionOf";
            if ( res instanceof KnowledgeResource ) {
                fillResource( (KnowledgeResource) res, r );
            }
            resources.add( r );
        }
        return resources;
    }

    public static List<SupportingResource> getSupportingEvidence( String id ) {
        List<SupportingResource> resources = new ArrayList<SupportingResource>();
        HeDKnowledgeDocument dok = core.getArtifact( id );
        for ( Evidence ev : dok.getSupportingEvidence() ) {
            SupportingResource supp = new SupportingResource();
            supp.Id = ev.getRdfId().toString();

            if ( ev.getQualityOfEvidence() != null && ! ev.getQualityOfEvidence().isEmpty() ) {
                ConceptCode qual = ev.getQualityOfEvidence().get( 0 );
                if ( qual.getCode() != null && ! qual.getCode().isEmpty() ) {
                    supp.Quality = qual.getCode().get( 0 );
                }
                if ( qual.getCodeSystem() != null && ! qual.getCodeSystem().isEmpty() ) {
                    supp.QualityCodeSystem = qual.getCodeSystem().get( 0 );
                }
                if ( qual.getLabel() != null && ! qual.getLabel().isEmpty() ) {
                    supp.QualityLabel = qual.getLabel().get( 0 );
                }
            }
            if ( ev.getStrengthOfReccomendation() != null && ! ev.getStrengthOfReccomendation().isEmpty() ) {
                ConceptCode str = ev.getStrengthOfReccomendation().get( 0 );
                if ( str.getCode() != null && ! str.getCode().isEmpty() ) {
                    supp.Strength = str.getCode().get( 0 );
                }
                if ( str.getCodeSystem() != null && ! str.getCodeSystem().isEmpty() ) {
                    supp.StrengthCodeSystem = str.getCodeSystem().get( 0 );
                }
                if ( str.getLabel() != null && ! str.getLabel().isEmpty() ) {
                    supp.StrengthLabel = str.getLabel().get( 0 );
                }
            }
            if ( ev.getSupportingResource() != null && ! ev.getSupportingResource().isEmpty() ) {
                fillResource( ev.getSupportingResource().get( 0 ), supp );
            }
        }
        return resources;
    }



    private static void fillResource( KnowledgeResource res, Resource r ) {
        if ( r.Id == null ) {
            r.Id = res.getRdfId().toString();
        }
        if ( res.getTitle() != null && ! res.getTitle().isEmpty() ) {
            r.Title = res.getTitle().get( 0 );
        }
        if ( res.getHasLocation() != null && ! res.getHasLocation().isEmpty() ) {
            Entity ent = res.getHasLocation().get( 0 );
            if ( ent.getDescription() != null && ! ent.getDescription().isEmpty() ) {
                r.Location = ent.getDescription().get( 0 );
            }
        }
        if ( res.getBibliographicCitation() != null && ! res.getBibliographicCitation().isEmpty() ) {
            r.Citation = res.getBibliographicCitation().get( 0 );
        }
        if ( res.getDescription() != null && ! res.getDescription().isEmpty() ) {
            r.Description = res.getDescription().get( 0 );
        }
    }



    public static void addContributor( Contributor cont, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );

        Agent agent;
        if ( "Person".equals( cont.Type ) ) {
            agent = new SocialPersonImpl();
        } else {
            agent = new OrganizationImpl();
        }
        if ( cont.Address != null ) {
            agent.addAddress( cont.Address );
        }
        if ( cont.Contact != null ) {
            agent.addContactInformation( cont.Contact );
        }
        if ( cont.Name != null ) {
            agent.addAgentName( cont.Name );
        }
        if ( "Author".equals( cont.Role ) ) {
            dok.addAuthor( agent );
        } else if ( "Editor".equals( cont.Role ) ) {
            dok.addEditor( agent );
        } else if ( "Endorser".equals( cont.Role ) ) {
            dok.addEndorser( agent );
        } else if ( "Reviewer".equals( cont.Role ) ) {
            dok.addReviewer( agent );
        }
    }

    public static void addCoverage( models.metadata.Coverage cv, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );

        ClinicalCoverage cover = new ClinicalCoverageImpl();
        dok.addApplicability( cover );

        cover.addCoverageType( cv.Type );

        ConceptCode code = new ConceptCodeImpl();
        if ( cv.Code != null ) {
            code.addCode( cv.Code );
        }
        if ( cv.CodeSet != null ) {
            code.addCodeSystem( cv.CodeSet );
        }
        if ( cv.Description != null ) {
            code.addLabel( cv.Description );
        }

        cover.addCoveredConcept( code );
    }

    public static void addPublisher( Contributor cont, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );

        Agent agent;
        if ( "Person".equals( cont.Type ) ) {
            agent = new SocialPersonImpl();
        } else {
            agent = new OrganizationImpl();
        }
        if ( cont.Address != null ) {
            agent.addAddress( cont.Address );
        }
        if ( cont.Contact != null ) {
            agent.addContactInformation( cont.Contact );
        }
        if ( cont.Name != null ) {
            agent.addAgentName( cont.Name );
        }
        dok.addPublisher( agent );
    }

    public static void addUsageRights( UsageTerm term, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );

        RightsDeclaration decl = new RightsDeclarationImpl();
        dok.addUsageTerms( decl );


        Agent agent;
        if ( "Person".equals( term.Type ) ) {
            agent = new SocialPersonImpl();
        } else {
            agent = new OrganizationImpl();
        }
        if ( term.Name != null ) {
            agent.addAgentName( term.Name );
        }

        decl.addAuthor( agent );

        if ( term.Permissions != null ) {
            RightsStatement stat = new RightsStatementImpl();
            stat.addLicenseTerms( term.Permissions );
            decl.addAccessRights( stat );
        }

        if ( term.AssertedRights != null ) {
            RightsStatement stat = new RightsStatementImpl();
            stat.addLicenseTerms( term.AssertedRights );
            decl.addRights( stat );
        }
    }

    public static void setArtifactDocumentation( String doc, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );
        dok.getDescription().clear();
        dok.getDescription().add( doc );
    }

    public static void addRelatedResource( Resource res, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );
        KnowledgeResource kr = new KnowledgeResourceImpl();

        if ( res.Title != null ) {
            kr.addTitle( res.Title );
        }
        if ( res.Location != null ) {
            Location loc = new LocationImpl();
            loc.addDescription( res.Location );
            kr.addHasLocation( loc );
        }
        if ( res.Citation != null ) {
            kr.addBibliographicCitation( res.Citation );
        }
        if ( res.Description != null ) {
            kr.addDescription( res.Description );
        }

        if ( "AssociatedResource".equals( res.Type ) ) {
            dok.addAssociatedResource( kr );
        } else if ( "AdaptedFrom".equals( res.Type ) ) {
            dok.addAdaptedFrom( kr );
        } else if ( "DependsOn".equals( res.Type ) ) {
            dok.addDependsOn( kr );
        } else if ( "DerivedFrom".equals( res.Type ) ) {
            dok.addDerivedFrom( kr );
        } else if ( "SimilarTo".equals( res.Type ) ) {
            dok.addSimilarTo( kr );
        } else if ( "VersionOf".equals( res.Type ) ) {
            dok.addVersionOf( kr );
        }
    }

    public static void addSupportingEvidence( SupportingResource res, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );
        Evidence evidence = new EvidenceImpl();

        KnowledgeResource kr = new KnowledgeResourceImpl();
        evidence.addSupportingResource( kr );
        if ( res.Title != null ) {
            kr.addTitle( res.Title );
        }
        if ( res.Location != null ) {
            Location loc = new LocationImpl();
            loc.addDescription( res.Location );
            kr.addHasLocation( loc );
        }
        if ( res.Citation != null ) {
            kr.addBibliographicCitation( res.Citation );
        }
        if ( res.Description != null ) {
            kr.addDescription( res.Description );
        }

        if ( res.Quality != null ) {
            ConceptCode cd = new ConceptCodeImpl();
            cd.addCode( res.Quality );
            if ( res.QualityCodeSystem != null ) {
                cd.addCodeSystem( res.QualityCodeSystem );
            }
            if ( res.QualityLabel != null ) {
                cd.addLabel( res.QualityLabel );
            }
            evidence.addQualityOfEvidence( cd );
        }
        if ( res.Strength != null ) {
            ConceptCode cd = new ConceptCodeImpl();
            cd.addCode( res.Strength );
            if ( res.StrengthCodeSystem != null ) {
                cd.addCodeSystem( res.StrengthCodeSystem );
            }
            if ( res.StrengthLabel != null ) {
                cd.addLabel( res.StrengthLabel );
            }
            evidence.addStrengthOfReccomendation( cd );
        }
    }




    public static boolean removeContributor( Contributor cont, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );

        List<Agent> src = Collections.emptyList();
        if ( "Author".equals( cont.Role ) ) {
            src = dok.getAuthor();
        } else if ( "Editor".equals( cont.Role ) ) {
            src = dok.getEditor();
        } else if ( "Endorser".equals( cont.Role ) ) {
            src = dok.getEndorser();
        } else if ( "Reviewer".equals( cont.Role ) ) {
            src = dok.getReviewer();
        }

        for ( Agent ag : src ) {
            if ( ag.getRdfId().toString().equals( cont.Id ) ) {
                src.remove( ag );
                return true;
            }
        }
        return false;
    }


    public static boolean removePublisher( Contributor cont, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );

        for ( Agent ag : dok.getPublisher() ) {
            if ( ag.getRdfId().toString().equals( cont.Id ) ) {
                dok.removePublisher( ag );
            return true;
            }
        }
        return false;
    }


    public static boolean removeCoverage( models.metadata.Coverage cover, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );

        for ( Coverage c : dok.getApplicability() ) {
            if ( c.getRdfId().toString().equals( cover.Id ) ) {
                dok.removeApplicability( c );
                return true;
            }
        }
        return false;
    }

    public static boolean removeUsageRights( UsageTerm term, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );

        for ( RightsDeclaration decl : dok.getUsageTerms() ) {
            if ( decl.getRdfId().toString().equals( term.Id ) ) {
                dok.removeApplicability( decl );
                return true;
            }
        }
        return false;
    }

    public static boolean removeSupportingEvidence( SupportingResource res, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );

        for ( Evidence evidence : dok.getSupportingEvidence() ) {
            if ( evidence.getRdfId().toString().equals( res.Id ) ) {
                dok.removeSupportingEvidence( evidence );
                return true;
            }
        }
        return false;
    }

    public static boolean removeRelatedResource( Resource res, String id ) {
        HeDKnowledgeDocument dok = core.getArtifact( id );

        List<KnowledgeResource> src = Collections.emptyList();

        if ( "AssociatedResource".equals( res.Type ) ) {
            src = dok.getAssociatedResource();
        } else if ( "AdaptedFrom".equals( res.Type ) ) {
            src = dok.getAdaptedFrom();
        } else if ( "DependsOn".equals( res.Type ) ) {
            src = dok.getDependsOn();
        } else if ( "DerivedFrom".equals( res.Type ) ) {
            src = dok.getDerivedFrom();
        } else if ( "SimilarTo".equals( res.Type ) ) {
            src = dok.getSimilarTo();
        } else if ( "VersionOf".equals( res.Type ) ) {
            src = new ArrayList<KnowledgeResource>();
            for ( Thing t : dok.getVersionOf() ) {
                if ( t instanceof KnowledgeResource ) {
                    src.add( (KnowledgeResource) t );
                }
            }
        }

        for ( KnowledgeResource kr : src ) {
            if ( kr.getRdfId().toString().equals( res.Id ) ) {
                src.remove( kr );
                return true;
            }
        }
        return false;
    }




    /************************************************************************************************************/
    /* EXPRESSION APIs */
    /************************************************************************************************************/





    public static Map<String,String> getNamedExpressions() {
        return core.getNamedExpressions();
    }

    public static byte[] getNamedExpression( String expressionIri ) {
        return core.getNamedExpression( expressionIri );
    }

    public static boolean deleteNamedExpression( String expressionIri ) {
        return core.deleteNamedExpression( expressionIri );
    }

    public static String cloneNamedExpression( String expressionIri ) {
        return core.cloneNamedExpression( expressionIri );
    }

    public static void updateNamedExpression( String expressionIRI, String exprName, byte[] doxBytes ) {
        core.updateNamedExpression( expressionIRI, exprName, doxBytes );
    }



    static String createUUID()
    {
        return UUID.randomUUID().toString();
    }

    public static PrimitiveInst createPrimitiveInst(String ruleId,
                                                    String templateId)
    {
        Rule rule = ModelHome.getArtifact( ruleId );

        PrimitiveTemplate template = ModelHome.getPrimitiveTemplate( templateId );

        PrimitiveInst inst = template.createInst();
//        PrimitiveInst inst = new PrimitiveInst();
//        inst.type = template;
//        inst.id = createUUID();

        rule.primitives.add( inst );

        return inst;
    }

    /**
     * new
     */
    public static PrimitiveInst createPrimitiveInst(String templateId) {
        //TODO pass actual values from form
        String name = "foo";
        Map<String,Map<String,Object>> parameterValues = new HashMap<String,Map<String,Object>>();

        PrimitiveTemplate templ = templateCache.get( templateId );
        for ( String pid : templ.parameterIds ) {
            Map<String,Object> details = new HashMap<String,Object>();
            ParameterType param = templ.getParameter( pid );
            for ( ElementType elem : param.elements ) {
                details.put( elem.name, "TODO" );
            }
            parameterValues.put( pid, details );
        }

        core.instantiateTemplate( templateId, name, parameterValues );

        return null;
    }

    /**
     * new
     */
    public static PrimitiveTemplate getPrimitiveTemplateHed(final String id)
    {
        TemplateList tList = createJavaInstanceFromJsonFile( TemplateList.class );

        HedTypeList pList = createJavaInstanceFromJsonFile( HedTypeList.class );

        PrimitiveTemplate selectedTemplate = null;
        for ( PrimitiveTemplate t : tList.templates )
        {
            if ( t.templateId.equals( id ) )
            {
                selectedTemplate = t;
            }
        }
        spliceInHedTypes( selectedTemplate, pList );

        return selectedTemplate;
    }

    public static PrimitiveTemplate getPrimitiveTemplate(final String key)
    {
//        TemplateList tList = createJavaInstanceFromJsonFile( TemplateList.class );
//
//        ParameterList pList = createJavaInstanceFromJsonFile( ParameterList.class );
//
//        PrimitiveTemplate selectedTemplate = null;
//        for ( PrimitiveTemplate t : tList.templates )
//        {
//            if ( t.templateId.equals( id ) )
//            {
//                selectedTemplate = t;
//            }
//        }
//        spliceInParameters( selectedTemplate, pList );

        return templateCache.get( key );
    }

    private static void spliceInParameters(final PrimitiveTemplate selectedTemplate,
                                           final ParameterList allParameters)
    {
        if ( selectedTemplate == null )
        {
            return;
        }
        for ( String paramId : selectedTemplate.parameterIds )
        {
            ParameterType p = findParameter( paramId, allParameters );
            if ( p != null )
            {
                selectedTemplate.parameters.add( p );
            }
        }
    }

    static ParameterType findParameter(final String paramId,
                                       final ParameterList allParameters)
    {
        for ( ParameterType p : allParameters.parameters )
        {
            if ( p.key.equals( paramId ) )
            {
                return p;
            }
        }
        return null;
    }

    private static void spliceInHedTypes(final PrimitiveTemplate selectedTemplate,
                                         final HedTypeList allHedTypes)
    {
        if ( selectedTemplate == null ) {
            return;
        }
        for ( ParameterType p : selectedTemplate.parameters ) {
            HedType hedType = findHedType( p.hedTypeName, allHedTypes );

            if ( hedType != null ) {
                p.hedType = hedType;
                for ( ElementType eType : hedType.elements )
                {
                    p.elements.add( eType );
                }
            }
        }
    }

    static HedType findHedType(final String typeName,
                               final HedTypeList allTypes) {
        for ( HedType p : allTypes.hedTypes ) {
//            if ( p.hedTypeName.equals( typeName ) )
            if ( p.name.equals( typeName ) ) {
                return p;
            }
        }
        return null;
    }

    //========================================================================================

    public static String jsonFileForClass(final Class<?> aClass)
    {
        return jsonFileForClass( aClass.getSimpleName() );
    }

    public static String jsonFileForClass(final String aClassName)
    {
        return "public/data/" + aClassName + ".json";
    }

    /**
     * General factory method for creating Sharp Java instance of a particular class. Loads the JSON
     * from a file with a name corresponding to the Class name (e.g., for ParameterList, would load file
     * /public/data/ParameterList.json.
     *
     * Currently only files for TemplateList and ParameterList.
     */
    public static <M> M createJavaInstanceFromJsonFile(final Class<M> aClass) throws ModelDataFileNotFoundException, ConvertJsonToJavaException {
        String jsonDataResourcePath = jsonFileForClass( aClass );
        System.out.println( "jsonDataResourcePath = " + jsonDataResourcePath );

        final URL urlParameters = Resources.getResource( jsonDataResourcePath );

        final String jsonText;
        try
        {
            jsonText = Resources.toString( urlParameters, Charsets.UTF_8 );
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new ModelDataFileNotFoundException( e );
        }
        try
        {
            JsonNode jsonNode = Json.parse( jsonText );
//            System.out.println( "fetched value (json) = |" + jsonValue + "|" );

            Object o;
            o = Json.fromJson( jsonNode, aClass );

            return (M) o;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ConvertJsonToJavaException( ex );
        }
    }


    private static Map<String,PrimitiveTemplate> templateCache = new HashMap<String,PrimitiveTemplate>();

    public static TemplateList getTemplateList( String category ) {
        System.out.println( "ModelHome was asked a template list" );
        TemplateList templateList = new TemplateList();
        templateCache.clear();

        Set<String> templateIds = core.getTemplateIds( category );

        for ( String templateId : templateIds ) {
            Map<String,Object> templateDetails = core.getTemplateInfo( templateId );
            PrimitiveTemplate template = new PrimitiveTemplate();
            template.templateId = (String) templateDetails.get( "templateId" );
            template.key = template.templateId.substring( template.templateId.lastIndexOf( "#" ) + 1 );
            template.name = (String) templateDetails.get( "name" );
            template.category = (String) templateDetails.get( "category" );
            template.group = (String) templateDetails.get( "group" );
            template.description = (String) templateDetails.get( "description" );
            template.example = (String) templateDetails.get( "example" );
            template.parameterIds = (List<String>) templateDetails.get( "parameterIds" );
            template.parameters = rebuildParameterInfo( (Map<String,Map<String,Object>>) templateDetails.get( "parameterData" ) );

            spliceInHedTypes( template, hedTypeList );

            templateCache.put( template.key, template );
            templateList.addTemplate( template );
        }

        System.out.println( "Template list from the home " + templateList );
        return templateList;
    }



    static HedTypeList hedTypeList = initHeDTypeList();

    private static HedTypeList initHeDTypeList() {
        HedTypeList typeList = createJavaInstanceFromJsonFile( HedTypeList.class );

        for ( HedType type : typeList.hedTypes ) {
            if ( "CodeLiteral".equals( type.name ) ) {
                ElementType codeSystem = type.getElement( "codeSystem" );
                //  codeSystem.selectionChoices = lookupCodeSystems();
            }
        }

        return typeList;
    }

    private static List<String> lookupCodeSystems() {
        CodeSystemCatalogEntryDirectory codesSystems = new Cts2RestClient( true ).getCts2Resource( "http://localhost:8080/cts2framework/codesystems", CodeSystemCatalogEntryDirectory.class );
        List<String> codeSystemNames = new ArrayList<>( codesSystems.getEntryCount() );
        for ( CodeSystemCatalogEntrySummary entry : codesSystems.getEntry() ) {
            codeSystemNames.add( entry.getCodeSystemName() );
        }
        return codeSystemNames;
    }


    private static List<ParameterType> rebuildParameterInfo( Map<String, Map<String,Object>> parameterData ) {
        ArrayList<ParameterType> parameterTypes = new ArrayList<ParameterType>();
        for ( String key : parameterData.keySet() ) {
            Map<String,Object> pData = parameterData.get( key );
            ParameterType param = new ParameterType();

            param.key = key;
            param.name = (String) pData.get( "name" );
            param.label = (String) pData.get( "label" );
            param.description = (String) pData.get( "description" );
            param.hedTypeName = (String) pData.get( "typeName" );
            param.expressionChoices = (List<String>) pData.get( "expressionChoices" );

            parameterTypes.add( param );
        }
        return parameterTypes;
    }










    public static Map<String, String> getDomainClasses() {
        Map<String,String> dk = core.getDomainClasses();
        System.out.println( "Retrieved domain klasses" );
        return dk;
    }

    public static Map<String, String> getDomainProperties() {
        return core.getDomainProperties();
    }
    public static Map<String, String> getDomainProperties( String klassId ) {
        return core.getDomainProperties( klassId );
    }



}
