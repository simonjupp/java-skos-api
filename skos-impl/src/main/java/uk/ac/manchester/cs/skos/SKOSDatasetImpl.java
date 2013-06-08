package uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.PropertyAssertionValueShortFormProvider;
import org.semanticweb.skos.*;
import org.semanticweb.skos.extensions.SKOSLabelRelation;
import org.semanticweb.skosapibinding.SKOSManager;
import org.semanticweb.skosapibinding.SKOStoOWLConverter;

import java.net.URI;
import java.util.*;
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
 *
 * A <code>SKOSDataset</code> is the central point of access to your SKOS vocabularies.
 * The SKOS vocabulary is managed by a <code>SKOSManager</code>. A SKOSDataset can be thought
 * of as an OWL Ontology which conatins a single of multiple SKOS concept schemes.<br>
 * Because the SKOS API is built on top of the OWL API we use an OWLOntology to contain our SKOS Vocabularies,
 * you always have access to the underlying OWLOntology, but in most cases you should be fine just using the SKOSDataset directly.
 *
 */
public class SKOSDatasetImpl implements SKOSDataset {

    private OWLOntologyManager man;
    private SKOSManager skosManContent;
    private OWLOntology owlOntology;
    private Map<URI, SKOSEntity> SKOSEntity;

    private SKOStoOWLConverter skos2owlConverter;

    private BidirectionalShortFormProviderAdapter biAdapt;

    /* Constructors */

    public SKOSDatasetImpl(SKOSManager skosMan, OWLOntology onto) {
        this.skosManContent = skosMan;
        this.owlOntology = onto;
        this.man = skosMan.getOWLManger();
        this.SKOSEntity = new HashMap<URI, SKOSEntity>(20000);
        skos2owlConverter = new SKOStoOWLConverter();
//        this.propUtility = new SKOSObjectPropertyUtility(man);
//        setUpShortFormProviders ();
    }

    public SKOSDatasetImpl(SKOSManager skosMan, URI uri) throws SKOSCreationException {
        this.skosManContent = skosMan;
        this.man = skosMan.getOWLManger();
        try {
            this.owlOntology = man.createOntology(IRI.create(uri));
        } catch (OWLOntologyCreationException e) {
            throw new SKOSCreationException("Can't create a new SKOS Manager");
        }
        skos2owlConverter = new SKOStoOWLConverter();
        this.SKOSEntity = new HashMap<URI, SKOSEntity>(20000);
//        this.propUtility = new SKOSObjectPropertyUtility(man);
//        setUpShortFormProviders ();
    }

    /* Methods */


    public SKOSEntity getSKOSEntity(String shortForm) {
        OWLEntity entity = biAdapt.getEntity(shortForm);
        return SKOSEntity.get(entity.getIRI().toURI());
    }

    public URI getURI() {
        if (owlOntology.getOntologyID().getOntologyIRI() == null) {
            return URI.create("http://skosapi.sourceforge.net/anonymous/" + Math.random());
        }
        return owlOntology.getOntologyID().getOntologyIRI().toURI();
    }

    public Set<SKOSConceptScheme> getSKOSConceptSchemes() {
        Set<SKOSConceptScheme> conceptSchemes = new HashSet<SKOSConceptScheme>(20);
        for (OWLClassAssertionAxiom ax : owlOntology.getClassAssertionAxioms(man.getOWLDataFactory().getOWLClass(IRI.create(SKOSRDFVocabulary.CONCEPTSCHEME.getURI())))) {
            SKOSConceptScheme scheme = skosManContent.getSKOSDataFactory().getSKOSConceptScheme(ax.getIndividual().asOWLNamedIndividual().getIRI().toURI());
            conceptSchemes.add(scheme);
            SKOSEntity.put(ax.getIndividual().asOWLNamedIndividual().getIRI().toURI(), scheme);
        }
        return conceptSchemes;
    }

