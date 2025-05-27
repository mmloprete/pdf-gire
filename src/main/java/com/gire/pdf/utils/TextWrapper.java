package com.gire.pdf.utils;

import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextWrapper {

    public static List<String> wrap(String text, PDFont font, float fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            float textWidth = font.getStringWidth(testLine) / 1000 * fontSize;

            if (textWidth > maxWidth) {
                if (currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    lines.add(word);
                    currentLine = new StringBuilder();
                }
            } else {
                currentLine = new StringBuilder(testLine);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }
}
