package com.apicatalog.jsonld.api;

import java.net.URI;
import java.util.Optional;

import javax.json.JsonStructure;

import com.apicatalog.jsonld.document.Document;
import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.rdf.RdfDataset;

public class NullRemoteDocument implements Document {

    private final JsonStructure structure;
    
    public NullRemoteDocument() {
        this(null);
    }

    public NullRemoteDocument(JsonStructure structure) {
        this.structure = structure;
    }

    @Override
    public MediaType getContentType() {
        return null;
    }

    @Override
    public URI getContextUrl() {
        return null;
    }

    @Override
    public void setContextUrl(URI contextUrl) {
    }

    @Override
    public URI getDocumentUrl() {
        return null;
    }

    @Override
    public void setDocumentUrl(URI documentUrl) {        
    }

    @Override
    public Optional<String> getProfile() {
        return null;
    }

    @Override
    public Optional<JsonStructure> getJsonContent() throws JsonLdError {
        return Optional.ofNullable(structure);
    }

    @Override
    public Optional<RdfDataset> getRdfContent() throws JsonLdError {
        // TODO Auto-generated method stub
        return null;
    }
}
