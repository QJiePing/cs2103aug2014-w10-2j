//@author A0099778X

package taskaler.common.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LoggerFormatter extends Formatter {
    // this formatter code is modified from http://kodejava.org/how-do-i-create-a-custom-logger-formatter/
    // Create a DateFormat to format the logger timestamp.
    //
    private static final DateFormat dateFormat = new SimpleDateFormat("[dd/MM/yyyy] [hh:mm:ss]");
    
    @Override
	public String format(LogRecord record) {
    	StringBuilder builder = new StringBuilder(1000);
        builder.append(dateFormat.format(new Date(record.getMillis()))).append(" - ");
        builder.append(formatMessage(record));
        builder.append("\n");
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