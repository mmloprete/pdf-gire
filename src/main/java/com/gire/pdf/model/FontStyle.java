package com.gire.pdf.model;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class FontStyle {
    private final PDType1Font font;
    private final float fontSize;

    public FontStyle(PDType1Font font, float fontSize) {
        this.font = font;
        this.fontSize = fontSize;
    }

    public PDType1Font getFont() {
        return font;
    }

    public float getFontSize() {
        return fontSize;
    }


    public static FontStyle normal() {
        return new FontStyle(PDType1Font.HELVETICA, 12);
    }

    public static FontStyle title() {
        return new FontStyle(PDType1Font.HELVETICA_BOLD, 18);
    }

    public static FontStyle subtitle() {
        return new FontStyle(PDType1Font.HELVETICA_BOLD, 14);
    }
}
