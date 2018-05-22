package orodja;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import orodja.export.Metadata;
import orodja.export.TableHeader;
import orodja.export.TableRow;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@ApplicationScoped
public class FileExporter {

    private final static Logger log = Logger.getLogger(FileExporter.class.getName());

    private final static String GENERATED_FILES = "generated";
    private final static char separator = ',';
    private String NOTOSANS_BOLD = "/fonts/NotoSans-Bold.ttf";
    private String NOTOSANS_REGULAR = "/fonts/NotoSans-Regular.ttf";
    private Font notoRegular;

    @PostConstruct
    public void init() {
        FontFactory.register(NOTOSANS_BOLD, "notosans-bold");
        FontFactory.register(NOTOSANS_REGULAR, "notosans-regular");

        notoRegular = FontFactory.getFont("notosans-regular", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8);
    }

    public File createFile(orodja.export.Document document) {
        StringBuilder sb = new StringBuilder();
//        sb.append(GENERATED_FILES);
//        sb.append("/");
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

    private void createPdf(orodja.export.Document document, String fileName) {
        log.info("Ustvarjanje PDF dokumenta: " + fileName);
        com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(fileName));
            writer.setPageEvent(new Footer());
            doc.setPageSize(PageSize.A4.rotate());
            doc.open();

            Paragraph paragraph = new Paragraph();
            paragraph.setFont(FontFactory.getFont("notosans-bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 20));
            paragraph.add(new Chunk(document.getName() != null ? document.getName() : ""));
            doc.add(paragraph);

            addDocumentHeaders(document.getMetadata(), doc);
            PdfPTable table = createTable(document);
            table.setSpacingBefore(50f);
            doc.add(table);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        doc.close();
        log.info("Postopek uspesno zakljucen");
    }

    private void createCsv(orodja.export.Document document, String fileName) {
        log.info("Ustvarjanje CSV dokumenta: " + fileName);
        TableHeader header = document.getTableHeader();
        TableRow[] tableRow = document.getTableRows();

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            writeMetadataCsv(document.getMetadata(), fileWriter);
            addHeaders(header, fileWriter);
            addRowsCsv(tableRow, fileWriter);

            fileWriter.flush();
            log.info("Postopek uspesno zakljucen");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private PdfPTable createTable(orodja.export.Document document) {
        PdfPTable table = new PdfPTable(document.getTableHeader().getRow().size());
        addTableHeader(document.getTableHeader(), table);
        addRows(document.getTableRows(), table);
        return table;
    }

    private void addDocumentHeaders(Metadata metadata, com.itextpdf.text.Document document) {

        if (metadata != null) {
            String studyYear = metadata.getStudyYear() != null ?
                    metadata.getStudyYear().getStudijskoLeto() : null;
            String studyProgramme = metadata.getStudyProgramme() != null ?
                    metadata.getStudyProgramme().getNaziv() : null;
            String studyProgrammeCode = metadata.getStudyProgramme() != null ?
                    metadata.getStudyProgramme().getSifraEVS().toString() : null;
            String course = metadata.getSubject() != null ?
                    metadata.getSubject().getNaziv() : null;
            String courseCode = metadata.getSubject() != null ?
                    metadata.getSubject().getSifra().toString() : null;
            String yearOfStudy = metadata.getYearOfStudy() != null ?
                    metadata.getYearOfStudy().getLetnik().toString() : null;

            Paragraph paragraph = new Paragraph();
            paragraph.setFont(notoRegular);

            Chunk chunk;
            try {
                if (course != null) {
                    chunk = new Chunk(course + " (" + courseCode + ")",
                            FontFactory.getFont("notosans-bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12));
                    paragraph.add(chunk);
                    paragraph.add(Chunk.NEWLINE);
                }
                if (studyProgramme != null) {
                    chunk = new Chunk(studyProgramme + " (" + studyProgrammeCode + ")");
                    paragraph.add(chunk);
                    paragraph.add(Chunk.NEWLINE);
                }
                if (yearOfStudy != null) {
                    chunk = new Chunk(yearOfStudy + ". letnik");
                    paragraph.add(chunk);
                    paragraph.add(Chunk.NEWLINE);
                }
                if (studyYear != null) {
                    chunk = new Chunk("Študijsko leto: " + studyYear);
                    paragraph.add(chunk);
                    paragraph.add(Chunk.NEWLINE);
                }
                document.add(paragraph);
            } catch(DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private void addTableHeader(TableHeader tableHeader, PdfPTable table) {
        for(String hCell : tableHeader) {
            PdfPCell header = new PdfPCell();
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(hCell, notoRegular));
            table.addCell(header);
        };
    }

    private void addRows(TableRow[] rows, PdfPTable table) {
        for(TableRow tableRow : rows) {
            for (String celica : tableRow) {
                table.addCell(new Phrase(celica, notoRegular));
            }
        }
    }

    private void writeMetadataCsv(Metadata metadata, FileWriter fileWriter) throws IOException {
        if (metadata != null) {
            StringBuilder sb = new StringBuilder();

            if (metadata.getStudyYear() != null) {
                sb.append(metadata.getStudyYear().getStudijskoLeto());
                sb.append("\n");
            }
            if (metadata.getStudyProgramme() != null) {
                sb.append(metadata.getStudyProgramme().getNaziv());
                sb.append(separator);
                sb.append(metadata.getStudyProgramme().getSifraEVS().toString());
                sb.append("\n");
            }
            if (metadata.getSubject() != null) {
                sb.append(metadata.getSubject().getNaziv());
                sb.append(separator);
                sb.append(metadata.getSubject().getSifra().toString());
                sb.append("\n");
            }
            if (metadata.getYearOfStudy() != null) {
                sb.append(metadata.getYearOfStudy().getLetnik().toString());
                sb.append("\n");
            }
            fileWriter.append(sb.toString());
        }
    }

    private void addHeaders(TableHeader header, FileWriter fileWriter) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(String cell : header) {
            if (!first) {
                sb.append(separator);
            }
            sb.append(cell);
            first = false;
        }
        sb.append("\n");
        fileWriter.append(sb.toString());
    }

    private void addRowsCsv(TableRow[] rows, FileWriter fw) throws IOException {
        for (TableRow row : rows) {
            boolean first = true;
            StringBuilder sb = new StringBuilder();
            for(String cell : row) {
                if (!first) {
                    sb.append(separator);
                }
                sb.append(cell);
                first = false;
            }
            sb.append("\n");
            fw.append(sb.toString());
        }
    }

    /**
     * Inner class to add a table as header.
     */
    class Footer extends PdfPageEventHelper {
        PdfTemplate total;

        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(10, 16);
        }

        /**
         * Adds a header to every page
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable table = new PdfPTable(2);
            try {
                table.setTotalWidth(130);
                table.setLockedWidth(true);
                table.getDefaultCell().setFixedHeight(20);
                table.getDefaultCell().setBorder(Rectangle.TOP);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(String.format("Stran %d od", writer.getPageNumber()));
                PdfPCell cell = new PdfPCell(Image.getInstance(total));
                cell.setBorder(Rectangle.TOP);
                table.addCell(cell);
                table.writeSelectedRows(0, -1, 34, document.bottom(), writer.getDirectContent());
            }
            catch(DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }

        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber())),
                    2, 2, 0);
        }
    }
}
