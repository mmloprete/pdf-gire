package com.gire.pdf.splitters;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfSplitterByRange {

    // Funcionamiento similar que splitByPage pero indicando rangos a dividir
    public List<File> splitByRanges(File inputFile, File outputDirectory, List<int[]> ranges) throws IOException {
        List<File> outputFiles = new ArrayList<>();

        try (PDDocument original = PDDocument.load(inputFile)) {
            int totalPages = original.getNumberOfPages();

            int index = 1;
            for (int[] range : ranges) {
                int start = range[0] - 1;
                int end = range[1] - 1;

                if (start < 0 || end >= totalPages || start > end) {
                    throw new IllegalArgumentException("Rango inválido: [" + range[0] + ", " + range[1] + "]");
                }

                PDDocument subDoc = new PDDocument();
                for (int i = start; i <= end; i++) {
                    subDoc.addPage(original.getPage(i));
                }

                File outFile = new File(outputDirectory, "range-" + index + ".pdf");
                subDoc.save(outFile);
                subDoc.close();
                outputFiles.add(outFile);
                index++;
            }
        }

        return outputFiles;
    }
}
