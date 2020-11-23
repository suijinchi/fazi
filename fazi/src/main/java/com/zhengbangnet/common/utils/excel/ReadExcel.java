package com.zhengbangnet.common.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
  
/** 
 * <pre> 
 *   Title: ReadExcel.java 
 *   Description:  
 *   Copyright: Maple Copyright (c) 2013 
 *   Company:  
 * </pre> 
 *  
 * @author duanke 
 * @version 1.0 
 * @date 2013年12月31日 
 */  
public class ReadExcel {  
	//%%%%%%%%-------常量部分 开始----------%%%%%%%%%    
	  /**  
	   * 默认的开始读取的行位置为第一行（索引值为0）  
	   */    
	  private final static int READ_START_POS = 0;    
	      
	  /**  
	   * 默认结束读取的行位置为最后一行（索引值=0，用负数来表示倒数第n行）  
	   */    
	  private final static int READ_END_POS = 0;    
	      
	  /**  
	   * 默认Excel内容的开始比较列位置为第一列（索引值为0）  
	   */    
	  private final static int COMPARE_POS = 0;    
	      
	  /**  
	   * 默认多文件合并的时需要做内容比较（相同的内容不重复出现）  
	   */    
	  private final static boolean NEED_COMPARE = true;    
	      
	  /**  
	   * 默认多文件合并的新文件遇到名称重复时，进行覆盖  
	   */    
	  private final static boolean NEED_OVERWRITE = true;    
	      
	  /**  
	   * 默认只操作一个sheet  
	   */    
	  private final static boolean ONLY_ONE_SHEET = true;    
	      
	  /**  
	   * 默认读取第一个sheet中（只有当ONLY_ONE_SHEET = true时有效）  
	   */    
	  private final static int SELECTED_SHEET = 0;    
	      
	  /**  
	   * 默认从第一个sheet开始读取（索引值为0）  
	   */    
	  private final static int READ_START_SHEET= 0;    
	      
	  /**  
	   * 默认在最后一个sheet结束读取（索引值=0，用负数来表示倒数第n行）  
	   */    
	  private final static int READ_END_SHEET = 0;    
	      
	  /**  
	   * 默认打印各种信息  
	   */    
	  private final static boolean PRINT_MSG = true;    
	      
	  //%%%%%%%%-------常量部分 结束----------%%%%%%%%%    
	      
	  
	  //%%%%%%%%-------字段部分 开始----------%%%%%%%%%    
	  /**  
	   * Excel文件路径  
	   */    
	  private String excelPath = "data.xlsx";    
	  
	  /**  
	   * 设定开始读取的位置，默认为0  
	   */    
	  private int startReadPos = READ_START_POS;    
	  
	  /**  
	   * 设定结束读取的位置，默认为0，用负数来表示倒数第n行  
	   */    
	  private int endReadPos = READ_END_POS;    
	      
	  /**  
	   * 设定开始比较的列位置，默认为0  
	   */    
	  private int comparePos = COMPARE_POS;    
	  
	  /**  
	   *  设定汇总的文件是否需要替换，默认为true  
	   */    
	  private boolean isOverWrite = NEED_OVERWRITE;    
	      
	  /**  
	   *  设定是否需要比较，默认为true(仅当不覆写目标内容是有效，即isOverWrite=false时有效)  
	   */    
	  private boolean isNeedCompare = NEED_COMPARE;    
	      
	  /**  
	   * 设定是否只操作第一个sheet  
	   */    
	  private boolean onlyReadOneSheet = ONLY_ONE_SHEET;    
	      
	  /**  
	   * 设定操作的sheet在索引值  
	   */    
	  private int selectedSheetIdx =SELECTED_SHEET;    
	      
	  /**  
	   * 设定操作的sheet的名称  
	   */    
	  private String selectedSheetName = "";    
	      
	  /**  
	   * 设定开始读取的sheet，默认为0  
	   */    
	  private int startSheetIdx = READ_START_SHEET;    
	  
	  /**  
	   * 设定结束读取的sheet，默认为0，用负数来表示倒数第n行      
	   */    
	  private int endSheetIdx = READ_END_SHEET;    
	      
	  /**  
	   * 设定是否打印消息  
	   */    
	  private boolean printMsg = PRINT_MSG;    
	      
	      
	  //%%%%%%%%-------字段部分 结束----------%%%%%%%%%    
	      
	  public ReadExcel(){}    
	    
	      
	  public ReadExcel(String excelPath){    
	      this.excelPath = excelPath;    
	  }    
	      
	  /**  
	   * 还原设定（其实是重新new一个新的对象并返回）  
	   * @return  
	   */    
	  public ReadExcel RestoreSettings(){    
		  ReadExcel instance = new  ReadExcel(this.excelPath);    
	      return instance;    
	  }    
	      
