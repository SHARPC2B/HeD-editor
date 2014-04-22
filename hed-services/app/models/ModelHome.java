package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import edu.asu.sharpc2b.hed.api.EditorCore;
import edu.asu.sharpc2b.hed.impl.EditorCoreImpl;
import edu.asu.sharpc2b.hed.impl.HeDArtifactData;
import edu.asu.sharpc2b.metadata.ClinicalCoverage;
import edu.asu.sharpc2b.metadata.ClinicalCoverageImpl;
import edu.asu.sharpc2b.metadata.Coverage;
import edu.asu.sharpc2b.metadata.Evidence;
import edu.asu.sharpc2b.metadata.EvidenceImpl;
import edu.asu.sharpc2b.metadata.InlineResource;
import edu.asu.sharpc2b.metadata.KnowledgeResource;
import edu.asu.sharpc2b.metadata.KnowledgeResourceImpl;
import edu.asu.sharpc2b.metadata.RightsDeclaration;
import edu.asu.sharpc2b.metadata.RightsDeclarationImpl;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.skos_ext.ConceptCode;
import edu.asu.sharpc2b.skos_ext.ConceptCodeImpl;
import edu.asu.sharpc2b.templates.Parameter;
import edu.asu.sharpc2b.templates.Template;
import edu.mayo.cts2.framework.core.client.Cts2RestClient;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntry;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntryDirectory;
import edu.mayo.cts2.framework.model.codesystem.CodeSystemCatalogEntrySummary;
import edu.mayo.cts2.framework.model.entity.EntityDirectory;
import edu.mayo.cts2.framework.model.entity.EntityDirectoryEntry;
import models.ex.ConvertJsonToJavaException;
import models.ex.ModelDataFileNotFoundException;
import models.metadata.Contributor;
import models.metadata.Resource;
import models.metadata.SupportingResource;
import models.metadata.UsageTerm;
import org.ontologydesignpatterns.ont.dul.dul.Entity;
import org.ontologydesignpatterns.ont.dul.dul.OrganizationImpl;
import org.ontologydesignpatterns.ont.dul.dul.SocialPerson;
import org.ontologydesignpatterns.ont.dul.dul.SocialPersonImpl;
import org.purl.dc.terms.Agent;
import org.purl.dc.terms.Location;
import org.purl.dc.terms.LocationImpl;
import org.purl.dc.terms.RightsStatement;
import org.purl.dc.terms.RightsStatementImpl;
import org.springframework.web.client.HttpClientErrorException;
import play.libs.Json;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * User: rk Date: 8/19/13 Package: models
 */
public class ModelHome {


    private static EditorCore core = EditorCoreImpl.getInstance();

    //TODO Read from configuration
    private final static String cts2Base = "http://localhost:8080/cts2framework";

    private static HedTypeList hedTypeList = initHeDTypeList();



    /************************************************************************************************************/
    /* ARTIFACT APIs */
    /************************************************************************************************************/

    public static List<String> getAvailableArtifacts() {
        return core.getAvailableArtifacts();
    }

    public static Rule getArtifact( String id ) {
        final HeDArtifactData dok = core.getArtifactData( id );
        Rule rule = new Rule();
        rule.ruleId = dok.getArtifactId();
        rule.Name = dok.getTitle() != null ? dok.getTitle() : "[no title]";
        rule.Description = dok.getKnowledgeDocument().getDescription().isEmpty() ? "" : dok.getKnowledgeDocument().getDescription().get( 0 );
        return rule;
    }

