package com.gire.pdf.model;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class PageSize {


    public static final PDRectangle A0 = PDRectangle.A0;
    public static final PDRectangle A1 = PDRectangle.A1;
    public static final PDRectangle A2 = PDRectangle.A2;
    public static final PDRectangle A3 = PDRectangle.A3;
    public static final PDRectangle A4 = PDRectangle.A4;
    public static final PDRectangle A5 = PDRectangle.A5;
    public static final PDRectangle A6 = new PDRectangle(298f, 420f); // no existe directamente en PDFBox


    public static final PDRectangle B4 = new PDRectangle(729f, 1032f);
    public static final PDRectangle B5 = new PDRectangle(516f, 729f);


    public static final PDRectangle LETTER = PDRectangle.LETTER;     // 612x792 pts
    public static final PDRectangle LEGAL = PDRectangle.LEGAL;       // 612x1008
    public static final PDRectangle TABLOID = PDRectangle.TABLOID;   // 792x1224
    public static final PDRectangle LEDGER = new PDRectangle(1224f, 792f); // Tabloid apaisado
    public static final PDRectangle EXECUTIVE = new PDRectangle(522f, 756f);


    public static final PDRectangle ANSI_A = PDRectangle.LETTER;
    public static final PDRectangle ANSI_B = PDRectangle.TABLOID;
    public static final PDRectangle ANSI_C = new PDRectangle(1224f, 1584f);
    public static final PDRectangle ANSI_D = new PDRectangle(1584f, 2448f);
    public static final PDRectangle ANSI_E = new PDRectangle(2448f, 3168f);


    public static final PDRectangle SQUARE_SMALL = new PDRectangle(500f, 500f);
    public static final PDRectangle SQUARE_LARGE = new PDRectangle(800f, 800f);

}
