import java.util.ArrayList;

import weka.classifiers.*;
import weka.core.Instance;

// Effectue les votes en fonction de differents classifiers et d'un preneur de decision
public class Voter {
	private DecisionMaker dm;
	private ArrayList<Classifier> cList;

	public Voter(ArrayList<Classifier> c){
		this.dm = new MajorityDecisionMaker(); //Default Choice
		this.cList = c;
	}

	public Voter(DecisionMaker dm, ArrayList<Classifier> c){
		this.dm = dm;
		this.cList = c;
	}

	public void addClassifier(Classifier c){
		this.cList.add(c);
	}

	public void setDecisionMaker(DecisionMaker dm){
		this.dm = dm;
	}

	public int classifyInstance(Instance inst) throws Exception {
		double[] vote = new double[cList.size()];
		for(int i = 0; i < cList.size(); i++){
			vote[i] = cList.get(i).classifyInstance(inst);
		}
		return dm.decide(vote);
	}
}

