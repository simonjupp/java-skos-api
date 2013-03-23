package uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
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
 * Date: Mar 12, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOSDataRelationAssertionImpl implements SKOSDataRelationAssertion {

    OWLDataPropertyAssertionAxiom axiom;
    SKOSEntity subject;
    SKOSDataProperty property;
    SKOSLiteral literal;

    public SKOSDataRelationAssertionImpl(OWLDataFactory dataFactory, SKOSEntity subject, SKOSDataProperty property, SKOSLiteral value) {
        this.subject = subject;
        this.property = property;
        this.literal = value;
        SKOStoOWLConverter converter = new SKOStoOWLConverter();
        axiom = dataFactory.getOWLDataPropertyAssertionAxiom(converter.getAsOWLDataProperty(property), converter.getAsOWLIndiviudal(subject), converter.getAsOWLConstant(value));
    }

    public SKOSDataRelationAssertionImpl(SKOSDataFactoryImpl dataFactory, SKOSEntity subject, SKOSDataProperty property, SKOSLiteral value) {
        this.subject = subject;
        this.property = property;
        this.literal = value;
        SKOStoOWLConverter converter = new SKOStoOWLConverter();
        axiom = dataFactory.getOWLDataPropertyAssertionAxiom(converter.getAsOWLDataProperty(property), converter.getAsOWLIndiviudal(subject), converter.getAsOWLConstant(value));

    }

    public SKOSEntity getSKOSSubject() {
        return subject;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SKOSLiteral getSKOSObject() {
        return literal;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SKOSDataProperty getSKOSProperty() {
        return property;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SKOSEntity getReferencedEntities() {
        return null;
    }

    public void accept(SKOSAssertionVisitor visitor) {
        visitor.visit(this);
    }

    public OWLDataPropertyAssertionAxiom getAssertionAxiom() {
        return axiom;
    }

    public void accept(SKOSObjectVisitor visitor) {
        visitor.visit(this);
    }
}
