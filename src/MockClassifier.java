import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.Instance;

@SuppressWarnings("serial")
public class MockClassifier extends Classifier {
	private double result;

	public MockClassifier(double result) {
		this.result = result;
	}

	public void buildClassifier(Instances data) {
		// Do nothing
	}

	public double classifyInstance(Instance instance) {
		return this.result;
	}
}
