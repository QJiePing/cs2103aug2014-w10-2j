package taskaler.controller.parser;

import static taskaler.controller.common.*;
import taskaler.common.util.parser.calendarToString;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

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
    public void parseCMD(String commandString, String taskID) throws Exception{
        String CMD = getFirstWord(commandString);
        command = determineCMD_TYPE(CMD);
        parameters = getParams(command, commandString);
        currentTaskID = taskID;
    }
    
    /**
     * 
     * Method to differentiate between CMD_TYPE(s) when given the user command 
     * 
     * @param command
     * @return CmdType command type
     */
    private CmdType determineCMD_TYPE(String command) {
        CmdType commandType = ParserLibrary.getCommandMap().get(command);
        if(commandType == null){
            return CmdType.INVALID;
        }
        else {
            return commandType;
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
            return getParam_DELETE(commandString);
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
        case GOTO:
            return getParam_GOTO(commandString);
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
    private static String[] getParam_ADD(String commandString){
        int name_index = 0;
        int description_index = 1;
        int date_index = 2;
        int workload_index = 3;
        
        String paramString = removeFirstWord(commandString);
        String[] paramArray = paramString.split(",");
        String[] paramADD = new String[MAX_ADD_PARAMETERS];
        int numOfParams = paramArray.length;
        switch(numOfParams){
        case 1:
            paramADD = getParam_ADD1(paramString);
        case 2:
            paramADD = getParam_ADD2(paramString);
        case 3:
            paramADD = getParam_ADD3(paramString);
        }
        return paramADD;
    }
    
    private static String[] getParam_ADD1(String paramString){
        "add lala: blabla, 09/10/2014: 1700-1800, 3"
    }
    
    private static String[] getParam_ADD2(String paramString){
        
    }
 
    private static String[] getParam_ADD3(String paramString){
     
    }
 
   /* private static String[] getParam_ADD(String commandString) {
        int name_index = 0;
        int description_index = 1;

        String paramString = removeFirstWord(commandString);
        String[] paramADD = new String[MAX_ADD_PARAMETERS];
        if (paramString.isEmpty()) {
            return paramADD;
        }
        int descriptionTagIndex = paramString.toLowerCase().indexOf(" -d ");

        if (descriptionTagIndex == INVALID_VALUE) {
            paramADD[name_index] = paramString;
        } else {
            paramADD[name_index] = paramString.substring(0, descriptionTagIndex);
            paramADD[description_index] = paramString.substring(descriptionTagIndex + TAG_LENGTH);
        }
        return paramADD;
    }*/
    
    /**
     * Method to retrieve parameters for the DELETE command specifically
     * 
     * @param commandString
     * @return String[] parameters for the DELETE command
     */
    private static String[] getParam_DELETE(String commandString){
        int taskID_index = 0;
        String[] paramDELETE = new String[DELETE_PARAMETERS];
        String taskID = getTaskID(commandString);
        if(taskID.isEmpty()){
            paramDELETE[taskID_index] = currentTaskID;
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
    private static String removeCMD_N_TaskID(String commandString){
        return removeFirstWord(removeFirstWord(commandString));
    }

    /**
     * Method to retrieve parameters for EDIT command specifically
     * 
     * @param commandString
     * @return String[] parameters for EDIT command
     */
    private static String[] getParam_EDIT(String commandString) {
        int taskID_index = 0;
        int name_index = 1;
        int description_index = 2;
        String[] paramEDIT = new String[MAX_EDIT_PARAMETERS];
        String taskID = getTaskID(commandString);
        if(taskID.equals("-n") || taskID.equals("-d")){
            paramEDIT[taskID_index] = currentTaskID;
        }
        else {
           paramEDIT[taskID_index] = taskID; 
        }
        int nameTagIndex = commandString.toLowerCase().indexOf(" -n ");
        int descriptionTagIndex = commandString.toLowerCase().indexOf(" -d ");

        if (nameTagIndex > descriptionTagIndex) {
            paramEDIT[name_index] = 
                    commandString.substring(nameTagIndex + TAG_LENGTH);
            if (descriptionTagIndex != INVALID_VALUE) {
                paramEDIT[description_index] = 
                        commandString.substring(descriptionTagIndex + TAG_LENGTH, 
                                nameTagIndex);
            }
        } else if (nameTagIndex < descriptionTagIndex) {
            paramEDIT[description_index] = 
                    commandString.substring(descriptionTagIndex + TAG_LENGTH);
            if (nameTagIndex != INVALID_VALUE) {
                paramEDIT[name_index] = 
                        commandString.substring(nameTagIndex + TAG_LENGTH, 
                                descriptionTagIndex);
            }
        }
        return paramEDIT;
    }

    /** 
     * Method to retrieve parameters for DATE command specifically
     * 
     * @param commandString
     * @return String[] parameters for DATE command
     */
    private static String[] getParam_DATE(String commandString) throws Exception{
        int taskID_index = 0;
        int date_index = 1;
        String[] paramArray = removeFirstWord(commandString).split("\\s+");
        String date = removeCMD_N_TaskID(commandString);
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
     * 
     * Method to retrieve parameters for WORKLOAD command specifically
     * 
     * @param commandString
     * @return String[] parameters for WORKLOAD command
     */
    private static String[] getParam_WL(String commandString) throws Exception{
        int taskID_index = 0;
        int workload_index = 1;
        String paramString = removeCMD_N_TaskID(commandString);
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
    private static String[] getParam_FIND(String commandString) throws Exception{
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
    private static String[] getParam_ARCH(String commandString) throws Exception{
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
    private static String[] getParam_GOTO(String commandString){
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
    
}
