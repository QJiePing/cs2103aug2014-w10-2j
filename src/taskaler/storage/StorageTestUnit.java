package taskaler.storage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.Test;

import taskaler.common.data.Task;

/*
 * @author Quek Jie Ping A0111798X
 */
public class StorageTestUnit {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File f= new File("history.txt");
		f.delete();
	}

	/*
	 * This is a equivalence partitioning case for 'null' value partition.
	 */

	//test for null array list object 
	@Test
	public void test() {
		Storage storeObj= Storage.getInstance();
		boolean result=storeObj.writeToFile("testing.txt", null);
		assertFalse(result);
	}

	//test for null filename
	@Test
	public void test2() {
		ArrayList<Task> arrList= new ArrayList<Task>();
		arrList.add(new Task());
		Storage storeObj= Storage.getInstance();
		boolean result=storeObj.writeToFile(null, arrList);
		assertFalse(result);
	}

	/*
	 * testing json writing in and reading out methods
	 */

	//testing the case of an empty arraylist 
	@Test
	public void test3(){
		ArrayList<Task> arrList= new ArrayList<Task>();
		Storage storeObj= Storage.getInstance();
		storeObj.writeToFile("testing.txt", arrList);

		ArrayList<Task> temp= storeObj.readFromFile("testing.txt");
		assertEquals(null,temp);
	}
	//testing the case where there is 2 task
	@Test
	public void test4(){
		boolean result=false;
		ArrayList<Task> arrList= new ArrayList<Task>();
		arrList.add(new Task("Task1","1","not done",Calendar.getInstance(),"5","description1"));
		arrList.add(new Task("Task2","2","done",Calendar.getInstance(),"1","description2"));
		Storage storeObj= Storage.getInstance();
		storeObj.writeToFile("testing.txt", arrList);

		ArrayList<Task> temp= storeObj.readFromFile("testing.txt");
		for(int i=0;i<arrList.size();i++){
			if(arrList.get(i).getTaskID().equals(temp.get(i).getTaskID())){
				result=true;
			}
			else{
				result=false;
				break;
			}
		}
		assertTrue(result);
	}

	/*
	 * testing history writer and reader methods
	 */
	@Test
	public void test5()throws IOException{
		boolean test=false;
		HistoryStorage store=HistoryStorage.getInstance();
		store.writeToHistory("history.txt", "line1");
		store.writeToHistory("history.txt", "line2");
		
		String result=store.readFromHistory("history.txt");
		if(result.split("\n")[0].equals("line1")&&result.split("\n")[1].equals("line2")){
			test=true;
			assertTrue(test);
		}
		else{
			assertTrue(false);
		}
	}
	
}
