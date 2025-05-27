package com.gire.pdf.extractors;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MetadataExtractorTest {

    @Test
    public void shouldExtractDocumentMetadata() throws IOException {
        PDDocument doc = new PDDocument();
        doc.addPage(new PDPage());

        PDDocumentInformation info = new PDDocumentInformation();
        info.setTitle("Mi documento");
        info.setAuthor("Gire");
        info.setSubject("Test");
        info.setKeywords("pdf,metadata");
        info.setCreator("pdf-gire");
        info.setProducer("pdfbox");

        doc.setDocumentInformation(info);

        File file = File.createTempFile("metadata-test", ".pdf");
        file.deleteOnExit();
        doc.save(file);
        doc.close();

        MetadataExtractor extractor = new MetadataExtractor();
        Map<String, String> metadata = extractor.extractMetadata(file);

        assertEquals("Mi documento", metadata.get("Title"));
        assertEquals("Gire", metadata.get("Author"));
        assertEquals("Test", metadata.get("Subject"));
        assertEquals("pdf,metadata", metadata.get("Keywords"));
        assertEquals("pdf-gire", metadata.get("Creator"));
        assertEquals("pdfbox", metadata.get("Producer"));
    }
}
