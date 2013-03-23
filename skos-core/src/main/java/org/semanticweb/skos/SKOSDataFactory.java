package org.semanticweb.skos;

import org.semanticweb.skos.extensions.SKOSLabelRelation;
import org.semanticweb.skos.extensions.SKOSSeeLabelRelationProperty;
import org.semanticweb.skos.properties.*;

import java.net.URI;
import java.util.Collection;
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
 * Date: Apr 21, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * <br>
 * The SKOS Data Factory is the central point of access for creating SKOS objects, these include entities and SKOS assertions
 * 
 */
public interface SKOSDataFactory {

    /* SKOS CLASSES */

    SKOSConcept getSKOSConcept(URI uri);

    SKOSCollection getSKOSCollection(URI uri);

    SKOSConceptScheme getSKOSConceptScheme(URI uri);

    SKOSOrderedCollection getSKOSOrderedCollection(URI uri);

    SKOSResource getSKOSResource(URI uri);
    
    /* SKOS PROPERTIES */

    SKOSObjectProperty getSKOSObjectProperty (URI uri);

    SKOSDataProperty getSKOSDataProperty (URI uri);

    SKOSAltLabelProperty getSKOSAltLabelProperty ();

    SKOSBroadMatchProperty getSKOSBroadMatchProperty();

    SKOSBroaderProperty getSKOSBroaderProperty ();

    SKOSBroaderTransitiveProperty getSKOSBroaderTransitiveProperty ();

    SKOSChangeNoteDataProperty getSKOSChangeNoteDataProperty();

    SKOSChangeNoteObjectProperty getSKOSChangeNoteObjectProperty();

    SKOSCloseMatchProperty getSKOSCloseMatchProperty();

    SKOSDefinitionDataProperty getSKOSDefinitionDataProperty();

    SKOSDefinitionObjectProperty getSKOSDefinitionObjectProperty();

    SKOSEditorialNoteDataProperty getSKOSEditorialNoteDataProperty();

    SKOSEditorialNoteObjectProperty getSKOSEditorialNoteObjectProperty();

    SKOSExactMatchProperty getSKOSExactMatchProperty ();

    SKOSExampleDataProperty getSKOSExampleDataProperty();

    SKOSExampleObjectProperty getSKOSExampleObjectProperty();

    SKOSHasTopConceptProperty getSKOSHasTopConceptProperty ();

    SKOSHiddenLabelProperty getSKOSHiddenLabelProperty ();

    SKOSHistoryNoteDataProperty getSKOSHistoryNoteDataProperty();

    SKOSHistoryNoteObjectProperty getSKOSHistoryNoteObjectProperty();

    SKOSInSchemeProperty getSKOSInSchemeProperty ();

    SKOSMappingRelationProperty getSKOSMappingRelationProperty();

    SKOSMemberProperty getSKOSMemberProperty ();

    SKOSMemberListProperty getSKOSMemberListProperty ();

    SKOSNarrowMatchProperty getSKOSNarrowMatchProperty ();

    SKOSNarrowerProperty getSKOSNarrowerProperty();

    SKOSNarrowerTransitiveProperty getSKOSNarrowerTransitiveProperty();

    SKOSNotationProperty getSKOSNotationProperty();

    SKOSPrefLabelProperty getSKOSPrefLabelProperty ();

    SKOSRelatedProperty getSKOSRelatedProperty ();

    SKOSRelatedMatchProperty getSKOSRelatedMatchProperty ();

    SKOSScopeNoteObjectProperty getSKOSScopeNoteObjectProperty ();

    SKOSScopeNoteDataProperty getSKOSScopeNoteDataProperty ();

    SKOSSemanticRelationProperty getSKOSSemanticRelationProperty();

    SKOSTopConceptOfProperty getSKOSTopConceptOfProperty();

    /* SKOS CONSTANTS */

    SKOSTypedLiteral getSKOSTypedConstant(SKOSDataType type, String literal);

    SKOSUntypedLiteral getSKOSUntypedConstant(String literal, String lang);

    /* SKOS ANNOTATIONS */

    SKOSAnnotation getSKOSAnnotation (URI uri, SKOSLiteral literal);

    SKOSAnnotation getSKOSAnnotation (URI uri, String literal);

    SKOSAnnotation getSKOSAnnotation (URI uri, String literal, String lang);

    SKOSAnnotation getSKOSAnnotation (URI uri, SKOSEntity entity);

    SKOSLabelRelation getSKOSLabelRelation(URI uri, List<SKOSLiteral> literals);

    SKOSSeeLabelRelationProperty getSKOSSeeLabelRelationProperty ();

    // assertions

    List<SKOSEntityAssertion> getSKOSEntityAssertions(Collection<SKOSEntity> skosEntities);

    List<SKOSObjectRelationAssertion> getSKOSObjectRelationAssertions(Set<? extends SKOSEntity> subjects, SKOSObjectProperty property, SKOSEntity object);

    SKOSObjectRelationAssertion getSKOSObjectRelationAssertion(SKOSEntity subject, SKOSObjectProperty prop, SKOSEntity entity);

    SKOSDataRelationAssertion getSKOSDataRelationAssertion(SKOSEntity entity, SKOSDataProperty prop, String string);

    SKOSDataRelationAssertion getSKOSDataRelationAssertion(SKOSEntity entity, SKOSDataProperty prop, String string, String lang);

    SKOSDataRelationAssertion getSKOSDataRelationAssertion(SKOSEntity entity, SKOSDataProperty prop, SKOSLiteral literal);

    SKOSAnnotationAssertion getSKOSAnnotationAssertion(SKOSEntity entity, SKOSAnnotation anno);

    SKOSObjectRelationAssertion getSKOSLabelRelationAssertion(SKOSConcept concept, SKOSLabelRelation labRel);

    SKOSObjectRelationAssertion getSKOSLabelRelationAssertion(SKOSConcept concept, SKOSObjectProperty labelRelation, SKOSLabelRelation labRel);

    SKOSEntityAssertion getSKOSEntityAssertion(SKOSEntity entity);

    SKOSDataType getSKOSDataType(URI uri);

//    SKOSConceptSchemeAssertion getSKOSConceptSchemeAssertion(SKOSConceptScheme scheme);

//    SKOSConceptSchemeAssertion getSKOSConceptSchemeAssertion(URI uri);

//    SKOSConceptAssertion getSKOSConceptAssertion(SKOSConcept concept);

//    SKOSResourceAssertion getSKOSResourceAssertion(SKOSResource concept);

    SKOSAnnotationProperty getSKOSAnnotationProperty(URI uri);
}
