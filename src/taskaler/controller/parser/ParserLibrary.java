/**
 * @author Brendan Yong, A0108541M
 *
 */

package taskaler.controller.parser;

import static taskaler.controller.common.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public final class ParserLibrary {
    
    /**
     * Contains the mappings of the command word to the command type
     */
    public static final HashMap<String, CmdType> commandList = new HashMap<String, CmdType>()
            {{  put("add", CmdType.ADD);
                put("put", CmdType.ADD);
                put("delete", CmdType.DELETE);
                put("remove", CmdType.DELETE);
                put("clear", CmdType.DELETE);
                put("edit", CmdType.EDIT);
                put("date", CmdType.DEADLINE);
                put("deadline", CmdType.DEADLINE);
                put("time", CmdType.TIME);
                put("setTime", CmdType.TIME);
                put("repeat", CmdType.REPEAT);
                put("setRepeat", CmdType.REPEAT);
                put("workload", CmdType.WORKLOAD);
                put("wl", CmdType.WORKLOAD);
                put("priority", CmdType.WORKLOAD);
                put("completed", CmdType.COMPLETION_TAG);
                put("done", CmdType.COMPLETION_TAG);
                put("view", CmdType.VIEW);
                put("find", CmdType.FIND);
                put("search", CmdType.FIND);
                put("arch", CmdType.ARCHIVE);
                put("archive", CmdType.ARCHIVE);
                put("history", CmdType.ARCHIVE);
                put("undo", CmdType.UNDO);
                put("next", CmdType.GOTO);
                put("back", CmdType.GOTO);
                put("goto", CmdType.GOTO);
                put("exit", CmdType.EXIT);
            }};
            
    /**
     * Returns the list of available command words (keys of the commandList)
     * 
     * @return String[] command words
     */
    public static String[] getCommands(){
        Set<String> commandSet = commandList.keySet();
        String[] array = new String[commandSet.size()];
        return commandSet.toArray(array);
    }
    
    /**
     * Contains the available variations of date formats for user input, and its number of arguments 
     */
    public static final ArrayList<String[]> availableDateSyntax = new ArrayList<String[]>()
            {{  add(new String[]{"3","d/M/y"});
                add(new String[]{"3","d/MMM/y"});
                add(new String[]{"3","d.M.y"});
                add(new String[]{"3","d.MMM.y"});
                add(new String[]{"3","d-M-y"});
                add(new String[]{"3","d-MMM-y"});
                add(new String[]{"2","d-MMM"});
                add(new String[]{"2","d/MMM"});
                add(new String[]{"2","d.MMM"});
                add(new String[]{"2","d/M"});
                add(new String[]{"2","d.M"});
                add(new String[]{"2","d-M"});
                add(new String[]{"1","d"});
            }};
            
    /**
     * Contains the available variations of time formats for user input
     */
    public static final ArrayList<String> availableTimeSyntax = new ArrayList<String>()
            {{  add("hhmmaa");
                add("hh:mmaa");
                add("hh.mmaa");
                add("hhmm aa");
                add("hh:mm aa");
                add("hh.mm aa");
                add("HHmm");
                add("HH:mm");
                add("HH.mm");
                add("hhaa");
                add("hhaa");
                add("hh aa");
                add("HH");
            }};
    
    /**
     * Contains the available variations of time formats for user input
     */            
    public static final HashMap<String, String> availableWorkloadSyntax = new HashMap<String, String>()
            {{  put("high", "3");
                put("low", "1");
                put("medium", "2");
                put("med", "2");
                put("1", "1");
                put("2", "2");
                put("3", "3");
                put("0", "0");
                put("one", "1");
                put("two", "2");
                put("three", "3");
                put("zero", "0");
                put("none", "0");
            }};
    
    /**
     * Contains the available variations of "repeat pattern" formats for user input
     */
    public static final HashMap<String, String> availablePatternSyntax = new HashMap<String, String>()
            {{  put("daily", "1 DAY");
                put("everyday", "1 DAY");
                put("weekly", "1 WEEK");
                put("monthly", "1 MONTH");
                put("yearly", "1 YEAR");
                put("annually", "1 YEAR");
                put("fortnightly", "2 WEEK");
                put("alternate", "2 DAY");
                put("weekends", "WEEKEND");
                put("weekend", "WEEKEND");
                put("weekday", "WEEKDAY");
                put("weekdays", "WEEKDAY");
                put("last", "LAST");
                put("final", "LAST");
                put("sunday", "1 DAYOFWEEK");
                put("monday", "2 DAYOFWEEK");
                put("tuesday", "3 DAYOFWEEK");
                put("wednesday", "4 DAYOFWEEK");
                put("thursday", "5 DAYOFWEEK");
                put("friday", "6 DAYOFWEEK");
                put("saturday", "7 DAYOFWEEK");
                put("sundays", "1 DAYOFWEEK");
                put("mondays", "2 DAYOFWEEK");
                put("tuesdays", "3 DAYOFWEEK");
                put("wednesdays", "4 DAYOFWEEK");
                put("thursdays", "5 DAYOFWEEK");
                put("fridays", "6 DAYOFWEEK");
                put("saturdays", "7 DAYOFWEEK");
                put("sun", "1 DAYOFWEEK");
                put("mon", "2 DAYOFWEEK");
                put("tue", "3 DAYOFWEEK");
                put("wed", "4 DAYOFWEEK");
                put("thu", "5 DAYOFWEEK");
                put("fri", "6 DAYOFWEEK");
                put("sat", "7 DAYOFWEEK");
            }};
}

