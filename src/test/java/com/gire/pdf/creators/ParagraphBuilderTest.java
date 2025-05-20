package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import com.gire.pdf.model.TextAlignment;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParagraphBuilderTest {

    @Test
    public void shouldWriteAlignedParagraphs() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        ParagraphBuilder builder = new ParagraphBuilder(creator.getDocument())
                .withLineSpacing(1.5f);


        builder.withAlignment(TextAlignment.LEFT)
                .writeParagraph(page, "Texto alineado a la izquierda\nSegunda línea", 100, 700, FontStyle.normal());


        builder.withAlignment(TextAlignment.CENTER)
                .writeParagraph(page, "Texto centrado\nMás texto centrado", 100, 600, FontStyle.normal());


        builder.withAlignment(TextAlignment.RIGHT)
                .writeParagraph(page, "Texto alineado a la derecha\nMás texto", 100, 500, FontStyle.normal());

        File outputDir = new File("test-output");
        if (!outputDir.exists()) outputDir.mkdirs();

        File file = new File("test-output/paragraph-alignment-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        boolean deleted = file.delete();
        if (!deleted) {
            System.err.println("No se pudo eliminar el archivo de prueba: " + file.getAbsolutePath());
        }
    }
}
