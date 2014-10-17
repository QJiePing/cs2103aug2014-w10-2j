/**
 * 
 */
package taskaler.ui.model;

import java.util.ArrayList;
import java.util.Calendar;

import taskaler.common.data.Task;

/**
 * Model associated with the CalendarPane
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public class CalendarPaneModel {

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
}
