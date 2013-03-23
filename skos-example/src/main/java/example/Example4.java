package example;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.skos.SKOSCreationException;
import org.semanticweb.skos.SKOSDataFactory;
import org.semanticweb.skos.SKOSDataset;
import org.semanticweb.skos.SKOSObjectProperty;
import org.semanticweb.skosapibinding.SKOSManager;
import org.semanticweb.skosapibinding.SKOStoOWLConverter;

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
 * Date: Aug 26, 2009<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Example of removing assertions from the dataset
 */
public class Example4 {

    public static void main(String[] args) {

        try {

            SKOSManager man = new SKOSManager();
            SKOSDataFactory fact = man.getSKOSDataFactory();
            SKOSDataset dataset = man.loadDatasetFromPhysicalURI(URI.create("file:/Users/simon/ontologies/skos/obo-in-skos/so.rdf.xml"));

            SKOSObjectProperty partOf = fact.getSKOSObjectProperty(URI.create("http://obo.sourceforge.net/obo/oborelations#part_of"));
            // convert the SKOS object to the equivalent OWL object
            SKOStoOWLConverter conv = new SKOStoOWLConverter();
            OWLObjectProperty owlPartOf = conv.getAsOWLObjectProperty(partOf);

            // get the SKOS dataset as an owl ontology object
            OWLOntology onto = conv.getAsOWLOntology(dataset);
            //query the owl ontology for superproperties
            for (OWLObjectPropertyExpression prop : owlPartOf.getSuperProperties(onto)) {
                System.out.println(prop.asOWLObjectProperty().getIRI());
            }



//
//            for (SKOSObjectRelationAssertion ass : dataset.getSKOSObjectRelationAssertions(fact.getSKOSConcept(URI.create("http://skosexample.com/concept2")))) {
//                System.out.println(ass.getSKOSSubject().getURI() + " - " + ass.getSKOSProperty().getURI().getFragment() + " - " + ass.getSKOSObject().getURI());
//                man.applyChange(new RemoveAssertion(dataset, ass));
//
//            }
//
//            man.save(dataset, URI.create("file:/Users/simon/skostest-2.rdf"));



        } catch (SKOSCreationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        catch (SKOSChangeException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (SKOSStorageException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

    }

}
