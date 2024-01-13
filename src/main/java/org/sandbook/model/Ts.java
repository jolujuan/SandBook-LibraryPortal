package org.sandbook.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Ts {
	public static long MS_SEC = 1000L;
	public static long MS_MIN = 60000L;
	public static long MS_HOUR = 3600000L;
	public static long MS_DAY = 86400000L;
	public static long MS_MONTH = 2592000000L;
	public static long MS_YEAR = 31536000000L;

	static public String hm(Float seconds) {
		if (seconds == null) return "";
		if (seconds == 0F) return "";
		Integer hour = (int) Math.floor(Math.abs(seconds));
    	Integer min = (int) Math.round((Math.abs(seconds) - hour) * 60F);
    	return (seconds < 0 ? "-" : "") + hour + "h " + min + "m";
	}
	
	static public String hms(long time) {
		long hour = time / 3600000L;
		long min = time % 3600000L / 60000L;
		long sec = time % 3600000L % 60000L / 1000L;
		return "" + hour + "h " + min + "m " + sec + "s";
	}
	
	static public String dow(Date date) {
		return new SimpleDateFormat("EEEE").format(date);
	}
	
	static public String date(Date date) {
		if (date == null) return "";
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}
	
	public static Date dateES(String value) {
		if (value == null) return null;
		if (value.trim().equals("")) return null;
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(value);
		} catch (ParseException e) {}
		return null;
	}
	
	static public String time(Date date) {
		if (date == null) return "";
		return new SimpleDateFormat("HH:mm:ss").format(date);
	}
	
	static public String ts(Date date) {
		if (date == null) return "";
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
	}
	
	static public String tsUser(String user) {
		return "\r\n[" + new SimpleDateFormat("dd/MM/yyyy HH:mm Z").format(new Date()) + " " + user + "] ";
	}
	
	public static String formatTs(Date date) {
		if (date == null) return null;
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static String formatFile(Date date) {
		if (date == null) return null;
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
	}
	
	public static boolean isValidFormat(String format, String string) {
		/* Check if string is a date with the format "format" */
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(string);
        } catch (ParseException ex) {
        }
        return date != null;
    }
	
	public static Date MondayOfWeek(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		return calendar.getTime();
	}
	public static Date SundayOfWeek(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.WEEK_OF_YEAR, 1);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		return calendar.getTime();
	}
	
	public static Date parseDateOrIso(String string) {
		/* Returns date if string is "dd/MM/yyyy" or yyyy-MM-dd'T'HH:mm:ssXXX */ 
		
		Date date = null;
		
		if (string == null) return null;
		if (string.equals("")) return null;
		
		if (isValidFormat("dd/MM/yyyy",string)) {
			//format "dd/MM/yyyy"
			date= parseDate(string);
		}else {
			//format yyyy-MM-dd'T'HH:mm:ssXXX
			date= parseIso(string);
		}
		return date;
	}
	
	public static Date parseDate(String string) {
		Date date = null;
		try {
			if (string != null) if (!string.equals("")) date = new SimpleDateFormat("dd/MM/yyyy").parse(string);
		} catch (ParseException e) {}
		return date;
	}
	public static Date parseDate(String string, String format) {
		Date date = null;
		try {
			if (string != null) if (!string.equals("")) date = new SimpleDateFormat(format).parse(string.substring(0, format.length()-1));
		} catch (ParseException e) {}
		return date;
	}
	
	public static Date parseIso(String string) {
		Date date = null;
		try {
			if (string != null) if (!string.equals("")) date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(string);
		} catch (ParseException e) {}
		return date;
	}
	
	public static Date parseFormat(String string, String format) {
		Date date = null;
		try {
			if (string != null) if (!string.equals("")) {
				if (format != null){
					date = new SimpleDateFormat(format).parse(string);
				} else {
					date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(string);
				}
			}
		} catch (ParseException e) {}
		return date;
	}
	
	public static String formatIso(Date date) {
		if (date == null) return null;//evitar error fecha nula
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(date);
	}
	
	public static Date parseIsoNoZ(String string) {
		Date date = null;
		try {
			if (string != null) if (!string.equals("")) date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(string);
		} catch (ParseException e) {}
		return date;
	}
	
	public static String formatIsoNoZ(Date date) {
		if (date == null) return null;
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);
	}
	
	public static String formatIsoMin(Date date) {
		if (date == null) return null;
		return new SimpleDateFormat("yyyy-MM-dd'T'00:00:00").format(date);
	}
	
	public static String formatIsoMax(Date date) {
		if (date == null) return null;
		return new SimpleDateFormat("yyyy-MM-dd'T'23:59:59").format(date);
	}
	
	public static Date parseIsoMin(String string) {
		Date date = null;
		try {
			if (string != null) if (!string.equals("")) date = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00").parse(string);
		} catch (ParseException e) {}
		return date;
	}
	
	public static String iso2ts(String date) {
		if (date == null) return null;
		return date.replace("T", " ");
	}
	
	public static Date min(Date date) {
		if (date == null) return null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date max(Date date) {
		if (date == null) return null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date nextDow(Date date, int dow) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		while (cal.get(Calendar.DAY_OF_WEEK) < dow) 
			cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	public static Date previousDow(Date date, int dow) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		while (cal.get(Calendar.DAY_OF_WEEK) > dow) 
			cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	
	public static Date nextLab(Date date, int days) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		while (days > 0) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			if (
					cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
					cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
				)
					days--;
		}
		return cal.getTime();
	}
	
	public static Date previousLab(Date date, int days) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		while (days > 0) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
			if (
					cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
					cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
				)
					days--;
		}
		return cal.getTime();
	}
	
	public static int diffMonth(Calendar from, Calendar to) {
		int diff = 
				Math.abs(from.get(Calendar.YEAR) - to.get(Calendar.YEAR)) * 12 +
				Math.abs(from.get(Calendar.MONTH) - to.get(Calendar.MONTH));
		return diff;
	}
	
	public static int diffMonth(Date from, Date to) {
		Calendar desde = Calendar.getInstance();
		desde.setTime(from);
		Calendar hasta = Calendar.getInstance();
		hasta.setTime(to);
		return Ts.diffMonth(desde, hasta);
	}
	
	public static long diffMinute(Date from, Date to) {
		return Math.abs(from.getTime() - to.getTime()) / 60000L;
	}
	
	/**
     * Devuelve la diferencia en dias entre las fechas "from" y "to"
     *
     * @param from A Date.
     * @param to A Date.
     * @return An Integer.
     * @since 1.0
     */
	public static Integer diffDays(Date from, Date to) {
		String days = String.valueOf(Math.abs(from.getTime() - to.getTime()) / (60000L*60*24));
		return Integer.valueOf(days);
	}
	
	public static Date addMinute(Date date, int amount) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, amount);
		return cal.getTime();
	}
	
	public static Date addHour(Date date, int amount) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, amount);
		return cal.getTime();
	}
	
	public static Integer get24Hour(Date date) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hourOfDay  = cal.get(Calendar.HOUR_OF_DAY); // 24 hour clock
		
		return hourOfDay;
	}
	
	public static Date addDay(Date date, int amount) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}
	
	public static Date addMonth(Date date, int amount) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		return cal.getTime();
	}
	
	public static Date addYear(Date date, int amount) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		return cal.getTime();
	}
	
	public static int getYear(Date date) {
		if (date == null) return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	public static int getMonth(Date date) {
		if (date == null) return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}
	
	public static int getDow(Date date) {
		if (date == null) return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	public static int getDowIso(Date date) {
		if (date == null) return 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY: return 1;
		case Calendar.TUESDAY: return 2;
		case Calendar.WEDNESDAY: return 3;
		case Calendar.THURSDAY: return 4;
		case Calendar.FRIDAY: return 5;
		case Calendar.SATURDAY: return 6;
		case Calendar.SUNDAY: return 7;
		};
		return 0;
	}
	
	public static Date firstMonth(Date date) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	public static Date lastMonth(Date date) {
		if (date == null) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	public static Date today() {
		return Ts.min(new Date());
	}
	
	public static Date tomorrow() {
		return Ts.addDay(Ts.today(), 1);
	}
	public static String formatIsoDateTime(Date date, Date time) {
		if (date == null) return null;
		String format = new SimpleDateFormat("yyyy-MM-dd'T'").format(date); 
		if (time != null)
			format += new SimpleDateFormat("HH:mm:ss").format(time);
		else
			format += "00:00:00";
		return format;
	}
}
