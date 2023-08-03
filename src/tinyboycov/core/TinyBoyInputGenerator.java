package tinyboycov.core;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

import tinyboy.core.ControlPad;
import tinyboy.core.TinyBoyInputSequence;
import tinyboy.util.AutomatedTester;

/**
 * The TinyBoy Input Generator is responsible for generating and refining inputs
 * to try and ensure that sufficient branch coverage is obtained.
 *
 * @author David J. Pearce
 *
 */
public class TinyBoyInputGenerator implements AutomatedTester.InputGenerator<TinyBoyInputSequence> {
	/**
	 * Represents the number of buttons on the control pad.
	 */
	private final static int NUM_BUTTONS = ControlPad.Button.values().length;
	/**
	 * Current batch being processed
	 */
	private ArrayList<TinyBoyInputSequence> worklist = new ArrayList<>();

	/**
	 * Create new input generator for the TinyBoy simulation.
	 */
	public TinyBoyInputGenerator() {
		// FIXME: this is a very simplistic and poor implementation. However, it
		// illustrates how to create input sequences!
		this.worklist.add(new TinyBoyInputSequence(ControlPad.Button.LEFT,ControlPad.Button.UP));
		this.worklist.add(new TinyBoyInputSequence(ControlPad.Button.LEFT,ControlPad.Button.LEFT));
	}

	@Override
	public boolean hasMore() {
		return this.worklist.size() > 0;
	}

	@Override
	public @Nullable TinyBoyInputSequence generate() {
		if (!this.worklist.isEmpty()) {
			// remove last item from worklist
			return this.worklist.remove(this.worklist.size() - 1);
		}
		return null;
	}

	/**
	 * A record returned from the fuzzer indicating the coverage and final state
	 * obtained for a given input sequence.
	 */
	@Override
	public void record(TinyBoyInputSequence input, BitSet coverage, byte[] state) {
		// NOTE: this method is called when fuzzing has finished for a given input. It
		// produces three potentially useful items: firstly, the input sequence that was
		// used for fuzzing; second, the set of instructions which were covered when
		// executing that sequence; finally, the complete state of the machine's RAM at
		// the end of the run.
		//
		// At this point, you will want to use the feedback gained from fuzzing to help
		// prune the space of inputs to try next. A few helper methods are given below,
		// but you will need to write a lot more.
	}

	/**
	 * Check whether a given input sequence is completely subsumed by another.
	 *
	 * @param lhs The one which may be subsumed.
	 * @param rhs The one which may be subsuming.
	 * @return True if lhs subsumed by rhs, false otherwise.
	 */
	public static boolean subsumedBy(BitSet lhs, BitSet rhs) {
		for (int i = lhs.nextSetBit(0); i >= 0; i = lhs.nextSetBit(i + 1)) {
			if (!rhs.get(i)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Reduce a given set of items to at most <code>n</code> inputs by randomly
	 * sampling.
	 *
	 * @param inputs List of inputs to sample from.
	 * @param n      Size of inputs after reduction.
	 */
	private static <T> void randomSample(List<T> inputs, int n) {
		// Randomly shuffle inputs
		Collections.shuffle(inputs);
		// Remove inputs until only n remain
		while (inputs.size() > n) {
			inputs.remove(inputs.size() - 1);
		}
	}
}
