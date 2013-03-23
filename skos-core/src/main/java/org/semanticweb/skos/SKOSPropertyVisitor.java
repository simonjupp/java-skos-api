package org.semanticweb.skos;

import org.semanticweb.skos.properties.*;
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
 * Date: Sep 5, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public interface SKOSPropertyVisitor {

    void visit (SKOSAltLabelProperty property);

    void visit (SKOSBroaderProperty property);

    void visit (SKOSBroaderTransitiveProperty property);

    void visit (SKOSBroadMatchProperty property);

    void visit (SKOSChangeNoteDataProperty property);

    void visit (SKOSChangeNoteObjectProperty property);

    void visit (SKOSCloseMatchProperty property);

    void visit (SKOSDefinitionDataProperty property);

    void visit (SKOSDefinitionObjectProperty property);

    void visit (SKOSEditorialNoteDataProperty property);

    void visit (SKOSEditorialNoteObjectProperty property);

    void visit (SKOSExactMatchProperty property);

    void visit (SKOSExampleDataProperty property);

    void visit (SKOSExampleObjectProperty property);

    void visit (SKOSHasTopConceptProperty property);

    void visit (SKOSHiddenLabelProperty property);

    void visit (SKOSHistoryNoteDataProperty property);

    void visit (SKOSHistoryNoteObjectProperty property);

    void visit (SKOSInSchemeProperty property);

    void visit (SKOSMappingRelationProperty property);

    void visit (SKOSMemberProperty property);

    void visit (SKOSMemberListProperty property);

    void visit (SKOSNarrowMatchProperty property);

    void visit (SKOSNarrowerProperty property);

    void visit (SKOSNarrowerTransitiveProperty property);

    void visit (SKOSNotationProperty property);

    void visit (SKOSNoteDataProperty property);

    void visit (SKOSNoteObjectProperty property);

    void visit (SKOSPrefLabelProperty property);

    void visit (SKOSRelatedProperty property);

    void visit (SKOSRelatedMatchProperty property);

    void visit (SKOSScopeNoteDataProperty property);

    void visit (SKOSScopeNoteObjectProperty property);

    void visit (SKOSSemanticRelationProperty property);

    void visit (SKOSTopConceptOfProperty property);

    void visit (SKOSObjectProperty property);

    void visit (SKOSDataProperty property);

    void visit (SKOSAnnotationProperty property);
}
