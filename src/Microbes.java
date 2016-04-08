import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class Microbes {
	public static final String DATASET_PATH = "./train_set.arff";

	public static void main(String[] args) throws Exception {
		LoadSave ls = new LoadSave();

		System.out.println("Loading dataset...");
		Instances input = ls.loadDataset(DATASET_PATH);
		input.setClassIndex(input.numAttributes() - 1);

		System.out.println("Filtering attributes...");
		ArrayList<Instances> filtered = selection_variables.filtres(input);

		System.out.println("Selecting best classifier...");
		BestClassifierSelector bcs = new BestClassifierSelector();
		ArrayList<Classifier> classifiers = bcs.select(filtered.get(0));

		System.out.println("Initializing vote...");
		DecisionMaker dm = new MajorityDecisionMaker();
		Voter voter = new Voter(dm, classifiers);

		System.out.println("Classifying instances...");
		try {
			Instance inst = null;
			int result = voter.classifyInstance(inst);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Done!");
	}
}
