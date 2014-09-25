import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Storage {
	//temporary  array list to work with
	public static ArrayList<Task> tempArrList = new ArrayList<Task>();

	public static boolean readFromFile(String fileName){
		JSONParser parser= new JSONParser();
		try{
			Object obj=parser.parse(new FileReader(fileName));
			JSONArray jsonArr=(JSONArray)obj;
			Iterator<JSONObject> iterator=jsonArr.iterator();
			while(iterator.hasNext()){
				JSONObject jObj= iterator.next();
				Task temp= new Task(jObj.get("Name").toString(),jObj.get("ID").toString(),jObj.get("Status").toString(),jObj.get("Deadline").toString(),jObj.get("Workload").toString(),jObj.get("Description").toString());
				tempArrList.add(temp);
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;	
	}

}
