package com.gire.pdf.styles;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;

// Más manipulación de fuentes (puedo usar estas o las de model)
public class TextStyle {

    private final PDFont font;
    private final float fontSize;
    private final PDColor color;

    public TextStyle(PDFont font, float fontSize, PDColor color) {
        this.font = font;
        this.fontSize = fontSize;
        this.color = color;
    }

    public PDFont getFont() {
        return font;
    }

    public float getFontSize() {
        return fontSize;
    }

    public PDColor getColor() {
        return color;
    }
}
