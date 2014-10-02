import java.io.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Storage {
	//global array list for loading all the task from storage file
	public static ArrayList<Task> globalArrList = new ArrayList<Task>();

	public static boolean readFromFile(String file){
		try{
			FileReader reader= new FileReader(file);
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setPrettyPrinting();
			Gson gson = gsonBuilder.create();
			globalArrList=gson.fromJson(reader, new TypeToken<ArrayList<Task>>(){}.getType());
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean writeToFile(String file){

		try{
			FileWriter fw= new FileWriter(file);

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setPrettyPrinting();
			Gson gson = gsonBuilder.create();

			String output= gson.toJson(globalArrList);

			if(globalArrList.isEmpty()){
				fw.write("");
			}
			else{
				fw.write(output);
			}

			fw.close();
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
