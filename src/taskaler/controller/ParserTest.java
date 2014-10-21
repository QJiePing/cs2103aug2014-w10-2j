package taskaler.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import taskaler.controller.Parser.CmdType;

/*
 * Parser just parses the user command string into its arguments, the other components will deal with 
 * flawed arguments, so there would not be boundary cases for parser
 */

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
            newParser.parseCMD("add hello");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 2);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], null);
            
            //This is the testing of valid syntax
            //Adding task with name and description
            reset();
            newParser.parseCMD("add hello -d world!");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 2);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], "world!");
            
            //This is the testing for invalid syntax
            //Incomplete Add
            reset();
            newParser.parseCMD("add ");
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
            newParser.parseCMD("delete 1");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.DELETE);
            assertEquals(currentParams.length, 1);
            assertEquals(currentParams[0], "1");
            
            //This is the test case for invalid syntax
            reset();
            newParser.parseCMD("delete ");
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
            newParser.parseCMD("edit 1 -n bye -d ha");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "1");
            assertEquals(currentParams[1], "bye");
            assertEquals(currentParams[2], "ha");
            
            //This is the test case for valid syntax
            reset();
            newParser.parseCMD("edit 2 -d haha");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "2");
            assertEquals(currentParams[1], null);
            assertEquals(currentParams[2], "haha");
            
            //This is the test case for valid syntax
            reset();
            newParser.parseCMD("edit 3 -d hahaha -n byebye");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "3");
            assertEquals(currentParams[1], "byebye");
            assertEquals(currentParams[2], "hahaha");
            
            //This is the test case for valid syntax
            reset();
            newParser.parseCMD("edit -d ahah");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "-d");
            assertEquals(currentParams[1], null);
            assertEquals(currentParams[2], "ahah");
            
            //This is the test for invalid syntax
            reset();
            newParser.parseCMD("edit -d lala");
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "-d");
            assertEquals(currentParams[1], null);
            assertEquals(currentParams[2], "lala");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
