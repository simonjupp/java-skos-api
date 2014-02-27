package example;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;
import org.semanticweb.skos.*;
import org.semanticweb.skos.properties.SKOSAltLabelProperty;
import org.semanticweb.skosapibinding.SKOSFormatExt;
import org.semanticweb.skosapibinding.SKOSManager;
import org.semanticweb.skosapibinding.SKOStoOWLConverter;

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
 * Date: Mar 4, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class CreateSKOSExample {

    public static String baseURI = "http://skosexample.com/";

    public CreateSKOSExample() {
    }

    public static void main(String[] args) throws SKOSCreationException {

        // new manager for SKOS vocabularies
        SKOSManager man = new SKOSManager();

        // a skos vocabulary is essentially an OWL ontology that can contain multiple concept schemes
        SKOSDataset vocab = man.createSKOSDataset(URI.create(baseURI));

        // a data factory is used to create types of SKOS Objects
        SKOSDataFactory factory = man.getSKOSDataFactory();

        SKOSConceptScheme scheme = factory.getSKOSConceptScheme(URI.create(baseURI + "conceptSchemeA"));

        // make a concept scheme assertion in a particular data set
        SKOSEntityAssertion schemaAss = factory.getSKOSEntityAssertion(scheme);

        SKOSConcept concept1 = factory.getSKOSConcept(URI.create(baseURI + "concept1"));
        SKOSConcept concept2 = factory.getSKOSConcept(URI.create(baseURI + "concept2"));

        // just add it with a Concept Assertion
        SKOSEntityAssertion conAss1 = factory.getSKOSEntityAssertion(concept1);
        SKOSEntityAssertion conAss2 = factory.getSKOSEntityAssertion(concept2);

        // make assertion on concepts in your SKOS vocabulary - you do it by making SKOS assertion objects
        SKOSObjectRelationAssertion inScheme = factory.getSKOSObjectRelationAssertion(concept1, factory.getSKOSInSchemeProperty(), scheme);
        SKOSObjectRelationAssertion inScheme1 = factory.getSKOSObjectRelationAssertion(concept2, factory.getSKOSInSchemeProperty(), scheme);

        SKOSObjectRelationAssertion topConcept = factory.getSKOSObjectRelationAssertion(scheme, factory.getSKOSHasTopConceptProperty(), concept1);

        // add object relation between individuals, this is with respect to a vocabulary

        SKOSObjectRelationAssertion assertion1 = factory.getSKOSObjectRelationAssertion(concept2, factory.getSKOSBroaderProperty(), concept1);

        // add data relation on individuals, this is with respect to a vocabulary

        SKOSAnnotation labelAnno = factory.getSKOSAnnotation(factory.getSKOSPrefLabelProperty().getURI(), "Some Label", "en");
        SKOSAnnotation altLabelAnno = factory.getSKOSAnnotation(factory.getSKOSAltLabelProperty().getURI(), "Another Label", "en");
        SKOSAnnotationAssertion assertion2 = factory.getSKOSAnnotationAssertion(concept2, labelAnno);
        SKOSAnnotationAssertion assertion3 = factory.getSKOSAnnotationAssertion(concept2, altLabelAnno);

        /* creating a sub property of altLabel */

        SKOSAnnotationProperty newAltLabel = factory.getSKOSAnnotationProperty(URI.create("http://myexample/altLable/newSubAltLabel"));
        SKOSAltLabelProperty altLabel = factory.getSKOSAltLabelProperty();

        SKOStoOWLConverter converter = new SKOStoOWLConverter();

        OWLOntologyManager owlMan = man.getOWLManger();
        OWLDataFactory owlFact = owlMan.getOWLDataFactory();
        OWLSubAnnotationPropertyOfAxiom axiom = owlFact.getOWLSubAnnotationPropertyOfAxiom(converter.getAsOWLAnnotationProperty(newAltLabel), converter.getAsOWLAnnotationProperty(altLabel));
        try {
            owlMan.applyChange(new AddAxiom(converter.getAsOWLOntology(vocab), axiom));
        } catch (OWLOntologyChangeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        /*
       * The full legal use of SKOS documantation propertiiies canno't be supported by this implmentation with the OWL API
       * The documantation properties can be used as either data or object properties, depending on how they are being used
        */

        // SKOS documentation properties as object properties

        // we might want to create some abritary resource, e.g. a web page that is used as a scope note
        SKOSResource someResource = factory.getSKOSResource(URI.create("http://www.wikipedia.org/someExample"));
        SKOSObjectRelationAssertion assertion4 = factory.getSKOSObjectRelationAssertion(concept2, factory.getSKOSScopeNoteObjectProperty(), someResource);

        // we might want to use the documentation properties with a filler that is a literal
        SKOSDataRelationAssertion assertion5 = factory.getSKOSDataRelationAssertion(concept2, factory.getSKOSScopeNoteDataProperty(), "A scope note for the concept", "en");


        /* Annotations - you can assign any kind of annotation (e.g. dublic core) to your entities
        * here is an example adding a dc:creator and a rdfs:comment
        */

        SKOSAnnotation anno1 = factory.getSKOSAnnotation(DublinCoreVocabulary.DATE.getURI(), "12-07-2008");
        SKOSAnnotation anno2 = factory.getSKOSAnnotation(DublinCoreVocabulary.CREATOR.getURI(), "Simon Jupp", "en");
        SKOSAnnotation anno3 = factory.getSKOSAnnotation(URI.create("http://my-custom-annotation.com/example"), someResource);
        SKOSAnnotation anno4 = factory.getSKOSAnnotation(DublinCoreVocabulary.CREATOR.getURI(),  factory.getSKOSUntypedConstant("Simon Jupp", "en"));
        // todo need to work on typed on objects
        //factory.getSKOSAnnotationsByURI(DublinCoreVocabulary.CREATOR.getURI(),  factory.getSKOSTypedConstant("String", "Simon Jupp"));

        
        SKOSAnnotationAssertion assertion6 = factory.getSKOSAnnotationAssertion(concept2, anno1);
        SKOSAnnotationAssertion assertion7 = factory.getSKOSAnnotationAssertion(concept2, anno2);
        SKOSAnnotationAssertion assertion8 = factory.getSKOSAnnotationAssertion(concept2, anno3);
        SKOSAnnotationAssertion assertion9 = factory.getSKOSAnnotationAssertion(concept2, anno4);

        /* Add some utility stuff fomr creating single or multiple inScheme objects,
        *  these are afetr all just ObjectPropertyAssertions */
        // or add it to concept shceme in a paryicular vocab
        // SKOSInSchemeAssertion insSchemeAss = factory.getSKOSInSchemeAssertion(vocab, concept1, scheme);
        // this can also takes sets
        // SKOSInSchemeAssertion insSchemeAss = factory.getSKOSInsCHEMEAssertion(vocab, Set <SKOSConcept> concept1, scheme);

        List<SKOSChange> addList = new ArrayList<SKOSChange>();
        addList.add(new AddAssertion(vocab, schemaAss));
        addList.add(new AddAssertion(vocab, inScheme));
        addList.add(new AddAssertion(vocab, inScheme1));
        addList.add(new AddAssertion(vocab, conAss1));
        addList.add(new AddAssertion(vocab, conAss2));
        addList.add(new AddAssertion(vocab, topConcept));
        addList.add(new AddAssertion(vocab, assertion1));
        addList.add(new AddAssertion(vocab, assertion2));
        addList.add(new AddAssertion(vocab, assertion3));
        addList.add(new AddAssertion(vocab, assertion4));
        addList.add(new AddAssertion(vocab, assertion5));
        addList.add(new AddAssertion(vocab, assertion6));
        addList.add(new AddAssertion(vocab, assertion7));
        addList.add(new AddAssertion(vocab, assertion8));
        addList.add(new AddAssertion(vocab, assertion9));
        try {

//            man.applyChange(new AddAssertion(vocab, schemaAss));
//            man.applyChange(new AddAssertion(vocab, inScheme));
//            man.applyChange(new AddAssertion(vocab, inScheme1));
//            man.applyChange(new AddAssertion(vocab, conAss1));
//            man.applyChange(new AddAssertion(vocab, conAss2));
//            man.applyChange(new AddAssertion(vocab, topConcept));
//            man.applyChange(new AddAssertion(vocab, assertion1));
//            man.applyChange(new AddAssertion(vocab, assertion2));
//            man.applyChange(new AddAssertion(vocab, assertion3));

            man.applyChanges(addList);

            System.err.println("writing ontology");
            man.save(vocab, SKOSFormatExt.RDFXML, URI.create("file:/Users/jupp/tmp/testskos/testskos.rdf"));

        } catch (SKOSChangeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SKOSStorageException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }




//
//            // create a Label relation...
//            List<OWLConstant> labels = new ArrayList<OWLConstant>();
//            labels.add(factory.getOWLUntypedConstant("Some Label"));
//            labels.add(factory.getOWLUntypedConstant("Another Label"));
//
//            SKOSLabelRelation labRel = factory.getSKOSLabelRelation(URI.create(baseURI + "labelRel1"), labels);
//            vocab.addSKOSLabelRelation(labRel);
//
//            SKOSObjectRelationAssertion assertion4 = factory.getSKOSLabelRelationAssertion(concept2, labRel);
//
            System.out.println("Read");

            // get all the concept schemes
            for (SKOSConceptScheme sch : vocab.getSKOSConceptSchemes()) {

                System.err.println("Concept Scheme_______");
                // get all the concepts in that scheme
                for (SKOSConcept co : vocab.getConceptsInScheme(sch)) {

                    System.out.println("\tConcept: " + co.getURI());

                    // get any pref labels
                    for (SKOSAnnotation con : co.getSKOSAnnotationsByURI(vocab, factory.getSKOSPrefLabelProperty().getURI())) {
                        if (con.getAnnotationValueAsConstant() instanceof SKOSUntypedLiteral) {
                            SKOSUntypedLiteral unCon = con.getAnnotationValueAsConstant().getAsSKOSUntypedLiteral();
                            System.out.println("\t\tPrefLabel: " + unCon.getLiteral() + " lang: " + unCon.getLang());
                        }
                        else if (con.getAnnotationValueAsConstant() instanceof SKOSTypedLiteral) {
                            SKOSTypedLiteral unCon = con.getAnnotationValueAsConstant().getAsSKOSTypedLiteral();
                            System.out.println("\t\tPrefLabel: " + unCon.getLiteral());
                        }
                    }

                    // get definitions
                    for (SKOSObject object : co.getSKOSRelatedEntitiesByProperty(vocab, factory.getSKOSDefinitionObjectProperty())) {

                        if (object instanceof SKOSLiteral) {
                            
                        }
                        else if (object instanceof SKOSEntity) {

                        }


                    }

                    // get broader concepts from all schemes
                    for (SKOSEntity c : co.getSKOSRelatedEntitiesByProperty(vocab, factory.getSKOSBroaderProperty())) {
                        System.out.println("\t\thasBroader: " + c.getURI());
                    }

                    // get label relations
//                    for (SKOSLabelRelation rel : co.getSKOSLabelRelations(vocab)) {
//
//                        Iterator it =  rel.getLabels().iterator();
//                        while (it.hasNext()) {
//                            OWLConstant con  = (OWLConstant) it.next();
//                            System.out.println("Releated Labels: " + con.getLiteral() );
//                        }
//                    }


                }

            }
//
//            // do a simple lookup
//            SKOSEntity entity = vocab.getSKOSEntity("Another Label");
//            System.out.println("Entity lookup : " + entity.asSKOSConcept().getAsOWLIndividual().getURI());
//
//
//
//        } catch (OWLOntologyChangeException e) {
//            e.printStackTrace();
//        } catch (OWLOntologyStorageException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

    }

}
