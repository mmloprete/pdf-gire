package com.gire.pdf.html2pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HtmlToPdfConverterTest {

    @Test
    public void shouldConvertHtmlStringToPdf() throws IOException {
        String html = "<html><body><h1>Hola PDF</h1><p>Esto es un párrafo generado desde HTML.</p></body></html>";

        File outputFile = File.createTempFile("html-string-test", ".pdf");
        outputFile.deleteOnExit();

        HtmlToPdfConverter converter = new HtmlToPdfConverter();
        converter.convertHtmlToPdf(html, outputFile);

        try (PDDocument doc = PDDocument.load(outputFile)) {
            assertTrue(doc.getNumberOfPages() > 0);
        }
    }

    @Test
    public void shouldConvertHtmlFileToPdf() throws IOException {
        File htmlFile = File.createTempFile("sample-html", ".html");
        htmlFile.deleteOnExit();

        String html = "<html><body><h2>Desde archivo</h2><p>Probando archivo local HTML a PDF.</p></body></html>";
        java.nio.file.Files.write(htmlFile.toPath(), html.getBytes("UTF-8"));

        File outputFile = File.createTempFile("html-file-test", ".pdf");
        outputFile.deleteOnExit();

        HtmlToPdfConverter converter = new HtmlToPdfConverter();
        converter.convertHtmlFileToPdf(htmlFile, outputFile);

        try (PDDocument doc = PDDocument.load(outputFile)) {
            assertTrue(doc.getNumberOfPages() > 0);
        }
    }
}
