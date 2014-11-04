/**
 * @author Brendan Yong, A0108541M
 *
 */

package taskaler.controller.parser;

import static taskaler.controller.common.*;
import taskaler.common.util.parser.calendarToString;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ParseAttribute {
    
    /**
     * Parses the date and translates it into a consistent syntax, "dd/MM/yyyy"
     * 
     * @param paramDate
     * @return String date in correct format
     */
    public static String parseDate(String paramDate) throws Exception{
        if(paramDate.equals(null) || paramDate.equals("")){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);
        
        String[] dateFormat = tryDateFormats(paramDate);
        int numOfParams = Integer.parseInt(dateFormat[NUM_OF_PARAMS_INDEX]);
        Date date = null;
        if(numOfParams == 0){
            throw new Exception("Invalid date syntax, try: <dd/mm/yyyy>");
        }
        else {
            date = (new SimpleDateFormat(dateFormat[FORMAT_INDEX])).parse(paramDate);
            cal.setTime(date);
            if(numOfParams == 1){
                cal.set(Calendar.MONTH, currentMonth);
                cal.set(Calendar.YEAR, currentYear);
            }
            else if(numOfParams == 2){
                cal.set(Calendar.YEAR, currentYear);
            }
            String dateString = calendarToString.parseDate(cal);
            return dateString;
        }
    }
    
    /**
     * Method to try all date formats in availableDateSyntax
     * 
     * @param paramDate
     * @return String[] correct format for the date
     */
    private static String[] tryDateFormats(String paramDate){
        Date date = null;
        String[] dateFormat = null;
        for(int i = 0; i < ParserLibrary.availableDateSyntax.size(); i++){
            try{
                dateFormat = ParserLibrary.availableDateSyntax.get(i);
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat[FORMAT_INDEX]);
                date = sdf.parse(paramDate);
                break;
            }
            catch(Exception e){
                dateFormat = new String[]{"0", null};
            }
        }
        return dateFormat;
    }
    
    public static String[] parseRange(String variable, String type) throws Exception{
        int startIndex = 0;
        int endIndex = 1;
        
        String[] startAndEnd = new String[2];
        int dashIndex = variable.indexOf("-");
        int fromIndex = variable.indexOf("from"); 
        int toIndex = variable.indexOf("to");
        if(dashIndex != INVALID_VALUE){
            if(type.equalsIgnoreCase("date")){
                startAndEnd[startIndex] = parseDate(variable.substring(0,dashIndex).trim());
                startAndEnd[endIndex] = parseDate(variable.substring(dashIndex + LENGTH_OF_SYMBOL).trim());
            } else if(type.equalsIgnoreCase("time")){
                startAndEnd[startIndex] = parseTime(variable.substring(0,dashIndex).trim());
                startAndEnd[endIndex] = parseTime(variable.substring(dashIndex + LENGTH_OF_SYMBOL).trim());
            }
        }
        else if(fromIndex != INVALID_VALUE){
            if(toIndex != INVALID_VALUE){
                if(type.equalsIgnoreCase("date")){
                    startAndEnd[startIndex] = 
                        parseDate(variable.substring(fromIndex + LENGTH_OF_FROM, toIndex).trim());
                    startAndEnd[endIndex] = parseDate(variable.substring(toIndex + LENGTH_OF_TO).trim());
            
                } else if(type.equalsIgnoreCase("time")){
                    startAndEnd[startIndex] = 
                            parseTime(variable.substring(fromIndex + LENGTH_OF_FROM, toIndex).trim());
                        startAndEnd[endIndex] = parseTime(variable.substring(toIndex + LENGTH_OF_TO).trim());
                }
            }
            else {
                if(type.equalsIgnoreCase("date")){
                    startAndEnd[startIndex] = 
                            parseDate(variable.substring(fromIndex + LENGTH_OF_FROM).trim());
                } else if(type.equalsIgnoreCase("time")){
                    startAndEnd[startIndex] = 
                        parseTime(variable.substring(fromIndex + LENGTH_OF_FROM).trim());
                }
            }
        }
        else if(toIndex != INVALID_VALUE){
            if(type.equalsIgnoreCase("date")){
                startAndEnd[endIndex] = parseDate(variable.substring(toIndex + LENGTH_OF_TO).trim());
            } else if(type.equalsIgnoreCase("time")){
                startAndEnd[endIndex] = parseTime(variable.substring(toIndex + LENGTH_OF_TO).trim());
            }
        }
        else {
            try{
                if(type.equalsIgnoreCase("date")){
                    startAndEnd[startIndex] = parseDate(variable);
                } else if(type.equalsIgnoreCase("time")){
                    startAndEnd[startIndex] = parseTime(variable);
                }
            }
            catch(Exception e){
                if(type.equalsIgnoreCase("date")){
                    throw new Exception("Invalid date range syntax, try: <start date> - <end date>");
                } else if(type.equalsIgnoreCase("time")){
                    throw new Exception("Invalid time range syntax, try: <start time> - <end time>");
                }
            }
        }
        return startAndEnd;
    }
    
    /** 
     * Parses the time and translates it to a consistent syntax, "HHmm"
     * 
     * @param time
     * @return
     * @throws Exception
     */
    public static String parseTime(String time) throws Exception{
        SimpleDateFormat defaultSyntax = new SimpleDateFormat("HHmm");
        SimpleDateFormat sdf = null;
        String timeInSyntax = null;
        Date correctTime = null;
        if(time == null || time.equals("")){
            return null;
        }
        for(int i = 0; i < ParserLibrary.availableTimeSyntax.size(); i++){
            try{
                sdf = new SimpleDateFormat(ParserLibrary.availableTimeSyntax.get(i));
                correctTime = sdf.parse(time);
                timeInSyntax = defaultSyntax.format(correctTime);
                break;
            }
            catch(Exception e){
                ;
            }
        }
        if(timeInSyntax == null){
            throw new Exception("Invalid time syntax, try: HHmm");
        }
        return timeInSyntax;
    }
    
    /**
     * Parses the workload attribute and translates it to a consistent syntax
     * 
     * @param workload
     * @return String workload in correct format
     * @throws Exception
     */
    public static String parseWL(String workload) throws Exception {
        String workloadInSyntax = ParserLibrary.availableWorkloadSyntax.get(workload);
        if(workloadInSyntax == null){
            throw new Exception("Invalid workload attribute syntax, try: <1 or 2 or 3>");
        }
        else {
            return workloadInSyntax;
        }
    }
    
    /**
     * Parses the pattern keyword and translates it to a consistent syntax
     * 
     * @param pattern
     * @return String pattern in correct format
     * @throws Exception
     */
    public static String parsePattern(String pattern) throws Exception {
        String patternInSyntax = ParserLibrary.availablePatternSyntax.get(pattern);
        if(patternInSyntax == null){
            throw new Exception("Invalid pattern syntax, try: wednesday, or weekly, or last");
        }
        else {
            return patternInSyntax;
        }
    }
    
    public static String parseMonthYear(String monthYear, int currentYear) throws Exception {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
        SimpleDateFormat monthFormat2 = new SimpleDateFormat("M");
        SimpleDateFormat yearFormat = new SimpleDateFormat("y");
        SimpleDateFormat returnFormat = new SimpleDateFormat("M/yyyy");
        
        int dashIndex = monthYear.indexOf("-");
        int slashIndex = monthYear.indexOf("/");
        int spaceIndex = monthYear.indexOf(" ");
        String monthField = null;
        String yearField = null;
        int yearInt = 0;
        Calendar cal = Calendar.getInstance();
        if(dashIndex != INVALID_VALUE){
            monthField = monthYear.substring(0, dashIndex).trim();
            yearField = monthYear.substring(dashIndex + LENGTH_OF_SYMBOL).trim();
        } else if(slashIndex != INVALID_VALUE){
            monthField = monthYear.substring(0, slashIndex).trim();
            yearField = monthYear.substring(slashIndex + LENGTH_OF_SYMBOL).trim();
        } else if(spaceIndex != INVALID_VALUE){
            monthField = monthYear.substring(0, spaceIndex).trim();
            yearField = monthYear.substring(spaceIndex + LENGTH_OF_SYMBOL).trim();
        } else {
            monthField = monthYear;
            yearField = ""+currentYear;
        }
        try{
            cal.setTime(yearFormat.parse(yearField));
            yearInt = cal.get(Calendar.YEAR);
        } 
        catch(Exception e){
            throw new Exception("Invalid goto syntax, try: goto <MM/YYYY>");
        }
        try{
            cal.setTime(monthFormat.parse(monthField));
            cal.set(Calendar.YEAR, yearInt);
        }
        catch(Exception e){
            try{
                cal.setTime(monthFormat2.parse(monthYear));
                cal.set(Calendar.YEAR, yearInt);
            }
            catch(Exception f){
                throw new Exception("Invalid goto syntax, try: goto <MM/YYYY>");
            }
        }
        return returnFormat.format(cal.getTime());
    }
}
