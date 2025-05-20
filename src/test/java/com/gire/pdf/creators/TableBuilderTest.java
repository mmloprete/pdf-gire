package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TableBuilderTest {

    @Test
    public void shouldDrawSimpleTable() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        TableBuilder tableBuilder = new TableBuilder(creator.getDocument());

        List<List<String>> data = Arrays.asList(
                Arrays.asList("Nombre", "Edad", "Ciudad"),
                Arrays.asList("Ana", "28", "Rosario"),
                Arrays.asList("Luis", "35", "Córdoba"),
                Arrays.asList("María", "22", "Mendoza")
        );

        tableBuilder.drawSimpleTable(page, 100, 700, data);

        File outputDir = new File("test-output");
        if (!outputDir.exists()) outputDir.mkdirs();

        File file = new File("test-output/table-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        boolean deleted = file.delete();
        if (!deleted) {
            System.err.println("No se pudo eliminar el archivo de prueba: " + file.getAbsolutePath());
        }
    }
}
