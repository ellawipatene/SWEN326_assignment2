package tinyboycov.tests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Tests for part 4 of the assignment.
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part4_Tests {
	/**
	 * Configure number of threads to use. You may need to hand tune this a little
	 * to maximum performance, which makes a real difference on the big domains.
	 */
	private final int NTHREADS = 1; // Runtime.getRuntime().availableProcessors();
	/**
	 * Number of inputs each thread to process in one go. You may need to hand
	 * tune this a little to maximum performance, which makes a real difference on
	 * the big domains.
	 */
	private final int BATCHSIZE = 128;
	/**
	 * Enable/disable enforcement of a hard timeout. Disabling this can help with
	 * debugging, but remember that the automated marking script will enforce a
	 * timeout.
	 */
	private static boolean HARD_TIMEOUT = true;

	/**
	 * Enable/disable the GUI. This can improve overall performance (especially for multiple threads).
	 */
	private static boolean GUI_ENABLED = true;

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_01() throws Exception {
		TestUtils.checkCoverage(85.0, "blocker_1.hex", HARD_TIMEOUT, GUI_ENABLED, this.NTHREADS, this.BATCHSIZE); //$NON-NLS-1$
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_02() throws Exception {
		TestUtils.checkCoverage(85.0, "blocker_2.hex", HARD_TIMEOUT, GUI_ENABLED, this.NTHREADS, this.BATCHSIZE); //$NON-NLS-1$
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_03() throws Exception {
		// NOTE: some condition checks in numbers prevent some areas for being reachable at all.
		TestUtils.checkCoverage(85.0, "numbers_1.hex", HARD_TIMEOUT, GUI_ENABLED, this.NTHREADS, this.BATCHSIZE); //$NON-NLS-1$
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_04() throws Exception {
		TestUtils.checkCoverage(85.0, "snake.hex", HARD_TIMEOUT, GUI_ENABLED, this.NTHREADS, this.BATCHSIZE); //$NON-NLS-1$
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_05() throws Exception {
		// NOTE: tetris is pretty hard to get beyond 84%.
		TestUtils.checkCoverage(85.0, "tetris.hex",HARD_TIMEOUT,GUI_ENABLED,this.NTHREADS,this.BATCHSIZE); //$NON-NLS-1$
	}
}
