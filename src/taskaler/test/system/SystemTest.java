package taskaler.test.system;

import static org.junit.Assert.*;
import taskaler.common.configurations.Configuration;
import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;
import taskaler.common.data.TaskList;
import taskaler.controller.*;
import taskaler.storage.TaskAndConfigStorage;
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
	
	private static final String CHANGED_TASK_NAME = "changedTaskName";
	private static final String TESTCASE4_CMD6 = "delete 3";
	private static final String TESTCASE4_CMD5 = "edit changedTaskName";
	private static final String TESTCASE4_CMD4 = "repeat 3 monday, to 24/11/2014";
	private static final String TESTCASE4_CMD3 = "add task3 :It is a repeated task, 3/11/2014, 3";
	private static final String TESTCASE4_CMD2 = "add task2 :It is a deadline task, 29/10/2014, 2";
	private static final String FAILED_MSG_3 = "Integration test case 4 failed";
	private static final String FAILED_MSG_2 = "Failed empty command test";
	private static final String EMPTY_STRING = " ";
	private static final String FAILED_MSG_1 = "Failed illegal command test";
	private static final String TESTCASE2_CMD1 = "illegal command";
	private static final String TASK2_NAME = "task2";
	private static final String TASK1_NAME = "task1";
	private static final String TESTCASE1_RESULT1 = "changedTask3";
	private static final String UNDO_COMMAND = "undo";
	private static final String TESTCASE1_CMD5 = "delete 1";
	private static final String TESTCASE1_CMD4 = "edit changedTask3";
	private static final String TESTCASE1_CMD3 = "add task3 :It is a deadline task, 29/10/2014, 2";
	private static final String TESTCASE1_CMD2 = "add task2 :It is a float task2";
	private static final String COMMON_ADD_CMD = "add task1 :It is a float task";

	/**
	 * The file name should be the same as the file name state in the configuration file.
	 */
    private static final String TASK_LIST_FILE = "task_list";

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    static Controller controller = null;

    @After
    public void tearDownAfterClass() throws Exception {
        File f = new File(".\\taskaler\\"+TASK_LIST_FILE);
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
     * Equivalence Partition: valid command
     * Boundary Analysis: more than 1 tasks
     * 
     * Expected result: There are 2 float tasks and an edited deadline task with
     * the name changedTask3.
     */
	@Test
	public void test1() {
		TaskAndConfigStorage storeObj = TaskAndConfigStorage.getInstance();
		try {
			controller.start(new Stage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
		/**
         * Clear the file content for this test case
         */
        TaskList taskList=TaskList.getInstance();
        taskList.clear();
        storeObj.writeToFile(TASK_LIST_FILE, taskList);
        
		boolean switch1 = false;
		boolean switch2 = false;

		controller.executeCMD(COMMON_ADD_CMD);
		controller.executeCMD(TESTCASE1_CMD2);
		controller.executeCMD(TESTCASE1_CMD3);
		controller.executeCMD(TESTCASE1_CMD4);
		controller.executeCMD(TESTCASE1_CMD5);
		controller.executeCMD(UNDO_COMMAND);

		ArrayList<Object> tempArr = storeObj.readFromFile(TASK_LIST_FILE);
		ArrayList<FloatTask> floatArr = (ArrayList<FloatTask>) tempArr.get(0);
		ArrayList<DeadLineTask> deadlineArr = (ArrayList<DeadLineTask>) tempArr
				.get(1);

		if (deadlineArr.get(0).getTaskName().equals(TESTCASE1_RESULT1)) {
			switch1 = true;
		}

		if (floatArr.size() == 2) {
			if (floatArr.get(0).getTaskName().equals(TASK1_NAME)
					&& floatArr.get(1).getTaskName().equals(TASK2_NAME)) {
				switch2 = true;
			}
		}
		assertTrue((switch1 && switch2));
        /**
         * Clear the file content for other test case
         */
        taskList=TaskList.getInstance();
        taskList.clear();
        storeObj.writeToFile(TASK_LIST_FILE, taskList);
    }

    /**
     * Integration test 2
     * 
     * Test case: Executes any invalid command
     * 
     * Equivalence Partition: Invalid Command
     * Boundary Analysis: 0 task
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

		controller.executeCMD(TESTCASE2_CMD1);

		TaskAndConfigStorage storeObj = TaskAndConfigStorage.getInstance();
		ArrayList<Object> tempArr = storeObj.readFromFile(TASK_LIST_FILE);
		ArrayList<FloatTask> floatArr = (ArrayList<FloatTask>) tempArr.get(0);
		ArrayList<DeadLineTask> deadlineArr = (ArrayList<DeadLineTask>) tempArr
				.get(1);
		ArrayList<RepeatedTask> repeatedArr = (ArrayList<RepeatedTask>) tempArr
				.get(2);

		if (floatArr.size() == 0 && deadlineArr.size() == 0
				&& repeatedArr.size() == 0) {
			assertTrue(true);
		} else {
			fail(FAILED_MSG_1);
		}

	}
    /**
     * Integration 3
     * 
     * Test Case: Execute an empty command
     * 
     * Equivalence Partition: Empty Command
     * Boundary Analysis: 0 task
     * 
     * Expected Result: No changes are made to TaskList and program should not crash.
     */
    @Test
	public void test3() {
		try {
			controller.start(new Stage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		String cmd1 = EMPTY_STRING;

		TaskAndConfigStorage storeObj = TaskAndConfigStorage.getInstance();
		ArrayList<Object> tempArr = storeObj.readFromFile(TASK_LIST_FILE);
		ArrayList<FloatTask> floatArr = (ArrayList<FloatTask>) tempArr.get(0);
		ArrayList<DeadLineTask> deadlineArr = (ArrayList<DeadLineTask>) tempArr
				.get(1);
		ArrayList<RepeatedTask> repeatedArr = (ArrayList<RepeatedTask>) tempArr
				.get(2);

		int floatNum = floatArr.size();
		int deadlineNum = deadlineArr.size();
		int repeatedNum = repeatedArr.size();

		controller.executeCMD(cmd1);

		storeObj = TaskAndConfigStorage.getInstance();
		tempArr = storeObj.readFromFile(TASK_LIST_FILE);
		floatArr = (ArrayList<FloatTask>) tempArr.get(0);
		deadlineArr = (ArrayList<DeadLineTask>) tempArr.get(1);
		repeatedArr = (ArrayList<RepeatedTask>) tempArr.get(2);

		if (floatArr.size() == floatNum && deadlineArr.size() == deadlineNum
				&& repeatedArr.size() == repeatedNum) {
			assertTrue(true);
		} else {
			fail(FAILED_MSG_2);
		}
	}
    /**
     * Integration test 4
     * 
     * Test case: Add 1 float task, 1 deadline task and 1 repeated task. Change the task name of
     * the repeated task to changedTaskName. Then, delete the repeated task. Execute undo command.
     * 
     * Equivalence Partition: valid command
     * Boundary Analysis: 1 task in each arraylist
     * 
     * Expected result: There are 1 float task, 1 deadline task and 1 edited repeated task with
     * the name changedTaskName.
     */
	@Test
	public void test4() {
		try {
			controller.start(new Stage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		controller.executeCMD(COMMON_ADD_CMD);
		controller.executeCMD(TESTCASE4_CMD2);
		controller.executeCMD(TESTCASE4_CMD3);
		controller.executeCMD(TESTCASE4_CMD4);
		controller.executeCMD(TESTCASE4_CMD5);
		controller.executeCMD(TESTCASE4_CMD6);
		controller.executeCMD(UNDO_COMMAND);

		TaskAndConfigStorage storeObj = TaskAndConfigStorage.getInstance();
		ArrayList<Object> tempArr = storeObj.readFromFile(TASK_LIST_FILE);
		ArrayList<FloatTask> floatArr = (ArrayList<FloatTask>) tempArr.get(0);
		ArrayList<DeadLineTask> deadlineArr = (ArrayList<DeadLineTask>) tempArr
				.get(1);
		ArrayList<RepeatedTask> repeatedArr = (ArrayList<RepeatedTask>) tempArr
				.get(2);
		if (floatArr.get(0).getTaskName().equals(TASK1_NAME)
				&& deadlineArr.get(0).getTaskName().equals(TASK2_NAME)
				&& repeatedArr.get(0).getTaskName().equals(CHANGED_TASK_NAME)) {
			assertTrue(true);
		} else {
			fail(FAILED_MSG_3);
		}

		/**
		 * Clear the file content for other test case
		 */
		TaskList taskList = TaskList.getInstance();
		taskList.clear();
		storeObj.writeToFile(TASK_LIST_FILE, taskList);
	}
}
