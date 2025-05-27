package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import com.gire.pdf.model.TextAlignment;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeaderFooterManagerTest {

    @Test
    public void shouldAddHeadersAndFootersWithCustomFont() throws IOException {
        PdfCreator creator = new PdfCreator();

        for (int i = 0; i < 3; i++) {
            PDPage page = creator.addBlankPage();
            creator.addText(page, "Contenido de la página " + (i + 1), 100, 500);
        }

        FontStyle customFont = new FontStyle(PDType1Font.TIMES_ROMAN, 10);

        HeaderFooterManager manager = new HeaderFooterManager(creator.getDocument())
                .withHeader("Informe GIRE - Uso Interno")
                .withFooter("Página {page} de {total}")
                .withStyle(customFont)
                .withAlignment(TextAlignment.RIGHT);

        manager.apply();

        File file = new File("test-output/header-footer-custom-font-test.pdf");
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
