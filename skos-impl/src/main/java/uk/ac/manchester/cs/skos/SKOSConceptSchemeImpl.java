package uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.skos.*;
import org.semanticweb.skos.extensions.SKOSLabelRelation;

import java.net.URI;
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
public class SKOSConceptSchemeImpl implements SKOSConceptScheme {

    OWLNamedIndividual ind;

    public SKOSConceptSchemeImpl(OWLNamedIndividual ind) {
        this.ind = ind;
    }

    public SKOSConceptSchemeImpl(OWLDataFactory factory, URI uri) {
        ind = factory.getOWLNamedIndividual(IRI.create(uri));
    }

    public OWLNamedIndividual getAsOWLIndividual() {
        return ind;
    }

    public Set<SKOSConcept> getTopConcepts(SKOSDataset vocab) {
        return vocab.getTopConcepts(this);
    }

    public Set<SKOSConcept> getConceptsInScheme (SKOSDataset vocab) {
        return vocab.getConceptsInScheme(this);
    }

    public Set<SKOSConceptScheme> getImportedConceptSchemes(SKOSDataset vocab) {
        return null;
    }

    public boolean isSKOSConcept() {
        return false;
    }

    public SKOSConcept asSKOSConcept() {
        throw new OWLRuntimeException("Not an SKOS Concept");
    }

    public boolean isSKOSConceptScheme() {
        return true;
    }

    public SKOSConceptScheme asSKOSConceptScheme() {
        return this;
    }

    public boolean isSKOSLabelRelation() {
        return false;
    }

    public SKOSLabelRelation asSKOSLabelRelation() {
        throw new OWLRuntimeException("Not a SKOS Label Relation");
    }

    public boolean isSKOSResource() {
        return false;
    }

    public SKOSResource asSKOSResource() {
        throw new OWLRuntimeException("Not a SKOS Resource");
    }

    public URI getURI() {
        return ind.asOWLNamedIndividual().getIRI().toURI();
    }

    public Set<SKOSAnnotation> getSKOSAnnotations(SKOSDataset dataset) {
        return dataset.getSKOSAnnotations(this);
    }

    public void accept(SKOSEntityVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(SKOSObjectVisitor visitor) {
        visitor.visit(this);
    }
}
