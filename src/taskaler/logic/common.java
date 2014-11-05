//@author A0099778X

package taskaler.logic;

import java.text.SimpleDateFormat;

/**
 * All constants used by any logic component

 */
public class common {
    
    public static String ADD_NO_CONTENT = "No content is added";
    public static String EDIT_NO_CONTENT = "NO content, cannot be edited";
    public static String TASKID_NOT_EXIST = "Task ID no exist";
    public static String TASK_PARAMETER_DEFAULT_VALUE = "";
    public static String TAG_TRUE = "true";
    public static String TAG_TIME = "time";
    public static String TAG_DATE = "date";
    
    public static String NOTIFY_TYPE_ADD = "ADD";
    public static String NOTIFY_TYPE_EDIT = "EDIT";
    public static String NOTIFY_TYPE_DELETE = "DELETE";
    public static String NOTIFY_TYPE_UNDO = "UNDO";
    public static String NOTIFY_TYPE_DATE = "DATE";
    public static String NOTIFY_TYPE_TIME = "TIME";
    public static String NOTIFY_TYPE_REPEAT = "REPEAT";
    public static String NOTIFY_TYPE_WORKLOAD = "WORKLOAD";
    public static String NOTIFY_TYPE_COMPLETE = "COMPLETE";

    public static int TAG_TYPE_DATE = 0;
    public static int TAG_TYPE_MONTH = 1;
    public static int TAG_TYPE_YEAR = 2;
    public static int TAG_TASK_NOT_EXIST = -1;
    public static int DAYS_IN_A_WEEK = 7;
    public static int DAYS_IN_TWO_WEEK = 14;
    public static int OFF_SET_BY_ONE = 1;
    public static int DAYS_OF_ALTER = 2;

    public static int DEFAULT_TASK_ID = 0;
    public static int DEFAULT_COLLECTION_ID = 1;
    public static int DEFAULT_TASK_TYPE = 0;
    public static int DEADLINE_TASK_TYPE = -1;
    public static int FLOAT_TASK_TYPE = -2;
    public static int REPEATED_TASK_TYPE = -3;
    public static int DEFAULT_NUM_OF_INCOMPLETE = 0;

    public static final int SUNDAY = 1;
    public static final int MONDAY = 2;
    public static final int TUESDAY = 3;
    public static final int WEDNESDAY = 4;
    public static final int THURSDAY = 5;
    public static final int FRIDAY = 6;
    public static final int SATURDAY = 7;
    

    public static boolean TASK_INITIAL_STATUS = false;
    public static boolean TASK_COMPLETED_STATUS = true;
    
    
    public enum RepeatPattern {
        DAY, ALTER, WEEK, WEEKDAY, WEEKEND, MONTH, YEAR, 
        SUN, MON, TUES, WED, THURS, FRI, SAT, TWO_WEEK, LAST, NONE
    }
}
