package example;

import org.semanticweb.skosapibinding.SKOSManager;
import org.semanticweb.skosapibinding.SKOSFormatExt;
import org.semanticweb.skos.*;

import java.net.URI;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
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
 * Date: Sep 8, 2008<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 */
public class Example3 {

    public static void main(String[] args) {

        /*
        * This example recreates the SKOS dataset in Example2.java, but does so using some convenience methods in fewer lines of code
        *
         */

        try {

            SKOSManager manager = new SKOSManager();

            final String baseURI = "http://www.semanticweb.org/skos/example2.rdf";

            SKOSDataset dataset = manager.createSKOSDataset(URI.create(baseURI));

            SKOSDataFactory df = manager.getSKOSDataFactory();

            List<SKOSEntity> allEntities = new ArrayList<SKOSEntity>();

            // Create a concept scheme identified by a URI
            SKOSConceptScheme conceptScheme1 = df.getSKOSConceptScheme(URI.create(baseURI + "#conceptScheme1"));

            // create a set of concepts

            Set<SKOSConcept> concepts = new HashSet<SKOSConcept>();
            concepts.add(df.getSKOSConcept(URI.create(baseURI + "#conceptA")));
            concepts.add(df.getSKOSConcept(URI.create(baseURI + "#conceptB")));
            concepts.add(df.getSKOSConcept(URI.create(baseURI + "#conceptC")));
            concepts.add(df.getSKOSConcept(URI.create(baseURI + "#conceptD")));

            // add all the entities to this set
            allEntities.add(conceptScheme1);
            allEntities.addAll(concepts);

            List<SKOSEntityAssertion> entityAssertions = df.getSKOSEntityAssertions(allEntities);
            List<SKOSObjectRelationAssertion> relationAssertions = df.getSKOSObjectRelationAssertions(concepts, df.getSKOSInSchemeProperty(), conceptScheme1);

            List<SKOSChange> addAssertions = new ArrayList<SKOSChange>();

            for (SKOSEntityAssertion ass : entityAssertions) {
                addAssertions.add(new AddAssertion(dataset, ass));
            }

            for (SKOSObjectRelationAssertion ass : relationAssertions) {
                addAssertions.add(new AddAssertion(dataset, ass));
            }

            manager.applyChanges(addAssertions);

            manager.save(dataset, SKOSFormatExt.RDFXML, URI.create("file:/Users/simon/tmp/example3.rdf"));

        } catch (SKOSCreationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SKOSChangeException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SKOSStorageException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

}
