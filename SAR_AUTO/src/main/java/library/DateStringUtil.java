package library;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/** 日付文字列操作クラス **/
public class DateStringUtil {
	private DateStringUtil() {}
	
	/** 祝日定義ファイル
	 *  国民の祝日一覧csvダウンロード元 : http://www8.cao.go.jp/chosei/shukujitsu/gaiyou.html
	 **/
	public static final String PUBLIC_HOLIDAY_FILE_NAME = "_public_holiday.properties";
	
	/** 
	 * 指定日が休日か判定します。<br>
	 * (土曜日、日曜日を休日として判断します。)
	 * @param date : 日付文字列
	 * @return 休日(true), それ以外(false)
	 */
	public static boolean isHoliday(String date) {
		LocalDateTime dateTime = convertDateStringToLocalDateTime(date);
		DayOfWeek week = dateTime.getDayOfWeek();
		
		switch(week) {
			case SUNDAY:
			case SATURDAY: 
				return true;
			default :
				return false;
		}
	}
	
	/**
     * 指定日が休日か判定します。
     * <pre>
	 *　(yyyy_public_holiday.propertiesに定義されている日付を祝日と判断します。)<br>
	 *  yyyy:指定日の年
	 * </pre>
	 * @param date : 日付文字列
	 * @return 祝日(true), それ以外(false)
	 */
	public static boolean isPublicHoliday(String date) {
		LocalDateTime dateTime = convertDateStringToLocalDateTime(date);
		String year = String.valueOf(dateTime.getYear());
		
		Properties pubHolidayProp = ResourceLoader.getProperties(year + PUBLIC_HOLIDAY_FILE_NAME);
		return pubHolidayProp.containsKey(date);
	}

	/** 
	 * 指定日から祝日名返却します。
	 * <pre>
	 *　yyyy_public_holiday.properties の定義を読込む 
	 * </pre>
	 * @param date : 日付文字列
	 * @return 祝日(true), それ以外(false)
	 */
	public static String getPublicHolidayName(String date) {
		LocalDateTime dateTime = convertDateStringToLocalDateTime(date);
		String year = String.valueOf(dateTime.getYear());
		
		Properties pubHolidayProp = ResourceLoader.getProperties(year + PUBLIC_HOLIDAY_FILE_NAME);
	    String publicHolidayName = pubHolidayProp.getProperty(date);
	    
	    if (publicHolidayName == null) {
	    	return "";
	    }
	    
		return publicHolidayName;
	}
	
	/** 
	 * 指定日の曜日和名を返却します。
	 * @param date : 日付文字列
	 * @return 曜日和名 
	 */
	public static String getJapaneseWeekName(String date) {        
		LocalDateTime dateTime = convertDateStringToLocalDateTime(date);
		
		DayOfWeek week = dateTime.getDayOfWeek();
		switch(week) {
			case SUNDAY:    return "日";
			case MONDAY:    return "月";
			case TUESDAY:   return "火";
			case WEDNESDAY: return "水";
			case THURSDAY:  return "木";
			case FRIDAY:    return "金";
			case SATURDAY:  return "土";
		}
		return "";
	}
	
	/** 
	 * 日付文字列をLocalDateTimeのインスタンスへ変換します。
	 * @param date : 日付文字列( "-"付き or "/"付き or 区切り文字無し)
	 * @return LocalDateTimeのインスタンス
	 */
	public static LocalDateTime convertDateStringToLocalDateTime(String date) {        
        String year = null;
        String month = null;
        String day = null;
        
        if (date.indexOf("/") >= 0 || date.indexOf("-") >= 0) {
        	year = date.substring(0,4);
        	month = date.substring(5,7);
        	day = date.substring(8,10);
        } else {
        	year = date.substring(0,4);
        	month = date.substring(4,6);
        	day = date.substring(6,8);
        }
        
        LocalDateTime dateTime = LocalDateTime.now()
				.withYear(Integer.parseInt(year))
				.withMonth(Integer.parseInt(month))
				.withDayOfMonth(Integer.parseInt(day));
		
        return dateTime;
	}
	
	/** 
	 * LocalDateTimeのインスタンスを日付文字列を変換し、返却します。
	 * @param dateTime : LocalDateTimeのインスタンス
	 * @param format   : 日付フォーマット
	 * @return 日付文字列
	 */
	public static String format(LocalDateTime dateTime, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return dateTime.format(formatter);
		
	}
	
	/** 
	 * 日付文字列へ区切り文字を付加し、返却します。
	 * <pre>
	 * 例1) date: 20160920, delimiter: -,  return: 2016-09-20
	 * 例2) date: 20160920, delimiter: /,  return: 2016/09/20      
	 * </pre>
	 * @param date : 日付文字列(区切り文字無し)
	 * @param delimiter : 日付区切り文字
	 * @return 区切り文字付きの日付文字列
	 */
	public static String addDateStringDelimiter(String date, String delimiter) {
		StringBuffer tmpDate = new StringBuffer()
			.append(date.substring(0,4))
			.append(delimiter)
			.append(date.substring(4,6))
			.append(delimiter)
			.append(date.substring(6,8));
		
        return tmpDate.toString();
	}
}