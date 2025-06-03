package com.gire.pdf.operations;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

public class PdfRotator {

    // Rota una pagina de un archivo (con salida en otro) indicando indice de la pagina a rotar y grados
    public void rotatePage(File inputFile, File outputFile, int pageIndex, int degrees) throws IOException {
        try (PDDocument document = PDDocument.load(inputFile)) {
            if (pageIndex < 0 || pageIndex >= document.getNumberOfPages()) {
                throw new IllegalArgumentException("Índice de página inválido");
            }

            PDPage page = document.getPage(pageIndex);
            int currentRotation = page.getRotation();
            page.setRotation((currentRotation + degrees) % 360);

            document.save(outputFile);
        }
    }
}
