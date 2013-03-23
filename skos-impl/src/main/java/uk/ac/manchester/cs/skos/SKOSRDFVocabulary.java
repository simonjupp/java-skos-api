package uk.ac.manchester.cs.skos;

import java.net.URI;
import java.util.HashSet;
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
 */
public enum SKOSRDFVocabulary {

    LABELRELATED("labelRelated"),

    MEMBER("member"),

    MEMBERLIST("memberList"),

    MAPPINGRELATION("mappingRelation"),

    BROADMATCH("broadMatch"),

    NARROWMATCH("narrowMatch"),

    RELATEDMATCH("relatedMatch"),

    EXACTMATCH("exactMatch"),

    BROADER("broader"),

    NARROWER("narrower"),

    BROADERTRANS("broaderTransitive"),

    NARROWERTRANS("narrowerTransitive"),

    RELATED("related"),

    HASTOPCONCEPT("hasTopConcept"),

    SEMANTICRELATION("semanticRelation"),

    CONCEPT("Concept"),

    LABELRELATION("LabelRelation"),

    SEELABELRELATION("seeLabelRelation"),

    COLLECTION("Collection"),

    CONCEPTSCHEME("ConceptScheme"),

    TOPCONCEPTOF("topConceptOf"),

    INSCHEME("inScheme"),

    CLOSEMATCH("closeMatch"),

    DOCUMENT("Document"),

    IMAGE("Image"),

    ORDEREDCOLLECTION("OrderedCollection"),

    COLLECTABLEPROPERTY("CollectableProperty"),

    RESOURCE("Resource"),

    PREFLABEL("prefLabel"),

    ALTLABEL("altLabel"),

    COMMENT("comment"),

    EXAMPLE("example"),

    NOTE("note"),

    NOTATION("notation"),

    SCOPENOTE("scopeNote"),

    HIDDENLABEL("hiddenLabel"),

    EDITORIALNOTE("editorialNote"),

    HISTORYNOTE("historyNote"),

    DEFINITION("definition"),

    CHANGENOTE("changeNote");

    public static final Set<URI> ALL_URIS;

    static {
        ALL_URIS = new HashSet<URI>();
        for(SKOSRDFVocabulary v : values()) {
            ALL_URIS.add(v.getURI());
        }
    }

    private String localName;

    private String namespace = "http://www.w3.org/2004/02/skos/core#";

    private URI uri;

    SKOSRDFVocabulary(String localname) {
        this.localName = localname;
        this.uri = URI.create(namespace + localname);
    }


    public String getLocalName() {
        return localName;
    }


    public URI getURI() {
        return uri;
    }

}

