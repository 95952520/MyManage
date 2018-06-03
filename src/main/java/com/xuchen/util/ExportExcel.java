package com.xuchen.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

/***
 * 导出List<Object>数据到excel（最多可导出65535行）
 *
 * @author user
 */
public final class ExportExcel {


    /**
     * @param dataList     对象集合
     * @param filePathName 保存文件的全路径(D://2.xls)
     * @param sheetName    sheet名称和表头值
     */
    public static void excelExport(String sheetName, String title, List<?> dataList, String filePathName) {
        createTable(sheetName, title, dataList);
        try {
            OutputStream out = new FileOutputStream(filePathName);
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
    public static void excelHttpExport(HttpServletResponse response, String fileName, String sheetName, String title, List<?> dataList) {
        response.setHeader("content-Type", "application/vnd.ms-excel");
        OutputStream out = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "utf-8"));
            out = response.getOutputStream();
            createTable(sheetName, title, dataList);
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

    private synchronized static void createTable(String sheetName, String title, List<?> list) {
        initHSSFWorkbook(sheetName == null ? "sheet" : sheetName);
        createTitleRow(title);
        Object obj = list.get(0);
        if (obj instanceof List) {
            List<String> stringList = (List<String>) list.get(0);
            length = stringList.size();
            for (int i = 0; i < list.size(); i++) {
                createOneRow((List<String>) list.get(i));
            }
        } else {
            length = obj.getClass().getDeclaredFields().length;
            createHeadRow(obj);
            createContentRow(list);
        }
        autoSizeColumn();
    }

    private ExportExcel() {
    }

    private static HSSFWorkbook workbook;
    private static HSSFCellStyle cellStyle;
    private static HSSFSheet sheet;
    /**
     * 当前行
     */
    private static int currentLine;
    private static int length;

    /**
     * 设置sheet名
     */
    private static void initHSSFWorkbook(String sheetName) {
        workbook = new HSSFWorkbook();
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
        HSSFRow titleRow = sheet.createRow(currentLine++);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
    }


    /**
     * 依据对象创建表头行
     * 对象属性名称->表头显示名称
     */
    private static void createHeadRow(Object obj) {
        HSSFRow headRow = sheet.createRow(currentLine++);
        Field[] fields = obj.getClass().getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            if ("serialVersionUID".equals(field.getName())){
                continue;
            }
            cellStyle.setFillForegroundColor((short) (i + 12));
            HSSFCell headCell = headRow.createCell(i++);
            headCell.setCellStyle(cellStyle);
            headCell.setCellValue(field.getName());
        }
    }

    private static void createContentRow(List<?> dataList) {
        try {
            Field[] fields = dataList.get(0).getClass().getDeclaredFields();
            Field.setAccessible(fields, true);
            for (Object obj : dataList) {
                HSSFRow textRow = sheet.createRow(currentLine++);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createOneRow(List<String> list) {
        HSSFRow textRow = sheet.createRow(currentLine++);
        int i = 0;
        for (String s : list) {
            HSSFCell textCell = textRow.createCell(i++);
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