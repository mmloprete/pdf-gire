package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WatermarkInserterTest {

    @Test
    public void shouldAddTextWatermarkToPage() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        creator.addText(page, "Documento de uso interno", 100, 600);

        FontStyle style = new FontStyle(PDType1Font.HELVETICA_BOLD, 60);

        WatermarkInserter inserter = new WatermarkInserter(creator.getDocument());
        inserter.insertTextWatermark(page, "CONFIDENCIAL", style, 45f, 0.1f, Color.GRAY);

        File file = new File("test-output/watermark-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
        /*
        boolean deleted = file.delete();
        if (!deleted) {
            System.err.println("No se pudo eliminar el archivo de prueba: " + file.getAbsolutePath());
        }
        */
    }

    @Test
    public void shouldAddImageWatermarkToPage() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        creator.addText(page, "Documento de uso interno", 100, 600);

        PDImageXObject image = PDImageXObject.createFromFile("src/test/resources/sample-image.jpg", creator.getDocument());

        WatermarkInserter inserter = new WatermarkInserter(creator.getDocument());
        inserter.insertImageWatermark(page, image, 0.5f, 0.1f);

        File file = new File("test-output/watermark-image-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        /*
        boolean deleted = file.delete();
        if (!deleted) {
            System.err.println("No se pudo eliminar el archivo de prueba: " + file.getAbsolutePath());
        }
        */
    }
}
