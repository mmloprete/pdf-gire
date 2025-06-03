package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;

public class ReceiptLayoutWriter {
    private final PdfCreator creator;
    private final PDPage page;
    private final FontStyle style;
    private final float x;
    private float y;
    private final float spacing;

    public ReceiptLayoutWriter(PdfCreator creator, PDPage page, FontStyle style, float xStart, float yStart, float spacing) {
        this.creator = creator;
        this.page = page;
        this.style = style;
        this.x = xStart;
        this.y = yStart;
        this.spacing = spacing;
    }

    public void line(String text) throws IOException {
        creator.appendText(page, text, x, y, style.getFont(), style.getFontSize());
        y -= spacing;
    }


    public void line(String text, float customX) throws IOException {
        creator.appendText(page, text, customX, y, style.getFont(), style.getFontSize());
        y -= spacing;
    }


    public void skip() {
        y -= spacing;
    }


    public float getCurrentY() {
        return y;
    }


}
