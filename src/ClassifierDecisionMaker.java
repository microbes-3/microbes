import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.FastVector;
import weka.classifiers.Classifier;

public class ClassifierDecisionMaker implements DecisionMaker {
	private Classifier classifier;

	public ClassifierDecisionMaker(Classifier classifier) {
		this.classifier = classifier;
	}

	private Instance buildInstance(double[] votes, Double result) {
		Instance inst = new Instance(votes.length + 1);
		for (int i = 0; i < votes.length; i++) {
			inst.setValue(i, votes[i]);
		}
		inst.setValue(votes.length, result);
		return inst;
	}

	public int decide(double[] votes) {
		Instance inst = this.buildInstance(votes, null);

		try {
			return (int) this.classifier.classifyInstance(inst);
		} catch (Exception e) {
			throw new RuntimeException("Cannot classify instance: "+e.getMessage());
		}
	}

	public void train(double[][] votes, double[] results) {
		FastVector attrs = new FastVector();

		Instances instances = new Instances("train_vote", attrs, votes.length);
		for (int i = 0; i < votes.length; i++) {
			instances.add(this.buildInstance(votes[i], results[i]));
		}

		try {
			this.classifier.buildClassifier(instances);
		} catch (Exception e) {
			throw new RuntimeException("Cannot build classifier: "+e.getMessage());
		}
	}
}