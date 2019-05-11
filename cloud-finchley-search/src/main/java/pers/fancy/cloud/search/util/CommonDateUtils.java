package pers.fancy.cloud.search.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 */
public class CommonDateUtils {
	private static final Log LOG = LogFactory.getLog(CommonDateUtils.class);

	private static final String SHORT_DATE_FORMAT_STR = "yyyy-MM-dd";
	private static final String LONG_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
 
	private static final String EARLY_TIME = "00:00:00";
	private static final String LATE_TIME = "23:59:59";

	/**
	 * 使用预设Format格式化Date成字符串
	 *
	 * @return String
	 */
	public static String format(Date date) {
		return date == null ? "" : format(date, LONG_DATE_FORMAT_STR);
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 *
	 * @return String
	 */
	public static String format(Date date, String pattern) {
		return date == null ? "~" : new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 字符串解析成 java.sql.Date 日期
	 *
	 * @author jianguo.xu
	 * @param shortDateStr
	 * @param format
	 * @return
	 */
	public static java.sql.Date parserShortDate(String shortDateStr,
			String format) {
		if (shortDateStr.isEmpty()) {
			return null;
		}
		DateFormat dateFormate = new SimpleDateFormat(format);
		try {
			Date date = dateFormate.parse(shortDateStr);
			return new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			LOG.error("parser java.sql.Date error", e);
			return null;
		}
	}

	/**
	 * 字符串解析成日期
	 *
	 * @author jianguo.xu
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date parserDate(String dateStr, String format) {
		if (dateStr.isEmpty()) {
			return null;
		}
		DateFormat dateFormate = new SimpleDateFormat(format);
		try {
			Date date = dateFormate.parse(dateStr);
			return new Date(date.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据TimeUnit增加指定日期的的时间
	 *
	 * @author jianguo.xu
	 * @param date
	 *            要增加的日期
	 * @param timeUnit
	 *            增加的日历字段（只能取 DAYS 到 MILLISECONDS 之间的枚举，否则报错）
	 * @param value
	 *            增加的值(当值为负数时表示减少)
	 * @return
	 */
	public static Date add(Date date, TimeUnit timeUnit, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(calField(timeUnit), value);
		return cal.getTime();
	}

	/**
	 * 根据TimeUnit设定指定日期的的时间
	 *
	 * @author jianguo.xu
	 * @param date
	 *            要设定的日期
	 * @param timeUnit
	 *            设定的日历字段（只能取 DAYS 到 MILLISECONDS 之间的枚举，否则报错）
	 * @param value
	 *            设定的值(当值为负数时表示减少)
	 * @return
	 */
	public static Date set(Date date, TimeUnit timeUnit, int value) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(calField(timeUnit), value);
		return cal.getTime();
	}

	public static enum TimeUnit {
		/**日期类枚举*/
		YEAR, MONTH, WEEK_OF_YEAR, WEEK_OF_MONTH,DAY_OF_WEEK, DAYS,DAY_OF_MONTH, HOURS, MINUTES, SECONDS, MILLISECONDS;
	}
	private static int calField(TimeUnit timeUnit) {

		int field = 0;
		if (timeUnit == TimeUnit.YEAR) {
			field = Calendar.YEAR;
		} else if (timeUnit == TimeUnit.MONTH) {
			field = Calendar.MONTH;
		} else if (timeUnit == TimeUnit.WEEK_OF_YEAR) {
			field = Calendar.WEEK_OF_YEAR;
		} else if (timeUnit == TimeUnit.WEEK_OF_MONTH) {
			field = Calendar.WEEK_OF_MONTH;
		} else if (timeUnit == TimeUnit.DAY_OF_WEEK) {
			field = Calendar.DAY_OF_WEEK;
		} else if (timeUnit == TimeUnit.DAYS) {
			field = Calendar.DAY_OF_YEAR;
		} else if (timeUnit == TimeUnit.DAY_OF_MONTH) {
			field = Calendar.DAY_OF_MONTH;
		} else if (timeUnit == TimeUnit.HOURS) {
			field = Calendar.HOUR_OF_DAY;
		} else if (timeUnit == TimeUnit.MINUTES) {
			field = Calendar.MINUTE;
		} else if (timeUnit == TimeUnit.SECONDS) {
			field = Calendar.SECOND;
		} else if (timeUnit == TimeUnit.MILLISECONDS) {
			field = Calendar.MILLISECOND;
		} else {
			throw new RuntimeException("timeUnit error");
		}
		return field;
	}



	/**
	 * 根据TimeUnit得到指定日期的值
	 *
	 * @author jianguo.xu
	 * @param date
	 * @param timeUnit
	 * @return
	 */
	public static int getValue(Date date, TimeUnit timeUnit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(calField(timeUnit));
	}
	/**
	 * 根据日期得到当前星期数<br/>
	 * 星期一为1、 星期二为2 ... 星期日为7
	 * @param date
	 * @return
	 * @author  xu.jianguo
	 */
	public static int getDayOfWeek(Date date) {
		int dayOfWeek =  CommonDateUtils.getValue(date, TimeUnit.DAY_OF_WEEK);
		return (dayOfWeek == 1)?7:dayOfWeek-1;
	}

	/**
	 * 根据TimeUnit清除指定的日历字段
	 *
	 * @author jianguo.xu
	 * @param date
	 *            要清除的日期
	 * @param timeUnit
	 *            清除的日历字段（只能取 DAYS 到 MILLISECONDS 之间的枚举，否则报错）
	 * @return
	 */
	public static Date clear(Date date, TimeUnit timeUnit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.clear(calField(timeUnit));
		return cal.getTime();
	}

	/**
	 * <br>
	 * 第一个日期减去第二个日期后得到的天数</br> <br>
	 * 如果减去的后的天数有不满足一整天的，则不计入天数内</br>
	 *
	 * @param date
	 *            被减日期
	 * @param other
	 *            减去的日期
	 * @return 返回减去后的天数
	 */
	public static long subtractDay(Date date, Date other) {
		return subtractSecond(date, other) / (24 * 60 * 60);
	}

	/**
	 * 两个日期相减得到相差的秒数
	 *
	 * @param date
	 * @param other
	 * @return
	 */
	public static long subtractSecond(Date date, Date other) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		long dateTimeInMillis = calendar.getTimeInMillis();
		Calendar otherCalendar = Calendar.getInstance();
		otherCalendar.setTime(other);
		long otherTimeInMillis = otherCalendar.getTimeInMillis();
		return (dateTimeInMillis - otherTimeInMillis) / (1000);
	}

