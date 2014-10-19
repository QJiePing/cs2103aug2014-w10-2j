package taskaler.common.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import taskaler.common.util.parser.calendarToString;

public class CommonLogger {

    private static Logger logger = null;
    
    private static CommonLogger instance = null;
    
    private final static Level currentLogLevel = Level.ALL;

    private static final String FORMAT_DAY_MONTH_YEAR = "dd_MM_yyyy";
    private static final String EXCEPTION_FILE_NAME = "/log/Exception_%s.log";

    /**
     * Method to log an exception to the log file. If this method fails, an
     * error message will be thrown
     * 
     * @param error
     *            The exception to be logged
     * @param logLevel
     *            The level of exception to be logged; Accepted log levels are
     *            ALL, CONFIG, INFO, WARNING, SEVERE
     */
    public void exceptionLogger(Exception error, Level logLevel) {
        logger.log(logLevel, error.getMessage(), error);
    }

    /**
     * Method to set up the log file handler.
     * 
     * @param fileName
     *            The filename to be associated with this log
     * @throws IOException
     *             Thrown if an IO error is encountered while opening file
     * @throws SecurityException
     *             Thrown if there is a error with an existing security manager
     * @see FileHandler#FileHandler(String)
     */
    private void handler_formatterSetUp(String fileName)
            throws IOException, SecurityException {
        FileHandler fileHandler = new FileHandler(fileName, true);

        logger.setUseParentHandlers(false);

        LoggerFormatter formatter = new LoggerFormatter();
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
    }

    private CommonLogger() {
        try {
            logger = Logger.getLogger(CommonLogger.class.getName());
            String fileName = fileNameGenerator(EXCEPTION_FILE_NAME);
            handler_formatterSetUp(fileName);
            logger.setLevel(currentLogLevel);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static CommonLogger getInstance(){
        if(instance == null){
            instance = new CommonLogger();
        }
        
        return instance;
    }

    private static String fileNameGenerator(String fileNameType) {
        Calendar currentDate = Calendar.getInstance();
        String date = calendarToString.parseDate(currentDate,
                FORMAT_DAY_MONTH_YEAR);

        return String.format(fileNameType, date);
    }

}
