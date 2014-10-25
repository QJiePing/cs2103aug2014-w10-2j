package taskaler.controller.parser;

import static taskaler.controller.common.*;

public class Parser {
    // Local Variables
    private static String currentTaskID;
    private static String currentState;
    private CmdType command;
    private String[] parameters;
    
    /**
     * @param commandString
     * 
     * Contructor for Parser
     */
    public Parser() throws Exception{
        ;
    }
    
    /**
     * Method to retrieve the command type
     * 
     * @return CmdType
     */
    public CmdType getCommand(){
        return command;
    }
    
    /**
     * Method to retrieve parameters
     * 
     * @return String[] array of parameters
     */
    public String[] getParameters(){
        return parameters;
    }
    
    /**
     * Public function to parse the command string
     * 
     * @param commandString
     */
    public void parseCMD(String commandString, String state, String taskID) throws Exception{
        String CMD = getFirstWord(commandString);
        command = determineCMD_TYPE(CMD);
        currentState = state;
        currentTaskID = taskID;
        parameters = getParams(command, commandString);
    }
    
    /** 
     * Method to differentiate between CMD_TYPE(s) when given the user command 
     * 
     * @param command
     * @return CmdType command type
     */
    private CmdType determineCMD_TYPE(String command) {
        CmdType commandType = ParserLibrary.commandList.get(command);
        if(commandType == null){
            return CmdType.INVALID;
        }
        else {
            return commandType;
        }
    }
    
    /**
     * Method to get the parameters for each command, differentiated by CMD_TYPE
     * 
     * @param commandType
     * @param commandString
     * @return String[] parameters
     */
    private static String[] getParams(CmdType commandType, String commandString) throws Exception{
        switch(commandType){
        case ADD:
            return getParamADD(commandString);
        case DELETE:
            return getParamDELETE(commandString);
        case EDIT:
            return getParamEDIT(commandString);
        case DATE:
            return getParamDATE(commandString);
        case REPEAT:
            return getParamREPEAT(commandString);
        case WORKLOAD:
            return getParamWL(commandString);
        case COMPLETION_TAG:
            return getParamCT(commandString);
        case VIEW:
            return getParamVIEW(commandString);
        case FIND:
            return getParamFIND(commandString);
        case ARCHIVE:
            return getParamARCH(commandString);
        case GOTO:
            return getParamGOTO(commandString);
        default:
            return null;
        }
    }
    
    /** 
     * Method to retrieve parameters for ADD command specifically
     * 
     * @param commandString
     * @return String[] parameters for ADD command
     */
    private static String[] getParamADD(String commandString) throws Exception{
        int nameIndex = 0;
        int descriptionIndex = 1;
        int dateAndTimeIndex = 2;
        int workloadIndex = 3;
        
        String paramString = removeFirstWord(commandString);
        String[] paramArray = paramString.split(",");
        String[] paramADD = new String[MAX_ADD_PARAMETERS];
        int numOfParams = paramArray.length;
        switch(numOfParams){
        case 1:
            paramADD = appendNameAndDescription(paramArray[0].trim(), paramADD, 
                    nameIndex, descriptionIndex);
            break;
        case 2:
            paramADD = appendNameAndDescription(paramArray[0].trim(), paramADD, 
                    nameIndex, descriptionIndex);
            paramADD = appendDateAndTime(paramArray[1].trim(), paramADD, dateAndTimeIndex);
            break;
        case 3:
            paramADD = appendNameAndDescription(paramArray[0].trim(), paramADD, 
                    nameIndex, descriptionIndex);
            paramADD = appendDateAndTime(paramArray[1].trim(), paramADD, dateAndTimeIndex);
            paramADD = appendWorkload(paramArray[2].trim(), paramADD, workloadIndex);
            break;
        default:
            throw new Exception("Invalid parameters");
        }
        return paramADD;
    }
    
    /**
     * Method to append the name and description parameters
     * 
     * @param paramString
     * @param paramArray
     * @return
     */
    private static String[] appendNameAndDescription(String paramString, String[] paramArray, 
            int indexN, int indexD){
        int nameIndex = indexN;
        int descriptionIndex = indexD;
        
        int splitIndex = paramString.indexOf(":");
        if(splitIndex == INVALID_VALUE) {
            if(!paramString.isEmpty()){
                paramArray[nameIndex] = paramString;
            }
        }
        else {
            String name = paramString.substring(0, splitIndex).trim();
            String description = paramString.substring(splitIndex + 1).trim();
            paramArray[nameIndex] = convertToNullIfEmpty(name);
            paramArray[descriptionIndex] = convertToNullIfEmpty(description);
        }
        return paramArray;
    }
    