	  /**  
	   * 自动根据文件扩展名，调用对应的读取方法  
	   *   
	   * @Title: writeExcel  
	   * @Date : 2016-7-27 下午01:50:38  
	   * @param xlsPath  
	   * @throws IOException  
	   */    
	  public List<Row> readExcel() throws IOException{    
	      return readExcel(this.excelPath);    
	  }    
	  
	  /**  
	   * 自动根据文件扩展名，调用对应的读取方法  
	   *   
	   * @Title: writeExcel  
	   * @Date : 2016-7-27 下午01:50:38  
	   * @param xlsPath  
	   * @throws IOException  
	   */    
	  public List<Row> readExcel(String xlsPath) throws IOException{    
	          
	      //扩展名为空时，    
	      if (xlsPath.equals("")){    
	          throw new IOException("文件路径不能为空！");    
	      }else{    
	          File file = new File(xlsPath);    
	          if(!file.exists()){    
	              throw new IOException("文件不存在！");    
	          }    
	      }    
	          
	      //获取扩展名    
	      String ext = xlsPath.substring(xlsPath.lastIndexOf(".")+1);    
	          
	      try {    
	              
	          if("xls".equals(ext)){              //使用xls方式读取    
	              return readExcel_xls(xlsPath);    
	          }else if("xlsx".equals(ext)){       //使用xlsx方式读取    
	              return readExcel_xlsx(xlsPath);    
	          }else{                                  //依次尝试xls、xlsx方式读取    
	              out("您要操作的文件没有扩展名，正在尝试以xls方式读取...");    
	              try{    
	                  return readExcel_xls(xlsPath);    
	              } catch (IOException e1) {    
	                  out("尝试以xls方式读取，结果失败！，正在尝试以xlsx方式读取...");    
	                  try{    
	                      return readExcel_xlsx(xlsPath);    
	                  } catch (IOException e2) {    
	                      out("尝试以xls方式读取，结果失败！\n请您确保您的文件是Excel文件，并且无损，然后再试。");    
	                      throw e2;    
	                  }    
	              }    
	          }    
	      } catch (IOException e) {    
	          throw e;    
	      }    
	  }    
	      
	
	  
	  /**  
	   * //读取Excel 2007版，xlsx格式  
	   *   
	   * @Title: readExcel_xlsx  
	   * @Date : 2016-7-27 上午11:43:11  
	   * @return  
	   * @throws IOException  
	   */    
	  public List<Row> readExcel_xlsx() throws IOException {    
	      return readExcel_xlsx(excelPath);    
	  }    
	  
	  /**  
	   * //读取Excel 2007版，xlsx格式  
	   *   
	   * @Title: readExcel_xlsx  
	   * @Date : 2016-7-27 上午11:43:11  
	   * @return  
	   * @throws Exception  
	   */    
	  public List<Row> readExcel_xlsx(String xlsPath) throws IOException {    
	      // 判断文件是否存在    
	      File file = new File(xlsPath);    
	      if (!file.exists()) {    
	          throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");    
	      }    
	  
	      XSSFWorkbook wb = null;    
	      List<Row> rowList = new ArrayList<Row>();    
	      try {    
	          FileInputStream fis = new FileInputStream(file);    
	          // 去读Excel    
	          wb = new XSSFWorkbook(fis);    
	  
	          // 读取Excel 2007版，xlsx格式    
	          rowList = readExcel(wb);    
	  
	      } catch (IOException e) {    
	          e.printStackTrace();    
	      }    
	      return rowList;    
	  }    
	  
	  /***  
	   * 读取Excel(97-03版，xls格式)  
	   *   
	   * @throws IOException  
	   *   
	   * @Title: readExcel  
	   * @Date : 2016-7-27 上午09:53:21  
	   */    
	  public List<Row> readExcel_xls() throws IOException {    
	      return readExcel_xls(excelPath);    
	  }    
	  
	  /***  
	   * 读取Excel(97-03版，xls格式)  
	   *   
	   * @throws Exception  
	   *   
	   * @Title: readExcel  
	   * @Date : 2016-7-27 上午09:53:21  
	   */    
	  public List<Row> readExcel_xls(String xlsPath) throws IOException {    
	  
	      // 判断文件是否存在    
	      File file = new File(xlsPath);    
	      if (!file.exists()) {    
	          throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");    
	      }    
	  
	      HSSFWorkbook wb = null;// 用于Workbook级的操作，创建、删除Excel    
	      List<Row> rowList = new ArrayList<Row>();    
	  
	      try {    
	          // 读取Excel    
	          wb = new HSSFWorkbook(new FileInputStream(file));    
	  
	          // 读取Excel 97-03版，xls格式    
	          rowList = readExcel(wb);    
	  
	      } catch (IOException e) {    
	          e.printStackTrace();    
	      }    
	      return rowList;    
	  }    
	  
