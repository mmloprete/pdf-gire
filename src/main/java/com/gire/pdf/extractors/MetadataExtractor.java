package com.gire.pdf.extractors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MetadataExtractor {

    // Metodo para devolver un mapa con la metadata de un pdf
    public Map<String, String> extractMetadata(File file) throws IOException {
        Map<String, String> metadata = new HashMap<>();

        try (PDDocument document = PDDocument.load(file)) {
            PDDocumentInformation info = document.getDocumentInformation();

            metadata.put("Title", info.getTitle());
            metadata.put("Author", info.getAuthor());
            metadata.put("Subject", info.getSubject());
            metadata.put("Keywords", info.getKeywords());
            metadata.put("Creator", info.getCreator());
            metadata.put("Producer", info.getProducer());
            metadata.put("CreationDate", info.getCreationDate() != null ? info.getCreationDate().getTime().toString() : null);
            metadata.put("ModificationDate", info.getModificationDate() != null ? info.getModificationDate().getTime().toString() : null);
        }

        return metadata;
    }
}
