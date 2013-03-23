package org.semanticweb.skosapibinding;


import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.skos.*;
import org.semanticweb.skos.extensions.SKOSLabelRelation;
import uk.ac.manchester.cs.skos.SKOSRDFVocabulary;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Simon Jupp<br>
 * Date: Aug 28, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOSReasoner {

    SKOSManager manager;
    OWLReasoner reasoner;
    OWLOntology ontology;
    SKOSDataset dataSet;
    SKOStoOWLConverter skos2owlconverter;
    OWLOntology skosOntology;

    //    final static String SKOSURI = "file:/Users/simon/skos-core.rdf";
    final static String SKOSURI = "http://www.w3.org/2009/08/skos-reference/skos-owl1-dl.rdf";

    // constructors
//
//    public SKOSReasoner (SKOSManager manager) {
//        this.manager  = manager;
//        this.reasoner = new Reasoner(manager.getOWLManger());
//        this.skos2owlconverter = new SKOStoOWLConverter();
//        try {
//            this.skosOntology = manager.getOWLManger().loadOntology(URI.create(SKOSURI));
//        } catch (OWLOntologyCreationException e) {
//            e.printStackTrace();
//        }
//        classify();
//    }

    public SKOSReasoner (SKOSManager manager, OWLReasoner reasoner) {
        this.manager = manager;
        this.reasoner = reasoner;
        this.skos2owlconverter = new SKOStoOWLConverter();
        try {
            if (!manager.getOWLManger().contains(IRI.create("http://www.w3.org/2004/02/skos/core"))) {
                this.skosOntology = manager.getOWLManger().loadOntology(IRI.create(SKOSURI));
            }
        } catch (OWLOntologyCreationException e) {
            System.err.println("Can't load SKOS data model from SKOSURI: " + SKOSURI);
            e.printStackTrace();
        }
        classify();
    }

    public SKOSReasoner (SKOSManager manager, OWLReasoner reasoner, OWLOntology skosOntology) {
        this.manager = manager;
        this.reasoner = reasoner;
        this.skos2owlconverter = new SKOStoOWLConverter();
        this.skosOntology = skosOntology;
        classify();
    }

    // methods

    public void classify() {

//        Set<OWLOntology> ontologies = new HashSet<OWLOntology>();
//        ontologies.addAll(manager.getOWLManger().getOntologies());
//        ontologies.add(skosOntology);
//            reasoner.loadOntologies(ontologies);
        reasoner.flush();
        reasoner.getInstances(manager.getOWLManger().getOWLDataFactory().getOWLThing(), false);
    }

//    public void loadDataset (SKOSDataset dataSet) throws OWLReasonerException {
//        this.ontology = skos2owlconverter.getAsOWLOntology(dataSet);
//        reasoner.loadOntologies(Collections.singleton(ontology));
//    }

    public Set<SKOSConceptScheme> getSKOSConceptSchemes() {
        OWLClass cls = manager.getOWLManger().getOWLDataFactory().getOWLClass(IRI.create(SKOSRDFVocabulary.CONCEPTSCHEME.getURI()));
        Set<SKOSConceptScheme> conSet = new HashSet<SKOSConceptScheme>();
        for (Node node : reasoner.getInstances(cls, false)) {
            for (Object o : node.getEntities()) {
                if (o instanceof OWLNamedIndividual) {
                    OWLNamedIndividual ind = (OWLNamedIndividual) o;
                    conSet.add(manager.getSKOSDataFactory().getSKOSConceptScheme(ind.getIRI().toURI()));
                }
            }
        }
        return conSet;
    }

    public Set<SKOSConcept> getSKOSConcepts() {
        OWLClass cls = manager.getOWLManger().getOWLDataFactory().getOWLClass(IRI.create(SKOSRDFVocabulary.CONCEPT.getURI()));
        Set<SKOSConcept> conSet = new HashSet<SKOSConcept>();
        for (Node node : reasoner.getInstances(cls, false)) {
            for (Object o : node.getEntities()) {
                if (o instanceof OWLNamedIndividual) {
                    OWLNamedIndividual ind = (OWLNamedIndividual) o;
                    conSet.add(manager.getSKOSDataFactory().getSKOSConcept(ind.getIRI().toURI()));
                }
            }
        }
        return conSet;
    }

    public Set<SKOSObjectRelationAssertion> getSKOSObjectRelationAssertions(SKOSEntity skosEntity) {
        return null;
    }

    public Set<SKOSObjectRelationAssertion> getReferencingSKOSObjectRelationAssertions(SKOSEntity skosEntity) {
        return null;
    }

    public Set<SKOSConcept> getTopConcepts(SKOSConceptScheme scheme) {
        return getSKOSRelatedConcept(scheme, SKOSRDFVocabulary.TOPCONCEPTOF.getURI());
    }

    public Set<SKOSConcept> getConceptsInScheme(SKOSConceptScheme scheme) {
        return getSKOSRelatedConcept(scheme, SKOSRDFVocabulary.INSCHEME.getURI());
    }

    public Set<SKOSConcept> getSKOSRelatedConcept(SKOSEntity concept, URI uri) {
        OWLNamedIndividual ind = skos2owlconverter.getAsOWLIndiviudal(concept);
        OWLObjectProperty prop = manager.getOWLManger().getOWLDataFactory().getOWLObjectProperty(IRI.create(uri));
        Set<SKOSConcept> conSet = new HashSet<SKOSConcept>();
        NodeSet<OWLNamedIndividual> set = reasoner.getObjectPropertyValues(ind, prop);
        for (OWLNamedIndividual relind : reasoner.getObjectPropertyValues(ind, prop).getFlattened()) {
            conSet.add(manager.getSKOSDataFactory().getSKOSConcept(relind.getIRI().toURI()));
        }
        return conSet;
    }

    public Set<SKOSConcept> getSKOSNarrowerConcepts(SKOSConcept skosConcept) {
        return getSKOSRelatedConcept(skosConcept, SKOSRDFVocabulary.NARROWER.getURI());
    }

    public Set<SKOSConcept> getSKOSRelatedConcepts(SKOSConcept concept) {
        return getSKOSRelatedConcept(concept, SKOSRDFVocabulary.RELATED.getURI());
    }

    public Set<SKOSConcept> getSKOSBroaderConcepts(SKOSConcept skosConcept) {
        return getSKOSRelatedConcept(skosConcept, SKOSRDFVocabulary.BROADER.getURI());
    }

    public Set<SKOSConcept> getSKOSBroaderTransitiveConcepts(SKOSConcept concept) {
        return getSKOSRelatedConcept(concept, SKOSRDFVocabulary.BROADERTRANS.getURI());
    }

    public Set<SKOSConcept> getSKOSNarrowerTransitiveConcepts(SKOSConcept concept) {
        return getSKOSRelatedConcept(concept, SKOSRDFVocabulary.NARROWERTRANS.getURI());
    }

    public Set<SKOSConcept> getSKOSSemanticRelatedConcepts(SKOSConcept concept) {
        return getSKOSRelatedConcept(concept, SKOSRDFVocabulary.SEMANTICRELATION.getURI());
    }

    public Set<SKOSConcept> getSKOSRelatedMatchConcepts(SKOSConcept concept) {
        return getSKOSRelatedConcept(concept, SKOSRDFVocabulary.RELATEDMATCH.getURI());
    }

    public Set<SKOSConcept> getSKOSNarrowMatchConcepts(SKOSConcept concept) {
        return getSKOSRelatedConcept(concept, SKOSRDFVocabulary.NARROWMATCH.getURI());
    }

    public Set<SKOSConcept> getSKOSBroadMatchConcepts(SKOSConcept concept) {
        return getSKOSRelatedConcept(concept, SKOSRDFVocabulary.BROADMATCH.getURI());
    }

    public Set<SKOSDataRelationAssertion> getSKOSDataRelationAssertions(SKOSEntity skosEntity) {
        return null;
    }

    public Set<SKOSLabelRelation> getSKOSLabelRelations(SKOSConcept concept) {
        return null;
    }

    public Set<SKOSUntypedLiteral> getSKOSPrefLabel(SKOSConcept skosConcept) {
        return null;
    }

    public Set<SKOSUntypedLiteral> getSKOSAltLabel(SKOSConcept skosConcept) {
        return null;
    }

    public Set<SKOSUntypedLiteral> getSKOSHiddenLabel(SKOSConcept skosConcept) {
        return null;
    }

    public Set<SKOSAnnotation> getSKOSDefinition(SKOSConcept skosConcept) {
        return null;
    }

    public Set<SKOSAnnotation> getSKOSAnnotation(SKOSConcept skosConcept, URI annotationURI) {
        return null;
    }
}
