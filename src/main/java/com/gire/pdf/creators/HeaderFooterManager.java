package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import com.gire.pdf.model.TextAlignment;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public class HeaderFooterManager {

    private final PDDocument document;
    private String headerText = "";
    private String footerText = "";
    private FontStyle style = FontStyle.small();
    private TextAlignment alignment = TextAlignment.CENTER;

    public HeaderFooterManager(PDDocument document) {
        this.document = document;
    }

    public HeaderFooterManager withHeader(String text) {
        this.headerText = text;
        return this;
    }

    public HeaderFooterManager withFooter(String text) {
        this.footerText = text;
        return this;
    }

    public HeaderFooterManager withStyle(FontStyle style) {
        this.style = style;
        return this;
    }

    public HeaderFooterManager withAlignment(TextAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public void apply() throws IOException {
        int pageCount = document.getNumberOfPages();

        for (int i = 0; i < pageCount; i++) {
            PDPage page = document.getPage(i);
            float pageWidth = page.getMediaBox().getWidth();
            float headerY = page.getMediaBox().getHeight() - 30;
            float footerY = 20;

            try (PDPageContentStream stream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
                stream.setFont(style.getFont(), style.getFontSize());

                if (!headerText.isEmpty()) {
                    float textWidth = style.getFont().getStringWidth(headerText) / 1000 * style.getFontSize();
                    float x = computeX(pageWidth, textWidth);
                    stream.beginText();
                    stream.newLineAtOffset(x, headerY);
                    stream.showText(headerText);
                    stream.endText();
                }

                if (!footerText.isEmpty()) {
                    String text = footerText.replace("{page}", String.valueOf(i + 1))
                            .replace("{total}", String.valueOf(pageCount));
                    float textWidth = style.getFont().getStringWidth(text) / 1000 * style.getFontSize();
                    float x = computeX(pageWidth, textWidth);
                    stream.beginText();
                    stream.newLineAtOffset(x, footerY);
                    stream.showText(text);
                    stream.endText();
                }
            }
        }
    }

    private float computeX(float pageWidth, float textWidth) {
        switch (alignment) {
            case LEFT:
                return 40;
            case RIGHT:
                return pageWidth - textWidth - 40;
            default:
                return (pageWidth - textWidth) / 2;
        }
    }
}
