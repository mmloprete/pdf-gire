package com.gire.pdf.extractors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.*;
import java.util.*;

public class WordPositionExtractor extends PDFTextStripper {

    private final List<WordData> words = new ArrayList<>();
    private int currentPage;

    public WordPositionExtractor() throws IOException {
        setSortByPosition(true);
    }

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
