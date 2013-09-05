package com.ku6ads.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.ku6ads.dao.base.BaseDao;
import com.ku6ads.dao.entity.ExtEntity;
import com.ku6ads.services.base.BaseAbstractService;

public class XLSExportor {
	
	private static Logger logger = Logger.getLogger(XLSExportor.class);
	
	/**
	 * 导出数据<br>
	 * 该方法用于导出大数据量<br>
	 * <a style="color: #FF0000; font-weight: bold; font-size: 20">注意：该方法导出时，之前的SQL语句可能会有错误，请尽可能使用来导出doExport(HttpServletRequest request, HttpServletResponse response, BaseServiceIface service, ExtEntity paramObj, String title, ExtLimit limit)<a>
	 * @param request
	 * @param response
	 * @param conn 数据库连接
	 * @param sql 需要执行的SQL语句
	 * @param title 标题
	 * @param limit 导出的表头以及数据列信息
	 */
	@SuppressWarnings("deprecation")
	public static void doExport(HttpServletRequest request, HttpServletResponse response, Connection conn, String sql, String title, ExtLimit limit) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(title);
			
			String[] exp_column_names = limit.getExp_column_names().split(",");
			String[] exp_column_indexs = limit.getExp_column_indexs().split(",");
			
			HSSFCellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(HSSFColor.LIME.index);
		    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			//生成表头
			HSSFRow row = sheet.createRow(0);
			for(int j = 0; j < exp_column_names.length; j++) {
				HSSFCell cell = row.createCell((short)j);
//				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellStyle(style);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(exp_column_names[j]);
			}
			
