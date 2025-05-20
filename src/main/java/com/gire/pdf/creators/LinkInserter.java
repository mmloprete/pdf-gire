package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDDocument;
import com.gire.pdf.model.FontStyle;

import java.awt.Color;
import java.io.IOException;
import java.util.Collections;

public class LinkInserter {

    private final PDDocument document;

    public LinkInserter(PDDocument document) {
        this.document = document;
    }

    public void insertExternalLink(PDPage page, String text, float x, float y, FontStyle style, String url, boolean styled) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
                PDPageContentStream.AppendMode.APPEND, true)) {

            if (styled) {
                contentStream.setNonStrokingColor(Color.BLUE);
            }

            contentStream.setFont(style.getFont(), style.getFontSize());
            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(text);
            contentStream.endText();

            if (styled) {
                contentStream.setNonStrokingColor(Color.BLACK);
            }
        }

        float textWidth = style.getFont().getStringWidth(text) / 1000 * style.getFontSize();
        float textHeight = style.getFontSize();

        PDRectangle linkRect = new PDRectangle(x, y, textWidth, textHeight);

        PDAnnotationLink link = new PDAnnotationLink();
        link.setRectangle(linkRect);

        PDBorderStyleDictionary noBorder = new PDBorderStyleDictionary();
        noBorder.setWidth(0);
        noBorder.setStyle(PDBorderStyleDictionary.STYLE_SOLID);

        link.setBorderStyle(noBorder);
        link.setBorder(null);




        PDActionURI action = new PDActionURI();
        action.setURI(url);
        link.setAction(action);

        page.getAnnotations().add(link);

    }
}
