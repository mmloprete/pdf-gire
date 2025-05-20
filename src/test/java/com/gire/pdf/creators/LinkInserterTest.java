package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinkInserterTest {

    @Test
    public void shouldInsertStyledAndPlainLinks() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        LinkInserter inserter = new LinkInserter(creator.getDocument());

        inserter.insertExternalLink(page, "Link Azul (estilo clásico)", 100, 700, FontStyle.normal(), "https://rapipago.com.ar/rapipagoWeb/usuarios", true);
        inserter.insertExternalLink(page, "Link Invisible (texto normal)", 100, 650, FontStyle.normal(), "https://rapipago.com.ar/rapipagoWeb/usuarios", false);

        File file = new File("test-output/link-styles-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        boolean deleted = file.delete();
        if (!deleted) {
            System.err.println("No se pudo eliminar el archivo de prueba: " + file.getAbsolutePath());
        }
    }

}
