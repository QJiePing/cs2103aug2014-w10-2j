package taskaler.controller;

public class Parser {
    
    public enum CmdType {
        ADD, DELETE, EDIT, DATE, WORKLOAD, COMPLETION_TAG, VIEW, FIND, ARCHIVE, UNDO, INVALID
    }
    
    // Magic Strings/Numbers
    private static final int INVALID_VALUE = -1;
    private static final int MAX_ADD_PARAMETERS = 2;
    private static final int MAX_EDIT_PARAMETERS = 3;
    private static final int MAX_DATE_PARAMETERS = 4;
    private static final int WORKLOAD_PARAMETERS = 2;
    private static final int VIEW_PARAMETERS = 2;
    private static final int FIND_PARAMETERS = 2;
    private static final int TAG_LENGTH = 4;
    
    //Variables
    private CmdType command;
    private String[] parameters;
    
    /**
     * @param commandString
     * 
     * Contructor for Parser
     */
    public Parser(String commandString) throws Exception{
        parseCMD(commandString);
    }
    
    public CmdType getCommand(){
        return command;
    }
    
    public String[] getParameters(){
        return parameters;
    }
    /**
     * @param commandString
     * 
     * Public function to parse the command string
     */
    private void parseCMD(String commandString) throws Exception{
        String CMD = getFirstWord(commandString);
        command = determineCMD_TYPE(CMD);
        parameters = getParams(command, commandString);
    }
    
    /**
     * 
     * Method to differentiate between CMD_TYPE(s) when given the user command 
     * 
     * @param command
     * @return CMD_TYPE command type
     */
    private CmdType determineCMD_TYPE(String command) {
        switch (command.toLowerCase()) {
        case "add":
            return CmdType.ADD;
        case "delete":
            return CmdType.DELETE;
        case "edit":
            return CmdType.EDIT;
        case "date":
            return CmdType.DATE;
        case "workload":
            return CmdType.WORKLOAD;
        case "completed":
            return CmdType.COMPLETION_TAG;
        case "view":
            return CmdType.VIEW;
        case "find":
            return CmdType.FIND;
        case "arch":
            return CmdType.ARCHIVE;
        case "undo":
            return CmdType.UNDO;
        default:
            return CmdType.INVALID;
        }
    }
    
    /**
     * 
     * Method to get the parameters for each command, differentiated by CMD_TYPE
     * 
     * @param commandType
     * @param commandString
     * @return String[] parameters
     */
    private static String[] getParams(CmdType commandType, String commandString) throws Exception{
        switch(commandType){
        case ADD:
            return getParam_ADD(commandString);
        case DELETE:
            String TaskID_DEL = getTaskID(commandString);
            return new String[]{TaskID_DEL};
        case EDIT:
            return getParam_EDIT(commandString);
        case DATE:
            return getParam_DATE(commandString);
        case WORKLOAD:
            return getParam_WL(commandString);
        case COMPLETION_TAG:
            String TaskID_CT = getTaskID(commandString);
            return new String[]{TaskID_CT};
        case VIEW:
            return getParam_VIEW(commandString);
        case FIND:
            return getParam_FIND(commandString);
        case ARCHIVE:
            return getParam_ARCH(commandString);
        default:
            return null;
        }
    }
    
    /**
     * 
     * Method to retrieve parameters for ADD command specifically
     * 
     * @param commandString
     * @return String[] parameters for ADD command
     */
    private static String[] getParam_ADD(String commandString) {
        int name = 0;
        int description = 1;

        String paramString = removeFirstWord(commandString);
        String[] paramADD = new String[MAX_ADD_PARAMETERS];
        if (paramString.isEmpty()) {
            return paramADD;
        }
        int descriptionTagIndex = paramString.indexOf(" -d ");

        if (descriptionTagIndex == INVALID_VALUE) {
            paramADD[name] = paramString;
        } else {
            paramADD[name] = paramString.substring(0, descriptionTagIndex);
            paramADD[description] = paramString.substring(descriptionTagIndex + TAG_LENGTH);
        }
        return paramADD;
    }

    /**
     * 
     * Method to retrieve TaskID from the user's command
     * 
     * @param commandString
     * @return String TaskID
     */
    private static String getTaskID(String commandString) {
        String TaskID = getFirstWord(removeFirstWord(commandString));
        return TaskID;
    }
    
    /**
     * 
     * Method to remove the command type and task id from the user's command
     * effectively getting the parameters for the command
     * 
     * @param commandString
     * @return String paramString
     */
    private static String removeCMD_N_TaskID(String commandString){
        return removeFirstWord(removeFirstWord(commandString));
    }

