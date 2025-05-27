package com.gire.pdf.styles;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;

public class CustomFontLoader {

    public static PDType0Font loadFromFile(PDDocument document, File fontFile) throws IOException {
        return PDType0Font.load(document, fontFile);
    }
}
