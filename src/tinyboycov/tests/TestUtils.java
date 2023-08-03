package tinyboycov.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.jdt.annotation.Nullable;

import javr.core.AVR;
import javr.core.AvrDecoder;
import javr.core.AvrInstruction;
import javr.io.HexFile;
import javr.memory.ByteMemory;
import tinyboy.util.AutomatedTester;
import tinyboy.util.CoverageAnalysis;
import tinyboycov.core.TinyBoyInputGenerator;
import tinyboycov.util.ProcessTimerMethod;

/**
 * Test utilities for the assignment
 *
 * *** DO NOT CHANGE THIS FILE ***
 *
 * @author David J. Pearce
 *
 */
public class TestUtils {
	/**
	 * Amount of time test is allowed to execute for. Note that this is an aid only,
	 * and should given a result indicative of the what the automated marking system
	 * will do.
	 */
	private static final long TIMEOUT = 300_000; // 5mins
	/**
	 * Specifies where to find the firmware images.
	 */
	private static final String FIRMWARE_DIR;

	static {
		// Manage safe static initialiser
		String dir = "tests/".replace("/", File.separator); //$NON-NLS-1$ //$NON-NLS-2$
		assert dir != null;
		FIRMWARE_DIR = dir;
	}

	/**
	 * Perform automated coverage analysis of a given instruction sequence. This
	 * sequence is first converted into a firmware image before being uploaded onto
	 * the tiny boy.
	 *
	 * @param target       The target coverage (as a percentage).
	 * @param instructions The program being tested.
	 * @throws Exception if something goes wrong.
	 */
	public static void checkCoverage(double target, AvrInstruction... instructions) throws Exception {
		// Determine test name
		String name = getMethodName(1);
		// Generate firmware
		HexFile firmware = assemble(instructions);
		// Done
		TestUtils.checkCoverage(name, firmware, target, false, false, 1, 1);
	}

	/**
	 * Perform automated coverage analysis of a given firmware file. The firmware is
	 * first loaded from the appropriate location and then uploaded into the
	 * TinyBoy.
	 *
	 * @param target    The target coverage (as a percentage).
	 * @param filename  File name of firmware image
	 * @param timeout   Time limit
	 * @param gui       Flag to show Graphical User Interface.
	 * @param nThreads  Number of threads to use.
	 * @param batchSize Batch size of jobs for each thread.
	 * @throws Exception If something goes wrong.
	 */
	public static void checkCoverage(double target, String filename, boolean timeout, boolean gui, int nThreads, int batchSize) throws Exception {
		// Determine test name
		String name = getMethodName(1) + ":" + filename; //$NON-NLS-1$
		// Generate firmware
		try(FileReader fr = new FileReader(FIRMWARE_DIR + filename)) {
			HexFile.Reader reader = new HexFile.Reader(fr);
			HexFile firmware = reader.readAll();
			assert firmware != null;
			// Done
			TestUtils.checkCoverage(name, firmware, target, timeout, gui, nThreads, batchSize);
		}
	}

