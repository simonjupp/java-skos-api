package example;

import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.skos.*;
import org.semanticweb.skosapibinding.SKOSManager;
import org.semanticweb.skosapibinding.SKOSFormatExt;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
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
public class Example2 {

    public static void main(String[] args) {

        /* This example shows how to create a new SKOS dataset and add assertions to it
        *
        * All object from the SKOS API are added to the dataset as assertions. Each assertion is
        * added with respect to a particular dataset. There are three types of assertions, Object property assertions,
        * data property assertions and annotation property assertions.
        *
        * Assertions can be made between entities or between entities and literal value only
        * A data factory is used to create object
        */

        try {
            // Always start by creating your SKOS manager
            SKOSManager manager = new SKOSManager();

            // give your dataset a URI so it can be retrieved by the manager
            // N.B as the SKOS API is implemented with the OWL API, ultimately everything you
            // create will be saved as an OWL ontology with this URI

            final String baseURI = "http://www.semanticweb.org/skos/example2.rdf";

            SKOSDataset dataset = manager.createSKOSDataset(URI.create(baseURI));

            SKOSDataFactory df = manager.getSKOSDataFactory();

            // Create a concept scheme identified by a URI
            SKOSConceptScheme conceptScheme1 = df.getSKOSConceptScheme(URI.create(baseURI + "#conceptScheme1"));

            // create some concepts
            SKOSConcept conceptA = df.getSKOSConcept(URI.create(baseURI + "#conceptA"));
            SKOSConcept conceptB = df.getSKOSConcept(URI.create(baseURI + "#conceptB"));
            SKOSConcept conceptC = df.getSKOSConcept(URI.create(baseURI + "#conceptC"));
            SKOSConcept conceptD = df.getSKOSConcept(URI.create(baseURI + "#conceptD"));

            // At present these object don't exist in any dataset, we have to create assertions and them add them to a particular dataset

            // the simplest type of assertions are just entity assertion, where we want to add our SKOS entities to the dataset, but say nothing else about them

            // N.B there is a convenience method on the datafactory for adding set of entites to create a set of entoty assertions
            SKOSEntityAssertion entityAssertion1 = df.getSKOSEntityAssertion(conceptScheme1);
            SKOSEntityAssertion entityAssertion2 = df.getSKOSEntityAssertion(conceptA);
            SKOSEntityAssertion entityAssertion3 = df.getSKOSEntityAssertion(conceptB);
            SKOSEntityAssertion entityAssertion4 = df.getSKOSEntityAssertion(conceptC);
            SKOSEntityAssertion entityAssertion5 = df.getSKOSEntityAssertion(conceptD);

            // Now create the assertions that these entities are all skos:inScheme concept scheme 1
            // The skos:inScheme is an object property, used to related two entities, in our case a concept to a concept scheme
            // again there exists a convenience method on the data factory for doing this with sets, but for this example we will do one by one

            // The SKOS API has built in object for all the SKOS properties, but you can define any of your own types of SKOSObjectProperty using URIs
            SKOSObjectProperty inScheme = df.getSKOSInSchemeProperty();

            SKOSObjectRelationAssertion propertyAssertion1 = df.getSKOSObjectRelationAssertion(conceptA, inScheme, conceptScheme1);
            SKOSObjectRelationAssertion propertyAssertion2 = df.getSKOSObjectRelationAssertion(conceptB, inScheme, conceptScheme1);
            SKOSObjectRelationAssertion propertyAssertion3 = df.getSKOSObjectRelationAssertion(conceptC, inScheme, conceptScheme1);
            SKOSObjectRelationAssertion propertyAssertion4 = df.getSKOSObjectRelationAssertion(conceptD, inScheme, conceptScheme1);


            // The SKOS API uses a simple paradigm for changes. You have a SKOSChange object which is either to
            // add an assertion (AddAssertion) or remove an assertion(RemoveAssertion)
            // We can use the assertions create above to create a list of SKOS changes, these changes will be to Add the Assertions
            // created above

            List<SKOSChange> addAssertions = new ArrayList<SKOSChange>();

            addAssertions.add (new AddAssertion(dataset, entityAssertion1));
            addAssertions.add (new AddAssertion(dataset, entityAssertion2));
            addAssertions.add (new AddAssertion(dataset, entityAssertion3));
            addAssertions.add (new AddAssertion(dataset, entityAssertion4));
            addAssertions.add (new AddAssertion(dataset, entityAssertion5));

            addAssertions.add (new AddAssertion(dataset, propertyAssertion1));
            addAssertions.add (new AddAssertion(dataset, propertyAssertion2));
            addAssertions.add (new AddAssertion(dataset, propertyAssertion3));
            addAssertions.add (new AddAssertion(dataset, propertyAssertion4));

            // It is at this point we apply the changes via our manager

            for (SKOSChange change : manager.applyChanges(addAssertions) ) {
                System.out.println("change: " + change.getSKOSAssertion().toString());
            }

            // finally save the dataset to a file in RDF/XML format

            manager.save(dataset, SKOSFormatExt.RDFXML, URI.create("file:/Users/simon/tmp/example2.rdf"));

            /* Have a look at Example3.java in the documentation to see how the following code be written in less lines using
                some convenience methods
             */


        } catch (SKOSCreationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SKOSChangeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SKOSStorageException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


}
