package tinyboycov.tests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javr.core.AvrInstruction;

/**
 * Tests for part 2 of the assignment.
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part2_Tests {
	/**
	 * Identifiers PINB mask.
	 */
	private final int PINB = 0x16;
	/**
	 * Identifies up button mask.
	 */
	private final int BUTTON_UP = 1;
	/**
	 * Identifies down button mask.
	 */
	private final int BUTTON_DOWN = 3;
	/**
	 * Identifies left button mask.
	 */
	private final int BUTTON_LEFT = 4;
	/**
	 * Identifies right button mask.
	 */
	private final int BUTTON_RIGHT = 5;

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_UU() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_UP),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_UP),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_UD() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_UP),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_DOWN),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_UL() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_UP),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_LEFT),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_UR() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_UP),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_RIGHT),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}
	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_DU() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_DOWN),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_UP),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_DD() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_DOWN),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_DOWN),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_DL() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_DOWN),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_LEFT),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_DR() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_DOWN),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_RIGHT),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_LU() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_LEFT),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_UP),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_LD() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_LEFT),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_DOWN),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_LL() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_LEFT),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_LEFT),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_LR() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_LEFT),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_RIGHT),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_RU() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_RIGHT),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_UP),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_RD() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_RIGHT),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_DOWN),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_RL() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_RIGHT),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_LEFT),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_RR() throws Exception {
		AvrInstruction[] instructions = new AvrInstruction[] {
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_RIGHT),
				new AvrInstruction.RJMP(-2),
				// Loop until button pressed
				new AvrInstruction.SBIS(this.PINB, this.BUTTON_RIGHT),
				new AvrInstruction.RJMP(-2),
				new AvrInstruction.RJMP(-1),
		};
		TestUtils.checkCoverage(100.0,instructions);
	}
}
