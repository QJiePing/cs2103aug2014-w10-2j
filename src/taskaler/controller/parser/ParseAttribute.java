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
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);
        
        String[] dateFormat = tryDateFormats(paramDate);
        int numOfParams = Integer.parseInt(dateFormat[NUM_OF_PARAMS_INDEX]);
        Date date = (new SimpleDateFormat(dateFormat[FORMAT_INDEX])).parse(paramDate);
        
        if(numOfParams == 0){
            throw new Exception("Invalid date syntax");
        }
        else {
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
    
    public static String[] parseTimeRange(String time) throws Exception{
        int startTimeIndex = 0;
        int endTimeIndex = 1;
        
        String[] startAndEndTime = new String[2];
        int dashIndex = time.indexOf("-");
        int fromIndex = time.indexOf("from"); 
        int toIndex = time.indexOf("to");
        if(dashIndex != INVALID_VALUE){
            startAndEndTime[startTimeIndex] = parseTime(time.substring(0,dashIndex).trim());
            startAndEndTime[endTimeIndex] = parseTime(time.substring(dashIndex + LENGTH_OF_DASH).trim());
        }
        else if(fromIndex != INVALID_VALUE){
            if(toIndex != INVALID_VALUE){
                startAndEndTime[startTimeIndex] = 
                        parseTime(time.substring(fromIndex + LENGTH_OF_FROM, toIndex).trim());
                startAndEndTime[endTimeIndex] = parseTime(time.substring(toIndex + LENGTH_OF_TO).trim());
            }
            else {
                startAndEndTime[startTimeIndex] = 
                        parseTime(time.substring(fromIndex + LENGTH_OF_FROM).trim());
            }
        }
        else if(toIndex != INVALID_VALUE){
            startAndEndTime[endTimeIndex] = parseTime(time.substring(toIndex + LENGTH_OF_TO).trim());
        }
        else {
            try{
                startAndEndTime[startTimeIndex] = parseTime(time);
            }
            catch(Exception e){
                throw new Exception("Invalid time range syntax, try: <start time> - <end time>");
            }
        }
        return startAndEndTime;
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
            throw new Exception("Invalid time syntax");
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
            throw new Exception("Invalid workload attribute syntax");
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
            throw new Exception("Invalid pattern syntax");
        }
        else {
            return patternInSyntax;
        }
    }
}
