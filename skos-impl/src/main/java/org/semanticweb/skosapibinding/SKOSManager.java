package org.semanticweb.skosapibinding;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.skos.*;
import uk.ac.manchester.cs.skos.SKOSDataFactoryImpl;
import uk.ac.manchester.cs.skos.SKOSDatasetImpl;

import java.net.URI;
import java.util.*;
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
 * Date: Mar 4, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 *
 * Provides a point of convenience for creating an <code>SKOSDataset</code>
 * with commonly required features (such as an RDF parser for example).
 * The <code>SKOSManager</code> manages a set of SKOS vocabularies.
 * It is the main point for creating, loading and accessing vocabularies.
 */
public class SKOSManager implements SKOSContentManager {

    private final OWLOntologyManager man;

    private Map<URI, SKOSDatasetImpl> skosVocabularies;

    private BidirectionalShortFormProviderAdapter biAdapt;


    /**
     * Creates a new SKOSManager object which is used to manage SKOS vocabularies
     * @throws SKOSCreationException A SKOS creation exception
     */
    public SKOSManager () throws SKOSCreationException {
        this.man = OWLManager.createOWLOntologyManager();
        skosVocabularies = new HashMap<URI, SKOSDatasetImpl>();

    }

    public SKOSManager (OWLOntologyManager manager) {
        this.man = manager;
        skosVocabularies = new HashMap<URI, SKOSDatasetImpl>();

        for (OWLOntology ont : man.getOntologies()) {
            IRI iri = ont.getOntologyID().getOntologyIRI();
            if (iri == null) {
                iri = IRI.generateDocumentIRI();
            }
            skosVocabularies.put(iri.toURI(), new SKOSDatasetImpl(this, ont));
        }
    }

//
//    private void updatePropertiesUtility () {
//        for (SKOSDatasetImpl voc : skosVocabularies.values()) {
//            voc.updatePropertyUtility();
//        }
//    }

    /**
     * As SKOSManager is a wrapper for an OWLOntologyManager, you get access to the actual OWLOntologyManger using this method
     *
     * @return OWLOntologyManager, the actual OWLOntologyManager that is being maniupulated by this class
     */
    public OWLOntologyManager getOWLManger() {
        return man;
    }


    public SKOSDataset createSKOSDataset(URI uri) throws SKOSCreationException {
        SKOSDatasetImpl voc = skosVocabularies.get(uri);
        if (voc == null) {
            voc = new SKOSDatasetImpl(this, uri);
            skosVocabularies.put(uri, voc);
        }
        return voc;
    }

    public SKOSDataset loadDataset(URI uri) throws SKOSCreationException {
        SKOSDatasetImpl voc = skosVocabularies.get(uri);
        if (voc == null) {

            OWLOntology onto = null;

            try {
                onto = man.loadOntology(IRI.create(uri));
            } catch (OWLOntologyCreationException e) {
                throw new SKOSCreationException(e);
            }

            voc = new SKOSDatasetImpl(this, onto);
            skosVocabularies.put(uri, voc);
        }
        return voc;
    }

    public SKOSDataset loadDataset(SKOSInputSource inputSource) throws SKOSCreationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

//    public SKOSDataset loadDataset(SKOSInputSource inputSource) throws SKOSCreationException {
//
//        OWLOntology onto = man.loadOntology(inputSource);
//        SKOSDatasetImpl voc = new SKOSDatasetImpl(this, onto);
//        if (voc.getURI() != null) {
//            skosVocabularies.put(voc.getURI(), voc);
//        }
//        return voc;
//    }

    public SKOSDataset loadDatasetFromPhysicalURI(URI uri) throws SKOSCreationException {

        OWLOntology onto = null;
        try {
            onto = man.loadOntology(IRI.create(uri));
        } catch (OWLOntologyCreationException e) {
            throw new SKOSCreationException(e);
        }

        SKOSDatasetImpl voc = new SKOSDatasetImpl(this, onto);
        if (voc.getURI() != null) {
            System.out.println("new ontology loaded: " + voc.getAsOWLOntology().getOntologyID());
            skosVocabularies.put(voc.getURI(), voc);
        }
        return voc;
    }


