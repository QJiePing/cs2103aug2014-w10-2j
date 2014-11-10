package taskaler.common.configurations;

import taskaler.common.util.CommonLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import taskaler.storage.TaskAndConfigStorage;

//@author A0099778X
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

    // Variable to keep track of if this is first run of Taskaler. Not meant to
    // be stored by storage
    private static boolean isFirstRun = false;

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

    public static String DEFAULT_VIEW = "today";
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

    public ArrayList<String> availableColor = null;

    /**
     * configuration class constructor
     */
    private Configuration() {
        availableColor = new ArrayList<String>() {
            {
                add("#FFFFFF"); // white
                add("#66CCFF"); // light blue
                add("#FFFF00"); // yellow
                add("#FF0000"); // red
                add("#FF6699"); // pink
                add("#FF6666"); // peach
                add("#9966CC"); // bluish-purple
                add("#FF6600"); // orange
                add("#3366FF"); // blue
                add("#CC0099"); // violet
                add("#996600"); // mud brown
                add("#909090"); // grey
            }
        };
        isFirstRun = false;
        loadConfiguration();
    }

    /**
     * Method to get an exist instance of this object
     * 
     * @return An instance of this object
     */
    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }

    /**
     * loadConfiguration() will set all the configuration attributes to user
     * default set if configuration information is valid
     */
    public void loadConfiguration() {
        ArrayList<String> configInfo = TaskAndConfigStorage.getInstance()
                .readConfigFile();
        configInfo = checkConfigInfo(configInfo);

        if (configInfo == null) {
            defaultView = DEFAULT_VIEW;
            defaultLogLevel = DEFAULT_LOG_LEVEL;
            defaultWelcomeMsg = DEFAULT_WELCOME_MSG;
            defaultRowColor = DEFAULT_ROW_COLOR;
            defaultAltRowColor = DEFAULT_ALTROW_COLOR;
            defaultToastColor = DEFAULT_TOAST_COLOR;
            defaultDoneColor = DEFAULT_DONE_COLOR;
            defaultHeaderColor = DEFAULT_HEADER_COLOR;
            defaultFileName = DEFAULT_FILE_NAME;
            defaultTimeFormat = new SimpleDateFormat(DEFAULT_TIME_FORMAT);
            defaultDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
            storeConfigInfo();
            isFirstRun = true;
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
            defaultDateFormat = new SimpleDateFormat(
                    configInfo.get(DATEFORMAT_POSITION));
            defaultTimeFormat = new SimpleDateFormat(
                    configInfo.get(TIMEFORMAT_POSITION));

        }

    }
    //@author A0111798X
    /**
     * checkConfigInfo(ArrayList<String> configInfo) will check the
     * configuration information are all valid
     * 
     * @param configInfo
     * @return return null if configInfo is not valid, otherwise return
     *         configInfo
     */

    private ArrayList<String> checkConfigInfo(ArrayList<String> configInfo) {

        if (configInfo == null || configInfo.size() != NUM_OF_ATTRIBUTE) {
            return null;
        } else {
            if (configInfo.get(VIEW_POISTION).compareToIgnoreCase("list") != 0
                    && configInfo.get(VIEW_POISTION).compareToIgnoreCase(
                            "calendar") != 0
                    && configInfo.get(VIEW_POISTION).compareToIgnoreCase(
                            "today") != 0) {
            	configInfo.set(VIEW_POISTION,DEFAULT_VIEW);
            }
            if (configInfo.get(LOG_LEVEL_POSITION).compareToIgnoreCase("all") != 0
                    && configInfo.get(LOG_LEVEL_POSITION).compareToIgnoreCase(
                            "none") != 0) {
            	configInfo.set(LOG_LEVEL_POSITION,DEFAULT_LOG_LEVEL);
            }
            if (configInfo.get(WECOME_MSG_POSITION).isEmpty()) {
            	configInfo.set(WECOME_MSG_POSITION,DEFAULT_WELCOME_MSG);
            }
      
            if (!availableColor.contains(configInfo.get(ROW_COLOR_POSITION))) {
            	configInfo.set(ROW_COLOR_POSITION,DEFAULT_ROW_COLOR);
            }

            if (!availableColor.contains(configInfo.get(ALTROW_COLOR_POSITION))) {
            	configInfo.set(ALTROW_COLOR_POSITION,DEFAULT_ALTROW_COLOR);
            }

            if (!availableColor.contains(configInfo.get(TOAST_COLOR_POSITION))) {
            	configInfo.set(TOAST_COLOR_POSITION,DEFAULT_TOAST_COLOR);
            }

            if (!availableColor.contains(configInfo.get(DONE_COLOR_POSITION))) {
            	configInfo.set(DONE_COLOR_POSITION,DEFAULT_DONE_COLOR);
            }
            
            if (!availableColor.contains(configInfo.get(HEADER_COLOR_POSITION))) {
            	configInfo.set(HEADER_COLOR_POSITION,DEFAULT_HEADER_COLOR);
            }
            
            if(configInfo.get(FILENAME_POSITION).isEmpty()){
            	configInfo.set(FILENAME_POSITION,DEFAULT_FILE_NAME);
            }

            if (configInfo.get(TIMEFORMAT_POSITION).compareTo("hh:mm aa") != 0
                    && configInfo.get(TIMEFORMAT_POSITION).compareTo("HH:mm") != 0) {
            	configInfo.set(TIMEFORMAT_POSITION,DEFAULT_TIME_FORMAT);
            }
        }

        return configInfo;
    }
    
    //@author A0099778X
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

        TaskAndConfigStorage.getInstance().writeConfigFile(configInfo);
    }

    /**
     * Accessor to get default file name
     * @return default file name
     */
    public String getDefaultFileName() {
        return defaultFileName;
    }

    /**
     * Accessor to get default log level
     * @return default log level name
     */
    public String getLogLevel() {
        return defaultLogLevel;
    }

    /**
     * Accessor to get default welcome message
     * @return default welcome message
     */
    public String getWelcomeMsg() {
        return defaultWelcomeMsg;
    }

    /**
     * Accessor to get default view
     * @return default view
     */
    public String getDefaultView() {
        return defaultView;
    }

    /**
     * Accessor to get default row color
     * @return default row color
     */
    public String getDefaultRowColor() {
        return defaultRowColor;
    }

    /**
     * Accessor to get default alternate row color
     * @return default alternate row color
     */
    public String getDefaultAltRowColor() {
        return defaultAltRowColor;
    }

    /**
     * Accessor to get default toast color
     * @return default alternate toast color
     */
    public String getDefaultToastColor() {
        return defaultToastColor;
    }

    /**
     * Accessor to get default color for completed task
     * @return default color for completed task
     */
    public String getDefaultDoneColor() {
        return defaultDoneColor;
    }

    /**
     * Accessor to get default header color
     * @return default header color
     */
    public String getDefaultHeaderColor() {
        return defaultHeaderColor;
    }

    /**
     * Accessor to get default data format
     * @return default data format
     */
    public String getDateFormat() {
        return defaultDateFormat.toPattern();
    }

    /**
     * Accessor to get default time format
     * @return default time format
     */
    public String getTimeFormat() {
        return defaultTimeFormat.toPattern();
    }

    /**
     * Accessor to check whether the user is running the taskaler first time
     * @return true if user is running the taskaler first time, else false.
     */
    public boolean getIsFirstRun() {
        return isFirstRun;
    }

    /**************** Mutators ************************/
    /**
     * Mutators to set default file name
     * @return default default file name
     */
    public void setDefaultFileName(String fileName) {
    	defaultFileName = fileName;
    }

    /**
     * Mutator to set default log level
     * @return default log level name
     */
    public void setDefaultLogLevel(String level) {
        defaultLogLevel = level;
    }

    /**
     * Mutator to set default welcome message
     * @return default welcome message
     */
    public void setDefaultWelcomeMsg(String msg) {
        defaultWelcomeMsg = msg;
    }

    /**
     * Mutator to set default view
     * @return default view
     */
    public void setDefaultView(String view) {
        defaultView = view;
    }

    /**
     * Mutator to set default row color
     * @return default row color
     */
    public void setDefaultRowColor(String color) {
        defaultRowColor = color;
    }

    /**
     * Mutator to set default alternate row color
     * @return default alternate row color
     */
    public void setDefaultAltRowColor(String color) {
        defaultAltRowColor = color;
    }

    /**
     * Mutator to set default toast color
     * @return default alternate toast color
     */
    public void setDefaultToastColor(String color) {
        defaultToastColor = color;
    }

    /**
     * Mutator to set default color for completed task
     * @return default color for completed task
     */
    public void setDefaultDoneColor(String color) {
        defaultDoneColor = color;
    }

    /**
     * Mutator to set default header color
     * @return default header color
     */
    public void setDefaultHeaderColor(String color) {
        defaultHeaderColor = color;
    }

    /**
     * Mutator to set default data format
     * @return default data format
     */
    public void setDateFormat(String dateFormat) {
        defaultDateFormat = new SimpleDateFormat(dateFormat);
    }

    /**
     * Mutator to set default time format
     * @return default time format
     */
    public void setTimeFormat(String timeFormat) {
        defaultTimeFormat = new SimpleDateFormat(timeFormat);
    }

}
