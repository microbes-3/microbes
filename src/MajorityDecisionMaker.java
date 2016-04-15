import java.util.HashMap;

//Retourne le resultat le plus frequent
public class MajorityDecisionMaker implements DecisionMaker {

	public MajorityDecisionMaker() {}

	public int decide(int[] vote) {
		HashMap<Integer, Integer> count = new HashMap<Integer, Integer>();
		for(Integer classifierRes : vote){
			if(!count.containsKey(classifierRes)){
				count.put(classifierRes, 1);
			}else{
				count.put(classifierRes, count.get(classifierRes) +1);
			}
		}
		
		Integer max = 0;
		for(Integer d : count.keySet()){
			if(count.get(d) > max){
				max = count.get(d);
			}
		}
		return max;
	}

}
