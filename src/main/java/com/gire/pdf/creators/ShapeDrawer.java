package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.awt.Color;
import java.io.IOException;

public class ShapeDrawer {

    private final PDDocument document;

    public ShapeDrawer(PDDocument document) {
        this.document = document;
    }

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
