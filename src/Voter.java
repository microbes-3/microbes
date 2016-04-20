import java.util.ArrayList;

import weka.classifiers.Classifier;
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
		int[] votes = new int[n];
		double[][] dists = new double[n][inst.numClasses()];

		boolean useDists = (this.dm instanceof DistDecisionMaker);

		for (int i = 0; i < n; i++) {
			double[] dist = classifiers.get(i).distributionForInstance(inst);

			if (useDists) {
				dists[i] = dist;
			} else {
				int classIndex = -1;
				double classWeight = 0;
				for (int j = 0; j < dist.length; j++) {
					if (classIndex == -1 || dist[j] > classWeight) {
						classIndex = j;
						classWeight = dist[j];
					}
				}
				votes[i] = classIndex;
			}
		}

		if (useDists) {
			return ((DistDecisionMaker) this.dm).decide(dists);
		} else {
			return this.dm.decide(votes);
		}
	}
}
