package com.gire.pdf.mergers;

import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PdfMergerWithBookmarks {

    // Une archivos (pasandole una lista al igual que el metodo merge de PdfMerger
    // y devolviendolos en la ruta indicada). Solo que contempla marcadores (bookmarks)
    public void mergeWithBookmarks(List<File> inputFiles, File outputFile) throws IOException {
        PDDocument mergedDocument = new PDDocument();
        PDDocumentOutline masterOutline = new PDDocumentOutline();
        mergedDocument.getDocumentCatalog().setDocumentOutline(masterOutline);

        int currentPageOffset = 0;

        for (File inputFile : inputFiles) {
            try (PDDocument source = PDDocument.load(inputFile)) {
                PDFMergerUtility merger = new PDFMergerUtility();
                merger.appendDocument(mergedDocument, source);

                PDDocumentOutline outline = source.getDocumentCatalog().getDocumentOutline();
                if (outline != null) {
                    PDOutlineItem item = outline.getFirstChild();
                    while (item != null) {
                        PDOutlineItem cloned = cloneBookmark(item, mergedDocument, currentPageOffset);
                        masterOutline.addLast(cloned);
                        item = item.getNextSibling();
                    }
                }

                currentPageOffset += source.getNumberOfPages();
            }
        }

        masterOutline.openNode();
        mergedDocument.save(outputFile);
        mergedDocument.close();
    }

    private PDOutlineItem cloneBookmark(PDOutlineItem original, PDDocument targetDoc, int pageOffset) throws IOException {
        PDOutlineItem cloned = new PDOutlineItem();
        cloned.setTitle(original.getTitle());

        if (original.getDestination() instanceof PDPageDestination) {
            PDPageDestination dest = (PDPageDestination) original.getDestination();
            int targetIndex = dest.retrievePageNumber() + pageOffset;
            PDPage targetPage = targetDoc.getPage(targetIndex);

            PDPageDestination newDest = new PDPageFitDestination();
            newDest.setPage(targetPage);
            cloned.setDestination(newDest);
        }

        PDOutlineItem child = original.getFirstChild();
        while (child != null) {
            cloned.addLast(cloneBookmark(child, targetDoc, pageOffset));
            child = child.getNextSibling();
        }

        return cloned;
    }
}
