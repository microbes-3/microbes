abstract public class DistDecisionMaker implements DecisionMaker {
	public int decide(int[] vote) {
		double[] pred = new double[vote.length];
		for (int i = 0; i < vote.length; i++) {
			pred[i] = 1;
		}

		return this.decide(vote, pred);
	}

	abstract public int decide(int[] vote, double[] dist);
}
