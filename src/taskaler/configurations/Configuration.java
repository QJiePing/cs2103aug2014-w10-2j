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
	private String defaultFontColor = null;
	private String defaultFileName = null;
	private SimpleDateFormat defaultTimeFormat = null;
	
	public static final int VIEW_POISTION = 0;
	public static final int COLOR_POSITION = 1;
	public static final int FILENAME_POSITION = 2;
	public static final int TIMEFORMAT_POSITION = 3;
	public static final int NUM_OF_ATTRIBUTE = 4;
	public static final String Config_File="config_file";
	
	public static String DEFAULT_VIEW = "list";
	public static String DEFAULT_FONT_COLOR = "black";
	public static String DEFAULT_FILE_NAME = "task_list";
	public static String DEFAULT_TIME_FORMAT = "HHmm";
	
	private static CommonLogger log = CommonLogger.getInstance();
	
    public static final ArrayList<String> availableColor = new ArrayList<String>()
            {{  add("black");
                add("red");
                add("organe");
                add("yellow");
                add("green");
                add("blue");
                add("violet");
            }};
	

	private Configuration(){
		File f= new File(Config_File);
		try{
		if(!f.exists()){
			f.createNewFile();
		}
		}catch(Exception e){
			log.exceptionLogger(e, Level.SEVERE);
		}
		
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
		ArrayList<String> configInfo = Storage.getInstance().readConfigFile(Config_File);
		configInfo = checkConfigInfo(configInfo);
		
		if(configInfo == null) {
			defaultView = DEFAULT_VIEW;
			defaultFontColor = DEFAULT_FONT_COLOR;
			defaultFileName = DEFAULT_FILE_NAME ;
			defaultTimeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
			storeConfigInfo();
		} else {
			defaultView = configInfo.get(VIEW_POISTION);
			defaultFontColor = configInfo.get(COLOR_POSITION);
			defaultFileName = configInfo.get(FILENAME_POSITION);
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
			
			if(!availableColor.contains(configInfo.get(COLOR_POSITION).toLowerCase())) {
				return null;
			}
			
			if (configInfo.get(TIMEFORMAT_POSITION).compareTo("hhmm") != 0
					&& configInfo.get(TIMEFORMAT_POSITION).compareTo("HHmm") != 0) {
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
		configInfo.add(defaultFontColor);
		configInfo.add(defaultFileName);
		configInfo.add(defaultTimeFormat.toPattern());
		
		Storage.getInstance().writeConfigFile(Config_File,configInfo);
	}
	
	/**************** Accessors ***********************/
	public String getDefaultFileName() {
		return defaultFileName;
	}
	
	public String getDefaultView() {
		return defaultView;
	}
	
	public String getDefaultFontColor() {
		return defaultFontColor;
	}
	
	public String getTimeFormat() {
		return defaultTimeFormat.toPattern();
	}
	
	
	/**************** Mutators ************************/
	public void setDefaultFileName(String fileName) {
		defaultFileName = fileName;
	}
	
	public void setDefaultView(String view) {
		defaultView = view;
	}
	
	public void setDefaultFontColor(String color) {
		defaultFontColor = color;
	}
	
	public void setTimeFormat(String timeFormat) {
		defaultTimeFormat = new SimpleDateFormat(timeFormat);
	}
	

}
