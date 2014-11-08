

package taskaler.archive;

import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import taskaler.common.data.Task;
import taskaler.common.util.parser.calendarToString;
import taskaler.storage.HistoryStorage;

//@author A0099778X

public class PastHistory implements Observer {
	public static String HISTORY_FILE_NAME = "History_%s_%s_%s.log";
	public static String NO_DATE_SPECIFIED = "";
	public static String NO_HISTORY_RECORD_MESSAGE = "No history found";
	public static String DATE_CHAR_SEPARATOR = "/";

	public static int DAY_INDEX = 0;
	public static int MONTH_INDEX = 1;
	public static int YEAR_INDEX = 2;

	/**
	 * logHistory(String logMessage) will call storage to log the message
	 * @param logMessage
	 * @throws Exception
	 */
	public static void logHistory(String logMessage) throws Exception {
		String fileName = fileNameGenerator(NO_DATE_SPECIFIED);
		
		HistoryStorage historyStore = HistoryStorage.getInstance();
		historyStore.writeToHistory(fileName, logMessage);
	}

	/**
	 * retrieveHistory(String retrieveDate) will call storage to read the history record
	 * of all the operations done in the given date.
	 * @param retrieveDate
	 * @return
	 */
	public static String retrieveHistory(String retrieveDate) {
		String fileName = fileNameGenerator(retrieveDate);
		
		HistoryStorage historyStore = HistoryStorage.getInstance();
		String currentHistory = historyStore.readFromHistory(fileName);

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
			fileNameDate = date.split(DATE_CHAR_SEPARATOR);
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
