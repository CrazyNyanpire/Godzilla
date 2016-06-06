package com.acetecsemi.godzilla.Godzilla.application.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class MyDateUtils {
	
	public static final String DefFormatString = "yyyy-MM-dd";
	
	public static final String formater = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DefFormatGMTString_CN = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z '(中国标准时间)'";
	
	public static final String DefFormatExtString = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static Long milliSecondDateDiff(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			return endDate.getTime() - startDate.getTime();
		} else {
			return null;
		}
	}

	public static Long secondDateDiff(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			float temp = (float) (endDate.getTime() - startDate.getTime()) / 1000;
			return Long.valueOf(Math.round(temp));
		} else {
			return null;
		}
	}

	public static boolean isDate(String value, String format) {

		SimpleDateFormat sdf = null;
		ParsePosition pos = new ParsePosition(0);// 指定从所传字符串的首位开始解析

		if (value == null || format == null || "".equals(format)) {
			return false;
		}
		try {
			sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			Date date = sdf.parse(value, pos);
			if (date == null) {
				return false;
			} else {
				// 更为严谨的日期,如2011-03-024认为是不合法的
				if (pos.getIndex() > sdf.format(date).length()) {
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Date str2Date(String strDate, String formater) {
		if (strDate == null || "".equals(strDate)) {
			return null;
		}
		if (formater == null) {
			formater = DefFormatString;
		}
		SimpleDateFormat df = new SimpleDateFormat(formater);
		Date date = new Date();
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			pe.getStackTrace();
		}
		return date;
	}
	
	public static Date str2Date(Object strDate, String formater,Locale locale) {
		if (strDate == null || "".equals(strDate)) {
			return null;
		}
		if (formater == null) {
			formater = DefFormatString;
		}
		SimpleDateFormat df = new SimpleDateFormat(formater,locale);
		Date date = null;
		try {
			date = df.parse(strDate.toString());
		} catch (ParseException pe) {
			pe.getStackTrace();
		}
		return date;
	}


	public static String getToday() {
		return formaterDate(new Date(), DefFormatString);
	}

	public static String getTodayTime(String formater) {
		return formaterDate(new Date(), formater);
	}

	public static String formaterDate(Date date, String formater) {
		return formaterDate(date,formater,null);
	}
	
	public static String formaterDate(Date date, String formater,Locale locale) {
		if (date == null) {
			return "";
		}
		if (formater == null) {
			formater = DefFormatString;
		}
		SimpleDateFormat sdf ;
		if(locale == null){
			sdf = new SimpleDateFormat(formater);
		}else{
			sdf = new SimpleDateFormat(formater,locale);
		}
		return sdf.format(date);
	}

	public static Date getTodayDD() {
		SimpleDateFormat df = new SimpleDateFormat(DefFormatString);
		String today = getToday();
		Date date = new Date();
		try {
			date = df.parse(today);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static int getaaa(Date date1, Date date2) {
		String strDate = formaterDate(date1, null);
		String year = strDate.substring(0, 4);
		String month = strDate.substring(5, 7);
		int intYear = Integer.parseInt(year);
		if ("01".equals(month) || "02".equals(month) || "03".equals(month)) {
			strDate = String.valueOf(intYear) + "-03-31";
		} else {
			strDate = String.valueOf(intYear + 1) + "-03-31";
		}
		Date date3 = str2Date(strDate, null);
		int compar = compartToDate(date2, date3);
		if (compar == 1) {
			return 2;
		} else if (compar == -1) {
			return 1;
		} else {
			return 3;
		}
	}

	public static int getCurrentMonthLastDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public static String getNewDayNumDate(String strDate, int days) {
		String[] date = strDate.split("-"); // 将要转换的日期字符串拆分成年月日
		int year, month, day;
		year = Integer.parseInt(date[0]);
		month = Integer.parseInt(date[1]) - 1;
		day = Integer.parseInt(date[2]);
		GregorianCalendar d = new GregorianCalendar(year, month, day);
		d.add(Calendar.DATE, days);
		return DateFormat.getDateInstance().format(d.getTime());
	}

	public static Date getNewDayDate(Date date, int days) {
		if (date != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, days);
			return c.getTime();
		}
		return null;
	}

	public static int getDayNumBetween2Date(Date d1, Date d2) {
		if (isEqualsInYMD(d1, d2)) {
			return 0;
		}
		long l = d2.getTime() - d1.getTime();
		int day = (int) (l / (24 * 60 * 60 * 1000));
		if (day < 0) {
			day = (-day);
		}
		int m = (int) (l % (24 * 60 * 60 * 1000));
		Calendar c = Calendar.getInstance();
		if (d1.compareTo(d2) <= 0) {
			c.setTime(d1);
			c.add(Calendar.MILLISECOND, m);
			if (isEqualsInYMD(d1, c.getTime())) {
				return day;
			} else {
				return day + 1;
			}
		} else {
			c.setTime(d2);
			c.add(Calendar.MILLISECOND, m);
			if (isEqualsInYMD(d2, c.getTime())) {
				return day;
			} else {
				return day + 1;
			}
		}
	}

	public static boolean isEqualsInYMD(Date d1, Date d2) {
		Calendar c = Calendar.getInstance();
		c.setTime(d1);
		int year1 = c.get(Calendar.YEAR);
		int dayOfYear1 = c.get(Calendar.DAY_OF_YEAR);
		c.setTime(d2);
		int year2 = c.get(Calendar.YEAR);
		int dayOfYear2 = c.get(Calendar.DAY_OF_YEAR);
		if (year1 != year2) {
			return false;
		}
		if (dayOfYear1 != dayOfYear2) {
			return false;
		}
		return true;
	}

	public static Date getNewDayPrevDate(Date date, int days) {
		if (date != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_MONTH, -days);
			return c.getTime();
		}
		return null;
	}

	public static int compartToDate(Date date1, Date date2) {
		if (date1 != null && date2 != null) {
			if (date1.getTime() > date2.getTime()) {
				return 1;
			} else if (date1.getTime() < date2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		}
		return 0;
	}

	// 计算当月最后一天,返回字符串
	public static String getMonthLastDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);// 设为当前月的1号
		c.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		c.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		return ymd(c.getTime());
	}

	// 计算当月第一天,返回字符串
	public static String getMonthFirstDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);// 设为当前月的1号
		return ymd(c.getTime());
	}

	public static String ymd(Date date) {
		return formaterDate(date, null);
	}

	public static Date ymdStr2Date(String strDate) {
		String formater = DefFormatString;
		return str2Date(strDate, formater);
	}

	public static String getAfterSomeDayYms(String date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(MyDateUtils.ymdStr2Date(date));
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + day);
		return ymd(c.getTime());
	}

	public static List<String> getDateList(String startDate, String endDate)
			throws ParseException {
		List<String> dateList = new ArrayList<String>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DefFormatString,
				Locale.ENGLISH); // 设定格式
		Date start = dateFormat.parse(startDate);
		Date end = dateFormat.parse(endDate);
		long day = ((end.getTime() - start.getTime()) / 1000);// 秒数
		day = day / (60 * 60 * 24); // 天数
		for (int i = 0; i <= day; i++) {
			String date = getAfterSomeDayYms(startDate, i);
			dateList.add(date);
		}
		return dateList;
	}

	public static String getCurrentSeasonLastDate(String year, String monthInNum) {
		if ("01".equals(monthInNum) || "02".equals(monthInNum)
				|| "03".equals(monthInNum)) {
			return year + "-03-31";
		} else if ("04".equals(monthInNum) || "05".equals(monthInNum)
				|| "06".equals(monthInNum)) {
			return year + "-06-30";
		} else if ("07".equals(monthInNum) || "08".equals(monthInNum)
				|| "09".equals(monthInNum)) {
			return year + "-09-30";
		} else {
			return year + "-12-31";
		}
	}
	
	public static String getAttendanceDefStartDate(int year,int month) {
		GregorianCalendar d = new GregorianCalendar(year, month-2, 1);
		return MyDateUtils.getAfterSomeDayYms(ymd(d.getTime()), 25);
	}
	
	public static String getAttendanceDefEndDate(int year,int month) {
		GregorianCalendar d = new GregorianCalendar(year, month-2, 1);
		d.set(Calendar.DATE, 1);// 设为当前月的1号
		d.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		d.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		return MyDateUtils.getAfterSomeDayYms(ymd(d.getTime()), 25);
	}
	
    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
    
    public static Date addHours(Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }
    
    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }
    
    public static String addMonths(String dateStr, int amount) {
    	Date date = str2Date(dateStr,DefFormatString);
        return formaterDate(add(date, Calendar.MONTH, amount),DefFormatString);
    }
    
	public static String getDayHour(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			Long min = (endDate.getTime() - startDate.getTime())/(1000*60);
			Long hour = min / 60 % 24;
			Long day = min / (60 * 24);
			min = min % 60;
			if(day > 0)
				return String.valueOf(day) + "d " + String.valueOf(hour) + "h";
			else
				return String.valueOf(hour) + "h " + String.valueOf(min) + "m";
		} else {
			return null;
		}
	}
	
	// 查询当前日期的YYWW,返回字符串
	public static String getYYWW() {
		Calendar c = Calendar.getInstance();
		String weekOfYear = "00" + String.valueOf(c.get(Calendar.WEEK_OF_YEAR));
		weekOfYear = weekOfYear.substring(weekOfYear.length()-2);
		String year = String.valueOf(c.get(Calendar.YEAR)).substring(2);
		return year + weekOfYear;
	}
	
	// 查询当前日期的YYMM,返回字符串
	public static String getYYMM() {
		Calendar c = Calendar.getInstance();
		String weekOfYear = "00" + String.valueOf(c.get(Calendar.MONTH));
		weekOfYear = weekOfYear.substring(weekOfYear.length()-2);
		String year = String.valueOf(c.get(Calendar.YEAR) + 1).substring(2);
		return year + weekOfYear;
	}
}
