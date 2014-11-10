package taskaler.storage;

import taskaler.common.configurations.Configuration;
import taskaler.common.data.*;
import taskaler.common.util.*;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

//@author A0111798X

/**
 * This is the main storage class which performs storage function of Taskaler.
 * It is a singleton class.
 */
public class TaskAndConfigStorage {

    private static final String MSG_UNKNOWN_OS_VERSION = "Unknown JVM version detected";
    private static final String OS_BIT_PROPERTY = "sun.arch.data.model";
    private static final String OS_64_BIT = "64";
    private static final String OS_32_BIT = "32";
    private static CommonLogger log = CommonLogger.getInstance();
    private static TaskAndConfigStorage instance = null;
    private static File configFile = null;
    private static final String STORAGE_DIR = ".\\taskaler\\";

    // DLL Constants
    private static final String DLL_PATH_PARENT = "/lib";
    private static final String DLL_PATH_BIT_32 = "/x86";
    private static final String DLL_PATH_BIT_64 = "/x64";
    private static final String DLL_PATH_32 = "/JNILibrary.dll";
    private static final String DLL_PATH_64 = "/JNILibrary64.dll";
    private static final String DLL_PATH_MSVCR = "/msvcr120.dll";
    private static File[] libraryLoaded = null;

    /**
     * Constructors
     */
    private TaskAndConfigStorage() {

        File dir = new File(STORAGE_DIR);
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        configFile = new File(STORAGE_DIR + "config_file");

        try {
            libraryLoaded = new File[2];
            if (is32Bit()) {
                libraryLoaded[0] = loadDll(DLL_PATH_PARENT + DLL_PATH_BIT_32,
                        STORAGE_DIR, DLL_PATH_MSVCR);
                libraryLoaded[1] = loadDll(DLL_PATH_PARENT + DLL_PATH_BIT_32,
                        STORAGE_DIR, DLL_PATH_32);
            } else if (is64Bit()) {
                libraryLoaded[0] = loadDll(DLL_PATH_PARENT + DLL_PATH_BIT_64,
                        STORAGE_DIR, DLL_PATH_MSVCR);
                libraryLoaded[1] = loadDll(DLL_PATH_PARENT + DLL_PATH_BIT_64,
                        STORAGE_DIR, DLL_PATH_64);
            } else {
                throw new Exception(MSG_UNKNOWN_OS_VERSION);
            }
        } catch (UnsatisfiedLinkError e) {
            log.exceptionLogger(e, Level.CONFIG);
        } catch (IOException e) {
            log.exceptionLogger(e, Level.WARNING);
        } catch (Exception e) {
            log.exceptionLogger(e, Level.CONFIG);
        }
    }

    /**
     * Method to check if the OS is 32-bits
     * 
     * @return True if the OS is 32-bits; False otherwise
     */
    private boolean is32Bit() {
        String jvmVer = System.getProperty(OS_BIT_PROPERTY);
        return jvmVer.compareTo(OS_32_BIT) == 0;
    }

    /**
     * Method to check if the OS is 64-bits
     * 
     * @return True if the OS is 64-bits; False otherwise
     */
    private boolean is64Bit() {
        String jvmVer = System.getProperty(OS_BIT_PROPERTY);
        return jvmVer.compareTo(OS_64_BIT) == 0;
    }

    public static TaskAndConfigStorage getInstance() {
        if (instance == null) {
            instance = new TaskAndConfigStorage();
        }
        return instance;
    }

    /**
     * Task storage method
     */

    /**
     * Method to read in task data from the text file
     * 
     * @param file
     *            The directory of the text file
     * 
     * @return return an arraylist of saved tasks from the text file
     */
    public ArrayList<Object> readFromFile(String file) {

        ArrayList<Object> result = new ArrayList<Object>();
        CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask> holder = new CollectionOfTask<FloatTask, DeadLineTask, RepeatedTask>();

        try {
            FileReader reader = new FileReader(STORAGE_DIR + file);
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
    	
    	if(file==null){
    		return false;
    	}

        try {
            FileWriter fw = new FileWriter(STORAGE_DIR + file);
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
     * Configuration storage method
     */

    /**
     * Method to read in configuration data from the text file
     * 
     * @param file
     *            The directory of the text file
     * 
     * @return return an config object from the text file
     */
    public ArrayList<String> readConfigFile() {

        ArrayList<String> config = new ArrayList<String>();
        try {
            if (!configFile.exists()) {
                return null;
            }
            FileReader reader = new FileReader(configFile);
            Gson gson = createGsonObj();
            TypeToken<ArrayList<String>> typeToken = new TypeToken<ArrayList<String>>() {
            };
            config = gson.fromJson(reader, typeToken.getType());
            reader.close();
        } catch (Exception e) {
            log.exceptionLogger(e, Level.SEVERE);
            return null;
        }
        return config;
    }

    /**
     * Method to write configuration information to the text file
     * 
     * @param file
     *            The directory of the text file
     * @param Config
     *            Config object which contains all the configuration data
     * 
     * @return return a boolean indicating whether the write operation is a
     *         success or fail
     */
    public boolean writeConfigFile(ArrayList<String> configArr) {
        try {
            FileWriter fw = new FileWriter(configFile);
            Gson gson = createGsonObj();
            TypeToken<ArrayList<String>> typeToken = new TypeToken<ArrayList<String>>() {
            };
            String output = gson.toJson(configArr, typeToken.getType());
            fw.write(output);
            fw.close();
        } catch (Exception e) {
            log.exceptionLogger(e, Level.SEVERE);
            return false;
        }
        return true;
    }

    /**
     * Helper methods
     */

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

    /**
     * Method to load a dll into the JVM
     * 
     * @param parent
     *            The path to the parent of the library
     * @param outputFolder
     *            The destination folder
     * @param library
     *            the library to be loaded
     * @return the file object of the library
     * @throws IOException
     *             thrown if there is an error reading the file
     */
    private File loadDll(String parent, String outputFolder, String library)
            throws IOException, UnsatisfiedLinkError {
        InputStream in = TaskAndConfigStorage.class.getResourceAsStream(parent
                + library);
        byte[] buffer = new byte[1024];
        int read = -1;
        File windowsUserTempDirectory = new File(outputFolder);
        if (!windowsUserTempDirectory.exists()) {
            windowsUserTempDirectory.mkdir();
        }
        File temp = new File(windowsUserTempDirectory, library);
        if (temp.exists()) {
            temp.delete();
        }
        // System.out.println("Creating temp dll: " + temp.getAbsolutePath());
        FileOutputStream fos = new FileOutputStream(temp);

        while ((read = in.read(buffer)) != -1) {
            fos.write(buffer, 0, read);
        }
        fos.close();
        in.close();

        System.load(temp.getAbsolutePath());

        return temp;
    }

}
