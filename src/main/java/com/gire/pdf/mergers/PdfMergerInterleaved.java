package com.gire.pdf.mergers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

public class PdfMergerInterleaved {
    public void mergeInterleaved(File fileA, File fileB, File output) throws IOException {
        try (PDDocument docA = PDDocument.load(fileA);
             PDDocument docB = PDDocument.load(fileB);
             PDDocument result = new PDDocument()) {

            int maxPages = Math.max(docA.getNumberOfPages(), docB.getNumberOfPages());

            for (int i = 0; i < maxPages; i++) {
                if (i < docA.getNumberOfPages()) {
                    PDPage pageA = docA.getPage(i);
                    result.addPage(pageA);
                }
                if (i < docB.getNumberOfPages()) {
                    PDPage pageB = docB.getPage(i);
                    result.addPage(pageB);
                }
            }

            result.save(output);
        }
    }
}
