package taskaler.test.integration;

import static org.junit.Assert.*;

import org.junit.Test;

import taskaler.archive.Undo;
import taskaler.common.data.Task;
import taskaler.logic.OPLogic;
import taskaler.logic.SearchLogic;

//@author A0111798X
public class OPLogicUndoIntegrationTest {
	

	private static final String EDITED_DESCRIPTION = "Edited task 1.";
	private static final String EDITED_NAME = "Edited task name";
	private static final String TASK3_WORKLOAD = "3";
	private static final String TASK3_END_TIME = "23:15";
	private static final String TASK3_START_TIME = "07:45";
	private static final String TASK3_CREATION_DATE = "08/11/2014";
	private static final String TASK3_DESCRIPTION = "Test task 3";
	private static final String TASK3_NAME = "Task 3";
	private static final String TASK2_WORKLOAD = "1";
	private static final String TASK2_END_TIME = "22:59";
	private static final String TASK2_START_TIME = "19:00";
	private static final String TASK2_CREATION_DATE = "07/11/2014";
	private static final String TASK2_DESCRIPTION = "Test task 2";
	private static final String TASK2_NAME = "Task 2";
	private static final String TASK1_NAME = "Task 1";
	private static final String TASK1_WORKLOAD = "2";
	private static final String TASK1_END_TIME = "23:59";
	private static final String TASK1_START_TIME = "09:00";
	private static final String TASK1_CREATION_DATE = "06/11/2014";
	private static final String TASK1_DESCRIPTION = "Test task 1";
	/**
	 * Test undo method in the Undo class interacting with OPLogic class
	 * Equivalence Partition: no tasks in the undo stack, more than 1 task in 
	 * the undo stack
	 */
	
	
	/**
	 * Equivalence Partition: no task in the undo stack.(no command is executed)
	 */
	@Test
	public void test() {
		boolean switch1 = false;
		Undo undoObj = new Undo();
		Task result = undoObj.undo();
		if (result == null) {
			switch1 = true;
		}
		assertTrue(switch1);
	}
	
	/**
	 * Test case: User executes two add commands, a delete command and lastly a undo command.
	 * Equivalence Partition: more than 1 task in the undo stack
	 */
	@Test
	public void test2() {
		boolean switch1 = false;
		Undo undoObj = new Undo();
		OPLogic logic = OPLogic.getInstance();
		logic.addObserver(undoObj);
		Task task1 = logic.addTask(TASK1_NAME, TASK1_DESCRIPTION,
				TASK1_CREATION_DATE, TASK1_START_TIME, TASK1_END_TIME,
				TASK1_WORKLOAD);
		Task task2 = logic.addTask(TASK2_NAME, TASK2_DESCRIPTION,
				TASK2_CREATION_DATE, TASK2_START_TIME, TASK2_END_TIME,
				TASK2_WORKLOAD);
		logic.deleteTask(task1.getTaskID());
		Task result = undoObj.undo();
		if (result.getTaskName().equals(TASK1_NAME)) {
			switch1 = true;
		}
		assertTrue(switch1);
	}
	/**
	 * Test case: Users executes one add command, a edit command and lastly a undo command.
	 * Equivalence Partition: 1 task in the undo stack
	 */
	@Test
	public void test3() {
		boolean switch1 = false;
		Undo undoObj = new Undo();
		OPLogic logic = OPLogic.getInstance();
		logic.addObserver(undoObj);
		Task task3 = logic.addTask(TASK3_NAME, TASK3_DESCRIPTION,
				TASK3_CREATION_DATE, TASK3_START_TIME, TASK3_END_TIME,
				TASK3_WORKLOAD);
		logic.editTask(task3.getTaskID(), EDITED_NAME, EDITED_DESCRIPTION);
		undoObj.undo();
		SearchLogic search = new SearchLogic();
		Task result = search.findByID(task3.getTaskID());
		if (result.getTaskName().equals(TASK3_NAME)) {
			switch1 = true;
		}
		assertTrue(switch1);
	}
}
