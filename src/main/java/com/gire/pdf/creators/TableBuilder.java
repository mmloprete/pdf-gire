package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.List;

public class TableBuilder {

    private final PDDocument document;
    private final float rowHeight = 20f;
    private final float tableWidth = 400f;
    private final float cellMargin = 5f;
    private final FontStyle fontStyle = FontStyle.normal();

    public TableBuilder(PDDocument document) {
        this.document = document;
    }

    public void drawSimpleTable(PDPage page, float startX, float startY, List<List<String>> data) throws IOException {
        int cols = data.get(0).size();
        float colWidth = tableWidth / cols;
        float y = startY;

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.setFont(fontStyle.getFont(), fontStyle.getFontSize());

            for (List<String> row : data) {
                float x = startX;

                for (String cell : row) {
                    contentStream.addRect(x, y - rowHeight, colWidth, rowHeight);
                    contentStream.stroke();

                    contentStream.beginText();
                    contentStream.newLineAtOffset(x + cellMargin, y - 15);
                    contentStream.showText(cell);
                    contentStream.endText();

                    x += colWidth;
                }
                y -= rowHeight;
            }
        }
    }
}
