package taskaler.common.configurations;

import taskaler.common.util.CommonLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import taskaler.storage.Storage;

public class Configuration {
	
	private static Configuration instance = null;

	private String defaultView = null;
	private String defaultLogLevel = null; 
	private String defaultWelcomeMsg = null;
	private String defaultRowColor = null;
	private String defaultAltRowColor = null;
	private String defaultToastColor = null;
	private String defaultDoneColor = null;
	private String defaultHeaderColor = null;
	private String defaultFileName = null;
	private SimpleDateFormat defaultDateFormat = null;
	private SimpleDateFormat defaultTimeFormat = null;
	
	public static final int VIEW_POISTION = 0;
	public static final int LOG_LEVEL_POSITION = 1;
	public static final int WECOME_MSG_POSITION = 2;
	public static final int ROW_COLOR_POSITION = 3;
	public static final int ALTROW_COLOR_POSITION = 4;
	public static final int TOAST_COLOR_POSITION = 5;
	public static final int DONE_COLOR_POSITION = 6;
	public static final int HEADER_COLOR_POSITION = 7;
	public static final int FILENAME_POSITION = 8;
	public static final int DATEFORMAT_POSITION = 9;
	public static final int TIMEFORMAT_POSITION = 10;
	public static final int NUM_OF_ATTRIBUTE = 11;
	
	public static String DEFAULT_VIEW = "list";
	public static String DEFAULT_WELCOME_MSG = "Welcome to Taskaler!";
	public static String DEFAULT_LOG_LEVEL = "all";
	public static String DEFAULT_ROW_COLOR = "#FFFFFF";
	public static String DEFAULT_ALTROW_COLOR = "#66CCFF";
	public static String DEFAULT_TOAST_COLOR = "#FFFF00";
	public static String DEFAULT_DONE_COLOR = "#FF6600";
	public static String DEFAULT_HEADER_COLOR = "#9966CC";
	public static String DEFAULT_FILE_NAME = "task_list";
	public static String DEFAULT_TIME_FORMAT = "HH:mm";
	public static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	
	private static CommonLogger log = CommonLogger.getInstance();
	
    public static ArrayList<String> availableColor = null;
	

	private Configuration(){
	    availableColor = new ArrayList<String>()
	            {{  add("#FFFFFF");     //white
	                add("#66CCFF");     //light blue
	                add("#FFFF00");     //yellow
	                add("#FF0000");     //red
	                add("#FF6699");     //pink
	                add("#FF6666");     //peach
	                add("#9966CC");     //bluish-purple
	                add("#FF6600");     //orange
	                add("#3366FF");     //blue
	                add("#CC0099");     //violet
	                add("#996600");     //mud brown
	                add("#909090");     //grey
	            }};
		loadConfiguration();
	}
	
	
	public static Configuration getInstance(){
		if(instance == null) {
			instance = new Configuration();
		}
		
		return instance;
	}
	
	
	/**
	 * loadConfiguration() will set all the configuration attributes to user default set
	 * if configuration information is valid
	 */
	public void loadConfiguration() {
		ArrayList<String> configInfo = Storage.getInstance().readConfigFile();
		configInfo = checkConfigInfo(configInfo);
		
		if(configInfo == null) {
			defaultView = DEFAULT_VIEW;
			defaultLogLevel = DEFAULT_LOG_LEVEL;
			defaultWelcomeMsg = DEFAULT_WELCOME_MSG;
			defaultRowColor = DEFAULT_ROW_COLOR;
			defaultAltRowColor = DEFAULT_ALTROW_COLOR;
			defaultToastColor = DEFAULT_TOAST_COLOR;
			defaultDoneColor = DEFAULT_DONE_COLOR;
			defaultHeaderColor = DEFAULT_HEADER_COLOR;
			defaultFileName = DEFAULT_FILE_NAME ;
			defaultTimeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
			defaultDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			storeConfigInfo();
		} else {
			defaultView = configInfo.get(VIEW_POISTION);
			defaultLogLevel = configInfo.get(LOG_LEVEL_POSITION);
			defaultWelcomeMsg = configInfo.get(WECOME_MSG_POSITION);
			defaultRowColor = configInfo.get(ROW_COLOR_POSITION);
			defaultAltRowColor = configInfo.get(ALTROW_COLOR_POSITION);
			defaultToastColor = configInfo.get(TOAST_COLOR_POSITION);
			defaultDoneColor = configInfo.get(DONE_COLOR_POSITION);
			defaultHeaderColor = configInfo.get(HEADER_COLOR_POSITION);
			defaultFileName = configInfo.get(FILENAME_POSITION);
			defaultDateFormat = new SimpleDateFormat(configInfo.get(DATEFORMAT_POSITION));
			defaultTimeFormat = new SimpleDateFormat(configInfo.get(TIMEFORMAT_POSITION));

		}
		
	}
	
	/**
	 * checkConfigInfo(ArrayList<String> configInfo) will check the configuration information are all valid
	 * 
	 * @param configInfo
	 * @return return null if configInfo is not valid, otherwise return configInfo
	 */
	
