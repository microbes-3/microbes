import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

public class Microbes {
	public static final String DATASET_PATH = "./train_set.arff";

	public static void main(String[] args) throws Exception {
		LoadSave ls = new LoadSave();

		System.out.println("Loading dataset...");
		Instances data = ls.loadDataset(DATASET_PATH);
		data.setClassIndex(data.numAttributes() - 1);

		System.out.println("Filtering attributes...");
		ArrayList<Instances> filtered = selection_variables.filtres(data);
		data = filtered.get(0); // TODO: get the best one

		System.out.println("Selecting best classifier...");
		BestClassifierSelector bcs = new BestClassifierSelector();
		ArrayList<Classifier> classifiers = bcs.select(data);

		System.out.println("Initializing vote...");
		DecisionMaker dm = new MajorityDecisionMaker();
		Voter voter = new Voter(dm, classifiers);

		System.out.println("Classifying instances...");
		try {
			Instance inst = data.firstInstance();
			int result = voter.classifyInstance(inst);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Done!");
	}
}
