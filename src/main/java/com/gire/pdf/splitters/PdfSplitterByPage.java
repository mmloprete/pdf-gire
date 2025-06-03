package com.gire.pdf.splitters;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfSplitterByPage {

    // Divide un pdf en varios (cada uno por pagina) (debo indicar directorio de salida)
    public List<File> splitByPage(File inputFile, File outputDirectory) throws IOException {
        List<File> outputFiles = new ArrayList<>();

        try (PDDocument document = PDDocument.load(inputFile)) {
            Splitter splitter = new Splitter();
            List<PDDocument> pages = splitter.split(document);

            int index = 1;
            for (PDDocument singlePageDoc : pages) {
                File outFile = new File(outputDirectory, "page-" + index + ".pdf");
                singlePageDoc.save(outFile);
                singlePageDoc.close();
                outputFiles.add(outFile);
                index++;
            }
        }

        return outputFiles;
    }
}
