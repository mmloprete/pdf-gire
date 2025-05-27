package com.gire.pdf.extractors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.filespecification.*;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AttachmentExtractorTest {

    @Test
    public void shouldExtractEmbeddedFiles() throws IOException {

        PDDocument document = new PDDocument();
        document.addPage(new PDPage());


        byte[] data = "Contenido de prueba".getBytes("UTF-8");

        PDEmbeddedFile embeddedFile = new PDEmbeddedFile(document, new ByteArrayInputStream(data));
        embeddedFile.setSubtype("text/plain");
        embeddedFile.setSize(data.length);

        PDComplexFileSpecification fileSpec = new PDComplexFileSpecification();
        fileSpec.setFile("test.txt");
        fileSpec.setEmbeddedFile(embeddedFile);


        Map<String, PDComplexFileSpecification> efMap = new HashMap<>();
        efMap.put("test.txt", fileSpec);

        PDEmbeddedFilesNameTreeNode efTree = new PDEmbeddedFilesNameTreeNode();
        efTree.setNames(efMap);

        PDDocumentNameDictionary names = new PDDocumentNameDictionary(document.getDocumentCatalog());
        names.setEmbeddedFiles(efTree);
        document.getDocumentCatalog().setNames(names);


        File file = File.createTempFile("attachment-test", ".pdf");
        file.deleteOnExit();
        document.save(file);
        document.close();


        AttachmentExtractor extractor = new AttachmentExtractor();
        List<AttachmentExtractor.AttachmentData> attachments = extractor.extractAttachments(file);

        assertEquals(1, attachments.size());

        AttachmentExtractor.AttachmentData a = attachments.get(0);
        assertEquals("test.txt", a.name);
        assertEquals(data.length, a.size);
        assertEquals("text/plain", a.type);
    }
}
