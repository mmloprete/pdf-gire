package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.PDPage;

import java.awt.Color;
import java.io.IOException;

public class LineSeparator {

    private final ShapeDrawer shapeDrawer;

    // Setter usar de forma externa (debo pasarle una instancia de ShapeDrawer)
    public LineSeparator(ShapeDrawer shapeDrawer) {
        this.shapeDrawer = shapeDrawer;
    }

    // Dibuja una linea horizontal separadora indicando pagina, inicio y fin de las coordenadas horizontales,
    // Coordenada vertical para la línea horizontal, anchura y color de la línea
    public void draw(PDPage page, float startX, float endX, float y, float thickness, Color color) throws IOException {
        shapeDrawer.drawLine(page, startX, y, endX, y, thickness, color);
    }
}
