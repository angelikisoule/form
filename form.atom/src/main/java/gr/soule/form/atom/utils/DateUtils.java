package gr.media24.mSites.atom.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author npapadopoulos
 */
public class DateUtils {
	
	/**
	 * Parse Date Strings Read From Atom Feeds And Return The Calendar Representation
	 * @param date String Representation Of Date
	 * @return Calendar Representation Of Date, Or Epoch Time In Case Of An Exception
	 */
	public static Calendar stringToCalendar(String date) {
		
		SimpleDateFormat formats[] = new SimpleDateFormat[] { new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") };
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(0); //Epoch In Case Of An Exception
		
		try {
			date = date.trim();
			calendar.setTime(formats[0].parse(date));
		}
		catch(ParseException exception) {
			//TODO You Can Add More Date Formats Here
		}
		catch(NullPointerException exception) { //Do Nothing
		
		}
		
		return calendar;
	}
	
	/**
	 * Date To Calendar Conversion
	 * @param date Date
	 * @return Calendar
	 */
	public static Calendar dateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	/**
	 * String Format Of Given Calendar Object
	 * @param calendar Calendar
	 * @return String Representation Of Calendar Object
	 */
	public static String formatCalendar(Calendar calendar) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(calendar.getTime());
	}
	
	/**
	 * Infrequently The Atom Parser Reads Not Valid Dates For Articles (i.e. '5300-09-08 17:56:30').
	 * To Overcome This Problem Check If The Date Read From The Parser Is Within A Valid Date Range
	 * @param date Date
	 * @return true or false
	 */
	public static boolean inRange(String dateString) {
		if(dateString == null) { //null Dates Are Allowed
			return true;
		}
		else {
			Calendar minimum = Calendar.getInstance();
			minimum.add(Calendar.YEAR, -10);
	
			Calendar maximum = Calendar.getInstance();
			maximum.add(Calendar.YEAR, +1);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setLenient(false);
			
			try {
				Date date = dateFormat.parse(dateString);
				if(date.after(minimum.getTime()) && date.before(maximum.getTime())) { //Date In Range
					return true;
				}
				else {
					return false;
				}
			} 
			catch(ParseException exception) {
				return false;
			}
		}
	}
}