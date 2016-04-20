abstract public class DistDecisionMaker implements DecisionMaker {
	public int decide(int[] votes) {
		int max = -1;
		for (int vote : votes) {
			if (vote > max) {
				max = vote;
			}
		}

		double[][] dists = new double[votes.length][max + 1];

		for (int i = 0; i < votes.length; i++) {
			int vote = votes[i];
			dists[i][vote] = 1.0;
		}

		return this.decide(dists);
	}

	abstract public int decide(double[][] votes);
}
