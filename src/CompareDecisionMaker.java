import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class CompareDecisionMaker {
	private String filePath;
	private ArrayList<DecisionMaker> dms;
	
	
	public CompareDecisionMaker(String path){
		this.filePath = path;
	}
	
	public CompareDecisionMaker(String path, ArrayList<DecisionMaker> dmList){
		this.filePath = path;
	}
	
	public void addDecisionMaker(DecisionMaker dm){
		this.dms.add(dm);
	}

	public HashMap<String, Integer> compare(double[] vote){
		HashMap<String, Integer> results = new HashMap<String, Integer>();
		for(int i = 0; i < this.dms.size(); i++){
			//Il va falloir gérer les doublons
			String name = this.dms.getClass().toString();
			results.put(name, this.dms.get(i).decide(vote));
		}
		return results;
	}

	public void saveCompareDM(HashMap<String, Integer> results) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(this.filePath, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return;
		}

		for(String name : results.keySet()){
			writer.println(name + "," + results.get(name));
		}
		writer.close();
	}
}