    /**
     * Method to append the date and time parameters
     * 
     * @param paramString
     * @param paramArray
     * @return
     * @throws Exception
     */
    private static String[] appendDateAndTime(String paramString, String[] paramArray, int indexDT) 
            throws Exception {
        int dateAndTimeIndex = indexDT;

        int splitIndex = paramString.indexOf(":");
        if(splitIndex == INVALID_VALUE) {
            paramArray[dateAndTimeIndex] = ParseAttribute.parseDate(paramString);
        }
        else {
            String date = ParseAttribute.parseDate(paramString.substring(0, splitIndex).trim());
            String time = ParseAttribute.parseTime(paramString.substring(splitIndex + 1).trim());
            paramArray[dateAndTimeIndex] = date + ": " + time;
        }
        return paramArray;
    }
 
    /**
     * Method to append the workload attribute parameters
     * 
     * @param paramString
     * @param paramArray
     * @return
     * @throws Exception
     */
    private static String[] appendWorkload(String paramString, String[] paramArray, int indexWL) throws Exception{
        int workloadIndex = indexWL;
        String workload = ParseAttribute.parseWL(paramString);
        paramArray[workloadIndex] = workload;
        return paramArray;
    }
 
    /**
     * Method to retrieve parameters for the DELETE command specifically
     * 
     * @param commandString
     * @return String[] parameters for the DELETE command
     */
    private static String[] getParamDELETE(String commandString) throws Exception{
        int taskID_index = 0;
        String[] paramDELETE = new String[DELETE_PARAMETERS];
        String taskID = getTaskID(commandString);
        if(taskID.isEmpty()){
            if(currentTaskID != null){
                paramDELETE[taskID_index] = currentTaskID;
            }
            else {
                throw new Exception("Invalid task ID");
            }
        }
        else {
            paramDELETE[taskID_index] = taskID;
        }
        return paramDELETE;
    }
    /** 
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
     * Method to remove the command type and task id from the user's command
     * effectively getting the parameters for the command
     * 
     * @param commandString
     * @return String paramString
     */
    private static String removeCommandAndTaskID(String commandString){
        return removeFirstWord(removeFirstWord(commandString));
    }

    /**
     * Method to retrieve parameters for EDIT command specifically
     * 
     * @param commandString
     * @return String[] parameters for EDIT command
     */
    private static String[] getParamEDIT(String commandString) throws Exception {
        int taskIDIndex = 0;
        int nameIndex = 1;
        int descriptionIndex = 2;
        
        String[] paramEDIT = new String[MAX_EDIT_PARAMETERS];
        String paramString = removeFirstWord(commandString);
        String[] paramArray = paramString.split("\\s+");
        if(currentTaskID == null){
            if(paramArray.length > 1){
                paramEDIT[taskIDIndex] = getFirstWord(paramString);
                paramEDIT = appendNameAndDescription(removeFirstWord(paramString), paramEDIT, 
                        nameIndex, descriptionIndex);
            }
            else {
                throw new Exception("Invalid task ID");
            }
        }
        else {
            paramEDIT[taskIDIndex] = currentTaskID;
            paramEDIT = appendNameAndDescription(paramString, paramEDIT, nameIndex, descriptionIndex);
        }
        return paramEDIT;
    }

    /** 
     * Method to retrieve parameters for DATE command specifically
     * 
     * @param commandString
     * @return String[] parameters for DATE command
     */
    private static String[] getParamDATE(String commandString) throws Exception{
        int taskID_index = 0;
        int date_index = 1;
        String[] paramArray = removeFirstWord(commandString).split("\\s+");
        String date = removeCommandAndTaskID(commandString);
        String[] paramDATE = new String[DATE_PARAMETERS];
        if(paramArray.length == 1){
            if(currentTaskID != null){
                paramDATE[taskID_index] = currentTaskID;
                date = removeFirstWord(commandString);
            }
            else {
                throw new Exception("Invalid task ID");
            }
        }
        else if(paramArray.length == 2){
            paramDATE[taskID_index] = getTaskID(commandString);
        }
        else {
            throw new Exception("Invalid date syntax");
        }
        String dateInFormat = ParseAttribute.parseDate(date);
        paramDATE[date_index] = dateInFormat;
        return paramDATE;
    }
    
