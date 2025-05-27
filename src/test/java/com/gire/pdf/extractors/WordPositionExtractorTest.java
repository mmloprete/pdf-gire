package com.gire.pdf.extractors;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class WordPositionExtractorTest {

    @Test
    public void shouldExtractFullWordsWithPosition() throws IOException {

        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();
        creator.addText(page, "Hola mundo", 100, 700);

        File file = File.createTempFile("word-position-test", ".pdf");
        file.deleteOnExit();
        creator.saveTo(file.getAbsolutePath());


        WordPositionExtractor extractor = new WordPositionExtractor();
        List<WordPositionExtractor.WordData> words = extractor.extractWords(file);


        List<String> texts = words.stream()
                .map(w -> w.text)
                .collect(Collectors.toList());


        assertFalse(words.isEmpty());
        assertTrue(texts.contains("Hola"));
        assertTrue(texts.contains("mundo"));

        for (WordPositionExtractor.WordData word : words) {
            assertEquals(1, word.page);
            assertTrue(word.x > 0);
            assertTrue(word.y > 0);
            assertNotNull(word.text);
        }
    }
}
