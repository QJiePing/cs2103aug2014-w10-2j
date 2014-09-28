import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Storage {
	//temporary  array list to work with
	public static ArrayList<Task> globalArrList = new ArrayList<Task>();

	public static boolean readFromFile(String file){
		JSONParser parser= new JSONParser();
		try{
			Object obj=parser.parse(new FileReader(file));
			JSONArray jsonArr=(JSONArray)obj;
			Iterator<JSONObject> iterator=jsonArr.iterator();
			while(iterator.hasNext()){
				JSONObject jObj= iterator.next();
				Task temp= new Task(jObj.get("Name").toString(),jObj.get("ID").toString(),jObj.get("Status").toString(),jObj.get("Deadline").toString(),jObj.get("Workload").toString(),jObj.get("Description").toString());
				globalArrList.add(temp);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;	
	}

	public static boolean writeToFile(String file){

		JSONArray objArr= new JSONArray();
		try{
			FileWriter fw= new FileWriter(file);

			for(int index=0;index<globalArrList.size();index++){
				JSONObject obj= new JSONObject();
				obj.put("Name", globalArrList.get(index).getTaskName());
				obj.put("ID", globalArrList.get(index).getTaskID());
				obj.put("Status", globalArrList.get(index).getTaskStatus());
				obj.put("Deadline", globalArrList.get(index).getTaskDeadLine());
				obj.put("Workload", globalArrList.get(index).getTaskWorkLoad());
				obj.put("Description", globalArrList.get(index).getTaskDescription());
				objArr.add(obj);
			}
			fw.write(objArr.toJSONString());
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
