import java.util.ArrayList;
import java.util.Enumeration;
import java.lang.Double;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Attribute;

import weka.classifiers.rules.OneR;

public class Microbes {
	public static final String DATASET_PATH = "./train_set.arff";

	public static Instances load() {
		LoadSave ls = new LoadSave();

		System.out.println("Loading dataset...");
		Instances data = ls.loadDataset(DATASET_PATH);
		data.setClassIndex(data.numAttributes() - 1);

		return data;
	}

	public static String[] listClasses(Instances data) {
		Attribute classesAttr = data.attribute(data.classIndex());
		Enumeration<String> classesEnum = classesAttr.enumerateValues();
		String[] classes = new String[classesAttr.numValues()];
		int i = 0;
		for (Double val = null; classesEnum.hasMoreElements(); i++) {
			classes[i] = classesEnum.nextElement();
		}
		return classes;
	}

	public static Instances filterAttributes(Instances data) throws Exception {
		System.out.println("Filtering attributes...");
		ArrayList<Instances> filtered = selection_variables.filtres(data);
		return filtered.get(0); // TODO: get the best one
	}

	public static ArrayList<Classifier> selectBestClassifiers(Instances data) throws Exception {
		System.out.println("Selecting best classifiers...");
		BestClassifierSelector bcs = new BestClassifierSelector();
		return bcs.select(data);
	}

	public static void main(String[] args) throws Exception {
		Instances data = load();

		data = filterAttributes(data);
		ArrayList<Classifier> classifiers = selectBestClassifiers(data);

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

	/*public static void main(String[] args) throws Exception {
		Instances data = load();
		String[] classes = listClasses(data);

		Classifier classifier = new OneR();
		classifier.buildClassifier(data);

		System.out.println("Classifying instances...");
		try {
			Instance inst = data.firstInstance();

			double[] dist = classifier.distributionForInstance(inst);
			for (int i = 0; i < classes.length; i++) {
				System.out.println(classes[i] + ": " + dist[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Done!");
	}*/
}
