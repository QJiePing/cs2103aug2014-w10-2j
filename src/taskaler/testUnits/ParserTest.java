//@author A0108541M

package taskaler.testUnits;

import static org.junit.Assert.*;

import org.junit.Test;

import taskaler.controller.common.*;
import taskaler.controller.parser.Parser;

import java.util.Calendar;
import java.util.HashMap;


public class ParserTest {
    private static Parser newParser;

    // stateVariables represent the "current" pane of the UI, and any relevant
    // information
    private static HashMap<String, String> stateVariables = new HashMap<String, String>();

    // function to reset newParser
    private static void reset() throws Exception {
        newParser = new Parser();
        stateVariables = new HashMap<String, String>();
    }

    @Test
    public void test_parseADD() {
        try {
            CmdType currentCMD;
            String[] currentParams;

            // Adding task with only name
            reset();
            newParser.parseCMD("add hello", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 6);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], null);

            // Adding task with name and description only
            reset();
            newParser.parseCMD("add hello:world!", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 6);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], "world!");

            // Adding task, with name, description, date, time and workload (All
            // parameters)
            reset();
            newParser.parseCMD("add bye:world??, 09/jan:from 10am to 1030, 2",
                    stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 6);
            assertEquals(currentParams[0], "bye");
            assertEquals(currentParams[1], "world??");
            assertEquals(currentParams[2], "09/01/2014");
            assertEquals(currentParams[3], "1000");
            assertEquals(currentParams[4], "1030");
            assertEquals(currentParams[5], "2");

            // Adding task, with name, description, time, and workload
            reset();
            newParser.parseCMD("add hello:world!!, :1430-1530, 2",
                    stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 6);
            assertEquals(currentParams[0], "hello");
            assertEquals(currentParams[1], "world!!");
            assertEquals(currentParams[2], null);
            assertEquals(currentParams[3], "1430");
            assertEquals(currentParams[4], "1530");
            assertEquals(currentParams[5], "2");

            // Invalid add
            reset();
            newParser.parseCMD("add ", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.ADD);
            assertEquals(currentParams.length, 6);
            assertEquals(currentParams[0], null);
            assertEquals(currentParams[1], null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_parseDELETE() {
        try {
            CmdType currentCMD;
            String[] currentParams;

            // Delete task 1
            reset();
            newParser.parseCMD("delete 1", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.DELETE);
            assertEquals(currentParams.length, 1);
            assertEquals(currentParams[0], "1");

            // Test for no task ID, but is in TaskPane
            reset();
            stateVariables.put("VIEW", "TaskPane");
            stateVariables.put("TASKID", "21");
            newParser.parseCMD("delete ", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.DELETE);
            assertEquals(currentParams.length, 1);
            assertEquals(currentParams[0], "21");

            // Test for no task ID, and not in TaskPane
            reset();
            try {
                newParser.parseCMD("delete ", stateVariables);
                currentCMD = newParser.getCommand();
                currentParams = newParser.getParameters();
                assertEquals(currentCMD, CmdType.DELETE);
                assertEquals(currentParams.length, 1);
                assertEquals(currentParams[0], "");
            } catch (Exception e) {
                assertEquals(e.getMessage(), "Invalid task ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_parseEDIT() {
        try {
            CmdType currentCMD;
            String[] currentParams;

            // edit task 1 with new name, and new description
            reset();
            newParser.parseCMD("edit 1 bye: ha", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "1");
            assertEquals(currentParams[1], "bye");
            assertEquals(currentParams[2], "ha");

            // edit task 2 with new description only
            reset();
            newParser.parseCMD("edit 2 :haha", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.EDIT);
            assertEquals(currentParams.length, 3);
            assertEquals(currentParams[0], "2");
            assertEquals(currentParams[1], null);
            assertEquals(currentParams[2], "haha");

            // edit on the task pane, task 3 with new name and new description
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

            // edit on the task pane, task 4 with new name only
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

            // edit without taskid but not on TaskPane
            reset();
            try {
                newParser.parseCMD("edit ahah", stateVariables);
                currentCMD = newParser.getCommand();
                currentParams = newParser.getParameters();
                assertEquals(currentCMD, CmdType.EDIT);
                assertEquals(currentParams.length, 3);
                assertEquals(currentParams[0], "ahah");
                assertEquals(currentParams[1], "l");
                assertEquals(currentParams[2], null);
            } catch (Exception e) {
                assertEquals(e.getMessage(), "Invalid task ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_parseWORKLOAD() {
        CmdType currentCMD;
        String[] currentParams;

        try {
            // edit workload of task 5
            reset();
            newParser.parseCMD("workload 5 high", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.WORKLOAD);
            assertEquals(currentParams[0], "5");
            assertEquals(currentParams[1], "3");

            // edit workload on the taskPane, task 6
            reset();
            stateVariables.put("VIEW", "TaskPane");
            stateVariables.put("TASKID", "6");
            newParser.parseCMD("workload 1", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.WORKLOAD);
            assertEquals(currentParams[0], "6");
            assertEquals(currentParams[1], "1");

            // edit without taskid, but not on TaskPane
            try {
                reset();
                newParser.parseCMD("workload", stateVariables);
                currentCMD = newParser.getCommand();
                currentParams = newParser.getParameters();
                assertEquals(currentCMD, CmdType.WORKLOAD);
                assertEquals(currentParams[0], null);
                assertEquals(currentParams[1], null);
            } catch (Exception e) {
                assertEquals(e.getMessage(),
                        "Invalid workload attribute syntax, try: <1 or 2 or 3>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_parseVIEW() {
        CmdType currentCMD;
        String[] currentParams;

        try {
            //view list mode
            reset();
            newParser.parseCMD("view l", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.VIEW);
            assertEquals(currentParams[0], "LIST");
            assertEquals(currentParams[1], null);
            
            //view calendar mode
            reset();
            newParser.parseCMD("view c", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.VIEW);
            assertEquals(currentParams[0], "CALENDAR");
            assertEquals(currentParams[1], null);
            
            //view specific task
            reset();
            newParser.parseCMD("view 23", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.VIEW);
            assertEquals(currentParams[0], "TASK");
            assertEquals(currentParams[1], "23");
            
            //view all tasks on a specific date
            reset();
            newParser.parseCMD("view d: 09/11", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.VIEW);
            assertEquals(currentParams[0], "DATE");
            assertEquals(currentParams[1], "09/11/2014");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_parseTIME() {
        CmdType currentCMD;
        String[] currentParams;

        try {
            // Editting time on task 11, no specification of start or end time
            reset();
            newParser.parseCMD("time 11 0900", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.TIME);
            assertEquals(currentParams[0], "11");
            assertEquals(currentParams[1], "0900");
            assertEquals(currentParams[2], null);

            // Editting time on task 12, only start time
            reset();
            newParser.parseCMD("time 12 from 0915", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.TIME);
            assertEquals(currentParams[0], "12");
            assertEquals(currentParams[1], "0915");
            assertEquals(currentParams[2], null);

            // Editting time on task 13, only end time
            reset();
            newParser.parseCMD("time 13 -1030", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.TIME);
            assertEquals(currentParams[0], "13");
            assertEquals(currentParams[1], null);
            assertEquals(currentParams[2], "1030");

            // Editting time on TaskPane, task 14, both start and end times
            reset();
            stateVariables.put("VIEW", "TaskPane");
            stateVariables.put("TASKID", "14");
            newParser.parseCMD("time 11am-1030", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.TIME);
            assertEquals(currentParams[0], "14");
            assertEquals(currentParams[1], "1100");
            assertEquals(currentParams[2], "1030");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_parseREPEAT() {
        CmdType currentCMD;
        String[] currentParams;

        try {
            // Test for no start and end date provided
            reset();
            newParser.parseCMD("repeat 11 alternate", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.REPEAT);
            assertEquals(currentParams[0], "11");
            assertEquals(currentParams[1], "2 DAY");
            assertEquals(currentParams[2], null);
            assertEquals(currentParams[3], null);

            // Test for no TaskID provided, but currently in TaskPane, only
            // startDate
            reset();
            stateVariables.put("VIEW", "TaskPane");
            stateVariables.put("TASKID", "12");
            newParser
                    .parseCMD("repeat weekly, from 19/10/2014", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.REPEAT);
            assertEquals(currentParams[0], "12");
            assertEquals(currentParams[1], "1 WEEK");
            assertEquals(currentParams[2], "19/10/2014");
            assertEquals(currentParams[3], null);

            // Test for no TaskID provided, but currently in TaskPane, only
            // endDate
            reset();
            stateVariables.put("VIEW", "TaskPane");
            stateVariables.put("TASKID", "12");
            newParser.parseCMD("repeat last, to 19/10/2014", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.REPEAT);
            assertEquals(currentParams[0], "12");
            assertEquals(currentParams[1], "LAST");
            assertEquals(currentParams[2], null);
            assertEquals(currentParams[3], "19/10/2014");

            // Test for the full set of parameters
            reset();
            newParser.parseCMD("repeat 13 weekdays, 19/Oct/2014 - 21/10/14",
                    stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.REPEAT);
            assertEquals(currentParams[0], "13");
            assertEquals(currentParams[1], "WEEKDAY");
            assertEquals(currentParams[2], "19/10/2014");
            assertEquals(currentParams[3], "21/10/2014");

            // Test for no taskID but not on taskPane
            try {
                reset();
                newParser.parseCMD("repeat", stateVariables);
                currentCMD = newParser.getCommand();
                currentParams = newParser.getParameters();
                assertEquals(currentCMD, CmdType.WORKLOAD);
                assertEquals(currentParams[0], null);
                assertEquals(currentParams[1], null);
            } catch (Exception e) {
                assertEquals(e.getMessage(), "Invalid task ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_parseGOTO() {
        CmdType currentCMD;
        String[] currentParams;
        Calendar cal = Calendar.getInstance();

        try {
            // next command, not on calendarPane (today's date is used)
            reset();
            newParser.parseCMD("next", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            String correctNextMonth = (cal.get(Calendar.MONTH) + 2) + "/"
                    + cal.get(Calendar.YEAR);
            assertEquals(currentCMD, CmdType.GOTO);
            assertEquals(currentParams[0], correctNextMonth);

            reset();
            // next command, on calendarPane, december 2015
            stateVariables.put("VIEW", "CALENDARPANE");
            stateVariables.put("CURRENTMONTH", "12");
            stateVariables.put("CURRENTYEAR", "2015");
            newParser.parseCMD("next", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.GOTO);
            assertEquals(currentParams[0], "1/2016");

            // back command, not on calendarPane (today's date is used)
            reset();
            newParser.parseCMD("back", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            String correctPrevMonth = (cal.get(Calendar.MONTH)) + "/"
                    + cal.get(Calendar.YEAR);
            assertEquals(currentCMD, CmdType.GOTO);
            assertEquals(currentParams[0], correctPrevMonth);

            // back command, on calendarPane, january 2015
            reset();
            stateVariables.put("VIEW", "CALENDARPANE");
            stateVariables.put("CURRENTMONTH", "1");
            stateVariables.put("CURRENTYEAR", "2015");
            newParser.parseCMD("back", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.GOTO);
            assertEquals(currentParams[0], "12/2014");

            // goto command, month and year
            reset();
            newParser.parseCMD("goto sep/16", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.GOTO);
            assertEquals(currentParams[0], "9/2016");

            // goto command, month only
            reset();
            newParser.parseCMD("goto 10", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.GOTO);
            assertEquals(currentParams[0], "10/2014");

            // goto command, month only, different format
            reset();
            newParser.parseCMD("goto nov", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.GOTO);
            assertEquals(currentParams[0], "11/2014");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // This is the boundary case for the Parser
    @Test
    public void test_noSuchCommand() {
        CmdType currentCMD;
        String[] currentParams;
        try {
            reset();
            newParser.parseCMD("asdf", stateVariables);
            currentCMD = newParser.getCommand();
            currentParams = newParser.getParameters();
            assertEquals(currentCMD, CmdType.INVALID);
            assertNull(currentParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