    /**
     * Method to return the parameters for REPEAT command specifically
     * "repeat 1 weekly, from 09/10/2014 to 21/11/2014"
     * @param commandString
     * @return String[] parameters for REPEAT command
     * @throws Exception Invalid date/time/pattern syntax
     */
    private static String[] getParamREPEAT(String commandString) throws Exception {
        String[] paramREPEAT = new String[MAX_REPEAT_PARAMETERS];
        String paramString = removeFirstWord(commandString);
        int splitIndex = paramString.indexOf(",");
        String IDAndPatternField = "";
        String dateField = "";
        if(splitIndex != INVALID_VALUE){
            IDAndPatternField = paramString.substring(0, splitIndex).trim();
            dateField = paramString.substring(splitIndex + 1).trim();
            if(!dateField.isEmpty()) {
                paramREPEAT = appendStartAndEndDate(dateField, paramREPEAT); 
            }
        }
        else {
            IDAndPatternField = paramString.trim();
        }
        paramREPEAT = appendIDAndPattern(IDAndPatternField, paramREPEAT);
        return paramREPEAT;
    }
    
    /**
     * Parses and appends the taskID and pattern parameters, for getParamREPEAT
     * 
     * @param field
     * @param paramArray
     * @return
     * @throws Exception
     */
    private static String[] appendIDAndPattern(String IDAndPatternfield, String[] paramArray) throws Exception {
        int taskIDIndex = 0;
        int patternIndex = 1;
        
        String[] fieldSplit = IDAndPatternfield.split("\\s+");
        if(fieldSplit.length == 1){
            if(currentTaskID != null){
                paramArray[taskIDIndex] = currentTaskID;
                paramArray[patternIndex] = ParseAttribute.parsePattern(IDAndPatternfield);
            }
            else {
                throw new Exception("Invalid task ID");
            }
        }
        else if(fieldSplit.length == 2){
            paramArray[taskIDIndex] = getFirstWord(IDAndPatternfield);
            paramArray[patternIndex] = ParseAttribute.parsePattern(removeFirstWord(IDAndPatternfield));
        }
        else {
            throw new Exception("Invalid task ID");
        }
        return paramArray;
    }
    
    /**
     * Parses and appends the start and end date parameter(s) to the array, for getParamREPEAT
     * 
     * @param dateField
     * @param paramArray
     * @return String[] paramArray after appending start and end date
     * @throws Exception Invalid start-to-endDate/date syntax
     */
    private static String[] appendStartAndEndDate(String dateField, String[] paramArray) throws Exception {
        int startDateIndex = 2;
        int endDateIndex = 3;
        
        int fromIndex = dateField.indexOf("from");
        int toIndex = dateField.indexOf("to");
        if(fromIndex == INVALID_VALUE){
            if(toIndex == INVALID_VALUE){
                throw new Exception("Invalid start and end date parameters,"
                        + "try: from <start date> to <end date>");
            }
            else {
                paramArray[endDateIndex] = ParseAttribute.parseDate(
                        dateField.substring(toIndex + LENGTH_OF_TO).trim());
            }
        }
        else {
            if(toIndex == INVALID_VALUE){
                paramArray[startDateIndex] = ParseAttribute.parseDate(
                        dateField.substring(fromIndex + LENGTH_OF_FROM).trim());
            }
            else {
                try{
                    assert(fromIndex < toIndex);
                }
                catch(AssertionError ae){
                    throw new Exception("Invalid start to end date parameters, "
                            + "try: from <start date> to <end date>");
                }
                paramArray[startDateIndex] = ParseAttribute.parseDate(
                        dateField.substring(fromIndex + LENGTH_OF_FROM).trim());
                paramArray[endDateIndex] = ParseAttribute.parseDate(
                        dateField.substring(toIndex + LENGTH_OF_TO).trim());
            }
        }
        return paramArray;
    }
    
    /**
     * Method to retrieve parameters for WORKLOAD command specifically
     * 
     * @param commandString
     * @return String[] parameters for WORKLOAD command
     */
    private static String[] getParamWL(String commandString) throws Exception{
        int taskID_index = 0;
        int workload_index = 1;
        String paramString = removeCommandAndTaskID(commandString);
        String[] paramArray = new String[WORKLOAD_PARAMETERS];
        paramArray[taskID_index] = getTaskID(commandString);
        String paramWL = "0";
        try {
            int WL = Integer.parseInt(paramString);
            if(WL >= 1 && WL <= 3){
                paramWL = String.valueOf(WL);
            } 
            else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new Exception("Invalid Workload Attribute");
        }
        paramArray[workload_index] = paramWL;
        return paramArray;
    }
    
