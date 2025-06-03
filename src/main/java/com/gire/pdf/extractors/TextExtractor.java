package com.gire.pdf.extractors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextExtractor {

    // Extrae texto de un archivo pdf
    public String extractText(File file) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    // Extrae texto de una pagina dentro de un archivo pdf
    public String extractTextFromPage(File file, int pageNumber) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(pageNumber);
            stripper.setEndPage(pageNumber);
            return stripper.getText(document);
        }
    }

    // Extrae texto de un pdf pero indicando rango de paginas (pagina de inicio y pagina final)
    public String extractTextFromRange(File file, int startPage, int endPage) throws IOException {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(startPage);
            stripper.setEndPage(endPage);
            return stripper.getText(document);
        }
    }

    // Devuelve el texto de cada pagina del pdf y lo almacena y devuelve en una lista
    public List<String> extractTextByPage(File file) throws IOException {
        List<String> pages = new ArrayList<>();
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            int pageCount = document.getNumberOfPages();

            for (int i = 1; i <= pageCount; i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                pages.add(stripper.getText(document));
            }
        }
        return pages;
    }

    // Extrae texto de un pdf pero en formato de texto plano (sin saltos de linea)
    public String extractTextFlat(File file) throws IOException {
        return extractText(file).replaceAll("\\r?\\n", " ").trim();
    }
}
