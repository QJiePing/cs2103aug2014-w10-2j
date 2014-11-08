package taskaler.logic;

import java.util.ArrayList;
import java.util.Calendar;

import taskaler.logic.common.RepeatPattern;

//@author A0099778X
public class RepeatedDate {
	
	public ArrayList<Calendar> repeatDays;
	
	public RepeatedDate() {
		
	}
	
	/**
	 * getRepeatDay(Calendar startTime, Calendar endRepeatedTime, String pattern) is to
	 * compute all the dates for repeated task from startTime to endRepeatedTime with
	 * the given pattern
	 * 
	 * @param startTime
	 * @param endRepeatedTime
	 * @param pattern
	 * @return return ArrayList<Calendar> with all repeated dates for RepeatedTask object
	 */
    public ArrayList<Calendar> getRepeatDay(Calendar startTime, Calendar endRepeatedTime, String pattern) {
    	repeatDays = new ArrayList<Calendar>();
    	RepeatPattern repeatPattern = getPattern(pattern);
    	switch(repeatPattern) {
    	case DAY:
    		repeatDays = computeDaily((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case SUN:
    		repeatDays = computeSpecifyDay((Calendar) startTime.clone(), endRepeatedTime, common.SUNDAY);
    		break;
    	case MON:
    		repeatDays = computeSpecifyDay((Calendar) startTime.clone(), endRepeatedTime, common.MONDAY);
    		break;
    	case TUES:
    		repeatDays = computeSpecifyDay((Calendar) startTime.clone(), endRepeatedTime, common.TUESDAY);
    		break;
    	case WED:
    		repeatDays = computeSpecifyDay((Calendar) startTime.clone(), endRepeatedTime, common.WEDNESDAY);
    		break;
    	case THURS:
    		repeatDays = computeSpecifyDay((Calendar) startTime.clone(), endRepeatedTime, common.THURSDAY);
    		break;
    	case FRI:
    		repeatDays = computeSpecifyDay((Calendar) startTime.clone(), endRepeatedTime, common.FRIDAY);
    		break;
    	case SAT:
    		repeatDays = computeSpecifyDay((Calendar) startTime.clone(), endRepeatedTime, common.SATURDAY);
    		break;
    	case ALTER:
    		repeatDays = computeAlter((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case WEEK:
    		repeatDays = computeWeekly((Calendar) startTime.clone(), endRepeatedTime);
    		break;
		case TWO_WEEK:
			repeatDays = computeTwoWeeks((Calendar) startTime.clone(),endRepeatedTime);
			break;
    	case WEEKDAY:
    		repeatDays = computeWeekday((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case WEEKEND:
    		repeatDays = computeWeekend((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case MONTH:
    		repeatDays = computeMonthly((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case YEAR:
    		repeatDays = computeYearly((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case LAST:
            repeatDays = computeLast((Calendar) startTime.clone(), endRepeatedTime);
            break;
    	}
		return repeatDays;
	}

    
    /**
	 * computeSpecifyDay(Calendar startTime, Calendar endRepeatedTime, int pattern)
	 * is to compute all the dates for repeated task with pattern: repeat on Mon
	 * or Tues or Wed or Thur or Fri or Sat or Sun
	 * 
	 * @param startTime
	 * @param endRepeatedTime
	 * @param pattern
	 * @return return ArrayList<Calendar> with all repeated dates for RepeatedTask object
	 */
    private ArrayList<Calendar> computeSpecifyDay(Calendar startTime,
			Calendar endRepeatedTime, int pattern) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		int day = newDay.get(Calendar.DAY_OF_WEEK);
    		if (day == pattern) {
    			repeatDays.add(newDay);
    			startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_A_WEEK);
    		} else {
    			startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    		}
			
    	}
		return repeatDays;
	}
    
    /**
     * computeTwoWeeks(Calendar startTime, Calendar endRepeatedTime) is to
     * compute all the dates for repeated task with pattern: repeat once for two weeks
     * 
     * @param startTime
     * @param endRepeatedTime
     * @return return ArrayList<Calendar> with all repeated dates for RepeatedTask object
     */
	private ArrayList<Calendar> computeTwoWeeks(Calendar startTime, Calendar endRepeatedTime) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_TWO_WEEK);
    	}
		return repeatDays;
	}


	/**
	 * computeYearly(Calendar startTime, Calendar endRepeatedTime) is to 
     * compute all the dates for repeated task with pattern: repeat once per year
	 * 
	 * @param startTime
	 * @param endRepeatedTime
	 * @return return ArrayList<Calendar> with all repeated dates for RepeatedTask object
	 */
	private ArrayList<Calendar> computeYearly(Calendar startTime, Calendar endRepeatedTime) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.YEAR, common.OFF_SET_BY_ONE);
    	}
		return repeatDays;
	}

	/**
	 * computeMonthly(Calendar startTime, Calendar endRepeatedTime) is to
	 * compute all the dates for repeated task with pattern: repeat once per month
	 * 
	 * @param startTime
	 * @param endRepeatedTime
	 * @return return ArrayList<Calendar> with all repeated dates for RepeatedTask object
	 */
	private ArrayList<Calendar> computeMonthly(Calendar startTime, Calendar endRepeatedTime) {
		ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.MONTH, common.OFF_SET_BY_ONE);
    	}
		return repeatDays;
	}

	/**
	 * computeWeekend(Calendar startTime, Calendar endRepeatedTime) is to
	 * compute all the dates for repeated task with pattern: repeat only on weekend
	 * 
	 * @param startTime
	 * @param endRepeatedTime
	 * @return return ArrayList<Calendar> with all repeated dates for RepeatedTask object
	 */
	private ArrayList<Calendar> computeWeekend(Calendar startTime, Calendar endRepeatedTime) {
		ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		int day = newDay.get(Calendar.DAY_OF_WEEK);
    		if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) {
    			repeatDays.add(newDay);
    		}
			startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    	}
		return repeatDays;
	}

	/**
	 * computeWeekday(Calendar startTime, Calendar endRepeatedTime) is to
	 * compute all the dates for repeated task with pattern: repeat on weekday
	 * 
	 * @param startTime
	 * @param endRepeatedTime
	 * @return return ArrayList<Calendar> with all repeated dates for RepeatedTask object
	 */
	private ArrayList<Calendar> computeWeekday(Calendar startTime, Calendar endRepeatedTime) {
		ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		int day = newDay.get(Calendar.DAY_OF_WEEK);
    		if (!(day == Calendar.SATURDAY || day == Calendar.SUNDAY)) {
    			repeatDays.add(newDay);
    		}
			startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    	}
		return repeatDays;
	}

	/**
	 * computeWeekly(Calendar startTime, Calendar endRepeatedTime) is to
	 * compute all the dates for repeated task with pattern: repeat once per week
	 * 
	 * @param startTime
	 * @param endRepeatedTime
	 * @return return ArrayList<Calendar> with all repeated dates for RepeatedTask object
	 */
	private ArrayList<Calendar> computeWeekly(Calendar startTime, Calendar endRepeatedTime) {
		ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_A_WEEK);
    	}
		return repeatDays;
	}

	
	/**
	 * computeAlter(Calendar startTime, Calendar endRepeatedTime) is to
	 * compute all the dates for repeated task with pattern: repeat once for two days
	 * 
	 * @param startTime
	 * @param endRepeatedTime
	 * @return return ArrayList<Calendar> with all repeated dates for RepeatedTask object
	 */
	private ArrayList<Calendar> computeAlter(Calendar startTime, Calendar endRepeatedTime) {
		ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_OF_ALTER);
    	}
		return repeatDays;
	}

	
	/**
	 * computeDaily(Calendar startTime, Calendar endRepeatedTime) is to
	 * compute all the dates for repeated task with pattern: repeat daily
	 * 
	 * @param startTime
	 * @param endRepeatedTime
	 * @return return ArrayList<Calendar> with all repeated dates for RepeatedTask object
	 */
	private ArrayList<Calendar> computeDaily(Calendar startTime, Calendar endRepeatedTime) {
		ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    	}
		return repeatDays;
	}

	private ArrayList<Calendar> computeLast(Calendar startTime, Calendar endRepeatedTime) {
	    ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
        while(startTime.before(endRepeatedTime)) {
            int lastDate = startTime.getActualMaximum(Calendar.DATE);
            Calendar newDay = (Calendar) startTime.clone();
            newDay.set(Calendar.DATE, lastDate);
            repeatDays.add(newDay);
            startTime.add(Calendar.MONTH, common.OFF_SET_BY_ONE);
        }
        return repeatDays;
    }
	/**
	 * getPattern(String pattern) convert the given pattern to enum type
	 *  
	 * @param pattern
	 * @return
	 */
	public RepeatPattern getPattern(String pattern) {
    	pattern = pattern.toUpperCase();
		RepeatPattern repeatPattern = RepeatPattern.NONE;
    	if(pattern.equals("1 DAY")) {
    		repeatPattern = RepeatPattern.DAY;
    	} else if (pattern.equals("2 DAY")) {
    		repeatPattern = RepeatPattern.ALTER;
    	} else if (pattern.equals("1 WEEK")) {
    		repeatPattern = RepeatPattern.WEEK;
    	} else if (pattern.equals("2 WEEK")) {
    		repeatPattern = RepeatPattern.TWO_WEEK;
    	} else if (pattern.equals("WEEKDAY")) {
    		repeatPattern = RepeatPattern.WEEKDAY;
    	} else if (pattern.equals("WEEKEND")) {
    		repeatPattern = RepeatPattern.WEEKEND;
    	} else if (pattern.equals("1 MONTH")) {
    		repeatPattern = RepeatPattern.MONTH;
    	} else if (pattern.equals("1 YEAR")) {
    		repeatPattern = RepeatPattern.YEAR;
    	} else if (pattern.equals("1 DAYOFWEEK")) {
        	repeatPattern = RepeatPattern.SUN;
    	} else if (pattern.equals("2 DAYOFWEEK")) {
        	repeatPattern = RepeatPattern.MON;
    	} else if (pattern.equals("3 DAYOFWEEK")) {
        	repeatPattern = RepeatPattern.TUES;
    	} else if (pattern.equals("4 DAYOFWEEK")) {
        	repeatPattern = RepeatPattern.WED;
    	} else if (pattern.equals("5 DAYOFWEEK")) {
        	repeatPattern = RepeatPattern.THURS;
    	} else if (pattern.equals("6 DAYOFWEEK")) {
        	repeatPattern = RepeatPattern.FRI;
    	} else if (pattern.equals("7 DAYOFWEEK")) {
        	repeatPattern = RepeatPattern.SAT;
    	} else if (pattern.equals("LAST")){
    	    repeatPattern = RepeatPattern.LAST;
	    } else {
    		repeatPattern = RepeatPattern.NONE;
    	}
    	
		return repeatPattern;
	}
}
