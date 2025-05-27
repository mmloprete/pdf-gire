package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceDictionary;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceStream;
import org.apache.pdfbox.pdmodel.interactive.form.*;

import java.io.IOException;
import java.util.Collections;

public class FormBuilder {

    private final PDDocument document;
    private final PDAcroForm acroForm;

    public FormBuilder(PDDocument document) {
        this.document = document;
        this.acroForm = new PDAcroForm(document);
        document.getDocumentCatalog().setAcroForm(acroForm);
    }

    public void addTextField(PDPage page, String name, String defaultValue,
                             float x, float y, float width, float height,
                             boolean readOnly, FontStyle style) throws IOException {

        PDTextField textBox = new PDTextField(acroForm);
        textBox.setPartialName(name);

        if (readOnly) textBox.setReadOnly(true);

        String alias = getFontAlias(style.getFont());


        if (acroForm.getDefaultResources() == null) {
            acroForm.setDefaultResources(new org.apache.pdfbox.pdmodel.PDResources());
        }
        acroForm.getDefaultResources().put(COSName.getPDFName(alias), style.getFont());


        textBox.setDefaultAppearance("/" + alias + " " + style.getFontSize() + " Tf 0 g");
        textBox.setValue(defaultValue);

        PDAnnotationWidget widget = new PDAnnotationWidget();
        widget.setRectangle(new PDRectangle(x, y, width, height));
        widget.setPage(page);
        textBox.setWidgets(Collections.singletonList(widget));

        page.getAnnotations().add(widget);
        acroForm.getFields().add(textBox);
    }

    public void addCheckbox(PDPage page, String name, boolean checked,
                            float x, float y, float size, FontStyle style) throws IOException {

        PDCheckBox checkBox = new PDCheckBox(acroForm);
        checkBox.setPartialName(name);

        PDAnnotationWidget widget = new PDAnnotationWidget();
        widget.setRectangle(new PDRectangle(x, y, size, size));
        widget.setPage(page);

        PDAppearanceCharacteristicsDictionary appearance = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
        appearance.setBorderColour(new PDColor(new float[]{0, 0, 0}, PDDeviceRGB.INSTANCE));
        appearance.setBackground(new PDColor(new float[]{1, 1, 1}, PDDeviceRGB.INSTANCE));
        widget.setAppearanceCharacteristics(appearance);


        PDAppearanceStream yesAppearance = new PDAppearanceStream(document);
        yesAppearance.setResources(new PDResources());
        yesAppearance.setBBox(new PDRectangle(size, size));

        try (PDPageContentStream stream = new PDPageContentStream(document, yesAppearance)) {
            stream.setNonStrokingColor(0);
            stream.setFont(style.getFont(), style.getFontSize());
            stream.beginText();
            stream.newLineAtOffset(2, 2);
            stream.showText("X");
            stream.endText();
        }

        PDAppearanceStream offAppearance = new PDAppearanceStream(document);

        COSDictionary normalAppearances = new COSDictionary();
        normalAppearances.setItem(COSName.YES, yesAppearance);
        normalAppearances.setItem(COSName.Off, offAppearance);

        COSDictionary appearanceDict = new COSDictionary();
        appearanceDict.setItem(COSName.N, normalAppearances);

        widget.setAppearance(new PDAppearanceDictionary(appearanceDict));
        checkBox.setWidgets(Collections.singletonList(widget));

        checkBox.setValue(checked ? "Yes" : "Off");

        page.getAnnotations().add(widget);
        acroForm.getFields().add(checkBox);
    }





    private String getFontAlias(PDFont font) {
        if (font == PDType1Font.HELVETICA) return "Helv";
        if (font == PDType1Font.HELVETICA_BOLD) return "Helv-Bold";
        if (font == PDType1Font.TIMES_ROMAN) return "TiRo";
        if (font == PDType1Font.TIMES_BOLD) return "TiBo";
        if (font == PDType1Font.COURIER) return "Cour";
        return "Helv";
    }
}
