package com.googlecode.jtiger.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * 
 * <p>
 * <a href="DateUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 *         pattern. Minutes should be mm not MM (MM is month).
 * @version $Revision: 1.7 $ $Date: 2005/05/04 04:57:41 $
 */
public final class DateUtil {
  // ~ Static fields/initializers =============================================
  /**
   * Log of the class
   */
  private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

  /**
   * 缺省的日期匹配模式
   */
  private static String defaultDatePattern = null;

  /**
   * 时间匹配模式
   */
  private static String timePattern = "HH:mm:ss";

  // ~ Methods ================================================================

  /**
   * Return default datePattern (MM/dd/yyyy)
   * 
   * @return a string representing the date pattern on the UI
   */
  public static synchronized String getDatePattern() {
    Locale locale = LocaleContextHolder.getLocale();
    try {
      defaultDatePattern = ResourceBundle.getBundle(
          ResBundleUtil.DEFAULT_BUNDLE, locale).getString("date.format");
    } catch (MissingResourceException mse) {
      defaultDatePattern = "yyyy-MM-dd";
    }

    return defaultDatePattern;
  }

  /**
   * This method attempts to convert an Oracle-formatted date in the form
   * dd-MMM-yyyy to mm/dd/yyyy.
   * 
   * @param aDate date from database as a string
   * @return formatted string for the ui
   */
  public static final String getDate(Date aDate) {
    SimpleDateFormat df = null;
    String returnValue = "";

    if (aDate != null) {
      df = new SimpleDateFormat(getDatePattern());
      returnValue = df.format(aDate);
    }

    return (returnValue);
  }

  /**
   * This method generates a string representation of a date/time in the format
   * you specify on input
   * 
   * @param aMask the date pattern the string is in
   * @param strDate a string representation of a date
   * @return a converted Date object
   * @see java.text.SimpleDateFormat
   * @throws ParseException
   */
  public static final Date convertStringToDate(String aMask, String strDate)
      throws ParseException {
    if (aMask == null || aMask.length() == 0) {
      aMask = getDatePattern();
    }
    if (strDate == null || strDate.length() == 0) {
      return new Date();
    }
    SimpleDateFormat df = null;
    Date date = null;
    df = new SimpleDateFormat(aMask);

    
    logger.debug("converting '{}' to date with mask '{}'" , strDate, aMask);
    

    try {
      date = df.parse(strDate);
    } catch (ParseException pe) {
      // log.error("ParseException: " + pe);
      throw new ParseException(pe.getMessage(), pe.getErrorOffset());
    }

    return (date);
  }

  /**
   * This method returns the current date time in the format: MM/dd/yyyy HH:MM a
   * 
   * @param theTime the current time
   * @return the current date/time
   */
  public static String getTimeNow(Date theTime) {
    return getDateTime(timePattern, theTime);
  }

  /**
   * This method returns the current date in the format: MM/dd/yyyy
   * 
   * @return the current date
   * @throws ParseException
   */
  public static Calendar getToday() throws ParseException {
    Date today = new Date();
    SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

    // This seems like quite a hack (date -> string -> date),
    // but it works ;-)
    String todayAsString = df.format(today);
    Calendar cal = new GregorianCalendar();
    cal.setTime(convertStringToDate(todayAsString));

    return cal;
  }

  /**
   * This method generates a string representation of a date's date/time in the
   * format you specify on input
   * 
   * @param aMask the date pattern the string is in
   * @param aDate a date object
   * @return a formatted string representation of the date
   * 
   * @see java.text.SimpleDateFormat
   */
  public static final String getDateTime(String aMask, Date aDate) {
    SimpleDateFormat df = null;
    String returnValue = null;

    if (aDate != null) {
      df = new SimpleDateFormat(aMask);
      returnValue = df.format(aDate);
    }

    return (returnValue);
  }

  /**
   * This method generates a string representation of a date based on the System
   * Property 'dateFormat' in the format you specify on input
   * 
   * @param aDate A date to convert
   * @return a string representation of the date
   */
  public static final String convertDateToString(Date aDate) {
    return getDateTime(getDatePattern(), aDate);
  }

  /**
   * This method generates a string representation of a date based on the System
   * Property 'dateFormat' in the format you specify on input
   * @param aDate A date to convert
   * @param defaultString defaultString.
   * @return
   */
  public static final String convertDateToString(Date aDate,
      String defaultString) {
    String s = getDateTime(getDatePattern(), aDate);
    if (StringUtils.isBlank(s)) {
      return defaultString;
    }

    return s;
  }

