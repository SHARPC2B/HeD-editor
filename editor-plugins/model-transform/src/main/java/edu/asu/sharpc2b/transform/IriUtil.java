package edu.asu.sharpc2b.transform;

import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

/**
 * User: rk Date: 5/22/13 Time: 12:22 PM
 */
public final class IriUtil
{

    private static final DefaultPrefixManager owlapiDefaultPrefixManager = new DefaultPrefixManager();

    public static final String skos = "http://www.w3.org/2004/02/skos/core";

    public static final IRI skosIRI = IRI.create( skos );

    public static final IRI skosIRI (String name)
    {
        return IRI.create( skos + "#" + name );
    }

    public static String skosNamespace = skosIRI.toString() + "#";

    public static final String sharpOntBase = "http://asu.edu/sharpc2b/";

    public static final String sharpIRIString (String name)
    {
        return sharpOntBase + name;
    }

    public static final IRI sharpEditorIRI (String name)
    {
        return IRI.create( sharpIRIString( name ) );
    }


    public static final PrefixOWLOntologyFormat getDefaultSharpOntologyFormat ()
    {
        PrefixOWLOntologyFormat oFormat = new OWLFunctionalSyntaxOntologyFormat();
        oFormat.copyPrefixesFrom( owlapiDefaultPrefixManager );
        addSharpPrefixes( oFormat );
        return oFormat;
    }

    public static void addSharpPrefixes (PrefixOWLOntologyFormat of)
    {
        of.setPrefix( "opsb:", sharpOntBase + "opsb#" );
        of.setPrefix( "opsc:", sharpOntBase + "opsc#" );
        of.setPrefix( "opsd:", sharpOntBase + "opsd#" );
        of.setPrefix( "ops:", sharpOntBase + "ops#" );
        of.setPrefix( "shops:", sharpOntBase + "shops#" );
        of.setPrefix( "prr:", sharpOntBase + "prr#" );
        of.setPrefix( "actions:", sharpOntBase + "actions#" );
        of.setPrefix( "skos-ext:", sharpOntBase + "skos-ext#" );
        of.setPrefix( "sharp-master:", sharpOntBase + "sharp-master#" );
        of.setPrefix( "prr-sharp:", sharpOntBase + "prr-sharp#" );
        of.setPrefix( "metadata:", sharpOntBase + "metadata#" );
        of.setPrefix( "skoslmm:", sharpOntBase + "skoslmm#" );
        of.setPrefix( "dc2dul:", sharpOntBase + "dc2dul#" );
        of.setPrefix( "dul:", "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#" );
        of.setPrefix( "lmm:", "http://www.ontologydesignpatterns.org/ont/lmm/LMM_L1.owl#" );
        of.setPrefix( "IOLite:", "http://www.ontologydesignpatterns.org/ont/dul/IOLite.owl#" );
        of.setPrefix( "dc2:", "http://purl.org/NET/dc_owl2dl#" );
        of.setPrefix( "skos:", skos + "#" );
    }

    /**
     * Same as above, but for DefaultPrefixManager.
     */
    public static void addSharpPrefixes (DefaultPrefixManager of)
    {
        of.setPrefix( "opsb:", sharpOntBase + "opsb#" );
        of.setPrefix( "opsc:", sharpOntBase + "opsc#" );
        of.setPrefix( "opsd:", sharpOntBase + "opsd#" );
        of.setPrefix( "shops:", sharpOntBase + "shops#" );
        of.setPrefix( "ops:", sharpOntBase + "ops#" );
        of.setPrefix( "prr:", sharpOntBase + "prr#" );
        of.setPrefix( "actions:", sharpOntBase + "actions#" );
        of.setPrefix( "skos-ext:", sharpOntBase + "skos-ext#" );
        of.setPrefix( "sharp-master:", sharpOntBase + "sharp-master#" );
        of.setPrefix( "prr-sharp:", sharpOntBase + "prr-sharp#" );
        of.setPrefix( "metadata:", sharpOntBase + "metadata#" );
        of.setPrefix( "skoslmm:", sharpOntBase + "skoslmm#" );
        of.setPrefix( "dc2dul:", sharpOntBase + "dc2dul#" );
        of.setPrefix( "dul:", "http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#" );
        of.setPrefix( "lmm:", "http://www.ontologydesignpatterns.org/ont/lmm/LMM_L1.owl#" );
        of.setPrefix( "IOLite:", "http://www.ontologydesignpatterns.org/ont/dul/IOLite.owl#" );
        of.setPrefix( "dc2:", "http://purl.org/NET/dc_owl2dl#" );
    }

}
