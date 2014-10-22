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
        ADD, DELETE, EDIT, DATE, WORKLOAD, COMPLETION_TAG, VIEW, FIND, ARCHIVE, UNDO, GOTO, INVALID
    }
 
    //Array for all the types of commands in String
    public static final String[] commandList = 
        {"ADD", "DELETE", "EDIT", "DATE", "WORKLOAD", "COMPLETION_TAG", 
        "VIEW", "FIND", "UNDO", "GOTO", "INVALID"};
    
    
    // Magic Strings/Numbers
    public static final int INVALID_VALUE = -1;
    public static final int MAX_ADD_PARAMETERS = 2;
    public static final int MAX_EDIT_PARAMETERS = 3;
    public static final int MAX_DATE_PARAMETERS = 4;
    public static final int WORKLOAD_PARAMETERS = 2;
    public static final int VIEW_PARAMETERS = 2;
    public static final int FIND_PARAMETERS = 2;
    public static final int ARCHIVE_PARAMETERS = 1;
    public static final int GOTO_PARAMETERS = 1;
    public static final int TAG_LENGTH = 4;
}
