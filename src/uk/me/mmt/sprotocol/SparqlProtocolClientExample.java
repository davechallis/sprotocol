/*
    sprotocol - Java SPARQL Protocol Client Library

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/**
 * Copyright 2011 Mischa Tuffield
 *
 */
package uk.me.mmt.sprotocol;

import uk.me.mmt.sprotocol.SparqlProtocolClient;

public class SparqlProtocolClientExample {

    public static void main(String[] args) {
        if (args.length == 2) {
            if (args[1].startsWith("http")) {
                SparqlProtocolClient sparql = new SparqlProtocolClient( args[1] );
                SelectResultSet sparqlResults = sparql.executeSelect( args[0] );
                                
                if (sparqlResults != null) {
                	System.out.println("YAY");
                }
                for (SelectResult result : sparqlResults.getResults()) {
					for (String variable : sparqlResults.getHead() ) {
						System.err.println("This variable '"+variable+"' with this result: '"+result.getResult().get(variable).getValue()+"' was returned");
					}
					System.err.println("---------------");
				}
                
                System.out.println("Finished - awesome");
            } else {
                System.err.println("The sparql endpoint needs to an http one");
            }
        } else {
            System.err.println("Two parameters please: SparqlProtocolClientExample <sparql query> <sparql endpoint>");

        }


    }
    
}

/* vi:set ts=8 sts=4 sw=4 et: */
