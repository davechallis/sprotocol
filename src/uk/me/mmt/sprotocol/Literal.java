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

/**
 * Literal class, immutable with option datatype and language
 */
public final class Literal extends SparqlResource {
    protected String datatype;
    protected String language;

    protected Literal(String literal, String dt, String lang) {
        if (null == literal) {
            throw new IllegalArgumentException("The value of a Literal can not be 'null'");
        }
        value = literal;
        datatype = dt;
        language = lang;
    }

    public String getDatatype() {
        return datatype;
    }

    public String getLanguage() {
        return language;
    }

}

/* vi:set ts=8 sts=4 sw=4 et: */
