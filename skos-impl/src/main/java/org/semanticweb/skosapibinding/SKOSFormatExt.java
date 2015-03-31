package org.semanticweb.skosapibinding;

import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormatFactory;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TurtleDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.skos.SKOSFormat;
import org.semanticweb.skos.SKOSUnkownFormatException;

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
 * Date: Aug 27, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public enum SKOSFormatExt implements SKOSFormat {

//    RDFXML(new OWLXMLVocabulary());

    RDFXML(new RDFXMLDocumentFormatFactory()) ,

    TURTRLE(new TurtleDocumentFormatFactory()),

    OWLXML(new OWLXMLDocumentFormatFactory()),

    MOS(new ManchesterSyntaxDocumentFormatFactory());

    private OWLDocumentFormatFactory factory;


    SKOSFormatExt(OWLDocumentFormatFactory factory) {
        this.factory=factory;
    }


    public OWLDocumentFormat getFormat() throws SKOSUnkownFormatException {
        return factory.createFormat();
    }
}
