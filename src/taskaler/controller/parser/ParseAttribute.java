package taskaler.controller.parser;

import static taskaler.controller.common.*;
import taskaler.common.util.parser.calendarToString;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ParseAttribute {
    /**
     * Method to check whether the date is in correct syntax, 
     * and translates it into the default date syntax 
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
     * Method to try all date formats in taskaler.common.java
     * 
     * @param paramDate
     * @return String[] correct format for the date
     */
    private static String[] tryDateFormats(String paramDate){
        Date date = null;
        String[] dateFormat = null;
        for(int i = 0; i < ParserLibrary.availableDateFormats.size(); i++){
            try{
                dateFormat = ParserLibrary.availableDateFormats.get(i);
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
    
    public static String parseTime(String time) throws Exception{
        return "";
    }
    
    public static String parseWL(String workload) throws Exception{
        String workloadInSyntax = ParserLibrary.availableWorkloadSyntax.get(workload);
        if(workloadInSyntax == null){
            throw new Exception("Invalid workload attribute syntax");
        }
        else {
            return workloadInSyntax;
        }
    }
}
