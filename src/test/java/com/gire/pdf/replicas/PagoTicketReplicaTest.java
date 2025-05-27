package com.gire.pdf.replicas;

import com.gire.pdf.creators.PdfCreator;
import com.gire.pdf.model.FontStyle;
import com.gire.pdf.model.PageSize;
import org.apache.pdfbox.pdmodel.PDPage;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PagoTicketReplicaTest {

    @Test
    public void shouldMatchItextTicketVisualStyle() throws IOException {
        PdfCreator creator = new PdfCreator();

        PDPage page = new PDPage(PageSize.TICKET);
        creator.getDocument().addPage(page);

        FontStyle style = FontStyle.ticket();

        float x = 20f;
        float y = 570f;
        float spacing = 13f;

        creator.appendText(page, "RAPIPAGO             ORIGINAL", x, y, style.getFont(), style.getFontSize());
        y -= spacing;

        creator.appendText(page, "Puesto: 998294", x, y, style.getFont(), style.getFontSize());
        y -= spacing;
        creator.appendText(page, "Fecha: 01/10/2021         Hora: 16:15:13", x, y, style.getFont(), style.getFontSize());
        y -= spacing * 2;

        creator.appendText(page, "Código de Operación: 61575e42ff27c1429582828d", x, y, style.getFont(), style.getFontSize());
        y -= spacing;
        creator.appendText(page, "Empresa: 9 CLARO", x, y, style.getFont(), style.getFontSize());
        y -= spacing;
        creator.appendText(page, "Importe: $600.00", x, y, style.getFont(), style.getFontSize());
        y -= spacing;
        creator.appendText(page, "Cliente: FA0060195930", x, y, style.getFont(), style.getFontSize());
        y -= spacing;
        creator.appendText(page, "Referencia factura: 0820060195930131120000600003SF", x, y, style.getFont(), style.getFontSize());
        y -= spacing;
        creator.appendText(page, "Nro.Op:128648380697278", x, y, style.getFont(), style.getFontSize());
        y -= spacing;
        creator.appendText(page, "Cod.Seg:B6BB8388EB", x, y, style.getFont(), style.getFontSize());
        y -= spacing * 2;

        creator.appendText(page, "Forma de Pago      Importe", x, y, style.getFont(), style.getFontSize());
        y -= spacing;
        creator.appendText(page, "Efectivo           $600.00", x, y, style.getFont(), style.getFontSize());
        y -= spacing;
        creator.appendText(page, "** TOTAL **        $600.00", x, y, style.getFont(), style.getFontSize());
        y -= spacing * 2;

        creator.appendText(page, "VISÍTENOS EN WWW.RAPIPAGO.COM.AR", x + 10, y, style.getFont(), style.getFontSize());
        y -= spacing;
        creator.appendText(page, "---------------*****---------------", x + 15, y, style.getFont(), style.getFontSize());

        File file = new File("test-output/pago-replica-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
    }
}
