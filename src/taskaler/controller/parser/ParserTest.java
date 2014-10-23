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
            newParser.parseCMD("add hello", null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 2);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], null);
            
            //This is the testing of valid syntax
            //Adding task with name and description
            reset();
            newParser.parseCMD("add hello -d world!", null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 2);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], "world!");
            
            //This is the testing for invalid syntax
            //Incomplete Add
            reset();
            newParser.parseCMD("add ", null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 2);
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
            newParser.parseCMD("delete 1", null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.DELETE);
            assertEquals(currentParams.length, 1);
            assertEquals(currentParams[0], "1");
            
            //This is the test case for invalid syntax
            reset();
            newParser.parseCMD("delete ", null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.DELETE);
            assertEquals(currentParams.length, 1);
            assertEquals(currentParams[0], "");
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
            newParser.parseCMD("edit 1 -n bye -d ha", null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "1");
            assertEquals(currentParams[1], "bye");
            assertEquals(currentParams[2], "ha");
            
            //This is the test case for valid syntax
            reset();
            newParser.parseCMD("edit 2 -d haha", null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "2");
            assertEquals(currentParams[1], null);
            assertEquals(currentParams[2], "haha");
            
            //This is the test case for valid syntax
            reset();
            newParser.parseCMD("edit 3 -d hahaha -n byebye", null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "3");
            assertEquals(currentParams[1], "byebye");
            assertEquals(currentParams[2], "hahaha");
            
          //This is the test case for invalid syntax
            reset();
            newParser.parseCMD("edit -d ahah", null);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "-d");
            assertEquals(currentParams[1], null);
            assertEquals(currentParams[2], "ahah");
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
            newParser.parseCMD("asdf", null);
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
