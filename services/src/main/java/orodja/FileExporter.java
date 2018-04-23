package orodja;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class FileExporter {

    private final static String GENERATED_FILES = "generated";

    public File createFile(Document document) {
        StringBuilder sb = new StringBuilder();
        sb.append(GENERATED_FILES);
        sb.append("/");
        sb.append(document.getName());
        sb.append("_");
        sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss")));

        switch (document.getDocumentType()) {
            case CSV:
                sb.append(".csv");
                createCsv(document, sb.toString());
                break;
            case PDF:
                sb.append(".pdf");
                createPdf(document, sb.toString());
        }

        return new File(sb.toString());
    }

    private void createPdf(Document document, String fileName) {
        com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(fileName));
            doc.open();
            Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);

            addDocumentHeaders(doc);
            PdfPTable table = createTable(document);
            doc.add(table);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        doc.close();
    }

    private void createCsv(Document document, String fileName) {
        return;
    }

    private PdfPTable createTable(Document document) {
        PdfPTable table = new PdfPTable(document.getTableHeader().getRow().size());
        addTableHeader(document.getTableHeader(), table);
        addRows(document.getTableRows(), table);
        return table;
    }

    private void addDocumentHeaders(com.itextpdf.text.Document document) {
    }

    private void addTableHeader(TableHeader tableHeader, PdfPTable table) {
        for(String hCell : tableHeader) {
            PdfPCell header = new PdfPCell();
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(hCell));
            table.addCell(header);
        };
    }

    private void addRows(TableRow[] rows, PdfPTable table) {
        for(TableRow tableRow : rows) {
            for (String celica : tableRow) {
                table.addCell(celica);
            }
        }
    }
}
