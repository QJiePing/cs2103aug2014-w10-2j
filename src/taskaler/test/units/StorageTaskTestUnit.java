package taskaler.test.units;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.Test;

import taskaler.common.data.*;
import taskaler.storage.TaskAndConfigStorage;

//@author A0111798X

public class StorageTaskTestUnit {

	private static final String TASK4_DESCRIPTION = "description4";
	private static final String STRING_VALUE_4 = "4";
	private static final String TASK4_NAME = "Task4";
	private static final String WEEKLY_VALUE = "weekly";
	private static final String TASK3_NAME = "Task3";
	private static final String STRING_VALUE_3 = "3";
	private static final String TASK2_DESCRIPTION = "description2";
	private static final String STRING_VALUE_2 = "2";
	private static final String TASK2_NAME = "Task2";
	private static final String TASK1_DESCRIPTION = "description1";
	private static final String STRING_VALUE_5 = "5";
	private static final String STRING_VALUE_1 = "1";
	private static final String TASK1_NAME = "Task1";
	private static final String EMPTY_STRING = "";
	private static final String TESTING_FILE = "testing.txt";

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File f = new File(TESTING_FILE);
		f.delete();
	}
	
	/**
	 * Equivalence Partition: valid file name, empty file name, null file name
	 * Boundary Analysis: 0,1, more than 1 tasks
	 */


	/**
	 * Test for null filename
	 * 
	 * Equivalence partitioning: Null partition.
	 * Boundary Analysis:0
	 */
	@Test
	public void test1() {
		TaskList taskList = TaskList.getInstance();
		taskList.clear();
		TaskAndConfigStorage storeObj = TaskAndConfigStorage.getInstance();
		boolean result = storeObj.writeToFile(null, taskList);
		assertFalse(result);
	}
	/**
	 * Test for empty filename
	 * 
	 * Equivalence partitioning: Empty partition.
	 * Boundary Analysis:0
	 */
	@Test
	public void test2() {
		TaskList taskList = TaskList.getInstance();
		taskList.clear();
		TaskAndConfigStorage storeObj = TaskAndConfigStorage.getInstance();
		boolean result = storeObj.writeToFile(EMPTY_STRING, taskList);
		assertFalse(result);
	}

	/**
	 * Testing the case of an empty task list
	 * 
	 * Equivalence partitioning: valid file name.
	 * Boundary Analysis: 0
	 */
	@Test
	public void test3() {
		TaskList taskList = TaskList.getInstance();
		taskList.clear();
		TaskAndConfigStorage storeObj = TaskAndConfigStorage.getInstance();
		storeObj.writeToFile(TESTING_FILE, taskList);

		ArrayList<Object> tempArr = storeObj.readFromFile(TESTING_FILE);
		ArrayList<FloatTask> floatArr = (ArrayList<FloatTask>) tempArr.get(0);
		ArrayList<DeadLineTask> deadlineArr = (ArrayList<DeadLineTask>) tempArr
				.get(1);
		ArrayList<RepeatedTask> repeatArr = (ArrayList<RepeatedTask>) tempArr
				.get(2);

		assertTrue(floatArr.isEmpty() && deadlineArr.isEmpty()
				&& repeatArr.isEmpty());
	}

	/**
	 * Testing the case where there is 1 float task in TaskList
	 * 
	 * Combination:
	 * Equivalence partitioning: valid file name
	 * Boundary analysis: 1 float task, 0 deadline task, 0 repeated
	 */
	@Test
	public void test4() {
		boolean result = false;
		TaskList taskList = TaskList.getInstance();
		taskList.clear();
		taskList.add(new FloatTask(TASK1_NAME, STRING_VALUE_1, true, Calendar.getInstance(),
				STRING_VALUE_5, TASK1_DESCRIPTION, Calendar.getInstance(), Calendar
						.getInstance()));

		TaskAndConfigStorage storeObj = TaskAndConfigStorage.getInstance();
		storeObj.writeToFile(TESTING_FILE, taskList);

		ArrayList<Object> temp = storeObj.readFromFile(TESTING_FILE);
		ArrayList<FloatTask> floatArr = (ArrayList<FloatTask>) temp.get(0);
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).getTaskID().equals(floatArr.get(i).getTaskID())) {
				result = true;
			} else {
				result = false;
				break;
			}
		}
		assertTrue(result);
	}

	/**
	 * Testing the case where there is a mixture of 3 different type of tasks
	 * 
	 * Combination
	 * Equivalence partitioning: valid filename
	 * Boundary Analysis: 2 float tasks, 1 deadline task, 1 repeated task
	 */
	@Test
	public void test5() {
		/**
		 * boolean switch for each items in the TaskList
		 */
		boolean switch1 = false;
		boolean switch2 = false;
		boolean switch3 = false;
		boolean switch4 = false;

		/**
		 * Prepare the task object for the testing
		 */

		TaskList taskList = TaskList.getInstance();
		taskList.clear();
		ArrayList<Calendar> arrCal = new ArrayList<Calendar>();
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.YEAR, Calendar.DECEMBER, Calendar.MONDAY);
		arrCal.add(temp);
		taskList.add(new FloatTask(TASK1_NAME, STRING_VALUE_1, true, Calendar.getInstance(),
				STRING_VALUE_5, TASK1_DESCRIPTION, Calendar.getInstance(), Calendar
						.getInstance()));
		taskList.add(new DeadLineTask(TASK2_NAME, STRING_VALUE_2, false, Calendar
				.getInstance(), STRING_VALUE_1, TASK2_DESCRIPTION, Calendar.getInstance(),
				Calendar.getInstance(), Calendar.getInstance()));
		taskList.add(new RepeatedTask(TASK3_NAME, STRING_VALUE_3, true, Calendar
				.getInstance(), STRING_VALUE_2, TASK2_DESCRIPTION, Calendar.getInstance(),
				Calendar.getInstance(), WEEKLY_VALUE, arrCal, Calendar
						.getInstance(), 5));
		taskList.add(new FloatTask(TASK4_NAME, STRING_VALUE_4, true, Calendar.getInstance(),
				STRING_VALUE_1, TASK4_DESCRIPTION, Calendar.getInstance(), Calendar
						.getInstance()));

		/**
		 * Execute the writeToFile and readFromFile method
		 */
		TaskAndConfigStorage storeObj = TaskAndConfigStorage.getInstance();
		storeObj.writeToFile(TESTING_FILE, taskList);

		ArrayList<Object> tempArr = storeObj.readFromFile(TESTING_FILE);
		ArrayList<FloatTask> floatArr = (ArrayList<FloatTask>) tempArr.get(0);
		ArrayList<DeadLineTask> deadlineArr = (ArrayList<DeadLineTask>) tempArr
				.get(1);
		ArrayList<RepeatedTask> repeatArr = (ArrayList<RepeatedTask>) tempArr
				.get(2);

		/**
		 * Test if the boolean switch values to detect if there are any problems
		 * in both read and write methods.
		 * 
		 * TaskList will arrange the tasks such that FloatTask is in the first
		 * part of the array list, DeadLineTaask is in the second part of array
		 * list and repeatedTask is at the last part of the array list.
		 */
		if (taskList.get(0).getTaskID().equals(floatArr.get(0).getTaskID()))
			switch1 = true;
		if (taskList.get(2).getTaskID().equals(deadlineArr.get(0).getTaskID()))
			switch2 = true;
		if (taskList.get(3).getTaskID().equals(repeatArr.get(0).getTaskID()))
			switch3 = true;
		if (taskList.get(1).getTaskID().equals(floatArr.get(1).getTaskID()))
			switch4 = true;
		assertTrue(switch1 && switch2 && switch3 && switch4);

	}
}
