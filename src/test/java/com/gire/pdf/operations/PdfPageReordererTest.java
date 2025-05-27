package com.gire.pdf.operations;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfPageReordererTest {

    @Test
    public void shouldReorderPages() throws IOException {
        PdfCreator creator = new PdfCreator();
        creator.addText(creator.addBlankPage(), "Página 1", 100, 700);
        creator.addText(creator.addBlankPage(), "Página 2", 100, 700);
        creator.addText(creator.addBlankPage(), "Página 3", 100, 700);

        File inputFile = File.createTempFile("reorder-test-input", ".pdf");
        inputFile.deleteOnExit();
        creator.saveTo(inputFile.getAbsolutePath());

        File outputFile = File.createTempFile("reorder-test-output", ".pdf");
        outputFile.deleteOnExit();

        PdfPageReorderer reorderer = new PdfPageReorderer();
        reorderer.reorderPages(inputFile, outputFile, Arrays.asList(2, 0, 1));

        try (PDDocument doc = PDDocument.load(outputFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(1);
            stripper.setEndPage(1);
            String page1Text = stripper.getText(doc);
            assertTrue(page1Text.contains("Página 3"));
        }
    }
}
