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
    public static final ArrayList<String> commandList = new ArrayList<String>() 
        {{  add("ADD");
            add("DELETE");
            add("EDIT");
            add("DATE");
            add("WORKLOAD");
            add("COMPLETION_TAG");
            add("VIEW");
            add("FIND");
            add("ARCHIVE");
            add("UNDO");
            add("GOTO");
            add("INVALID");
        }};
    
    public static final ArrayList<String[]> availableDateFormats = new ArrayList<String[]>()
            {{  add(new String[]{"3", "d M y"});
                add(new String[]{"3","d MMM y"});
                add(new String[]{"3","d/M/y"});
                add(new String[]{"3","d/MMM/y"});
                add(new String[]{"3","d.M.y"});
                add(new String[]{"3","d.MMM.y"});
                add(new String[]{"3","d-M-y"});
                add(new String[]{"3","d-MMM-y"});
                add(new String[]{"2","d M"});
                add(new String[]{"2","d MMM"});
                add(new String[]{"2","d/M"});
                add(new String[]{"2","d/MMM"});
                add(new String[]{"2","d.M"});
                add(new String[]{"2","d.MMM"});
                add(new String[]{"2","d-M"});
                add(new String[]{"2","d-MMM"});
                add(new String[]{"1","d"});
            }};
    
    // Magic Strings/Numbers
    public static final int INVALID_VALUE = -1;
    public static final int MAX_ADD_PARAMETERS = 2;
    public static final int DELETE_PARAMETERS = 1;
    public static final int MAX_EDIT_PARAMETERS = 3;
    public static final int DATE_PARAMETERS = 2;
    public static final int WORKLOAD_PARAMETERS = 2;
    public static final int VIEW_PARAMETERS = 2;
    public static final int FIND_PARAMETERS = 2;
    public static final int ARCHIVE_PARAMETERS = 1;
    public static final int GOTO_PARAMETERS = 1;
    public static final int TAG_LENGTH = 4;
    public static final int NUM_OF_PARAMS_INDEX = 0;
    public static final int FORMAT_INDEX = 1;
    public static final int OFFSET_OF_MONTH = 1;
}
