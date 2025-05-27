package com.gire.pdf.extractors;

import com.gire.pdf.creators.ImageInserter;
import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ImageExtractorTest {

    @Test
    public void shouldExtractImagesFromPdf() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        ImageInserter inserter = new ImageInserter(creator.getDocument());
        File imageFile = new File("src/test/resources/sample-image.jpg");

        try (FileInputStream input = new FileInputStream(imageFile)) {
            inserter.insertImageFromStream(page, input, 100, 500, 100, 100);
        }

        File pdfFile = File.createTempFile("image-extractor-test", ".pdf");
        pdfFile.deleteOnExit();
        creator.saveTo(pdfFile.getAbsolutePath());


        ImageExtractor extractor = new ImageExtractor();
        List<BufferedImage> images = extractor.extractImages(pdfFile);


        assertNotNull(images);
        assertFalse(images.isEmpty(), "No se extrajo ninguna imagen");
        assertTrue(images.get(0).getWidth() > 0);
        assertTrue(images.get(0).getHeight() > 0);

    }
}
