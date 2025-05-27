package com.gire.pdf.extractors;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageCountExtractorTest {

    @Test
    public void shouldReturnCorrectPageCount() throws IOException {
        PdfCreator creator = new PdfCreator();
        creator.addBlankPage();
        creator.addBlankPage();
        creator.addBlankPage();

        File file = File.createTempFile("page-count-test", ".pdf");
        file.deleteOnExit();
        creator.saveTo(file.getAbsolutePath());

        PageCountExtractor extractor = new PageCountExtractor();
        int pageCount = extractor.extractPageCount(file);

        assertEquals(3, pageCount);
    }
}
