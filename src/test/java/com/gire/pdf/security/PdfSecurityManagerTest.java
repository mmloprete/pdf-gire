package com.gire.pdf.security;

import com.gire.pdf.creators.PdfCreator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfSecurityManagerTest {

    @Test
    public void shouldEncryptAndDecryptPdf() throws IOException {

        PdfCreator creator = new PdfCreator();
        creator.addBlankPage();
        File original = File.createTempFile("secured-original", ".pdf");
        original.deleteOnExit();
        creator.saveTo(original.getAbsolutePath());

        String userPass = "user123";
        String ownerPass = "owner123";
        AccessPermission perms = new AccessPermission();
        perms.setCanPrint(false);
        perms.setCanModify(false);

        File encrypted = File.createTempFile("secured-encrypted", ".pdf");
        encrypted.deleteOnExit();

        PdfSecurityManager manager = new PdfSecurityManager();
        manager.protect(original, encrypted, ownerPass, userPass, perms);

        assertTrue(encrypted.exists());

        File decrypted = File.createTempFile("secured-decrypted", ".pdf");
        decrypted.deleteOnExit();
        manager.removeSecurity(encrypted, decrypted, ownerPass);

        try (PDDocument result = PDDocument.load(decrypted)) {
            assertTrue(result.getNumberOfPages() == 1);
        }
    }
}
