package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageInserter {

    private final PDDocument document;

    public ImageInserter(PDDocument document) {
        this.document = document;
    }

    // Insertar imagen desde un archivo
    public void insertImageFromFile(PDPage page, String imagePath, float x, float y, float width, float height) throws IOException {
        PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);
        drawImage(page, pdImage, x, y, width, height);
    }

    // Insertar imagen desde un input stream
    public void insertImageFromStream(PDPage page, InputStream inputStream, float x, float y, float width, float height) throws IOException {
        byte[] bytes = toByteArray(inputStream);
        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, bytes, "image");
        drawImage(page, pdImage, x, y, width, height);
    }

    // Dibuja la imagen a partir de un objeto PDImageXObject
    private void drawImage(PDPage page, PDImageXObject image, float x, float y, float width, float height) throws IOException {
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            contentStream.drawImage(image, x, y, width, height);
        }
    }

    // Metodo auxiliar para pasar un input stream (imagen) a un array de bytes para manipularlo
    private byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }
}
