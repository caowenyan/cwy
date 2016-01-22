package cn.cindy.date;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	/**
	 * 获取UTC世界协调时间，会去除时区和夏令时的影响
	 * @return 标准间隔毫秒数（1970） 
	 */
	public static long getUTCDateWithTimeInMillisFormate(){
		
		Calendar cal = Calendar.getInstance();
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		int dstOffset = cal.get(Calendar.DST_OFFSET);

		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return cal.getTimeInMillis();
	}
	/**
	 * 获取UTC世界协调时间，会去除时区和夏令时的影响
	 * @return Date
	 */
	public static Date getUTCDateWithDateFormate(){
		
		return new Date(getUTCDateWithTimeInMillisFormate());
	}
	/**
	 * 获取当地时间(非系統時間)
	 * @return 标准间隔毫秒数（1970） 
	 */
	public static long getLocalDateWithTimeInMillisFormate(){
		
		return Calendar.getInstance(Locale.CHINA).getTimeInMillis();
	}
	/**
	 * 获取当地时间(非系統時間)
	 * @return Date
	 */
	public static Date getLocalDateWithDateFormate(){
		
		return new Date(getLocalDateWithTimeInMillisFormate());
	}
}