  /**
   * This method converts a String to a date using the datePattern
   * 
   * @param strDate the date to convert (in format MM/dd/yyyy)
   * @return a date object
   * 
   * @throws ParseException
   */
  public static Date convertStringToDate(String strDate) throws ParseException {
    Date aDate = null;

    try {
      
      logger.debug("converting date with pattern: {}", getDatePattern());    
      aDate = convertStringToDate(getDatePattern(), strDate);
    } catch (ParseException pe) {
      logger.error("Could not convert '{}' to a date, throwing exception", strDate);
      // pe.printStackTrace();
      throw new ParseException(pe.getMessage(), pe.getErrorOffset());

    }

    return aDate;
  }

  /** 一天最后一个小时 */
  public static final int LAST_HOUR_OF_DATE = 23;

  /** 一小时最后一分钟 */
  public static final int LAST_MINUTE_OF_HOUR = 59;

  /** 一分钟最后一秒 */
  public static final int LAST_SECOND_OF_MIN = 59;

  /**
   * 到某一天的最后一秒
   * @param date 给定的时间
   * @return 定位到当天的最后一秒
   */
  public static Date lastSecondOfDate(Date date) {
    if (date == null) {
      date = new Date();
    }

    Calendar c = Calendar.getInstance();
    c.setTime(date);

    c.set(Calendar.HOUR_OF_DAY, LAST_HOUR_OF_DATE);
    c.set(Calendar.MINUTE, LAST_MINUTE_OF_HOUR);
    c.set(Calendar.SECOND, LAST_SECOND_OF_MIN);

    return c.getTime();
  }

  /**
   * 到某一天的第一秒
   * @param date 给定的时间
   * @return 定位到当天的第一秒
   */
  public static Date firstSecondOfDate(Date date) {
    if (date == null) {
      date = new Date();
    }

    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);

    return c.getTime();
  }
  
  /**
   * 某小时第一分第一秒
   * @param date
   * @return
   */
  public static Date firstSecondOfHour(Date date) {
  	if (date == null) {
      date = new Date();
    }

    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);

    return c.getTime();
  }

  /**
   * @see {@link Calendar#add(int, int)}
   */
  public static Date add(Date date, int field, int amount) {
    if (amount == 0) {
      return date;
    }

    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(field, amount);

    return c.getTime();
  }

  /**
   * 得到当前的日期，时间从00:00:00开始
   * @return yyyy-MM-dd hh:mm:ss
   */
  public static Date getCurrentDate() {
    return firstSecondOfDate(new Date());
  }

  /**
   * 返回自今日00:00:00以后若干天日期（如果返回自今日以前的若干天日期，请用"-days"）
   * @param date
   * @param days
   * @return
   */
  public static Date getDate(Date date, int days) {
    if (date == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, days);
    calendar.set(Calendar.HOUR, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);

    return calendar.getTime();
  }

  /**
   * @param date
   * @param months
   * @return
   */
  public static Date getMonth(Date date, int months) {
    if (date == null) {
      return null;
    }
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, months);
    calendar.set(Calendar.HOUR, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);

    return calendar.getTime();
  }
  
  /**
   * 两日期相差多少天 end - start
   * @param start
   * @param end
   * @return
   */
  public static Long getPhaseDay(Date start, Date end) {
    if (start == null || end == null) {
      return null;
    }
    
    return (end.getTime() - start.getTime()) / (1000 * 24 * 60 * 60);
  } 
  
  /**
   * 毫秒换算成时分秒
   */
  public static String millisecond2String(final Long millisecond) {
    if(millisecond == null || millisecond < 0L) {
      return "";
    }
    long dur = millisecond.longValue();  
    
    StringBuffer txt = new StringBuffer(100);
    long h = 3600 * 1000;
    long m = 60 * 1000;
    long s = 1000;
    
    if(dur >= h) {
      txt.append((int) (dur / h)).append("小时");
      dur = dur % h;
    }
    
    if(dur >= m) {
      txt.append((int) (dur / m)).append("分");
      dur = dur % m;
    }
    
    if(dur >= s) {
      txt.append((int) (dur / s)).append("秒");
    }
    
    return txt.toString();
  }


  /**
   * prevent from initializing
   * 
   */

  private DateUtil() {
  }
}
