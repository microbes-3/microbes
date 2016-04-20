import java.util.HashMap;

public class DistWeightedDecisionMaker extends DistDecisionMaker {
	public DistWeightedDecisionMaker() {}

	public int decide(double[][] votes) {
		HashMap<Integer, Double> results = new HashMap<Integer, Double>();

		double max = 0;
		int maxLabel = -1;

		for (int i = 0; i < votes.length; i++) {
			double[] vote = votes[i];

			for (int classIndex = 0; classIndex < vote.length; classIndex++) {
				double weight = vote[classIndex];

				Double old = results.get(classIndex);
				if (old == null) {
					old = 0.;
				}

				double result = old + weight;
				results.put(classIndex, result);

				if (result > max) {
					max = result;
					maxLabel = classIndex;
				}
			}
		}

		return maxLabel;
	}
}