	  /***  
	   * 读取单元格的值  
	   *   
	   * @Title: getCellValue  
	   * @Date : 2016-7-27 上午10:52:07  
	   * @param cell  
	   * @return  
	   */    
	  public static String getCellValue(Cell cell) {    
	      Object result = "";    
	      if (cell != null) {    
	          switch (cell.getCellType()) {    
	          case Cell.CELL_TYPE_STRING:    
	              result = cell.getStringCellValue();    
	              break;    
	          case Cell.CELL_TYPE_NUMERIC:    
	              result = cell.getNumericCellValue();    
	              break;    
	          case Cell.CELL_TYPE_BOOLEAN:    
	              result = cell.getBooleanCellValue();    
	              break;    
	          case Cell.CELL_TYPE_FORMULA:    
	              result = cell.getCellFormula();    
	              break;    
	          case Cell.CELL_TYPE_ERROR:    
	              result = cell.getErrorCellValue();    
	              break;    
	          case Cell.CELL_TYPE_BLANK:    
	              break;    
	          default:
	        	  result = cell.getStringCellValue();
	              break;    
	          }    
	      }    
	      return result.toString();    
	  }    
	  
	  /**  
	   * 通用读取Excel  
	   *   
	   * @Title: readExcel  
	   * @Date : 2016-7-27 上午11:26:53  
	   * @param wb  
	   * @return  
	   */    
	  private List<Row> readExcel(Workbook wb) {    
	      List<Row> rowList = new ArrayList<Row>();    
	          
	      int sheetCount = 1;//需要操作的sheet数量    
	          
	      Sheet sheet = null;    
	      if(onlyReadOneSheet){   //只操作一个sheet    
	          // 获取设定操作的sheet(如果设定了名称，按名称查，否则按索引值查)    
	          sheet =selectedSheetName.equals("")? wb.getSheetAt(selectedSheetIdx):wb.getSheet(selectedSheetName);    
	      }else{                          //操作多个sheet    
	          sheetCount = wb.getNumberOfSheets();//获取可以操作的总数量    
	      }    
	          
	      // 获取sheet数目    
	      for(int t=startSheetIdx; t<sheetCount+endSheetIdx;t++){    
	          // 获取设定操作的sheet    
	          if(!onlyReadOneSheet) {    
	              sheet =wb.getSheetAt(t);    
	          }    
	              
	          //获取最后行号    
	          int lastRowNum = sheet.getLastRowNum();    
	  
	          if(lastRowNum>0){    //如果>0，表示有数据    
	              out("\n开始读取名为【"+sheet.getSheetName()+"】的内容：");    
	          }    
	              
	          Row row = null;    
	          // 循环读取    
	          for (int i = startReadPos; i <= lastRowNum + endReadPos; i++) {  
	              row = sheet.getRow(i);    
	              if (row != null) {    
	                  rowList.add(row);    
	                  /*out("第"+(i+1)+"行：",false);    
	                   // 获取每一单元格的值    
	                  for (int j = 0; j < row.getLastCellNum(); j++) {   
	                       String value = getCellValue(row.getCell(j));
	                	   double cellValue = row.getCell(j).getNumericCellValue();   
	                	   //String value = new DecimalFormat("#").format(cellValue);   
	                       if (!value.equals("")) {    
	                           out(value + " | ",false);    
	                       } 
	                  }    
	                  out("");  */  
	              }    
	          }    
	      }    
	      return rowList;    
	  }    
	  
    
	  
	  /**  
	   * 查找某行数据是否在Excel表中存在，返回行数。  
	   *   
	   * @Title: findInExcel  
	   * @Date : 2016-7-27 下午02:23:12  
	   * @param sheet  
	   * @param row  
	   * @return  
	   */    
	private int findInExcel(Sheet sheet, Row row) {    
	      int pos = -1;    
	  
	      try {    
	          // 如果覆写目标文件，或者不需要比较，则直接返回    
	          if (isOverWrite || !isNeedCompare) {    
	              return pos;    
	          }    
	          for (int i = startReadPos; i <= sheet.getLastRowNum() + endReadPos; i++) {    
	              Row r = sheet.getRow(i);    
	              if (r != null && row != null) {    
	                  String v1 = getCellValue(r.getCell(comparePos));    
	                  String v2 = getCellValue(row.getCell(comparePos));    
	                  if (v1.equals(v2)) {    
	                      pos = i;    
	                      break;    
	                  }    
	              }    
	          }    
	      } catch (Exception e) {    
	          e.printStackTrace();    
	      }    
	      return pos;    
	  }    
	  
	
	  /**  
	   * 打印消息，  
	   * @param msg 消息内容  
	   * @param tr 换行  
	   */    
	  private void out(String msg){    
	      if(printMsg){    
	          out(msg,true);    
	      }    
	  }    
	  /**  
	   * 打印消息，  
	   * @param msg 消息内容  
	   * @param tr 换行  
	   */    
	  private void out(String msg,boolean tr){    
	      if(printMsg){    
	          System.out.print(msg+(tr?"\n":""));    
	      }    
	  }    
	  
