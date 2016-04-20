import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.FastVector;
import weka.classifiers.Classifier;

import static org.junit.Assert.*;
import org.junit.*;

public class VoterTest {
	@Test
	public void dummy() throws Exception {
		ArrayList<Classifier> classifiers = new ArrayList<Classifier>();
		classifiers.add(new MockClassifier(0));
		classifiers.add(new MockClassifier(1));
		classifiers.add(new MockClassifier(1));

		DecisionMaker dm = new MajorityDecisionMaker();
		Voter voter = new Voter(dm, classifiers);

		FastVector resultValues = new FastVector(2);
		resultValues.addElement("1");
		resultValues.addElement("2");
		Attribute result = new Attribute("result", resultValues);

		FastVector attrs = new FastVector(1);
		attrs.addElement(result);

		Instances data = new Instances("voter_test", attrs, 1);
		data.setClassIndex(0);

		Instance inst1 = new Instance(1);
		inst1.setValue(0, 1);
		data.add(inst1);

		Instance inst2 = new Instance(1);
		inst2.setValue(0, 2);
		data.add(inst2);

		Instance inst = new Instance(1);
		inst.setDataset(data);

		int decision = voter.classifyInstance(inst);
		assertEquals(1, decision);
	}
}
