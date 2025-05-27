package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColumnLayoutBuilderTest {

    @Test
    public void shouldRenderTextInTwoColumns() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        FontStyle style = new FontStyle(PDType1Font.HELVETICA, 10);

        ColumnLayoutBuilder builder = new ColumnLayoutBuilder(creator.getDocument());

        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                + "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
                + "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi "
                + "ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit "
                + "in voluptate velit esse cillum dolore eu fugiat nulla pariatur.";

        builder.drawTwoColumnText(page, text, 50, style);

        File file = new File("test-output/column-layout-test.pdf");
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
    public void shouldRenderTextInThreeColumns() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        FontStyle style = new FontStyle(PDType1Font.HELVETICA, 9);
        ColumnLayoutBuilder builder = new ColumnLayoutBuilder(creator.getDocument());

        String base = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(base);
        }
        String longText = sb.toString();

        builder.drawMultiColumnText(page, longText, 3, 40, style);

        File file = new File("test-output/column-layout-3cols.pdf");
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
