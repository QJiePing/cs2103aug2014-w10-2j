package taskaler.storage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Test;

/**
 * @author Quek Jie Ping A0111798X
 */
public class HistoryStorageTestUnit {

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		File f = new File("history.txt");
		f.delete();
	}

	/**
	 * This is a equivalence partitioning case for 'null' value partition. Test
	 * the history writer method supplied with a null filename
	 */
	@Test
	public void test() {
		HistoryStorage historyObj = HistoryStorage.getInstance();
		boolean result = historyObj.writeToHistory(null, "message1");
		assertFalse(result);
	}

	/**
	 * This is a equivalence partitioning case for 'null' value partition. Test
	 * the history writer method supplied with a null message
	 */
	@Test
	public void test2() {
		HistoryStorage historyObj = HistoryStorage.getInstance();
		boolean result = historyObj.writeToHistory("history.txt", null);
		assertFalse(result);
	}

	/**
	 * This is a equivalence partitioning case for 'null' value partition. Test
	 * the history reader method supplied with a null filename
	 */
	@Test
	public void test3() {
		HistoryStorage historyObj = HistoryStorage.getInstance();
		String result = historyObj.readFromHistory(null);
		assertEquals(null, result);
	}

	/**
	 * Test if the history writer and reader can read and writer the correct
	 * number of line of file content
	 */
	@Test
	public void test4() throws IOException {
		boolean test = false;
		HistoryStorage store = HistoryStorage.getInstance();
		store.writeToHistory("history.txt", "line1");
		store.writeToHistory("history.txt", "line2");

		String result = store.readFromHistory("history.txt");
		if (result.split("\n")[0].equals("line1")
				&& result.split("\n")[1].equals("line2")) {
			test = true;
			assertTrue(test);
		} else {
			assertTrue(false);
		}
	}

}
