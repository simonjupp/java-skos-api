package org.semanticweb.skos;

import java.net.URI;
import java.util.List;
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
 * Date: Apr 25, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public interface SKOSContentManager {
    /**
     * A conveniance method which applies a list of AddAssertion objects (changes) to some or all the vocabularies that are managed by this Manager
     *
     * @param change The changes to be applied
     * @return OWLEntity, the List of changes that were actually applied
     * @throws org.semanticweb.owl.model.OWLOntologyChangeException If one or more of the changes could not be applied.
     */
    List<SKOSChange> applyChanges(List<SKOSChange> change) throws SKOSChangeException;

    /**
     * Applies a single AddAssertion object (change) to some or all the vocabularies that are managed by this Manager
     *
     * @param change The changes to be applied
     * @return OWLEntity, the List of changes that were actually applied
     * @throws org.semanticweb.owl.model.OWLOntologyChangeException If one or more of the changes could not be applied.
     */
    List<SKOSChange> applyChange(SKOSChange change) throws SKOSChangeException;

    /**
     * Creates a new (empty) SKOS vocabulary that has the specified ontology URI. The SKOS API uses an OWLOntology
     * to contain single or multiple Concept Schemes. This URI is the base URI for that Ontology.
     * @param uri The URI of the ontology to be created.  The ontology
     * URI will be mapped to a physical URI in order to determine the type of
     * ontology factory that will be used to create the ontology.  If this mapping
     * is <code>null</code> then a default (in memory) implementation of the
     * ontology will most likely be created.
     * @return The newly created SKOSDataset
     * @throws org.semanticweb.owl.model.OWLOntologyCreationException If the ontology could not be created.
     */
    SKOSDataset createSKOSDataset(URI uri) throws SKOSCreationException;

    /**
     * Loads the ontology specified by the <code>uri</code>
     * parameter.  Note that this is <b>NOT</b> the physical URI that
     * points to a concrete representation (e.g. an RDF/XML OWL file)
     * of an ontology.  The mapping to a physical URI will be determined
     * by using one of the loaded <code>OWLOntologyURIMapper</code>s.
     * Again, it is important to realise that the ontology being loaded is the container for your SKOSVocabularies
     * @param uri The ontology URI (sometimes called logical URI
     * of the ontology to be loaded)
     * @return The <code>SKOSDataset</code> represented in the Ontology
     * that was loaded.  If an ontology with the specified URI is already loaded
     * then that ontology will be returned.
     * @throws org.semanticweb.owl.model.OWLOntologyCreationException If there was a problem in creating and
     * loading the ontology.
     */
    SKOSDataset loadDataset(URI uri) throws SKOSCreationException;

    /**
     * A convenience method that load an ontology from an input source.  If the ontology
     * contains imports then the appropriate mappers should be set up before calling this
     * method.
     * @param inputSource
     * @return SKOSDataset
     * @throws org.semanticweb.owl.model.OWLOntologyCreationException
     */
    SKOSDataset loadDataset(SKOSInputSource inputSource) throws SKOSCreationException;

    /**
     * A convenience method that loads an ontology from a physical URI.  If
     * the ontology contains imports, then the appropriate mappers should
     * be set up before calling this method.
     * @param uri The physical URI which points to a concrete representation
     * of an ontology. (e.g a physical URI could be file:/home/simon/myvocab.rdf)
     * @return The SKOSDataset conatined in the loaded ontology
     * @throws org.semanticweb.owl.model.OWLOntologyCreationException If the ontology could not be created and loaded.
     */
    SKOSDataset loadDatasetFromPhysicalURI(URI uri) throws SKOSCreationException;

    /**
     * Return a SKOSDataFactory for this Manager. The <code>SKOSDataFactory</code> is used to
     * construct SKOS object (for example a SKOSConcept)
     * @return SKOSDataFactory
     */
    SKOSDataFactory getSKOSDataFactory();

    void save(SKOSDataset vocab) throws SKOSStorageException;

    /**
     * Saves the specified ontology, using the specified URI to determine where/how the ontology
     * should be saved.
     * @param vocab The vocabulary to be saved.
     * @param uri The physical URI which will be used to determine how and where the
     * ontology will be saved.
     * @throws org.semanticweb.owl.model.OWLOntologyStorageException If the ontology cannot be saved.
     */
    void save(SKOSDataset vocab, URI uri) throws SKOSStorageException;

    /**
     * Saves the specified ontology, using the specified URI to determine where/how the ontology
     * should be saved.
     * @param dataset The vocabulary to be saved.
     * @param uri The physical URI which will be used to determine how and where the
     * ontology will be saved.
     * @throws org.semanticweb.owl.model.OWLOntologyStorageException If the ontology cannot be saved.
     */
    void save(SKOSDataset dataset, SKOSFormat format, URI uri) throws SKOSStorageException;


}
