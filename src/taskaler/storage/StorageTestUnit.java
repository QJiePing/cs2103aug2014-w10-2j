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
		File f= new File("testing.txt");
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
		TaskList taskList= TaskList.getInstance();
		taskList.add(new FloatTask());
		Storage storeObj= Storage.getInstance();
		boolean result=storeObj.writeToFile(null,taskList);
		assertFalse(result);
	}

	/**
	 * Testing json writing in and reading out methods
	 */

	//testing the case of an empty task list
	@Test
	public void test2(){
		TaskList taskList= TaskList.getInstance();
		taskList.clear();
		Storage storeObj= Storage.getInstance();
		storeObj.writeToFile("testing.txt",taskList);

		ArrayList<Task> temp= storeObj.readFromFile("testing.txt");
		assertTrue(temp.isEmpty());
	}

	/**
	 * Testing the case where there is 2 float task in TaskList
	 */
	@Test
	public void test3(){
		boolean result=false;
		TaskList taskList= TaskList.getInstance();
		taskList.clear();
		taskList.add(new FloatTask("Task1","1","not done",Calendar.getInstance(),"5","description1",Calendar.getInstance(),Calendar.getInstance()));
		taskList.add(new FloatTask("Task2","2","done",Calendar.getInstance(),"1","description2",Calendar.getInstance(),Calendar.getInstance()));
		
		Storage storeObj= Storage.getInstance();
		storeObj.writeToFile("testing.txt",taskList);
		
		ArrayList<Task> temp= storeObj.readFromFile("testing.txt");
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
	/**
	 * Testing the case where there is a mixture of 3 different type of tasks
	 */
	@Test
	public void test4(){
		boolean result=false;
		TaskList taskList= TaskList.getInstance();
		taskList.clear();
		ArrayList<Calendar> arrCal=new ArrayList<Calendar>();
		Calendar temp= Calendar.getInstance();
		temp.set(Calendar.YEAR, Calendar.DECEMBER, Calendar.MONDAY);
		arrCal.add(temp);
		taskList.add(new FloatTask("Task1","1","not done",Calendar.getInstance(),"5","description1",Calendar.getInstance(),Calendar.getInstance()));
		taskList.add(new DeadLineTask("Task2","2","done",Calendar.getInstance(),"1","description2",Calendar.getInstance(),Calendar.getInstance(),Calendar.getInstance()));
		taskList.add(new RepeatedTask("Task3","3","not done",Calendar.getInstance(),"2","description2",Calendar.getInstance(),Calendar.getInstance(),arrCal,Calendar.getInstance(),5));
		taskList.add(new FloatTask("Task4","4","not done",Calendar.getInstance(),"1","description4",Calendar.getInstance(),Calendar.getInstance()));
		
		Storage storeObj= Storage.getInstance();
		storeObj.writeToFile("testing.txt",taskList);
		
		ArrayList<Task> tempArr= storeObj.readFromFile("testing.txt");
		for(int i=0;i<taskList.size();i++){
			if(taskList.get(i).getTaskID().equals(tempArr.get(i).getTaskID())){
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
