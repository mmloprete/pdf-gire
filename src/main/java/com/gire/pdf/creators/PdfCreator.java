package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfCreator {
    // Documento a modificar
    private PDDocument document;

    // Genera una nueva instancia de PDDocument y se la asigna document
    public PdfCreator() {
        this.document = new PDDocument();
    }

    // Genera una nueva pagina en blanco (Por defecto A4)
    public PDPage addBlankPage() {
        return addBlankPageInternal(PDRectangle.A4);
    }

    // Genera una nueva pagina en blanco (Indicando tamaño)
    public PDPage addBlankPage(PDRectangle size) {
        return addBlankPageInternal(size);
    }

    // Metodo auxiliar de la clase
    private PDPage addBlankPageInternal(PDRectangle size) {
        PDPage page = new PDPage(size);
        document.addPage(page);
        return page;
    }


    // Añade texto (fuente helvetica), pasandole una pagina, texto, y coordenadas (x e y).
    // Si ya hay texto en esas coordenadas lo reemplaza
    public void addText(PDPage page, String text, float x, float y) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(text);
            contentStream.endText();
        }
    }

    // Añade texto (fuente helvetica), pasandole una pagina, texto, y coordenadas (x e y).
    public void appendText(PDPage page, String text, float x, float y) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(
                document, page,
                PDPageContentStream.AppendMode.APPEND,
                true,
                true)) {

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(text);
            contentStream.endText();
        }
    }

    // Añade texto (fuente helvetica) pero haciendo saltos de linea si ya llego al final de la linea.
    // Deben pasarseles página (PDPage), texto (String), y coordenadas (x e y)
    public void addMultilineText(PDPage page, String text, float x, float y) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.setLeading(14);
            contentStream.newLineAtOffset(x, y);

            for (String line : text.split("\\r?\\n")) {
                contentStream.showText(line);
                contentStream.newLine();
            }

            contentStream.endText();
        }
    }

    // Mismo formato que addText pero pasándole tipo de fuente y tamaño de fuente
    public void addText(PDPage page, String text, float x, float y, PDFont font, float fontSize) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(text);
            contentStream.endText();
        }
    }

    // Mismo formato que appendText pero pasándole tipo de fuente y tamaño de fuente
    public void appendText(PDPage page, String text, float x, float y, PDFont font, float fontSize) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(text);
            contentStream.endText();
        }
    }

    // Mismo formato que addMultilineText pero pasandole tipo de fuente y tamaño de fuente
    public void addMultilineText(PDPage page, String text, float x, float y, PDFont font, float fontSize) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.setLeading(14);
            contentStream.newLineAtOffset(x, y);

            for (String line : text.split("\\r?\\n")) {
                contentStream.showText(line);
                contentStream.newLine();
            }

            contentStream.endText();
        }
    }

    // Guardar el pdf en un path
    public void saveTo(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        document.save(path.toFile());
        document.close();
    }


    public PDDocument getDocument() {
        return document;
    }

}
