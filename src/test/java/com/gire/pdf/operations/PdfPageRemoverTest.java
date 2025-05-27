package com.gire.pdf.operations;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfPageRemoverTest {

    @Test
    public void shouldRemoveSpecifiedPages() throws IOException {
        PdfCreator creator = new PdfCreator();
        creator.addBlankPage();
        creator.addBlankPage();
        creator.addBlankPage();

        File inputFile = File.createTempFile("remover-test-input", ".pdf");
        inputFile.deleteOnExit();
        creator.saveTo(inputFile.getAbsolutePath());

        File outputFile = File.createTempFile("remover-test-output", ".pdf");
        outputFile.deleteOnExit();

        PdfPageRemover remover = new PdfPageRemover();
        remover.removePages(inputFile, outputFile, Arrays.asList(1));

        try (PDDocument result = PDDocument.load(outputFile)) {
            assertEquals(2, result.getNumberOfPages());
        }
    }
}
