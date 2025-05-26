package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LineSeparatorTest {

    @Test
    public void shouldDrawLineSeparator() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        ShapeDrawer shapeDrawer = new ShapeDrawer(creator.getDocument());
        LineSeparator separator = new LineSeparator(shapeDrawer);

        separator.draw(page, 100, 500, 700, 1f, Color.GRAY);
        separator.draw(page, 100, 500, 650, 2f, Color.DARK_GRAY);
        separator.draw(page, 100, 500, 600, 0.5f, Color.LIGHT_GRAY);

        File file = new File("test-output/line-separator-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        boolean deleted = file.delete();
        if (!deleted) {
            System.err.println("No se pudo eliminar el archivo de prueba: " + file.getAbsolutePath());
        }
    }
}
