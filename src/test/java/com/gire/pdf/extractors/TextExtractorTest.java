package com.gire.pdf.extractors;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextExtractorTest {

    private File generateMultiPagePdf() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page1 = creator.addBlankPage();
        creator.addText(page1, "Texto en página uno.", 100, 700);

        PDPage page2 = creator.addBlankPage();
        creator.addText(page2, "Texto en página dos.", 100, 700);

        PDPage page3 = creator.addBlankPage();
        creator.addText(page3, "Texto en página tres.", 100, 700);

        File file = File.createTempFile("text-extractor-multipage", ".pdf");
        file.deleteOnExit();
        creator.saveTo(file.getAbsolutePath());
        return file;
    }

    @Test
    public void shouldExtractAllText() throws IOException {
        File file = generateMultiPagePdf();
        TextExtractor extractor = new TextExtractor();

        String result = extractor.extractText(file);

        assertNotNull(result);
        assertTrue(result.contains("Texto en página uno."));
        assertTrue(result.contains("Texto en página tres."));
    }

    @Test
    public void shouldExtractTextFromSinglePage() throws IOException {
        File file = generateMultiPagePdf();
        TextExtractor extractor = new TextExtractor();

        String result = extractor.extractTextFromPage(file, 2);

        assertNotNull(result);
        assertTrue(result.contains("página dos"));
        assertFalse(result.contains("página uno"));
    }

    @Test
    public void shouldExtractTextFromPageRange() throws IOException {
        File file = generateMultiPagePdf();
        TextExtractor extractor = new TextExtractor();

        String result = extractor.extractTextFromRange(file, 1, 2);

        assertNotNull(result);
        assertTrue(result.contains("página uno"));
        assertTrue(result.contains("página dos"));
        assertFalse(result.contains("página tres"));
    }

    @Test
    public void shouldExtractTextByPageAsList() throws IOException {
        File file = generateMultiPagePdf();
        TextExtractor extractor = new TextExtractor();

        List<String> pages = extractor.extractTextByPage(file);

        assertEquals(3, pages.size());
        assertTrue(pages.get(0).contains("página uno"));
        assertTrue(pages.get(1).contains("página dos"));
        assertTrue(pages.get(2).contains("página tres"));
    }

    @Test
    public void shouldExtractTextWithoutLineBreaks() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();
        creator.addMultilineText(page, "Primera línea.\nSegunda línea.", 100, 700);

        File file = File.createTempFile("text-flat", ".pdf");
        file.deleteOnExit();
        creator.saveTo(file.getAbsolutePath());

        TextExtractor extractor = new TextExtractor();
        String result = extractor.extractTextFlat(file);

        assertFalse(result.contains("\n"));
        assertTrue(result.contains("Primera línea. Segunda línea."));
    }
}
