package com.gire.pdf.operations;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfPageReorderer {

    // Reordena las paginas de un archivo (en otro indicado) indicando el nuevo orden (lista de indices)
    public void reorderPages(File inputFile, File outputFile, List<Integer> newOrder) throws IOException {
        try (PDDocument document = PDDocument.load(inputFile)) {
            if (newOrder.size() != document.getNumberOfPages()) {
                throw new IllegalArgumentException("La cantidad de índices no coincide con la cantidad de páginas");
            }

            List<PDPage> reordered = new ArrayList<>();
            for (int index : newOrder) {
                if (index < 0 || index >= document.getNumberOfPages()) {
                    throw new IllegalArgumentException("Índice inválido en el orden");
                }
                reordered.add(document.getPage(index));
            }


            int total = document.getNumberOfPages();
            for (int i = total - 1; i >= 0; i--) {
                document.removePage(i);
            }


            for (PDPage page : reordered) {
                document.addPage(page);
            }

            document.save(outputFile);
        }
    }
}
