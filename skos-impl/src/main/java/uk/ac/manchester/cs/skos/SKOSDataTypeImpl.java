package uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLRuntimeException;
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
 * Date: Sep 8, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOSDataTypeImpl implements SKOSDataType {

    OWLDatatype type;

    public SKOSDataTypeImpl (SKOSDataFactoryImpl skosDataFactory, URI uri) {
        this.type = skosDataFactory.getOWLDatatype(IRI.create(uri));
    }


    public boolean isSKOSConcept() {
        return false;
    }

    public SKOSConcept asSKOSConcept() {
        throw new OWLRuntimeException("Not a SKOS Concept");

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
        return type.getIRI().toURI();
    }

    public Set<SKOSAnnotation> getSKOSAnnotations(SKOSDataset dataset) {
        return null;
    }

    public OWLDatatype getAsOWLDataType() {
        return type;
    }

    public void accept(SKOSEntityVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(SKOSObjectVisitor visitor) {
        visitor.visit(this);
    }
}
