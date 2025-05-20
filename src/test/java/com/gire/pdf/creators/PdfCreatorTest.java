package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfCreatorTest {

    @Test
    public void shouldCreatePdfWithText() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();
        creator.addText(page, "Texto de prueba", 100, 700);

        File outputDir = new File("test-output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File file = new File("test-output/creator-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists(), "El archivo PDF debería haberse creado");
        assertTrue(file.length() > 0, "El archivo PDF no debería estar vacío");

        boolean deleted = file.delete();
        if (!deleted) {
            System.err.println("No se pudo eliminar el archivo de prueba: " + file.getAbsolutePath());
        }
    }
}
