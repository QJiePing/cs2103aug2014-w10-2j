import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;



public class ArchiveFunction {
	
	public final static Logger logger = Logger.getLogger(ArchiveFunction.class.getName());
	
	public static String FORMAT_DAY_MONTH_YEAR = "dd_MM_yyyy";
	public static String EXCEPTION_FILE_NAME = "resource/Exception_%s.log";
	public static String HISTORY_FILE_NAME = "resource/History_%s.log";
	
	public static void exceptionLogger(Exception error) throws Exception {
		String fileName = fileNameGenerator(EXCEPTION_FILE_NAME);
		handler_formatterSetUp(fileName);
		
		logger.log(Level.WARNING, error.getMessage(), error);
	}

	
	public static void historyLogger(String message) throws Exception {
		String fileName = fileNameGenerator(HISTORY_FILE_NAME);
		handler_formatterSetUp(fileName);
		
		logger.log(Level.WARNING, message);
	}
	
	public static String archiveHistory(String date) throws Exception {
		if(date.length() == 0) {
			date = fileNameGenerator(HISTORY_FILE_NAME);
		}
		BufferedReader newReader = new BufferedReader(new FileReader(date));
		
		String historyOperation = "";
		String logRecord = "";
		while((logRecord = newReader.readLine())!=null && logRecord.length()!=0) {
			historyOperation += logRecord +"\n";
		}
		
		newReader.close();
		return historyOperation;
	}
	
	private static void handler_formatterSetUp(String fileName) throws Exception {
		FileHandler fileHandler = new FileHandler(fileName, true);

		logger.setUseParentHandlers(false);

		MyFormatter formatter = new MyFormatter();
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


class MyFormatter extends Formatter {
    // this formatter code is modified from http://kodejava.org/how-do-i-create-a-custom-logger-formatter/
    // Create a DateFormat to format the logger timestamp.
    //
    private static final DateFormat dateFormat = new SimpleDateFormat("[dd/MM/yyyy] [hh:mm:ss]");
    
    public String format(LogRecord record) {
    	StringBuilder builder = new StringBuilder(1000);
        builder.append(dateFormat.format(new Date(record.getMillis()))).append(" - ");
        builder.append(formatMessage(record));
        builder.append("\n");
        return builder.toString();
    }

 
    public String getHead(Handler h) {
        return super.getHead(h);
    }
 
    public String getTail(Handler h) {
        return super.getTail(h);
    }
}