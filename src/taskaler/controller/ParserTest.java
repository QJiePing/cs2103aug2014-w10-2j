package taskaler.controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {
    private static Parser newParser;
    
    @Test
    public void test_getParam_ADD(){
        try{
            newParser.parseCMD("add hello");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void test(){
        
    }
}
