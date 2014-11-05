package taskaler.configurations;

import java.io.File;

import taskaler.common.util.CommonLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;

import taskaler.storage.Storage;

public class Configuration {
	
	private static Configuration instance = null;

	private String defaultView = null;
	private String defaultLogLevel = null; 
	private String defaultRowColor = null;
	private String defaultAltRowColor = null;
	private String defaultToastColor = null;
	private String defaultFileName = null;
	private SimpleDateFormat defaultDateFormat = null;
	private SimpleDateFormat defaultTimeFormat = null;
	
	public static final int VIEW_POISTION = 0;
	public static final int LOG_LEVEL_POSITION = 1;
	public static final int ROW_COLOR_POSITION = 2;
	public static final int ALTROW_COLOR_POSITION = 3;
	public static final int TOAST_COLOR_POSITION = 4;
	public static final int FILENAME_POSITION = 5;
	public static final int TIMEFORMAT_POSITION = 6;
	public static final int DATEFORMAT_POSITION = 7;
	public static final int NUM_OF_ATTRIBUTE = 8;
	
	public static String DEFAULT_VIEW = "list";
	public static String DEFAULT_LOG_LEVEL = "all";
	public static String DEFAULT_ROW_COLOR = "#FFFFFF";
	public static String DEFAULT_ALTROW_COLOR = "#66CCFF";
	public static String DEFAULT_TOAST_COLOR = "#FFFF00";
	public static String DEFAULT_FILE_NAME = "task_list";
	public static String DEFAULT_TIME_FORMAT = "HHmm";
	public static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	
	private static CommonLogger log = CommonLogger.getInstance();
	
    public static final ArrayList<String> availableColor = new ArrayList<String>()
            {{  add("#FFFFFF");     //white
                add("#66CCFF");     //light blue
                add("#FFFF00");     //yellow
                add("#FF0000");     //red
                add("#FF6699");     //pink
                add("#3366FF");     //blue
                add("#CC0099");     //violet
                add("#993300");     //hazelnut brown
            }};
	

	private Configuration(){
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
			defaultRowColor = DEFAULT_ROW_COLOR;
			defaultAltRowColor = DEFAULT_ALTROW_COLOR;
			defaultToastColor = DEFAULT_TOAST_COLOR;
			defaultFileName = DEFAULT_FILE_NAME ;
			defaultTimeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
			defaultDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			storeConfigInfo();
		} else {
			defaultView = configInfo.get(VIEW_POISTION);
			defaultLogLevel = configInfo.get(LOG_LEVEL_POSITION);
			defaultRowColor = configInfo.get(ROW_COLOR_POSITION);
			defaultAltRowColor = configInfo.get(ALTROW_COLOR_POSITION);
			defaultToastColor = configInfo.get(TOAST_COLOR_POSITION);
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
					&& configInfo.get(VIEW_POISTION).compareToIgnoreCase("calendar") != 0) {
				return null;
			}
			if(configInfo.get(LOG_LEVEL_POSITION).compareToIgnoreCase("all") != 0
			        && configInfo.get(LOG_LEVEL_POSITION).compareToIgnoreCase("none") != 0){
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
			
			if (configInfo.get(DATEFORMAT_POSITION).compareTo("dd/MMM/yyyy") != 0
                    && configInfo.get(TIMEFORMAT_POSITION).compareTo("dd/M/yyyy") != 0
                    && configInfo.get(TIMEFORMAT_POSITION).compareTo("dd MMM yyyy") != 0) {
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
		configInfo.add(defaultRowColor);
		configInfo.add(defaultAltRowColor);
		configInfo.add(defaultToastColor);
		configInfo.add(defaultFileName);
		configInfo.add(defaultDateFormat.toPattern());
		configInfo.add(defaultTimeFormat.toPattern());
		
		Storage.getInstance().writeConfigFile(configInfo);
	}
	
	/**************** Accessors ***********************/
	public String getDefaultFileName() {
		return defaultFileName;
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
	
	public void setDefaultlogLevel(String level){
	    defaultLogLevel = level;
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
	
	public void setDateFormat(String dateFormat){
	    defaultDateFormat = new SimpleDateFormat(dateFormat);
	}
	
	public void setTimeFormat(String timeFormat) {
		defaultTimeFormat = new SimpleDateFormat(timeFormat);
	}
	

}
