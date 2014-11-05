package taskaler.testUnits;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Test;

import taskaler.storage.Storage;

//@author A0111798X
public class StorageConfigTestUnit {

	
	/**
	 * Boundary Analysis: empty array list, array list with one or more strings
	 */
	
	/**
	 * Test for empty array list
	 * Boundary Analysis: empty array list
	 */
	@Test
	public void test1() {
		boolean switch1=false;
		
		ArrayList<String> testArr= new ArrayList<String>();
		Storage storeObj= Storage.getInstance();
		storeObj.writeConfigFile(testArr);
		
		ArrayList<String> result= storeObj.readConfigFile();
		
		if(result.size()==0){
			switch1=true;
		}
		assertTrue(switch1);
	}
	/**
	 * Test for correctness of the string object insert into the array list
	 * Boundary Analysis: array list with more than 1 string object
	 */
	@Test
	public void test2(){
		boolean switch1=false;
		
		ArrayList<String> testArr= new ArrayList<String>();
		testArr.add("black");
		testArr.add("abc");
		testArr.add("testing 123");
		Storage storeObj= Storage.getInstance();
		storeObj.writeConfigFile(testArr);
		
		ArrayList<String> result=storeObj.readConfigFile();
		
		if(result.size()>0 && testArr.get(0).equals("black") && testArr.get(1).equals("abc") && 
				testArr.get(2).equals("testing 123")){
			switch1=true;
		}
		assertTrue(switch1);
	}

}
