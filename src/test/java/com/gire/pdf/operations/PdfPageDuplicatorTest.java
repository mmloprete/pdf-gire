package com.gire.pdf.operations;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfPageDuplicatorTest {

    @Test
    public void shouldDuplicatePageAndInsertIt() throws IOException {
        PdfCreator creator = new PdfCreator();
        creator.addBlankPage();
        creator.addBlankPage();

        File inputFile = File.createTempFile("duplicate-test-input", ".pdf");
        inputFile.deleteOnExit();
        creator.saveTo(inputFile.getAbsolutePath());

        File outputFile = File.createTempFile("duplicate-test-output", ".pdf");
        outputFile.deleteOnExit();

        PdfPageDuplicator duplicator = new PdfPageDuplicator();
        duplicator.duplicatePage(inputFile, outputFile, 0, 1);

        try (PDDocument result = PDDocument.load(outputFile)) {
            assertEquals(3, result.getNumberOfPages());
        }
    }
}
