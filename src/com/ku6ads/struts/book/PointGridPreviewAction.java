package com.ku6ads.struts.book;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.ku6ads.dao.entity.advflight.Book;
import com.ku6ads.dao.entity.advflight.Project;
import com.ku6ads.services.iface.advflight.BookService;
import com.ku6ads.struts.advflight.BookForm;
import com.ku6ads.util.AjaxOut;
import com.ku6ads.util.EntityReflect;
import com.opensymphony.xwork2.ActionSupport;

public class PointGridPreviewAction extends ActionSupport {
	
	private static final long serialVersionUID = -8183224450461345666L;
	private BookService bookService;
	Logger logger = Logger.getLogger(PointGridPreviewAction.class);
	
	private static JSONArray GridColumModleInfoArrCN = new JSONArray();
	private static JSONArray GridColumModleInfoArEn = new JSONArray();
	static {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("{header :'频道名称', dataIndex:'channelName', sortable:false, menuDisabled:true},");
		sb.append("{header:'广告条名称', dataIndex:'advbarName', sortable:false, menuDisabled:true},");
		sb.append("{header:'形式', dataIndex:'format', sortable:false, menuDisabled:true},");
		sb.append("{header:'素材规格', dataIndex:'materiel', sortable:false, menuDisabled:true},");
		sb.append("{header:'使用方式', dataIndex:'useTypeName', sortable:false, menuDisabled:true},");
		sb.append("{header:'日均流量', dataIndex:'allflux', sortable:false, menuDisabled:true},");
		sb.append("{header:'总流量', dataIndex:'periodSum',  sortable:false, menuDisabled:true},");
		sb.append("{header:'日均点击', dataIndex:'dayClick', sortable:false, menuDisabled:true},");
		sb.append("{header:'总点击', dataIndex:'allClick', sortable:false, menuDisabled:true},");
		sb.append("{header:'单位', dataIndex:'saleTypeName', sortable:false, menuDisabled:true},");
		sb.append("{header:'刊例单价', dataIndex:'price', sortable:false, menuDisabled:true},");
		sb.append("{header:'刊例总价', dataIndex:'priceSum', sortable:false, menuDisabled:true},");
		sb.append("{header:'折扣%', dataIndex:'discount', sortable:false, menuDisabled:true},");
		sb.append("{header:'折扣总价', dataIndex:'dispriceSum', sortable:false, menuDisabled:true},");
		sb.append("{header:'折扣单价', dataIndex:'disprice', sortable:false, menuDisabled:true},");
		sb.append("{header:'地域定向', dataIndex:'areaDirect', sortable:false, menuDisabled:true},");
		sb.append("{header:'关键词定向', dataIndex:'keyword', sortable:false, menuDisabled:true},");
		sb.append("{header:'小时定向', dataIndex:'hourDirect', sortable:false, menuDisabled:true},");
		sb.append("{header:'频次定向', dataIndex:'frequencyText', sortable:false, menuDisabled:true},");
		sb.append("{header:'刊例总价', dataIndex:'allPrice', sortable:false, menuDisabled:true},");
		sb.append("{header:'配送总价', dataIndex:'psPrice', sortable:false, menuDisabled:true},");
		sb.append("{header:'购买总价', dataIndex:'buyPrice', sortable:false, menuDisabled:true},");
		sb.append("{header:'配送比例', dataIndex:'psRate', sortable:false, menuDisabled:true},");
		sb.append("{header:'备注', dataIndex:'remark', sortable:false, menuDisabled:true}");
		sb.append("]");
		GridColumModleInfoArrCN = JSONArray.fromObject(sb.toString());
	}
	static {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("{header :'Position', dataIndex:'channelName', sortable:false, menuDisabled:true},");
		sb.append("{header:'广告条名称', dataIndex:'advbarName', sortable:false, menuDisabled:true},");
		sb.append("{header:'使用方式', dataIndex:'useTypeName', sortable:false, menuDisabled:true},");
		sb.append("{header:'Format', dataIndex:'format', sortable:false, menuDisabled:true},");
		sb.append("{header:'Banner Type', dataIndex:'materiel', sortable:false, menuDisabled:true},");
		sb.append("{header:'Daily Impression', dataIndex:'allflux', sortable:false, menuDisabled:true},");
		sb.append("{header:'Total Impression', dataIndex:'periodSum',  sortable:false, menuDisabled:true},");
		sb.append("{header:'Daily Click', dataIndex:'dayClick', sortable:false, menuDisabled:true},");
		sb.append("{header:'Total Click', dataIndex:'allClick', sortable:false, menuDisabled:true},");
		sb.append("{header:'单位', dataIndex:'saleTypeName', sortable:false, menuDisabled:true},");
		sb.append("{header:'Unit price  (RMB)', dataIndex:'price', sortable:false, menuDisabled:true},");
		sb.append("{header:'Total price   (RMB)', dataIndex:'priceSum', sortable:false, menuDisabled:true},");
		sb.append("{header:'Dis%', dataIndex:'discount', sortable:false, menuDisabled:true},");
		sb.append("{header:'Total price after discount(RMB)', dataIndex:'dispriceSum', sortable:false, menuDisabled:true},");
		sb.append("{header:'Unit price after discount(RMB)', dataIndex:'disprice', sortable:false, menuDisabled:true},");
		sb.append("{header:'Location', dataIndex:'areaDirect', sortable:false, menuDisabled:true},");
		sb.append("{header:'关键词定向', dataIndex:'keyword', sortable:false, menuDisabled:true},");
		sb.append("{header:'小时定向', dataIndex:'hourDirect', sortable:false, menuDisabled:true},");
		sb.append("{header:'频次定向', dataIndex:'frequencyText', sortable:false, menuDisabled:true},");
		sb.append("{header:'Sub Total price     (RMB)', dataIndex:'allPrice', sortable:false, menuDisabled:true},");
		sb.append("{header:'Given Budget (RMB)', dataIndex:'psPrice', sortable:false, menuDisabled:true},");
		sb.append("{header:'Total net price (RMB)', dataIndex:'buyPrice', sortable:false, menuDisabled:true},");
		sb.append("{header:'Given proportion', dataIndex:'psRate', sortable:false, menuDisabled:true},");
		sb.append("{header:'备注', dataIndex:'remark', sortable:false, menuDisabled:true}");
		sb.append("]");
		GridColumModleInfoArEn = JSONArray.fromObject(sb.toString());
	}
	
