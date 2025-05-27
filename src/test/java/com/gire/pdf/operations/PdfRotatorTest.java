package com.gire.pdf.operations;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfRotatorTest {

    @Test
    public void shouldRotateSpecifiedPage() throws IOException {

        PdfCreator creator = new PdfCreator();
        PDPage page1 = creator.addBlankPage();
        PDPage page2 = creator.addBlankPage();

        File inputFile = File.createTempFile("rotate-test-input", ".pdf");
        inputFile.deleteOnExit();
        creator.saveTo(inputFile.getAbsolutePath());


        File outputFile = File.createTempFile("rotate-test-output", ".pdf");
        outputFile.deleteOnExit();

        PdfRotator rotator = new PdfRotator();
        rotator.rotatePage(inputFile, outputFile, 1, 90);

        try (PDDocument doc = PDDocument.load(outputFile)) {
            assertEquals(0, doc.getPage(0).getRotation());
            assertEquals(90, doc.getPage(1).getRotation());
        }
    }
}
