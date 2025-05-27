package com.gire.pdf.mergers;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfMerger {

    public void merge(List<File> inputFiles, File outputFile) throws IOException {
        PDFMergerUtility merger = new PDFMergerUtility();
        for (File file : inputFiles) {
            merger.addSource(file);
        }
        merger.setDestinationFileName(outputFile.getAbsolutePath());
        merger.mergeDocuments(null);
    }
}
