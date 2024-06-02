package org.abol.springstarter.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    private static final String UPLOADS_DIR = "uploads/";

    public String generatePdf(String content, String fileName) throws DocumentException, IOException {
        // Ensure the directory exists
        File uploadsDir = new File(UPLOADS_DIR);
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }

        // Create the full file path
        String filePath = UPLOADS_DIR + fileName;

        // Create the PDF document
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();
        document.add(new Paragraph(content));
        document.close();

        return filePath;
    }
}