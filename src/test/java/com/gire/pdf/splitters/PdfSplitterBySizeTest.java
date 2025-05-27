package com.gire.pdf.splitters;

import com.gire.pdf.creators.PdfCreator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfSplitterBySizeTest {

    @Test
    public void shouldSplitPdfEveryNGivenPages() throws IOException {
        PdfCreator creator = new PdfCreator();
        for (int i = 0; i < 7; i++) {
            creator.addBlankPage();
        }

        File inputFile = File.createTempFile("split-npages-input", ".pdf");
        inputFile.deleteOnExit();
        creator.saveTo(inputFile.getAbsolutePath());

        File outputDir = new File(System.getProperty("java.io.tmpdir"), "split-npages");
        outputDir.mkdirs();

        PdfSplitterBySize splitter = new PdfSplitterBySize();
        List<File> result = splitter.splitBySize(inputFile, outputDir, 3);

        assertEquals(3, result.size());
    }
}
