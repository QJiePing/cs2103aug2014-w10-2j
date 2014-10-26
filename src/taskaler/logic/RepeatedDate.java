package taskaler.logic;

import java.util.ArrayList;
import java.util.Calendar;

import taskaler.logic.common.RepeatPattern;

public class RepeatedDate {
	
	public ArrayList<Calendar> repeatDays;
	
	public RepeatedDate() {
		
	}
	
    public ArrayList<Calendar> getRepeatDay(Calendar startTime, Calendar endRepeatedTime, String pattern) {
    	repeatDays = new ArrayList<Calendar>();
    	RepeatPattern repeatPattern = getPattern(pattern);
    	switch(repeatPattern) {
    	case DAY:
    		repeatDays = computeDaily((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case SUN:
    		repeatDays = computeSun((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case MON:
    		repeatDays = computeMon((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case TUES:
    		repeatDays = computeTues((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case WED:
    		repeatDays = computeWed((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case THURS:
    		repeatDays = computeThurs((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case FRI:
    		repeatDays = computeFri((Calendar) startTime.clone(), endRepeatedTime);
    		break;
    	case SAT:
    		repeatDays = computeSat((Calendar) startTime.clone(), endRepeatedTime);
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
 
    	}
		return repeatDays;
	}

    
    private ArrayList<Calendar> computeSun(Calendar startTime,
			Calendar endRepeatedTime) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		int day = newDay.get(Calendar.DAY_OF_WEEK);
    		if (day == Calendar.SUNDAY) {
    			repeatDays.add(newDay);
    			startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_A_WEEK);
    		} else {
    			startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    		}
			
    	}
		return repeatDays;
	}
    
    private ArrayList<Calendar> computeMon(Calendar startTime,
			Calendar endRepeatedTime) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		int day = newDay.get(Calendar.DAY_OF_WEEK);
    		if (day == Calendar.MONDAY) {
    			repeatDays.add(newDay);
    			startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_A_WEEK);
    		} else {
    			startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    		}
			
    	}
		return repeatDays;
	}

    private ArrayList<Calendar> computeTues(Calendar startTime,
			Calendar endRepeatedTime) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		int day = newDay.get(Calendar.DAY_OF_WEEK);
    		if (day == Calendar.TUESDAY) {
    			repeatDays.add(newDay);
    			startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_A_WEEK);
    		} else {
    			startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    		}
			
    	}
		return repeatDays;
	}
    
    private ArrayList<Calendar> computeWed(Calendar startTime,
			Calendar endRepeatedTime) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		int day = newDay.get(Calendar.DAY_OF_WEEK);
    		if (day == Calendar.WEDNESDAY) {
    			repeatDays.add(newDay);
    			startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_A_WEEK);
    		} else {
    			startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    		}
			
    	}
		return repeatDays;
	}

    private ArrayList<Calendar> computeThurs(Calendar startTime,
			Calendar endRepeatedTime) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		int day = newDay.get(Calendar.DAY_OF_WEEK);
    		if (day == Calendar.THURSDAY) {
    			repeatDays.add(newDay);
    			startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_A_WEEK);
    		} else {
    			startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    		}
			
    	}
		return repeatDays;
	}
    
    private ArrayList<Calendar> computeFri(Calendar startTime,
			Calendar endRepeatedTime) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		int day = newDay.get(Calendar.DAY_OF_WEEK);
    		if (day == Calendar.FRIDAY) {
    			repeatDays.add(newDay);
    			startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_A_WEEK);
    		} else {
    			startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    		}
			
    	}
		return repeatDays;
	}
    
    private ArrayList<Calendar> computeSat(Calendar startTime,
			Calendar endRepeatedTime) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		int day = newDay.get(Calendar.DAY_OF_WEEK);
    		if (day == Calendar.SATURDAY) {
    			repeatDays.add(newDay);
    			startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_A_WEEK);
    		} else {
    			startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    		}
			
    	}
		return repeatDays;
	}
    
	private ArrayList<Calendar> computeTwoWeeks(Calendar startTime, Calendar endRepeatedTime) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_TWO_WEEK);
    	}
		return repeatDays;
	}


	private ArrayList<Calendar> computeYearly(Calendar startTime, Calendar endRepeatedTime) {
    	ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.YEAR, common.OFF_SET_BY_ONE);
    	}
		return repeatDays;
	}

	private ArrayList<Calendar> computeMonthly(Calendar startTime, Calendar endRepeatedTime) {
		ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.MONTH, common.OFF_SET_BY_ONE);
    	}
		return repeatDays;
	}

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

	private ArrayList<Calendar> computeWeekly(Calendar startTime, Calendar endRepeatedTime) {
		ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_IN_A_WEEK);
    	}
		return repeatDays;
	}

	private ArrayList<Calendar> computeAlter(Calendar startTime, Calendar endRepeatedTime) {
		ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.DAY_OF_MONTH, common.DAYS_OF_ALTER);
    	}
		return repeatDays;
	}

	private ArrayList<Calendar> computeDaily(Calendar startTime, Calendar endRepeatedTime) {
		ArrayList<Calendar> repeatDays = new ArrayList<Calendar>();
    	while(startTime.compareTo(endRepeatedTime) <= 0) {
    		Calendar newDay = (Calendar) startTime.clone();
    		repeatDays.add(newDay);
            startTime.add(Calendar.DAY_OF_MONTH, common.OFF_SET_BY_ONE);
    	}
		return repeatDays;
	}

	
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
    	} else {
    		repeatPattern = RepeatPattern.NONE;
    	}
    	
		return repeatPattern;
	}
}
