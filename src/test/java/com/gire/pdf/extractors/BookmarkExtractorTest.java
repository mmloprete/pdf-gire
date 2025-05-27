package com.gire.pdf.extractors;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookmarkExtractorTest {

    @Test
    public void shouldExtractBookmarks() throws IOException {
        PDDocument document = new PDDocument();

        PDPage page1 = new PDPage();
        PDPage page2 = new PDPage();
        document.addPage(page1);
        document.addPage(page2);

        PDDocumentOutline outline = new PDDocumentOutline();

        PDOutlineItem item1 = new PDOutlineItem();
        item1.setTitle("Sección 1");
        item1.setDestination(page1);

        PDOutlineItem item2 = new PDOutlineItem();
        item2.setTitle("Sección 2");
        item2.setDestination(page2);

        outline.addLast(item1);
        outline.addLast(item2);

        document.getDocumentCatalog().setDocumentOutline(outline);

        File file = File.createTempFile("bookmark-test", ".pdf");
        file.deleteOnExit();
        document.save(file);
        document.close();

        BookmarkExtractor extractor = new BookmarkExtractor();
        List<BookmarkExtractor.BookmarkData> bookmarks = extractor.extractBookmarks(file);

        assertEquals(2, bookmarks.size());
        assertEquals("Sección 1", bookmarks.get(0).title);
        assertEquals(1, bookmarks.get(0).page);
        assertEquals(0, bookmarks.get(0).level);

        assertEquals("Sección 2", bookmarks.get(1).title);
        assertEquals(2, bookmarks.get(1).page);
        assertEquals(0, bookmarks.get(1).level);
    }
}
