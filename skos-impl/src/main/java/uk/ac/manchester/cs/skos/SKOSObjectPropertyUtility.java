package uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
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
 * Date: Mar 26, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class SKOSObjectPropertyUtility {

    private Map<URI, OWLObjectProperty> narrowerProperties;
    private Map<URI, OWLObjectProperty> broaderProperties;
    private Map<URI, OWLObjectProperty> relatedProperties;
//    private Set<OWLObjectProperty> narrowerProperties;
//    private Set<OWLObjectProperty> narrowerProperties;
//    private Set<OWLObjectProperty> narrowerProperties;
//    private Set<OWLObjectProperty> narrowerProperties;

    private OWLOntologyManager man;
    private Map<URI, OWLObjectProperty> tempProperty;


    public SKOSObjectPropertyUtility (OWLOntologyManager man) {

        tempProperty = new HashMap<URI, OWLObjectProperty>();
        this.man = man;
        narrowerProperties = new HashMap<URI, OWLObjectProperty>();
        broaderProperties = new HashMap<URI, OWLObjectProperty>();
        relatedProperties = new HashMap<URI, OWLObjectProperty>();

//        updateProperties();
    }

    public void updateProperties() {
        for (OWLOntology onto : man.getOntologies()) {
            for (OWLObjectProperty prop : onto.getObjectPropertiesInSignature()) {
                processProperty(prop, onto);
            }
        }
    }

    private void processProperty(OWLObjectProperty prop, OWLOntology ont) {

        if (!prop.getSuperProperties(ont).isEmpty()) {
            tempProperty.put(prop.getIRI().toURI(), prop);
            for(OWLObjectPropertyExpression sup : prop.getSuperProperties(ont)) {
                processProperty(sup.asOWLObjectProperty(), ont);
            }
        }
        else {
            if (prop.asOWLObjectProperty().equals(man.getOWLDataFactory().getOWLObjectProperty(IRI.create(SKOSRDFVocabulary.BROADER.getURI())))) {
                broaderProperties.put(prop.getIRI().toURI(), prop);
                broaderProperties.putAll(tempProperty);
            }
            else if (prop.asOWLObjectProperty().equals(man.getOWLDataFactory().getOWLObjectProperty(IRI.create(SKOSRDFVocabulary.NARROWER.getURI())))) {
                narrowerProperties.put(prop.getIRI().toURI(), prop);
                narrowerProperties.putAll(tempProperty);
            }
            else if (prop.asOWLObjectProperty().equals(man.getOWLDataFactory().getOWLObjectProperty(IRI.create(SKOSRDFVocabulary.RELATED.getURI())))) {
                relatedProperties.put(prop.getIRI().toURI(), prop);
                relatedProperties.putAll(tempProperty);
            }
            tempProperty.clear();
        }

    }


    public boolean isNarrowerProperty(URI uri) {
        if (narrowerProperties.containsKey(uri)) {
            return true;
        }
        return false;
    }

    public boolean isBroaderProperty(URI uri) {
        if (broaderProperties.containsKey(uri)) {
            return true;
        }
        return false;
    }

    public boolean isRelatesProperty(URI uri) {
        if (relatedProperties.containsKey(uri)) {
            return true;
        }
        return false;
    }

    public String toString () {

        StringBuilder tmp = new StringBuilder();
        for (OWLObjectProperty prop : narrowerProperties.values()) {
            tmp.append("Narrower: " + prop.getIRI().getFragment() + "\n");
        }
        for (OWLObjectProperty prop : broaderProperties.values()) {
            tmp.append("Broader: " + prop.getIRI().getFragment() + "\n");
        }
        for (OWLObjectProperty prop : relatedProperties.values()) {
            tmp.append("Related: " + prop.getIRI().getFragment() + "\n");
        }

        return tmp.toString();
    }

}
