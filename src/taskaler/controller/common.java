//@author A0108541M

package taskaler.controller;

/**
 * @author Brendan
 *
 *         Contains all constants used in Controller
 */
public final class common {

    // enum for all the types of commands for Taskaler
    public enum CmdType {
        ADD, DELETE, EDIT, DEADLINE, TIME, REPEAT, WORKLOAD, TODAY, 
        COMPLETION_TAG, VIEW, FIND, ARCHIVE, UNDO, GOTO, EXIT, INVALID
    }

    // Messages
    public static final String MSG_WELCOME = "Welcome to Taskaler!";
    public static final String MSG_DELETED = "The Task \"%s\" has Been Deleted";
    public static final String MSG_DELETE_ALL = "All tasks have been deleted";
    public static final String MSG_VIEW_DATE = "All Tasks on \"%s\"";
    public static final String MSG_VIEW_UNDO = "Actions Last Taken";
    public static final String MSG_HISTORY = "History";
    public static final String MSG_FIND = "Search Result for %s, %s";
    public static final String MSG_UNDO = "The last operation has been undone";
    public static final String MSG_TODAY = "All Tasks for Today: ";
    public static final String MSG_OVERDUE = "Tasks Which are Overdue: ";

    // Exception Messages
    public static final String EXCEPTION_INVALID_ADD = "Invalid ADD parameters";
    public static final String EXCEPTION_INVALID_COMMAND = "Invalid command entered!";
    public static final String EXCEPTION_INVALID_TASKID = "Invalid task ID";
    public static final String EXCEPTION_INVALID_DATE = "Invalid date syntax, try: <dd/mm/yyyy>";
    public static final String EXCEPTION_INVALID_TIME = "Invalid time syntax, try: HHmm";
    public static final String EXCEPTION_INVALID_RANGE = 
            "Invalid %s range syntax, try: <start %s> - <end %s>";
    public static final String EXCEPTION_INVALID_WORKLOAD = 
            "Invalid workload attribute syntax, try: <1 or 2 or 3>";
    public static final String EXCEPTION_INVALID_PATTERN = 
            "Invalid pattern syntax, try: wednesday, or weekly, or last";
    public static final String EXCEPTION_INVALID_GOTO = "Invalid goto syntax, try: goto <MM/YYYY>";
    public static final String EXCEPTION_INVALID_BOOLEAN = 
            "Invalid boolean attribute syntax, try: true or false";
    public static final String ERROR_UNEXPECTED = "Unknown error occurred";

    // Magic Numbers
    public static final int INVALID_VALUE = -1;
    public static final int MAX_ADD_PARAMETERS = 6;
    public static final int DELETE_PARAMETERS = 1;
    public static final int MAX_EDIT_PARAMETERS = 3;
    public static final int DEADLINE_PARAMETERS = 2;
    public static final int TIME_PARAMETERS = 3;
    public static final int MAX_REPEAT_PARAMETERS = 4;
    public static final int WORKLOAD_PARAMETERS = 2;
    public static final int COMPLETION_TAG_PARAMETERS = 1;
    public static final int VIEW_PARAMETERS = 2;
    public static final int FIND_PARAMETERS = 2;
    public static final int ARCHIVE_PARAMETERS = 1;
    public static final int GOTO_PARAMETERS = 1;
    public static final int TODAY_PARAMETERS = 1;
    public static final int TAG_LENGTH = 4;
    public static final int NUM_OF_PARAMS_INDEX = 0;
    public static final int FORMAT_INDEX = 1;
    public static final int OFFSET_OF_MONTH = 1;
    public static final int LENGTH_OF_FROM = 4;
    public static final int LENGTH_OF_TO = 2;
    public static final int LENGTH_OF_SYMBOL = 1;
    public static final int JANUARY = 0;
    public static final int DECEMBER = 11;
    public static final int MONTHS_IN_A_YEAR = 12;
    public static final int MONTH_OVERFLOW_VALUE = 13;
    public static final int MONTH_INVALID_VALUE = 0;
}
