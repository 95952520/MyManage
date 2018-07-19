package util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.exportdb.Table;
import util.exportdb.TableColumn;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

/***
 * 导出List<Object>数据到excel（最多可导出65535行）
 */
public final class ExportExcelUtil {
    private ExportExcelUtil() {
    }

    /**
     * 工作簿
     */
    private static HSSFWorkbook workbook;
    private static HSSFCellStyle cellStyle;
    private static HSSFFont font;
    /**
     * sheet
     */
    private static HSSFSheet sheet;
    /**
     * 当前所在行
     */
    private static int contentLine;
    /**
     * 总列数
     */
    private static int length;

    /**
     * @param dataList     对象集合
     * @param filePathName 保存文件的全路径(D://1.xls)
     */
    public static void exportExcel(List<?> dataList, String filePathName) throws Exception {
        Object obj = dataList.get(0);
        length = obj.getClass().getDeclaredFields().length;
        initHSSFWorkbook();
        createOneRow(obj);
        createListRow(dataList);
        autoSizeColumn();
        try (OutputStream out = new FileOutputStream(filePathName)){
            workbook.write(out);
        }
    }

    /**
     * 导出表格集合对象
     */
    public static void exportDBList(List<Table> dataList, String filePathName) throws Exception {
        TableColumn obj = dataList.get(0).getList().get(0);
        length = obj.getClass().getDeclaredFields().length;
        initHSSFWorkbook();
        createRow(dataList);
        autoSizeColumn();
        try (OutputStream out = new FileOutputStream(filePathName)){
            workbook.write(out);
        }
    }

    private static void initHSSFWorkbook() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet("sheet");
        cellStyle = workbook.createCellStyle();
        font = workbook.createFont();
        contentLine = 0;
    }

    private static void createRow(List<Table> dataList) throws Exception {
        for (Table table : dataList) {
            HSSFRow row = sheet.createRow(contentLine++);
            HSSFCell rowCell = row.createCell(0);
            rowCell.setCellValue(table.getTableName());
            createOneRow(dataList.get(0).getList().get(0));
            createListRow(table.getList());
        }
    }

    /**
     * 创建集合列名
     */
    private static void createOneRow(Object obj) {
        HSSFRow headRow = sheet.createRow(contentLine++);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            HSSFCell textCell = headRow.createCell(i);
            textCell.setCellValue(fields[i].getName());
        }
    }

    /**
     * 创建集合值
     */
    private static void createListRow(List detailList) throws Exception {
        Field[] fields = detailList.get(0).getClass().getDeclaredFields();
        for (Object obj : detailList) {
            HSSFRow textRow = sheet.createRow(contentLine++);
            int i = 0;
            for (Field field : fields) {
                if ("serialVersionUID".equals(field.getName())){
                    continue;
                }
                String value = String.valueOf(field.get(obj));
                HSSFCell textCell = textRow.createCell(i++);
                textCell.setCellValue("null".equals(value) ? "" : value);
            }
        }
    }

    /**
     * 自动伸缩列（如非必要，请勿打开此方法，耗内存）
     */
    private static void autoSizeColumn() {
        for (int j = 0; j < length; j++) {
            sheet.autoSizeColumn(j);
        }
    }
}