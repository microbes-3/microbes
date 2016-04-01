import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.misc.VFI;
import weka.classifiers.rules.ConjunctiveRule;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;

public class BestClassifierSelector {
	LoadSave loadSaver;

	public BestClassifierSelector(LoadSave ls) {
		this.loadSaver = ls;
	}

	public ArrayList<Classifier> select() throws Exception {
		ArrayList<Classifier> tabClassifier = new ArrayList<Classifier>();

		// load data
		Instances data = this.loadSaver.loadDataset("train_set.arff");;
		Instances validset = this.loadSaver.loadDataset("valid_set.arff");
		Instances testset = this.loadSaver.loadDataset("test_set.arff");
		data.setClassIndex(data.numAttributes() - 1);

		// setup classifier
		System.out.println("Select best options for J48");
		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new J48());
		ps.setNumFolds(2);  // using 2-fold CV
		ps.addCVParameter("C 0.1 0.5 5");

		// build and output best options

		System.out.println("build J48");
		ps.buildClassifier(data);		     
		tabClassifier.add(ps);
		System.out.println(Utils.joinOptions(ps.getBestClassifierOptions()));

		ZeroR model2 = new ZeroR();
		System.out.println("build ZeroR");
		model2.buildClassifier(data);

		System.out.println("Select best options for ConjunctiveRule");
		CVParameterSelection ps2 = new CVParameterSelection();
		ps2.setClassifier(new ConjunctiveRule());
		ps2.setNumFolds(2);  // using 5-fold CV
		ps2.addCVParameter("N 2 6 2");
		ps2.addCVParameter("M 1.0 5.0 4");
		ps2.addCVParameter("P -1 3 2");
		ps2.addCVParameter("S 1 5 3");

		// build and output best options
		System.out.println("build ConjunctiveRule");
		ps2.buildClassifier(data);
		tabClassifier.add(ps2);


		System.out.println("Select best options for OneR");
		CVParameterSelection ps3 = new CVParameterSelection();
		ps3.setClassifier(new OneR());
		ps3.setNumFolds(2);  // using 5-fold CV
		ps3.addCVParameter("B 1 10 10");	      


		// build and output best options
		System.out.println("build OneR");
		ps3.buildClassifier(data);
		tabClassifier.add(ps3);


		System.out.println("Select best options for VFI");
		CVParameterSelection ps4 = new CVParameterSelection();
		ps4.setClassifier(new VFI());
		ps4.setNumFolds(2);  // using 5-fold CV
		ps4.addCVParameter("B 0.1 1 20");


		System.out.println("Build VFI");
		// build and output best options
		ps3.buildClassifier(data);
		tabClassifier.add(ps4);

		return tabClassifier;
	}
}
