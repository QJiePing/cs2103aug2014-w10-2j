package taskaler.storage;

import taskaler.common.data.*;
import taskaler.common.util.*;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * @author Quek Jie Ping A0111798X

 */

/**
 * This is the main storage class which performs storage function of Taskaler.
 */
public class Storage {

	private static CommonLogger log = CommonLogger.getInstance();
	private static Storage instance = null;

	/**
	 * Constructors
	 */
	private Storage() {

	}

	public static Storage getInstance() {
		if (instance == null) {
			instance = new Storage();
		}
		return instance;
	}

	/**
	 * Method to read in task data from the text file
	 * 
	 * @param file
	 *            The directory of the text file
	 * 
	 * @return return an arraylist of saved tasks from the text file
	 */
	public ArrayList<Object> readFromFile(String file) {

		/**
		 * temporary holder variables
		 */
		ArrayList<Object> result = new ArrayList<Object>();
		CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask> holder = new CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask>();

		try {
			FileReader reader = new FileReader(file);
			Gson gson = createGsonObj();
			TypeToken<CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask>> typeToken = new TypeToken<CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask>>() {
			};
			holder = gson.fromJson(reader, typeToken.getType());
			result.add(holder.getFloatArr());
			result.add(holder.getDeadLineArr());
			result.add(holder.getRepeatedArr());
			reader.close();
		} catch (Exception e) {
			log.exceptionLogger(e, Level.SEVERE);
			return null;
		}
		return result;
	}

	/**
	 * Method to write all saved tasks information to the text file
	 * 
	 * @param file
	 *            The directory of the text file
	 * @param TaskList
	 *            TaskList that contains all the task information
	 * 
	 * @return return a boolean indicating whether the write operation is a
	 *         success or fail
	 */
	public boolean writeToFile(String file, TaskList taskList) {

		try {
			FileWriter fw = new FileWriter(file);
			Gson gson = createGsonObj();
			CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask> helper = prepareTaskList(taskList);
			TypeToken<CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask>> typeToken = new TypeToken<CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask>>() {
			};
			String output = gson.toJson(helper, typeToken.getType());
			fw.write(output);
			fw.close();
		} catch (Exception e) {
			log.exceptionLogger(e, Level.SEVERE);
			return false;
		}
		return true;
	}

	/**
	 * Prepare the TaskList for gson to process
	 * 
	 * @param taskList
	 *            TaskList object that contains all the tasks information
	 * @return CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask>
	 */
	private CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask> prepareTaskList(
			TaskList taskList) {
		CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask> helper = new CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask>();
		helper.setFloatArr(taskList.floatToArray());
		helper.setDeadLineArr(taskList.deadlineToArray());
		helper.setRepeatedArr(taskList.repeatedToArray());
		return helper;
	}

	/**
	 * Method to instantiate a gson object for the reading json object from json
	 * formatted file and output all saved task information in json format to
	 * the text file.
	 * 
	 * @return return a gson object
	 */
	private Gson createGsonObj() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		Gson gson = gsonBuilder.create();
		return gson;
	}

	public void storageWriteStub(String file, String message) {
		System.out.println("=====================================");
		System.out.println("Filename : " + file);
		System.out.println("Content : " + message);
		System.out.println("=====================================");
	}

	public String storageReadStub(String file) {
		System.out.println("=====================================");
		System.out.println("Filename : " + file);
		System.out.println("Giving back Weird String");
		System.out.println("=====================================");

		return "WEIRD WEIRD WEIRD\nWEIRD WEIRD WEIRD";
	}
}
