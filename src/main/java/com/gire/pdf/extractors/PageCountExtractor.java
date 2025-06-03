package com.gire.pdf.extractors;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class PageCountExtractor {

    // Extrae y devuelve el numero de paginas de un pdf
    public int extractPageCount(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            return document.getNumberOfPages();
        }
    }
}