    private static String[] getParamCT(String commandString) throws Exception{
        int taskIDIndex = 0;
        String[] paramWL = new String[COMPLETION_TAG_PARAMETERS];
        String paramString = removeFirstWord(commandString);
        if(paramString.isEmpty()){
            if(currentTaskID != null){
                paramWL[taskIDIndex] = currentTaskID;
            }
            else {
                throw new Exception("Invalid task ID");
            }
        }
        else {
            paramWL[taskIDIndex] = getTaskID(commandString);
        }
        return paramWL;
    }
    
    /**
     * 
     * Method to retrieve parameters for VIEW command specifically
     * 
     * @param commandString
     * @return String[] parameters for VIEW command
     */
    private static String[] getParamVIEW(String commandString) {
        String paramString = removeFirstWord(commandString);
        String[] paramArray = new String[VIEW_PARAMETERS];
        if (paramString.equalsIgnoreCase("-l") ||
                paramString.equalsIgnoreCase("list") || 
                paramString.equalsIgnoreCase("all")) {
            paramArray[0] =  "LIST";
        } else if (paramString.equalsIgnoreCase("-c") ||
                paramString.equalsIgnoreCase("calendar")) {
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
     * @throws Exception if date or workload parameter is invalid
     */
    private static String[] getParamFIND(String commandString) throws Exception{
        int tag_index = 0;
        int toSearch_index = 1;

        String[] paramArray = new String[FIND_PARAMETERS];
        String paramString = removeFirstWord(commandString);

        String tagType = getFirstWord(paramString);

        if (tagType.equalsIgnoreCase("-t")) {
            paramArray[tag_index] = "DATE";
            String date = ParseAttribute.parseDate(removeFirstWord(paramString));
            paramArray[toSearch_index] = date;
        } 
        else if (tagType.equalsIgnoreCase("-w")) {
            paramArray[tag_index] = "WORKLOAD";
            paramArray[toSearch_index] = removeFirstWord(paramString);
        } 
        else {
            paramArray[tag_index] = "KEYWORD";
            paramArray[toSearch_index] = paramString;
        }
        
        return paramArray;
    }
    
    /**
     * Method to retrieve the parameters for the ARCHIVE command specifically
     * 
     * @param commandString
     * @return String[] parameters for ARCHIVE command
     * @throws Exception if date parameter is invalid
     */
    private static String[] getParamARCH(String commandString) throws Exception{
        String paramString = removeFirstWord(commandString);
        String[] paramArray = new String[ARCHIVE_PARAMETERS];
        if(paramString.equals("")){
            paramArray[0] = null;
        }
        else {
            paramArray[0] = ParseAttribute.parseDate(paramString);  
        }
        return paramArray;
    }
    
    /**
     * Method to retrieve parameters for the GOTO command specifically
     * 
     * @param commandString
     * @return String[] parameters for the GOTO command specifically
     */
    private static String[] getParamGOTO(String commandString){
        String[] paramArray = new String[GOTO_PARAMETERS];
        String command = getFirstWord(commandString).toLowerCase();
        String paramString = removeFirstWord(commandString);
        switch(command){
        case "next":
            ;
        case "back":
            ;
        case "goto":
            ;
        }
        return paramArray;
    }

    /********************************** Helper Functions *************************************/
    
    /**
     * Method for removing the first word(separated by a whitespace) from a string
     * 
     * @param line
     * @return String remaining string
     */
    private static String removeFirstWord(String line) {
        return line.replaceFirst(getFirstWord(line), "").trim();
    }

    /** 
     * Method for retrieving the first word(separated by a whitespace) from a string
     * 
     * @param commandString
     * @return String first word
     */
    private static String getFirstWord(String commandString) {
        String firstWord = commandString.trim().split("\\s+")[0];
        return firstWord;
    }
    
    /**
     * Returns the string itself, or null if it was an empty string
     * 
     * @param string
     * @return
     */
    private static String convertToNullIfEmpty(String string){
        if(string.isEmpty()){
            return null;
        }
        else {
            return string;
        }
    }
    
}