	public void getPointGrid() {
		try {
			JSONObject retObj = new JSONObject();
			retObj.put("action", true);
			retObj.put("message", "error!");
			
			List<Book> pointList = bookService.selectPreviewBookPoint(projectId);
			
			JSONArray gridColumModlePointArr = createColumModlePointArr(pointList);
			JSONArray columModleArray = new JSONArray();
			columModleArray.addAll(GridColumModleInfoArrCN);
			columModleArray.addAll(gridColumModlePointArr);
			
			retObj.put("columModle", columModleArray);
			retObj.put("data", getData(pointList));
			retObj.put("fieldsNames", getFieldsNamesArray(pointList));
			
			AjaxOut.responseText(ServletActionContext.getResponse(), retObj.toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void exportExcel() {
		try {
			List pointList = bookService.selectPreviewBookPoint(projectId);
			Workbook wb = new XSSFWorkbook();
			Sheet  sheet = wb.createSheet("点位预览");
			
		    Row row = null;
		    int startIndex = 0;
		    //1. 生成头上的图标
		    
		    String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("");
		    ByteArrayOutputStream bao = new ByteArrayOutputStream();
			BufferedImage bi = ImageIO.read(new File(path + "\\HTML\\images\\ku6.jpg"));
			ImageIO.write(bi, "jpg", bao);
			Drawing drawing = sheet.createDrawingPatriarch();
			
			CreationHelper helper = wb.getCreationHelper();
			ClientAnchor anchor = helper.createClientAnchor();
			anchor.setCol1(0);
		    anchor.setRow1(0);
		    Picture pict = drawing.createPicture(anchor, wb.addPicture(bao.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
			pict.resize();
			
			Project project = bookService.selectProjectById(projectId);
			
			row = sheet.createRow(4);
			Cell cell = row.createCell(0);
			cell.setCellValue("Client/客户:");
			cell = row.createCell(1);
			cell.setCellValue(project.getConsumerName());
			
			row = sheet.createRow(5);
			cell = row.createCell(0);
			cell.setCellValue("Product/产品:");
			cell = row.createCell(1);
			cell.setCellValue(project.getProductName());
			
			row = sheet.createRow(6);
			cell = row.createCell(0);
			cell.setCellValue("Periods/投放周期:");
			Map map = bookService.getProjectPeriodsInfo(projectId);
			Date minTime = (Date) map.get("minTime");
			Date maxTime = (Date) map.get("maxTime");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			cell = row.createCell(1);
			cell.setCellValue(df.format(minTime) + "~" + df.format(maxTime));
			
			JSONArray gridColumModlePointArr = createColumModlePointArr(pointList);
//			GridColumModleInfoArrCN;
//			GridColumModleInfoArEn;
			
			Font defaultFont = wb.createFont();
		    defaultFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		    defaultFont.setBoldweight((short) 7);
			
		    //2. 生成表头
		    sheet.createRow(startIndex++);
		    sheet.createRow(startIndex++);
		    CellStyle style = wb.createCellStyle();
		    style.setFont(defaultFont);
			style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		    style.setBorderBottom((short) 1);
		    style.setBorderLeft((short) 1);
		    style.setBorderRight((short) 1);
		    style.setBorderTop((short) 1);
		    style.setAlignment(CellStyle.ALIGN_CENTER);
		    style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		    
		    CellStyle weekendStyle = wb.createCellStyle();
		    weekendStyle.setFont(defaultFont);
		    weekendStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		    weekendStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		    weekendStyle.setBorderBottom((short) 1);
		    weekendStyle.setBorderLeft((short) 1);
		    weekendStyle.setBorderRight((short) 1);
		    weekendStyle.setBorderTop((short) 1);
		    weekendStyle.setAlignment(CellStyle.ALIGN_CENTER);
		    weekendStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		    
		    startIndex = 8;
		    row = sheet.createRow(startIndex);
		    //开头的英文标题---基本信息部分
			for(int j = 0; j < GridColumModleInfoArEn.size(); j++) {
				JSONObject aObj = GridColumModleInfoArEn.getJSONObject(j);
				CellRangeAddress cra = new CellRangeAddress(startIndex, startIndex + 1, j, j);
				sheet.addMergedRegion(cra);
				cell = row.createCell(j);
				cell.setCellStyle(style);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(aObj.getString("header"));
			}
			
			String lastMonth = "";
			int lastCellIndex = GridColumModleInfoArEn.size();
			for(int j = GridColumModleInfoArEn.size(); j < GridColumModleInfoArEn.size() + gridColumModlePointArr.size(); j++) {
				JSONObject aObj = gridColumModlePointArr.getJSONObject(j - GridColumModleInfoArEn.size());
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(aObj.getString("header"));
				String nowMonthStr = new SimpleDateFormat("yyyy年MM月").format(date);
				if(nowMonthStr.equals(lastMonth)) {
				}
				else if(!lastMonth.equals("")) {
					cell = row.createCell(lastCellIndex);
					cell.setCellStyle(style);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(lastMonth);
					CellRangeAddress cra = new CellRangeAddress(startIndex, startIndex, lastCellIndex, j - 1);
					sheet.addMergedRegion(cra);
					
					for(int k = lastCellIndex + 1; k < j; k++) {
						cell = row.createCell(k);
						cell.setCellStyle(style);
					}
					
					lastCellIndex = j;
				}
				lastMonth = nowMonthStr;
			}
			
			if(lastCellIndex < GridColumModleInfoArEn.size() + gridColumModlePointArr.size()) {
				cell = row.createCell(lastCellIndex);
				cell.setCellStyle(style);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(lastMonth);
				CellRangeAddress cra = new CellRangeAddress(startIndex, startIndex, lastCellIndex, GridColumModleInfoArEn.size() + gridColumModlePointArr.size() - 1);
				for(int k = lastCellIndex + 1; k < GridColumModleInfoArEn.size() + gridColumModlePointArr.size(); k++) {
					cell = row.createCell(k);
					cell.setCellStyle(style);
				}
				sheet.addMergedRegion(cra);
			}
			startIndex++;
			row = sheet.createRow(startIndex);
			for(int j = 0; j < GridColumModleInfoArEn.size(); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(style);
			}
			
			//开头的英文标题---点位数据部分，到天，显示星期几
			for(int j = GridColumModleInfoArEn.size(); j < GridColumModleInfoArEn.size() + gridColumModlePointArr.size(); j++) {
				JSONObject aObj = gridColumModlePointArr.getJSONObject(j - GridColumModleInfoArEn.size());
				cell = row.createCell(j);
				cell.setCellStyle(style);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(aObj.getString("header"));
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				int n = cal.get(Calendar.DAY_OF_WEEK);
				switch (n) {
				case Calendar.SUNDAY:
				case Calendar.SATURDAY:
					cell.setCellValue("S");
					cell.setCellStyle(weekendStyle);
					break;	
				case Calendar.MONDAY:
					cell.setCellValue("M");
					break;
				case Calendar.THURSDAY:
				case Calendar.TUESDAY:
					cell.setCellValue("T");
					break;
				case Calendar.WEDNESDAY:
					cell.setCellValue("W");
					break;
				case Calendar.FRIDAY:
					cell.setCellValue("F");
					break;
				default:
					cell.setCellValue("");
					break;
				}
			}
			
			startIndex++;
			
			//开头的中文标题，基本信息部分
		    row = sheet.createRow(startIndex++);
			for(int j = 0; j < GridColumModleInfoArrCN.size(); j++) {
				JSONObject aObj = GridColumModleInfoArrCN.getJSONObject(j);
				cell = row.createCell(j);
				cell.setCellStyle(style);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(aObj.getString("header"));
			}
			
			//开头的英文标题---点位数据部分，到天，显示日期
			for(int j = GridColumModleInfoArrCN.size(); j < GridColumModleInfoArrCN.size() + gridColumModlePointArr.size(); j++) {
				JSONObject aObj = gridColumModlePointArr.getJSONObject(j - GridColumModleInfoArEn.size());
				cell = row.createCell(j);
				cell.setCellStyle(style);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(aObj.getString("header"));
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				int n = cal.get(Calendar.DAY_OF_MONTH);
				cell.setCellValue(n);
				
				n = cal.get(Calendar.DAY_OF_WEEK);
				switch (n) {
				case Calendar.SUNDAY:
				case Calendar.SATURDAY:
					cell.setCellStyle(weekendStyle);
					break;	
				default:
					break;
				}
			}
			
			CellStyle dataStyle = wb.createCellStyle();
			dataStyle.setFont(defaultFont);
			dataStyle.setBorderBottom((short) 1);
			dataStyle.setBorderLeft((short) 1);
			dataStyle.setBorderRight((short) 1);
			dataStyle.setBorderTop((short) 1);
			dataStyle.setAlignment(CellStyle.ALIGN_CENTER);
			dataStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			//生成数据
			JSONArray dataArray = getData(pointList);
			
			//处理一次数据，将配送的分出
			JSONArray saleDataArray = new JSONArray();
			JSONArray givenDataArray = new JSONArray();
			for(int i = 0; i < dataArray.size(); i++) {
				JSONObject aObj = dataArray.getJSONObject(i);
				if(aObj.containsKey("useTypeName") && aObj.getString("useTypeName").equals("配送")) {
					givenDataArray.add(aObj);
				}
				else {
					saleDataArray.add(aObj);
				}
			}
			
			//添加销售数据
			addExpData(sheet, saleDataArray, gridColumModlePointArr, startIndex, dataStyle, weekendStyle);
			startIndex += saleDataArray.size();
			
			row = sheet.createRow(startIndex);
			cell = row.createCell(0);
			CellStyle tStyle = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(HSSFColor.RED.index);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			tStyle.setFont(font);
			tStyle.setBorderBottom((short) 1);
			tStyle.setBorderLeft((short) 1);
			tStyle.setBorderRight((short) 1);
			tStyle.setBorderTop((short) 1);
			tStyle.setAlignment(CellStyle.ALIGN_LEFT);
			cell.setCellStyle(tStyle);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue("以下为配送资源");
			sheet.addMergedRegion(new CellRangeAddress(startIndex,startIndex,0,GridColumModleInfoArrCN.size() + gridColumModlePointArr.size() - 1));
			
			startIndex++;
			CellStyle psStyle = wb.createCellStyle();
			psStyle.setFillForegroundColor(HSSFColor.LIME.index);
			psStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			psStyle.setBorderBottom((short) 1);
			psStyle.setBorderLeft((short) 1);
			psStyle.setBorderRight((short) 1);
			psStyle.setBorderTop((short) 1);
			psStyle.setAlignment(CellStyle.ALIGN_CENTER);
			psStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			
			//添加配送数据
			addExpData(sheet, givenDataArray, gridColumModlePointArr, startIndex, psStyle, weekendStyle);
			startIndex += givenDataArray.size();
			startIndex++;
			startIndex++;
			
			tStyle = wb.createCellStyle();
			font = wb.createFont();
			font.setColor(HSSFColor.BLACK.index);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			tStyle.setFont(font);
			tStyle.setAlignment(CellStyle.ALIGN_LEFT);
			
			row = sheet.createRow(startIndex++);
			cell = row.createCell(0);
			cell.setCellStyle(tStyle);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue("甲方：");
			
			startIndex++;
			row = sheet.createRow(startIndex++);
			cell = row.createCell(0);
			cell.setCellStyle(tStyle);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue("执行人：");
			
			startIndex++;
			row = sheet.createRow(startIndex++);
			cell = row.createCell(0);
			cell.setCellStyle(tStyle);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue("日期：");
			
			for(int j = GridColumModleInfoArEn.size(); j < GridColumModleInfoArEn.size() + gridColumModlePointArr.size(); j++) {
				sheet.autoSizeColumn((short)j); 
			}
			
			String fileName = project.getName() + "_" + df.format(new Date());
			ServletActionContext.getResponse().setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1") + ".xlsx");
			ServletActionContext.getResponse().setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	        ServletActionContext.getResponse().setHeader("Pragma", "public");
	        ServletActionContext.getResponse().setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	        ServletActionContext.getResponse().setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" + ";charset=gbk");
	        wb.write(ServletActionContext.getResponse().getOutputStream());
	        ServletActionContext.getResponse().getOutputStream().flush();
	        ServletActionContext.getResponse().getOutputStream().close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void addExpData(Sheet sheet, JSONArray data, JSONArray gridColumModlePointArr, int startIndex, CellStyle dataStyle,CellStyle weekendStyle) throws Exception {
		Cell cell = null;
		Row row = null;
		for(int i = 0; i < data.size(); i++) {
			row = sheet.createRow(startIndex++);				
			JSONObject aObj = data.getJSONObject(i);
			
			for(int j = 0; j < GridColumModleInfoArrCN.size(); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(dataStyle);
				
				JSONObject headerObj = GridColumModleInfoArrCN.getJSONObject(j);
				String key = headerObj.getString("dataIndex");
				Object value = null;
				if(aObj.containsKey(key)) {
					value = aObj.get(key);
				}
				
				if(value instanceof Double) {
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cell.setCellValue((Double) value);
				}
				else if(value != null){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(value.toString());
				}
			}
			
			//点位数据
			for(int j = GridColumModleInfoArrCN.size(); j < GridColumModleInfoArrCN.size() + gridColumModlePointArr.size(); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(dataStyle);
				
				JSONObject headerObj = gridColumModlePointArr.getJSONObject(j - GridColumModleInfoArrCN.size());
				String key = headerObj.getString("dataIndex");
				Object value = null;
				if(aObj.containsKey(key)) {
					value = aObj.get(key);
				}
				if(value instanceof Double) {
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cell.setCellValue((Double) value);
				}
				else if(value != null){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(value.toString());
				}
				
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(key);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				
				int n = cal.get(Calendar.DAY_OF_WEEK);
				switch (n) {
				case Calendar.SUNDAY:
				case Calendar.SATURDAY:
					cell.setCellStyle(weekendStyle);
					break;	
				default:
					break;
				}
			}
		}
	}
	
	private JSONArray createColumModlePointArr(List pointList) {
		JSONArray gridColumModlePointArr = new JSONArray();
		Map<String, String> map = new HashMap<String, String>();
		if(pointList != null && pointList.size() > 0) {
			Book book;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			for(int i = 0; i < pointList.size(); i++) {
				book = (Book) pointList.get(i);
				JSONObject jobj = new JSONObject();
				jobj.put("header", df.format(book.getStartTime()));
				jobj.put("dataIndex", df.format(book.getStartTime()));
				jobj.put("sortable", false);
				
				if(map.containsKey(df.format(book.getStartTime())))
					continue;
				map.put(df.format(book.getStartTime()), df.format(book.getStartTime()));
				gridColumModlePointArr.add(jobj);
			}
		}
		return gridColumModlePointArr;
	}
	
	private JSONArray getData(List<Book> pointList) throws Exception {
		JSONArray dataArray = new JSONArray();
		
		Book book = null;
		//1. 
//		整个执行单的合计项：
//		刊例总价，CPM(单价*CPM总数), CPD(单价*总天数)
//		配送总价
//		购买总价
//		配送比例
		Map retMap = bookService.getProjectAllPrice(projectId);
		Double allPrice = ((Double) retMap.get("allPrice") == null ? 0 : (Double) retMap.get("allPrice"));//刊例总价
		Double buyPrice = ((Double) retMap.get("buyPrice") == null ? 0 : (Double) retMap.get("buyPrice"));//购买总价
		Double psPrice = ((Double)bookService.getPSPrice(projectId) == null ? 0 : bookService.getPSPrice(projectId));//配送总价
		Double psRate = 0.0;
		if(buyPrice != 0)
			psRate = psPrice / buyPrice;//配送比例
		
		book = new Book();
		book.setProjectId(projectId);
		book.setStatus(0);
//		int count = bookService.selectPreviewBookLimitCount(projectId);
		List<BookForm> bookList = bookService.selectPreviewBookLimit(book);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i = 0; i < bookList.size(); i++) {
			BookForm bookEty = bookList.get(i);
			JSONObject obj = JSONObject.fromObject(EntityReflect.getObjectJSonString(bookEty, df));
			
			if(pointList != null && pointList.size() > 0) {
				Book bookPointEty;
				for(int j = 0; j < pointList.size(); j++) {
					bookPointEty = (Book) pointList.get(j);
					if(bookPointEty.getBookPackageId().intValue() == bookEty.getBookPackageId().intValue()) {
						obj.put(df.format(bookPointEty.getStartTime()), bookPointEty.getFlightNum());
					}
				}
			}
			
			//地域定向格式特殊处理
			String areaDirect = bookEty.getAreaDirect();
			JSONArray array = JSONArray.fromObject(areaDirect);
			areaDirect = "";
			for(int k = 0; k < array.size(); k++) {
				JSONObject jobj = array.getJSONObject(k);
				areaDirect += jobj.get("display") + "; ";
			}
			obj.put("areaDirect", areaDirect);
			
			obj.put("allPrice", allPrice);//刊例总价
			obj.put("buyPrice", buyPrice);//购买总价
			obj.put("psPrice", psPrice);//配送总价
			obj.put("psRate", psRate);//配送比例
			
			dataArray.add(obj);
		}
		
		return dataArray;
	}
	
	private JSONArray getFieldsNamesArray(List pointList) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("{name:'channelName'},");
		sb.append("{name:'advbarName'},");
		sb.append("{name:'format'},");
		sb.append("{name:'materiel'},");
		sb.append("{name:'useTypeName'},");
		sb.append("{name:'allflux'},");
		sb.append("{name:'sumPer'},");
		sb.append("{name:'dayClick'},");
		sb.append("{name:'allClick'},");
		sb.append("{name:'period'},");
		sb.append("{name:'periodSum'},");
		sb.append("{name:'saleType'},");
		sb.append("{name:'saleTypeName'},");
		sb.append("{name:'price'},");
		sb.append("{name:'priceSum'},");
		sb.append("{name:'discount'},");
		sb.append("{name:'disprice'},");
		sb.append("{name:'dispriceSum'},");
		sb.append("{name:'areaDirect'},");
		sb.append("{name:'keyword'},");
		sb.append("{name:'hourDirect'},");
		sb.append("{name:'allPrice'},");
		sb.append("{name:'buyPrice'},");
		sb.append("{name:'psPrice'},");
		sb.append("{name:'psRate'},");
		sb.append("{name:'frequencyText'},");
		sb.append("{name:'remark'},");
		sb.append("{name:'isFrequency'}");
		
		if(pointList != null && pointList.size() > 0) {
			sb.append(",");
			Book book;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			for(int i = 0; i < pointList.size(); i++) {
				book = (Book) pointList.get(i);
				if(sb.toString().contains("{name:'" + df.format(book.getStartTime()) + "'}"))
					continue;
				
				sb.append("{name:'" + df.format(book.getStartTime()) + "'}");
				if(i < pointList.size() - 1)
					sb.append(",");
			}
		}
		
		sb.append("]");
		JSONArray fieldsNamesArray = JSONArray.fromObject(sb.toString());
		return fieldsNamesArray;
	}
	
	private Integer projectId;
	
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public BookService getBookService() {
		return bookService;
	}
	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}
	
	
}
