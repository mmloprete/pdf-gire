package com.gire.pdf.styles;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextStyleIntegrationTest {

    @Test
    public void shouldRenderStyledTextToPdf() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        TextStyle titleStyle = TextStyleFactory.title();
        TextStyle subtitleStyle = TextStyleFactory.subtitle();
        TextStyle paragraphStyle = TextStyleFactory.paragraph();


        creator.appendText(page, "Título Principal", 100, 700, titleStyle.getFont(), titleStyle.getFontSize());
        creator.appendText(page, "Subtítulo", 100, 675, subtitleStyle.getFont(), subtitleStyle.getFontSize());
        creator.appendText(page, "Este es un párrafo de prueba.", 100, 650, paragraphStyle.getFont(), paragraphStyle.getFontSize());

        File output = new File("test-output/styles-integration-test.pdf");
        creator.saveTo(output.getAbsolutePath());

        assertTrue(output.exists());
        assertTrue(output.length() > 0);
    }
}
