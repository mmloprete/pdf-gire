package com.gire.pdf.styles;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class FontRegistry {
    // Más definiciones de fuente (tambien tengo mas en model)
    public static final PDType1Font DEFAULT = PDType1Font.HELVETICA;
    public static final PDType1Font TITLE = PDType1Font.HELVETICA_BOLD;
    public static final PDType1Font MONO = PDType1Font.COURIER;
    public static final PDType1Font SERIF = PDType1Font.TIMES_ROMAN;
    public static final PDType1Font TICKET = PDType1Font.COURIER;
}
