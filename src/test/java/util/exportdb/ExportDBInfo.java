package util.exportdb;


import util.ExportExcelUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出数据库的所有表信息到excel
 */
public class ExportDBInfo {
    private static String url = "jdbc:mysql://localhost:3306/dbName?useUnicode=true&characterEncoding=utf8";
    private static String username = "root";
    private static String password = "xuchen93";
    private static Connection connection;
    private static DatabaseMetaData metaData;
    private static ResultSet resultSet;

    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            metaData = connection.getMetaData();
            ResultSet set = metaData.getTables(null, null, null, new String[]{"table"});
            List<Table> list = new ArrayList<>();
            while (set.next()) {
                Table table = new Table();
                table.setTableName(set.getString(3));
                list.add(table);
            }
            for (Table table : list) {
                List<TableColumn> columnList = getColumnByName(table.getTableName(), metaData);
                table.setList(columnList);
            }
            ExportExcelUtil.exportDBList(list, "D://DBInfo.xls");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static List<TableColumn> getColumnByName(String tableName, DatabaseMetaData metaData) throws Exception {
        List<TableColumn> list = new ArrayList<>();
        resultSet = metaData.getColumns(null, null, tableName, null);
        while (resultSet.next()) {
            TableColumn tableColumn = new TableColumn();
            tableColumn.setColumnName(resultSet.getString("COLUMN_NAME"));
            tableColumn.setTypeName(resultSet.getString("TYPE_NAME"));
            tableColumn.setColumnSize(resultSet.getString("COLUMN_SIZE"));
            tableColumn.setDecimalDigits(resultSet.getString("DECIMAL_DIGITS"));
            tableColumn.setRemarks(resultSet.getString("REMARKS"));
            tableColumn.setColumnDef(resultSet.getString("COLUMN_DEF"));
            tableColumn.setIsNullable(resultSet.getString("IS_NULLABLE"));
            tableColumn.setIsAutoincrement(resultSet.getString("IS_AUTOINCREMENT"));
            list.add(tableColumn);
        }
        return list;
    }
}
