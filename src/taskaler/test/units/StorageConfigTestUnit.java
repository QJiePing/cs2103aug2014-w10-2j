package taskaler.test.units;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import taskaler.storage.TaskAndConfigStorage;

//@author A0111798X
public class StorageConfigTestUnit {

	
	private static final String STRING_3 = "testing 123";
	private static final String STRING_2 = "abc";
	/**
	 * Boundary Analysis: empty array list, array list with one or more strings
	 */
	
	private static final String STRING_1 = "black";
	/**
	 * Test for empty array list
	 * Boundary Analysis: empty array list
	 */
	@Test
	public void test1() {
		boolean switch1 = false;

		ArrayList<String> testArr = new ArrayList<String>();
		TaskAndConfigStorage storeObj = TaskAndConfigStorage.getInstance();
		storeObj.writeConfigFile(testArr);

		ArrayList<String> result = storeObj.readConfigFile();

		if (result.size() == 0) {
			switch1 = true;
		}
		assertTrue(switch1);
	}
	/**
	 * Test for correctness of the string object insert into the array list
	 * Boundary Analysis: array list with more than 1 string object
	 */
	@Test
	public void test2() {
		boolean switch1 = false;

		ArrayList<String> testArr = new ArrayList<String>();
		testArr.add(STRING_1);
		testArr.add(STRING_2);
		testArr.add(STRING_3);
		TaskAndConfigStorage storeObj = TaskAndConfigStorage.getInstance();
		storeObj.writeConfigFile(testArr);

		ArrayList<String> result = storeObj.readConfigFile();

		if (result.size() > 0 && testArr.get(0).equals(STRING_1)
				&& testArr.get(1).equals(STRING_2)
				&& testArr.get(2).equals(STRING_3)) {
			switch1 = true;
		}
		assertTrue(switch1);
	}

}
