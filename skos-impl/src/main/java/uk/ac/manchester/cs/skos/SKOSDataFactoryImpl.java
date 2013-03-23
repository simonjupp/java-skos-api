package uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.skos.*;
import org.semanticweb.skos.extensions.SKOSLabelRelation;
import org.semanticweb.skos.extensions.SKOSSeeLabelRelationProperty;
import org.semanticweb.skos.properties.*;
import org.semanticweb.skosapibinding.SKOStoOWLConverter;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.skos.properties.*;

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
 */

public class SKOSDataFactoryImpl extends OWLDataFactoryImpl implements SKOSDataFactory {

    private Map<URI, SKOSConcept> conceptsByURI;
    private Map<URI, SKOSConceptScheme> conceptSchemeByURI;
    private SKOStoOWLConverter converter;

    public SKOSDataFactoryImpl() {
        conceptsByURI = new HashMap<URI, SKOSConcept>();
        conceptSchemeByURI = new HashMap<URI, SKOSConceptScheme>();
        converter = new SKOStoOWLConverter();

    }

    public SKOSConcept getSKOSConcept(URI uri) {
        SKOSConcept con  = conceptsByURI.get(uri);
        if (con == null) {
            con = new SKOSConceptImpl(this, uri);
            conceptsByURI.put(uri, con);
        }
        return con;
    }

    public SKOSCollection getSKOSCollection(URI uri) {
        return new SKOSCollectionImpl(this, uri);
    }

    public SKOSTypedLiteral getSKOSTypedConstant(SKOSDataType type, String literal) {
        return new SKOSTypedLiteralImpl(this, type, literal);
    }

    public SKOSUntypedLiteral getSKOSUntypedConstant(String literal, String lang) {
        return new SKOSUntypedLiteralImpl(this, literal, lang);
    }

    public SKOSConceptScheme getSKOSConceptScheme(URI uri) {
        SKOSConceptScheme con = conceptSchemeByURI.get(uri);
        if (con == null) {
            con = new SKOSConceptSchemeImpl(this, uri);
            conceptSchemeByURI.put(uri, con);
        }
        return con;
    }

    public SKOSOrderedCollection getSKOSOrderedCollection(URI uri) {
        return new SKOSOrderedCollectionImpl(this, uri);
    }

    public SKOSEntityAssertion getSKOSEntityAssertion(SKOSEntity entity) {
        return new SKOSEntityAssertionImpl(this, entity);
    }

    public SKOSDataType getSKOSDataType(URI uri) {
        return new SKOSDataTypeImpl(this, uri);
    }

    public SKOSAnnotationProperty getSKOSAnnotationProperty(URI uri) {
        return new SKOSAnnotationPropertyImpl(this, IRI.create(uri));
    }

//    public SKOSConceptSchemeAssertion getSKOSConceptSchemeAssertion(SKOSConceptScheme scheme) {
//        return new SKOSConceptSchemeAssertionImpl(this, scheme);
//    }
//
//    public SKOSConceptSchemeAssertion getSKOSConceptSchemeAssertion(URI uri) {
//        return new SKOSConceptSchemeAssertionImpl(this,getSKOSConceptScheme(uri));
//    }
//
//    public SKOSConceptAssertion getSKOSConceptAssertion(SKOSConcept concept) {
//        return new SKOSConceptAssertionImpl(this, concept);
//    }
//
//    public SKOSResourceAssertion getSKOSResourceAssertion(SKOSResource concept) {
//        return null;
//    }

    public SKOSResource getSKOSResource(URI uri) {
        return new SKOSResourceImpl(this, uri);
    }

    public SKOSBroaderProperty getSKOSBroaderProperty() {
        return new SKOSBroaderPropertyImpl(this);
    }

    public SKOSHasTopConceptProperty getSKOSHasTopConceptProperty() {
        return new SKOSHasTopConceptPropertyImpl(this);
    }

    public SKOSAnnotation getSKOSAnnotation(URI uri, SKOSLiteral literal) {
        return new SKOSAnnotationImpl(this, uri, literal);

    }

    public SKOSAnnotation getSKOSAnnotation(URI uri, String literal) {
        SKOSLiteral con = getSKOSUntypedConstant(literal, "");
        return new SKOSAnnotationImpl(this, uri, con);
    }

