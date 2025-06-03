package com.gire.pdf.styles;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;

public class CustomFontLoader {
    // Permite cargar una fuente personalizada en el documento
    public static PDType0Font loadFromFile(PDDocument document, File fontFile) throws IOException {
        return PDType0Font.load(document, fontFile);
    }
}
