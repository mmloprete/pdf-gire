package com.gire.pdf.extractors;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.text.BeginText;
import org.apache.pdfbox.contentstream.operator.text.EndText;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.state.Concatenate;
import org.apache.pdfbox.contentstream.operator.state.Restore;
import org.apache.pdfbox.contentstream.operator.state.Save;
import org.apache.pdfbox.contentstream.operator.state.SetGraphicsStateParameters;
import org.apache.pdfbox.contentstream.operator.graphics.*;
import org.apache.pdfbox.cos.COSName;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ImageExtractor extends PDFStreamEngine {

    // Array de imagenes
    private final List<BufferedImage> images = new ArrayList<>();

    // Constructor
    public ImageExtractor() throws IOException {
        addOperator(new Concatenate());
        addOperator(new DrawObject());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new BeginText());
        addOperator(new EndText());
    }

    // Metodo para extraer imagenes de un archivo y almacenarlas y devolverlas en el array anterior
    public List<BufferedImage> extractImages(File file) throws IOException {
        images.clear();

        try (PDDocument document = PDDocument.load(file)) {
            for (PDPage page : document.getPages()) {
                processPage(page);
            }
        }

        return images;
    }
// Este metodo se ejecuta para cada operador de contenido en la página.
// Si el operador es "Do", significa que se está dibujando un objeto, que puede ser una imagen o un formulario.
    @Override
    protected void processOperator(Operator operator, List<COSBase> operands) throws IOException {
        String operation = operator.getName();
        if ("Do".equals(operation)) {
            COSName objectName = (COSName) operands.get(0);
            PDResources resources = getResources();
            if (resources != null) {
                if (resources.isImageXObject(objectName)) {
                    PDImageXObject image = (PDImageXObject) resources.getXObject(objectName);
                    if (image != null) {
                        images.add(image.getImage());
                    }
                }
            }
        } else {
            super.processOperator(operator, operands);
        }
    }
}
