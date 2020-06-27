package com.apicatalog.jsonld.suite.loader;

import java.net.URI;

import com.apicatalog.jsonld.api.JsonLdError;
import com.apicatalog.jsonld.api.JsonLdErrorCode;
import com.apicatalog.jsonld.document.Document;
import com.apicatalog.jsonld.loader.HttpLoader;
import com.apicatalog.jsonld.loader.LoadDocumentCallback;
import com.apicatalog.jsonld.loader.LoadDocumentOptions;

public final class UriBaseRewriter implements LoadDocumentCallback {

    private final String sourceBase;
    private final String targetBase;
    
    private final LoadDocumentCallback loader;
    
    public UriBaseRewriter(final String sourceBase, final String targetBase) {
        this(sourceBase, targetBase, new HttpLoader());
    }
    
    public UriBaseRewriter(final String sourceBase, final String targetBase, final LoadDocumentCallback loader) {
        this.sourceBase = sourceBase;
        this.targetBase = targetBase;
        
        this.loader = loader;
    }
    
    @Override
    public Document loadDocument(final URI url, final LoadDocumentOptions options) throws JsonLdError {

        final String sourceUrl = url.toString();
        
        if (!sourceUrl.startsWith(sourceBase)) {
            return loader.loadDocument(url, options);
        }

        final String relativePath = sourceUrl.substring(sourceBase.length());

        final Document remoteDocument = loader.loadDocument(URI.create(targetBase + relativePath), options);
        
        if (remoteDocument == null) {
            throw new JsonLdError(JsonLdErrorCode.LOADING_DOCUMENT_FAILED);
        }

        if (remoteDocument.getDocumentUrl() != null && remoteDocument.getDocumentUrl().toString().startsWith(targetBase)) {
            
            final String remoteRelativePath = remoteDocument.getDocumentUrl().toString().substring(targetBase.length()); 
            remoteDocument.setDocumentUrl(URI.create(sourceBase + remoteRelativePath));

        }
        return remoteDocument;

    }
}