package util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/***
 * 导出List<Object>数据到excel（最多可导出65535行）
 * 
 * @author user
 */
public final class ExportExcel {

	/***
	 * 构造方法
	 */
	private ExportExcel() {

	}

	/***
	 * 工作簿
	 */
	private static HSSFWorkbook workbook;
	
	/***
	 * 工作簿
	 */
	private static HSSFCellStyle  cellStyle;

	/***
	 * sheet
	 */
	private static HSSFSheet sheet;
	/***
	 * 标题行开始位置
	 */
	private static final int TITLE_START_POSITION = 0;

	/***
	 * 时间行开始位置
	 */
	private static final int DATEHEAD_START_POSITION = 1;

	/***
	 * 表头行开始位置
	 */
	private static final int HEAD_START_POSITION = 0;

	/***
	 * 文本行开始位置
	 */
	private static final int CONTENT_START_POSITION = 1;

	/**
	 * 
	 * @param dataList 对象集合
	 * @param filePathName 保存文件的全路径(D://2.xls)
	 * @param sheetName sheet名称和表头值
	 */
	public static void excelExport(List<?> dataList,String filePathName,String sheetName) {
		Object obj = dataList.get(0);
		Integer length = obj.getClass().getDeclaredFields().length;
		if (sheetName==null){
			sheetName="sheet";
		}
		// 初始化workbook
		initHSSFWorkbook(sheetName);
		// 标题行
//		createTitleRow(length, sheetName);
		// 时间行
//		createDateHeadRow(length);
		// 表头行
		createHeadRow(obj);
		// 文本行
		createContentRow(dataList);
		// 设置自动伸缩
		// autoSizeColumn(titleMap.size());
		// 写入处理结果
		try {
			OutputStream out = new FileOutputStream(filePathName);
			workbook.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 
	 * @param sheetName
	 *            sheetName
	 */
	private static void initHSSFWorkbook(String sheetName) {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet(sheetName);
		cellStyle=workbook.createCellStyle();
	}

	/**
	 * 生成标题（第零行创建）
	 * 
	 * @param sheetName
	 *            sheet名称
	 */
	private static void createTitleRow(Integer length, String sheetName) {
		CellRangeAddress titleRange = new CellRangeAddress(0, 0, 0, length - 1);
		sheet.addMergedRegion(titleRange);
		HSSFRow titleRow = sheet.createRow(TITLE_START_POSITION);
		HSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(sheetName);
	}

	/**
	 * 创建时间行（第一行创建）
	 * Y      对象属性名称->表头显示名称
	 */
	private static void createDateHeadRow(Integer length) {
		CellRangeAddress dateRange = new CellRangeAddress(1, 1, 0, length - 1);
		sheet.addMergedRegion(dateRange);
		HSSFRow dateRow = sheet.createRow(DATEHEAD_START_POSITION);
		HSSFCell dateCell = dateRow.createCell(0);
		dateCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	/**
	 * 创建表头行（第二行创建）
	 * Y       对象属性名称->表头显示名称
	 */
	private static void createHeadRow(Object obj) {
		// 第1行创建
		HSSFRow headRow = sheet.createRow(HEAD_START_POSITION);
		Field[] fields = obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			cellStyle.setFillForegroundColor((short)(i+12));
			HSSFCell headCell = headRow.createCell(i);
			headCell.setCellStyle(cellStyle);
			headCell.setCellValue(fields[i].getName());
		}
	}

	/**
	 * 
	 * @param dataList
	 *            对象数据集合
	 */
	private static void createContentRow(List<?> dataList) {
		try {
			int i = 0;
			Field[] fields = dataList.get(0).getClass().getDeclaredFields();
			for (Object obj : dataList) {
				HSSFRow textRow = sheet.createRow(CONTENT_START_POSITION + i);
				int j = 0;
				for (Field field : fields) {
					String type = field.getGenericType().toString(); // 获取属性的类型
					String entry = field.getName();
					String method = "get" + entry.substring(0, 1).toUpperCase() + entry.substring(1);
					Method m = obj.getClass().getMethod(method);
					String value = String.valueOf(m.invoke(obj));
					HSSFCell textCell = textRow.createCell(j);
					textCell.setCellValue(value.equals("null") ? "" : value);
					j++;
				}
				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自动伸缩列（如非必要，请勿打开此方法，耗内存）
	 * 
	 * @param size
	 *            列数
	 */
	private static void autoSizeColumn(Integer size) {
		for (int j = 0; j < size; j++) {
			sheet.autoSizeColumn(j);
		}
	}
}