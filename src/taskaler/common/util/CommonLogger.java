package taskaler.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;



public class CommonLogger {
	
	public final static Logger logger = Logger.getLogger(CommonLogger.class.getName());
	
	public static String FORMAT_DAY_MONTH_YEAR = "dd_MM_yyyy";
	public static String EXCEPTION_FILE_NAME = "resource/Exception_%s.log";
	
	public static void exceptionLogger(Exception error) throws Exception {
		try {
			String fileName = fileNameGenerator(EXCEPTION_FILE_NAME);
			handler_formatterSetUp(fileName);
			
			logger.log(Level.WARNING, error.getMessage(), error);
		} catch (Exception LOGGERFAIL) {
			//Log Fail
		}
	}


	private static void handler_formatterSetUp(String fileName) throws Exception {
		FileHandler fileHandler = new FileHandler(fileName, true);

		logger.setUseParentHandlers(false);

		LoggerFormatter formatter = new LoggerFormatter();
		fileHandler.setFormatter(formatter);
		logger.addHandler(fileHandler);
	}

	
	private static String fileNameGenerator(String fileNameType) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat calendarFformatter = new SimpleDateFormat(FORMAT_DAY_MONTH_YEAR);
		String date = calendarFformatter.format(currentDate.getTime());
		
		return String.format(fileNameType, date);
	}
	
}
