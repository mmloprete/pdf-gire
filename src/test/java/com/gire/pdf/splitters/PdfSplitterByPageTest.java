package com.gire.pdf.splitters;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfSplitterByPageTest {

    @Test
    public void shouldSplitPdfIntoSeparatePages() throws IOException {
        PdfCreator creator = new PdfCreator();
        creator.addBlankPage();
        creator.addBlankPage();
        creator.addBlankPage();

        File inputFile = File.createTempFile("splitter-test-input", ".pdf");
        inputFile.deleteOnExit();
        creator.saveTo(inputFile.getAbsolutePath());

        File outputDir = new File(System.getProperty("java.io.tmpdir"), "split-pages");
        outputDir.mkdirs();

        PdfSplitterByPage splitter = new PdfSplitterByPage();
        List<File> resultFiles = splitter.splitByPage(inputFile, outputDir);

        assertEquals(3, resultFiles.size());
        for (File f : resultFiles) {
            assertEquals(".pdf", getFileExtension(f));
        }
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        return name.substring(name.lastIndexOf('.'));
    }
}
