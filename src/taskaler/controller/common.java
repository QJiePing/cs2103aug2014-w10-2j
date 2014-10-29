package taskaler.controller;

import java.util.ArrayList;

/**
 * @author Brendan
 *
 * Contains all constants used in Controller
 */
public final class common {
    
    //enum for all the types of commands for Taskaler
    public enum CmdType {
        ADD, DELETE, EDIT, DEADLINE, TIME, REPEAT, WORKLOAD, 
        COMPLETION_TAG, VIEW, FIND, ARCHIVE, UNDO, GOTO, INVALID
    }
    
    // Magic Strings/Numbers
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
    public static final int TAG_LENGTH = 4;
    public static final int NUM_OF_PARAMS_INDEX = 0;
    public static final int FORMAT_INDEX = 1;
    public static final int OFFSET_OF_MONTH = 1;
    public static final int LENGTH_OF_FROM = 4;
    public static final int LENGTH_OF_TO = 2;
    public static final int LENGTH_OF_DASH = 1;
    public static final int LENGTH_OF_COLON = 1;
    public static final int JANUARY = 0;
    public static final int DECEMBER = 11;
    public static final int MONTHS_IN_A_YEAR = 12;
}
