package com.refacFabela.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.jupiter.api.Test;

class envioMailTest {

    @Test
    void crearZipFacturasRelacionadasIncluyeFacturaPrincipalYParciales() throws Exception {
        Path tempDir = Files.createTempDirectory("envioMailTest");
        Path pdfDir = tempDir.resolve("pdf");
        Path xmlDir = tempDir.resolve("xml");
        Files.createDirectories(pdfDir);
        Files.createDirectories(xmlDir);

        Files.write(pdfDir.resolve("1001.pdf"), new byte[] { 1, 2, 3 });
        Files.write(pdfDir.resolve("1001_P1.pdf"), new byte[] { 4, 5, 6 });
        Files.write(xmlDir.resolve("1001.xml"), "xml-principal".getBytes(StandardCharsets.UTF_8));
        Files.write(xmlDir.resolve("1001_P1.xml"), "xml-parcial".getBytes(StandardCharsets.UTF_8));

        byte[] zipBytes = envioMail.crearZipFacturasRelacionadas(tempDir.toString(), "1001");

        assertNotNull(zipBytes);
        assertTrue(zipBytes.length > 0);

        List<String> entries = new ArrayList<>();
        try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipBytes))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                entries.add(entry.getName());
            }
        }

        assertTrue(entries.contains("1001.pdf"));
        assertTrue(entries.contains("1001_P1.pdf"));
        assertTrue(entries.contains("1001.xml"));
        assertTrue(entries.contains("1001_P1.xml"));
    }
}
