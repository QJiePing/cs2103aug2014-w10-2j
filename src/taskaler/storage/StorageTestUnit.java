package taskaler.storage;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.Test;

import taskaler.common.data.*;

/**
 * @author Quek Jie Ping A0111798X
 */
public class StorageTestUnit {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File f = new File("testing.txt");
		f.delete();
	}

	/**
	 * This is a equivalence partitioning case for 'null' value partition.
	 */

	/**
	 * Test for null filename
	 */
	@Test
	public void test1() {
		TaskList taskList = TaskList.getInstance();
		taskList.add(new FloatTask());
		Storage storeObj = Storage.getInstance();
		boolean result = storeObj.writeToFile(null, taskList);
		assertFalse(result);
	}

	/**
	 * Testing json writing in and reading out methods
	 */

	/**
	 * testing the case of an empty task list
	 */
	@Test
	public void test2() {
		TaskList taskList = TaskList.getInstance();
		taskList.clear();
		Storage storeObj = Storage.getInstance();
		storeObj.writeToFile("testing.txt", taskList);

		ArrayList<Object> tempArr = storeObj.readFromFile("testing.txt");
		ArrayList<FloatTask> floatArr = (ArrayList<FloatTask>) tempArr.get(0);
		ArrayList<DeadLineTask> deadlineArr = (ArrayList<DeadLineTask>) tempArr
				.get(1);
		ArrayList<RepeatedTask> repeatArr = (ArrayList<RepeatedTask>) tempArr
				.get(2);

		assertTrue(floatArr.isEmpty() && deadlineArr.isEmpty()
				&& repeatArr.isEmpty());
	}

	/**
	 * Testing the case where there is 2 float task in TaskList
	 */
	@Test
	public void test3() {
		boolean result = false;
		TaskList taskList = TaskList.getInstance();
		taskList.clear();
		taskList.add(new FloatTask("Task1", "1", "not done", Calendar
				.getInstance(), "5", "description1", Calendar.getInstance(),
				Calendar.getInstance()));
		taskList.add(new FloatTask("Task2", "2", "done",
				Calendar.getInstance(), "1", "description2", Calendar
						.getInstance(), Calendar.getInstance()));

		Storage storeObj = Storage.getInstance();
		storeObj.writeToFile("testing.txt", taskList);

		ArrayList<Object> temp = storeObj.readFromFile("testing.txt");
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
	 */
	@Test
	public void test4() {
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
		taskList.add(new FloatTask("Task1", "1", "not done", Calendar
				.getInstance(), "5", "description1", Calendar.getInstance(),
				Calendar.getInstance()));
		taskList.add(new DeadLineTask("Task2", "2", "done", Calendar
				.getInstance(), "1", "description2", Calendar.getInstance(),
				Calendar.getInstance(), Calendar.getInstance()));
		taskList.add(new RepeatedTask("Task3", "3", "not done", Calendar
				.getInstance(), "2", "description2", Calendar.getInstance(),
				Calendar.getInstance(), arrCal, Calendar.getInstance(), 5));
		taskList.add(new FloatTask("Task4", "4", "not done", Calendar
				.getInstance(), "1", "description4", Calendar.getInstance(),
				Calendar.getInstance()));

		/**
		 * Execute the writeToFile and readFromFile method
		 */
		Storage storeObj = Storage.getInstance();
		storeObj.writeToFile("testing.txt", taskList);

		ArrayList<Object> tempArr = storeObj.readFromFile("testing.txt");
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
