package orodja;

public class Document {
    private String name;
    private DocumentType documentType;
    private TableHeader tableHeader;
    private TableRow[] tableRows;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public TableHeader getTableHeader() {
        return tableHeader;
    }

    public void setTableHeader(TableHeader tableHeader) {
        this.tableHeader = tableHeader;
    }

    public TableRow[] getTableRows() {
        return tableRows;
    }

    public void setTableRows(TableRow[] tableRows) {
        this.tableRows = tableRows;
    }
}
