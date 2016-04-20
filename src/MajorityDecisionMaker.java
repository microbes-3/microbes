import java.util.HashMap;

public class MajorityDecisionMaker implements DecisionMaker {
	public MajorityDecisionMaker() {}

	public int decide(int[] votes) {
		HashMap<Integer, Integer> count = new HashMap<Integer, Integer>();
		for (Integer vote : votes) {
			if (!count.containsKey(vote)) {
				count.put(vote, 1);
			} else {
				count.put(vote, count.get(vote) + 1);
			}
		}

		int max = 0;
		int maxKey = -1;
		for (Integer key : count.keySet()) {
			if (count.get(key) > max) {
				max = count.get(key);
				maxKey = key;
			}
		}
		return maxKey;
	}
}