    public SKOSDataFactory getSKOSDataFactory() {
        return new SKOSDataFactoryImpl();
    }
//
//    public void save(SKOSDataset vocab) throws OWLOntologyStorageException {
//        man.saveOntology(vocab.getAsOWLOntology());
//    }
//
//    public void save(SKOSDataset vocab, URI uri) throws OWLOntologyStorageException {
//        man.saveOntology(vocab.getAsOWLOntology(), uri);
//    }
    //
    public void save (SKOSDataset vocab, SKOSFormat format) throws SKOSStorageException {
        SKOSDatasetImpl setImpl = (SKOSDatasetImpl) vocab;
        try {
            man.saveOntology(setImpl.getAsOWLOntology(), (OWLOntologyFormat) format.getFormat());
        } catch (OWLOntologyStorageException e) {
            new SKOSStorageException(e);
        } catch (SKOSUnkownFormatException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
}

    public void save (SKOSDataset vocab, SKOSFormat format, URI uri) throws SKOSStorageException {
        SKOSDatasetImpl setImpl = (SKOSDatasetImpl) vocab;
        try {
            man.saveOntology(setImpl.getAsOWLOntology(), new RDFXMLOntologyFormat(), IRI.create(uri));
        } catch (OWLOntologyStorageException e) {
            new SKOSStorageException(e);
        }
    }
//
//    public void saveOntology (SKOSDataset vocab, OWLOntologyFormat format, URI uri) throws OWLOntologyStorageException {
//        man.saveOntology(vocab.getAsOWLOntology(), format, uri);
//    }
//
//    public void saveOntology (SKOSDataset vocab, OWLOntologyOutputTarget target) throws OWLOntologyStorageException {
//        man.saveOntology(vocab.getAsOWLOntology(), target);
//    }
//
//    public void saveOntology (SKOSDataset vocab, OWLOntologyFormat format, OWLOntologyOutputTarget target) throws OWLOntologyStorageException {
//        man.saveOntology(vocab.getAsOWLOntology(), format, target);
//    }


    
    public List<SKOSChange> applyChanges(List<SKOSChange> change) throws SKOSChangeException {

        List<OWLOntologyChange> ch = new ArrayList<OWLOntologyChange>();

        Map<OWLOntologyChange, SKOSChange> newChanges = new HashMap<OWLOntologyChange, SKOSChange>();

        for (SKOSChange skCh : change) {
            SKOSChangeUtility util = new SKOSChangeUtility(skCh.getSKOSDataset(), skCh.getSKOSAssertion());
            if (skCh.isAdd()) {
                AddAxiom addAx = util.getAddAxiom();
                ch.add(addAx);
                newChanges.put(addAx, skCh);
            }
            else {
                RemoveAxiom remAx = util.getRemoveAxiom();
                ch.add(remAx);
                newChanges.put(remAx, skCh);
            }
        }


        List<SKOSChange> succesfulChanges = new ArrayList<SKOSChange>();

        List<OWLOntologyChange> OWLChange;// = new ArrayList<OWLOntologyChange>();
        try {
            OWLChange = man.applyChanges(ch);
        } catch (OWLOntologyChangeException e) {
            throw new SKOSChangeException(newChanges.get(e.getChange()), e);
        }

        for (OWLOntologyChange ch1 : OWLChange) {
            if (newChanges.containsKey(ch1)) {
                succesfulChanges.add(newChanges.get(ch1));
            }
        }
        return succesfulChanges;
    }

    public List<SKOSChange> applyChange(SKOSChange change) throws SKOSChangeException {

        SKOSAssertion as = change.getSKOSAssertion();
        SKOSDataset dataSet = change.getSKOSDataset();

        SKOSChangeUtility util = new SKOSChangeUtility(dataSet, as);

        List<SKOSChange> succesfulChanges = new ArrayList<SKOSChange>();

        List<OWLOntologyChange> OWLChange = new ArrayList<OWLOntologyChange>();
        try {
            OWLAxiomChange cha;
            if (change.isAdd()) {
                AddAxiom addAx = util.getAddAxiom();
                OWLChange = man.applyChange(addAx);
            }
            else if (change.isRemove()) {
                RemoveAxiom remAx = util.getRemoveAxiom();
                OWLChange = man.applyChange(remAx);
            }
        } catch (OWLOntologyChangeException e) {
            throw new SKOSChangeException(change, e);
        }

        return succesfulChanges;
    }

    public void save(SKOSDataset vocab) throws SKOSStorageException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void save(SKOSDataset vocab, URI uri) throws SKOSStorageException {
        SKOSDatasetImpl setImpl = (SKOSDatasetImpl) vocab;
        try {
            man.saveOntology(setImpl.getAsOWLOntology(), IRI.create(uri));
        } catch (OWLOntologyStorageException e) {
            new SKOSStorageException(e);
        }
    }

    public Collection<SKOSDatasetImpl> getSKOSDataSets() {
        return skosVocabularies.values();
    }
}
