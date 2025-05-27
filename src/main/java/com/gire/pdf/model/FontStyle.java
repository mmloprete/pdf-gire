package com.gire.pdf.model;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class FontStyle {
    private final PDFont font;
    private final float fontSize;

    public FontStyle(PDFont font, float fontSize) {
        this.font = font;
        this.fontSize = fontSize;
    }

    public PDFont getFont() {
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

    public static FontStyle small() {
        return new FontStyle(PDType1Font.HELVETICA, 8);
    }

    public static FontStyle ticket() {
        return new FontStyle(PDType1Font.COURIER, 9f);
    }

    public static FontStyle ticketBold() {
        return new FontStyle(PDType1Font.COURIER_BOLD, 9f);
    }

    public static FontStyle ticketSmall() {
        return new FontStyle(PDType1Font.COURIER, 8f);
    }

}
