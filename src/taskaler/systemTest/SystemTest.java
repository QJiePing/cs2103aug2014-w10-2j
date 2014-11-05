package taskaler.systemTest;

import static org.junit.Assert.*;
import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;
import taskaler.controller.*;
import taskaler.storage.Storage;
import taskaler.ui.test.JavaFXThreadingRule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.stage.Stage;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

//@author A0111798X

public class SystemTest {
    private static final String TASK_LIST_FILE = "task_list";

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    static Controller controller = null;

    @After
    public void tearDownAfterClass() throws Exception {
        File f = new File(TASK_LIST_FILE);
        if (f.exists()) {
            f.delete();
        }
        controller = null;
    }

    @Before
    public void setUp() {
        controller = Controller.getInstance();
    }
    /**
     * Equivalence Partition: Invalid Command, Empty Command, Valid Command
     * Boundary Analysis: 0,1 or more than 1 tasks
     */

    /**
     * Integration test 1
     * 
     * Test case: Add 2 float task and 1 deadline task. Change the task name of
     * the deadline task to changedTask3. Then, delete one float task. Execute
     * undo command.
     * 
     * Boolean switch1: boolean value of whether the deadline task name is
     * changed. Boolean switch2: boolean value of whether there exist 2 float
     * tasks and check the task name is still the same.
     * 
     * Expected result: There are 2 float tasks and an edited deadline task with
     * the name changedTask3.
     */
    @Test
    public void test1() {
        try {
            controller.start(new Stage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
        boolean switch1 = false;
        boolean switch2 = false;
        String cmd1 = "add task1 :It is a float task";
        String cmd2 = "add task2 :It is a float task2";
        String cmd3 = "add task3 :It is a deadline task, 29/10/2014, 2";
        String cmd4 = "edit changedTask3";
        String cmd5 = "delete 1";
        String cmd6 = "undo";

        controller.executeCMD(cmd1);
        controller.executeCMD(cmd2);
        controller.executeCMD(cmd3);
        controller.executeCMD(cmd4);
        controller.executeCMD(cmd5);
        controller.executeCMD(cmd6);

        Storage storeObj = Storage.getInstance();
        ArrayList<Object> tempArr = storeObj.readFromFile(TASK_LIST_FILE);
        ArrayList<FloatTask> floatArr = (ArrayList<FloatTask>) tempArr.get(0);
        ArrayList<DeadLineTask> deadlineArr = (ArrayList<DeadLineTask>) tempArr
                .get(1);

        if (deadlineArr.get(0).getTaskName().equals("changedTask3")) {
            switch1 = true;
        }

        if (floatArr.size() == 2) {
            if (floatArr.get(0).getTaskName().equals("task1")
                    && floatArr.get(1).getTaskName().equals("task2")) {
                switch2 = true;
            }
        }

        assertTrue((switch1 && switch2));
    }

    /**
     * Integration test 2
     * 
     * Test case: Executes any invalid command
     * 
     * Expected result: There should not have any changes to the tasklist and
     * the components should not crash
     */
    @Test
    public void test2() {
        try {
            controller.start(new Stage());
        } catch (IOException e) {
            fail(e.getMessage());
        }

        String cmd1 = "illegal command";

        controller.executeCMD(cmd1);

        Storage storeObj = Storage.getInstance();
        ArrayList<Object> tempArr = storeObj.readFromFile(TASK_LIST_FILE);
        ArrayList<FloatTask> floatArr = (ArrayList<FloatTask>) tempArr.get(0);
        ArrayList<DeadLineTask> deadlineArr = (ArrayList<DeadLineTask>) tempArr
                .get(1);
        ArrayList<RepeatedTask> repeatedArr = (ArrayList<RepeatedTask>) tempArr
                .get(2);

        if (floatArr.size() == 0 && deadlineArr.size() == 0
                && repeatedArr.size() == 0) {
            assertTrue("Passed", true);
        } else {
            fail("Failed illegal command test");
        }

    }
    /**
     * Integration 3
     */
    @Test
    public void test3(){
    	
    }
}
