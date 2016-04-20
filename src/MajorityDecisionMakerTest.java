import static org.junit.Assert.*;
import org.junit.*;

public class MajorityDecisionMakerTest {
	double decision;
	MajorityDecisionMaker dm;

	@Before
	public void init(){
		dm = new MajorityDecisionMaker();
	}

	@Test
	public void dummy(){
		decision = dm.decide(new int[]{0,0,0,0,0});
		assertEquals(0, decision, 0.0);

		decision = dm.decide(new int[]{1,1});
		assertEquals(1, decision, 0.0);

		decision = dm.decide(new int[]{2,2,2,2,2,2,2});
		assertEquals(2, decision, 0.0);
	}

	@Test
	public void simpleMajority() {
		decision = dm.decide(new int[]{1,1});
		assertEquals(1, decision, 0.0);

		decision = dm.decide(new int[]{1,1,0});
		assertEquals(1, decision, 0.0);

		decision = dm.decide(new int[]{0,0,0,0,1,1});
		assertEquals(0, decision, 0.0);

		decision = dm.decide(new int[]{1,2,3,4,4});
		assertEquals(4, decision, 0.0);
	}

	@Test
	public void draw() {
		decision = dm.decide(new int[]{0,0,1,1});
		assertEquals(1, decision, 0.0);
		
		decision = dm.decide(new int[]{1,1,1,0,0,0});
		assertEquals(0, decision, 0.0);
	}

}
