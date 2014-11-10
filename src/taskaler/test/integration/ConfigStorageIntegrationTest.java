package taskaler.test.integration;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;

import taskaler.common.configurations.*;

import org.junit.Test;

//@author A0111798X
public class ConfigStorageIntegrationTest {
	
	private static final String CONFIG_FILE_DIR = ".\\taskaler\\config_file";
	private static final String EMPTY_STRING = "";
	public final String DEFAULT_VIEW = "today";
    public final String DEFAULT_WELCOME_MSG = "Welcome to Taskaler!";
    public final String DEFAULT_LOG_LEVEL = "all";
    public final String DEFAULT_ROW_COLOR = "#FFFFFF";
    public final String DEFAULT_ALT_ROW_COLOR = "#66CCFF";
    public final String DEFAULT_TOAST_COLOR = "#FFFF00";
    public final String DEFAULT_DONE_COLOR = "#FF6600";
    public final String DEFAULT_HEADER_COLOR = "#9966CC";
    public final String DEFAULT_FILENAME = "task_list";
    public final String DEFAULT_TIME_FORMAT = "HH:mm";
    public final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    
    public final String TESTCASE2_VIEW_VALUE = "abc";
    public final String TESTCASE4_ROW_VALUE = "#FF0000";
    public final String TESTCASE5_ROW_VALUE = "#3366FF";
    public final String TESTCASE5_FILENAME = "hello.txt";
    public final String TESTCASE5_INVALID_TIME_FORMAT = "dd:dd";
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
		config.setDefaultFileName(EMPTY_STRING);
		config.setDefaultLogLevel(EMPTY_STRING);
		config.setDefaultRowColor(EMPTY_STRING);
		config.setDefaultAltRowColor(EMPTY_STRING);
		config.setDefaultToastColor(EMPTY_STRING);
		config.setDefaultView(EMPTY_STRING);
		config.setTimeFormat(EMPTY_STRING);
		config.setDefaultWelcomeMsg(EMPTY_STRING);
		config.setDefaultDoneColor(EMPTY_STRING);
		config.setDefaultHeaderColor(EMPTY_STRING);
		config.storeConfigInfo();

