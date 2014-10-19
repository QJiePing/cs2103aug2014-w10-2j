package taskaler.storage;

import taskaler.common.data.Task;
import java.io.*;
import java.util.ArrayList;

import taskaler.common.data.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/*
 * @author Quek Jie Ping A0111798X
 */

/**
 * This is the main storage class which performs storage function of Taskaler.
 * To use this class, no instantiation of storage class is needed.
 */
public class Storage {

    /**
     * Method to read in task data from the text file
     * 
     * @param file
     *            The directory of the text file
     * 
     * @return return an arraylist of saved tasks from the text file
     */
    public static ArrayList<Task> readFromFile(String file) {

        // temporary holder to store an arraylist of saved tasks from the text
        // file
        ArrayList<Task> resultArrayList = new ArrayList<Task>();

        try {
            FileReader reader = new FileReader(file);
            Gson gson = createGsonObj();
            TypeToken<ArrayList<Task>> typeToken = new TypeToken<ArrayList<Task>>() {
            };
            resultArrayList = gson.fromJson(reader, typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return resultArrayList;
    }

    /**
     * Method to write all saved tasks information to the text file
     * 
     * @param file
     *            The directory of the text file
     * @param arrayList
     *            The arraylist of tasks to be written to the text file
     * 
     * @return return a boolean indicating whether the write operation is a
     *         success or fail
     */
    public static boolean writeToFile(String file, ArrayList<Task> arrayList) {

        try {
            FileWriter fw = new FileWriter(file);
            Gson gson = createGsonObj();
            String output = gson.toJson(arrayList);

            if (arrayList.isEmpty()) {
                fw.write("");
            } else {
                fw.write(output);
            }

            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Method to instantiate a gson object for the reading json object from json
     * formatted file and output all saved task information in json format to
     * the text file.
     * 
     * @return return a gson object
     */
    private static Gson createGsonObj() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson;
    }

    
    public static void storageWriteStub(String file, String message){
        System.out.println("=====================================");
        System.out.println("Filename : " + file);
        System.out.println("Content : " + message);
        System.out.println("=====================================");
    }
    
    public static String storageReadStub(String file){
        System.out.println("=====================================");
        System.out.println("Filename : " + file);
        System.out.println("Giving back Weird String");
        System.out.println("=====================================");
        
        return "WEIRD WEIRD WEIRD\nWEIRD WEIRD WEIRD";
    }
}
