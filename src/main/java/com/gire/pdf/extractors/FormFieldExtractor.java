package com.gire.pdf.extractors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FormFieldExtractor {

    // Extrae elementos de un formulario (PDFBox)
    public List<FormFieldData> extractFields(File file) throws IOException {
        List<FormFieldData> result = new ArrayList<>();

        try (PDDocument document = PDDocument.load(file)) {
            PDAcroForm form = document.getDocumentCatalog().getAcroForm();
            if (form == null) return result;

            for (PDField field : form.getFieldTree()) {
                String name = field.getFullyQualifiedName();
                String value = field.getValueAsString();
                String type = getFieldType(field);
                result.add(new FormFieldData(name, value, type));
            }
        }

        return result;
    }

    // Devuelve el alias del campo a analizar del formulario
    private String getFieldType(PDField field) {
        if (field instanceof PDTextField) return "Text";
        if (field instanceof PDCheckBox) return "Checkbox";
        if (field instanceof PDRadioButton) return "Radio";
        if (field instanceof PDComboBox) return "ComboBox";
        if (field instanceof PDListBox) return "ListBox";
        return "Unknown";
    }

    // Formato de la data de formulario a extraer (nombre, valor y tipo)
    public static class FormFieldData {
        public final String name;
        public final String value;
        public final String type;

        public FormFieldData(String name, String value, String type) {
            this.name = name;
            this.value = value;
            this.type = type;
        }
    }
}
