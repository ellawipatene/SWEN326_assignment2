package tinyboycov.tests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Tests for part 3 of the assignment.
 * @author David J. Pearce
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Part3_Tests {
	/**
	 * Configure number of threads to use. You may need to hand tune this a little
	 * to maximum performance, which makes a real difference on the big domains.
	 */
	private final int NTHREADS = 1; // Runtime.getRuntime().availableProcessors();
	/**
	 * Number of inputs each thread to process in one go. You may need to hand tune
	 * this a little to maximum performance, which makes a real difference on the
	 * big domains.
	 */
	private final int BATCHSIZE = 128;
	/**
	 * Enable/disable enforcement of a hard timeout. Disabling this can help with
	 * debugging, but remember that the automated marking script will enforce a
	 * timeout.
	 */
	private final boolean HARD_TIMEOUT = true;

	/**
	 * Enable/disable the GUI. This can improve overall performance (especially for
	 * multiple threads).
	 */
	private final boolean GUI_ENABLED = true;

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_01() throws Exception {
		TestUtils.checkCoverage(95.0, "blocks_1.hex", this.HARD_TIMEOUT, this.GUI_ENABLED, this.NTHREADS, this.BATCHSIZE); //$NON-NLS-1$
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_02() throws Exception {
		TestUtils.checkCoverage(95.0, "blocks_2.hex", this.HARD_TIMEOUT, this.GUI_ENABLED, this.NTHREADS, this.BATCHSIZE); //$NON-NLS-1$
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_03() throws Exception {
		TestUtils.checkCoverage(95.0, "blocks_3.hex", this.HARD_TIMEOUT, this.GUI_ENABLED, this.NTHREADS, this.BATCHSIZE); //$NON-NLS-1$
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_04() throws Exception {
		TestUtils.checkCoverage(95.0, "blocks_4.hex", this.HARD_TIMEOUT, this.GUI_ENABLED, this.NTHREADS, this.BATCHSIZE); //$NON-NLS-1$
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_05() throws Exception {
		TestUtils.checkCoverage(95.0, "blocks_5.hex", this.HARD_TIMEOUT, this.GUI_ENABLED, this.NTHREADS, this.BATCHSIZE); //$NON-NLS-1$
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_06() throws Exception {
		TestUtils.checkCoverage(95.0, "blocks_6.hex", this.HARD_TIMEOUT, this.GUI_ENABLED, this.NTHREADS, this.BATCHSIZE); //$NON-NLS-1$
	}

	/**
	 * A Test
	 * @throws Exception If something goes wrong.
	 */
	@Test
	public void test_07() throws Exception {
		TestUtils.checkCoverage(95.0, "blocks_7.hex", this.HARD_TIMEOUT, this.GUI_ENABLED, this.NTHREADS, this.BATCHSIZE); //$NON-NLS-1$
	}
}
