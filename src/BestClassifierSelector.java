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
import weka.core.Instances;

public class BestClassifierSelector {

	/*
	 * Fonction permettant de choisir les meilleures parametres de trie pour le classifier J48
	 * @param Instances data : donn?s a tri?
	 * @return ps : classifier entrainn?sur les donn?s
	 */
	public Classifier selectBestJ48(Instances data) throws Exception{
		// setup classifier
				System.out.println("Select best options for J48");
				CVParameterSelection ps = new CVParameterSelection();
				ps.setClassifier(new J48());
				ps.setNumFolds(2);  // using 2-fold CV
				ps.addCVParameter("C 0.1 0.5 5");

				// build and output best options

				System.out.println("build J48");
				ps.buildClassifier(data);

				return ps;

	}

	/*
	 * Fonction permettant de choisir les meilleures parametres de trie pour le classifier J48
	 * @param Instances data : donn?s a tri?
	 * @return ps : classifier entrainn?sur les donn?s
	 */
	public Classifier selectBestZeroR(Instances data) throws Exception{
		// setup classifier
		ZeroR ps = new ZeroR();
		System.out.println("build ZeroR");
		ps.buildClassifier(data);

		return ps;

	}

	/*
	 * Fonction permettant de choisir les meilleures parametres de trie pour le classifier J48
	 * @param Instances data : donn?s a tri?
	 * @return ps : classifier entrainn?sur les donn?s
	 */
	public Classifier selectBestConjunctive(Instances data) throws Exception{
		// setup classifier
		System.out.println("Select best options for ConjunctiveRule");
		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new ConjunctiveRule());
		ps.setNumFolds(2);  // using 5-fold CV
		ps.addCVParameter("N 2 6 2");
		ps.addCVParameter("M 1.0 5.0 4");
		ps.addCVParameter("P -1 3 2");
		ps.addCVParameter("S 1 5 3");

		// build and output best options
		System.out.println("build ConjunctiveRule");
		ps.buildClassifier(data);


		return ps;

	}


	/*
	 * Fonction permettant de choisir les meilleures parametres de trie pour le classifier J48
	 * @param Instances data : donn?s a tri?
	 * @return ps : classifier entrainn?sur les donn?s
	 */
	public Classifier selectBestOneR(Instances data) throws Exception{
		System.out.println("Select best options for OneR");
		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new OneR());
		ps.setNumFolds(2);  // using 5-fold CV
		ps.addCVParameter("B 1 10 10");


		// build and output best options
		System.out.println("build OneR");
		ps.buildClassifier(data);


		return ps;
	}

	/*
	 * Fonction permettant de choisir les meilleures parametres de trie pour le classifier J48
	 * @param Instances data : donn?s a tri?
	 * @return ps : classifier entrainn?sur les donn?s
	 */
	public Classifier selectBestVFI(Instances data) throws Exception{
		System.out.println("Select best options for VFI");
		CVParameterSelection ps = new CVParameterSelection();
		ps.setClassifier(new VFI());
		ps.setNumFolds(2);  // using 5-fold CV
		ps.addCVParameter("B 0.1 1 20");


		System.out.println("Build VFI");
		// build and output best options
		ps.buildClassifier(data);

		return ps;
	}


	/*
	 * Fonction qui recupere les meilleurs classifier et qui les ajoute dans une ArrauList pour ensuite les envoy?au vote
	 * @param Instances data : donn?s a tri?
	 * @return ArrayList<Classifier> tabClassifier : arraylist contenant les meilleurs classifier
	 */
	public ArrayList<Classifier> select(Instances data) throws Exception {
		ArrayList<Classifier> tabClassifier = new ArrayList<Classifier>();

		tabClassifier.add(selectBestJ48(data));
		tabClassifier.add(selectBestZeroR(data));
		tabClassifier.add(selectBestConjunctive(data));
		tabClassifier.add(selectBestOneR(data));
		tabClassifier.add(selectBestVFI(data));

		return tabClassifier;
	}
}
