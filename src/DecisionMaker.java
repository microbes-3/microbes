public interface DecisionMaker {
	/**
	 * Decide of the result of a vote.
	 * @param votes An array of indexes corresponding to classifiers' responses.
	 * @return An index.
	 */
	public int decide(int[] votes);
}
