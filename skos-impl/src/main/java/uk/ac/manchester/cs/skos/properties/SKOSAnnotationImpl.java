package uk.ac.manchester.cs.skos.properties;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.skos.SKOSAnnotation;
import org.semanticweb.skos.SKOSEntity;
import org.semanticweb.skos.SKOSLiteral;
import org.semanticweb.skos.SKOSObjectVisitor;
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
 * Date: Sep 3, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOSAnnotationImpl implements SKOSAnnotation {

    private URI uri;

    private SKOSLiteral literal;

    private SKOSEntity entity;

    private OWLAnnotation owlAnnotation;

    boolean isConstant;
    public SKOSAnnotationImpl (OWLDataFactory factory, URI uri, SKOSLiteral literal) {
        this.uri = uri;
        this.entity = null;
        this.literal = literal;
        SKOStoOWLConverter converter = new SKOStoOWLConverter();

        OWLLiteral owlLiteral = converter.getAsOWLConstant(literal);
        if (!owlLiteral.isRDFPlainLiteral()) {
            owlAnnotation = factory.getOWLAnnotation(
                    factory.getOWLAnnotationProperty(IRI.create(uri)), factory.getOWLLiteral(owlLiteral.getLiteral(), owlLiteral.getDatatype()));
        }
        else {
            owlAnnotation = factory.getOWLAnnotation(
                    factory.getOWLAnnotationProperty(IRI.create(uri)), owlLiteral);

        }

//        ConstantAnnotation(uri, converter.getAsOWLConstant(literal));
        isConstant = true;
    }

    public SKOSAnnotationImpl (OWLDataFactory factory, URI uri, SKOSEntity entity) {
        this.uri = uri;
        this.literal = null;
        this.entity = entity;
        SKOStoOWLConverter converter = new SKOStoOWLConverter();
        owlAnnotation = factory.getOWLAnnotation(
                factory.getOWLAnnotationProperty(IRI.create(uri)), IRI.create(entity.getURI()));


        OWLAnnotationValue v ;

//        (factory.getOWLAnnotationProperty(IRI.create(uri)), converter.getAsOWLIndiviudal(entity));
        isConstant = false;
    }


    public URI getURI() {
        return uri;
    }

    public OWLAnnotation getAsOWLAnnotation () {
        return owlAnnotation;
    }

    public SKOSLiteral getAnnotationValueAsConstant() {
        return literal;
    }

    public SKOSEntity getAnnotationValue() {
        return entity;
    }

    public boolean isAnnotationByConstant() {
        return isConstant;
    }

    public void accept(SKOSObjectVisitor visitor) {
        visitor.visit(this);
    }
}
