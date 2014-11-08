package taskaler.common.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


//@author A0099778X

public class LoggerFormatter extends Formatter {
    // this formatter code is modified from http://kodejava.org/how-do-i-create-a-custom-logger-formatter/
    // Create a DateFormat to format the logger timestamp.
    //
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("[dd/MM/yyyy] [hh:mm:ss]");
    
    private static int BUILDER_CAPACITY = 1000;
    
    private static String CHAR_SEPARATOR = " - ";
    private static String CHAR_NEWLINE = "\n";
    
    @Override
	public String format(LogRecord record) {
    	
    	StringBuilder builder = new StringBuilder(BUILDER_CAPACITY);
        builder.append(DATE_FORMAT.format(new Date(record.getMillis()))).append(CHAR_SEPARATOR);
        builder.append(formatMessage(record));
        builder.append(CHAR_NEWLINE);
        
        return builder.toString();
    }

 
    @Override
	public String getHead(Handler h) {
        return super.getHead(h);
    }
 
    @Override
	public String getTail(Handler h) {
        return super.getTail(h);
    }
}