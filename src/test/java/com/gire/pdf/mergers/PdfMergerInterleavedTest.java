package com.gire.pdf.mergers;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfMergerInterleavedTest {

    @Test
    public void shouldInterleavePagesFromTwoPdfs() throws IOException {
        PdfCreator creatorA = new PdfCreator();
        creatorA.addBlankPage();
        creatorA.addBlankPage();
        File fileA = File.createTempFile("interleaved-A", ".pdf");
        fileA.deleteOnExit();
        creatorA.saveTo(fileA.getAbsolutePath());


        PdfCreator creatorB = new PdfCreator();
        creatorB.addBlankPage();
        creatorB.addBlankPage();
        creatorB.addBlankPage();
        File fileB = File.createTempFile("interleaved-B", ".pdf");
        fileB.deleteOnExit();
        creatorB.saveTo(fileB.getAbsolutePath());


        File output = File.createTempFile("interleaved-output", ".pdf");
        output.deleteOnExit();


        PdfMergerInterleaved interleaver = new PdfMergerInterleaved();
        interleaver.mergeInterleaved(fileA, fileB, output);


        try (PDDocument result = PDDocument.load(output)) {
            assertEquals(5, result.getNumberOfPages());
        }
    }
}
