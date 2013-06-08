package example;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.skos.*;
import org.semanticweb.skosapibinding.SKOSFormatExt;
import org.semanticweb.skosapibinding.SKOSManager;
import org.semanticweb.skosapibinding.SKOSReasoner;
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
 * Date: Aug 28, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class ReadInferredSKOS {

    public static void main(String[] args) {

        try {
            SKOSManager manager = new SKOSManager();
//
//            SKOSDataset dataSet = manager.loadDatasetFromPhysicalURI(URI.create("file:/Users/simon/ontologies/skos/apitest.owl"));
//
//
//            SKOStoOWLConverter converter = new SKOStoOWLConverter();
//            OWLReasoner reasoner= new Reasoner.ReasonerFactory().createReasoner(converter.getAsOWLOntology(dataSet));
//            SKOSReasoner skosreasoner = new SKOSReasoner(manager, reasoner);

//            reasoner.loadDataset(dataSet);

//            manager.save(dataSet, SKOSFormatExt.RDFXML, URI.create("file:/Users/simon/importtest.owl"));
//

//            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//            try {
//                OWLOntology myonto = man.loadOntology(IRI.create("file:/Users/jupp/tmp/apitest.owl"));
////                OWLOntology skosonto = man.loadOntology(IRI.create("http://www.w3.org/2004/02/skos/core"));
//
//                OWLOntology skosonto = man.loadOntology(IRI.create("http://www.w3.org/2004/02/skos/core"));
//                OWLImportsDeclaration importsDec = man.getOWLDataFactory().getOWLImportsDeclaration(IRI.create ("http://www.w3.org/2004/02/skos/core"));
//
//                for (OWLOntologyChange change: man.applyChange(new AddImport(myonto, importsDec))) {
//                    System.out.println(change.toString());
//                }
//
//                OWLReasoner reasoner= new com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory().createReasoner(myonto);
//
////                reasoner.flush();
//                for (OWLNamedIndividual i : reasoner.getObjectPropertyValues(man.getOWLDataFactory().getOWLNamedIndividual(IRI.create("http://cs.man.ac.uk/~sjupp/skos/apitest#Body")),
//                        man.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://www.w3.org/2004/02/skos/core#narrowerTransitive"))).getFlattened()) {
//
//                    System.out.println(i.getIRI());
//                }
//
//
//
//            } catch (OWLOntologyCreationException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }

            SKOSDataset skosCoreOntology = manager.loadDataset(URI.create ("http://www.w3.org/2004/02/skos/core"));
            SKOSDataset dataSet = manager.loadDatasetFromPhysicalURI(URI.create("file:/Users/jupp/Downloads/rtwobugsinthelatestversionofapiskosapi3_jar/a.owl"));

            SKOStoOWLConverter converter = new SKOStoOWLConverter();
            OWLOntology mySkosAsOWLOntology = converter.getAsOWLOntology(dataSet);

            OWLImportsDeclaration importsDec = manager.getOWLManger().getOWLDataFactory().getOWLImportsDeclaration(IRI.create ("http://www.w3.org/2004/02/skos/core"));

            for (OWLOntologyChange change: manager.getOWLManger().applyChange(new AddImport(mySkosAsOWLOntology, importsDec))) {
                System.out.println(change.toString());
            }

//            SKOSDataset skosCoreOntology = manager.loadDataset(URI.create("http://www.w3.org/2004/02/skos/core"));

//            now make your skos ontology import the skos ontology

            for (OWLOntology o : mySkosAsOWLOntology.getImports()) {
                System.out.println("imports:"+ o.toString());
            }

//           OWLReasoner reasoner= new com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory().createReasoner(converter.getAsOWLOntology(dataSet));

//            SKOSReasoner skosreasoner = new SKOSReasoner(manager, reasoner, converter.getAsOWLOntology(skosCoreOntology));
//
//            for (SKOSConcept con : skosreasoner.getSKOSConcepts()) {
//
//                for (SKOSAnnotation literal : con.getSKOSAnnotationsByURI(dataSet, manager.getSKOSDataFactory().getSKOSPrefLabelProperty().getURI())) {
//                    System.err.println("Concept label: " + literal.getAnnotationValueAsConstant().getLiteral());
//                }
//
//                for (SKOSConcept broaderCon : skosreasoner.getSKOSNarrowerTransitiveConcepts(con)) {
//                    for (SKOSAnnotation literal : broaderCon.getSKOSAnnotationsByURI(dataSet, manager.getSKOSDataFactory().getSKOSPrefLabelProperty().getURI())) {
//                        System.err.println("Narrower concepts: " + literal.getAnnotationValueAsConstant().getLiteral());
//                    }
//                }
//
//            }




        } catch (SKOSCreationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
