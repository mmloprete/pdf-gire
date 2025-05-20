package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListBuilderTest {

    @Test
    public void shouldWriteOrderedAndUnorderedList() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        ListBuilder listBuilder = new ListBuilder(creator.getDocument());

        listBuilder.writeUnorderedList(page,
                Arrays.asList("Elemento A", "Elemento B", "Elemento C"),
                100, 700, FontStyle.normal(), 20);

        listBuilder.writeOrderedList(page,
                Arrays.asList("Paso 1", "Paso 2", "Paso 3"),
                300, 700, FontStyle.normal(), 20);

        File outputDir = new File("test-output");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File file = new File("test-output/list-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists(), "El archivo PDF debería haberse creado");
        assertTrue(file.length() > 0, "El archivo PDF no debería estar vacío");

        boolean deleted = file.delete();
        if (!deleted) {
            System.err.println("No se pudo eliminar el archivo de prueba: " + file.getAbsolutePath());
        }
    }
}
