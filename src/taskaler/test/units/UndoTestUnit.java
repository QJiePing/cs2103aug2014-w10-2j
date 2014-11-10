package taskaler.test.units;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import taskaler.archive.OperationRecord;
import taskaler.archive.Undo;
import taskaler.common.data.Task;
import taskaler.common.data.DeadLineTask;
import taskaler.common.data.FloatTask;
import taskaler.common.data.RepeatedTask;

//@author A0111798X

public class UndoTestUnit {

	private static final String INVALID_CMD = "abc";
	private static final String EMPTY_STRING = "";
	private static final String WORKLOAD_CMD = "WORKLOAD";
	private static final String DATE_CMD = "DATE";
	private static final String EDIT_CMD = "EDIT";
	private static final String DELETE_CMD = "DELETE";
	private static final String ADD_CMD = "ADD";
	private static final int VALUE_5 = 5;
	private static final String WEEKLY_VALUE = "weekly";
	private static final String TASK2_NAME = "Task2";
	private static final String TASK3_DESCRIPTION = "description2";
	private static final String TASK3_NAME = "Task3";
	private static final String TASK1_DESCRIPTION = "description1";
	private static final String STRING_VALUE_2 = "2";
	private static final String STRING_VALUE_3 = "3";
	private static final String STRING_VALUE_1 = "1";
	/**
	 * viewUndoMethod 
	 * Equivalence partition: empty stack, not empty stack (more
	 * than 1 operationRecord object)
	 */
	/**
	 * inverseFunction 
	 * Equivalence partition: empty command, valid command,
	 * invalid command
	 */

	private static final String TASK1_NAME = "Task1";

	/**
	 * Testing viewUndo function of the Undo class 
	 * Equivalence partition:Stack<OperationRecord<Task,String>> is empty
	 */
	@Test
	public void test1() {
		Undo undoObj = new Undo();
		ArrayList<OperationRecord<Task, String>> result = undoObj.viewUndo();
		assertTrue(result.isEmpty());
	}

	/**
	 * Testing viewUndo function of the Undo class 
	 * Equivalence partition:Stack<OperationRecord<Task,String>> is not empty and contains
	 * more than 1 task.
	 */
	@Test
	public void test2() {
		/**
		 * Prepare all the data for testing
		 */
		Undo undoObj = new Undo();
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.YEAR, Calendar.DECEMBER, Calendar.MONDAY);
		ArrayList<Calendar> arrCal = new ArrayList<Calendar>();
		arrCal.add(temp);
		FloatTask floatTask = new FloatTask(TASK1_NAME, STRING_VALUE_1, true,
				Calendar.getInstance(), STRING_VALUE_3, TASK1_DESCRIPTION,
				Calendar.getInstance(), Calendar.getInstance());
		RepeatedTask repeatTask = new RepeatedTask(TASK3_NAME, STRING_VALUE_3,
				true, Calendar.getInstance(), STRING_VALUE_2,
				TASK3_DESCRIPTION, Calendar.getInstance(),
				Calendar.getInstance(), WEEKLY_VALUE, arrCal,
				Calendar.getInstance(), VALUE_5);
		DeadLineTask deadlineTask = new DeadLineTask(TASK2_NAME,
				STRING_VALUE_2, false, Calendar.getInstance(), STRING_VALUE_1,
				TASK3_DESCRIPTION, Calendar.getInstance(),
				Calendar.getInstance(), Calendar.getInstance());
		/**
		 * Testing
		 */
		undoObj.saveOperation(floatTask, ADD_CMD);
		undoObj.saveOperation(repeatTask, "DELTE");
		undoObj.saveOperation(deadlineTask, EDIT_CMD);

		ArrayList<OperationRecord<Task, String>> result = undoObj.viewUndo();
		boolean switch1 = false;
		boolean switch2 = false;
		boolean switch3 = false;
		if (result.get(0).getTask().equals(deadlineTask))
			switch1 = true;
		if (result.get(1).getTask().equals(repeatTask))
			switch2 = true;
		if (result.get(2).getTask().equals(floatTask))
			switch3 = true;
		assertTrue(switch1 && switch2 && switch3);

	}

	/**
	 * Testing inverseFuncton of Undo class 
	 * Equivalence Partition: empty command
	 */
	@Test
	public void test3() {
		boolean switch1 = false;
		Undo undoObj = new Undo();
		String result = undoObj.inverseFunction(EMPTY_STRING);
		if (result.isEmpty()) {
			switch1 = true;
		}
		assertTrue(switch1);
	}

	/**
	 * Testing inverseFunction of Undo class 
	 * Equivalence Partition: valid command
	 */
	@Test
	public void test4() {
		boolean switch1 = false;
		Undo undoObj = new Undo();
		String result = undoObj.inverseFunction(ADD_CMD);
		String result2 = undoObj.inverseFunction(DELETE_CMD);
		String result3 = undoObj.inverseFunction(EDIT_CMD);
		String result4 = undoObj.inverseFunction(DATE_CMD);
		String result5 = undoObj.inverseFunction(WORKLOAD_CMD);

		if (result.equals(DELETE_CMD) && result2.equals(ADD_CMD)
				&& result3.equals(EDIT_CMD) && result4.equals(EDIT_CMD)
				&& result5.equals(EDIT_CMD)) {
			switch1 = true;
		}
		assertTrue(switch1);
	}

	/**
	 * Testing inverseFunction of Undo class 
	 * Equivalence Partition: invalid command
	 */
	@Test
	public void test5() {
		boolean switch1 = false;
		Undo undoObj = new Undo();
		String result = undoObj.inverseFunction(INVALID_CMD);

		if (result.equals(EMPTY_STRING)) {
			switch1 = true;
		}
		assertTrue(switch1);
	}
}
