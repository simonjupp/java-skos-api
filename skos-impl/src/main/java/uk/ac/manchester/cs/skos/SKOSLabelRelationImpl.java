package uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.skos.*;
import org.semanticweb.skos.extensions.SKOSLabelRelation;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
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
public class SKOSLabelRelationImpl implements SKOSEntity, SKOSLabelRelation {

    private OWLNamedIndividual ind;
    private OWLDataProperty labelRel;
    private List<OWLLiteral> labels;
    private Set<OWLAxiom> labelAxioms;
    private URI uri;

    public SKOSLabelRelationImpl(OWLNamedIndividual individual) {
        this.ind = individual;
    }

    public SKOSLabelRelationImpl(OWLDataFactory factory, URI uri, List<SKOSLiteral> labels) {
        labelAxioms = new HashSet<OWLAxiom>();
//        this.labels = labels;
        this.uri = uri;

        ind = factory.getOWLNamedIndividual(IRI.create(uri));
        OWLClass cls = factory.getOWLClass(IRI.create(SKOSRDFVocabulary.LABELRELATION.getURI()));
        labelAxioms.add(factory.getOWLClassAssertionAxiom(cls, ind));

        labelRel = factory.getOWLDataProperty(IRI.create(SKOSRDFVocabulary.LABELRELATED.getURI()));
//        Iterator<OWLConstant> it = labels.iterator();

//        while (it.hasNext()) {
//            labelAxioms.add(factory.getOWLDataPropertyAssertionAxiom(ind, labelRel, it.next()));
//        }
    }

    public SKOSLabelRelationImpl(OWLNamedIndividual subject, OWLDataProperty property, List<OWLLiteral> labels ) {
        this.ind = subject;
        this.labelRel = property;
        this.labels = labels;
    }


    public SKOSDataProperty getLabelProperty () {
        return null;
    }

    public List<SKOSLiteral> getLabels () {
        return null;
    }

    public Set<OWLAxiom> getOWLAxioms() {
        return labelAxioms;
    }

    public boolean isSKOSConcept() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SKOSConceptImpl asSKOSConcept() {
        throw new OWLRuntimeException("Not a SKOS Concept");

    }

    public boolean isSKOSConceptScheme() {
        return false ;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SKOSConceptScheme asSKOSConceptScheme() {
        throw new OWLRuntimeException("Not a SKOS Concept Scheme");
    }

    public boolean isSKOSLabelRelation() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SKOSLabelRelationImpl asSKOSLabelRelation() {
        return this;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isSKOSResource() {
        return false;
    }

    public SKOSResource asSKOSResource() {
        throw new OWLRuntimeException("Not a SKOS Resource");
    }

    public OWLNamedIndividual getAsOWLIndividual() {
        return ind;
    }

    public URI getURI() {
        return ind.getIRI().toURI();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<SKOSAnnotation> getSKOSAnnotations(SKOSDataset dataset) {
        return dataset.getSKOSAnnotations(this);
    }

    public void accept(SKOSEntityVisitor visitor) {
//        visitor.visit(this);
    }

    public void accept(SKOSObjectVisitor visitor) {
        visitor.visit(this);
    }
}