    public Set<SKOSConcept> getSKOSConcepts() {
        Set<SKOSConcept> concepts = new HashSet<SKOSConcept>(30000);
        for (OWLClassAssertionAxiom ax : owlOntology.getClassAssertionAxioms(man.getOWLDataFactory().getOWLClass(IRI.create(SKOSRDFVocabulary.CONCEPT.getURI())))) {
            SKOSEntity ent = SKOSEntity.get(ax.getIndividual().asOWLNamedIndividual().getIRI().toURI());
            if (ent == null) {
                SKOSConceptImpl concept = (SKOSConceptImpl) skosManContent.getSKOSDataFactory().getSKOSConcept(ax.getIndividual().asOWLNamedIndividual().getIRI().toURI());
                SKOSEntity.put(ax.getIndividual().asOWLNamedIndividual().getIRI().toURI(), concept);
                concepts.add(concept);
            }
            else {
                if (ent instanceof SKOSConcept) {
                    concepts.add((SKOSConceptImpl) ent);
                }
            }
        }
        return concepts;
    }

    public Set<SKOSConcept> getTopConcepts(SKOSConceptScheme scheme) {
        Set<SKOSConcept> concepts = new HashSet<SKOSConcept> (10);
        for (SKOSObjectRelationAssertion ass : getSKOSObjectRelationAssertions(scheme)) {
            if (ass.getSKOSProperty().getURI().equals(SKOSRDFVocabulary.HASTOPCONCEPT.getURI())) {
                concepts.add(SKOSEntity.get(ass.getSKOSObject().getURI()).asSKOSConcept());
            }
        }
        return concepts;
    }

    public Set<SKOSConcept> getConceptsInScheme (SKOSConceptScheme scheme) {
        Set<SKOSConcept> concepts = new HashSet<SKOSConcept>(10);
        for (OWLAxiom ax : owlOntology.getAxioms()) {
            if (ax instanceof OWLObjectPropertyAssertionAxiom) {
                OWLObjectPropertyAssertionAxiom propAx = (OWLObjectPropertyAssertionAxiom) ax;
                if (propAx.getProperty().asOWLObjectProperty().getIRI().toURI().equals(SKOSRDFVocabulary.INSCHEME.getURI())) {
                    OWLNamedIndividual ind = (OWLNamedIndividual) propAx.getSubject();
                    OWLNamedIndividual schemeInd = (OWLNamedIndividual) propAx.getObject();
                    if (schemeInd.equals(skos2owlConverter.getAsOWLIndiviudal(scheme))) {
                        SKOSEntity ent = SKOSEntity.get(ind.asOWLNamedIndividual().getIRI().toURI());
                         if (ent == null) {
                             ent = new SKOSConceptImpl(ind);
                             SKOSEntity.put(ind.asOWLNamedIndividual().getIRI().toURI(), ent.asSKOSConcept());
                        }
                        concepts.add(ent.asSKOSConcept());
                    }
                }
            }
        }
        return concepts;
    }

    public Set<SKOSObjectRelationAssertion> getSKOSObjectRelationAssertions(SKOSEntity skosEntity) {
        Set<SKOSObjectRelationAssertion> assertion = new HashSet<SKOSObjectRelationAssertion>(50);

        if (!SKOSEntity.containsKey(skosEntity.getURI())) {
            SKOSEntity.put(skosEntity.getURI(), skosEntity);
        }

        for (OWLObjectPropertyAssertionAxiom ax : owlOntology.getObjectPropertyAssertionAxioms(skos2owlConverter.getAsOWLIndiviudal(skosEntity))) {

            OWLIndividual object = ax.getObject();
            OWLIndividual subject = ax.getSubject();

            OWLObjectPropertyExpression prop = ax.getProperty();

            SKOSEntity entOb = SKOSEntity.get(object.asOWLNamedIndividual().getIRI().toURI());
            SKOSEntity entSub = SKOSEntity.get(subject.asOWLNamedIndividual().getIRI().toURI());

            if (entOb == null) {
                entOb = entityCreationHandler(object.asOWLNamedIndividual());
                SKOSEntity.put(object.asOWLNamedIndividual().getIRI().toURI(), entOb);
            }
            if (entSub == null) {
                entSub = entityCreationHandler(subject.asOWLNamedIndividual());
                SKOSEntity.put(subject.asOWLNamedIndividual().getIRI().toURI(), entSub);
            }

            SKOSObjectProperty skosProp = skosManContent.getSKOSDataFactory().getSKOSObjectProperty(prop.asOWLObjectProperty().getIRI().toURI());
            assertion.add(skosManContent.getSKOSDataFactory().getSKOSObjectRelationAssertion(entSub, skosProp, entOb));
        }
        return assertion;
    }

