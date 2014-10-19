/**
 * 
 */
package taskaler.common.util.parser;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Special Calendar Parser
 *
 */
public class calendarToString {
    // Special constants
    private static final String REG_MONTH_YEAR = "dd/MM/yyyy";

    /**
     * Custom calendar parser. The resulting string is in the form of dd/MM/yyyy
     * 
     * @param c
     *            Calendar object to be parsed into string
     * @return String representation of Calendar object
     */
    public static String parseDate(Calendar c) {
        SimpleDateFormat formatter = new SimpleDateFormat(REG_MONTH_YEAR);
        String deadline = formatter.format(c.getTime());

        return deadline;
    }

    /**
     * Custom calendar parser. The resulting string is in the form of the
     * specified regex
     * 
     * @param c
     *            The calendar object to be parsed
     * @param regex
     *            The format of the output string
     * @return String representation of the calendar object
     */
    public static String parseDate(Calendar c, String regex) {
        SimpleDateFormat formatter = new SimpleDateFormat(regex);
        String deadline = formatter.format(c.getTime());

        return deadline;
    }

    /**
     * Custom calendar parser. Converts the Calendar object into an array. The
     * resulting array has the following fields index 0 => dd index 1 => MM
     * index 2 => yyyy
     * 
     * @param c
     *            Calendar object to be parsed into array
     * @return Array representation of Calendar object
     */
    public static String[] toArray(Calendar c) {
        String deadLine = parseDate(c);
        String[] date = deadLine.split("/");
        return date;
    }
}
