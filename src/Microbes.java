import java.util.ArrayList;
import java.io.File;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Attribute;

public class Microbes {
	public static final String TRAINSET = "train_set.arff";
	public static final String TESTSET = "test_set.arff";
	public static final String VALIDSET = "valid_set.arff";

	public static final String TESTSET_PREDICT = "test.predict";
	public static final String VALIDSET_PREDICT = "valid.predict";

	public static final String CLASSIFIERS_DIR = "classifiers";

	public static String[] filteredAttributes = new String[]{"num_medications", "discharge_disposition_id", "num_lab_procedures", "num_procedures", "number_diagnoses", "diag_1", "diag_3", "insulin", "diag_2", "number_inpatient", "glipizide", "admission_source_id", "A1Cresult", "glyburide", "pioglitazone", "repaglinide", "change", "glimepiride", "rosiglitazone", "age", "readmitted", "acetohexamide", "chlorpropamide", "number_outpatient", "metformin-rosiglitazone", "troglitazone", "max_glu_serum", "metformin-pioglitazone", "acarbose", "glipizide-metformin"};

	public static void selectBestFeatures() throws Exception {
		LoadSave ls = new LoadSave();

		System.out.println("Loading train dataset...");
		Instances data = ls.loadDataset(TRAINSET);

		System.out.println("Filtering attributes...");
		ArrayList<Attribute[]> attributes = FeaturesSelection.select(data);

		System.out.println("Results:");
		for (Attribute[] attrs : attributes) {
			System.out.print("*");
			for (Attribute attr : attrs) {
				if (attr == null) continue; // TODO: why is this happening?
				System.out.print(" " + attr.name());
			}
			System.out.println("");
		}
	}

	public static void selectBestClassifiers() throws Exception {
		LoadSave ls = new LoadSave();

		System.out.println("Loading train dataset...");
		Instances data = ls.loadDataset(TRAINSET);

		System.out.println("Filtering attributes...");
		filterAttributes(data);

		System.out.println("Selecting best classifiers...");
		BestClassifierSelector bcs = new BestClassifierSelector();
		ArrayList<Classifier> classifiers = bcs.select(data);

		System.out.println("Saving models...");

		File modelsDir = new File(CLASSIFIERS_DIR);
		modelsDir.mkdir(); // Ensure models dir exists

		// Empty dir
		for (File file : modelsDir.listFiles()) {
			file.delete();
		}

		// Write models data
		for (int i = 0; i < classifiers.size(); i++) {
			ls.saveModel(classifiers.get(i), CLASSIFIERS_DIR + "/" + i + ".model");
		}
	}

	private static void filterAttributes(Instances data) {
		Attribute[] attrs = LoadSave.listAttributes(data);
		for (int i = attrs.length - 1; i >= 0; i--) {
			Attribute attr = attrs[i];
			if (attr == null) continue; // TODO: why is this even happening?

			boolean found = false;
			for (String name : filteredAttributes) {
				if (attr.name().equals(name)) {
					found = true;
					break;
				}
			}

			if (!found) {
				data.deleteAttributeAt(attr.index());
			}
		}
	}

	private static ArrayList<Classifier> loadClassifiers(LoadSave ls) {
		ArrayList<Classifier> classifiers = new ArrayList<Classifier>();
		File modelsDir = new File(CLASSIFIERS_DIR);
		for (File modelFile : modelsDir.listFiles()) {
			classifiers.add((Classifier) ls.loadModel(modelFile.getAbsolutePath()));
			System.out.print(".");
		}
		System.out.println(" done");

		return classifiers;
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

		System.out.println("Selecting " + filteredAttributes.length + "/" + trainData.numAttributes() + " attributes...");
		filterAttributes(trainData);
		filterAttributes(testData);
		filterAttributes(validData);

		System.out.println("Loading classifiers...");
		ArrayList<Classifier> classifiers = loadClassifiers(ls);

		System.out.println("Initializing vote...");
		DecisionMaker dm = new DistWeightedDecisionMaker();
		Voter voter = new Voter(dm, classifiers);

		System.out.println("Classifying test instances...");
		classify(voter, testData);
		System.out.println("Classifying valid instances...");
		classify(voter, validData);

		System.out.println("Writing results to output files...");
		ls.saveResults(testData, TESTSET_PREDICT);
		ls.saveResults(validData, VALIDSET_PREDICT);
	}

	public static void rankVoters() throws Exception {
		// Taux de succès de chaque classifier, d'après le tableau
		double[] successRates = new double[]{61.96, 50, 0, 57.86, 0, 71.51, 72.40, 66.6};

		LoadSave ls = new LoadSave();

		System.out.println("Loading train dataset...");
		Instances trainData = ls.loadDataset(TRAINSET);

		System.out.println("Selecting attributes...");
		filterAttributes(trainData);

		System.out.println("Loading classifiers...");
		ArrayList<Classifier> classifiers = loadClassifiers(ls);

		System.out.println("Initializing vote...");

		ArrayList<DecisionMaker> dms = new ArrayList<DecisionMaker>();
		dms.add(new MajorityDecisionMaker());
		dms.add(new WeightedDecisionMaker(successRates));
		dms.add(new DistWeightedDecisionMaker());

		VoteRanker ranker = new VoteRanker();
		for (DecisionMaker dm : dms) {
			Voter voter = new Voter(dm, classifiers);
			ranker.addVoter(voter);
		}

		System.out.println("Ranking voters...");
		ranker.rankInstances(trainData);

		System.out.println("Writing results to output file...");
		ranker.save("vote-rank.csv");
	}

	public static void main(String[] args) throws Exception {
		String action = "";
		if (args.length > 0) {
			action = args[0];
		}

		switch (action) {
		case "select-features":
			selectBestFeatures();
			break;
		case "build-classifiers":
			selectBestClassifiers();
			break;
		case "classify":
			trainAndClassify();
			break;
		case "rank-voters":
			rankVoters();
			break;
		default:
			System.out.println("Usage: microbes select-features|build-classifiers|classify");
			return;
		}

		System.out.println("Done!");
	}
}