    public static Rule getCurrentArtifact() {
        final HeDArtifactData dok = core.getCurrentArtifactData();
        if ( dok == null ) {
            Rule rule = new Rule();
            rule.Description = "NO RULE - Open or Create";
            return rule;
        } else {
            Rule rule = new Rule();
            rule.ruleId = dok.getArtifactId();
            rule.Name = dok.getTitle() != null ? dok.getTitle() : "[no title]";
            rule.Description = dok.getKnowledgeDocument().getDescription().isEmpty() ? "" : dok.getKnowledgeDocument().getDescription().get( 0 );
            rule.Status = dok.getKnowledgeDocument().getStatus().isEmpty() ? "Draft" : dok.getKnowledgeDocument().getStatus().get( 0 );
            return rule;
        }
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
                c.Role = "Author";
                fillAgent( a, c );
                contris.add( c );
            }
            for ( Agent a : dok.getEditor() ) {
                Contributor c = new Contributor();
                c.Role = "Editor";
                fillAgent( a, c );
                contris.add( c );
            }
            for ( Agent a : dok.getEndorser() ) {
                Contributor c = new Contributor();
                c.Role = "Endorser";
                fillAgent( a, c );
                contris.add( c );
            }
            for ( Agent a : dok.getReviewer() ) {
                Contributor c = new Contributor();
                c.Role = "Reviewer";
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
        if ( dok.getApplicability() == null || dok.getApplicability().isEmpty() ) {
            return covers;
        }
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
        if ( dok.getPublisher() == null || dok.getPublisher().isEmpty() ) {
            return pubs;
        }
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
        if ( dok.getUsageTerms() == null || dok.getUsageTerms().isEmpty() ) {
            return terms;
        }

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
                if ( ! stat.getLicenseTerms().isEmpty() ) {
                    term.Permissions = stat.getLicenseTerms().get( 0 );
                }
            }
            if ( decl.getRights() != null && ! decl.getRights().isEmpty() ) {
                RightsStatement stat = decl.getRights().get( 0 );
                if ( ! stat.getLicenseTerms().isEmpty() ) {
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

        if ( dok.getAssociatedResource() != null && ! dok.getAssociatedResource().isEmpty() ) {
            for ( KnowledgeResource res : dok.getAssociatedResource() ) {
                Resource r = new Resource();
                r.Type = "AssociatedResource";
                fillResource( res, r );
                resources.add( r );
            }
        }
        if ( dok.getAdaptedFrom() != null && ! dok.getAdaptedFrom().isEmpty() ) {
            for ( KnowledgeResource res : dok.getAdaptedFrom() ) {
                Resource r = new Resource();
                r.Type = "AdaptedFrom";
                fillResource( res, r );
                resources.add( r );
            }
        }
        if ( dok.getDependsOn() != null && ! dok.getDependsOn().isEmpty() ) {
            for ( KnowledgeResource res : dok.getDependsOn() ) {
                Resource r = new Resource();
                r.Type = "DependsOn";
                fillResource( res, r );
                resources.add( r );
            }
        }
        if ( dok.getDerivedFrom() != null && ! dok.getDerivedFrom().isEmpty() ) {
            for ( KnowledgeResource res : dok.getDerivedFrom() ) {
                Resource r = new Resource();
                r.Type = "DerivedFrom";
                fillResource( res, r );
                resources.add( r );
            }
        }
        if ( dok.getSimilarTo() != null && ! dok.getSimilarTo().isEmpty() ) {
            for ( KnowledgeResource res : dok.getSimilarTo() ) {
                Resource r = new Resource();
                r.Type = "SimilarTo";
                fillResource( res, r );
                resources.add( r );
            }
        }
        if ( dok.getVersionOf() != null && ! dok.getVersionOf().isEmpty() ) {
            for ( KnowledgeResource res : dok.getVersionOf() ) {
                Resource r = new Resource();
                r.Type = "VersionOf";
                fillResource( res, r );
                resources.add( r );
            }
        }
        return resources;
    }

    public static List<SupportingResource> getSupportingEvidence( String id ) {
        List<SupportingResource> resources = new ArrayList<SupportingResource>();
        HeDKnowledgeDocument dok = core.getArtifact( id );
        if ( dok.getSupportingEvidence() == null || dok.getSupportingEvidence().isEmpty() ) {
            return resources;
        }
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
            src = dok.getVersionOf();
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
    /**
     ***********************************************************************************************************/





    public static Map<String,String> getNamedExpressions( String returnType ) {
        return core.getNamedExpressions( returnType );
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

    public static String updateNamedExpression( String expressionIRI, String exprName, byte[] doxBytes ) {
        return core.updateNamedExpression( expressionIRI, exprName, doxBytes );
    }

    public static byte[] getConditionExpression() {
        return core.getLogicExpression();
    }

    public static byte[] setConditionExpression( byte[] conditionExpression ) {
        return core.updateLogicExpression( conditionExpression );
    }

    public static byte[] getTriggers() {
        return core.getTriggers();
    }

    public static byte[] setTriggers( byte[] triggerExpression ) {
        return core.updateTriggers( triggerExpression );
    }

    public static byte[] getActions() {
        return core.getActions();
    }

    public static byte[] setActions( byte[] triggerExpression ) {
        return core.updateActions( triggerExpression );
    }




    /************************************************************************************************************/
    /* DOMAIN MODEL APIs */
    /**
     ***********************************************************************************************************/


    public static Map<String, String> getDomainClasses() {
        Map<String,String> dk = core.getDomainClasses();
        return dk;
    }

    public static Map<String, String> getDomainProperties() {
        return core.getDomainProperties();
    }

    public static Map<String, String> getDomainProperties( String klassId ) {
        return core.getDomainProperties( klassId );
    }

    public static String getDomainClassHierarchyDescription() {
        return core.getDomainClassHierarchyDescription();
    }


    public static void updateBasicInfo( Rule rule ) {
        core.updateBasicInfo( rule.ruleId, rule.Name, rule.Description, rule.Status, rule.Type );
    }

    public static Map<String,String> getUsedDomainClasses( String id ) {
        HeDArtifactData data = core.getArtifactData( id );
        if ( data == null ) {
            return Collections.emptyMap();
        }
        return data.getUsedDomainClasses();
    }

    public static void addUsedDomainClass( String id ) {
        core.addUsedDomainClass( id );
    }



    /************************************************************************************************************/
    /* DOMAIN MODEL APIs */
    /**
     ***********************************************************************************************************/

    public static List<String> getCodeSystems() {
        return lookupCodeSystems();
    }












    /************************************************************************************************************/
    /* TEMPLATE APIs */
    /**
     ***********************************************************************************************************/






    public static TemplateList getTemplateList( String category ) {
        TemplateList templateList = new TemplateList();

        Set<String> templateIds = core.getTemplateIds( category );

        for ( String templateId : templateIds ) {
            PrimitiveTemplate template = new PrimitiveTemplate();
            Template templateDetails = core.getTemplateInfo( templateId );

            fillBasicTemplateDetails( templateId, template, templateDetails );

            templateList.addTemplate( template );
        }

        return templateList;
    }

    public static TemplateList getTemplateList( String category, String codeSystem, String code ) {
        TemplateList templateList = new TemplateList();

        Set<String> templateIds = core.getTemplateIds( category );

        for ( String templateId : templateIds ) {
            PrimitiveTemplate template = new PrimitiveTemplate();
            Template templateDetails = core.getTemplateInfo( templateId );

            fillBasicTemplateDetails( templateId, template, templateDetails );

            if ( applyCodeRestrictions( template, templateDetails, codeSystem, code ) ) {
                templateList.addTemplate( template );
            }
        }

        System.out.println( "Template list from the home " + templateList );
        return templateList;
    }

    private static boolean applyCodeRestrictions( PrimitiveTemplate template, Template templateDetails, String codeSystem, String code ) {
        boolean found = false;
        for ( Parameter parm : templateDetails.getHasParameter() ) {
            if ( ! parm.getConstraint().isEmpty() ) {
                for ( String constr : parm.getConstraint() ) {
                    if ( isCodeHierarchyConstraint( constr ) ) {
                        EntityDirectory space = getCodespace( constr.trim() );
                        for ( EntityDirectoryEntry entry : space.getEntry() ) {
                            if ( entry.getName().getNamespace().equals( codeSystem )
                               && entry.getName().getName().equals( code ) ) {
                                found = true;

                                for ( Parameter param : templateDetails.getHasParameter() ) {
                                    template.parameterIds.add( param.getName().get( 0 ) );
                                }
                                template.parameters = rebuildParameterInfo( templateDetails.getHasParameter() );

                                for ( ParameterType p : template.parameters ) {
                                    if ( p.name.equals( parm.getName().get( 0 ) ) ) {
                                        p.getElement( "code" ).value = code;
                                        p.getElement( "codeSystem" ).value = codeSystem.toUpperCase();
                                        p.getElement( "displayValue" ).value = entry.getKnownEntityDescription()[0].getDesignation();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return found;
    }

    private static EntityDirectory getCodespace( String constr ) {
        int pos = constr.indexOf( ":" );
        String codeSystem = constr.substring( 0, pos );
        String code = constr.substring( pos + 1 );
        String req = cts2Base
                     + "/codesystem/"
                     + codeSystem
                     + "/version/"
                     + codeSystem.toUpperCase()     // TODO check this!
                     + "-LATEST/entity/"
                     + code
                     + "/children";

        EntityDirectory space = new Cts2RestClient( true ).getCts2Resource( req, EntityDirectory.class );
        return space;
    }

    private static boolean isCodeHierarchyConstraint( String constr ) {
        //TODO....
        return constr.contains( ":" );
    }

    private static void fillBasicTemplateDetails( String templateId, PrimitiveTemplate template, Template templateDetails ) {
        template.templateId = templateId;
        template.key = templateId;
        template.name = templateDetails.getName().get( 0 );
        template.description = templateDetails.getDescription().get( 0 );
        template.category = new ArrayList( templateDetails.getCategory() );
        template.group = templateDetails.getGroup().get( 0 );
    }


    public static PrimitiveTemplate getTemplateDetail( String id ) {
        PrimitiveTemplate template = new PrimitiveTemplate();
        Template templateDetails = core.getTemplateInfo( id );

        fillBasicTemplateDetails( id, template, templateDetails );

        for ( Parameter param : templateDetails.getHasParameter() ) {
            template.parameterIds.add( param.getName().get( 0 ) );
        }
        if ( template.parameters == null || template.parameters.isEmpty() ) {
            template.parameters = rebuildParameterInfo( templateDetails.getHasParameter() );
        }

        return template;
    }


    private static HedTypeList initHeDTypeList() {
        HedTypeList typeList = createJavaInstanceFromJsonFile( HedTypeList.class );

        for ( HedType type : typeList.hedTypes ) {
            if ( "CodeLiteral".equals( type.name ) ) {
                ElementType codeSystem = type.getElement( "codeSystem" );
                codeSystem.selectionChoices = lookupCodeSystems();
                //codeSystem.value = codeSystem.selectionChoices.iterator().next();
            }
        }

        return typeList;
    }

    private static List<String> lookupCodeSystems() {
        CodeSystemCatalogEntryDirectory codesSystems = new Cts2RestClient( true ).getCts2Resource( cts2Base + "/codesystems", CodeSystemCatalogEntryDirectory.class );
        List<String> codeSystemNames = new ArrayList<>( codesSystems.getEntryCount() );
        for ( CodeSystemCatalogEntrySummary entry : codesSystems.getEntry() ) {
            codeSystemNames.add( entry.getCodeSystemName() );
        }
        return codeSystemNames;
    }


    private static List<ParameterType> rebuildParameterInfo( List<Parameter> params ) {
        List<ParameterType> parameterTypes = new ArrayList<ParameterType>();
        for ( Parameter p : params ) {
            ParameterType param = new ParameterType();

            param.key = p.getName().get( 0 );
            param.name = p.getName().get( 0 );
            param.label = p.getLabel().get( 0 );
            param.description = p.getDescription().get( 0 );
            param.hedTypeName = p.getTypeName().get( 0 );
            param.optional = p.getOptional().get( 0 );
            param.multiple = p.getMultiple().get( 0 );

            param.expressionChoices = new ArrayList( p.getCompatibleExpression() );

            param.operationChoices = new ArrayList( p.getCompatibleOperation() );
            param.selectedOperation = param.operationChoices.get( 0 );

            HedType hedType = findHedType( param.hedTypeName, hedTypeList );

            if ( hedType != null ) {
                param.hedType = hedType;
                for ( ElementType eType : hedType.elements ) {
                    param.elements.add( eType.clone() );
                }
            }

            if ( ! p.getDefaultValue().isEmpty() ) {
                parseAndInjectDefaultValue( param, p.getDefaultValue().get( 0 ) );
            }

            parameterTypes.add( param );
        }
        return parameterTypes;
    }

    private static void parseAndInjectDefaultValue( ParameterType param, String value ) {
        StringTokenizer tok = new StringTokenizer( value, ";" );
        while ( tok.hasMoreTokens() ) {
            String pair = tok.nextToken().trim();
            int eqIndex = pair.indexOf( "=" );
            String elem = pair.substring( 0, eqIndex ).trim();
            String vals = pair.substring( eqIndex + 1 );

            param.getElement( elem ).value = vals;
            param.getElement( elem ).initialValue = vals;

        }
        if ( "CodeLiteral".equals( param.hedTypeName ) ) {

            try {
                String code = (String) param.getElement( "code" ).value;
                CodeSystemCatalogEntry entry = new Cts2RestClient( true ).getCts2Resource( cts2Base + "/entity/" + code, CodeSystemCatalogEntry.class );
                param.getElement( "label" ).value = entry.getFormalName();
            } catch ( HttpClientErrorException e ) {
                System.err.println( "Warning : code " + value + " could not be looked up, only partial information will be available" );
            }
        }
    }


    public static List<String> verifyTemplate( PrimitiveTemplate templ ) {
        List<String> errors = new ArrayList<String>();
        Template templateDetail = core.getTemplateInfo( templ.templateId );
        for ( Parameter param : templateDetail.getHasParameter() ) {
            if ( ! param.getConstraint().isEmpty() ) {
                for ( String constr : param.getConstraint() ) {
                    String error = applyConstraint( param, constr, templ );
                    if ( error != null ) {
                        errors.add( error );
                    }
                }
            }
            if ( ! param.getOptional().get( 0 ) ) {
                ParameterType ptype = extractParameter( templ.parameters, param.getName().get( 0 ) );
                boolean found = true;
                for ( ElementType el : ptype.elements ) {
                    if ( el.value == null ) {
                        found = false;
                        break;
                    }
                }
                if ( ! found ) {
                    errors.add( "Parameter " + ptype.name + " is mandatory, but not all values have been assigned " );
                }
            }
        }
        return errors;
    }

    private static String applyConstraint( Parameter param, String constr, PrimitiveTemplate templ ) {
        if ( isCodeHierarchyConstraint( constr ) ) {
            ParameterType p = extractParameter( templ.parameters, param.getName().get( 0 ) );
            EntityDirectory space = getCodespace( constr );

            String actualCode = (String) p.getElement( "code" ).value;
            String actualCodeSystem = (String) p.getElement( "codeSystem" ).value;

            boolean found = false;
            for ( EntityDirectoryEntry entry : space.getEntry() ) {
                if ( entry.getName().getNamespace().toUpperCase().equals( actualCodeSystem )
                     && entry.getName().getName().equals( actualCode ) ) {
                    found = true;
                    break;
                }
            }
            if ( ! found ) {
                return "Code " + actualCodeSystem + ":" + actualCode + " is not allowed in parameter " + p.label;
            }
        }
        return null;
    }

    private static ParameterType extractParameter( List<ParameterType> parameters, String pname ) {
        for ( ParameterType p : parameters ) {
            if ( p.name.equals( pname ) ) {
                return p;
            }
        }
        return null;
    }


    public static PrimitiveInst createPrimitiveInst( String ruleId, String templateId ) {
        /*
        Rule rule = ModelHome.getArtifact( ruleId );

        PrimitiveTemplate template = ModelHome.getPrimitiveTemplate( templateId );

        PrimitiveInst inst = template.createInst();
        rule.primitives.add( inst );

        return inst;*/
        return null;
    }

    /**
     * new
     */
    public static PrimitiveInst createPrimitiveInst(String templateId) {
        /*
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
        */
        return null;
    }




    static ParameterType findParameter( final String paramId,
                                        final ParameterList allParameters ) {
        for ( ParameterType p : allParameters.parameters ) {
            if ( p.key.equals( paramId ) ) {
                return p;
            }
        }
        return null;
    }


    static HedType findHedType(final String typeName,
                               final HedTypeList allTypes) {
        for ( HedType p : allTypes.hedTypes ) {
            if ( p.name.equals( typeName ) ) {
                return p;
            }
        }
        return null;
    }




    //========================================================================================

    public static String jsonFileForClass( final Class<?> aClass ) {
        return jsonFileForClass( aClass.getSimpleName() );
    }

    public static String jsonFileForClass( final String aClassName ) {
        return "public/data/" + aClassName + ".json";
    }

    /**
     * General factory method for creating Sharp Java instance of a particular class. Loads the JSON
     * from a file with a name corresponding to the Class name (e.g., for ParameterList, would load file
     * /public/data/ParameterList.json.
     *
     * Currently only files for TemplateList and ParameterList.
     */
    public static <M> M createJavaInstanceFromJsonFile( final Class<M> aClass )
            throws ModelDataFileNotFoundException, ConvertJsonToJavaException {
        String jsonDataResourcePath = jsonFileForClass( aClass );
        System.out.println( "jsonDataResourcePath = " + jsonDataResourcePath );

        final URL urlParameters = Resources.getResource( jsonDataResourcePath );

        final String jsonText;
        try {
            jsonText = Resources.toString( urlParameters, Charsets.UTF_8 );
        } catch (IOException e) {
            e.printStackTrace();
            throw new ModelDataFileNotFoundException( e );
        }
        try {
            JsonNode jsonNode = Json.parse( jsonText );
//            System.out.println( "fetched value (json) = |" + jsonValue + "|" );

            Object o;
            o = Json.fromJson( jsonNode, aClass );

            return (M) o;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ConvertJsonToJavaException( ex );
        }
    }


}
