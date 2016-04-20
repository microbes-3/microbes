import java.util.ArrayList;
import java.io.File;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

import weka.classifiers.rules.OneR;
import weka.classifiers.rules.ZeroR;

public class Microbes {
	public static final String TRAINSET = "train_set.arff";
	public static final String TESTSET = "test_set.arff";
	public static final String VALIDSET = "valid_set.arff";

	public static final String TESTSET_PREDICT = "test.predict";
	public static final String VALIDSET_PREDICT = "valid.predict";

	public static final String MODELS_DIR = "models";

	public static void selectBestAttributes() throws Exception {
		LoadSave ls = new LoadSave();

		System.out.println("Loading train dataset...");
		Instances data = ls.loadDataset(TRAINSET);

		System.out.println("Filtering attributes...");
		ArrayList<Instances> filtered = selection_variables.filtres(data);
		//filtered.get(0)

		// TODO: print results
	}

	public static void selectBestClassifiers() throws Exception {
		LoadSave ls = new LoadSave();

		System.out.println("Loading train dataset...");
		Instances data = ls.loadDataset(TRAINSET);

		System.out.println("Selecting best classifiers...");
		BestClassifierSelector bcs = new BestClassifierSelector();
		ArrayList<Classifier> classifiers = bcs.select(data);

		System.out.println("Saving models...");

		File modelsDir = new File(MODELS_DIR);
		modelsDir.mkdir(); // Ensure models dir exists

		// Empty dir
		for (File file : modelsDir.listFiles()) {
			file.delete();
		}

		// Write models data
		for (int i = 0; i < classifiers.size(); i++) {
			ls.saveModel(classifiers.get(i), MODELS_DIR + "/" + i + ".model");
		}
	}

	private static void classify(Voter voter, Instances data) throws Exception {
		for (int i = 0; i < data.numInstances(); i++) {
			Instance inst = data.instance(i);
			int classIndex = voter.classifyInstance(inst);
			inst.setClassValue((double) classIndex);
		}
	}

	public static void trainAndClassify() throws Exception {
		LoadSave ls = new LoadSave();

		System.out.println("Loading train dataset...");
		Instances trainData = ls.loadDataset(TRAINSET);
		System.out.println("Loading test dataset...");
		Instances testData = ls.loadDataset(TESTSET);
		System.out.println("Loading valid dataset...");
		Instances validData = ls.loadDataset(VALIDSET);

		System.out.println("Loading classifiers...");
		ArrayList<Classifier> classifiers = new ArrayList<Classifier>();
		File modelsDir = new File(MODELS_DIR);
		for (File modelFile : modelsDir.listFiles()) {
			classifiers.add(ls.loadModel(modelFile.getAbsolutePath()));
			System.out.print(".");
		}
		System.out.println(" done");

		System.out.println("Initializing vote...");
		DecisionMaker dm = new MajorityDecisionMaker();
		Voter voter = new Voter(dm, classifiers);

		System.out.println("Classifying test instances...");
		classify(voter, testData);
		System.out.println("Classifying valid instances...");
		classify(voter, validData);

		System.out.println("Writing results to output files...");
		ls.saveResults(testData, TESTSET_PREDICT);
		ls.saveResults(validData, VALIDSET_PREDICT);
	}

	public static void main(String[] args) throws Exception {
		String action = "";
		if (args.length > 0) {
			action = args[0];
		}

		switch (action) {
		case "select-features":
			selectBestAttributes();
			break;
		case "build-models":
			selectBestClassifiers();
			break;
		case "classify":
			trainAndClassify();
			break;
		default:
			System.out.println("Usage: microbes select-features|build-models|classify");
			return;
		}

		System.out.println("Done!");
	}
}
