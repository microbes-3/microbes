import java.util.ArrayList;

import weka.classifiers.*;
import weka.core.Instance;

// Effectue les votes en fonction de differents classifiers et d'un preneur de decision
public class Voter {
	private DecisionMaker dm;
	private ArrayList<Classifier> classifiers;

	public Voter(ArrayList<Classifier> c){
		this.dm = new MajorityDecisionMaker(); //Default Choice
		this.classifiers = c;
	}

	public Voter(DecisionMaker dm, ArrayList<Classifier> c){
		this.dm = dm;
		this.classifiers = c;
	}

	public void addClassifier(Classifier c){
		this.classifiers.add(c);
	}

	public void setDecisionMaker(DecisionMaker dm){
		this.dm = dm;
	}

	public int classifyInstance(Instance inst) throws Exception {
		double[] vote = new double[classifiers.size()];
		for(int i = 0; i < classifiers.size(); i++){
			vote[i] = classifiers.get(i).classifyInstance(inst);
		}
		return dm.decide(vote);
	}
}
