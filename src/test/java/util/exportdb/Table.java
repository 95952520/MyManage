package util.exportdb;

import java.util.List;

public class Table {
    private String tableName;
    private List<TableColumn> list;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableColumn> getList() {
        return list;
    }

    public void setList(List<TableColumn> list) {
        this.list = list;
    }
}