    private SKOSEntity entityCreationHandler(OWLNamedIndividual ind) {

        for (OWLClassExpression desc : ind.getTypes(owlOntology)) {

            if (desc instanceof OWLClass) {

                OWLClass cls = desc.asOWLClass();
                if (cls.getIRI().toURI().equals(SKOSRDFVocabulary.CONCEPT.getURI())) {
                    return new SKOSConceptImpl(ind);
                }
                else if (cls.getIRI().toURI().equals(SKOSRDFVocabulary.CONCEPTSCHEME.getURI())) {
                    return new SKOSConceptSchemeImpl(ind);
                }
                else if (cls.getIRI().toURI().equals(SKOSRDFVocabulary.LABELRELATION.getURI())) {
                    return new SKOSLabelRelationImpl(ind);
                }
            }
        }
        return new SKOSResourceImpl(ind);
    }

    public Set<SKOSObjectRelationAssertion> getReferencingSKOSObjectRelationAssertions(SKOSEntity skosEntity) {
        Set<SKOSObjectRelationAssertion> assertion = new HashSet<SKOSObjectRelationAssertion>(100);

        if (!SKOSEntity.containsKey(skosEntity.getURI())) {
            SKOSEntity.put(skosEntity.getURI(), skosEntity);
        }

        for (OWLAxiom ax : owlOntology.getReferencingAxioms(skos2owlConverter.getAsOWLIndiviudal(skosEntity).asOWLNamedIndividual())) {
            if (ax instanceof OWLObjectPropertyAssertionAxiom) {
                OWLObjectPropertyAssertionAxiom  propAx = (OWLObjectPropertyAssertionAxiom) ax;
                OWLNamedIndividual object = (OWLNamedIndividual) propAx.getObject();
                OWLNamedIndividual subject = (OWLNamedIndividual) propAx.getSubject();
                OWLObjectPropertyExpression prop = propAx.getProperty();

                SKOSEntity entOb = SKOSEntity.get(object.asOWLNamedIndividual().getIRI().toURI());
                SKOSEntity entSub = SKOSEntity.get(subject.asOWLNamedIndividual().getIRI().toURI());

                if (entOb == null) {
                    entOb = new SKOSConceptImpl(object);
                    SKOSEntity.put(object.asOWLNamedIndividual().getIRI().toURI(), entOb);
                }
                if (entSub == null) {
                    entSub = new SKOSConceptImpl(subject);
                    SKOSEntity.put(subject.asOWLNamedIndividual().getIRI().toURI(), entSub);
                }

                SKOSObjectProperty skosProp = skosManContent.getSKOSDataFactory().getSKOSObjectProperty(prop.asOWLObjectProperty().getIRI().toURI());
                assertion.add(skosManContent.getSKOSDataFactory().getSKOSObjectRelationAssertion(entSub, skosProp, entOb));
            }
        }

        return assertion;
    }

