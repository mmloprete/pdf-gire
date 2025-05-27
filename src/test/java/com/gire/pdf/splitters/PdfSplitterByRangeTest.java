package com.gire.pdf.splitters;

import com.gire.pdf.creators.PdfCreator;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfSplitterByRangeTest {

    @Test
    public void shouldSplitPdfByGivenRanges() throws IOException {
        PdfCreator creator = new PdfCreator();
        for (int i = 0; i < 5; i++) {
            creator.addBlankPage();
        }

        File inputFile = File.createTempFile("range-test-input", ".pdf");
        inputFile.deleteOnExit();
        creator.saveTo(inputFile.getAbsolutePath());

        File outputDir = new File(System.getProperty("java.io.tmpdir"), "split-ranges");
        outputDir.mkdirs();

        List<int[]> ranges = Arrays.asList(new int[]{1, 2}, new int[]{3, 5});

        PdfSplitterByRange splitter = new PdfSplitterByRange();
        List<File> outputs = splitter.splitByRanges(inputFile, outputDir, ranges);

        assertEquals(2, outputs.size());
    }
}