    public SKOSAnnotation getSKOSAnnotation(URI uri, String literal, String lang) {
        SKOSLiteral con = getSKOSUntypedConstant(literal, lang);
        return new SKOSAnnotationImpl(this, uri, con);
    }

    public SKOSAnnotation getSKOSAnnotation(URI uri, SKOSEntity entity) {
        SKOSResource resource = new SKOSResourceImpl(this, entity.getURI());
        return new SKOSAnnotationImpl(this, uri, resource);
    }

    public SKOSScopeNoteObjectProperty getSKOSScopeNoteObjectProperty() {
        return new SKOSScopeNoteObjectPropertyImpl(this);
    }

    public SKOSScopeNoteDataProperty getSKOSScopeNoteDataProperty() {
        return new SKOSScopeNoteDataPropertyImpl(this);
    }

    public SKOSObjectProperty getSKOSObjectProperty(URI uri) {
        return new SKOSObjectPropertyImpl(this, uri);
    }

    public SKOSDataProperty getSKOSDataProperty(URI uri) {
        return new SKOSDataPropertyImpl(this, uri);
    }

    public SKOSSemanticRelationProperty getSKOSSemanticRelationProperty() {
        return new SKOSSemanticRelationPropertyImpl(this);
    }

    public SKOSTopConceptOfProperty getSKOSTopConceptOfProperty() {
        return new SKOSTopConceptOfPropertyImpl(this);
    }

    public SKOSNarrowerProperty getSKOSNarrowerProperty() {
        return new SKOSNarrowerPropertyImpl(this);
    }

    public SKOSNarrowerTransitiveProperty getSKOSNarrowerTransitiveProperty() {
        return new SKOSNarrowerTransitivePropertyImpl(this);
    }

    public SKOSNotationProperty getSKOSNotationProperty() {
        return new SKOSNotationPropertyImpl(this);
    }

    public SKOSRelatedProperty getSKOSRelatedProperty () {
        return new SKOSRelatedPropertyImpl(this);
    }

    public SKOSBroaderTransitiveProperty getSKOSBroaderTransitiveProperty () {
        return new SKOSBroaderTransitivePropertyImpl(this);
    }

    public SKOSChangeNoteDataProperty getSKOSChangeNoteDataProperty() {
        return new SKOSChangeNoteDataPropertyImpl(this);
    }

    public SKOSChangeNoteObjectProperty getSKOSChangeNoteObjectProperty() {
        return new SKOSChangeNoteObjectPropertyImpl(this);
    }

    public SKOSCloseMatchProperty getSKOSCloseMatchProperty() {
        return new SKOSCloseMatchPropertyImpl(this);
    }

    public SKOSDefinitionDataProperty getSKOSDefinitionDataProperty() {
        return new SKOSDefinitionDataPropertyImpl(this);
    }

    public SKOSDefinitionObjectProperty getSKOSDefinitionObjectProperty() {
        return new SKOSDefinitionObjectPropertyImpl(this);
    }

    public SKOSEditorialNoteDataProperty getSKOSEditorialNoteDataProperty() {
        return new SKOSEditorialNoteDataPropertyImpl(this);
    }

    public SKOSEditorialNoteObjectProperty getSKOSEditorialNoteObjectProperty() {
        return new SKOSEditorialNoteObjectPropertyImpl(this);
    }

    public SKOSNarrowerTransitiveProperty getSKOSNarrowerTransitveProperty () {
        return new SKOSNarrowerTransitivePropertyImpl(this);
    }

    public SKOSSeeLabelRelationProperty getSKOSSeeLabelRelationProperty () {
        return new SKOSSeeLabelRelationPropertyImpl(this);
    }

    public List<SKOSEntityAssertion> getSKOSEntityAssertions(Collection<SKOSEntity> skosEntities) {
        List<SKOSEntityAssertion> assertions = new ArrayList<SKOSEntityAssertion>();
        for (SKOSEntity entity : skosEntities) {
            assertions.add(this.getSKOSEntityAssertion(entity));
        }
        return assertions;
    }

    public SKOSMemberProperty getSKOSMemberProperty () {
        return new SKOSMemberPropertyImpl(this);
    }

    public SKOSMemberListProperty getSKOSMemberListProperty () {
        return new SKOSMemberListPropertyImpl(this);
    }

    public SKOSBroadMatchProperty getSKOSBroadMatchProperty () {
        return new SKOSBroadMatchPropertyImpl(this);
    }