		Configuration readConfig = config.getInstance();
		readConfig.loadConfiguration();
		if (readConfig.getDefaultFileName().equals(DEFAULT_FILENAME)
				&& readConfig.getLogLevel().equals(DEFAULT_LOG_LEVEL)
				&& readConfig.getDefaultRowColor().equals(DEFAULT_ROW_COLOR)
				&& readConfig.getDefaultAltRowColor().equals(
						DEFAULT_ALT_ROW_COLOR)
				&& readConfig.getDefaultToastColor()
						.equals(DEFAULT_TOAST_COLOR)
				&& readConfig.getDefaultDoneColor().equals(DEFAULT_DONE_COLOR)
				&& readConfig.getDefaultHeaderColor().equals(
						DEFAULT_HEADER_COLOR)
				&& readConfig.getWelcomeMsg().equals(DEFAULT_WELCOME_MSG)
				&& readConfig.getDefaultView().equals(DEFAULT_VIEW)
				&& readConfig.getTimeFormat().equals(DEFAULT_TIME_FORMAT)) {
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
		config.setDefaultView(TESTCASE2_VIEW_VALUE);
		config.storeConfigInfo();
		Configuration readConfig = config.getInstance();
		readConfig.loadConfiguration();
		if (readConfig.getDefaultView().equals(DEFAULT_VIEW)
				&& readConfig.getLogLevel().equals(DEFAULT_LOG_LEVEL)
				&& readConfig.getDefaultRowColor().equals(DEFAULT_ROW_COLOR)
				&& readConfig.getDefaultAltRowColor().equals(
						DEFAULT_ALT_ROW_COLOR)
				&& readConfig.getDefaultToastColor()
						.equals(DEFAULT_TOAST_COLOR)
				&& readConfig.getDefaultDoneColor().equals(DEFAULT_DONE_COLOR)
				&& readConfig.getDefaultHeaderColor().equals(
						DEFAULT_HEADER_COLOR)
				&& readConfig.getWelcomeMsg().equals(DEFAULT_WELCOME_MSG)
				&& readConfig.getDefaultFileName().equals(DEFAULT_FILENAME)
				&& readConfig.getTimeFormat().equals(DEFAULT_TIME_FORMAT)) {
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

		/**
		 * empty the file content
		 */
		try {
			String holder = EMPTY_STRING;
			FileWriter fw = new FileWriter(CONFIG_FILE_DIR);
			fw.write(holder);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Configuration readConfig = config.getInstance();
		readConfig.loadConfiguration();
		if (readConfig.getDefaultFileName().equals(DEFAULT_FILENAME)
				&& readConfig.getDefaultRowColor().equals(DEFAULT_ROW_COLOR)
				&& readConfig.getLogLevel().equals(DEFAULT_LOG_LEVEL)
				&& readConfig.getDefaultAltRowColor().equals(
						DEFAULT_ALT_ROW_COLOR)
				&& readConfig.getDefaultToastColor()
						.equals(DEFAULT_TOAST_COLOR)
				&& readConfig.getDefaultDoneColor().equals(DEFAULT_DONE_COLOR)
				&& readConfig.getDefaultHeaderColor().equals(
						DEFAULT_HEADER_COLOR)
				&& readConfig.getWelcomeMsg().equals(DEFAULT_WELCOME_MSG)
				&& readConfig.getDateFormat().equals(DEFAULT_DATE_FORMAT)
				&& readConfig.getDefaultView().equals(DEFAULT_VIEW)
				&& readConfig.getTimeFormat().equals(DEFAULT_TIME_FORMAT)) {
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
		config.setDefaultRowColor(TESTCASE4_ROW_VALUE); // red
		config.storeConfigInfo();

		Configuration readConfig = config.getInstance();
		readConfig.loadConfiguration();
		if (readConfig.getDefaultFileName().equals(DEFAULT_FILENAME)
				&& readConfig.getDefaultRowColor().equals(TESTCASE4_ROW_VALUE)
				&& readConfig.getDefaultAltRowColor().equals(
						DEFAULT_ALT_ROW_COLOR)
				&& readConfig.getDefaultToastColor()
						.equals(DEFAULT_TOAST_COLOR)
				&& readConfig.getDefaultDoneColor().equals(DEFAULT_DONE_COLOR)
				&& readConfig.getDefaultHeaderColor().equals(
						DEFAULT_HEADER_COLOR)
				&& readConfig.getLogLevel().equals(DEFAULT_LOG_LEVEL)
				&& readConfig.getWelcomeMsg().equals(DEFAULT_WELCOME_MSG)
				&& readConfig.getDefaultView().equals(DEFAULT_VIEW)
				&& readConfig.getTimeFormat().equals(DEFAULT_TIME_FORMAT)) {
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
		config.setDefaultFileName(TESTCASE5_FILENAME); // valid
		config.setDefaultRowColor(TESTCASE5_ROW_VALUE); // blue, valid
		config.setTimeFormat(TESTCASE5_INVALID_TIME_FORMAT);// invalid
		config.storeConfigInfo();

		Configuration readConfig = config.getInstance();
		readConfig.loadConfiguration();
		if (readConfig.getDefaultFileName().equals(TESTCASE5_FILENAME)
				&& readConfig.getDefaultRowColor().equals(TESTCASE5_ROW_VALUE)
				&& readConfig.getDefaultView().equals(DEFAULT_VIEW)
				&& readConfig.getDefaultAltRowColor().equals(
						DEFAULT_ALT_ROW_COLOR)
				&& readConfig.getDefaultToastColor()
						.equals(DEFAULT_TOAST_COLOR)
				&& readConfig.getDefaultDoneColor().equals(DEFAULT_DONE_COLOR)
				&& readConfig.getDefaultHeaderColor().equals(
						DEFAULT_HEADER_COLOR)
				&& readConfig.getWelcomeMsg().equals(DEFAULT_WELCOME_MSG)
				&& readConfig.getLogLevel().equals(DEFAULT_LOG_LEVEL)
				&& readConfig.getTimeFormat().equals(DEFAULT_TIME_FORMAT)) {
			switch1 = true;
		}
		assertTrue(switch1);
	}
}
