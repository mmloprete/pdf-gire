package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import com.gire.pdf.model.TextAlignment;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

public class ParagraphBuilder {

    private final PDDocument document;
    private TextAlignment alignment = TextAlignment.LEFT;
    private float lineSpacing = 1.2f;

    public ParagraphBuilder(PDDocument document) {
        this.document = document;
    }

    public ParagraphBuilder withAlignment(TextAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public ParagraphBuilder withLineSpacing(float lineSpacing) {
        this.lineSpacing = lineSpacing;
        return this;
    }

    public void writeParagraph(PDPage page, String text, float x, float y, FontStyle style) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
                PDPageContentStream.AppendMode.APPEND, true)) {

            contentStream.setFont(style.getFont(), style.getFontSize());

            String[] lines = text.split("\n");
            float currentY = y;

            for (String line : lines) {
                float textWidth = style.getFont().getStringWidth(line) / 1000 * style.getFontSize();
                float textX = x;

                if (alignment == TextAlignment.CENTER) {
                    float pageWidth = page.getMediaBox().getWidth();
                    textX = (pageWidth - textWidth) / 2;
                } else if (alignment == TextAlignment.RIGHT) {
                    float pageWidth = page.getMediaBox().getWidth();
                    textX = pageWidth - textWidth - x;
                }

                contentStream.beginText();
                contentStream.newLineAtOffset(textX, currentY);
                contentStream.showText(line);
                contentStream.endText();

                currentY -= style.getFontSize() * lineSpacing;
            }
        }
    }

    public void writeCenteredTitle(PDPage page, String text, float y, FontStyle style) throws IOException {
        this.withAlignment(TextAlignment.CENTER)
                .writeParagraph(page, text, 0, y, style);
    }
}
