package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.List;

public class ListBuilder {

    private final PDDocument document;

    public ListBuilder(PDDocument document) {
        this.document = document;
    }

    public void writeUnorderedList(PDPage page, List<String> items, float x, float startY, FontStyle style, float lineSpacing) throws IOException {
        float y = startY;

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.setFont(style.getFont(), style.getFontSize());

            for (String item : items) {
                contentStream.beginText();
                contentStream.newLineAtOffset(x, y);
                contentStream.showText("• " + item);
                contentStream.endText();
                y -= lineSpacing;
            }
        }
    }

    public void writeOrderedList(PDPage page, List<String> items, float x, float startY, FontStyle style, float lineSpacing) throws IOException {
        float y = startY;

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.setFont(style.getFont(), style.getFontSize());

            int count = 1;
            for (String item : items) {
                contentStream.beginText();
                contentStream.newLineAtOffset(x, y);
                contentStream.showText(count + ". " + item);
                contentStream.endText();
                y -= lineSpacing;
                count++;
            }
        }
    }
}
