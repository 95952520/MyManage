package com.xuchen.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

public final class ExportExcel {


    /**
     * @param isXlsx       true-xlsx格式导出 false-xls
     * @param sheetName    sheet名称和表头值
     * @param title        标题
     * @param dataList     对象集合
     * @param filePathName 保存文件的全路径(D://2.xls)
     */
    public static void excelExport(boolean isXlsx, String sheetName, String title, List<?> dataList, String filePathName) {
        if (isXlsx && !filePathName.endsWith(".xlsx")) {
            filePathName += ".xlsx";
        } else if (!isXlsx && !filePathName.endsWith(".xls")) {
            filePathName += ".xls";
        }
        excelExport(isXlsx, sheetName, title, dataList, new File(filePathName));
    }

    /**
     * @param isXlsx    true-xlsx格式导出 false-xls
     * @param sheetName sheet名称和表头值
     * @param title     标题
     * @param dataList  对象集合
     * @param file      保存文件的file对象
     */
    public static void excelExport(boolean isXlsx, String sheetName, String title, List<?> dataList, File file) {
        createTable(isXlsx, sheetName, title, dataList);
        try {
            OutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * http下载表格
     *
     * @param fileName 生成的文件名
     * @param title    首行内容
     * @param dataList 对象集合 List<List<String>> List<Object>两种格式
     */
    public static void excelHttpExport(HttpServletResponse response, boolean isXlsx, String fileName, String sheetName, String title, List<?> dataList) {
        response.setHeader("content-Type", "application/vnd.ms-excel");
        OutputStream out = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + "." + (isXlsx ? "xlsx" : "xls"), "utf-8"));
            out = response.getOutputStream();
            createTable(isXlsx, sheetName, title, dataList);
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private synchronized static void createTable(boolean isXlsx, String sheetName, String title, List<?> list) {
        flag = isXlsx;
        initHSSFWorkbook(sheetName == null ? "sheet" : sheetName);
        Object obj = list.get(0);
        if (obj instanceof List) {
            List<String> stringList = (List<String>) list.get(0);
            length = stringList.size();
            createTitleRow(title);
            for (int i = 0; i < list.size(); i++) {
                createOneRow((List<String>) list.get(i));
            }
        } else {
            length = obj.getClass().getDeclaredFields().length;
            createTitleRow(title);
            createHeadRow(obj);
            createContentRow(list);
        }
        autoSizeColumn();
    }

    private ExportExcel() {
    }

    private static Workbook workbook;
    private static CellStyle cellStyle;
    private static Sheet sheet;
    private static boolean flag;

    /**
     * 当前行
     */
    private static int currentLine;
    private static int length;

    /**
     * 设置sheet名
     */
    private static void initHSSFWorkbook(String sheetName) {
        if (flag) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }
        sheet = workbook.createSheet(sheetName);
        cellStyle = workbook.createCellStyle();
        currentLine = 0;
    }

    /**
     * 生成标题
     *
     * @param title 标题
     */
    private static void createTitleRow(String title) {
        if (title == null || "".equals(title.trim())) {
            return;
        }
        CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, length - 1);
        sheet.addMergedRegion(titleRange);
        Row titleRow = sheet.createRow(currentLine++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
    }


    /**
     * 依据对象创建表头行
     * 对象属性名称->表头显示名称
     */
    private static void createHeadRow(Object obj) {
        Row headRow = sheet.createRow(currentLine++);
        Field[] fields = obj.getClass().getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            cellStyle.setFillForegroundColor((short) (i + 12));
            Cell headCell = headRow.createCell(i++);
            headCell.setCellStyle(cellStyle);
            headCell.setCellValue(field.getName());
        }
    }

    private static void createContentRow(List<?> dataList) {
        try {
            Field[] fields = dataList.get(0).getClass().getDeclaredFields();
            Field.setAccessible(fields, true);
            for (Object obj : dataList) {
                Row textRow = sheet.createRow(currentLine++);
                int i = 0;
                for (Field field : fields) {
                    if ("serialVersionUID".equals(field.getName())) {
                        continue;
                    }
                    String value = String.valueOf(field.get(obj));
                    Cell textCell = textRow.createCell(i++);
                    Class<?> type = field.getType();
                    if (type == Integer.class || type == int.class) {
                        textCell.setCellValue(Integer.parseInt(value));
//                    }else if (type == Double.class || type == double.class || type == Float.class || type == float.class){
//                        textCell.setCellType(Cell.CELL_TYPE_NUMERIC);
//                        textCell.setCellValue(Float.parseFloat(value));
                    }else {
                        textCell.setCellValue("null".equals(value) ? "" : value);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createOneRow(List<String> list) {
        Row textRow = sheet.createRow(currentLine++);
        int i = 0;
        for (String s : list) {
            Cell textCell = textRow.createCell(i++);
            textCell.setCellValue("null".equals(s) ? "" : s);
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