    public Set<SKOSDataRelationAssertion> getSKOSDataRelationAssertions(SKOSEntity skosEntity) {

        Set<SKOSDataRelationAssertion> assertion = new HashSet<SKOSDataRelationAssertion>(50);

        if (!SKOSEntity.containsKey(skosEntity.getURI())) {
            SKOSEntity.put(skosEntity.getURI(), skosEntity);
        }

        for (OWLAxiom ax : owlOntology.getDataPropertyAssertionAxioms(skos2owlConverter.getAsOWLIndiviudal(skosEntity))) {
            if (ax instanceof OWLDataPropertyAssertionAxiom) {
                OWLDataPropertyAssertionAxiom  propAx = (OWLDataPropertyAssertionAxiom) ax;
                OWLLiteral object = propAx.getObject();
                OWLNamedIndividual subject = (OWLNamedIndividual) propAx.getSubject();
                OWLDataPropertyExpression prop = propAx.getProperty();

                SKOSEntity entSub = SKOSEntity.get(subject.asOWLNamedIndividual().getIRI().toURI());

                if (entSub == null) {
                    entSub = entityCreationHandler(subject);
                    SKOSEntity.put(subject.asOWLNamedIndividual().getIRI().toURI(), entSub);
                }
                SKOSDataProperty skosProp = skosManContent.getSKOSDataFactory().getSKOSDataProperty(prop.asOWLDataProperty().getIRI().toURI());
                SKOSLiteral literal = null;

                if (!object.isRDFPlainLiteral()) {
                    SKOSDataType type = skosManContent.getSKOSDataFactory().getSKOSDataType(object.getDatatype().getIRI().toURI());
                    literal = skosManContent.getSKOSDataFactory().getSKOSTypedConstant(type, object.getLiteral());
                }
                else {
                    literal = skosManContent.getSKOSDataFactory().getSKOSUntypedConstant(object.getLiteral(), object.getLang());
                }

                assertion.add(skosManContent.getSKOSDataFactory().getSKOSDataRelationAssertion(entSub, skosProp, literal));
            }
        }
        return assertion;
    }


    public Set<SKOSEntity> getSKOSObjectRelationByProperty(SKOSEntity entity, SKOSObjectProperty property) {
        Set<SKOSEntity> entitySet = new HashSet<SKOSEntity>(50);
        for (SKOSEntity subject : getSKOSObjectRelatedConcepts(getSKOSObjectRelationAssertions(entity), property)) {
            entitySet.add(subject);
        }
        return entitySet;
    }

    public Set<SKOSLiteral> getSKOSDataRelationByProperty(SKOSEntity entity, SKOSDataProperty property) {
        Set<SKOSLiteral> literalSets = new HashSet<SKOSLiteral>(10);
        for (SKOSLiteral literal : getSKOSDataRelatedConcepts(getSKOSDataRelationAssertions(entity), property)) {

            literalSets.add(literal);
        }
        return literalSets;
    }


    public Set<SKOSDataRelationAssertion> getReferencingSKOSDataRelationAssertions(SKOSEntity skosEntity) {

        Set<SKOSDataRelationAssertion> assertion = new HashSet<SKOSDataRelationAssertion>(50);

        if (!SKOSEntity.containsKey(skosEntity.getURI())) {
            SKOSEntity.put(skosEntity.getURI(), skosEntity);
        }

        for (OWLAxiom ax : owlOntology.getReferencingAxioms(skos2owlConverter.getAsOWLIndiviudal(skosEntity).asOWLNamedIndividual())) {
            if (ax instanceof OWLDataPropertyAssertionAxiom) {
                OWLDataPropertyAssertionAxiom  propAx = (OWLDataPropertyAssertionAxiom) ax;
                OWLLiteral object = propAx.getObject();
                OWLNamedIndividual subject = (OWLNamedIndividual) propAx.getSubject();
                OWLDataPropertyExpression prop = propAx.getProperty();

                SKOSEntity entSub = SKOSEntity.get(subject.asOWLNamedIndividual().getIRI().toURI());

                if (entSub == null) {
                    entSub = entityCreationHandler(subject);
                    SKOSEntity.put(subject.asOWLNamedIndividual().getIRI().toURI(), entSub);
                }
                SKOSDataProperty skosProp = skosManContent.getSKOSDataFactory().getSKOSDataProperty(prop.asOWLDataProperty().getIRI().toURI());
                SKOSLiteral literal = null;

                if (!object.isRDFPlainLiteral()) {
                    SKOSDataType type = skosManContent.getSKOSDataFactory().getSKOSDataType(object.getDatatype().getIRI().toURI());
                    literal = skosManContent.getSKOSDataFactory().getSKOSTypedConstant(type, object.getLiteral());
                }
                else {
                    literal = skosManContent.getSKOSDataFactory().getSKOSUntypedConstant(object.getLiteral(), object.getLang());
                }

                assertion.add(skosManContent.getSKOSDataFactory().getSKOSDataRelationAssertion(entSub, skosProp, literal));
            }
        }
        return assertion;
    }

