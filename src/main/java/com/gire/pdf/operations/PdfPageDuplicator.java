package com.gire.pdf.operations;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

public class PdfPageDuplicator {
    // Duplica la pagina indicada de un archivo (debo indicar despues de que pagina insertarla) en
    // otro archivo indicado
    public void duplicatePage(File inputFile, File outputFile, int pageIndex, int insertAfter) throws IOException {
        try (PDDocument document = PDDocument.load(inputFile)) {
            if (pageIndex < 0 || pageIndex >= document.getNumberOfPages()) {
                throw new IllegalArgumentException("Índice de página inválido");
            }

            PDPage original = document.getPage(pageIndex);
            PDPage duplicate = new PDPage(original.getCOSObject());

            int targetIndex = Math.min(insertAfter + 1, document.getNumberOfPages());
            document.getPages().insertAfter(duplicate, document.getPage(insertAfter));

            document.save(outputFile);
        }
    }
}
