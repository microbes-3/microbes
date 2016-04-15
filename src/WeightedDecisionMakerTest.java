import static org.junit.Assert.*;
import org.junit.*;

public class WeightedDecisionMakerTest {
	double decision;
	WeightedDecisionMaker dm;

	@Before
	public void initDecisionMaker() {
		double[] weights = new double[]{10, 20, 30};
		dm = new WeightedDecisionMaker(weights);
	}

	@Test
	public void dummy() {
		decision = dm.decide(new int[]{1, 1, 1});
		assertEquals(1, decision, 0.0);
	}

	@Test
	public void simpleMajority() {
		decision = dm.decide(new int[]{1, 0, 1});
		assertEquals(1, decision, 0.0);

		decision = dm.decide(new int[]{1, 0, 0});
		assertEquals(0, decision, 0.0);

		decision = dm.decide(new int[]{0, 1, 1});
		assertEquals(1, decision, 0.0);

		decision = dm.decide(new int[]{1, 0, 0});
		assertEquals(0, decision, 0.0);
	}

	@Test
	public void draw() {
		decision = dm.decide(new int[]{1, 1, 0});
		assertEquals(1, decision, 0.0);
	}
}