	/**
	 * 字符串解析成 java.sql.Time 时间
	 *
	 * @author jianguo.xu
	 * @param timeStr
	 * @param timeFormat
	 * @return
	 */
	public static java.sql.Time parserTime(String timeStr, String timeFormat) {
		DateFormat dateFormate = new SimpleDateFormat(timeFormat);
		try {
			Date date = dateFormate.parse(timeStr);
			return new java.sql.Time(date.getTime());
		} catch (ParseException e) {
			LOG.error("parser java.sql.Time error", e);
			return null;
		}
	}



	/**
	 * 得到某个日期在这一天中时间最早的日期对象
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getEarlyInTheDay(Date date) {
        DateFormat shortFormat = new SimpleDateFormat(SHORT_DATE_FORMAT_STR);
        DateFormat longFormat = new SimpleDateFormat(LONG_DATE_FORMAT_STR);
		String dateString = shortFormat.format(date) + " " + EARLY_TIME;
		try {
			return longFormat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException("parser date error.", e);
		}
	}

	/**
	 * 得到某个日期在这一天中时间最晚的日期对象
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getLateInTheDay(Date date) {
        DateFormat shortFormat = new SimpleDateFormat(SHORT_DATE_FORMAT_STR);
        DateFormat longFormat = new SimpleDateFormat(LONG_DATE_FORMAT_STR);
		String dateString = shortFormat.format(date) + " " + LATE_TIME;
		try {
			return longFormat.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException("parser date error.", e);
		}
	}

	/**
	 * 根据年龄计算出生日
	 *
	 * @author jianguo.xu
	 * @param age
	 * @return
	 */
	public static java.sql.Date getBirthday(int age) {
		Date date = new Date();
		date = add(date, TimeUnit.YEAR, -age);
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 得到当前日期的毫秒数
	 *
	 * @return
	 */
	public static long getNowTime() {
		return  System.currentTimeMillis();
	}
	/**
	 * 得到指定日期所在周的最早日期(星期一)
	 * @param date
	 * @return
	 */
	public static Date getEarlyIntheWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return getEarlyInTheDay(cal.getTime());
	}

	/**
	 * 得到指定日期所在周的最晚日期(星期日)
	 * @param date
	 * @return
	 */
	public static Date getLateInTheWeek(Date date) {
		return getLateInTheDay(add(getEarlyIntheWeek(date), TimeUnit.DAYS, 6));
	}
	/**
	 * 得到指定日期所在月的最早日期(1号)
	 * @param date
	 * @return
	 */
	public static Date getEarlyIntheMonth(Date date) {
		return getEarlyInTheDay(CommonDateUtils.set(date, TimeUnit.DAY_OF_MONTH, 1));
	}

	/**
	 * 得到指定日期所在月的最晚日期
	 * @param date
	 * @return
	 */
	public static Date getLateInTheMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, maxDay);
		return getLateInTheDay(cal.getTime());
	}

	/**
	 * 得到指定日期所在年的最早日期(1月1号)
	 * @param date
	 * @return
	 */
	public static Date getEarlyInTheYear(Date date) {
		Date result = CommonDateUtils.getEarlyInTheDay(new DateTime(date).withDayOfYear(1).toDate());
		return result;
	}

	/**
	 * 得到指定日期所在年的最晚日期(12月31号)
	 * @param date
	 * @return
	 */
	public static Date getLateInTheYear(Date date) {
		Date result = CommonDateUtils.getLateInTheDay(new DateTime(date).withDayOfYear(1).plusYears(1).minusDays(1).toDate());
		return result;
	}
	/**
     * 使用预设Format格式化Date成字符串 yyyy:mm:dd
     *
     * @return String
     */
    public static String shorDateformat(Date date) {
        return date == null ? "" : format(date, SHORT_DATE_FORMAT_STR);
    }
    
    /**
     * 获取当前时间的前几天或者后几天的时间
     * @param days
     * @return
     * @author ranjin 2014年12月3日
     * @throws ParseException 
     */
    public static Date getNextDay(int days) throws ParseException{
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -days);    //-表示前几天，+表示后几天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(df.format(date));
        
    }

}
