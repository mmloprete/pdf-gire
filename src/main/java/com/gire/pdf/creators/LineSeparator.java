package com.gire.pdf.creators;

import org.apache.pdfbox.pdmodel.PDPage;

import java.awt.Color;
import java.io.IOException;

public class LineSeparator {

    private final ShapeDrawer shapeDrawer;

    public LineSeparator(ShapeDrawer shapeDrawer) {
        this.shapeDrawer = shapeDrawer;
    }

    public void draw(PDPage page, float startX, float endX, float y, float thickness, Color color) throws IOException {
        shapeDrawer.drawLine(page, startX, y, endX, y, thickness, color);
    }
}
