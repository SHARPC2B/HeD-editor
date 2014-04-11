package edu.asu.sharpc2b.transform;

import com.clarkparsia.empire.annotation.RdfProperty;
import com.hp.hpl.jena.sparql.lib.iterator.Iter;
import edu.asu.sharpc2b.actions.AtomicAction;
import edu.asu.sharpc2b.actions.CompositeAction;
import edu.asu.sharpc2b.metadata.Coverage;
import edu.asu.sharpc2b.metadata.Evidence;
import edu.asu.sharpc2b.metadata.KnowledgeResource;
import edu.asu.sharpc2b.metadata.Site;
import edu.asu.sharpc2b.metadata.VersionedIdentifier;
import edu.asu.sharpc2b.ops.BinaryExpression;
import edu.asu.sharpc2b.ops.ClinicalRequestExpression;
import edu.asu.sharpc2b.ops.ClinicalRequestExpressionImpl;
import edu.asu.sharpc2b.ops.CreateExpression;
import edu.asu.sharpc2b.ops.DomainClass;
import edu.asu.sharpc2b.ops.DomainClassExpression;
import edu.asu.sharpc2b.ops.DomainPropertyExpression;
import edu.asu.sharpc2b.ops.IteratorExpression;
import edu.asu.sharpc2b.ops.LiteralExpression;
import edu.asu.sharpc2b.ops.NAryExpression;
import edu.asu.sharpc2b.ops.OperatorExpression;
import edu.asu.sharpc2b.ops.PropertyExpression;
import edu.asu.sharpc2b.ops.PropertyGetExpression;
import edu.asu.sharpc2b.ops.PropertySetExpression;
import edu.asu.sharpc2b.ops.SharpExpression;
import edu.asu.sharpc2b.ops.SingleIteratorExpression;
import edu.asu.sharpc2b.ops.TernaryExpression;
import edu.asu.sharpc2b.ops.UnaryExpression;
import edu.asu.sharpc2b.ops.VariableExpression;
import edu.asu.sharpc2b.ops_set.AndExpression;
import edu.asu.sharpc2b.ops_set.ListExpression;
import edu.asu.sharpc2b.ops_set.ObjectExpressionExpression;
import edu.asu.sharpc2b.prr.ComputerExecutableRule;
import edu.asu.sharpc2b.prr.Expression;
import edu.asu.sharpc2b.prr.NamedElement;
import edu.asu.sharpc2b.prr.ProductionRule;
import edu.asu.sharpc2b.prr.RuleAction;
import edu.asu.sharpc2b.prr.RuleCondition;
import edu.asu.sharpc2b.prr.RuleVariable;
import edu.asu.sharpc2b.prr.TypedElement;
import edu.asu.sharpc2b.prr.VariableImpl;
import edu.asu.sharpc2b.prr_sharp.HeDKnowledgeDocument;
import edu.asu.sharpc2b.skos_ext.ConceptCode;
import org.hl7.knowledgeartifact.r1.ClinicalRequest;
import org.ontologydesignpatterns.ont.dul.dul.Agent;
import org.ontologydesignpatterns.ont.dul.dul.InformationRealization;
import org.ontologydesignpatterns.ont.dul.dul.Organization;
import org.ontologydesignpatterns.ont.dul.dul.SocialPerson;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.sail.memory.model.MemURI;
import org.w3._2002._07.owl.Thing;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class OOwl2HedDumper implements HeDExporter {

    private static final String exprNS = "http://asu.edu/sharpc2b/ops-set#";

    public OOwl2HedDumper() {
        super();
    }

    public Document serialize( HeDKnowledgeDocument hed ) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware( true );

            Document dox = factory.newDocumentBuilder().newDocument();
            visit( hed, dox );

            return dox;
        } catch ( ParserConfigurationException e ) {
            e.printStackTrace();
        }
        return null;
    }

    private void visit( HeDKnowledgeDocument hed, Document dox ) {

        Element root = dox.createElement( "knowledgeDocument" );
        root.setAttribute( "xmlns", "urn:hl7-org:knowledgeartifact:r1" );
        root.setAttribute( "xmlns:dt", "urn:hl7-org:cdsdt:r2" );
        root.setAttribute( "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance" );

        visitMetadata( hed, dox, root);

        for ( ComputerExecutableRule rule : hed.getContains() ) {
            visitRule( rule, dox, root );
        }

        dox.appendChild( root );

    }

    private void visitRule( ComputerExecutableRule rule, Document dox, Element root ) {

        if ( rule instanceof ProductionRule ) {
            visitExternalData( (ProductionRule) rule, dox, root );
            visitExpressions( (ProductionRule) rule, dox, root );
            visitConditions( (ProductionRule) rule, dox, root );
            visitActions( (ProductionRule) rule, dox, root );
        }

    }

    private void visitActions( ProductionRule rule, Document dox, Element root ) {
        for ( RuleAction act : rule.getProductionRuleAction() ) {
            visitAction( act, dox, root );
        }
    }

    private void visitAction( RuleAction action, Document dox, Element root ) {
        if ( action instanceof CompositeAction ) {
            visitCompositeAction( (CompositeAction) action, dox, root );
        } else if ( action instanceof AtomicAction ) {
            visitSimpleAction( (AtomicAction) action, dox, root );
        }
    }

    private void visitCompositeAction( CompositeAction action, Document dox, Element root ) {
        Element actions = dox.createElement( "actionGroup" );
        Element sub = dox.createElement( "subElements" );

        visitTitle( action, dox, actions );
        visitDescription( action, dox, actions );
        visitEvidence( action, dox, actions );

        if ( ! action.getGroupSelection().isEmpty() ) {
            Element behavs = dox.createElement( "behaviors" );
                Element behav = dox.createElement( "behavior" );
                behav.setAttribute( "xsi:type", "GroupSelectionBehavior" );
                behav.setAttribute( "value", action.getGroupSelection().get( 0 ) );
            behavs.appendChild( behav );
            actions.appendChild( behavs );
        }

            for ( RuleAction act : action.getMemberAction() ) {
                visitAction( act, dox, sub );
            }

        actions.appendChild( sub );
        root.appendChild( actions );
    }

    private void visitEvidence( InformationRealization info, Document dox, Element root ) {
        for ( Evidence ev : info.getSupportingEvidence() ) {
            Element evidence = dox.createElement( "evidence" );
            for ( ConceptCode code : ev.getQualityOfEvidence() ) {
                visitCD( code, dox, evidence, "qualityOfEvidence" );
            }
            for ( ConceptCode code : ev.getStrengthOfReccomendation() ) {
                visitCD( code, dox, evidence, "strengthOfReccomendation" );
            }
            for ( KnowledgeResource res : ev.getSupportingResource() ) {
                visitKResource( res, dox, evidence );
            }
            root.appendChild( evidence );
        }
    }

    private void visitSimpleAction( AtomicAction action, Document dox, Element root ) {
        Element act = dox.createElement( "simpleAction" );
        act.setAttribute( "xsi:type", extractExpressionType( action.getClass() ) );

        SharpExpression sentence = action.getActionExpression().get( 0 ).getBodyExpression().get( 0 );

        visitTitle( action, dox, act );
        visitDescription( action, dox, act );
        visitEvidence( action, dox, act );



        visitExpression( null, sentence, act, "actionSentence", dox  );

        root.appendChild( act );
    }

    private void visitConditions( ProductionRule rule, Document dox, Element root ) {
        Element conds = dox.createElement( "conditions" );
        for ( RuleCondition cond : rule.getProductionRuleCondition() ) {
            Expression expr = cond.getConditionRepresentation().get( 0 );
            Element condElem = dox.createElement( "condition" );
            Element role = dox.createElement( "conditionRole" );

            SharpExpression sexpr = expr.getBodyExpression().get( 0 );

            visitExpression( null, sexpr, condElem, "logic", dox );

            role.setAttribute( "value", "ApplicableScenario" );
            condElem.appendChild( role );
            conds.appendChild( condElem );
        }
        root.appendChild( conds );
    }

    private void visitExpressions( ProductionRule rule, Document dox, Element root ) {
        Element expressions = dox.createElement( "expressions" );
        for ( RuleVariable var : rule.getProductionRuleBoundRuleVariable() ) {
            SharpExpression sexpr = var.getVariableFilterExpression().get( 0 ).getBodyExpression().get( 0 );

            if ( ! isExternalReq( sexpr )  ) {
                Element def = dox.createElement( "def" );
                def.setAttribute( "name", var.getName().get( 0 ) );
                    visitExpression( var, sexpr, def, "expression", dox );
                expressions.appendChild( def );
            }
        }
        root.appendChild( expressions );

    }

    private boolean isExternalReq( SharpExpression sexpr ) {
        // only true external requests point to the contextdata variable as a source
        if ( sexpr instanceof IteratorExpression ) {
            IteratorExpression iter = (IteratorExpression) sexpr;
            if ( ! iter.getSource().isEmpty() && iter.getSource().get( 0 ) instanceof edu.asu.sharpc2b.ops_set.ClinicalRequestExpression &&
                 ( (edu.asu.sharpc2b.ops_set.ClinicalRequestExpression) iter.getSource().get( 0 ) ).getScope().contains( "external" ) ) {
                return true;
            }
        }
        return false;
    }

    private void visitExternalData( ProductionRule rule, Document dox, Element root ) {
        Element external = dox.createElement( "externalData" );
        for ( RuleVariable var : rule.getProductionRuleBoundRuleVariable() ) {
            SharpExpression sexpr = var.getVariableFilterExpression().get( 0 ).getBodyExpression().get( 0 );
            if ( isExternalReq( sexpr ) ) {
                visitExternalRequest( var, dox, external, (IteratorExpression) sexpr );
            }
        }


        root.appendChild( external );
    }

    private void visitExternalRequest( TypedElement var, Document dox, Element external, IteratorExpression iter ) {
        Element def = dox.createElement( "def" );
        def.setAttribute( "name", var.getName().get( 0 ) );

        Element expr = dox.createElement( "expression" );
        visitClinicalRequest( var, expr, iter, dox );
        def.appendChild( expr );

        external.appendChild( def );
    }

    private void visitClinicalRequest( TypedElement var, Element expr, IteratorExpression iter, Document dox ) {
        edu.asu.sharpc2b.ops_set.ClinicalRequestExpression req = (edu.asu.sharpc2b.ops_set.ClinicalRequestExpression) iter.getSource().get( 0 );

        expr.setAttribute( "xsi:type", "ClinicalRequest" );
        try {
            fillProperties( expr, req, dox );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    private void fillProperties( Element elem, OperatorExpression expr, Document dox ) {
        try {
            Iterator<Statement> iter = expr.getInstanceTriples().iterator();
            while ( iter.hasNext() ) {
                Statement triple = iter.next();
                URI prop = triple.getPredicate();
                if ( exprNS.equals( prop.getNamespace() ) ) {
                    for ( Method m : expr.getClass().getMethods() ) {
                        if ( m.getAnnotation( RdfProperty.class ) != null && prop.toString().equals( m.getAnnotation( RdfProperty.class ).value() ) ) {
                            if ( Collection.class.isAssignableFrom( m.getReturnType() ) ) {
                                Collection coll = (Collection) m.invoke( expr );
                                for ( Object o : coll ) {
                                    fillArgument( elem, triple, o, dox );
                                }
                            } else {
                                Object o = m.invoke( expr );
                                fillArgument( elem, triple, o, dox );
                            }
                        }
                    }
                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void fillArgument( Element elem, Statement triple, Object o, Document dox ) {
        if ( triple.getObject() instanceof MemURI ) {
            if ( o instanceof Thing ) {
                String oid = ((Thing) o).getRdfId().value().toString();
                String tid = triple.getObject().stringValue();
                if ( oid.equals( tid ) && o instanceof SharpExpression ) {
                    visitExpression( null, (SharpExpression) o, elem, removeOverrides( triple.getPredicate().getLocalName() ), dox );
                }
            } else {
                System.err.println( "WARNING Found uri triple with non thing " + triple );
            }
        } else {
            elem.setAttribute( removeOverrides( triple.getPredicate().getLocalName() ), o.toString() );
        }

    }

    private String removeOverrides( String localName ) {
        if ( localName.indexOf( "_" ) > 1 ) {
            return localName.substring( 0, localName.lastIndexOf( "_" ) );
        }
        return localName;
    }

    private void visitExpression( TypedElement var, SharpExpression expr, Element parent, String tagName, Document dox ) {
        Element x = dox.createElement( tagName );

        if ( expr instanceof OperatorExpression && ! ( expr instanceof PropertySetExpression ) ) {
            // TODO fix this condition...
            String exprType = mapExprType( extractExpressionType( expr.getClass() ) );
            x.setAttribute( "xsi:type", exprType );
        }


        if ( expr instanceof VariableExpression ) {
            List vars = ((VariableExpression) expr ).getReferredVariable();

            x.setAttribute( "name", ( (NamedElement) vars.get( 0 ) ).getName().get( 0 ) );
        } else if ( expr instanceof IteratorExpression ) {
            visitClinicalRequest( var, x, (IteratorExpression) expr, dox );
        } else if ( expr instanceof DomainClassExpression ) {
            parent.setAttribute( tagName, expr.getHasCode().get( 0 ).getCode().get( 0 ) );
            return;
        } else if ( expr instanceof DomainPropertyExpression ) {
            parent.setAttribute( tagName, traversePropChain( (PropertyExpression) expr ) );
            return;
        } else if ( expr instanceof PropertySetExpression ) {
            PropertySetExpression setter = (PropertySetExpression) expr;
            visitExpression( var, setter.getFirstOperand().get( 0 ), x, "name", dox );
            visitExpression( var, setter.getSecondOperand().get( 0 ), x, "value", dox );
            parent.appendChild( x );
            return;
        } else if ( expr instanceof TernaryExpression ) {
            TernaryExpression ternary = (TernaryExpression) expr;
            visitExpression( var, ternary.getFirstOperand().get( 0 ), x, "operand", dox );
            visitExpression( var, ternary.getSecondOperand().get( 0 ), x, "operand", dox );
            visitExpression( var, ternary.getThirdOperand().get( 0 ), x, "operand", dox );
        } else if ( expr instanceof BinaryExpression ) {
            BinaryExpression binary = (BinaryExpression) expr;
            visitExpression( var, binary.getFirstOperand().get( 0 ), x, "operand", dox );
            visitExpression( var, binary.getSecondOperand().get( 0 ), x, "operand", dox );
        } else if ( expr instanceof UnaryExpression ) {
            UnaryExpression unary = (UnaryExpression) expr;
            visitExpression( var, unary.getFirstOperand().get( 0 ), x, "operand", dox );
        } else if ( expr instanceof NAryExpression ) {
            for ( SharpExpression op : ((NAryExpression) expr).getHasOperand() ) {
                visitExpression( var, op, x, "operand", dox );
            }
        }

        if ( expr instanceof OperatorExpression ) {
            try {
                fillProperties( x, (OperatorExpression) expr, dox );
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }

        parent.appendChild( x );
    }



    private String mapExprType( String exprType ) {
        if ( "PropertyGet".equals( exprType ) || "PropertySet".equals( exprType ) ) {
            return "Property";
        } else if ( "Variable".equals( exprType ) ) {
            return "ExpressionRef";
        } else if ( "AggregateIterator".equals( exprType ) || "SingleIterator".equals( exprType ) ) {
            return "ClinicalRequest";
        } else if ( "Create".equals( exprType ) ) {
            return "ObjectExpression";
        }
        return exprType;
    }

    private String extractExpressionType( Class expr ) {
        String name = expr.getSimpleName();
        if ( name.endsWith( "Impl" ) ) {
            name = name.substring( 0, name.length() - 4 );
        }
        if ( name.endsWith( "Expression" ) ) {
            name = name.substring( 0, name.length() - "Expression".length() );
        }
        return name;
    }



    private String traversePropChain( PropertyExpression pro ) {
        String prop = null;
        do {
            String fqn = pro.getPropCode().get( 0 ).getCode().get( 0 ).toString();
            fqn = fqn.substring( fqn.indexOf( '#' ) + 1 );
            fqn = fqn.substring( fqn.indexOf( ':' ) + 1 );
            if ( prop == null ) {
                prop = fqn;
            } else {
                prop = fqn + "." + prop;
            }
            if ( pro.getSource() != null && ! pro.getSource().isEmpty() ) {
                pro = pro.getSource().get( 0 ) instanceof DomainPropertyExpression ? (DomainPropertyExpression) pro.getSource().get( 0 ) : null;
            } else {
                pro = null;
            }
        } while ( pro != null );
        return prop;
    }

    private void visitMetadata( HeDKnowledgeDocument hed, Document dox, Element root ) {
        Element metadata = dox.createElement( "metadata" );

        visitIdentifiers( hed, dox, metadata );
        visitDataModels( hed, dox, metadata );
        visitTitle( hed, dox, metadata );

        visitRelatedResources( hed, dox, metadata );
        visitApplicability( hed, dox, metadata );

        visitStatus( hed, dox, metadata );

        visitContributors( hed, dox, metadata );

        root.appendChild( metadata );
    }

    private void visitContributors( HeDKnowledgeDocument hed, Document dox, Element metadata ) {
        Element contris = dox.createElement( "contributions" );

        if ( ! hed.getAuthor().isEmpty() ) {
            for ( Agent ag : hed.getAuthor() ) {
                if ( ag instanceof SocialPerson ) {
                    visitAgentPerson( ag, dox, contris, "Author" );
                } else {
                    visitAgentOrg( ag, dox, contris, "Author" );
                }
            }
        }
        if ( ! hed.getReviewer().isEmpty() ) {
            for ( Agent ag : hed.getReviewer() ) {
                if ( ag instanceof SocialPerson ) {
                    visitAgentPerson( ag, dox, contris, "Reviewer" );
                } else {
                    visitAgentOrg( ag, dox, contris, "Reviewer" );
                }
            }
        }
        if ( ! hed.getEndorser().isEmpty() ) {
            for ( Agent ag : hed.getEndorser() ) {
                if ( ag instanceof SocialPerson ) {
                    visitAgentPerson( ag, dox, contris, "Endorser" );
                } else {
                    visitAgentOrg( ag, dox, contris, "Endorser" );
                }
            }
        }
        if ( ! hed.getEditor().isEmpty() ) {
            for ( Agent ag : hed.getEditor() ) {
                if ( ag instanceof SocialPerson ) {
                    visitAgentPerson( ag, dox, contris, "Editor" );
                } else {
                    visitAgentOrg( ag, dox, contris, "Editor" );
                }
            }
        }
        metadata.appendChild( contris );

    }

    private void visitAgentOrg( Agent ag, Document dox, Element contris, String author ) {

    }

    private void visitAgentPerson( Agent ag, Document dox, Element contris, String roleName ) {
        Element contribution = dox.createElement( "contribution" );

        Element con = dox.createElement( "Contributor" );
        con.setAttribute( "xsi:type", ag instanceof SocialPerson ? "Person" : "Organization" );

        if ( ag instanceof SocialPerson ) {
            Element contacts = dox.createElement( "contacts" );
            for ( String ct : ( (SocialPerson) ag ).getContactInformation() ) {
                Element contact = dox.createElement( "contact" );
                contact.setAttribute( "value", ct );
                contacts.appendChild( contact );
            }
            for ( String nm : ( (SocialPerson) ag ).getAgentName() ) {
                Element name = dox.createElement( "name" );
                name.setAttribute( "use", "C" );
                Element part = dox.createElement( "dt:part" );
                part.setAttribute( "value", nm );
                part.setAttribute( "type", "GIV" );
                name.appendChild( part );
                contacts.appendChild( name );
            }

            if ( ! ( (SocialPerson) ag ).getActsThrough().isEmpty() ) {
                Element affil = dox.createElement( "affiliation" );
                Element name = dox.createElement( "name" );
                name.setAttribute( "value", ( (Organization) ( (SocialPerson) ag ).getActsThrough().get( 0 ) ).getAgentName().get( 0 ) );
                affil.appendChild( name );
                con.appendChild( affil );
            }
            con.appendChild( contacts );
        }

        contribution.appendChild( con );

        Element role = dox.createElement( "role" );
        role.setAttribute( "value", roleName );
        contribution.appendChild( role );

        contris.appendChild( contribution );
    }

    private void visitStatus( HeDKnowledgeDocument hed, Document dox, Element metadata ) {
        if ( ! hed.getStatus().isEmpty() ) {
            Element status = dox.createElement( "status" );
            status.setAttribute( "value", hed.getStatus().get( 0 ) );
            metadata.appendChild( status );
        }
    }

    private void visitApplicability( HeDKnowledgeDocument hed, Document dox, Element metadata ) {
        if ( ! hed.getApplicability().isEmpty() ) {
            Element appl = dox.createElement( "applicability" );
            for ( Coverage cov : hed.getApplicability() ) {
                Element cvg = dox.createElement( "coverage" );
                cvg.setAttribute( "xsi:type", "Coverage" );
                appl.appendChild( cvg );
                Element focus = dox.createElement( "focus" );
                focus.setAttribute( "value", cov.getCoverageType().get( 0 ) );
                appl.appendChild( focus );
                visitCD( cov.getCoveredConcept().get( 0 ), dox, appl, "value" );
            }
            metadata.appendChild( appl );
        }
    }

    private void visitCD( ConceptCode conceptCode, Document dox, Element parent, String name ) {
        Element val = dox.createElement( name );
        val.setAttribute( "code", conceptCode.getCode().get( 0 ) );
        if ( ! conceptCode.getCodeSystem().isEmpty() ) {
            val.setAttribute( "codeSystem", conceptCode.getCodeSystem().get( 0 ) );
        }
        if ( ! conceptCode.getCodeSystemName().isEmpty() ) {
            val.setAttribute( "codeSystem", conceptCode.getCodeSystemName().get( 0 ) );
        }
        if ( ! conceptCode.getLabel().isEmpty() ) {
            Element lab = dox.createElement( "dt:displayName" );
            lab.setAttribute( "value", conceptCode.getLabel().get( 0 ) );
            val.appendChild( lab );
        }
        parent.appendChild( val );
    }


    private void visitRelatedResources( HeDKnowledgeDocument hed, Document dox, Element metadata ) {
        Element relatedRoot = dox.createElement( "relatedResources" );
        if ( ! hed.getAdaptedFrom().isEmpty() ) {
            Element related = dox.createElement( "relatedResource" );
            Element relationship = dox.createElement( "relationship" );
            relationship.setAttribute( "value", "AdaptedFrom" );
            related.appendChild( relationship );
            for ( KnowledgeResource kr : hed.getAdaptedFrom() ) {
                visitKResource( kr, dox, related );
            }
            relatedRoot.appendChild( related );
        }

        metadata.appendChild( relatedRoot );

    }

    private void visitKResource( KnowledgeResource kr, Document dox, Element related ) {
        Element r = dox.createElement( "resource" );

        Element title = dox.createElement( "title" );
        title.setAttribute( "value", kr.getTitle().get( 0 ) );
        r.appendChild( title );

        Element loc = dox.createElement( "location" );
        List locs = ((Site) kr.getSpatial().get( 0 )).getUrlLocation();
        loc.setAttribute( "value", locs.get( 0 ).toString() );
        r.appendChild( loc );

        if ( ! kr.getDescription().isEmpty() ) {
            Element descr = dox.createElement( "description" );
            descr.setAttribute( "value", kr.getDescription().get( 0 ) );
            r.appendChild( descr );
        }

        related.appendChild( r );
    }

    private void visitTitle( InformationRealization info, Document dox, Element metadata ) {
        if ( ! info.getTitle().isEmpty() ) {
            Element title = dox.createElement( "title" );
            title.setAttribute( "value", info.getTitle().get( 0 ) );
            metadata.appendChild( title );
        }
    }

    private void visitDescription( InformationRealization info, Document dox, Element metadata ) {
        if ( ! info.getDescription().isEmpty() ) {
            Element descr = dox.createElement( "description" );
            descr.setAttribute( "value", info.getTitle().get( 0 ) );
            metadata.appendChild( descr );
        }
    }

    private void visitDataModels( HeDKnowledgeDocument hed, Document dox, Element metadata ) {
        Element models = dox.createElement( "dataModels" );
        for ( InformationRealization ir : hed.getReferences() ) {
            Element modelRef = dox.createElement( "modelReference" );
            if ( ! ir.getDescription().isEmpty() ) {
                Element descr = dox.createElement( "description" );
                descr.setAttribute( "value", ir.getDescription().get( 0 ) );
                modelRef.appendChild( descr );
            }
            Element model = dox.createElement( "referencedModel" );
            model.setAttribute( "value", ir.getIdentifier().get( 0 ) );
            modelRef.appendChild( model );
            models.appendChild( modelRef );
        }
        metadata.appendChild( models );
    }


    private void visitIdentifiers( HeDKnowledgeDocument hed, Document dox, Element metadata ) {
        Element identifiers = dox.createElement( "identifiers" );
        for ( VersionedIdentifier id : hed.getArtifactVersion() ) {
            Element identif = dox.createElement( "identifier" );
            identif.setAttribute( "root", id.getArtifactId().get( 0 ) );
            identif.setAttribute( "version", id.getVersionId().get( 0 ) );
            identifiers.appendChild( identif );
        }
        metadata.appendChild( identifiers );
    }


    public boolean validate( Document dox ) {
        try {
            URL schemaFile = getClass().getResource( "xsd/knowledgedocument.xsd" );
            SchemaFactory schemaFactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();

            validator.validate( new DOMSource( dox ) );
            return true;
        } catch ( Exception e ) {
            e.printStackTrace();
            return false;
        }
    }

    public static void dump( Document dox, OutputStream stream ) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StreamResult result = new StreamResult( stream );
            DOMSource source = new DOMSource( dox );
            transformer.transform( source, result );
        } catch ( TransformerException e ) {
            e.printStackTrace();
        }
    }


    @Override
    public byte[] export( HeDKnowledgeDocument dok ) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document dox = new OOwl2HedDumper().serialize( dok );
        OOwl2HedDumper.dump( dox, baos );
        return baos.toByteArray();
    }
}
