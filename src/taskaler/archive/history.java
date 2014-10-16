package taskaler.archive;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import taskaler.storage.Storage;

public class history {
	public static String FORMAT_DAY_MONTH_YEAR = "dd_MM_yyyy";
	public static String HISTORY_FILE_NAME = "History_%s.log";
	

	public static void logHistory(String message) throws Exception {
		String fileName = fileNameGenerator();
		Storage.writeToHistoryLogger(fileName, message);
	}
	
	public static String retrieveHistory(String date) {
		String fileName;
		if(date.length() == 0) {
			fileName = fileNameGenerator();
		} else {
			String[] fileNameDate = date.split("/");
			fileName = String.format("History_%s_%s_%s.log", fileNameDate[0], fileNameDate[1], fileNameDate[2]);
		}
	
		String currentHistory = Storage.readFromHistoryLogger(fileName);
		
		if(currentHistory == null) {
			return "No history";
		}
		
		return currentHistory;
		
	}
	
	
	private static String fileNameGenerator() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat calendarFformatter = new SimpleDateFormat(FORMAT_DAY_MONTH_YEAR);
		String date = calendarFformatter.format(currentDate.getTime());
		
		return String.format(HISTORY_FILE_NAME, date);
	}
}
