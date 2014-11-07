//@author A0099778X

package taskaler.archive;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import taskaler.common.data.Task;
import taskaler.common.util.parser.calendarToString;
import taskaler.storage.HistoryStorage;

public class PastHistory implements Observer {
	public static String HISTORY_FILE_NAME = "History_%s_%s_%s.log";
	public static String NO_DATE_SPECIFIED = "";
	public static String NO_HISTORY_RECORD_MESSAGE = "No history found";

	public static int DAY_INDEX = 0;
	public static int MONTH_INDEX = 1;
	public static int YEAR_INDEX = 2;

	public static void logHistory(String message) throws Exception {
		String fileName = fileNameGenerator(NO_DATE_SPECIFIED);
		// Storage store= Storage.getInstance();
		// store.storageWriteStub(fileName, message);
		HistoryStorage store = HistoryStorage.getInstance();
		store.writeToHistory(fileName, message);
	}

	public static String retrieveHistory(String date) {
		String fileName = fileNameGenerator(date);
		HistoryStorage store = HistoryStorage.getInstance();
		String currentHistory = store.readFromHistory(fileName);
		// Storage store= Storage.getInstance();
		// String currentHistory = store.storageReadStub(fileName);

		if (currentHistory == null) {
			return NO_HISTORY_RECORD_MESSAGE;
		}

		return currentHistory;

	}

	private static String fileNameGenerator(String date) {
		String fileName;
		String[] fileNameDate;
		if (date == null || date.isEmpty()) {
			fileNameDate = calendarToString.toArray(Calendar.getInstance());
		} else {
			fileNameDate = date.split("/");
		}

		fileName = String.format(HISTORY_FILE_NAME, fileNameDate[DAY_INDEX],
				fileNameDate[MONTH_INDEX], fileNameDate[YEAR_INDEX]);

		return fileName;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof OperationRecord<?, ?>) {
			OperationRecord<Task, String> currentRecord = (OperationRecord<Task, String>) arg;

			String message = "["
					+ calendarToString.parseDate(Calendar.getInstance(),
							"hh:mm:ss") + "]";
			switch (currentRecord.getOp()) {
			case "ADD":
				message = message
						+ String.format("Added new Task: %s (ID: %s)\n",
								currentRecord.getTask().getTaskName(),
								currentRecord.getTask().getTaskID());
				break;
			case "DELETE":
				message = message
						+ String.format("Deleted Task: %s (ID: %s)\n",
								currentRecord.getTask().getTaskName(),
								currentRecord.getTask().getTaskID());
				break;
			case "EDIT":
				message = message
						+ String.format("Modified Task: %s (ID: %s)\n",
								currentRecord.getTask().getTaskName(),
								currentRecord.getTask().getTaskID());
				break;
			case "DATE":
				message = message
						+ String.format("Modified date of Task: %s (ID: %s)\n",
								currentRecord.getTask().getTaskName(),
								currentRecord.getTask().getTaskID());
				break;
			case "COMPLETE":
				message = message
						+ String.format("Switched the Completion Tag of Task: %s (ID: %s)\n",
								currentRecord.getTask().getTaskName(),
								currentRecord.getTask().getTaskID());
				break;

			case "WORKLOAD":
				message = message
						+ String.format("Changed Workload of Task: %s (ID: %s)\n",
								currentRecord.getTask().getTaskName(),
								currentRecord.getTask().getTaskID());
				break;
			case "REPEAT":
				message = message
						+ String.format("Set Task: %s (ID: %s) to be repeated\n",
								currentRecord.getTask().getTaskName(),
								currentRecord.getTask().getTaskID());
				break;
			case "TIME":
				message = message
						+ String.format("Changed the Time of Task: %s (ID: %s)\n",
								currentRecord.getTask().getTaskName(),
								currentRecord.getTask().getTaskID());
				break;
			case "UNDO":
				message = message
						+ String.format("Reverted Task: %s (ID: %s)\n",
								currentRecord.getTask().getTaskName(),
								currentRecord.getTask().getTaskID());
				break;
			}

			try {
				logHistory(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
