package com.gire.pdf.replicas;

import com.gire.pdf.creators.LinkInserter;
import com.gire.pdf.creators.PdfCreator;
import com.gire.pdf.model.FontStyle;
import com.gire.pdf.model.PageSize;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PagoTicketReplicaTest {

    private static final float FONT_SIZE = 25f;
    private static final PDFont FONT_TYPE = PDType1Font.COURIER;
    private static final float FIRST_PADDING_TOP = 1741f - 120f;
    private static final float SPACING = 14f;
    private static final float X = 90f;

    @Test
    public void shouldMatchItextTicketVisualStyle() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage(PageSize.TICKET);

        float y = FIRST_PADDING_TOP;

        creator.appendText(page, "RAPIPAGO             ORIGINAL", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING;

        creator.appendText(page, "Puesto: 998294", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING;
        creator.appendText(page, "Fecha: 01/10/2021         Hora: 16:15:13", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING * 2;

        creator.appendText(page, "Código de Operación: 61575e42ff27c1429582828d", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING;
        creator.appendText(page, "Empresa: 9 CLARO", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING;
        creator.appendText(page, "Importe: $600.00", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING;
        creator.appendText(page, "Cliente: FA0060195930", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING;
        creator.appendText(page, "Referencia factura: 0820060195930131120000600003SF", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING;
        creator.appendText(page, "Nro.Op:128648380697278", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING;
        creator.appendText(page, "Cod.Seg:B6BB8388EB", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING * 2;

        creator.appendText(page, "Forma de Pago      Importe", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING;
        creator.appendText(page, "Efectivo           $600.00", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING;
        creator.appendText(page, "** TOTAL **        $600.00", X, y, FONT_TYPE, FONT_SIZE);
        y -= SPACING * 2;

        FontStyle style = new FontStyle(FONT_TYPE, FONT_SIZE);

        LinkInserter linkInserter = new LinkInserter(creator.getDocument());
        linkInserter.insertExternalLink(
                page,
                "VISÍTENOS EN WWW.RAPIPAGO.COM.AR",
                X + 10,
                y,
                style,
                "https://www.rapipago.com.ar",
                false
        );

        y -= SPACING;
        creator.appendText(page, "---------------*****---------------", X + 15, y, FONT_TYPE, FONT_SIZE);

        File file = new File("test-output/pago-replica-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
    }
}
