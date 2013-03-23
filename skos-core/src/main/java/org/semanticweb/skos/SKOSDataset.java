package org.semanticweb.skos;

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
 * Date: Apr 21, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * <br>
 *
 * A <code>SKOSDataset</code> is the central point of access to your SKOS vocabularies.
 * The SKOS vocabulary is managed by a <code>SKOSManager</code>. A SKOSDataset can be thought
 * of as an OWL Ontology which conatins a single or multiple SKOS concept schemes.<br>
 * Because the SKOS API is built on top of the OWL API we use an OWLOntology to represent our SKOS Vocabularies,
 * you always have access to the underlying OWLOntology, but in most cases you should be fine just using the SKOSDataset directly.
 */
public interface SKOSDataset {
    /**
     * Query the vocabulary for a Entity, this will query the URI fragment of the entity URI.
     * In addition it will query the prefLAbel, altLabel and hiddenLabel value.
     * @param shortForm
     * @return SKOSEntity A SKOS enity such as a SKOSConcept, SKOSConceptScheme or SKOSLabelRelation
     */
    SKOSEntity getSKOSEntity(String shortForm);

 
    /**
     * This is the URI for the OWL Ontology that contains the SKOSVocabularies
     * @return URI Returns the URI for the Ontology that contains the SKOS vocabularies
     */
    URI getURI();

    /**
     * Get the set of Concept Schemes in this vocabulary
     * @return Set<SKOSConceptScheme>
     */
    Set<SKOSConceptScheme> getSKOSConceptSchemes();

    /**
     * The set of Concepts in this vocabualry
     * @return Set<SKOSConcept>
     */
    Set<SKOSConcept> getSKOSConcepts();

    /**
     * Convenience method for getting the set of Concepts that are top concepts in a particular Concept Scheme
     * @param scheme The concept scheme you want the top cponcepts from
     * @return Set<SKOSConcept> All concepts at the top of this concept scheme
     */
    Set<SKOSConcept> getTopConcepts(SKOSConceptScheme scheme);

    /**
     * Convenience method for getting the set of Concept that are asserted to be in this scheme
     * @param scheme The concept scheme that contains the concepts
     * @return Set<SKOSConcept> The set of concepts in that scheme
     */
    Set<SKOSConcept> getConceptsInScheme (SKOSConceptScheme scheme);

    /**
     * Get the set of SKOS entities related to the current entity by SKOS object properties
     * @param skosEntity The entity you want to query
     * @return SKOSObjectRelationAssertion The set of object relations asserted on this entity
     */
    Set<SKOSObjectRelationAssertion> getSKOSObjectRelationAssertions(SKOSEntity skosEntity);

    /**
     * Get the set of SKOS object assertions that invove the current SKOS entity
     * @param skosEntity The entity you want to query
     * @return SKOSObjectRelationAssertion The set of object relations asserted on this entity
     */
    Set<SKOSObjectRelationAssertion> getReferencingSKOSObjectRelationAssertions(SKOSEntity skosEntity);

    /**
     * Get the set of SKOS entities related to the current entity by SKOS data properties
     * @param skosEntity The entity you want to query
     * @return Set<SKOSDataRelationAssertion> Set of Data relation assertions.
     */
    Set<SKOSDataRelationAssertion> getSKOSDataRelationAssertions(SKOSEntity skosEntity);

    /**
     * Get the set of SKOS object assertions that invove the current SKOS entity
     * @param skosEntity The entity you want to query
     * @return SKOSObjectRelationAssertion The set of object relations asserted on this entity
     */
    Set<SKOSDataRelationAssertion> getReferencingSKOSDataRelationAssertions(SKOSEntity skosEntity);

    /**
     * Get the set of SKOS entities related to a particular concept by a SKOS object property
     * @param entity The concept you want to query
     * @param property The SKOSObject property to follow
     * @return Set<SKOSEntity> Set of SKOS entities related by the specified property
     */
    Set<SKOSEntity> getSKOSObjectRelationByProperty(SKOSEntity entity, SKOSObjectProperty property);


    /**
     * Get the set of SKOS entities related to a particular concept by a SKOS data property
     * @param entity The concept you want to query
     * @param property The SKOSData property to follow
     * @return Set<SKOSEntity> Set of SKOS entities related by the specified property
     */
    Set<SKOSLiteral> getSKOSDataRelationByProperty(SKOSEntity entity, SKOSDataProperty property);

    /**
     * Get the set of SKOS annotations by URI asserted on a SKOSEntity
     * @param entity The entity you want to query
     * @param annotationURI The URI for the SKOS annotation
     * @return Set<SKOSAnnotation> Set of SKOSAnnotations
     */
    Set<SKOSAnnotation> getSKOSAnnotationsByURI(SKOSEntity entity, URI annotationURI);

    /**
     * Get the set of SKOS annotations asserted on a SKOS entity
     * @param entity The entity you want to query
     * @return Set<SKOSAnnotation> Set of SKOSAnnotations
     */
    Set<SKOSAnnotation> getSKOSAnnotations(SKOSEntity entity);

    /**
     * Get the set of label relations asserted on a particular concept
     * @param concept The concept you want to query
     * @return Set<SKOSLabelRelation> Set of Label Relations
     */
    Set<SKOSLabelRelation> getSKOSLabelRelations(SKOSConcept concept);

}
