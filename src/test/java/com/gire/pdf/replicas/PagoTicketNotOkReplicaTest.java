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

public class PagoTicketNotOkReplicaTest {

    @Test
    public void shouldMatchFailedTicketVisualStyle() throws IOException {
        PdfCreator creator = new PdfCreator();
        PDPage page = creator.addBlankPage(PageSize.TICKET);

        FontStyle style = new FontStyle(PDType1Font.COURIER, 28f);
        ReceiptLayoutWriter writer = new ReceiptLayoutWriter(creator, page, style, 90f, 1595f, 32f);

        writer.line("PAGO NO EXITOSO");
        writer.skip();
        writer.line("RAPIPAGO             ORIGINAL");
        writer.line("Fecha: 01/10/2021         Hora: 14:54:37");
        writer.skip();

        writer.line("Código de Operación: 61574b5d5fc0bc319d5fad5a");
        writer.line("Empresa: 1488 MOVISTAR - RECARGAS DE CELULAR");
        writer.line("Importe: $100.00");
        writer.line("Cliente: 1151658206");
        writer.line("Referencia factura:");
        writer.line("00771151658206030888600000020211001145437");
        writer.skip();

        writer.line("Forma de Pago", 90f);
        writer.line("Importe", 320f);
        writer.skip();

        writer.line("Efectivo", 90f);
        writer.line("$100.00", 320f);

        writer.line("** TOTAL **", 90f);
        writer.line("$100.00", 320f);
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

        File file = new File("test-output/pago-replica-not-ok.pdf");
        creator.saveTo(file.getAbsolutePath());

        assertTrue(file.exists());
    }
}
