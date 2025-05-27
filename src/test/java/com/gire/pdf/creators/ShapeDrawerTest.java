package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShapeDrawerTest {

    @Test
    public void shouldDrawLinesAndRectangles() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        ShapeDrawer drawer = new ShapeDrawer(creator.getDocument());

        drawer.drawLine(page, 100, 700, 400, 700, 2f, Color.BLACK); // línea horizontal
        drawer.drawLine(page, 100, 700, 100, 600, 1f, Color.BLUE);  // línea vertical

        drawer.drawRectangle(page, 150, 650, 200, 40, Color.BLACK, null);          // rectángulo bordeado
        drawer.drawRectangle(page, 150, 580, 200, 40, Color.BLACK, Color.LIGHT_GRAY); // rectángulo relleno

        File file = new File("test-output/shape-test.pdf");
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
