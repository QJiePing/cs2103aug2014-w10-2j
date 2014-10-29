package taskaler.controller.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import taskaler.controller.common.*;
import java.util.HashMap;

public class ParserTest {
    private static Parser newParser;
    private static HashMap<String, String> stateVariables = new HashMap<String, String>();
    
    //function to reset newParser
    private static void reset() throws Exception{
        newParser = new Parser();
        stateVariables = new HashMap<String, String>();
    }
    
    @Test
    public void test_parseADD(){
        try{
            CmdType currentCMD;
            String[] currentParams;
            //This is the testing of valid syntax
            //Adding task with name but no description
            reset();
            newParser.parseCMD("add hello", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 6);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], null);
            
            //This is the testing of valid syntax
            //Adding task with name and description
            reset();
            newParser.parseCMD("add hello:world!", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 6);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], "world!");
            
            //This is the testing for invalid syntax
            //Incomplete Add
            reset();
            newParser.parseCMD("add hello:world!!, 20/november:1430-1530, 2", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 6);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], "world!!");
            assertEquals(currentParams[2], "20/11/2014");
            assertEquals(currentParams[3], "1430");
            assertEquals(currentParams[4], "1530");
            assertEquals(currentParams[5], "2");
            
            reset();
            newParser.parseCMD("add ", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 6);
            assertEquals(currentParams[0], null);
            assertEquals(currentParams[1], null);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void test_parseDELETE(){
        try{
            CmdType currentCMD;
            String[] currentParams;
            
            //This is the test for valid syntax
            reset();
            newParser.parseCMD("delete 1", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.DELETE);
            assertEquals(currentParams.length, 1);
            assertEquals(currentParams[0], "1");
            
            //Test for no task ID, but is in TaskPane
            reset();
            stateVariables.put("VIEW", "TaskPane");
            stateVariables.put("TASKID", "21");
            newParser.parseCMD("delete ", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.DELETE);
            assertEquals(currentParams.length, 1);
            assertEquals(currentParams[0], "21");
            
            //Test for no task ID, and not in TaskPane
            reset();
            try{
                newParser.parseCMD("delete ", stateVariables);
                currentCMD = newParser.getCommand();
                currentParams = newParser.getParameters();
                assertEquals(currentCMD, CmdType.DELETE);
                assertEquals(currentParams.length, 1);
                assertEquals(currentParams[0], "");
            }
            catch(Exception e){
                assertEquals(e.getMessage(), "Invalid task ID");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public void test_parseEDIT(){
        try{
            CmdType currentCMD;
            String[] currentParams;
            
            //This is the test for valid syntax
            reset();
            newParser.parseCMD("edit 1 bye: ha", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "1");
            assertEquals(currentParams[1], "bye");
            assertEquals(currentParams[2], "ha");
            
            //This is the test case for valid syntax
            reset();
            newParser.parseCMD("edit 2 :haha", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "2");
            assertEquals(currentParams[1], null);
            assertEquals(currentParams[2], "haha");
            
            //This is the test case for valid syntax
            reset();
            stateVariables.put("VIEW", "TaskPane");
            stateVariables.put("TASKID", "3");
            newParser.parseCMD("edit hahaha : byebye", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "3");
            assertEquals(currentParams[1], "hahaha");
            assertEquals(currentParams[2], "byebye");
            
            reset();
            stateVariables.put("VIEW", "TaskPane");
            stateVariables.put("TASKID", "4");
            newParser.parseCMD("edit ahah", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "4");
            assertEquals(currentParams[1], "ahah");
            assertEquals(currentParams[2], null);
            
            reset();
            try{
                newParser.parseCMD("edit ahah", stateVariables);
                currentCMD = newParser.getCommand();
                currentParams = newParser.getParameters();
                assertEquals(currentCMD, CmdType.EDIT);
                assertEquals(currentParams.length, 3);
                assertEquals(currentParams[0], "ahah");
                assertEquals(currentParams[1], "l");
                assertEquals(currentParams[2], null);
            }
            catch(Exception e){
                assertEquals(e.getMessage(), "Invalid task ID");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public void test_REPEAT(){
        CmdType currentCMD;
        String[] currentParams;
        
        try{
            //Test for no start and end date provided
            reset();
            newParser.parseCMD("repeat 11 alternate", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.REPEAT);
            assertEquals(currentParams[0], "11");
            assertEquals(currentParams[1], "2 DAY");
            assertEquals(currentParams[2], null);
            assertEquals(currentParams[3], null);
            
            //Test for no TaskID provided, but currently in TaskPane
            reset();
            stateVariables.put("VIEW", "TaskPane");
            stateVariables.put("TASKID", "12");
            newParser.parseCMD("repeat weekly, from 19/10/2014", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.REPEAT);
            assertEquals(currentParams[0], "12");
            assertEquals(currentParams[1], "1 WEEK");
            assertEquals(currentParams[2], "19/10/2014");
            assertEquals(currentParams[3], null);
            
            //Test for the full set of parameters
            reset();
            newParser.parseCMD("repeat 13 weekdays, from 19/Oct/2014 to 21/10/14", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.REPEAT);
            assertEquals(currentParams[0], "13");
            assertEquals(currentParams[1], "WEEKDAY");
            assertEquals(currentParams[2], "19/10/2014");
            assertEquals(currentParams[3], "21/10/2014");
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //This is the boundary case for the Parser
    @Test
    public void test_noSuchCommand(){
        CmdType currentCMD;
        String[] currentParams;
        try{
            reset();
            newParser.parseCMD("asdf", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.INVALID);
            assertNull(currentParams);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
