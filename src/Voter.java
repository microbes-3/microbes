import java.util.ArrayList;

import weka.classifiers.*;
import weka.core.Instance;

// Effectue les votes en fonction de differents classifiers et d'un preneur de decision
public class Voter {
	private DecisionMaker dm;
	private ArrayList<Classifier> classifiers;

	public Voter(DecisionMaker dm, ArrayList<Classifier> c) {
		this.dm = dm;
		this.classifiers = c;
	}

	public void addClassifier(Classifier c) {
		this.classifiers.add(c);
	}

	public void setDecisionMaker(DecisionMaker dm) {
		this.dm = dm;
	}

	public int classifyInstance(Instance inst) throws Exception {
		int n = classifiers.size();
		int[] vote = new int[n];
		double[] dist = new double[n];

		for (int i = 0; i < n; i++) {
			double[] instDist = classifiers.get(i).distributionForInstance(inst);

			// TODO: get correct class name
			int klass = -1;
			double classDist = 0;
			for (int j = 0; j < instDist.length; i++) {
				if (klass == -1 || instDist[j] > classDist) {
					klass = j;
					classDist = instDist[j];
				}
			}

			vote[i] = klass;
			dist[i] = classDist;
		}

		if (this.dm instanceof DistDecisionMaker) {
			return ((DistDecisionMaker) this.dm).decide(vote, dist);
		}
		return this.dm.decide(vote);
	}
}