			Statement stmt = null;
			ResultSet rs = null;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int i = 1;
			while(rs.next()) {
				row = sheet.createRow(i++);
				
				for(int j = 0; j < exp_column_indexs.length; j++) {
					Object cellValue = null;
					
					try {
						cellValue = rs.getObject(exp_column_indexs[j]);
					}
					catch (Exception e) {
					}
					
					if(cellValue == null)
						continue;
					
					HSSFCell cell = row.createCell((short)j);
//					cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					if(cellValue == null) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue("");
					}
					else {
						if(cellValue instanceof java.util.Date) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(cellValue));
						}
						else {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(cellValue.toString());
						}
					}
				}
			}
			
			rs.close();
			stmt.close();
			
			response.setHeader("Content-Disposition", "attachment;filename=\"" + limit.getExp_name() + "\"");
	        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        response.setHeader("Pragma", "public");
	        response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	        response.setContentType("application/vnd.ms-excel" + ";charset=UTF-8");
	        wb.write(response.getOutputStream());
	        response.getOutputStream().flush();
	        response.getOutputStream().close();
		}
		catch (Exception e) {
			logger.error("导出数据错误！", e);
		}
		finally {
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param baseDao
	 * @param countMethod 记录数方法名称
	 * @param dataMethod 分页查询方法名称
	 * @param paramObj
	 * @param title
	 * @param exp_column_names
	 * @param exp_column_indexs
	 */
	@SuppressWarnings("unchecked")
	public static void doExport(HttpServletRequest request, HttpServletResponse response, BaseDao baseDao, String countMethod, String dataMethod, ExtEntity paramObj, String title, String fileName, String[] exp_column_names, String[] exp_column_indexs) {
		try {
			if(exp_column_names == null || exp_column_names.length == 0 || exp_column_indexs == null || exp_column_indexs.length == 0 || exp_column_indexs.length != exp_column_names.length)
				return;
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(title);
			createHeader(sheet, wb.createCellStyle(), exp_column_names);
			
			ExtLimit limit = new ExtLimit();
			int count = (Integer) EntityReflect.invokeMethod(baseDao, countMethod, paramObj);
			int startOffset = 0;
			int pageSize = 200;
			limit.setLimit(pageSize);
			
			int rowIndex = 1;
			//生成数据
			while(startOffset < count) {
				limit.setStart(startOffset);
				paramObj.setExtLimit(limit);
				List dataList =  (List) EntityReflect.invokeMethod(baseDao, dataMethod, paramObj);
				addDataToSheet(sheet, dataList, exp_column_indexs, rowIndex);
				rowIndex += dataList.size();
				startOffset += dataList.size();
			}
			exportWorkbook(response, wb, fileName);
		}
		catch (Exception e) {
			logger.error("导出数据错误！", e);
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param baseDao
	 * @param paramObj
	 * @param title
	 * @param exp_column_names 指定列头
	 * @param exp_column_indexs 列头对应的属性名称
	 */
	@SuppressWarnings("unchecked")
	public static void doExport(HttpServletRequest request, HttpServletResponse response, BaseDao baseDao, ExtEntity paramObj, String title, String fileEnName, String[] exp_column_names, String[] exp_column_indexs) {
		try {
			if(exp_column_names == null || exp_column_names.length == 0 || exp_column_indexs == null || exp_column_indexs.length == 0 || exp_column_indexs.length != exp_column_names.length)
				return;
			
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(title);
			createHeader(sheet, wb.createCellStyle(), exp_column_names);
			
			ExtLimit limit = new ExtLimit();
			int count = baseDao.selectLimitCount(paramObj);
			int startOffset = 0;
			int pageSize = 200;
			limit.setLimit(pageSize);
			
			int rowIndex = 1;
			//生成数据
			while(startOffset < count) {
				limit.setStart(startOffset);
				paramObj.setExtLimit(limit);
				List dataList = baseDao.selectByLimit(paramObj);
				addDataToSheet(sheet, dataList, exp_column_indexs, rowIndex);
				rowIndex += dataList.size();
				startOffset += dataList.size();
			}
			exportWorkbook(response, wb, fileEnName);
		}
		catch (Exception e) {
			logger.error("导出数据错误！", e);
		}
	}
	
	/**
	 * 导出大数据<br>
	 * 该方法导出时，将通过分页查询的方式多次查询数据库，每次查询页大小默认为200<br>
	 * 所以，在基础DAO中，必须实现好selectLimitCount和selectByLimit两个方法，否则无法正常导出！
	 * @param request
	 * @param response
	 * @param service BaseServiceIface
	 * @param paramObj  ExtEntity
	 * @param title
	 * @param limit
	 */
	@SuppressWarnings("unchecked")
	public static void doExport(HttpServletRequest request, HttpServletResponse response, BaseDao baseDao, ExtEntity paramObj, String title, ExtLimit limit) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(title);
			
			String[] exp_column_names = limit.getExp_column_names().split(",");
			String[] exp_column_indexs = limit.getExp_column_indexs().split(",");
			
			createHeader(sheet, wb.createCellStyle(), exp_column_names);
			
			int count = baseDao.selectLimitCount(paramObj);
			int startOffset = 0;
			int pageSize = 200;
			limit.setLimit(pageSize);
			
			int rowIndex = 1;
			//生成数据
			while(startOffset < count) {
				limit.setStart(startOffset);
				paramObj.setExtLimit(limit);
				List dataList = baseDao.selectByLimit(paramObj);
				addDataToSheet(sheet, dataList, exp_column_indexs, rowIndex);
				rowIndex += dataList.size();
				startOffset += dataList.size();
			}
			
			exportWorkbook(response, wb, limit.getExp_name());
		}
		catch (Exception e) {
			logger.error("导出数据错误！", e);
		}
	}
	
	/**
	 * 导出大数据<br>
	 * 该方法导出时，将通过分页查询的方式多次查询数据库，每次查询页大小默认为200<br>
	 * 所以，在基础DAO中，必须实现好selectLimitCount和selectByLimit两个方法，否则无法正常导出！
	 * @param request
	 * @param response
	 * @param service BaseServiceIface
	 * @param paramObj  ExtEntity
	 * @param title
	 * @param limit
	 */
	public static void doExport(HttpServletRequest request, HttpServletResponse response, BaseAbstractService service, ExtEntity paramObj, String title, ExtLimit limit) {
		doExport(request, response, service.getBaseDao(), paramObj, title, limit);
	}
	
	/**
	 * 导出数据<br>
	 * 该方法适用于小数据量导出，如果数据量超过2000条，不宜使用，否则会造成内存溢出
	 * @param request
	 * @param response
	 * @param dataList 导出的数据列表
	 * @param title 标题
	 * @param limit 导出的表头以及数据列信息
	 */
	@SuppressWarnings("unchecked")
	public static void doExport(HttpServletRequest request, HttpServletResponse response, List dataList, String title, ExtLimit limit) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(title);
			
			String[] exp_column_names = limit.getExp_column_names().split(",");
			String[] exp_column_indexs = limit.getExp_column_indexs().split(",");
			
			createHeader(sheet, wb.createCellStyle(), exp_column_names);
			//生成数据
			addDataToSheet(sheet, dataList, exp_column_indexs, 1);
			exportWorkbook(response, wb, limit.getExp_name());
		}
		catch (Exception e) {
			logger.error("导出数据错误！", e);
		}
	}
	
	private static HSSFRow createHeader(HSSFSheet sheet, HSSFCellStyle style, String[] exp_column_names) {
		HSSFRow row = sheet.createRow(0);
		style.setFillForegroundColor(HSSFColor.LIME.index);
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		for(int j = 0; j < exp_column_names.length; j++) {
			HSSFCell cell = row.createCell((short)j);
//			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellStyle(style);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(exp_column_names[j]);
		}
		return row;
	}
	
	private static int addDataToSheet(HSSFSheet sheet, List dataList, String[] exp_column_indexs, int startRowIndex) {
		for(int i = 0; i < dataList.size(); i++) {
			HSSFRow row = sheet.createRow(startRowIndex);
			
			Object dataObj = dataList.get(i);
			
			for(int j = 0; j < exp_column_indexs.length; j++) {
				Object cellValue = EntityReflect.getObjectProperty(dataObj, exp_column_indexs[j]);
				HSSFCell cell = row.createCell((short)j);
//				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				if(cellValue == null) {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue("");
				}
				else {
					if(cellValue instanceof java.util.Date) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(cellValue));
					}
					else {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(cellValue.toString());
					}
				}
			}
			
			startRowIndex ++;
		}
		return startRowIndex;
	}
	
	private static void exportWorkbook(HttpServletResponse response, HSSFWorkbook wb, String fileName) throws Exception {
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
        response.setContentType("application/vnd.ms-excel" + ";charset=UTF-8");
        wb.write(response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
	}
}
