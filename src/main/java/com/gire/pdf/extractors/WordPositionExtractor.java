package com.gire.pdf.extractors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.*;
import java.util.*;

public class WordPositionExtractor extends PDFTextStripper {

    // Lista de palabras y pagina que se esta procesando en el momento
    private final List<WordData> words = new ArrayList<>();
    private int currentPage;

    // Asegura que los TextPosition se ordenen como aparecen visualmente en la página.
    public WordPositionExtractor() throws IOException {
        setSortByPosition(true);
    }

    // Extrae palabras de un archivo y las devuelve en una lista de WordData (ver mas abajo) recorriendo
    // Todas las paginas del documento
    public List<WordData> extractWords(File file) throws IOException {
        words.clear();

        try (PDDocument document = PDDocument.load(file)) {
            for (int page = 1; page <= document.getNumberOfPages(); page++) {
                setStartPage(page);
                setEndPage(page);
                currentPage = page;


                writeText(document, new OutputStreamWriter(new ByteArrayOutputStream()));
            }
        }

        return words;
    }

    // PDFBox llama a este metodo cada vez que detecta un bloque de texto
    // toma la posición del primer carácter del bloque
    // divide el bloque en palabras individuales
    // agrega ccada palabra individual como WordData
    @Override
    protected void writeString(String string, List<TextPosition> textPositions) {
        if (!textPositions.isEmpty()) {
            TextPosition first = textPositions.get(0);

            float wordX = first.getX();
            float wordY = first.getY();
            float wordHeight = first.getHeightDir();

            String[] splitWords = string.trim().split("\\s+");
            for (String word : splitWords) {
                words.add(new WordData(
                        word,
                        currentPage,
                        wordX,
                        wordY,
                        0,
                        wordHeight
                ));
            }
        }
    }

    // Formato de la palabra a manipular
    public static class WordData {
        public final String text;
        public final int page;
        public final float x;
        public final float y;
        public final float width;
        public final float height;

        public WordData(String text, int page, float x, float y, float width, float height) {
            this.text = text;
            this.page = page;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
