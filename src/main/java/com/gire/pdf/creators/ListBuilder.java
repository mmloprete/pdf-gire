package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.List;

public class ListBuilder {

    private final PDDocument document;

    // Setter para usar de forma externa (debo pasarle PDDocument)
    public ListBuilder(PDDocument document) {
        this.document = document;
    }

    // Escribe una lista desordenada al pasarle una página (PDPage), una lista de strings (items), coordenadas x e y
    // estilo de fuente y espaciado entre lineas (en y)
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

    // Mismo funcionamiento que writeUnorderedList pero para listas ordenadas
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
