package com.gire.pdf.html2pdf;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.*;

public class HtmlToPdfConverter {


    // Convierte html a pdf, el primer parametro es el contenido html como string y el 2do el archivo de salida
    public void convertHtmlToPdf(String htmlContent, File outputFile) throws IOException {
        try (OutputStream os = new FileOutputStream(outputFile)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(os);
            builder.run();
        }
    }

    // Convierte html a pdf, debe pasarsele el archivo html y el archivo de salida
    public void convertHtmlFileToPdf(File htmlFile, File outputFile) throws IOException {
        String html = new String(java.nio.file.Files.readAllBytes(htmlFile.toPath()), "UTF-8");
        convertHtmlToPdf(html, outputFile);
    }
}
