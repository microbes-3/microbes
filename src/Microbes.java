import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;

import weka.classifiers.rules.OneR;
import weka.classifiers.rules.ZeroR;

public class Microbes {
	public static final String TRAINSET_PATH = "./train_set.arff";
	public static final String TESTSET_PATH = "./test_set.arff";
	public static final String VALIDSET_PATH = "./valid_set.arff";
	public static final String TESTSET_PREDICT_PATH = "./test.predict";
	public static final String VALIDSET_PREDICT_PATH = "./valid.predict";

	private static Instances selectBestAttributes(Instances data) throws Exception {
		System.out.println("Filtering attributes...");
		ArrayList<Instances> filtered = selection_variables.filtres(data);
		return filtered.get(0); // TODO: get the best one
	}

	private static ArrayList<Classifier> selectBestClassifiers(Instances data) throws Exception {
		System.out.println("Selecting best classifiers...");
		BestClassifierSelector bcs = new BestClassifierSelector();
		return bcs.select(data);
	}

	public static void selectBest() throws Exception {
		LoadSave ls = new LoadSave();

		System.out.println("Loading train dataset...");
		Instances data = ls.loadDataset(TRAINSET_PATH);

		data = selectBestAttributes(data);
		ArrayList<Classifier> classifiers = selectBestClassifiers(data);

		// TODO: print best attributes and best classifiers
		System.out.println("Done!");
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
		Instances trainData = ls.loadDataset(TRAINSET_PATH);
		System.out.println("Loading test dataset...");
		Instances testData = ls.loadDataset(TESTSET_PATH);
		System.out.println("Loading valid dataset...");
		Instances validData = ls.loadDataset(VALIDSET_PATH);

		System.out.println("Building classifiers...");
		ArrayList<Classifier> classifiers = new ArrayList<Classifier>();
		classifiers.add(new ZeroR());
		classifiers.add(new OneR());

		for (Classifier c : classifiers) {
			c.buildClassifier(trainData);
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
		ls.saveResults(testData, TESTSET_PREDICT_PATH);
		ls.saveResults(validData, VALIDSET_PREDICT_PATH);

		System.out.println("Done!");
	}

	public static void main(String[] args) throws Exception {
		trainAndClassify();
	}
}
