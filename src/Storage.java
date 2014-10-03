import java.io.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Storage {

	public static ArrayList<Task> readFromFile(String file){
		
		ArrayList<Task> resultArrayList = new ArrayList<Task>();
		
		try{
			FileReader reader= new FileReader(file);
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setPrettyPrinting();
			Gson gson = gsonBuilder.create();
			resultArrayList=gson.fromJson(reader, new TypeToken<ArrayList<Task>>(){}.getType());
		}catch(IOException e){
			e.printStackTrace();
			return null;
		}
		return resultArrayList;
	}

	public static boolean writeToFile(String file, ArrayList<Task> arrayList){

		try{
			FileWriter fw= new FileWriter(file);

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setPrettyPrinting();
			Gson gson = gsonBuilder.create();

			String output= gson.toJson(arrayList);

			if(arrayList.isEmpty()){
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
