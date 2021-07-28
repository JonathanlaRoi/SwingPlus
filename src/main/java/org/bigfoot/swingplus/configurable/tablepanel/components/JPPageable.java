package org.bigfoot.swingplus.configurable.tablepanel.components;

import lombok.Getter;
import lombok.Setter;

@Getter
public class JPPageable {

    @Setter
    private long page = 1;

    @Setter
    private long total;

    private int rowsPerPage;

    public JPPageable(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getMaxPage() {
        return Math.max(1, (int) Math.ceil((double) total / rowsPerPage));
    }

    public int getPageAsInt() {
        return Long.valueOf(page).intValue();
    }

    public long getOffset() {
        return (getPage() - 1) * getRowsPerPage();
    }

    public int getOffsetAsInt() {
        return Long.valueOf((getPage() - 1) * getRowsPerPage()).intValue();
    }

    @Override
    public String toString() {
        return "page:" + page + "\n" +
                "total:" + total + "\n" +
                "rowsPerPage:" + rowsPerPage + "\n" +
                "getMaxPage():" + getMaxPage();
    }
}
