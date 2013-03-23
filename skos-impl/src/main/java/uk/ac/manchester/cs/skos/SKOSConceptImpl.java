package uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.skos.*;

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
 * Date: Mar 4, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOSConceptImpl implements SKOSConcept {

    OWLNamedIndividual ind;

    public SKOSConceptImpl(OWLDataFactory factory, URI uri) {
        ind = factory.getOWLNamedIndividual(IRI.create(uri));
    }

    public SKOSConceptImpl(OWLNamedIndividual ind) {
        this.ind = ind;
    }

    public IRI getIRI() {
        return ind.asOWLNamedIndividual().getIRI();
    }

    public URI getURI() {
        return ind.asOWLNamedIndividual().getIRI().toURI();
    }

    public OWLNamedIndividual getAsOWLIndividual() {
        return ind;
    }

    public boolean isSKOSConcept() {
        return true;
    }

    public SKOSConceptImpl asSKOSConcept() {
        return this;
    }

    public boolean isSKOSConceptScheme() {
        return false;
    }

    public SKOSConceptScheme asSKOSConceptScheme() {
        throw new OWLRuntimeException("Not a SKOS Concept Scheme");
    }

    public boolean isSKOSLabelRelation() {
        return false;
    }

    public SKOSLabelRelationImpl asSKOSLabelRelation() {
        throw new OWLRuntimeException("Not a SKOS Label Relation");
    }

    public boolean isSKOSResource() {
        return false;
    }

    public SKOSResource asSKOSResource() {
        throw new OWLRuntimeException("Not a SKOS Resource");
    }

    /* methods for getting SKOS object relations */

    public Set<SKOSObjectRelationAssertion> getObjectRelationAssertions(SKOSDataset dataset) {
        return dataset.getReferencingSKOSObjectRelationAssertions(this);
    }

    public Set<SKOSDataRelationAssertion> getDataRelationAssertions(SKOSDataset dataset) {
        return dataset.getReferencingSKOSDataRelationAssertions(this);
    }

    public Set<SKOSEntity> getSKOSRelatedEntitiesByProperty(SKOSDataset dataset, SKOSObjectProperty property) {
        return dataset.getSKOSObjectRelationByProperty(this, property);
    }

    public Set<SKOSLiteral> getSKOSRelatedConstantByProperty(SKOSDataset dataset, SKOSDataProperty property) {
        return dataset.getSKOSDataRelationByProperty(this, property);
    }

    public Set<SKOSLiteral> getSKOSRelatedConstantByProperty(SKOSDataset dataset, SKOSAnnotationProperty property) {
        Set<SKOSLiteral> literal = new HashSet<SKOSLiteral>();
        for (SKOSAnnotation anno  : dataset.getSKOSAnnotationsByURI(this, property.getURI())) {

            literal.add(anno.getAnnotationValueAsConstant());
        }
        return literal;
    }

    public Set<SKOSAnnotation> getSKOSAnnotations(SKOSDataset dataset) {
        return dataset.getSKOSAnnotations(this);
    }

    public Set<SKOSAnnotation> getSKOSAnnotationsByURI(SKOSDataset dataset, URI uri) {
        return dataset.getSKOSAnnotationsByURI(this, uri);
    }

    public void accept(SKOSEntityVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(SKOSObjectVisitor visitor) {
        visitor.visit(this);
    }
}
