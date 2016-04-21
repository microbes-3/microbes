import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import weka.core.Instance;
import weka.core.Instances;

public class VoteRanker {
	private ArrayList<Voter> voters = new ArrayList<Voter>();
	private HashMap<Integer, Double> results = new HashMap<Integer, Double>();
	private double total = 0;

	public VoteRanker() {}

	public VoteRanker(ArrayList<Voter> voters) {
		this.voters = voters;
	}

	public void addVoter(Voter voter) {
		this.voters.add(voter);
	}

	public void rankInstance(Instance inst) throws Exception {
		int real = (int) inst.classValue();

		for (int i = 0; i < this.voters.size(); i++) {
			int pred = this.voters.get(i).classifyInstance(inst);

			if (pred == real) {
				// Voter successfully classified instance

				double rank = 0;
				if (this.results.containsKey(i)) {
					rank = this.results.get(i);
				}

				this.results.put(i, rank + 1);
			}
		}

		this.total++;
	}

	public void rankInstances(Instances data) throws Exception {
		for (int i = 0; i < data.numInstances(); i++) {
			this.rankInstance(data.instance(i));
		}
	}

	public void save(String path) {
		PrintWriter writer;
		try {
			writer = new PrintWriter(path, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}

		for (int i = 0; i < this.results.size(); i++){
			writer.println("" + i + "," + (this.results.get(i) / this.total));
		}
		writer.close();
	}
}
