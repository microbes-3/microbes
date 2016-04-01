import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instance;

public class Microbes {
	public static void main(String[] args) {
		ArrayList<Classifier> classifiers = new ArrayList<Classifier>();
		DecisionMaker dm = new MajorityDecisionMaker();

		Voter voter = new Voter(dm, classifiers);

		try {
			Instance inst = null;
			int result = voter.classifyInstance(inst);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
