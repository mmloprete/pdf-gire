package com.gire.pdf.extractors;

import com.gire.pdf.creators.FormBuilder;
import com.gire.pdf.creators.PdfCreator;
import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FormFieldExtractorTest {

    @Test
    public void shouldExtractFormFields() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        FormBuilder formBuilder = new FormBuilder(creator.getDocument());
        FontStyle style = new FontStyle(PDType1Font.HELVETICA, 10);

        formBuilder.addTextField(page, "nombre", "Juan", 100, 700, 200, 20, false, style);
        formBuilder.addCheckbox(page, "acepto", true, 100, 650, 15, style);

        File file = File.createTempFile("formfield-extractor-test", ".pdf");
        file.deleteOnExit();
        creator.saveTo(file.getAbsolutePath());

        FormFieldExtractor extractor = new FormFieldExtractor();
        List<FormFieldExtractor.FormFieldData> fields = extractor.extractFields(file);

        assertEquals(2, fields.size());

        FormFieldExtractor.FormFieldData nombreField = fields.stream()
                .filter(f -> f.name.equals("nombre"))
                .findFirst()
                .orElse(null);

        assertNotNull(nombreField);
        assertEquals("Juan", nombreField.value);
        assertEquals("Text", nombreField.type);

        FormFieldExtractor.FormFieldData checkboxField = fields.stream()
                .filter(f -> f.name.equals("acepto"))
                .findFirst()
                .orElse(null);

        assertNotNull(checkboxField);
        assertEquals("Yes", checkboxField.value);
        assertEquals("Checkbox", checkboxField.type);
    }
}
