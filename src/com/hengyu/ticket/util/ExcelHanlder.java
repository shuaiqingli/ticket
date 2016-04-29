package com.hengyu.ticket.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.SchemaType;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCell;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.impl.CTCellImpl;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * excel处理
 * @author LGF
 * 2015-12-21
 */
public class ExcelHanlder {
	
	//默认高度
	private static short DEFAULT_ROW_HEIGHT = 400;
	//默认宽度
	private static int DEFAULT_CELL_WIDTH = 3000;

	
	/**
	 * 
	 * @param book 工作簿对象，【可选】
	 * @param hanlder 自定义类型处理【可选】
	 * @param titles 标题
	 * @param columns 列名（Map类型处理，自定义可选）
	 * @param columnsWidth 宽度
	 * @param height 行高
	 * @param sheetTitle 表标题
	 * @param datas 数据
	 * @return
	 */
	@SuppressWarnings("all")
	public static XSSFWorkbook exportExcel(XSSFWorkbook book,ExcelTypeHanlder hanlder,String[] titles,String[] columns
			,Integer[] columnsWidth,Short height,String sheetTitle,List datas) throws Exception {
		
		if(book==null){
			book = new XSSFWorkbook();
		}
		
		int size = DEFAULT_CELL_WIDTH;
		
		//列大小
		if(columnsWidth!=null&&columnsWidth.length==1){
			size = columnsWidth[0];
		}
		if(height==null){
			height = DEFAULT_ROW_HEIGHT;
		}
		XSSFSheet sheet = book.createSheet(sheetTitle);
		int rowindex = 0;
		XSSFRow firstrow = sheet.createRow(rowindex);
		rowindex++;
		sheet.setDefaultColumnWidth(size);
		firstrow.setHeight(height);
		
		XSSFFont font = book.createFont();
		font.setBold(true);
		XSSFCellStyle cellstyle = book.createCellStyle();
		cellstyle.setFont(font);
		cellstyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		//标题
		if(titles!=null){
			int index = 0;
			for (String title : titles) {
				XSSFCell cell = firstrow.createCell(index);
				cell.setCellStyle(cellstyle);
				cell.setCellValue(title);
				//列宽度设置
				if(columnsWidth==null||columnsWidth.length==0||columnsWidth.length==1){
					sheet.setColumnWidth(cell.getColumnIndex(), size);
				}else{
					if((columnsWidth.length-1)>=index){
						sheet.setColumnWidth(cell.getColumnIndex(), columnsWidth[index]==null?size:columnsWidth[index]);
					}else{
						sheet.setColumnWidth(cell.getColumnIndex(), size);
					}
				}
				index++;
			}
		}
		if(datas==null){
			return book;
		}
		
		//写入数据
		for (Object data : datas) {
			
			//map 类型处理
			if(data instanceof Map){
				Map<String,Object> map = (Map<String, Object>) data;
				XSSFRow row = sheet.createRow(rowindex);
				rowHanlder(row,columns,hanlder,map);
				row.setHeight(DEFAULT_ROW_HEIGHT);
				rowindex++;
			}else{
				XSSFRow row = sheet.createRow(rowindex);
				row.setHeight(DEFAULT_ROW_HEIGHT);
				if(hanlder!=null){
					hanlder.typeHanlder(data, row);
				}
				objectHanlder(columns,data,row,hanlder);
				rowindex++;
				//其他处理
			}
		}
		return book;
	}

	public static void writer(XSSFWorkbook book,String name,HttpServletResponse resp, HttpServletRequest req) throws IOException {
        ServletOutputStream out = null;
        try {
            String userAgent = req.getHeader("User-Agent");
            // name.getBytes("UTF-8")处理safari的乱码问题
            byte[] bytes = userAgent.contains("MSIE") ? name.getBytes() : name.getBytes("UTF-8");
            // 各浏览器基本都支持ISO编码
            name = new String(bytes, "ISO-8859-1");

            resp.setCharacterEncoding("UTF-8");
            resp.addHeader("Content-type"," application/octet-stream");
            resp.addHeader("Content-Disposition",new StringBuffer().append("attachment;filename=").append(name).toString());
            out = resp.getOutputStream();
            book.write(out);

        }finally {
            if(book!=null){book.close();}
            if(out!=null){out.close();}
        }
    }

	public static void objectHanlder(String[] columns,Object data,XSSFRow row,ExcelTypeHanlder hanlder) throws Exception {
		BeanInfo bean = Introspector.getBeanInfo(data.getClass());
		PropertyDescriptor[] ps = bean.getPropertyDescriptors();
		Map<String,String> map = new HashMap<String,String>(ps.length);
		for (PropertyDescriptor p:ps ) {
			String name = p.getName();
			Method m = p.getReadMethod();
			if(m==null){
				continue;
			}
			Object obj = m.invoke(data);
			if(obj!=null){
				map.put(name,obj.toString());
			}
		}
		rowHanlder(row,columns,hanlder,map);
		row.setHeight(DEFAULT_ROW_HEIGHT);
	}


	public static void rowHanlder(XSSFRow row,String[] columns,ExcelTypeHanlder hanlder,Map map){
		int i = 0;
		for (String column : columns) {
			XSSFCell cell = row.createCell(i);
			Object val = map.get(column);
			if(hanlder!=null&&val==null){
				Object temp = hanlder.dataNullHander(column,map);
				cell.setCellValue(temp!=null?temp.toString():"");
                val = temp;
			}else{
				cell.setCellValue(val!=null?val.toString():"");
			}
            if(val!=null&&(val.getClass()==Integer.class)){
                cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
            }
			i++;
		}
	}
	
	
	/**
	 * 
	 * @param book 工作簿对象，【可选】
	 * @param titles 标题
	 * @param columns 列名（Map类型处理，自定义可选）
	 * @param sheetTitle 表标题
	 * @param datas 数据
	 * @return
	 */
	public static XSSFWorkbook exportExcel(XSSFWorkbook book,String[] titles,String[] columns,String sheetTitle,List datas) throws Exception {
		return exportExcel(book, null, titles, columns,null,null, sheetTitle, datas);
	}
	
	/**
	 * @param titles 标题
	 * @param columns 列名（Map类型处理，自定义可选）
	 * @param sheetTitle 表标题
	 * @param datas 数据
	 * @return
	 */
	@SuppressWarnings("all")
	public static XSSFWorkbook exportExcel(String[] titles,String[] columns,String sheetTitle,List datas,ExcelTypeHanlder hanlder) throws Exception {
		return exportExcel(null, hanlder, titles, columns,null,null, sheetTitle, datas);
	}
	
	public static XSSFWorkbook exportExcel(String[] titles,String[] columns,String sheetTitle,List datas) throws Exception {
		return exportExcel(null, null, titles, columns,null,null, sheetTitle, datas);
	}
	
	//自定义处理对象回调
	public static abstract class ExcelTypeHanlder<T>{
		//类型处理
		public void typeHanlder(T data,XSSFRow row){
			
		}
		
		//空数据处理
		public Object dataNullHander(String column,T obj){
			return null;
		}
	}
	
	
}