    public Set<SKOSLiteral> getSKOSDataRelatedConcepts (Set<SKOSDataRelationAssertion> assertionSet, SKOSDataProperty prop) {
        Set<SKOSLiteral> literal = new HashSet<SKOSLiteral> ();

        for (SKOSDataRelationAssertion rel : assertionSet) {
            if (rel.getSKOSProperty().getURI().equals(prop.getURI())) {
                literal.add(rel.getSKOSObject());
            }
        }
        return literal;
    }


    public Set<SKOSConcept> getSKOSObjectRelatedConcepts (Set<SKOSObjectRelationAssertion> assertionSet, SKOSObjectProperty prop) {
        Set<SKOSConcept> con = new HashSet<SKOSConcept> ();

        for (SKOSObjectRelationAssertion rel : assertionSet) {
            if (rel.getSKOSProperty().getURI().equals(prop.getURI())) {
                URI uri = rel.getSKOSObject().getURI();
                if (!SKOSEntity.containsKey(uri)) {
                    SKOSEntity.put(uri, entityCreationHandler(skos2owlConverter.getAsOWLIndiviudal(rel.getSKOSObject())));
                }
                if (SKOSEntity.get(uri).isSKOSConcept()) {
                    con.add(SKOSEntity.get(uri).asSKOSConcept());
                }
            }
        }
        return con;
    }


    public Set<SKOSLabelRelation> getSKOSLabelRelations(SKOSConcept concept) {
        return null;
    }

    public Set<SKOSAnnotation> getSKOSAnnotationsByURI(SKOSEntity entity, URI annotationURI) {

        Set<SKOSAnnotation> set = new HashSet<SKOSAnnotation>();
        for (SKOSAnnotation anno : getSKOSAnnotations(entity)) {
            if (anno.getURI().equals(annotationURI)) {
                set.add(anno);
            }
        }
        return set;
    }

    public Set<SKOSAnnotation> getSKOSAnnotations(SKOSEntity entity) {

        Set<SKOSAnnotation> annotations = new HashSet<SKOSAnnotation>();
        OWLIndividual ind = skos2owlConverter.getAsOWLIndiviudal(entity);

        AnnotationVisitor annoVis = new AnnotationVisitor(skosManContent);

        for (OWLAnnotation anno : ind.asOWLNamedIndividual().getAnnotations(owlOntology)) {

            OWLAnnotationValue value = anno.getValue();
            value.accept(annoVis);
            if (annoVis.getCurrentLiteral() != null) {
                annotations.add(skosManContent.getSKOSDataFactory().getSKOSAnnotation(anno.getProperty().getIRI().toURI(), annoVis.getCurrentLiteral()));
            }
            else {
                annotations.add(skosManContent.getSKOSDataFactory().getSKOSAnnotation(anno.getProperty().getIRI().toURI(), annoVis.getCurrentResource()));
            }
        }

        return annotations;
    }

    private static class AnnotationVisitor implements OWLAnnotationObjectVisitor {

        SKOSManager skosManager;
        SKOSLiteral skosCon;
        SKOSResource resource;

        public AnnotationVisitor (SKOSManager skosManContent) {
            this.skosManager = skosManContent;
            skosCon = null;
            resource = null;
        }


        public SKOSLiteral getCurrentLiteral () {
            return skosCon;
        }

        public SKOSResource getCurrentResource () {
            return resource;
        }

        public void visit(IRI iri) {
            skosCon = null;
            this.resource = skosManager.getSKOSDataFactory().getSKOSResource(iri.toURI());
        }

        public void visit(OWLAnonymousIndividual owlAnonymousIndividual) {
        }