	  public String getExcelPath() {    
	      return this.excelPath;    
	  }    
	  
	  public void setExcelPath(String excelPath) {    
	      this.excelPath = excelPath;    
	  }    
	  
	  public boolean isNeedCompare() {    
	      return isNeedCompare;    
	  }    
	  
	  public void setNeedCompare(boolean isNeedCompare) {    
	      this.isNeedCompare = isNeedCompare;    
	  }    
	  
	  public int getComparePos() {    
	      return comparePos;    
	  }    
	  
	  public void setComparePos(int comparePos) {    
	      this.comparePos = comparePos;    
	  }    
	  
	  public int getStartReadPos() {    
	      return startReadPos;    
	  }    
	  
	  public void setStartReadPos(int startReadPos) {    
	      this.startReadPos = startReadPos;    
	  }    
	  
	  public int getEndReadPos() {    
	      return endReadPos;    
	  }    
	  
	  public void setEndReadPos(int endReadPos) {    
	      this.endReadPos = endReadPos;    
	  }    
	  
	  public boolean isOverWrite() {    
	      return isOverWrite;    
	  }    
	  
	  public void setOverWrite(boolean isOverWrite) {    
	      this.isOverWrite = isOverWrite;    
	  }    
	  
	  public boolean isOnlyReadOneSheet() {    
	      return onlyReadOneSheet;    
	  }    
	  
	  public void setOnlyReadOneSheet(boolean onlyReadOneSheet) {    
	      this.onlyReadOneSheet = onlyReadOneSheet;    
	  }    
	  
	  public int getSelectedSheetIdx() {    
	      return selectedSheetIdx;    
	  }    
	  
	  public void setSelectedSheetIdx(int selectedSheetIdx) {    
	      this.selectedSheetIdx = selectedSheetIdx;    
	  }    
	  
	  public String getSelectedSheetName() {    
	      return selectedSheetName;    
	  }    
	  
	  public void setSelectedSheetName(String selectedSheetName) {    
	      this.selectedSheetName = selectedSheetName;    
	  }    
	  
	  public int getStartSheetIdx() {    
	      return startSheetIdx;    
	  }    
	  
	  public void setStartSheetIdx(int startSheetIdx) {    
	      this.startSheetIdx = startSheetIdx;    
	  }    
	  
	  public int getEndSheetIdx() {    
	      return endSheetIdx;    
	  }    
	  
	  public void setEndSheetIdx(int endSheetIdx) {    
	      this.endSheetIdx = endSheetIdx;    
	  }    
	  
	  public boolean isPrintMsg() {    
	      return printMsg;    
	  }    
	  
	  public void setPrintMsg(boolean printMsg) {    
	      this.printMsg = printMsg;    
	  }    
	    
	  private static List<Map<String,Object>> getMobilePrice(List<Row> rowList){
		  List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		  DecimalFormat decimalFormat = new DecimalFormat("#");
		  for(Row row : rowList){
			  Map<String,Object> map = new HashMap<String,Object>();
			  double phoneTemp = row.getCell(0).getNumericCellValue();
			  String phone = decimalFormat.format(phoneTemp);
			  Double amount = row.getCell(1).getNumericCellValue();
			  map.put("mobile", phone);
			  map.put("amount", amount);
			  list.add(map);
			//System.out.println(phone+"-"+amount);
		  }
		  return list;
	  }
	    
	  public static void main(String[] args) {    
		  ReadExcel eu = new ReadExcel();    
	          
	      //从第一行开始读取    
	      eu.setStartReadPos(eu.startReadPos);    
	          
	      String src_xlspath = "C:\\Users\\Administrator\\Desktop\\1.xls";    
	     //String dist_xlsPath = "D:\\2.xls";    
	      List<Row> rowList = null;    
	      try {    
	          rowList = eu.readExcel(src_xlspath);    
	        //eu.writeExcel_xls(rowList, src_xlspath, dist_xlsPath);    
	      } catch (IOException e) {    
	          e.printStackTrace();    
	      }
	      List<Map<String, Object>> mobilePrice = ReadExcel.getMobilePrice(rowList);
	    /*  for(Row row : rowList){
	    	  System.out.println(row.getCell(0)+"-"+row.getCell(1));
	      }*/
	      for(Map<String, Object> row : mobilePrice){
	    	  String mobile = (String) row.get("mobile");
	    	  Double amount = (Double) row.get("amount");
	    	  System.out.println(mobile+"-"+amount);
	      }
	      
	  }
	       
}  
