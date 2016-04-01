import java.util.HashMap;

//Retourne le resultat le plus frequent
public class MajorityDecisionMaker implements DecisionMaker {

	public MajorityDecisionMaker() {}

	public int decide(double[] vote) {
		HashMap<Double, Integer> count = new HashMap<Double, Integer>();
		for(Double classifierRes : vote){
			if(!count.containsKey(classifierRes)){
				count.put(classifierRes, 1);
			}else{
				count.put(classifierRes, count.get(classifierRes) +1);
			}
		}
		
		Integer max = 0;
		for(Double d : count.keySet()){
			if(count.get(d) > max){
				max = count.get(d);
			}
		}
		return max;
	}

}
