package com.gire.pdf.creators;

import com.gire.pdf.model.FontStyle;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormBuilderTest {

    @Test
    public void shouldAddTextFieldWithFontStyle() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        FontStyle inputStyle = new FontStyle(PDType1Font.TIMES_ROMAN, 11);

        FormBuilder formBuilder = new FormBuilder(creator.getDocument());
        formBuilder.addTextField(page, "nombre", "XXXX", 100, 650, 200, 20, false, inputStyle);
        formBuilder.addTextField(page, "email", "XXXX@gmail.com", 100, 600, 200, 20, false, inputStyle);

        creator.appendText(page, "Nombre:", 100, 675);
        creator.appendText(page, "Email:", 100, 625);

        File file = new File("test-output/form-textfield-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        /*
        boolean deleted = file.delete();
        if (!deleted) {
            System.err.println("No se pudo eliminar el archivo de prueba: " + file.getAbsolutePath());
        }
        */
    }

    @Test
    public void shouldAddCheckboxesWithLabels() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage();

        FontStyle style = new FontStyle(PDType1Font.HELVETICA, 10);
        FormBuilder formBuilder = new FormBuilder(creator.getDocument());

        formBuilder.addCheckbox(page, "acepto", true, 100, 650, 15, style);
        creator.appendText(page, "Acepto términos y condiciones", 120, 652);

        formBuilder.addCheckbox(page, "newsletter", false, 100, 600, 15, style);
        creator.appendText(page, "Suscribirme al boletín", 120, 602);

        File file = new File("test-output/form-checkbox-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
        assertTrue(file.length() > 0);
        /*
        boolean deleted = file.delete();
        if (!deleted) {
            System.err.println("No se pudo eliminar el archivo de prueba: " + file.getAbsolutePath());
        }
        */
    }
}
