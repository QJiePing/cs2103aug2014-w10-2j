/**
 * 
 */
package taskaler.ui.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import taskaler.common.data.Task;
import taskaler.common.util.parser.calendarToString;
import taskaler.ui.controller.common;

/**
 * Model associated with the CalendarPane
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
//@author A0059806W
public class CalendarPaneModel implements IModel{

    // State Attributes
    public static final String CURRENT_CALENDAR_ATTRIBUTE = "CURRENTCALENDAR";
    public static final String CURRENT_YEAR_ATTRIBUTE = "CURRENTYEAR";
    public static final String CURRENT_MONTH_ATTRIBUTE = "CURRENTMONTH";
    public static final String CURRENT_TASKLIST_ATTRIBUTE = "CURRENTTASKLIST";

    public ArrayList<Task> currentTaskList;

    public int currentYear;

    public int currentMonth;

    public Calendar currentCalendar;

    public CalendarPaneModel() {
        currentTaskList = new ArrayList<Task>();
        currentYear = 0;
        currentMonth = 0;
        currentCalendar = Calendar.getInstance();
    }

    @Override
    public HashMap<String, String> toHashMap() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put(VIEW_ATTRIBUTE, VIEW_CALENDAR_PANE);
        result.put(CURRENT_TASKLIST_ATTRIBUTE, currentTaskList.toString());
        result.put(CURRENT_MONTH_ATTRIBUTE, currentMonth + common.EMPTY_STRING);
        result.put(CURRENT_YEAR_ATTRIBUTE, currentYear + common.EMPTY_STRING);
        result.put(CURRENT_CALENDAR_ATTRIBUTE, calendarToString.parseDate(currentCalendar));
        return result;
    }
}
