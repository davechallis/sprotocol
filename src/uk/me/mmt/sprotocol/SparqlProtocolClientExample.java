/*
    sprotocol - Java SPARQL Protocol Client Library

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 */
/**
 * Copyright 2011 Mischa Tuffield
 *
 */
package uk.me.mmt.sprotocol;

import java.io.IOException;

import uk.me.mmt.sprotocol.SparqlProtocolClient;
import uk.me.mmt.sprotocol.SprotocolException;

public class SparqlProtocolClientExample {

    public static void main(String[] args) {
        if (args.length == 2) {
            if (args[1].startsWith("http")) {
                SparqlProtocolClient sparql = new SparqlProtocolClient( args[1] );
                SelectResultSetSimple sparqlResults;
                try {
                    sparqlResults = sparql.executeSelect( args[0] );

                    for (SelectResult result : sparqlResults.getResults()) {
                        for (String variable : sparqlResults.getHead() ) {
                            SparqlResource resource =  result.getResult().get(variable);
                            System.err.print("This variable '"+variable+"' with this result: '"+resource.getValue()+"' was returned");
                            if (resource instanceof Literal) {
                                Literal lit = (Literal) resource;
                                if (lit.getDatatype() != null) {
                                    System.err.print(" with a datatype of "+lit.getDatatype());
                                }
                                if (lit.getLanguage() != null) {
                                    System.err.print(" with a language of "+lit.getLanguage());
                                }
                            }
                            System.err.println();
                        }
                        System.err.println("---------------");
                    }
                    System.out.println("Finished - awesome");
                } catch (SprotocolException e) {
                    System.err.println(String.format("SPROTOCOL threw one of its own SprotocolExceptions: '{}'",e.getMessage()));
                } catch (IOException e) {
                    System.err.println(String.format("SPROTOCOL threw an IOException: '{}'",e.getMessage()));
                }
            } else {
                System.err.println("The sparql endpoint needs to an http one");
            }
        } else {
            System.err.println("Two parameters please: SparqlProtocolClientExample <sparql query> <sparql endpoint>");
        }
    }
}

/* vi:set ts=8 sts=4 sw=4 et: */
