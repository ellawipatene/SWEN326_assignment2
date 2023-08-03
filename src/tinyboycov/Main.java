package tinyboycov;

import java.io.IOException;
import javrsim.peripherals.JPeripheral;
import javrsim.views.CodeView;
import javrsim.views.DataView;
import javrsim.views.JAvrView;
import javrsim.windows.SimulationWindow;
import tinyboy.core.TinyBoyEmulator;
import tinyboy.views.TinyBoyPeripheral;

/**
 * A simple tool for generate test coverage information for the AVR simulator.
 *
 * @author David J. Pearce
 *
 */
public class Main {
	/**
	 * The default set of peripherals.
	 */
	public static JPeripheral.Descriptor[] PERIPHERALS = {

	};
	/**
	 * The default set of views.
	 */
	public static JAvrView.Descriptor[] VIEWS = {
			CodeView.DESCRIPTOR,
			DataView.DESCRIPTOR
	};

	/**
	 * Entry point for running the TinyBoy simulation. This is just for playing
	 * around with the simulation, and is not part of the automated testing system.
	 *
	 * @param args Command-line arguments (should be none).
	 * @throws IOException If something goes wrong.
	 */
	public static void main(String[] args) throws IOException {
		// Construct the tinyBoy emulator
		TinyBoyEmulator tinyBoy = new TinyBoyEmulator();
		// Construct the main simulation window
		SimulationWindow sim = new SimulationWindow(tinyBoy.getAVR(), PERIPHERALS, VIEWS);
		// Finally, construct the TinyBoy view
		JPeripheral p = new TinyBoyPeripheral(tinyBoy);
		sim.addPeripheral(p);
	}

}