	private ArrayList<String> checkConfigInfo(ArrayList<String> configInfo) {
	    
		if(configInfo == null || configInfo.size() != NUM_OF_ATTRIBUTE) {
			return null;
		} else {
			if (configInfo.get(VIEW_POISTION).compareToIgnoreCase("list") != 0
					&& configInfo.get(VIEW_POISTION).compareToIgnoreCase("calendar") != 0 
					&& configInfo.get(VIEW_POISTION).compareToIgnoreCase("today") != 0) {
				return null;
			}
			if(configInfo.get(LOG_LEVEL_POSITION).compareToIgnoreCase("all") != 0
			        && configInfo.get(LOG_LEVEL_POSITION).compareToIgnoreCase("none") != 0){
			    return null;
			}
			if(configInfo.get(WECOME_MSG_POSITION) == null){
			    return null;
			}
			if(!availableColor.contains(configInfo.get(ROW_COLOR_POSITION))) {
				return null;
			}
			
			if(!availableColor.contains(configInfo.get(ALTROW_COLOR_POSITION))) {
                return null;
            }
			
			if(!availableColor.contains(configInfo.get(TOAST_COLOR_POSITION))) {
                return null;
            }
			
			if(!availableColor.contains(configInfo.get(DONE_COLOR_POSITION))) {
                return null;
            }
			
			if(!availableColor.contains(configInfo.get(HEADER_COLOR_POSITION))) {
                return null;
            }
			
			if (configInfo.get(DATEFORMAT_POSITION).compareTo("dd/MMM/yyyy") != 0
                    && configInfo.get(DATEFORMAT_POSITION).compareTo("dd/MM/yyyy") != 0
                    && configInfo.get(DATEFORMAT_POSITION).compareTo("dd MMM yyyy") != 0) {
                return null;
            }
			
			if (configInfo.get(TIMEFORMAT_POSITION).compareTo("hh:mm aa") != 0
					&& configInfo.get(TIMEFORMAT_POSITION).compareTo("HH:mm") != 0) {
				return null;
			}
		}

		return configInfo;
	}

	
	/**
	 * storeConfigInfo() will call the storage to store all the attribute
	 */
	public void storeConfigInfo() {
		ArrayList<String> configInfo = new ArrayList<String>();
		configInfo.add(defaultView);
		configInfo.add(defaultLogLevel);
		configInfo.add(defaultWelcomeMsg);
		configInfo.add(defaultRowColor);
		configInfo.add(defaultAltRowColor);
		configInfo.add(defaultToastColor);
		configInfo.add(defaultDoneColor);
		configInfo.add(defaultHeaderColor);
		configInfo.add(defaultFileName);
		configInfo.add(defaultDateFormat.toPattern());
		configInfo.add(defaultTimeFormat.toPattern());
		
		Storage.getInstance().writeConfigFile(configInfo);
	}
	
	/**************** Accessors ***********************/
	public String getDefaultFileName() {
		return defaultFileName;
	}
	
	public String getLogLevel() {
	    return defaultLogLevel;
	}
	
	public String getWelcomeMsg(){
	    return defaultWelcomeMsg;
	}
	
	public String getDefaultView() {
		return defaultView;
	}
	
	public String getDefaultRowColor() {
		return defaultRowColor;
	}
	
	public String getDefaultAltRowColor() {
        return defaultAltRowColor;
    }
	
	public String getDefaultToastColor() {
        return defaultToastColor;
    }
	
	public String getDefaultDoneColor() {
        return defaultDoneColor;
    }
	
	public String getDefaultHeaderColor() {
        return defaultHeaderColor;
    }
	
	public String getDateFormat() {
        return defaultDateFormat.toPattern();
    }
	
	public String getTimeFormat() {
		return defaultTimeFormat.toPattern();
	}
	
	
	/**************** Mutators ************************/
	public void setDefaultFileName(String fileName) {
		defaultFileName = fileName;
	}
	
	public void setDefaultLogLevel(String level){
	    defaultLogLevel = level;
	}
	
	public void setDefaultWelcomeMsg(String msg){
	    defaultWelcomeMsg = msg;
	}
	
	public void setDefaultView(String view) {
		defaultView = view;
	}
	
	public void setDefaultRowColor(String color) {
		defaultRowColor = color;
	}
	
	public void setDefaultAltRowColor(String color) {
        defaultAltRowColor = color;
    }
	
	public void setDefaultToastColor(String color) {
        defaultToastColor = color;
    }
	
	public void setDefaultDoneColor(String color) {
        defaultDoneColor = color;
    }
	
	public void setDefaultHeaderColor(String color) {
        defaultHeaderColor = color;
    }
	
	public void setDateFormat(String dateFormat){
	    defaultDateFormat = new SimpleDateFormat(dateFormat);
	}
	
	public void setTimeFormat(String timeFormat) {
		defaultTimeFormat = new SimpleDateFormat(timeFormat);
	}
	

}