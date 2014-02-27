package example;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.skos.*;
import org.semanticweb.skosapibinding.SKOSManager;
import org.semanticweb.skosapibinding.SKOStoOWLConverter;

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
 * Date: Mar 17, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class ReadSKOSExample {

    public ReadSKOSExample() {

        try {

            final SKOSManager man = new SKOSManager();
//
            SKOSDataset dataSet = man.loadDatasetFromPhysicalURI(URI.create("file:/Users/simon/ontologies/skos/agrovoc_2007_SKOS/ag_skos_20070219.rdf"));

            //////

            // print out all concepts;
            for (SKOSConcept concept : dataSet.getSKOSConcepts()) {
                System.out.println("Concept: " + concept.getURI());
                // get the narrower concepts
                for (SKOSEntity narrowerConcepts : concept.getSKOSRelatedEntitiesByProperty(dataSet, man.getSKOSDataFactory().getSKOSNarrowerProperty())) {
                    System.err.println("\t hasNarrower: " + narrowerConcepts.getURI());
                }
                // get the broader concepts
                for (SKOSEntity broaderConcepts : concept.getSKOSRelatedEntitiesByProperty(dataSet, man.getSKOSDataFactory().getSKOSBroaderProperty())) {
                    System.err.println("\t hasBroader: " + broaderConcepts.getURI());
                }

            }



            System.out.println("");
            System.out.println("---------------------");
            System.out.println("");
            System.out.println("Ontology loaded!");

            for (SKOSConceptScheme scheme : dataSet.getSKOSConceptSchemes()) {

                System.out.println("ConceptScheme: " + scheme.getURI());

                // i can get all the concepts from this scheme
                for (SKOSConcept conceptsInScheme : dataSet.getSKOSConcepts()) {

                    System.err.println("\tConcepts: " + conceptsInScheme.getURI());

                    for (SKOSAnnotation anno : conceptsInScheme.getSKOSAnnotations(dataSet)) {
                        System.err.print("\t\tAnnotation: " + anno.getURI() + "-> ");
                        if (anno.isAnnotationByConstant()) {
                            if (anno.getAnnotationValueAsConstant().isTyped()) {
                                SKOSTypedLiteral con = anno.getAnnotationValueAsConstant().getAsSKOSTypedLiteral();
                                System.err.print(con.getLiteral() + " Type: " + con.getDataType().getURI());
                            }
                            else {
                                SKOSUntypedLiteral con = anno.getAnnotationValueAsConstant().getAsSKOSUntypedLiteral();
                                System.err.print(con.getLiteral());
                                if (con.hasLang()) {
                                    System.err.print("@" + con.getLang());
                                }
                            }
                            System.err.println("");
                        }
                        else {
                            System.err.println(anno.getAnnotationValue().getURI().toString());
                        }
                    }

                }

            }

//
//                for (SKOSConcept con : scheme.getTopConcepts(vocab)) {
//
//                    System.out.println("Top Concept: " + con.getURI());
//
//                }
//
//                int counter = 0;
//
//                for (SKOSConcept con : scheme.getConceptsInScheme(vocab)) {
//                    counter++;
//                    System.out.println("Concept: " + con.getURI().getFragment());
//
//                    for (OWLUntypedConstant type : con.getSKOSPrefLabel(vocab)) {
//                        System.out.println("PrefLabel: " + type.getLiteral() + " lang: " + type.getLang());
//                    }
//
//                    for (OWLUntypedConstant type : con.getSKOSAltLabel(vocab)) {
//                        System.out.println("AltLabel: " + type.getLiteral() + " lang: " + type.getLang());
//                    }
//
//                    for (SKOSConcept concepts : con.getSKOSBroaderConcepts(vocab)) {
//                        System.out.println("\tHas Broader: " + concepts.getURI().getFragment());
//                    }
//
//                }
//                System.out.println("Count: " + counter);
//            }
//
//
//            // see if we can get and find an entity
//
//            SKOSEntity entity = vocab.getSKOSEntity("conker");
//            System.out.println("Entity lookup for Nose " + entity.asSKOSConcept().getAsOWLIndividual().getURI());
//
////            vocab.getAssertions();
////            vocab.conatinsSKOSConcept();
////            vocab.getSKOSObjectRelationAssertions();
////            vocab.getSKOSBroaderAssertions();
////            vocab.getSKOSNarrowerAssertions();
////            vocab.getSKOSRelatedAssertions();
//
//
        } catch (SKOSCreationException e) {
            e.printStackTrace();
        }
//

    }



    public void dumpHierarchy() {

    }

    public static void main(String[] args) {

        ReadSKOSExample exp = new ReadSKOSExample();
    }
}
