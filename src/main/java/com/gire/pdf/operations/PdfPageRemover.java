package com.gire.pdf.operations;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfPageRemover {

    // Elimina las paginas indicadas de una lista de indices, pasandole un archivo de entrada
    // En un archivo de salida
    public void removePages(File inputFile, File outputFile, List<Integer> pageIndexesToRemove) throws IOException {
        try (PDDocument document = PDDocument.load(inputFile)) {

            pageIndexesToRemove.stream()
                    .sorted((a, b) -> b - a)
                    .forEach(index -> {
                        if (index >= 0 && index < document.getNumberOfPages()) {
                            document.removePage(index);
                        }
                    });

            document.save(outputFile);
        }
    }
}
