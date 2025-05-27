package com.gire.pdf.styles;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomFontLoaderTest {

    @Test
    public void shouldLoadCustomFontAndRenderText() throws IOException {
        File fontFile = new File("src/test/resources/fonts/Roboto-Regular.ttf");
        if (!fontFile.exists()) return;

        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        PDType0Font customFont = CustomFontLoader.loadFromFile(creator.getDocument(), fontFile);
        creator.appendText(page, "Texto con fuente Roboto", 100, 700, customFont, 14);

        File output = new File("test-output/custom-font-test.pdf");
        creator.saveTo(output.getAbsolutePath());

        assertNotNull(customFont);
    }
}
