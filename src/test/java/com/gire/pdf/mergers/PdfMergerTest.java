package com.gire.pdf.mergers;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfMergerTest {

    @Test
    public void shouldMergeMultiplePdfs() throws IOException {
        File file1 = File.createTempFile("merge-1", ".pdf");
        file1.deleteOnExit();
        PdfCreator creator1 = new PdfCreator();
        creator1.addBlankPage();
        creator1.saveTo(file1.getAbsolutePath());

        File file2 = File.createTempFile("merge-2", ".pdf");
        file2.deleteOnExit();
        PdfCreator creator2 = new PdfCreator();
        creator2.addBlankPage();
        creator2.addBlankPage();
        creator2.saveTo(file2.getAbsolutePath());

        File output = File.createTempFile("merged", ".pdf");
        output.deleteOnExit();

        PdfMerger merger = new PdfMerger();
        merger.merge(Arrays.asList(file1, file2), output);

        try (PDDocument result = PDDocument.load(output)) {
            assertEquals(3, result.getNumberOfPages());
        }
    }
}