        public void visit(OWLLiteral owlLiteral) {
            resource = null;

            if (owlLiteral.isRDFPlainLiteral()) {
                this.skosCon = skosManager.getSKOSDataFactory().getSKOSUntypedConstant(owlLiteral.getLiteral(), owlLiteral.getLang());
            }
            else {
                SKOSDataType dataType = skosManager.getSKOSDataFactory().getSKOSDataType(owlLiteral.getDatatype().getIRI().toURI());
                this.skosCon = skosManager.getSKOSDataFactory().getSKOSTypedConstant(dataType, owlLiteral.getLiteral());

            }
        }

        public void visit(OWLAnnotationAssertionAxiom owlAnnotationAssertionAxiom) {
        }

        public void visit(OWLSubAnnotationPropertyOfAxiom owlSubAnnotationPropertyOfAxiom) {
        }

        public void visit(OWLAnnotationPropertyDomainAxiom owlAnnotationPropertyDomainAxiom) {
        }

        public void visit(OWLAnnotationPropertyRangeAxiom owlAnnotationPropertyRangeAxiom) {
        }

        public void visit(OWLAnnotation owlAnnotation) {
        }
    }


    public OWLOntology getAsOWLOntology() {
        return owlOntology;
    }

//
//
//    public Set<SKOSLabelRelation> getSKOSLabelRelations(SKOSConcept concept) {
//
//        Set<SKOSLabelRelation> labelRel = new HashSet<SKOSLabelRelation>();
//        for (SKOSObjectRelationAssertion objRel : getSKOSObjectRelationAssertions(concept)) {
//
//            if (objRel.getProperty().asOWLObjectProperty().getURI().equals(SKOSRDFVocabulary.SEELABELRELATION.getURI())) {
//
//                OWLIndividual ind = objRel.getObject();
//                if (!SKOSEntity.containsKey(ind.getURI())) {
//
//                    // get label relation, create the object
//                    Map<OWLDataPropertyExpression, Set<OWLConstant>> dataProps = ind.getDataPropertyValues(owlOntology);
//                    Set<OWLConstant> constants =  dataProps.get(skosManContent.getSKOSDataFactory().getOWLDataProperty(SKOSRDFVocabulary.LABELRELATED.getURI()));
//
//                    SKOSEntity.put(ind.getURI(), new SKOSLabelRelationImpl(ind,
//                                skosManContent.getSKOSDataFactory().getOWLDataProperty(SKOSRDFVocabulary.LABELRELATED.getURI()),
//                                    new ArrayList<OWLConstant>(constants) ));
//                }
//                labelRel.add(SKOSEntity.get(ind.getURI()).asSKOSLabelRelation());
//           }
//       }
//        return labelRel;  //To change body of created methods use File | Settings | File Templates.
//    }
//


//    public void updatePropertyUtility() {
//        propUtility.updateProperties();
//    }


    protected void setUpShortFormProviders () {

        OWLDataPropertyExpression prop1 = man.getOWLDataFactory().getOWLDataProperty(IRI.create(SKOSRDFVocabulary.PREFLABEL.getURI()));
        OWLDataPropertyExpression prop2 = man.getOWLDataFactory().getOWLDataProperty(IRI.create(SKOSRDFVocabulary.ALTLABEL.getURI()));
        OWLDataPropertyExpression prop3 = man.getOWLDataFactory().getOWLDataProperty(IRI.create(SKOSRDFVocabulary.HIDDENLABEL.getURI()));

        List<OWLPropertyExpression<?,?>> list = new ArrayList<OWLPropertyExpression<?,?>>();
        list.add(prop3);
        list.add(prop2);
        list.add(prop1);

        /* this is how we can set up a language prefernece for rendering, don't need it yet
        List<String> langList = new ArrayList<String>();
        langList.add("en");
        langList.add("fr");

        Map<OWLDataPropertyExpression, List<String>> langMap = new HashMap<OWLDataPropertyExpression, List<String>> ();
        langMap.put(prop1, langList);
        langMap.put(prop2, langList);
        langMap.put(prop3, langList);
        */

        PropertyAssertionValueShortFormProvider propProv = new PropertyAssertionValueShortFormProvider(list, new HashMap(), man);

        biAdapt = new BidirectionalShortFormProviderAdapter(man, man.getOntologies(), propProv);
    }

}
