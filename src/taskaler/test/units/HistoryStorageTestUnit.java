package taskaler.test.units;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Test;

import taskaler.storage.HistoryStorage;

//@author A0111798X

public class HistoryStorageTestUnit {

	private static final String FILE_DIR = ".\\taskaler\\history.txt";
	private static final String MSG_1 = "message1";
	private static final String LINE_1 = "line1";
	private static final String EMPTY_STRING = " ";
	private static final String LINE_2 = "line2";
	private static final String HISTORY_TXT = "history.txt";

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File f = new File(FILE_DIR);
		f.delete();
	}
	
	/**
	 * Equivalence Partition(file name parameter): null file name, empty file name,
	 * non-empty file name.
	 * Equivalence Partition(message parameter): null message, empty message, 
	 * non-empty message.
	 */

	/**
	 * Write Method
	 * Combination:
	 * Equivalence Partition(file name parameter): null file name
	 * Equivalence Partition(message parameter): non-empty message
	 */
	@Test
	public void test() {
		HistoryStorage historyObj = HistoryStorage.getInstance();
		boolean result = historyObj.writeToHistory(null, MSG_1);
		assertFalse(result);
	}

	/**
	 * Write Method
	 * Combination:
	 * Equivalence Partition(file name parameter): non-empty file name
	 * Equivalence Partition(message parameter): null message
	 */
	@Test
	public void test2() {
		HistoryStorage historyObj = HistoryStorage.getInstance();
		boolean result = historyObj.writeToHistory(HISTORY_TXT, null);
		assertFalse(result);
	}
	
	/**
	 * Write Method
	 * Combination:
	 * Equivalence Partition(file name parameter): non-empty file name
	 * Equivalence Partition(message parameter): empty message
	 */
	@Test
	public void test3() {
		HistoryStorage historyObj = HistoryStorage.getInstance();
		boolean result = historyObj.writeToHistory(HISTORY_TXT, EMPTY_STRING);
		assertTrue(result);
	}

	/**
	 * read method
	 * Combination:
	 * Equivalence Partition(file name parameter): null file name
	 */
	@Test
	public void test4() {
		HistoryStorage historyObj = HistoryStorage.getInstance();
		String result = historyObj.readFromHistory(null);
		assertEquals(null, result);
	}
	
	/**
	 * read method
	 * Combination:
	 * Equivalence Partition(file name parameter): empty file name
	 */
	@Test
	public void test5() {
		HistoryStorage historyObj = HistoryStorage.getInstance();
		String result = historyObj.readFromHistory(EMPTY_STRING);
		assertEquals(null, result);
	}
	
	/**
	 * Both read and write methods
	 * Combination:
	 * Equivalence Partition(file name parameter): non-empty file name
	 * Equivalence Partition(message parameter): empty message
	 */
	@Test
	public void test6() {
		boolean switch1 = false;
		HistoryStorage store = HistoryStorage.getInstance();
		store.writeToHistory(HISTORY_TXT, EMPTY_STRING);
		store.writeToHistory(HISTORY_TXT, LINE_2);
		String result = store.readFromHistory(HISTORY_TXT);
		if (result.split("\n")[0].equals(LINE_2)) {
			switch1 = true;
		}
		assertTrue(switch1);
	}

	/**
	 * Both read and write methods
	 * Combination:
	 * Equivalence Partition(file name parameter): non-empty file name
	 * Equivalence Partition(message parameter): non-empty message
	 */
	@Test
	public void test7() throws IOException {
		boolean switch1 = false;
		HistoryStorage store = HistoryStorage.getInstance();
		store.writeToHistory(HISTORY_TXT, LINE_1);
		store.writeToHistory(HISTORY_TXT, LINE_2);

		String result = store.readFromHistory(HISTORY_TXT);
		if (result.split("\n")[1].equals(LINE_1)
				&& result.split("\n")[0].equals(LINE_2)) {
			switch1 = true;
		}
		assertTrue(switch1);
	}

}
