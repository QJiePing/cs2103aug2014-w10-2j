/**
 * 
 */
package taskaler.ui.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Abstract Class that all Models with extend
 * 
 * @author Cheah Kit Weng, A0059806W
 *
 */
public abstract class AbstractModel {
    
    // Special constants
    private static final String REG_MONTH_YEAR = "dd/MM/yyyy";
    
    /**
     * Custom calendar parser
     * 
     * @param c Calendar object to be parsed into string
     * @return 
     */
    public static String parseDate(Calendar c){
        SimpleDateFormat formatter = new SimpleDateFormat(REG_MONTH_YEAR);
        String deadline = formatter.format(c.getTime());
        
        return deadline;
    }
}
