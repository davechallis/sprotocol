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
 * Copyright 2011 Mischa Tuffield for Garlik
 *
 */
package com.garlik.sprotocol;

import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * A simple sparql protocol client, sparql in, sparql-results out
 */

public class SparqlProtocolClient {
    
    private static final String UTF_8 = "UTF-8";
    private static final String SPARQL_RESULTS_XML_MIME = "application/sparql-results+xml";
    private static final String SPARQL_RESULTS_JSN_MIME = "application/sparql-results+json";
    private static final String SPARQL_RESULTS_TSV_MIME = "text/tab-separated-values";
    private static final String SPARQL_RESULTS_CSV_MIME = "text/csv";
    private static final String RDF_XML_MIME = "application/rdf+xml";
    private static final String RDF_TTL_MIME = "text/turtle";

    //Known RDF mime-types
    public final static List<String> MIME_TYPES;
    static {
        final ArrayList<String> m = new ArrayList<String>();
        m.add(SPARQL_RESULTS_XML_MIME);
        m.add(SPARQL_RESULTS_JSN_MIME);
        m.add(SPARQL_RESULTS_TSV_MIME);
        m.add(SPARQL_RESULTS_CSV_MIME);
        m.add(RDF_XML_MIME);
        m.add(RDF_TTL_MIME);
        MIME_TYPES = Collections.unmodifiableList(m);
    };

    private static final int TIMEOUT = 60;
    private static final String USER_AGENT  = "sprotocol/1.1";
    private static final String ACCEPT_HEADER = SPARQL_RESULTS_TSV_MIME+", "+SPARQL_RESULTS_XML_MIME+", "+RDF_TTL_MIME+", "+RDF_XML_MIME;

    public SparqlProtocolClient() {
        //Default Values
    }

    /**
     * Send a SPARQL Query via POST
     */
    public String sparqlQueryPost (String query, String sparqlEndpoint) {

        String output = "";
        try {
            // Construct POST data packet
            String data = URLEncoder.encode("query", UTF_8) + "=" + URLEncoder.encode(query, UTF_8);

            // Send data
            URL url = new URL(sparqlEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setReadTimeout(TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", USER_AGENT);
            conn.setRequestProperty("Accept", ACCEPT_HEADER); 

            System.err.println("This is the query is '"+query+"' being sent to endpoint '"+sparqlEndpoint+"'");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.close();

            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                //Set default content-type to be sparql-xml
                String contenttype = SPARQL_RESULTS_TSV_MIME;
                for (Entry<String, List<String>> header : conn.getHeaderFields().entrySet()) {
                    System.err.println(header.getKey() + "=" + header.getValue());
                    if (header.getKey() != null && header.getKey().equals("Content-Type")) {
                        contenttype = header.getValue().get(0);
                    } 
                }

                boolean is_rdfie = false;
                for (String mime: MIME_TYPES) {
                    if (contenttype.equals(mime)) is_rdfie = true;
                }

                if (is_rdfie) {
                    // Get the response
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;

                    while ((line = rd.readLine()) != null) {
                        // Process line...
                        output = output+line+"\n";
                    }
                    rd.close();
                } else {
                    System.err.println("Mime type not an RDFie related one :"+contenttype);
                }
            } else {
                System.err.println("The result of the POST was a '"+code+"' HTTP response");
            }

        } catch (Exception e) {
            System.err.println("There was an error making a SPARQL query via POST: "+e.getMessage());
        }

        return output;
    }
}

/* vi:set ts=8 sts=4 sw=4 et: */