	/**
	 * Perform automated coverage analysis of a given firmware.
	 *
	 * @param name     Name used for printing out report
	 * @param firmware The firmware image being used
	 * @param target    The target coverage (as a percentage).
	 * @param timeout   Time limit
	 * @param gui       Flag to show Graphical User Interface.
	 * @param nThreads  Number of threads to use.
	 * @param batchSize Batch size of jobs for each thread.
	 * @throws Exception If something goes wrong.
	 */
	public static void checkCoverage(String name, HexFile firmware, double target, boolean timeout, boolean gui, int nThreads, int batchSize) throws Exception {
		// Manage safe boxing
		final Double _target = Double.valueOf(target);
		final Boolean _gui = Boolean.valueOf(gui);
		final Integer _nThreads = Integer.valueOf(nThreads);
		final Integer _batchSize = Integer.valueOf(batchSize);
		assert _target != null;
		assert _gui != null;
		assert _nThreads != null;
		assert _batchSize != null;
		// Done
		if (timeout) {
			String testClassName = TestUtils.class.getName();
			assert testClassName != null;
			try {
				ProcessTimerMethod.Outcome r = ProcessTimerMethod.exec(TIMEOUT, testClassName,
						"checkCoverageWithTimeout", name, firmware, _target, _gui, //$NON-NLS-1$
						_nThreads, _batchSize);
				//
				System.out.println(new String(r.getStdout()));
				System.out.println(new String(r.getStderr()));
				@Nullable Integer r_exitcode = r.exitCode();
				//
				if (r_exitcode == null) {
					fail("Timeout occurred"); //$NON-NLS-1$
				} else if (r_exitcode.intValue() != 0) {
					fail("Test failure"); //$NON-NLS-1$
				}
			} catch (IOException e) {
				throw e;
			} catch (Throwable e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		} else {
			checkCoverageWithTimeout(name, firmware, _target, _gui, _nThreads, _batchSize);
		}
	}

	/**
	 * Perform automated coverage analysis of a given firmware.
	 *
	 * @param name     Name used for printing out report
	 * @param firmware The firmware image being used
	 * @param target    The target coverage (as a percentage).
	 * @param gui       Flag to show Graphical User Interface.
	 * @param nThreads  Number of threads to use.
	 * @param batchSize Batch size of jobs for each thread.
	 * @throws Exception If something goes wrong.
	 */
	public static void checkCoverageWithTimeout(String name, HexFile firmware, Double target, Boolean gui, Integer nThreads, Integer batchSize) throws Exception {
		long time = System.currentTimeMillis();
		// Construct the input generator
		AutomatedTester.InputGenerator<?> generator = new TinyBoyInputGenerator();
		// Construct the fuzz tester
		AutomatedTester<?> tester = new AutomatedTester<>(firmware, generator, gui.booleanValue(), nThreads.intValue(), batchSize.intValue());
		// Run the fuzz tester for 50 inputs.
		CoverageAnalysis coverage = tester.run(target.doubleValue());
		// Record time
		time = System.currentTimeMillis() - time;
		// Destroy GUI (if present)
		tester.destroy();
		// Check whether the target was reached.
		if (coverage.getBranchCoverage() < target.doubleValue()) {
			// Indicates a fail
			System.out.println("==============================================="); //$NON-NLS-1$
			System.out.println(name + " (" + String.format("%.2f", Double.valueOf(coverage.getInstructionCoverage())) //$NON-NLS-1$ //$NON-NLS-2$
					+ "% instructions, " + String.format("%.2f", Double.valueOf(coverage.getBranchCoverage())) + "% branches, " + time + "ms)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			System.out.println("==============================================="); //$NON-NLS-1$
			printDisassembly(firmware, coverage);
			fail("Branch coverage failed to meet target of " + target + "%"); //$NON-NLS-1$ //$NON-NLS-2$
		} else {
			printDisassembly(firmware, coverage);
			System.out.println("TIME: " + time + "ms"); //$NON-NLS-1$ //$NON-NLS-2$
		}

	}

	/**
	 * This is an "interesting" method which determines the name of a method on the
	 * call stack, as determined by a given index relative to the position of the
	 * caller.
	 *
	 * @param n Index of stack frame relative to caller.
	 * @return The method's name.
	 */
	private static String getMethodName(int n) {
		StackTraceElement[] trace = new Exception().getStackTrace();
		assert trace != null;
		StackTraceElement element = trace[1 + n];
		assert element != null;
		String r = element.getMethodName();
		assert r != null;
		return r;
	}


	/**
	 * Responsible for turning a given sequence of instructions into a hexfile, so
	 * that it can in turn be uploaded to the tiny boy.
	 *
	 * @param instructions The program to be assembled into a hexfile.
	 * @return The assembled hexfile.
	 */
	private static HexFile assemble(AvrInstruction... instructions) {
		byte[][] chunks = new byte[instructions.length][];
		int total = 0;
		// Encode each instruction into a byte sequence
		for(int i=0;i!=instructions.length;++i) {
			byte[] bytes = instructions[i].getBytes();
			chunks[i] = bytes;
			total = total + bytes.length;
		}
		// Flatten the chunks into a sequence
		byte[] sequence = new byte[total];
		//
		for(int i=0,j=0;i!=chunks.length;++i) {
			byte[] chunk = chunks[i];
			System.arraycopy(chunk, 0, sequence, j, chunk.length);
			j = j + chunk.length;
		}
		// Finally, create the hex file!
		HexFile hf = HexFile.toHexFile(sequence,16);
		assert hf != null;
		return hf;
	}

	/**
	 * Disassemble the firmware image in order to provide useful feedback. This is
	 * essentially provided to help with debugging, and to determine where the
	 * unreachable code is in a given firmware image.
	 *
	 * @param firmware The firmware file to be disassembled.
	 * @param coverage The computed coverage which is included in the output.
	 */
	public static void printDisassembly(HexFile firmware, CoverageAnalysis coverage) {
		AvrDecoder decoder = new AvrDecoder();
		AVR.Memory code = new ByteMemory(8192);
		firmware.uploadTo(code);
		int size = code.size() / 2;
		boolean ignoring = false;
		int instructions = 0;
		int coveredInstructions = 0;
		int branches = 0;
		int coveredBranches = 0;
		for (int i = 0; i != size;) {
			if (coverage.isReachableInstruction(i)) {
				AvrInstruction insn = decoder.decode(code, i);
				System.out.print(String.format("%04X", Integer.valueOf(i))); //$NON-NLS-1$
				instructions++;
				if (coverage.wasCovered(i)) {
					System.out.print(" [*] "); //$NON-NLS-1$
					coveredInstructions++;
				} else {
					System.out.print(" [ ] "); //$NON-NLS-1$
				}
				System.out.print(insn.toString());
				if (coverage.isConditionalBranchCovered(i)) {
					System.out.println("\t<<<<<<<<<<<<<<<<<<<< (" + branches++ + ")"); //$NON-NLS-1$ //$NON-NLS-2$
					coveredBranches++;
				} else if (coverage.isConditionalBranch(i)) {
					System.out.println("\t<<<<<<<<<<<<<<<<<<<< UNCOVERED (" + branches++ + ")"); //$NON-NLS-1$ //$NON-NLS-2$
				} else {
					System.out.println();
				}
				i = i + insn.getWidth();
				ignoring = false;
			} else {
				if (!ignoring) {
					System.out.println(" ... "); //$NON-NLS-1$
					ignoring = true;
				}
				i = i + 1;
			}
		}
		System.out.println(
				"Instruction Coverage = " + coveredInstructions + " / " + instructions + " (" + code.size() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		System.out.println("Branch Coverage = " + coveredBranches + " / " + branches + " (" + coverage.getBranchCoverage() + "%)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

}
