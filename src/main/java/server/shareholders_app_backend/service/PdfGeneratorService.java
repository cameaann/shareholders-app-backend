package server.shareholders_app_backend.service;

import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfGeneratorService {
    public byte[] generateTransferPdf(String shareDetails) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Share Transfer Agreement");
            contentStream.newLine();
            contentStream.showText(shareDetails);
            contentStream.endText();
            contentStream.close();

            document.save(baos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }
}
