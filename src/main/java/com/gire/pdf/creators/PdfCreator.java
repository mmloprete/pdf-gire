package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PdfCreator {

    private PDDocument document;

    public PdfCreator() {
        this.document = new PDDocument();
    }

    public PDPage addBlankPage() {
        return addBlankPageInternal(PDRectangle.A4);
    }

    public PDPage addBlankPage(PDRectangle size) {
        return addBlankPageInternal(size);
    }

    private PDPage addBlankPageInternal(PDRectangle size) {
        PDPage page = new PDPage(size);
        document.addPage(page);
        return page;
    }


    public void addText(PDPage page, String text, float x, float y) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(text);
            contentStream.endText();
        }
    }

    public void appendText(PDPage page, String text, float x, float y) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(
                document, page,
                PDPageContentStream.AppendMode.APPEND,
                true,
                true)) {

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(text);
            contentStream.endText();
        }
    }

    public void addMultilineText(PDPage page, String text, float x, float y) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.setLeading(14);
            contentStream.newLineAtOffset(x, y);

            for (String line : text.split("\\r?\\n")) {
                contentStream.showText(line);
                contentStream.newLine();
            }

            contentStream.endText();
        }
    }

    public void appendText(PDPage page, String text, float x, float y, PDFont font, float fontSize) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.beginText();
            contentStream.setFont(font, fontSize);
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(text);
            contentStream.endText();
        }
    }


    public void saveTo(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        document.save(path.toFile());
        document.close();
    }

    public PDDocument getDocument() {
        return document;
    }

}
