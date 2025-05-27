package com.gire.pdf.extractors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkExtractor {

    public List<BookmarkData> extractBookmarks(File file) throws IOException {
        List<BookmarkData> bookmarks = new ArrayList<>();

        try (PDDocument document = PDDocument.load(file)) {
            PDDocumentOutline outline = document.getDocumentCatalog().getDocumentOutline();
            if (outline != null) {
                processOutline(outline, bookmarks, 0);
            }
        }

        return bookmarks;
    }

    private void processOutline(PDOutlineNode node, List<BookmarkData> result, int level) throws IOException {
        PDOutlineItem current = node.getFirstChild();
        while (current != null) {
            int pageNumber = -1;

            if (current.getDestination() instanceof PDPageDestination) {
                PDPageDestination dest = (PDPageDestination) current.getDestination();
                pageNumber = dest.retrievePageNumber() + 1;
            }

            result.add(new BookmarkData(current.getTitle(), pageNumber, level));

            if (current.hasChildren()) {
                processOutline(current, result, level + 1);
            }

            current = current.getNextSibling();
        }
    }

    public static class BookmarkData {
        public final String title;
        public final int page;
        public final int level;

        public BookmarkData(String title, int page, int level) {
            this.title = title;
            this.page = page;
            this.level = level;
        }
    }
}
