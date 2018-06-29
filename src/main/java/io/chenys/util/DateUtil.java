package io.chenys.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static final String FORMAT_DAY_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DAY_TIME_MID_PATTERN = "yyyy-MM-dd HH:mm";
	public static final String FORMAT_DAY_TIME_DIR_PATTERN = "yyyy/MM/dd HH:mm:ss";
	public static final String FORMAT_DAY_PATTERN = "yyyy-MM-dd";
	public static final String FORMAT_DAY_DIR_PATTERN = "yyyy/MM/dd";
	public static final String FORMAT_DAY_DOT_PATTERN = "yyyy.MM.dd";
	public static final String FORMAT_TIME_PATTERN = "HH:mm:ss";
	public static final String FORMAT_TIME_MID_PATTERN = "HH:mm";
	public static final String FORMAT_NO_SPLIT_DAY_TIME_PATTERN = "yyyyMMddHHmmss";

	private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
		return new SimpleDateFormat(pattern);
	}
	
	/**
	 * 时间转换为字符串	返回yyyy-MM-dd
	 * @param date	转换时间
	 * @return
	 */
	public static String date2DayString(Date date) {
		return date2String(date,FORMAT_DAY_PATTERN);
	}
	
	/**
	 * 时间转换为字符串	返回yyyy-MM-dd HH:mm:ss
	 * @param date	转换时间
	 * @return
	 */
	public static String date2DayTimeString(Date date) {
		return date2String(date,FORMAT_DAY_TIME_PATTERN);
	}
	
	/**
	 * 时间转换为字符串
	 * @param date		转换时间
	 * @param pattern	格式化类型
	 * @return
	 */
	public static String date2String(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = getDateFormat(pattern);
		if (date != null)
			return simpleDateFormat.format(date);
		return null;
	}
	
	/**
	 * 字符串转换为时间类型-根据字符串格式自动匹配时间格式类型
	 * @param dateString
	 * @return
	 */
	public static Date string2Date(String dateString) {
		String pattern = getPattern(dateString);
		return string2Date(dateString, pattern);
	}
	
	/**
	 * 字符串转换为时间类型
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date string2Date(String dateString, String pattern) {
		if (dateString == null || "".equals(dateString)) {
			return null;
		}
		SimpleDateFormat dateFormat = getDateFormat(pattern);
		try {
			Date result = dateFormat.parse(dateString);
			return result;
		} catch (Exception e) {
			logger.error("日期字符串转换出错!", e);
		}
		return null;
	}
	
	/**
	 * 字符串转换为时间类型
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static String stringByPattern(String dateString, String pattern) {
		if (dateString == null || "".equals(dateString)) {
			return null;
		}
		SimpleDateFormat dateFormat = getDateFormat(getPattern(dateString));
		try {
			Date temp = dateFormat.parse(dateString);
			return date2String(temp, pattern);
		} catch (Exception e) {
			logger.error("日期字符串转换出错!", e);
		}
		return null;
	}

	/**
	 * 获取某天时间开头时间或者末尾时间字符串
	 * @param date 指定时间
	 * @param type 0-返回某天00:00:00,1-返回某天23:59:59
	 * @return
	 */
	public static String getDateBeginOrEndString(Date date, int type) {
		Date dateReturn = getDateBeginOrEnd(date, type);
		return date2DayTimeString(dateReturn);
	}

	/**
	 * 获取某天时间开头时间或者末尾时间字符串
	 * @param dateString 某天时间
	 * @param type 0-返回某天00:00:00,1-返回某天23:59:59
	 * @return
	 */
	public static String getDateBeginOrEndString(String dateString, int type) {
		Date date = string2Date(dateString, FORMAT_DAY_PATTERN);
		Date dateBeginOrEnd = getDateBeginOrEnd(date, type);
		return date2DayTimeString(dateBeginOrEnd);
	}

	/**
	 * 获取某天时间开头时间或者末尾时间
	 * @param date	某天时间
	 * @param type	0-返回某天00:00:00,1-返回某天23:59:59
	 * @return
	 */
	public static Date getDateBeginOrEnd(Date date, int type) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (null != date) {
			if (0 == type) {
				calendar.set(11, 0);
				calendar.set(12, 0);
				calendar.set(13, 0);
				calendar.set(14, 0);
				return calendar.getTime();
			} else {
				calendar.set(11, 23);
				calendar.set(12, 59);
				calendar.set(13, 59);
				calendar.set(14, 999);
				return calendar.getTime();
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 获取某天时间开头时间或者末尾时间
	 * @param dateString	某天时间
	 * @param type			0-返回某天00:00:00,1-返回某天23:59:59
	 * @return
	 */
	public static Date getDateBeginOrEnd(String dateString, int type) {
		Date date = string2Date(dateString);
		return getDateBeginOrEnd(date, type);
	}

	/**
	 * 获取指定日期时间 小时：分钟 2016:01:01 01:01:01 则返回 01:01
	 * @param dateString
	 * @return
	 */
	public static String getTimeMidString(String dateString) {
		Date date = string2Date(dateString);
		SimpleDateFormat sdf = getDateFormat("HH:mm");
		String result = sdf.format(date);
		return result;
	}
	
	/**
	 * 根据时间获取年份
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		return getByType(date, Calendar.YEAR);
	}

	/**
	 * 根据时间获取月份
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		return getByType(date, Calendar.MONTH);
	}
	
	/**
	 * 根据时间获取日数
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		return getByType(date, Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 根据时间获取时间数
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		return getByType(date, Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 根据时间获取时间数
	 * @param date
	 * @return
	 */
	public static int getMinutes(Date date) {
		return getByType(date, Calendar.MINUTE);
	}
	
	/**
	 * 获取当前星期开始时间
	 * @return
	 */
	public static Date getThisWeekBegin() {
		return getWeekBegin(new Date());
	}
	
	/**
	 * 获取当前月份开始时间
	 * @return
	 */
	public static Date getThisMonthBegin() {
		return getMonthBegin(new Date());
	}

	/**
	 * 根据时间获取当前星期开始时间
	 * @param date
	 * @return
	 */
	public static Date getWeekBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, 1);
		return getDateBeginOrEnd(cal.getTime(), 0);
	}
	
	/**
	 * 根据时间获取当前月份开始时间
	 * @param date
	 * @return
	 */
	public static Date getMonthBegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getDateBeginOrEnd(cal.getTime(), 0);
	}
	
	/**
	 * 通过yyyy-MM-dd 样式的字符串得到一个星期中的第几天 
	 * @param dateString
	 * @return
	 */
	public static Integer getDaysInWeek(String dateString) {
		Calendar c = Calendar.getInstance();
		Date date = string2Date(dateString);
		c.setTime(date);
		Integer in = c.get(Calendar.DAY_OF_WEEK);
		switch (in) {
		case 1:
			in = 7;
			break;
		case 2:
			in = 1;
			break;
		case 3:
			in = 2;
			break;
		case 4:
			in = 3;
			break;
		case 5:
			in = 4;
			break;
		case 6:
			in = 5;
			break;
		case 7:
			in = 6;
			break;
		}
		return in;
	}
	
	/**
	 * 获取日期1与日期2相差天数，type为1时获取相差天数绝对值
	 * @param date1	日期1
	 * @param date2	日期2
	 * @param type	1时获取相差天数绝对值
	 * @return
	 */
	public static int getDayDif(Date date1, Date date2, int type) {
		int days = 0;
		long timeDif = date2.getTime() - date1.getTime();
		if (type == 1) {
			days = (int) (Math.abs(timeDif) / (24 * 60 * 60 * 1000));
		} else {
			days = (int) (timeDif / (24 * 60 * 60 * 1000));
		}
		return days;
	}
	
	/**
	 * 获取日期1与日期2相差天数，type为1时获取相差天数绝对值
	 * @param date1	日期1
	 * @param date2	日期2
	 * @param type	1时获取相差天数绝对值
	 * @return
	 */
	public static int getDayDif(String date1, String date2, int type) {
		Date d1 = string2Date(date1);
		Date d2 = string2Date(date2);
		return getDayDif(d1, d2, type);
	}
	
	

	/**
	 * 获取指定日期往前/往后N天日期
	 * @param date 	指定日期
	 * @param num 	N天，负数为往前，正数往后
	 * @return
	 */
	public static String addDay(String dateString, int num) {
		String pattern = getPattern(dateString);
		Date date = string2Date(dateString, pattern);
		Date addDate = addByTypeAndNums(date, Calendar.DATE, num);

		String dateStringAfter = date2String(addDate, pattern);
		return dateStringAfter;
	}
	
	/**
	 * 获取指定日期往前/往后N天日期
	 * @param date	指定日期
	 * @param num	N天，负数为往前，正数往后
	 * @return
	 */
	public static Date addDay(Date date, int num) {
		return addByTypeAndNums(date, Calendar.DATE, num);
	}
	
	/**
	 * 获取指定日期往前/往后N月日期
	 * @param date 	指定日期
	 * @param num 	N月，负数为往前，正数往后
	 * @return
	 */
	public static String addMonth(String dateString, int num) {
		String pattern = getPattern(dateString);
		Date date = string2Date(dateString, pattern);
		Date addDate = addByTypeAndNums(date, Calendar.MONTH, num);

		String dateStringAfter = date2String(addDate, pattern);
		return dateStringAfter;
	}
	
	/**
	 * 获取指定日期往前/往后N月日期
	 * @param date 	指定日期
	 * @param num 	N月，负数为往前，正数往后
	 * @return
	 */
	public static Date addMonth(Date date, int num) {
		return addByTypeAndNums(date, Calendar.MONTH, num);
	}
	
	/**
	 * 获取指定日期往前/往后N年日期
	 * @param date 	指定日期
	 * @param num 	N年，负数为往前，正数往后
	 * @return
	 */
	public static String addYear(String dateString, int num) {
		String pattern = getPattern(dateString);
		Date date = string2Date(dateString, pattern);
		Date addDate = addByTypeAndNums(date, Calendar.YEAR, num);

		String dateStringAfter = date2String(addDate, pattern);
		return dateStringAfter;
	}
	
	/**
	 * 获取指定日期往前/往后N年日期
	 * @param date 	指定日期
	 * @param num 	N年，负数为往前，正数往后
	 * @return
	 */
	public static Date addYear(Date date, int num) {
		return addByTypeAndNums(date, Calendar.YEAR, num);
	}
	
	/**
	 * 增加日期中某类型的某数值
	 * @param date
	 * @param dateType	增加类型  年-Calendar.YEAR，月-Calendar.MONTH，天-Calendar.DATE
	 * @param num		增加/减少数量
	 * @return
	 */
	private static Date addByTypeAndNums(Date date, int dateType, int num) {  
        Date dateReturn = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance(); 
            calendar.setTime(date);
            calendar.add(dateType, num);
            dateReturn = calendar.getTime();
        }
        return dateReturn;  
    }
	
	private static Integer getByType(Date date, int dateType) {  
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(dateType);
	}
	
	/**
	 * 根据字符串匹配格式化类型
	 * @param dateString
	 * @return
	 */
	private static String getPattern(String dateString) {
		if (null != dateString && !"".equals(dateString)) {
			if (dateString.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{0,1}){0,1}")) {
				return FORMAT_DAY_TIME_PATTERN;
			}else if (dateString.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}")) {
				return FORMAT_DAY_TIME_MID_PATTERN;
			} else if (dateString.matches("\\d{4}/\\d{1,2}/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{0,1}){0,1}")) {
				return FORMAT_DAY_TIME_DIR_PATTERN;
			} else if (dateString.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
				return FORMAT_DAY_PATTERN;
			} else if (dateString.matches("\\d{4}/\\d{1,2}/\\d{1,2}")) {
				return FORMAT_DAY_DIR_PATTERN;
			}else if(dateString.matches("\\d{1,2}:\\d{1,2}")){
				return FORMAT_TIME_MID_PATTERN;
			}else if(dateString.matches("\\d{1,2}:\\d{1,2}:\\d{1,2}")){
				return FORMAT_TIME_PATTERN;
			}else if(dateString.matches("\\d{4}\\d{1,2}\\d{1,2}\\d{1,2}\\d{1,2}\\d{1,2}")){
				return FORMAT_NO_SPLIT_DAY_TIME_PATTERN;
			}
		}
		return null;
	}
	
	/** 
     * 判断某个日期是否在某个日期范围 
     *  
     * @param beginDate 日期范围开始 
     * @param endDate   日期范围结束 
     * @param src  		需要判断的日期 
     * @return 
     */  
    public static boolean between(Date src, Date beginDate, Date endDate) {  
    	return src.getTime() >= beginDate.getTime() && src.getTime() <= endDate.getTime();
    } 
    
    /** 
     * 判断某个日期是否在某个日期范围 
     *  
     * @param beginDate 日期范围开始 
     * @param endDate   日期范围结束 
     * @param src  		需要判断的日期 
     * @return 
     */  
    public static boolean between(String src, String beginDate, String endDate) {  
    	if (StringUtils.isNotEmpty(src) && StringUtils.isNotEmpty(beginDate) && StringUtils.isNotEmpty(endDate)) {
			return between(string2Date(src), string2Date(beginDate), string2Date(endDate));
		}
    	return false;
    }
    
 	/** 
 	 * 方法描述: 判断是不是当日的时间
 	 * @param date
 	 * @return
 	*/
 	public static Boolean isCurrentDay(String date) {
 		Integer result = compare2DatesByPattern(date2DayString(new Date()), date, FORMAT_DAY_PATTERN);
 		if (null != result) {
			return result.intValue() == 0;
		}
		return null;
 	}
 	
 	/** 
 	 * 方法描述: 判断是不是当日的时间
 	 * @param Date date
 	 * @return
 	*/
 	public static Boolean isCurrentDay(Date date) {
 		Integer result = compare2DatesByPattern(getDateBeginOrEnd(new Date(), 0), date);
 		if (null != result) {
			return result.intValue() == 0;
		}
		return null;
 	}
 	
    /**
	 * 方法描述: 比较两个字符串类型日期前后顺序（默认精确到天，如需要精确到时分秒请传入时间格式化参数）
	 * @param d1   日期1
	 * @param d2   日期2
	 * @return 比较结果  （1表示 日期1>日期2）、（0表示日期1=日期2 ）、（-1表示 日期1<日期2） 
	 */
	public static Integer compare2Dates(String d1,String d2) {
		return compare2DatesByPattern(d1,d2,FORMAT_DAY_PATTERN);
	}
    
    /**
	 * 方法描述: 比较两个字符串类型日期前后顺序
	 * @param d1   日期1
	 * @param d2   日期2
	 * @param pattern 时间模式
	 * @return 比较结果  （1表示 日期1>日期2）、（0表示日期1=日期2 ）、（-1表示 日期1<日期2） 
	 */
	public static Integer compare2DatesByPattern(String d1, String d2, String pattern) {
		try {
			DateFormat df = new SimpleDateFormat(pattern); // HH:mm:ss
			Calendar calendar1 = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();
			calendar1.setTime(df.parse(d1));
			calendar2.setTime(df.parse(d2));
			int result = calendar1.compareTo(calendar2);
			return result;
		} catch (ParseException e) {
			logger.error("日期格式化异常...", e);
			return null;
		}
	}
	
	public static Integer compare2DatesByPattern(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		int result = c1.compareTo(c2);
		return result;
	}
	
	/** 
	 * 方法描述: 判断当日排班是否已结束
	 *
	 * @param isCurrentDay
	 * @param dayType
	 * @return
	 * @author chenys101
	 * @createDate 2016年3月22日 上午10:15:19
	*/
	public static boolean isEnd(boolean isCurrentDay, int dayType) {
		if (isCurrentDay) {
			String dayString = DateUtil.date2DayString(new Date());
			String endTimeString = "";
			switch (dayType) {
			case 1:
				endTimeString = dayString + " 12:00:00";//上午结束时间： yyyy-mm-dd 12:00:00
				break;
			case 2:
				endTimeString = dayString + " 17:30:00";//下午结束时间： yyyy-mm-dd 17:30:00
				break;
			case 4:
				endTimeString = dayString + " 23:59:00";//晚上结束时间 yyyy-mm-dd 23:59:00
				break;
			case 12:
				endTimeString = dayString + " 14:30:00";//中午结束时间 yyyy-mm-dd 14:30:00
				break;
			default:
				break;
			}
			if (DateUtil.string2Date(endTimeString).before(new Date())) {//结束时间早于当前时间
				return true;
			}
		}
		return false;
	}

	/**
	 * 方法描述: 判断某天某时是否已结束
	 *
	 * @param schDate
	 * @param time
	 * @return
	 * @author chenys101
	 * @createDate 2016年3月22日 上午10:15:19
	 */
	public static boolean isEnd(String schDate, String time) {
		String endTimeString = schDate + " "+ time;
		if (DateUtil.string2Date(endTimeString).before(new Date())) {//结束时间早于当前时间
			return true;
		}
		return false;
	}
	
	/** 
	 * 方法描述: 字符串转为Calendar
	 *
	 * @param date
	 * @return
	 * @author chenys101
	 * @createDate 2017年5月31日 下午3:40:35
	*/
	public static Calendar string2Calendar(String date) {
		return date2Calendar(string2Date(date));
	}
	
	
	/** 
	 * 方法描述: Date转为Calendar
	 *
	 * @param date
	 * @return
	 * @author chenys101
	 * @createDate 2017年5月31日 下午3:40:13
	*/
	public static Calendar date2Calendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	public static void main(String[] args) {
		System.err.println(getPattern("2014-10-10 00:11:1.8"));
		System.err.println(getPattern("2014/10/10 00:11:01.9"));
		System.err.println(getPattern("2014-10-10"));
		System.out.println(stringByPattern("08:00", DateUtil.FORMAT_TIME_PATTERN));
		
		System.err.println(addDay("2016/01/14 00:11:01", 18));
		
		System.err.println(string2Date("2014-10-1 00:1:1.1"));
		
		System.err.println(getThisWeekBegin());
		
		System.err.println(isEnd("2018-03-27","11:30"));
	}
}