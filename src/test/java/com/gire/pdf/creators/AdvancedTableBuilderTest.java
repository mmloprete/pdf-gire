package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import com.gire.pdf.model.TextAlignment;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdvancedTableBuilderTest {

    @Test
    public void shouldDrawAutoWidthCenteredTable() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        AdvancedTableBuilder tableBuilder = new AdvancedTableBuilder(creator.getDocument())
                .withAutoColumnWidths()
                .withHeader(true)
                .withCellStyle(FontStyle.normal())
                .withHeaderStyle(FontStyle.subtitle())
                .withBorders(true)
                .withAlignment(TextAlignment.CENTER);

        List<List<String>> data = Arrays.asList(
                Arrays.asList("Producto", "Cantidad", "Descripción"),
                Arrays.asList("Lápiz", "10", "Lápiz negro HB para dibujo técnico"),
                Arrays.asList("Goma", "5", "Goma blanca suave"),
                Arrays.asList("Regla", "1", "Regla metálica de 30cm")
        );

        tableBuilder.drawTable(page, 50, 700, data);

        File file = new File("test-output/advanced-table.pdf");
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
