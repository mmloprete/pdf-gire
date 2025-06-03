package com.gire.pdf.replicas;

import com.gire.pdf.creators.LinkInserter;
import com.gire.pdf.creators.PdfCreator;
import com.gire.pdf.creators.ReceiptLayoutWriter;
import com.gire.pdf.model.FontStyle;
import com.gire.pdf.model.PageSize;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PagoTicketReplicaTest {

    @Test
    public void shouldMatchItextTicketVisualStyle() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage(PageSize.TICKET);

        FontStyle style = new FontStyle(PDType1Font.COURIER, 22f);
        ReceiptLayoutWriter writer = new ReceiptLayoutWriter(creator, page, style, 90f, 1595f, 32f);

        writer.line("RAPIPAGO             ORIGINAL");
        writer.line("Puesto: 998294");
        writer.line("Fecha: 01/10/2021         Hora: 16:15:13");
        writer.skip();
        writer.skip();

        writer.line("Código de Operación: 61575e42ff27c1429582828d");
        writer.line("Empresa: 9 CLARO");
        writer.line("Importe: $600.00");
        writer.line("Cliente: FA0060195930");
        writer.line("Referencia factura: 0820060195930131120000600003SF");
        writer.line("Nro.Op:128648380697278");
        writer.line("Cod.Seg:B6BB8388EB");
        writer.skip();
        writer.skip();

        writer.line("Forma de Pago      Importe");
        writer.line("Efectivo           $600.00");
        writer.line("** TOTAL **        $600.00");
        writer.skip();
        writer.skip();
        writer.skip();


        new LinkInserter(creator.getDocument()).insertExternalLink(
                page,
                "VISÍTENOS EN WWW.RAPIPAGO.COM.AR",
                100f,
                writer.getCurrentY(),
                style,
                "https://www.rapipago.com.ar",
                false
        );
        writer.skip();

        writer.line("---------------*****---------------", 105f);

        File file = new File("test-output/pago-replica-test.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
    }
}
