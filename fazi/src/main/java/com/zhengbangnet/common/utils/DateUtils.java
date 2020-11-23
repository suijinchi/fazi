package com.zhengbangnet.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Admin
 *
 */
/**
 * @author Admin
 *
 */
public class DateUtils {

	/**
	 * yyyy-MM-dd
	 */
	public static final String patternA = "yyyy-MM-dd";

	/**
	 * yyyyMMdd
	 */
	public static final String patternB = "yyyyMMdd";
	/**
	 * yyyy-MM-dd HH-mm-ss
	 */
	public static final String patternC = "yyyy-MM-dd HH-mm-ss";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String patternD = "yyyy-MM-dd HH:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm
	 */
	public static final String patternE = "yyyy-MM-dd HH:mm";

	/**
	 * yyyyMMddHHmmss
	 */
	public static final String patternF = "yyyyMMddHHmmss";

	public static final String patternG = "yyyy年MM月dd日";

	/**
	 * @param yyyy-MM
	 * @return
	 */
	public static final String patternH = "yyyy-MM-dd";

	public static final String patternI = "yyyyMMdd";
	
    /**
     * 获取某月的最后一天
     * @Title:getLastDayOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     * @throws
     */
    public static Date getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
       
        return cal.getTime();
    }

	/**
	 * 格式化日期为yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formateDate(Date date) {
		return dateToString(date, patternA);
	}

	/**
	 * 格式化日期为yyyy-MM
	 * 
	 * @param date
	 * @return
	 */
	public static String formateYearAndMonth(Date date) {
		return dateToString(date, patternH);
	}

	/**
	 * @param 取当天日期
	 * @return
	 */
	public static Date getDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * @param 取指定年月日的日期,格式为yyyy-MM-dd,HH-mm-ss
	 *            00-00-00
	 * @return
	 */
	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, 0, 0, 0);
		return cal.getTime();

	}

	/**
	 * @param 取指定年,月,日,小时,分,秒的时间
	 * @return
	 */
	public static Date getDate(int year, int month, int date, int hour, int mintue, int second) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, mintue);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * @param days=n
	 *            n为-,则取n天前,n为+,则取n天后的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getSomeDaysBeforeAfter(Date date, int days) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(5, days);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		return gc.getTime();
	}
	
	public static Date addHours(Date date,int hour){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(GregorianCalendar.HOUR_OF_DAY, hour);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		return gc.getTime();
	}
	
	public static Date addMinute(Date date,int minute){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(GregorianCalendar.MINUTE, minute);
		gc.set(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
		return gc.getTime();
	}

	public static Date addMonth(Date date,int month){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH,month);
		return c.getTime();
	}

	/**
	 * @param 取指定日期年份
	 * @return
	 */
	public static Integer getDateYear(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * @param 取指定日期月份
	 * @return
	 */
	public static Integer getDateMonth(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * @param 取指定日期日份
	 * @return
	 */
	public static int getDateDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DATE);
	}

	/**
	 * @param 取指定日期小时
	 * @return
	 */
	public static int getDateHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * @param 取指定日期分钟
	 * @return
	 */
	public static int getDateMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	public static int getMonthDays(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @param 取指定日期的第二天的开始时间,小时,分,秒为00:00:00
	 * @return
	 */
	public static Date getNextDayStartTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return getNextDayStart(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
	}

	/**
	 * @param 取指定年,月,日的下一日的开始时间,小时,分,秒为00:00:00
	 * @param 主要是用来取跨月份的日期
	 * @return
	 */
	public static Date getNextDayStart(int year, int monthIn, int date) {
		int month = monthIn - 1;
		boolean lastDayOfMonth = false;
		boolean lastDayOfYear = false;

		Calendar time = Calendar.getInstance();
		time.set(year, month, date, 0, 0, 0);
		Calendar nextMonthFirstDay = Calendar.getInstance();
		nextMonthFirstDay.set(year, month + 1, 1, 0, 0, 0);

		if (time.get(Calendar.DAY_OF_YEAR) + 1 == nextMonthFirstDay.get(Calendar.DAY_OF_YEAR))
			lastDayOfMonth = true;

		if (time.get(Calendar.DAY_OF_YEAR) == time.getMaximum(Calendar.DATE))
			lastDayOfYear = true;

		time.roll(Calendar.DATE, 1);

		if (lastDayOfMonth)
			time.roll(Calendar.MONTH, 1);

		if (lastDayOfYear)
			time.roll(Calendar.YEAR, 1);

		return time.getTime();
	}

	/**
	 * @param 取指定日期的下一日的时间
	 * @return
	 */
	public static Date nextDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * @param 指定日期的下一日的开始时间,小时,分,秒为00:00:00
	 * @return
	 */
	public static Date getStartDateNext(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * @param 指定日期的开始时间,小时,分,秒为00:00:00
	 * @return
	 */
	public static Date getStartDateDay(Date date) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * @param 指定日期的结束时间,小时,分,秒为23:59:59
	 * @return
	 */
	public static Date getEndDateDay(Date date) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * @param 将指定日期,以指定pattern格式,输出String值
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return "";
		} else {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(date);
		}
	}

	public static String dateToString1(Date date, String formatIn) {
		String format = formatIn;
		if (date == null) {
			return "";
		}
		if (format == null) {
			format = patternD;
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * @param 将指定年,月,日的日期转为字符型,格式为yyyy-MM-dd
	 * @return
	 */
	public static String dateToString(int year, int month, int day, String pattern) {
		return dateToString(getDate(year, month, day), pattern);
	}

	/**
	 * @param 将指定字符型日期转为日期型,,格式为指定的pattern
	 * @return
	 */
	public static Date stringToDate(String string, String pattern) {
		SimpleDateFormat format = (SimpleDateFormat) DateFormat.getDateInstance();
		format.applyPattern(pattern);
		try {
			Date parse = format.parse(string);
			return parse;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param 将指定字符型日期转为日期型,指定格式为yyyy-MM-dd
	 * @return
	 */
	public static Date stringToDate(String string) {
		return stringToDate(string, patternA);
	}

	/**
	 * 获得两个日期之间间隔的天数
	 * 23:59:59.999至次日00:00:00.999也算一天
	 * 22:59:59.999至次日22:59:59.998也算一天
	 * @param startDate
	 *            起始年月日
	 * @param endDate
	 *            结束年月日
	 * @return int
	 */
	public static int getDays(Date startDate, Date endDate) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		startDate = calendar.getTime();
		
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		endDate = calendar.getTime();
		
		int elapsed = 0;
		long daterange = endDate.getTime() - startDate.getTime();
		long time = 1000 * 3600 * 24; // 一天的毫秒数
		elapsed = (int) (daterange / time);
		return elapsed;
	}
	
	/**
	 * 获得两个日期之间间隔的分钟
	 * @param startDate 起始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static int getMinutes(Date startDate, Date endDate) {
		int elapsed = 0;
		long daterange = endDate.getTime() - startDate.getTime();
		long time = 1000 * 60; // 一天的毫秒数
		elapsed = (int) (daterange / time);
		return elapsed;
	}
	
	/**
	 * @param date
	 * @param startTime
	 *            格式为0800，表示上午8点00分
	 * @param endTime格式为2200
	 * @return
	 */
	public static boolean isWorkHour(Date date, String startTimeIn, String endTimeIn) {// 是否是工作时间
		String startTime = startTimeIn;
		String endTime = endTimeIn;
		if (StringUtils.isEmpty(startTime))
			startTime = "0800";
		if (StringUtils.isEmpty(endTime))
			endTime = "2200";
		int start = Integer.parseInt(startTime);
		int end = Integer.parseInt(endTime);
		int hour = getDateHour(date);
		int m = getDateMinute(date);
		String hstr = hour <= 9 ? "0" + hour : hour + "";
		String mstr = m <= 9 ? "0" + m : m + "";
		int dateInt = Integer.parseInt(hstr + mstr);
		if (dateInt >= start && dateInt <= end) {
			return true;
		}
		return false;
	}

	/**
	 * @param 根据传入日期，返回此月有多少天
	 * @param date
	 *            格式为 201408
	 * @return
	 */
	public static int getDayOfMonth(String date) {
		int year = Integer.parseInt(date.substring(0, 3));
		int month = Integer.parseInt(date.substring(date.length() - 1, date.length()));

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);// Java月份才0开始算 6代表上一个月7月
		int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
		return dateOfMonth;
	}

	/**
	 * @param 取指定日期月份前一月
	 * @return
	 */
	public static int getLastDateMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}

	/**
	 * 取日期的本月第一天 0时0分0秒
	 */
	public static Date getMonthFirstDay(Date date) {
		return getDate(getDateYear(date), getDateMonth(date), 1);
	}

	/**
	 * 获取日期本月的最后一天 23时59分59秒
	 */
	public static Date getMonthEndDay(Date date) {
		return getDate(getDateYear(date), getDateMonth(date), getMonthDays(date), 23, 59, 59);
	}

	// 前月第一天
	public static Date getLastDateMonthDay(Date date) {
		return getDate(getDateYear(date), getLastDateMonth(date), 1);
	}

	/**
	 * 获取上一个月的起始时间 00:00:00
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastMonthFirstDate(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.add(Calendar.MONTH, -1);
		return getMonthFirstDay(calendar.getTime());
	}

	/**
	 * 获取上一个月的结束时间 23:59:59
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastMonthEndDate(Integer year, Integer month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.add(Calendar.MONTH, -1);
		return getMonthEndDay(calendar.getTime());
	}

	/**
	 * 比较日期中时间部分
	 * @param sd
	 * @param ed
	 * @return
	 */
	public static int compareTime(Date sd, Date ed) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sd);
		calendar.set(2017, 11, 30);
		Date stime = calendar.getTime();
		
		calendar.setTime(ed);
		calendar.set(2017, 11, 30);
		Date etime = calendar.getTime();
		
		return stime.compareTo(etime);
	}

	public static int compareTime(String startTime, String endTime) {

		Date sd = DateUtils.stringToDate(startTime, "HH:mm");
		Date ed = DateUtils.stringToDate(endTime, "HH:mm");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sd);
		calendar.set(2017, 11, 30);
		Date stime = calendar.getTime();

		calendar.setTime(ed);
		calendar.set(2017, 11, 30);
		Date etime = calendar.getTime();

		return stime.compareTo(etime);
	}

	/**
	 * 将日期分割成 一天一天的日期集合
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Map<String, Date>> dateToList(Date startDate, Date endDate) {
		Calendar calendar = Calendar.getInstance();
		List<Map<String,Date>> dateMap = new ArrayList<Map<String,Date>>();
		int days = getDays(startDate, endDate);
		for(int i=0;i<days+1;i++){
			Map<String,Date> map = new HashMap<String,Date>();
			dateMap.add(map);
			if(i==0){
				if(days==0){
					map.put("startDate", startDate);
					map.put("endDate", endDate);
				}else{
					calendar.set(Calendar.HOUR_OF_DAY, 23);
					calendar.set(Calendar.MINUTE, 59);
					calendar.set(Calendar.SECOND, 59);
					calendar.set(Calendar.MILLISECOND, 59);
					Date ed = calendar.getTime();
					map.put("startDate", startDate);
					map.put("endDate", ed);
				}
			}else if(i==days){
				calendar.setTime(endDate);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				Date sd = calendar.getTime();
				map.put("startDate", sd);
				map.put("endDate", endDate);
			}else{
				calendar.setTime(startDate);
				calendar.add(Calendar.DAY_OF_YEAR, i);
				
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				Date sd = calendar.getTime();
				
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 59);
				Date ed = calendar.getTime();
				
				map.put("startDate", sd);
				map.put("endDate", ed);
			}
		}
		return dateMap;
	}

	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 获取月份起始日期
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMinMonthDate(Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取月份最后日期
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMaxMonthDate(Date date) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	public static void main(String[] args) {
		/*
		Date sd = new Date();
		Date ed = getSomeDaysBeforeAfter(sd, 3);
		ed = addMinute(ed, 100);
		List<Map<String, Date>> dateToList = dateToList(sd,ed);
		for(Map<String, Date> map:dateToList){
			Date startDate=map.get("startDate");
			Date endDate=map.get("endDate");
			System.out.println(dateToString(startDate, patternD)+"-----"+dateToString(endDate, patternD));
		}
		*/
//		System.out.println(DateUtils.dateToString(new Date(), "yyyyMMddHHmmssSSS"));

		Date sd = stringToDate("2018-11-28 00:00:00.000", "yyyy-MM-dd HH:mm:ss.SSS");
		Date ed = stringToDate("2018-11-27 23:59:59.999", "yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(getDays(sd,ed));

	}
	
	
	
	
	
	
	
}
