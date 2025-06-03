package com.gire.pdf.creators;

import com.gire.pdf.utils.TextWrapper;
import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.util.List;

public class ColumnLayoutBuilder {

    private final PDDocument document;

    public ColumnLayoutBuilder(PDDocument document) {
        this.document = document;
    }

    // Al pasarle una página PdPage de PdfBox, un texto (string), el margen a tomar en las columnas y una fuente escribe
    // sobre la página dividiendo el texto en 2 columnas
    public void drawTwoColumnText(PDPage page, String text, float margin, FontStyle style) throws IOException {
        PDRectangle mediaBox = page.getMediaBox();
        float pageWidth = mediaBox.getWidth();
        float pageHeight = mediaBox.getHeight();

        float columnGap = 20f;
        float columnWidth = (pageWidth - 2 * margin - columnGap) / 2;
        float startY = pageHeight - margin;
        float leading = style.getFontSize() * 1.5f;

        PDFont font = style.getFont();
        float fontSize = style.getFontSize();

        List<String> wrappedLines = TextWrapper.wrap(text, font, fontSize, columnWidth);

        try (PDPageContentStream stream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            stream.setFont(font, fontSize);
            stream.setLeading(leading);

            float currentX = margin;
            float currentY = startY;
            int column = 0;

            stream.beginText();
            stream.newLineAtOffset(currentX, currentY);

            for (String line : wrappedLines) {
                if (currentY <= margin + leading) {
                    stream.endText();
                    column++;

                    if (column > 1) break;

                    currentX = margin + columnWidth + columnGap;
                    currentY = startY;

                    stream.beginText();
                    stream.setFont(font, fontSize);
                    stream.setLeading(leading);
                    stream.newLineAtOffset(currentX, currentY);
                }

                stream.showText(line);
                stream.newLine();
                currentY -= leading;
            }

            stream.endText();
        }
    }

    // Mismo funcionamiento que el drawTwoColumnText pero para un numero de columnas a indicar
    public void drawMultiColumnText(PDPage page, String text, int numberOfColumns, float margin, FontStyle style) throws IOException {
        PDRectangle mediaBox = page.getMediaBox();
        float pageWidth = mediaBox.getWidth();
        float pageHeight = mediaBox.getHeight();

        float columnGap = 20f;
        float totalGap = columnGap * (numberOfColumns - 1);
        float columnWidth = (pageWidth - 2 * margin - totalGap) / numberOfColumns;
        float startY = pageHeight - margin;
        float leading = style.getFontSize() * 1.5f;

        PDFont font = style.getFont();
        float fontSize = style.getFontSize();

        List<String> wrappedLines = TextWrapper.wrap(text, font, fontSize, columnWidth);

        try (PDPageContentStream stream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            stream.setFont(font, fontSize);
            stream.setLeading(leading);

            float currentY = startY;
            int currentColumn = 0;
            float currentX = margin;

            stream.beginText();
            stream.newLineAtOffset(currentX, currentY);

            for (String line : wrappedLines) {
                if (currentY <= margin + leading) {
                    stream.endText();
                    currentColumn++;

                    if (currentColumn >= numberOfColumns) break;

                    currentX = margin + currentColumn * (columnWidth + columnGap);
                    currentY = startY;

                    stream.beginText();
                    stream.setFont(font, fontSize);
                    stream.setLeading(leading);
                    stream.newLineAtOffset(currentX, currentY);
                }

                stream.showText(line);
                stream.newLine();
                currentY -= leading;
            }

            stream.endText();
        }
    }


}
