package uk.ac.manchester.cs.skos;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.skos.SKOSDataType;
import org.semanticweb.skos.SKOSLiteralVisitor;
import org.semanticweb.skos.SKOSTypedLiteral;
import org.semanticweb.skos.SKOSUntypedLiteral;
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
public class SKOSTypedLiteralImpl implements SKOSTypedLiteral {

    public String literal;
    public SKOSDataTypeImpl type;
    OWLLiteral constant;

    public SKOSTypedLiteralImpl(OWLDataFactory factory, SKOSDataType dataType, String literal) {
        this.literal = literal;
        this.type = (SKOSDataTypeImpl) dataType;
        constant = factory.getOWLTypedLiteral(literal, type.getAsOWLDataType());
    }

    public String getLiteral() {
        return literal;
    }

    public boolean isTyped() {
        return true;
    }

    public SKOSUntypedLiteral getAsSKOSUntypedLiteral() {
        throw new OWLRuntimeException("Not a SKOS Untyped Constant");
    }

    public SKOSTypedLiteral getAsSKOSTypedLiteral() {
        return this;
    }

    public void accept(SKOSLiteralVisitor visitor) {
        visitor.visit(this);
    }

    public SKOSDataType getDataType() {
        return type;
    }

    public OWLLiteral getAsOWLConstant() {
        return constant;  //To change body of created methods use File | Settings | File Templates.
    }
}
