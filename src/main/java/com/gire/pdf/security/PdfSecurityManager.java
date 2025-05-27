package com.gire.pdf.security;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import java.io.File;
import java.io.IOException;

public class PdfSecurityManager {

    public void protect(File inputFile, File outputFile, String ownerPassword, String userPassword, AccessPermission permission) throws IOException {
        try (PDDocument document = PDDocument.load(inputFile)) {
            StandardProtectionPolicy policy = new StandardProtectionPolicy(ownerPassword, userPassword, permission);
            policy.setEncryptionKeyLength(128);
            policy.setPermissions(permission);

            document.protect(policy);
            document.save(outputFile);
        }
    }


    public void removeSecurity(File inputFile, File outputFile, String ownerPassword) throws IOException {
        try (PDDocument document = PDDocument.load(inputFile, ownerPassword)) {
            document.setAllSecurityToBeRemoved(true);
            document.save(outputFile);
        }
    }
}
