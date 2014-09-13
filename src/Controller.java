import java.util.Arrays;


public class Controller {
	
	private static final int MAX_ADD_PARAMETERS = 4;
	private static final int MAX_EDIT_PARAMETERS = 5;
	private static final int TAG_LENGTH = 2;
	
	private enum CMDtype {
		ADD, DELETE, EDIT, VIEW, FIND, ARCHIVE, UNDO, INVALID
	}
	public void executeCMD(String commandString){
		String command = getFirstWord(commandString);
		CMDtype commandType = determineCMDtype(command);
		switch(commandType){
		case ADD:
			String[] paramADD = getParamADD(commandString);
			Logic.addTask(paramADD);
			break;
		case DELETE:
			String paramDELETE = getParam(commandString);
			Logic.deleteTask(paramDELETE);
			break;
		case EDIT:
			String taskID = getTaskIDforEDIT(commandString);
			String[] paramEDIT = getParamEDIT(commandString);
			Logic.editTask(taskID, paramEDIT);
			break;
		case VIEW:
			break;
		case FIND: 
			break;
		case ARCHIVE:
			break;
		case UNDO:
			break;
		case INVALID:
			break;
		default:
			throw new Error("");
		}
	}
	
	public CMDtype determineCMDtype(String command){
		switch(command.toLowerCase()){
		case "add":
			return CMDtype.ADD;
		case "delete":
			return CMDtype.DELETE;
		case "edit":
			return CMDtype.EDIT;
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
	
	private static String[] getParamADD(String commandString){
		String paramString = removeFirstWord(commandString);
		String[] paramArray = paramString.split("\\s+");
		String[] fullParamArray = new String[MAX_ADD_PARAMETERS];
		for(int i = 0; i < paramArray.length; i++){
			String tag = getTag(paramArray[i]);
			String param = removeTag(paramArray[i]);
			switch(determineTagType(tag)){
			case "DATE":
				fullParamArray[0] = param;
				break;
			case "WORKLOAD":
				fullParamArray[1] = param;
				break;
			case "NAME":
				fullParamArray[2] = param;
				break;
			case "DESCRIPTION":
				fullParamArray[3] = param;
				break;
			}
		}
		return fullParamArray;
	}
	
	private static String getTaskIDforEDIT(String commandString){
		String taskIDParam = getFirstWord(removeFirstWord(commandString));
		if(determineTagType(getTag(taskIDParam)).equals("TASK_ID")){
			return removeTag(taskIDParam);
		} else {
			
		}
	}
	
	private static String[] getParamEDIT(String commandString){
		String[] paramArray = commandString.trim().split("\\s+");
		paramArray = Arrays.copyOfRange(paramArray, 2, MAX_EDIT_PARAMETERS);
		return paramArray;
	}
	
	private static String getTag(String param){
		return ""+param.charAt(0) + param.charAt(1);
	}
	
	private static String removeTag(String param){
		return param.substring(TAG_LENGTH);
	}
	
	private static String determineTagType(String tag){
		switch(tag){
		case "-t":
			return "TASK_ID";
		case "-d":
			return "DATE";
		case "-w":
			return "WORKLOAD";
		case "-n":
			return "NAME";
		case "-s":
			return "COMPLETION_TAG";
		case "-x":
			return "DESCRIPTION";
		case "-c":
			return "CALENDAR";
		case "-l":
			return "LIST";
		default:
			return "INVALID";
		}
	}
	
	private static String removeFirstWord(String line){
		return line.replace(getFirstWord(line), "").trim();
	}
	
	private static String getParam(String commandString) {
		return removeFirstWord(commandString);
	}
	
	private static String getFirstWord(String commandString){
		String firstWord = commandString.trim().split("\\s+")[0];
		return firstWord;
	}
	
}
