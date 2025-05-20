package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import com.gire.pdf.model.TextAlignment;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AdvancedTableBuilder {

    private final PDDocument document;
    private float[] columnWidths;
    private float rowHeight = 20f;
    private float cellPadding = 5f;
    private boolean drawBorders = true;
    private boolean autoColumnWidths = false;

    private FontStyle cellStyle = FontStyle.normal();
    private FontStyle headerStyle = FontStyle.title();
    private boolean hasHeader = true;

    private TextAlignment alignment = TextAlignment.LEFT;

    public AdvancedTableBuilder(PDDocument document) {
        this.document = document;
    }

    public AdvancedTableBuilder withColumnWidths(float... widths) {
        this.columnWidths = widths;
        return this;
    }

    public AdvancedTableBuilder withAutoColumnWidths() {
        this.autoColumnWidths = true;
        return this;
    }

    public AdvancedTableBuilder withRowHeight(float height) {
        this.rowHeight = height;
        return this;
    }

    public AdvancedTableBuilder withCellPadding(float padding) {
        this.cellPadding = padding;
        return this;
    }

    public AdvancedTableBuilder withCellStyle(FontStyle style) {
        this.cellStyle = style;
        return this;
    }

    public AdvancedTableBuilder withHeaderStyle(FontStyle style) {
        this.headerStyle = style;
        return this;
    }

    public AdvancedTableBuilder withBorders(boolean enabled) {
        this.drawBorders = enabled;
        return this;
    }

    public AdvancedTableBuilder withHeader(boolean enabled) {
        this.hasHeader = enabled;
        return this;
    }

    public AdvancedTableBuilder withAlignment(TextAlignment align) {
        this.alignment = align;
        return this;
    }

    public void drawTable(PDPage page, float startX, float startY, List<List<String>> data) throws IOException {
        if (data.isEmpty()) return;

        int cols = data.get(0).size();

        // Si se pidió ancho automático
        if (autoColumnWidths) {
            columnWidths = new float[cols];
            for (int i = 0; i < cols; i++) {
                float max = 0;
                for (List<String> row : data) {
                    String text = row.get(i);
                    float width = cellStyle.getFont().getStringWidth(text) / 1000 * cellStyle.getFontSize();
                    max = Math.max(max, width + cellPadding * 2);
                }
                columnWidths[i] = max;
            }
        }

        float y = startY;

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
            for (int rowIndex = 0; rowIndex < data.size(); rowIndex++) {
                List<String> row = data.get(rowIndex);
                FontStyle style = (hasHeader && rowIndex == 0) ? headerStyle : cellStyle;

                contentStream.setFont(style.getFont(), style.getFontSize());
                float x = startX;

                for (int colIndex = 0; colIndex < row.size(); colIndex++) {
                    float colWidth = columnWidths[colIndex];
                    String cellText = row.get(colIndex);

                    if (drawBorders) {
                        contentStream.addRect(x, y - rowHeight, colWidth, rowHeight);
                        contentStream.stroke();
                    }

                    float textWidth = style.getFont().getStringWidth(cellText) / 1000 * style.getFontSize();
                    float textX = x + cellPadding;

                    if (alignment == TextAlignment.CENTER) {
                        textX = x + (colWidth - textWidth) / 2;
                    } else if (alignment == TextAlignment.RIGHT) {
                        textX = x + colWidth - textWidth - cellPadding;
                    }

                    contentStream.beginText();
                    contentStream.newLineAtOffset(textX, y - rowHeight + cellPadding);
                    contentStream.showText(cellText);
                    contentStream.endText();

                    x += colWidth;
                }
                y -= rowHeight;
            }
        }
    }
}
