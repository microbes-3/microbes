import java.util.HashMap;

public class WeightedDecisionMaker implements DecisionMaker {
	protected double[] weights;
	
	public WeightedDecisionMaker() {}

	public WeightedDecisionMaker(double[] weights) {
		this.setWeights(weights);
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public int decide(double[] votes) {
		if (this.weights == null) {
			throw new RuntimeException("No weights specified");
		}
		if (votes.length != this.weights.length) {
			throw new RuntimeException("Votes must have same length as weights");
		}

		HashMap<Integer, Double> results = new HashMap<Integer, Double>();

		double max = 0;
		int maxLabel = -1;

		for (int i = 0; i < votes.length; i++) {
			int vote = (int) votes[i];

			double result = results.get(vote) + this.weights[i];
			results.put(vote, result);

			if (result > max) {
				max = result;
				maxLabel = vote;
			}
		}

		return maxLabel;
	}
}

