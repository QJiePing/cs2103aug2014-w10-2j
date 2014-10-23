package taskaler.controller.parser;

import static taskaler.controller.common.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public final class ParserLibrary {
    public static final HashMap<String, CmdType> commandList = new HashMap<String, CmdType>()
            {{  put("add", CmdType.ADD);
                put("put", CmdType.ADD);
                put("delete", CmdType.DELETE);
                put("remove", CmdType.DELETE);
                put("clear", CmdType.DELETE);
                put("edit", CmdType.EDIT);
                put("date", CmdType.DATE);
                put("deadline", CmdType.DATE);
                put("time", CmdType.DATE);
                put("workload", CmdType.WORKLOAD);
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
            }};
            
    public static String[] getCommands(){
        Set<String> commandSet = commandList.keySet();
        String[] array = new String[commandSet.size()];
        return commandSet.toArray(array);
    }
    
    public static final ArrayList<String[]> availableDateFormats = new ArrayList<String[]>()
            {{  add(new String[]{"3","d/M/y"});
                add(new String[]{"3","d/MMM/y"});
                add(new String[]{"3","d.M.y"});
                add(new String[]{"3","d.MMM.y"});
                add(new String[]{"3","d-M-y"});
                add(new String[]{"3","d-MMM-y"});
                add(new String[]{"2","d/M"});
                add(new String[]{"2","d/MMM"});
                add(new String[]{"2","d.M"});
                add(new String[]{"2","d.MMM"});
                add(new String[]{"2","d-M"});
                add(new String[]{"2","d-MMM"});
                add(new String[]{"1","d"});
            }};
            
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
    
}

