package com.gire.pdf.mergers;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfMergerWithBookmarksTest {

    private File createPdfWithBookmark(String title) throws IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        PDDocumentOutline outline = new PDDocumentOutline();
        PDOutlineItem item = new PDOutlineItem();
        item.setTitle(title);

        PDPageFitDestination dest = new PDPageFitDestination();
        dest.setPage(page);
        item.setDestination(dest);

        outline.addLast(item);
        doc.getDocumentCatalog().setDocumentOutline(outline);

        File file = File.createTempFile("bookmark-source", ".pdf");
        file.deleteOnExit();
        doc.save(file);
        doc.close();
        return file;
    }

    @Test
    public void shouldMergeAndPreserveBookmarks() throws IOException {
        File file1 = createPdfWithBookmark("Capítulo 1");
        File file2 = createPdfWithBookmark("Capítulo 2");

        File merged = File.createTempFile("merged-with-bookmarks", ".pdf");
        merged.deleteOnExit();

        PdfMergerWithBookmarks merger = new PdfMergerWithBookmarks();
        merger.mergeWithBookmarks(Arrays.asList(file1, file2), merged);

        try (PDDocument doc = PDDocument.load(merged)) {
            PDDocumentOutline outline = doc.getDocumentCatalog().getDocumentOutline();
            assertTrue(outline.getFirstChild().getTitle().contains("Capítulo 1"));
            assertTrue(outline.getFirstChild().getNextSibling().getTitle().contains("Capítulo 2"));
        }
    }
}
