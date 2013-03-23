package org.semanticweb.skosapibinding;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.skos.*;
import uk.ac.manchester.cs.skos.*;
import uk.ac.manchester.cs.skos.properties.SKOSAnnotationImpl;
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
 * Date: Aug 21, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOStoOWLConverter {

    public SKOStoOWLConverter () {

    }

    public OWLNamedIndividual getAsOWLIndiviudal (SKOSEntity entity) {
        if (entity.isSKOSConcept()) {
            SKOSConceptImpl impl = (SKOSConceptImpl)entity;
            return impl.getAsOWLIndividual();
        }
        else if (entity.isSKOSConceptScheme()) {
            SKOSConceptSchemeImpl impl = (SKOSConceptSchemeImpl)entity;
            return impl.getAsOWLIndividual();
        }
        else if (entity.isSKOSLabelRelation()) {
            SKOSLabelRelationImpl impl = (SKOSLabelRelationImpl)entity;
            return impl.getAsOWLIndividual();
        }
        else if (entity.isSKOSResource()) {
            SKOSResourceImpl impl = (SKOSResourceImpl)entity;
            return impl.getAsOWLIndividual();
        }
        else {
            return null;
        }
    }

    public OWLObjectProperty getAsOWLObjectProperty(SKOSObjectProperty prop) {
        SKOSObjectPropertyImpl impl = (SKOSObjectPropertyImpl) prop;
        return impl.getOWLObjectProperty();
    }

    public OWLAnnotation getAsOWLAnnotation(SKOSAnnotation annotation) {
        SKOSAnnotationImpl impl = (SKOSAnnotationImpl) annotation;
        return impl.getAsOWLAnnotation();

    }

    public OWLAnnotationProperty getAsOWLAnnotationProperty(SKOSAnnotationProperty property) {
        SKOSAnnotationPropertyImpl impl = (SKOSAnnotationPropertyImpl) property;
        return impl.getProperty();
    }

    public OWLDataPropertyExpression getAsOWLDataProperty(SKOSDataProperty prop) {
        SKOSDataPropertyImpl impl = (SKOSDataPropertyImpl) prop;
        return impl.getDataObjectProperty();
    }

    public OWLLiteral getAsOWLConstant(SKOSLiteral literal) {
        if (literal.isTyped()) {
            SKOSTypedLiteralImpl impl= (SKOSTypedLiteralImpl) literal;
            return impl.getAsOWLConstant();
        }
        else {
            SKOSUntypedLiteralImpl impl= (SKOSUntypedLiteralImpl) literal;
            return impl.getAsOWLConstant();
        }
    }


    public OWLOntology getAsOWLOntology(SKOSDataset dataSet) {
        SKOSDatasetImpl impl = (SKOSDatasetImpl) dataSet;
        return impl.getAsOWLOntology();  //To change body of created methods use File | Settings | File Templates.
    }

}