    public SKOSNarrowMatchProperty getSKOSNarrowMatchProperty () {
        return new SKOSNarrowMatchPropertyImpl(this);
    }

    public SKOSExactMatchProperty getSKOSExactMatchProperty () {
        return new SKOSExactMatchPropertyImpl(this);
    }

    public SKOSExampleDataProperty getSKOSExampleDataProperty() {
        return new SKOSExampleDataPropertyImpl(this);
    }

    public SKOSExampleObjectProperty getSKOSExampleObjectProperty() {
        return new SKOSExampleObjectPropertyImpl(this);
    }

    public SKOSRelatedMatchProperty getSKOSRelatedMatchProperty () {
        return new SKOSRelatedMatchPropertyImpl(this);
    }

//    public OWLObjectProperty getSKOSMemberRelationProperty () {
//        return new SKOSMemberRelationProperty(this).getProperty();
//    }

    public SKOSInSchemeProperty getSKOSInSchemeProperty () {
        return new SKOSInSchemePropertyImpl(this);
    }

    public SKOSMappingRelationProperty getSKOSMappingRelationProperty() {
        return new SKOSMappingRelationPropertyImpl(this);
    }

    public SKOSPrefLabelProperty getSKOSPrefLabelProperty () {
        return new SKOSPrefLabelPropertyImpl(this);
    }

    public SKOSAltLabelProperty getSKOSAltLabelProperty () {
        return new SKOSAltLabelPropertyImpl(this);
    }

    public SKOSHiddenLabelProperty getSKOSHiddenLabelProperty () {
        return new SKOSHiddenLabelPropertyImpl(this);
    }

    public SKOSHistoryNoteDataProperty getSKOSHistoryNoteDataProperty() {
        return new SKOSHistoryNoteDataPropertyImpl(this);
    }

    public SKOSHistoryNoteObjectProperty getSKOSHistoryNoteObjectProperty() {
        return new SKOSHistoryNoteObjectPropertyImpl(this);
    }

    public List<SKOSObjectRelationAssertion> getSKOSObjectRelationAssertions(Set<? extends SKOSEntity> subjects, SKOSObjectProperty property, SKOSEntity object) {
        List<SKOSObjectRelationAssertion> assertions = new ArrayList<SKOSObjectRelationAssertion>();
        for (SKOSEntity entity : subjects) {
            assertions.add(this.getSKOSObjectRelationAssertion(entity, property, object));
        }
        return assertions;
    }


    public SKOSObjectRelationAssertion getSKOSObjectRelationAssertion(SKOSEntity con1, SKOSObjectProperty prop, SKOSEntity con2) {
        return new SKOSObjectRelationAssertionImpl(this, con1, prop, con2, converter);
    }

    public SKOSDataRelationAssertion getSKOSDataRelationAssertion(SKOSEntity con1, SKOSDataProperty prop, String string) {
        SKOSLiteral literal = getSKOSUntypedConstant(string, "");
        return new SKOSDataRelationAssertionImpl(this, con1, prop, literal);
    }

    public SKOSDataRelationAssertion getSKOSDataRelationAssertion(SKOSEntity con1, SKOSDataProperty prop, String string, String lang) {
        SKOSLiteral literal = getSKOSUntypedConstant(string, lang);
        return new SKOSDataRelationAssertionImpl(this, con1, prop, literal);
    }

    public SKOSDataRelationAssertion getSKOSDataRelationAssertion(SKOSEntity con1, SKOSDataProperty prop, SKOSLiteral literal) {
        return new SKOSDataRelationAssertionImpl(this, con1, prop, literal);
    }

    public SKOSAnnotationAssertion getSKOSAnnotationAssertion(SKOSEntity con1, SKOSAnnotation anno) {
        return new SKOSAnnotationAssertionImpl(this, con1, anno);
    }

    public SKOSLabelRelation getSKOSLabelRelation(URI uri, List<SKOSLiteral> literals) {
        return new SKOSLabelRelationImpl(this, uri, literals);
    }

    public SKOSObjectRelationAssertion getSKOSLabelRelationAssertion(SKOSConcept concept2, SKOSLabelRelation labRel) {
        return null;
    }

    public SKOSObjectRelationAssertion getSKOSLabelRelationAssertion(SKOSConcept concept2, SKOSObjectProperty labelRelation, SKOSLabelRelation labRel) {
        return null;
    }

}
