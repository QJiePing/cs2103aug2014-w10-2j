package taskaler.test.integration;

import static org.junit.Assert.*;
import java.io.FileWriter;
import java.io.IOException;
import taskaler.common.configurations.*;
import org.junit.Test;

//@author A0111798X
public class ConfigStorageIntegrationTest {

	/**
	 * Equivalence Partition: Invalid values, Valid values, empty value
	 * Boundary Analysis: change all values, change 1 value, change more than 1
	 * value but not all values
	 */

	/**
	 * Combination:
	 * Equivalence Partition: Empty value
	 * Boundary Analysis: Change all values
	 */
	@Test
	public void test1() {
		boolean switch1 = false;

		Configuration config = Configuration.getInstance();
		config.setDefaultFileName("");
		config.setDefaultLogLevel("");
		config.setDefaultRowColor("");
		config.setDefaultAltRowColor("");
		config.setDefaultToastColor("");
		config.setDefaultView("");
		config.setTimeFormat("");
		config.setDefaultWelcomeMsg("");
		config.setDefaultDoneColor("");
		config.setDefaultHeaderColor("");
		config.storeConfigInfo();

		Configuration readConfig = config.getInstance();
		readConfig.loadConfiguration();
		if (readConfig.getDefaultFileName().equals("task_list")
				&& readConfig.getLogLevel().equals("all")
				&& readConfig.getDefaultRowColor().equals("#FFFFFF")
				&& readConfig.getDefaultAltRowColor().equals("#66CCFF")
				&& readConfig.getDefaultToastColor().equals("#FFFF00")
				&& readConfig.getDefaultDoneColor().equals("#FF6600")
				&& readConfig.getDefaultHeaderColor().equals("#9966CC")
				&& readConfig.getWelcomeMsg().equals("Welcome to Taskaler!")
				&& readConfig.getDefaultView().equals("today")
				&& readConfig.getTimeFormat().equals("HH:mm")) {
			switch1 = true;
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
		boolean switch1 = false;

		Configuration config = Configuration.getInstance();
		config.setDefaultView("abc");
		config.storeConfigInfo();
		Configuration readConfig = config.getInstance();
		readConfig.loadConfiguration();
		if (readConfig.getDefaultView().equals("today")
			&& readConfig.getLogLevel().equals("all")
			&& readConfig.getDefaultRowColor().equals("#FFFFFF")
			&& readConfig.getDefaultAltRowColor().equals("#66CCFF")
			&& readConfig.getDefaultToastColor().equals("#FFFF00")
			&& readConfig.getDefaultDoneColor().equals("#FF6600")
			&& readConfig.getDefaultHeaderColor().equals("#9966CC")
			&& readConfig.getWelcomeMsg().equals("Welcome to Taskaler!")
			&& readConfig.getDefaultFileName().equals("task_list")
			&& readConfig.getTimeFormat().equals("HH:mm")) {
			switch1 = true;
		}
		assertTrue(switch1);
	}
	/**
	 * Combination:
	 * Equivalence Partition: Invalid values
	 * Boundary Analysis: Change all 8 values (empty the file content)
	 */
	@Test
	public void test3() {
		boolean switch1 = false;

		Configuration config = Configuration.getInstance();
		config.setDefaultFileName("");
		config.setDefaultLogLevel("");
		config.setDefaultRowColor("");
		config.setDefaultAltRowColor("");
		config.setDefaultToastColor("");
		config.setDefaultView("");
		config.setDateFormat("");
		config.setTimeFormat("");
		config.storeConfigInfo();

		/**
		 * empty the file content
		 */
		try {
			String holder = "";
			FileWriter fw = new FileWriter(".\\taskaler\\config_file");
			fw.write(holder);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Configuration readConfig = config.getInstance();
		readConfig.loadConfiguration();
		if (readConfig.getDefaultFileName().equals("task_list")
				&& readConfig.getDefaultRowColor().equals("#FFFFFF")
				&& readConfig.getLogLevel().equals("all")
				&& readConfig.getDefaultAltRowColor().equals("#66CCFF")
				&& readConfig.getDefaultToastColor().equals("#FFFF00")
				&& readConfig.getDefaultToastColor().equals("#FFFF00")
				&& readConfig.getDefaultDoneColor().equals("#FF6600")
				&& readConfig.getDefaultHeaderColor().equals("#9966CC")
				&& readConfig.getWelcomeMsg().equals("Welcome to Taskaler!")
				&& readConfig.getDateFormat().equals("dd/MM/yyyy")
				&& readConfig.getDefaultView().equals("today")
				&& readConfig.getTimeFormat().equals("HH:mm")) {
			switch1 = true;
		}
		assertTrue(switch1);

	}
	/**
	 * Combination:
	 * Equivalence Partition: Valid Values
	 * Boundary Analysis: Change 1 value
	 */
	@Test
	public void test4() {
		boolean switch1 = false;
		Configuration config = Configuration.getInstance();
		config.setDefaultRowColor("#FF0000"); // red
		config.storeConfigInfo();

		Configuration readConfig = config.getInstance();
		readConfig.loadConfiguration();
		if (readConfig.getDefaultFileName().equals("task_list")
				&& readConfig.getDefaultRowColor().equals("#FF0000")
				&& readConfig.getDefaultAltRowColor().equals("#66CCFF")
				&& readConfig.getDefaultToastColor().equals("#FFFF00")
				&& readConfig.getDefaultDoneColor().equals("#FF6600")
				&& readConfig.getDefaultHeaderColor().equals("#9966CC")
				&& readConfig.getWelcomeMsg().equals("Welcome to Taskaler!")
				&& readConfig.getDefaultView().equals("today")
				&& readConfig.getTimeFormat().equals("HH:mm")) {
			switch1 = true;
		}
		assertTrue(switch1);
	}
	/**
	 * Combination:
	 * Equivalence Partition: mixture Valid and Invalid values
	 * Boundary Analysis: Change 3 value
	 */
	@Test
	public void test5() {
		boolean switch1 = false;
		Configuration config = Configuration.getInstance();
		config.setDefaultFileName("hello.txt"); // valid
		config.setDefaultRowColor("#FF0000"); // red
		config.setTimeFormat("dd:dd");// invalid
		config.storeConfigInfo();

		Configuration readConfig = config.getInstance();
		readConfig.loadConfiguration();
		if (readConfig.getDefaultFileName().equals("hello.txt")
				&& readConfig.getDefaultRowColor().equals("#FF0000")
				&& readConfig.getDefaultView().equals("today")
				&& readConfig.getDefaultAltRowColor().equals("#66CCFF")
				&& readConfig.getDefaultToastColor().equals("#FFFF00")
				&& readConfig.getDefaultDoneColor().equals("#FF6600")
				&& readConfig.getDefaultHeaderColor().equals("#9966CC")
				&& readConfig.getWelcomeMsg().equals("Welcome to Taskaler!")
				&& readConfig.getLogLevel().equals("all")
				&& readConfig.getTimeFormat().equals("HH:mm")) {
			switch1 = true;
		}
		assertTrue(switch1);
	}
}
