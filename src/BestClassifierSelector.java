/*
 * Classe de selection de Classifier
 * @author : Biard David, Barroch?Quentin
 */

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.misc.VFI;
import weka.classifiers.rules.ConjunctiveRule;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.functions.RBFNetwork;
import weka.core.Instances;

public class BestClassifierSelector {

	/*
	 * Fonction permettant de choisir les meilleures parametres de trie pour le classifier J48
	 * @param Instances data : donn?s a tri?
	 * @return ps : classifier entrainn?sur les donn?s
	 */
	public Classifier selectBestJ48(Instances data) throws Exception{
		// setup classifier
		System.out.println("Build J48");
		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new J48());
		ps.setNumFolds(2);
		ps.addCVParameter("C 0.1 0.5 5");

		// build and output best options

		ps.buildClassifier(data);

		return ps;
	}

	/*
	 * Fonction permettant de choisir les meilleures parametres de trie pour le classifier J48
	 * @param Instances data : donn?s a tri?
	 * @return ps : classifier entrainn?sur les donn?s
	 */
	public Classifier selectBestZeroR(Instances data) throws Exception{
		System.out.println("Build ZeroR");

		// setup classifier
		ZeroR ps = new ZeroR();
		ps.buildClassifier(data);

		return ps;

	}

	/*
	 * Fonction permettant de choisir les meilleures parametres de trie pour le classifier J48
	 * @param Instances data : donn?s a tri?
	 * @return ps : classifier entrainn?sur les donn?s
	 */
	public Classifier selectBestConjunctive(Instances data) throws Exception{
		System.out.println("Build ConjunctiveRule");

		// setup classifier
		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new ConjunctiveRule());
		ps.setNumFolds(2);
		ps.addCVParameter("N 2 6 2");
		ps.addCVParameter("M 1.0 5.0 4");
		ps.addCVParameter("P -1 3 2");
		ps.addCVParameter("S 1 5 3");

		// build and output best options
		ps.buildClassifier(data);


		return ps;

	}


	/*
	 * Fonction permettant de choisir les meilleures parametres de trie pour le classifier J48
	 * @param Instances data : donn?s a tri?
	 * @return ps : classifier entrainn?sur les donn?s
	 */
	public Classifier selectBestOneR(Instances data) throws Exception{
		System.out.println("Build OneR");

		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new OneR());
		ps.setNumFolds(2);
		ps.addCVParameter("B 1 5 10");

		// build and output best options
		ps.buildClassifier(data);


		return ps;
	}

	/*
	 * Fonction permettant de choisir les meilleures parametres de trie pour le classifier J48
	 * @param Instances data : donn?s a tri?
	 * @return ps : classifier entrainn?sur les donn?s
	 */
	public Classifier selectBestVFI(Instances data) throws Exception{
		System.out.println("Build VFI");

		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new VFI());
		ps.setNumFolds(2);
		ps.addCVParameter("B 0.1 1 20");

		// build and output best options
		ps.buildClassifier(data);

		return ps;
	}

	public Classifier selectBestNaiveBayes(Instances data) throws Exception {
		System.out.println("Build NaiveBayes");

		Classifier c = new NaiveBayes();
		c.buildClassifier(data);

		return c;
	}

	public Classifier selectBestBayesNet(Instances data) throws Exception {
		System.out.println("Build BayesNet");

		// TODO
		Classifier c = new BayesNet();
		c.setOptions(new String[]{"-D", "-Q", "weka.classifiers.bayes.net.search.local.K2", "--", "-P", "1", "-S", "BAYES", "-E", "weka.classifiers.bayes.net.estimate.SimpleEstimator", "--", "-A", "0.5"});

		c.buildClassifier(data);

		return c;
	}

	public Classifier selectBestRBFNetwork(Instances data) throws Exception {
		System.out.println("Build RBFNetwork");

		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new RBFNetwork());
		ps.setNumFolds(2);
		ps.addCVParameter("B 1 2 3");
		ps.addCVParameter("S 1 2 3");
		ps.addCVParameter("R 1.0E-9 1.0E-8 1.0E-7");
		ps.addCVParameter("M -1 -2 -3");
		ps.addCVParameter("W 0.1 0.2 0.3");

		// build and output best options
		ps.buildClassifier(data);

		return ps;
	}


	/*
	 * Fonction qui recupere les meilleurs classifier et qui les ajoute dans une ArrauList pour ensuite les envoy?au vote
	 * @param Instances data : donn?s a tri?
	 * @return ArrayList<Classifier> : arraylist contenant les meilleurs classifier
	 */
	public ArrayList<Classifier> select(Instances data) throws Exception {
		ArrayList<Classifier> classifiers = new ArrayList<Classifier>();

		classifiers.add(selectBestJ48(data));
		classifiers.add(selectBestZeroR(data));
		classifiers.add(selectBestConjunctive(data));
		classifiers.add(selectBestOneR(data));
		classifiers.add(selectBestVFI(data));
		classifiers.add(selectBestNaiveBayes(data));
		classifiers.add(selectBestBayesNet(data));
		classifiers.add(selectBestRBFNetwork(data));

		return classifiers;
	}
}
