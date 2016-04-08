import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class Microbes {
	public static final String DATASET_PATH = "./train_set.arff";

	public static void main(String[] args) throws Exception {
		LoadSave ls = new LoadSave();

		Instances input = ls.loadDataset(DATASET_PATH);
		input.setClassIndex(input.numAttributes() - 1);

		ArrayList<Instances> filtered = new ArrayList<Instances>();
		selection_variables.filtres(input, filtered);

		BestClassifierSelector bcs = new BestClassifierSelector();
		ArrayList<Classifier> classifiers = bcs.select(filtered.get(0));

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
