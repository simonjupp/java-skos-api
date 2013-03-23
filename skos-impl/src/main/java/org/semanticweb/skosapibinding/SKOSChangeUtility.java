package org.semanticweb.skosapibinding;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.skos.*;
import uk.ac.manchester.cs.skos.*;
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
 * Date: Aug 22, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOSChangeUtility implements SKOSAssertionVisitor {

    OWLAxiom ax;
    SKOSDatasetImpl dataSet;

    public SKOSChangeUtility(SKOSDataset dataSet, SKOSAssertion as) {
        this.dataSet = (SKOSDatasetImpl) dataSet;
        as.accept(this);

        // check ax...


    }

    public AddAxiom getAddAxiom () {
        return new AddAxiom(dataSet.getAsOWLOntology(), ax);
    }

    public RemoveAxiom getRemoveAxiom () {
        return new RemoveAxiom(dataSet.getAsOWLOntology(), ax);
    }

    public OWLAxiom getAxiom() {
        return ax;
    }


    public void visit(SKOSObjectRelationAssertion skosObjectRelationAssertion) {
        SKOSObjectRelationAssertionImpl ass = (SKOSObjectRelationAssertionImpl) skosObjectRelationAssertion;
        ax = ass.getAssertionAxiom();
    }

    public void visit(SKOSDataRelationAssertion skosDataRelationAssertion) {
        SKOSDataRelationAssertionImpl ass = (SKOSDataRelationAssertionImpl) skosDataRelationAssertion;
        ax = ass.getAssertionAxiom();
    }

    public void visit(SKOSAnnotationAssertion skosAnnotationAssertion) {
        SKOSAnnotationAssertionImpl ass = (SKOSAnnotationAssertionImpl) skosAnnotationAssertion;
        ax = ass.getAssertionAxiom();
    }

    public void visit(SKOSEntityAssertion skosEntityAssertion) {
        SKOSEntityAssertionImpl ass = (SKOSEntityAssertionImpl) skosEntityAssertion;
        ax = ass.getAssertionAxiom();
    }

}

