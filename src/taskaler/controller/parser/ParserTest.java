package taskaler.controller.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import taskaler.controller.common.*;

public class ParserTest {
    private static Parser newParser;
    
    //function to reset newParser
    private static void reset() throws Exception{
        newParser = new Parser();
    }
    
    @Test
    public void test_parseADD(){
        try{
            CmdType currentCMD;
            String[] currentParams;
            
            //This is the testing of valid syntax
            //Adding task with name but no description
            reset();
            newParser.parseCMD("add hello", null, null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 4);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], null);
            
            //This is the testing of valid syntax
            //Adding task with name and description
            reset();
            newParser.parseCMD("add hello:world!", null, null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 4);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], "world!");
            
            //This is the testing for invalid syntax
            //Incomplete Add
            reset();
            newParser.parseCMD("add ", null, null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 4);
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
            newParser.parseCMD("delete 1", null, null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.DELETE);
            assertEquals(currentParams.length, 1);
            assertEquals(currentParams[0], "1");
            
            //Test for no task ID, but is in TaskPane
            reset();
            newParser.parseCMD("delete ", "TaskPane", "21");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.DELETE);
            assertEquals(currentParams.length, 1);
            assertEquals(currentParams[0], "21");
            
            //Test for no task ID, and not in TaskPane
            reset();
            try{
                newParser.parseCMD("delete ", "CalendarPane", null);
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
            newParser.parseCMD("edit 1 bye: ha", null, null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "1");
            assertEquals(currentParams[1], "bye");
            assertEquals(currentParams[2], "ha");
            
            //This is the test case for valid syntax
            reset();
            newParser.parseCMD("edit 2 :haha", null, null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "2");
            assertEquals(currentParams[1], null);
            assertEquals(currentParams[2], "haha");
            
            //This is the test case for valid syntax
            reset();
            newParser.parseCMD("edit hahaha : byebye", "TaskPane", "3");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "3");
            assertEquals(currentParams[1], "hahaha");
            assertEquals(currentParams[2], "byebye");
            
            reset();
            newParser.parseCMD("edit ahah", "TaskPane", "4");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "4");
            assertEquals(currentParams[1], "ahah");
            assertEquals(currentParams[2], null);
            
            reset();
            try{
                newParser.parseCMD("edit ahah", null, null);
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
            newParser.parseCMD("repeat 11 alternate", null, null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.REPEAT);
            assertEquals(currentParams[0], "11");
            assertEquals(currentParams[1], "2 d");
            assertEquals(currentParams[2], null);
            assertEquals(currentParams[3], null);
            
            //Test for no TaskID provided, but currently in TaskPane
            reset();
            newParser.parseCMD("repeat weekly, from 19/10/2014", "TaskPane", "12");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.REPEAT);
            assertEquals(currentParams[0], "12");
            assertEquals(currentParams[1], "1 w");
            assertEquals(currentParams[2], "19/10/2014");
            assertEquals(currentParams[3], null);
            
            //Test for the full set of parameters
            reset();
            newParser.parseCMD("repeat 13 weekdays, from 19/Oct/2014 to 21/10/14", null, null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.REPEAT);
            assertEquals(currentParams[0], "13");
            assertEquals(currentParams[1], "wd");
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
            newParser.parseCMD("asdf", null, null);
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