    /**
     * 
     * Method to retrieve parameters for EDIT command specifically
     * 
     * @param commandString
     * @return String[] parameters for EDIT command
     */
    private static String[] getParam_EDIT(String commandString) {
        int TaskID = 0;
        int name = 1;
        int description = 2;
        String[] paramEDIT = new String[MAX_EDIT_PARAMETERS];
        paramEDIT[TaskID] = getTaskID(commandString);
        
        int nameTagIndex = commandString.indexOf(" -n ");
        int descriptionTagIndex = commandString.indexOf(" -d ");

        if (nameTagIndex > descriptionTagIndex) {
            paramEDIT[name] = commandString.substring(nameTagIndex + TAG_LENGTH);
            if (descriptionTagIndex != INVALID_VALUE) {
                paramEDIT[description] = commandString.substring(descriptionTagIndex + TAG_LENGTH, nameTagIndex);
            }
        } else if (nameTagIndex < descriptionTagIndex) {
            paramEDIT[description] = commandString.substring(descriptionTagIndex + TAG_LENGTH);
            if (nameTagIndex != INVALID_VALUE) {
                paramEDIT[name] = commandString.substring(nameTagIndex + TAG_LENGTH, descriptionTagIndex);
            }
        }
        return paramEDIT;
    }

    /**
     * 
     * Method to retrieve parameters for DATE command specifically
     * 
     * @param commandString
     * @return String[] parameters for DATE command
     */
    private static String[] getParam_DATE(String commandString) throws Exception {
        int taskID = 0;
        String date = removeCMD_N_TaskID(commandString);
        String[] paramDATE = new String[MAX_DATE_PARAMETERS];
        paramDATE[taskID] = getTaskID(commandString);
        
        String[] dateArray = date.split("/");
        if (dateArray.length != MAX_DATE_PARAMETERS-1) {
            throw new Exception("Invalid Format");
        } else {
            for (int i = 0; i < dateArray.length; i++) {
                try {
                    int num = Integer.parseInt(dateArray[i]);
                    paramDATE[i+1] = String.valueOf(num);
                } catch (NumberFormatException e) {
                    throw new Exception("Invalid Number");
                }
            }
        }
        return paramDATE;
    }

    /**
     * 
     * Method to retrieve parameters for WORKLOAD command specifically
     * 
     * @param commandString
     * @return String[] parameters for WORKLOAD command
     */
    private static String[] getParam_WL(String commandString) throws Exception{
        int taskID = 0;
        int workload = 1;
        String paramString = removeCMD_N_TaskID(commandString);
        String[] paramArray = new String[WORKLOAD_PARAMETERS];
        paramArray[taskID] = getTaskID(commandString);
        String paramWL = "0";
        try {
            int WL = Integer.parseInt(paramString);
            paramWL = String.valueOf(WL);
        } catch (NumberFormatException e) {
            throw new Exception("Invalid Number");
        }
        paramArray[workload] = paramWL;
        return paramArray;
    }

    /**
     * 
     * Method to retrieve parameters for VIEW command specifically
     * 
     * @param commandString
     * @return String[] parameters for VIEW command
     */
    private static String[] getParam_VIEW(String commandString) {
        String paramString = removeFirstWord(commandString);
        String[] paramArray = new String[VIEW_PARAMETERS];
        if (paramString.equals("-l")) {
            paramArray[0] =  "LIST";
        } else if (paramString.equals("-c")) {
            paramArray[0] = "CALENDAR";
        } else {
            paramArray[0] = "TASK";
            paramArray[1] = paramString;
        }
        return paramArray;
    }

    /**
     * 
     * Method for retrieving parameters for FIND command specifically
     * 
     * @param commandString
     * @return String[] parameters for FIND command
     */
    private static String[] getParam_FIND(String commandString) {
        int tag = 0;
        int toSearch = 1;

        String[] paramArray = new String[FIND_PARAMETERS];
        String paramString = removeFirstWord(commandString);
        String tagType = getFirstWord(paramString);

        if (tagType.equals("-t")) {
            paramArray[tag] = "DATE";
            paramArray[toSearch] = removeFirstWord(paramString);
        } else if (tagType.equals("-w")) {
            paramArray[tag] = "WORKLOAD";
            paramArray[toSearch] = removeFirstWord(paramString);
        } else {
            paramArray[tag] = "KEYWORD";
            paramArray[toSearch] = paramString;
        }
        return paramArray;
    }
    
    private static String[] getParam_ARCH(String commandString){
        String paramString = removeFirstWord(commandString);
        String[] paramArray = new String[1];
        paramArray[0] = paramString;
        return paramArray;
    }

    /********************************** Helper Functions *************************************/

    /**
     * 
     * Method for removing the first word(separated by a whitespace) from a string
     * 
     * @param line
     * @return String remaining string
     */
    private static String removeFirstWord(String line) {
        return line.replaceFirst(getFirstWord(line), "").trim();
    }

    /**
     * 
     * Method for retrieving the first word(separated by a whitespace) from a string
     * 
     * @param commandString
     * @return String first word
     */
    private static String getFirstWord(String commandString) {
        String firstWord = commandString.trim().split("\\s+")[0];
        return firstWord;
    }
    
}
