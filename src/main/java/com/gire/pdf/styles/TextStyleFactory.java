package com.gire.pdf.styles;

import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;

public class TextStyleFactory {
    // Mas definiciones de fuentes (con colores)
    public static TextStyle title() {
        return new TextStyle(FontRegistry.TITLE, 18, new PDColor(new float[]{0, 0, 0}, PDDeviceRGB.INSTANCE));
    }

    public static TextStyle subtitle() {
        return new TextStyle(FontRegistry.DEFAULT, 14, new PDColor(new float[]{0.1f, 0.1f, 0.1f}, PDDeviceRGB.INSTANCE));
    }

    public static TextStyle paragraph() {
        return new TextStyle(FontRegistry.SERIF, 12, new PDColor(new float[]{0, 0, 0}, PDDeviceRGB.INSTANCE));
    }

    public static TextStyle warning() {
        return new TextStyle(FontRegistry.DEFAULT, 12, new PDColor(new float[]{1f, 0.2f, 0.2f}, PDDeviceRGB.INSTANCE));
    }
}
