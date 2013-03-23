package uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.skos.*;
import org.semanticweb.skosapibinding.SKOStoOWLConverter;
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
 * Date: Apr 22, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOSAnnotationAssertionImpl implements SKOSAnnotationAssertion {

    OWLAnnotationAxiom ax;
    SKOSEntity subject;
    SKOSAnnotation annotation;
    public SKOSAnnotationAssertionImpl(OWLDataFactory factory, SKOSEntity subject, SKOSAnnotation annotation) {

        SKOStoOWLConverter converter = new SKOStoOWLConverter();
        this.subject = subject;
        this.annotation = annotation;

        ax = factory.getOWLAnnotationAssertionAxiom(converter.getAsOWLIndiviudal(subject).getIRI(), converter.getAsOWLAnnotation(annotation));
    }

    public SKOSEntity getSubject() {
        return subject;
    }

    public SKOSAnnotation getSKOSAnnotation() {
        return annotation;
    }

    public OWLAnnotationAxiom getAssertionAxiom() {
        return ax;
    }

    public SKOSEntity getReferencedEntities() {
        return null; 
    }

    public void accept(SKOSAssertionVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(SKOSObjectVisitor visitor) {
        visitor.visit(this);
    }
}
