package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.Color;
import java.io.IOException;

public class ShapeDrawer {

    private final PDDocument document;

    // Setter de acceso al documento PDDocument
    public ShapeDrawer(PDDocument document) {
        this.document = document;
    }

    // Dibuja una linea en la página del pdf (pasandole página, coordenadas de inicio del dibujo y fin, ancho de la
    // linea y color
    public void drawLine(PDPage page, float startX, float startY, float endX, float endY, float width, Color color) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
                PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.setStrokingColor(color);
            contentStream.setLineWidth(width);
            contentStream.moveTo(startX, startY);
            contentStream.lineTo(endX, endY);
            contentStream.stroke();
        }
    }

    // Dibuja un rectángulo en la pagina del pdf (pasandole página, coordenadas, ancho, alto, color del trazo
    // y color del relleno
    public void drawRectangle(PDPage page, float x, float y, float width, float height, Color strokeColor, Color fillColor) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
                PDPageContentStream.AppendMode.APPEND, true)) {
            if (fillColor != null) {
                contentStream.setNonStrokingColor(fillColor);
                contentStream.addRect(x, y, width, height);
                contentStream.fill();
            }
            if (strokeColor != null) {
                contentStream.setStrokingColor(strokeColor);
                contentStream.addRect(x, y, width, height);
                contentStream.stroke();
            }
        }
    }
}
