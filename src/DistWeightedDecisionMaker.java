import java.util.HashMap;

public class DistWeightedDecisionMaker extends DistDecisionMaker {
	public DistWeightedDecisionMaker() {}

	public int decide(int[] votes, double[] dists) {
		HashMap<Integer, Double> results = new HashMap<Integer, Double>();

		double max = 0;
		int maxLabel = -1;

		for (int i = 0; i < votes.length; i++) {
			int vote = votes[i];

			Double old = results.get(vote);
			if (old == null) {
				old = 0.;
			}

			double result = old + dists[i];
			results.put(vote, result);

			if (result > max) {
				max = result;
				maxLabel = vote;
			}
		}

		return maxLabel;
	}
}
