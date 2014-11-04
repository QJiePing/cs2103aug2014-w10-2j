package taskaler.testUnits;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import taskaler.configurations.*;

import org.junit.AfterClass;
import org.junit.Test;

//@author A0111798X
public class ConfigStorageIntegrationTest {

	/**
	 * Equivalence Partition: Invalid values, Valid values, empty value
	 * Boundary Analysis: change all values, change 1 value
	 */

	/**
	 * Combination:
	 * Equivalence Partition: Empty value
	 * Boundary Analysis: Change all values
	 */
	@Test
	public void test1(){
		boolean switch1=false;

		Configuration config=Configuration.getInstance();
		config.setDefaultFileName("");
		config.setDefaultFontColor("");
		config.setDefaultView("");
		config.setTimeFormat("");
		config.storeConfigInfo();

		Configuration readConfig=config.getInstance();
		readConfig.loadConfiguration();
		if(readConfig.getDefaultFileName().equals("task_list") && 
				readConfig.getDefaultFontColor().equals("black") && 
				readConfig.getDefaultView().equals("list") &&
				readConfig.getTimeFormat().equals("HHmm")){
			switch1=true;
		}
		assertTrue(switch1);
	}
	/**
	 * Combination:
	 * Equivalence Partition: Invalid values
	 * Boundary Analysis: Change 1 value
	 */
	@Test
	public void test2() {
		boolean switch1=false;

		Configuration config=Configuration.getInstance();
		config.setDefaultView("abcdefg");
		config.storeConfigInfo();

		Configuration readConfig=config.getInstance();
		readConfig.loadConfiguration();
		if(readConfig.getDefaultView().equals("list")){
			switch1=true;
		}
		assertTrue(switch1);
	}
	/**
	 * Combination:
	 * Equivalence Partition: Invalid values
	 * Boundary Analysis: Change all 4 values (empty the file content)
	 */
	@Test
	public void test3(){
		boolean switch1=false;

		Configuration config=Configuration.getInstance();
		config.setDefaultFileName("");
		config.setDefaultFontColor("");
		config.setDefaultView("");
		config.setTimeFormat("");
		config.storeConfigInfo();

		/**
		 * empty the file content
		 */
		try{
			String holder="";
			FileWriter fw= new FileWriter("config_file");
			fw.write(holder);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Configuration readConfig=config.getInstance();
		readConfig.loadConfiguration();
		if(readConfig.getDefaultFileName().equals("task_list") && 
				readConfig.getDefaultFontColor().equals("black") && 
				readConfig.getDefaultView().equals("list") &&
				readConfig.getTimeFormat().equals("HHmm")){
			switch1=true;
		}
		assertTrue(switch1);

	}
	/**
	 * Combination:
	 * Equivalence Partition: Valid Values
	 * Boundary Analysis: Change 1 value
	 */
	@Test
	public void test4(){
		boolean switch1=false;
		Configuration config=Configuration.getInstance();
		config.setDefaultFontColor("red");
		config.storeConfigInfo();


		Configuration readConfig=config.getInstance();
		readConfig.loadConfiguration();
		if(readConfig.getDefaultFileName().equals("task_list") && 
				readConfig.getDefaultFontColor().equals("red") && 
				readConfig.getDefaultView().equals("list") &&
				readConfig.getTimeFormat().equals("HHmm")){
			switch1=true;
		}
		assertTrue(switch1);
	}
}
