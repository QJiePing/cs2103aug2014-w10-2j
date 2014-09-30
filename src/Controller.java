import java.util.Arrays;


public class Controller {
	
	private static final int INVALID_VALUE = -1;
	private static final int MAX_ADD_PARAMETERS = 2;
	private static final int MAX_EDIT_PARAMETERS = 2;
	private static final int MAX_DATE_PARAMETERS = 3;
	private static final int TAG_LENGTH = 4;
	private static final String EMPTY_STRING = "";
	
	private enum CMDtype {
		ADD, DELETE, EDIT, DATE, WORKLOAD, COMPLETION_TAG, 
		VIEW, FIND, ARCHIVE, UNDO, INVALID
	}
	public static void executeCMD(String commandString) {
		String command = getFirstWord(commandString);
		CMDtype commandType = determineCMDtype(command);
		switch(commandType){
		case ADD:
			String[] param_ADD = getParam_ADD(commandString);
			String name_ADD = param_ADD[0];
			String description_ADD = param_ADD[1];
			OPLogic.addTask(name_ADD, description_ADD);
			break;
		case DELETE:
			String taskID_DELETE = getTaskID(commandString);
			Logic.deleteTask(taskID_DELETE);
			break;
		case EDIT:
			String taskID_EDIT = getTaskID(commandString);
			String[] param_EDIT = getParam_EDIT(commandString);
			String name_EDIT = param_EDIT[0];
			String description_EDIT = param_EDIT[1];
			Logic.editTask(taskID_EDIT, name_EDIT, description_EDIT);
			break;
		case DATE:
			String taskID_DATE = getTaskID(commandString);
			int[] date = getParam_DATE(commandString);
			int day = date[0];
			int month = date[1];
			int year = date[2];
			//Logic.editDate(taskID, day, month, year);
			break;
		case WORKLOAD:
			String taskID_WORKLOAD = getTaskID(commandString);
			int workloadAttribute = getParam_WL(commandString);
			//Logic.editWorkload(taskID, workloadAttribute);
			break;
		case COMPLETION_TAG:
			String taskID = getTaskID(commandString);
			Logic.switchTag(taskID);
		case VIEW:
			String paramVIEW = getParamVIEW(commandString);
			//Logic.view(paramVIEW);
			break;
		case FIND:
			String tagTypeFIND = getTagFIND_ARCHIVE(commandString);
			//String paramFIND = removeTag(getParam(commandString));
			//Logic.find(tagTypeFIND, paramFIND);
			break;
		case ARCHIVE:
			String tagTypeARCHIVE = getTagFIND_ARCHIVE(commandString);
			//Logic.archive(tagTypeARCHIVE, paramARCHIVE);
			break;
		case UNDO:
			//Logic.undo();
			break;
		case INVALID:
			handleError("invalid command");
			break;
		default:
			handleError("unknown error");
		}
	}
	
	public static void handleError(String error){
		
	}
	
	private static CMDtype determineCMDtype(String command){
		switch(command.toLowerCase()){
		case "add":
			return CMDtype.ADD;
		case "delete":
			return CMDtype.DELETE;
		case "edit":
			return CMDtype.EDIT;
		case "date":
			return CMDtype.DATE;
		case "priority":
			return CMDtype.WORKLOAD;
		case "completed":
			return CMDtype.COMPLETION_TAG;
		case "view":
			return CMDtype.VIEW;
		case "find":
			return CMDtype.FIND;
		case "arch":
			return CMDtype.UNDO;
		default:
			return CMDtype.INVALID;
		}
	}
	
	private static String[] getParam_ADD(String commandString){
		int name = 0;
		int description = 1;
		
		String paramString = removeFirstWord(commandString);
		String[] paramADD = new String[MAX_ADD_PARAMETERS];
		if(paramString.isEmpty()){
			return paramADD;
		}
		int descriptionTagIndex = paramString.indexOf(" -d ");
		
		if(descriptionTagIndex == INVALID_VALUE){
			paramADD[name] = paramString;
		} 
		else {
			paramADD[name] = paramString.substring(0, descriptionTagIndex);
			paramADD[description] = paramString.substring(descriptionTagIndex + TAG_LENGTH);
		}
		return paramADD;
	}
	
	private static String getTaskID(String commandString){
		String TaskID = getFirstWord(removeFirstWord(commandString));
		return TaskID;
	}
	
	private static String[] getParam_EDIT(String commandString){
		int name = 0;
		int description = 1;
		String[] paramEDIT = new String[MAX_EDIT_PARAMETERS];
		
		int nameTagIndex = commandString.indexOf(" -n ");
		int descriptionTagIndex = commandString.indexOf(" -d ");
		
		if(nameTagIndex > descriptionTagIndex){
			paramEDIT[name] = commandString.substring(nameTagIndex + TAG_LENGTH);
			if(descriptionTagIndex != INVALID_VALUE){
				paramEDIT[description] = 
						commandString.substring(descriptionTagIndex + TAG_LENGTH, nameTagIndex);
			}
		}
		else if(nameTagIndex < descriptionTagIndex){
			paramEDIT[description] = commandString.substring(descriptionTagIndex + TAG_LENGTH);
			if(nameTagIndex != INVALID_VALUE){
				paramEDIT[name] = 
						commandString.substring(nameTagIndex + TAG_LENGTH, descriptionTagIndex);
			}
		} 
		return paramEDIT;
	}
	
	private static int[] getParam_DATE(String commandString){
		String date = removeFirstWord(commandString);
		int[] paramDATE = new int[3];
		String[] dateArray = date.split("/");
		if(dateArray.length != MAX_DATE_PARAMETERS){
			handleError("invalid format");
		} 
		else {
			for(int i =0; i < dateArray.length; i++){
				try{
					int num = Integer.parseInt(dateArray[i]);
					paramDATE[i] = num;
				} catch(NumberFormatException e){
					handleError("invalid number");
				}	
			}
		}
		return paramDATE;
	}
	
	private static int getParam_WL(String commandString){
		String paramString = removeFirstWord(commandString);
		int paramWL = 0;
		try{
			paramWL = Integer.parseInt(paramString);
		}
		catch(NumberFormatException e){
			handleError("invalid number");
		}
		return paramWL;
	}
	
	private static String getParamVIEW(String commandString){
//		String tag = determineTagType(getParam(commandString));
//		switch(tag){
//		case "LIST":
//			return tag;
//		case "CALENDAR":
//			return tag;
//		default:
//			return "Invalid command";
//		}
	    return "";
	}
	
	private static String getTagFIND_ARCHIVE(String commandString){
//		String tag = determineTagType(getParam(commandString));
//		switch(tag){
//		case "KEYWORD":
//			return tag;
//		case "DATE":
//			return tag;
//		case "MONTH":
//			return tag;
//		case "YEAR":
//			return tag;
//		case "WORKLOAD":
//			return tag;
//		default:
//			return "Invalid command";
//		}
	    return "";
	}
	
	private static String removeFirstWord(String line){
		return line.replace(getFirstWord(line), "").trim();
	}
	
	private static String getFirstWord(String commandString){
		String firstWord = commandString.trim().split("\\s+")[0];
		return firstWord;
	}
	
}
