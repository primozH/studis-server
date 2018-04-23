package orodja;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TableHeader implements Iterable<String> {
    private List<String> row;

    public TableHeader(String[] array) {
        row = new ArrayList<>(Arrays.asList(array));
    }

    public TableHeader() { }

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
