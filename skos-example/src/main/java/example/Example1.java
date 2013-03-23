package example;

import org.semanticweb.skos.properties.SKOSBroaderProperty;
import org.semanticweb.skosapibinding.SKOSManager;
import org.semanticweb.skos.*;

import java.net.URI;
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
 * Date: Sep 8, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class Example1 {

    public static void main(String[] args) {

        /*
        * How to load a SKOS vocabulary and print out the concepts and any assertions on these
        * The main object in the API include a SKOSManager which manages the loading, saving a dn editing of dataset,
        * The SKOSDataset object is a container for your SKOS vocabularies, each manager can have multiple dataset which
        * are accessed via a URIs. Finally there is a SKOSDataFactory object for creating and retrieving SKOSObject from your dataset.
         */

        try {

            // First create a new SKOSManager
            SKOSManager manager = new SKOSManager();

            // use the manager to load a SKOS vocabulary from a URI (either physical or on the web)

            SKOSDataset dataset = manager.loadDataset(URI.create("file:/Users/jupp/tmp/tmp.skos.owl"));

            // get all the concepts in this vocabulay and print out the URI

            for (SKOSConcept concept : dataset.getSKOSConcepts()) {

                System.out.println("Concept: " + concept.getURI());

                /*
                * SKOS entities such as Concepts, ConceptSchemes (See SKOSEntity in Javadoc for full list), are related to other
                * entities or literal values by three different types of relationships.
                * ObjectPropertyAssertions - These are relationships between two SKOS entities
                * DataPropertyAssertion - These relate entities to Literal values
                * SKOSAnnotation - These are either literal or entity annotation on a particular entity
                 */

                // print out object assertions
                concept.getSKOSAnnotationsByURI(dataset, manager.getSKOSDataFactory().getSKOSBroaderProperty().getURI());


                System.out.println("\tObject property assertions:");
                for (SKOSObjectRelationAssertion objectAssertion : dataset.getSKOSObjectRelationAssertions(concept)) {
                    System.out.println("\t\t" + objectAssertion.getSKOSProperty().getURI().getFragment() + " " + objectAssertion.getSKOSObject().getURI().getFragment());
                    
                }
                System.out.println("");


                // print out any data property assertions
                System.out.println("\tData property assertions:");
                for (SKOSDataRelationAssertion assertion : dataset.getSKOSDataRelationAssertions(concept)) {

                    // the object of a data assertion can be either a typed or untyped literal
                    SKOSLiteral literal = assertion.getSKOSObject();
                    String lang = "";
                    if (literal.isTyped()) {

                        SKOSTypedLiteral typedLiteral = literal.getAsSKOSTypedLiteral();
                        System.out.println("\t\t" + assertion.getSKOSProperty().getURI().getFragment() + " " + literal.getLiteral() + " Type:" + typedLiteral.getDataType().getURI() );
                    }
                    else {

                        // if it has  language
                        SKOSUntypedLiteral untypedLiteral = literal.getAsSKOSUntypedLiteral();
                        if (untypedLiteral.hasLang()) {
                            lang = untypedLiteral.getLang();
                        }
                        System.out.println("\t\t" + assertion.getSKOSProperty().getURI().getFragment() + " " + literal.getLiteral() + " Lang:" + lang);

                    }
                }
                System.out.println("");


                // finally get any OWL annotations - the object of a annotation property can be a literal or an entity
                System.out.println("\tAnnotation property assertions:");
                for (SKOSAnnotation assertion : dataset.getSKOSAnnotations(concept)) {

                    // if the annotation is a literal annotation?
                    String lang = "";
                    String value = "";

                    if (assertion.isAnnotationByConstant()) {

                        SKOSLiteral literal = assertion.getAnnotationValueAsConstant();
                        value = literal.getLiteral();
                        if (!literal.isTyped()) {
                            // if it has  language
                            SKOSUntypedLiteral untypedLiteral = literal.getAsSKOSUntypedLiteral();
                            if (untypedLiteral.hasLang()) {
                                lang = untypedLiteral.getLang();
                            }
                        }
                    }
                    else {
                        // annotation is some resource
                        SKOSEntity entity = assertion.getAnnotationValue();
                        value = entity.getURI().getFragment();
                    }
                    System.out.println("\t\t" + assertion.getURI().getFragment() + " " + value + " Lang:" + lang);
                }
                System.out.println("");
            }
        } catch (SKOSCreationException e) {
            e.printStackTrace();
        }
    }
}
