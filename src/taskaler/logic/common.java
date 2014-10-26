/**
 * 
 */
package taskaler.logic;

import java.text.SimpleDateFormat;

/**
 * All constants used by any logic component
 * 
 * @author Weng Yuan
 *
 */
public class common {
    public static SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("HHmm-HHmm");
    public static SimpleDateFormat DEFAULT_DATETIME_FORMAT = 
            new SimpleDateFormat("dd/MM/yyyy: HHmm-HHmm");
    
    public static String ADD_NO_CONTENT = "No content is added";
    public static String EDIT_NO_CONTENT = "NO content, cannot be edited";
    public static String TASKID_NOT_EXIST = "Task ID no exist";
    public static String TASK_INITIAL_STATUS = "Not Done";
    public static String TASK_COMPLETED_STATUS = "Done";
    public static String TASK_PARAMETER_DEFAULT_VALUE = "";

    public static int TAG_TYPE_DATE = 0;
    public static int TAG_TYPE_MONTH = 1;
    public static int TAG_TYPE_YEAR = 2;
    public static int TAG_TASK_NOT_EXIST = -1;
    public static int DAYS_IN_A_WEEK = 7;
    public static int DAYS_IN_TWO_WEEK = 14;
    public static int OFF_SET_BY_ONE = 1;
    public static int DAYS_OF_ALTER = 2;

    public static int DEFAULT_TASK_ID = 0;
    public static int DEFAULT_TASK_TYPE = 0;
    public static int DEADLINE_TASK_TYPE = -1;
    public static int FLOAT_TASK_TYPE = -2;
    public static int REPEATED_TASK_TYPE = -3;
    

    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;
    
    
    public enum RepeatPattern {
        DAY, ALTER, WEEK, WEEKDAY, WEEKEND, MONTH, YEAR, 
        SUN, MON, TUES, WED, THURS, FRI, SAT, TWO_WEEK, NONE
    }
}
