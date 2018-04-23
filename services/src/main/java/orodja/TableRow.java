package orodja;

import java.util.*;

public class TableRow implements Iterable<String> {
    private List<String> row;

    public TableRow(String[] array) {
        row = new ArrayList<>(Arrays.asList(array));
    }
    public TableRow() { }

    @Override
    public Iterator<String> iterator() {
        return row.iterator();
    }

    public List<String> getRow() {
        return row;
    }

    public void setRow(List<String> row) {
        this.row = row;
    }
}
