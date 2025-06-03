package com.gire.pdf.splitters;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfSplitterBySize {

    // funciona similar a los otros 2 metodos de split, solo que divide en paginas iguales (por ejemplo si el pdf
    // tiene 10 paginas y los divido por 3 paginas, tendre 2 pdfs de 3 paginas y 1 de 4 paginas).
    public List<File> splitBySize(File inputFile, File outputDirectory, int pagesPerSplit) throws IOException {
        List<File> outputFiles = new ArrayList<>();

        if (pagesPerSplit <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor que cero");
        }

        try (PDDocument original = PDDocument.load(inputFile)) {
            int totalPages = original.getNumberOfPages();
            int index = 1;

            for (int i = 0; i < totalPages; i += pagesPerSplit) {
                PDDocument subDoc = new PDDocument();
                for (int j = i; j < i + pagesPerSplit && j < totalPages; j++) {
                    subDoc.addPage(original.getPage(j));
                }

                File outFile = new File(outputDirectory, "split-" + index + ".pdf");
                subDoc.save(outFile);
                subDoc.close();
                outputFiles.add(outFile);
                index++;
            }
        }

        return outputFiles;
    }
}
