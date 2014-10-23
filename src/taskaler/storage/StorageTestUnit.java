package taskaler.storage;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.Test;

import taskaler.common.data.*;

/*
 * @author Quek Jie Ping A0111798X
 */
public class StorageTestUnit {
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File f= new File("testing.txt");
		f.delete();
	}

	/*
	 * This is a equivalence partitioning case for 'null' value partition.
	 */

	//test for null task list object 
	@Test
	public void test() {
		Storage storeObj= Storage.getInstance();
		boolean result=storeObj.writeToFile("testing.txt", null);
		assertTrue(result);
	}

	//test for null filename
	@Test
	public void test2() {
		TaskList taskList= TaskList.getInstance();
		taskList.add(new Task());
		Storage storeObj= Storage.getInstance();
		boolean result=storeObj.writeToFile(null, taskList);
		assertFalse(result);
	}

	/*
	 * testing json writing in and reading out methods
	 */

	//testing the case of an empty task list
	@Test
	public void test3(){
		TaskList taskList= TaskList.getInstance();
		taskList.clear();
		Storage storeObj= Storage.getInstance();
		storeObj.writeToFile("testing.txt", taskList);

		TaskList temp= storeObj.readFromFile("testing.txt");
		assertEquals(null,temp);
	}
	//testing the case where there is 2 task
	@Test
	public void test4(){
		boolean result=false;
		TaskList taskList= TaskList.getInstance();
		taskList.clear();
		taskList.add(new Task("Task1","1","not done",Calendar.getInstance(),"5","description1"));
		taskList.add(new Task("Task2","2","done",Calendar.getInstance(),"1","description2"));
		Storage storeObj= Storage.getInstance();
		storeObj.writeToFile("testing.txt", taskList);

		TaskList temp= storeObj.readFromFile("testing.txt");
		for(int i=0;i<taskList.size();i++){
			if(taskList.get(i).getTaskID().equals(temp.get(i).getTaskID())){
				result=true;
			}
			else{
				result=false;
				break;
			}
		}
		assertTrue(result);
	}
	
}
