// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a SWEN221 assignment.
// You may not distribute it in any other way without permission.
package tinyboycov.util;
import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Responsible for executing a Java method whilst enforcing a guaranteed
 * timeout. This is challenging in Java because there is no explicit (reliable)
 * support for this. Instead, we have to spin up a separate JVM into which the
 * method is actually executed.
 *
 * @author David J. Pearce
 *
 */
public class ProcessTimerMethod {
	/**
	 * Identifies where the Java executable is (this is needed in order to start a
	 * fresh JVM).
	 */
	public static String JAVA_CMD = System.getProperty("java.home") + "/bin/java"; //$NON-NLS-1$ //$NON-NLS-2$
	/**
	 * CLASSPATH to use for JVM being created.
	 */
	public static @Nullable String CLASSPATH = System.getProperty("java.class.path"); //$NON-NLS-1$

	/**
	 * Execute a given method with zero or more arguments whilst ensuring a timeout.
	 *
	 * @param timeout  Timeout (in ms).
	 * @param receiver Identifies enclosing class.
	 * @param method   Identifies method name
	 * @param args     Arguments to supply to method.
	 * @return Outcome of executing the method.
	 * @throws Throwable If something goes wrong.
	 */
	public static Outcome exec(long timeout, String receiver, String method, Object... args) throws Throwable {
		// ===================================================
		// Construct command
		// ===================================================
		ArrayList<@Nullable String> command = new ArrayList<>();
		command.add(JAVA_CMD);
		command.add("-ea"); // enable assertions by default //$NON-NLS-1$
		command.add("-cp"); //$NON-NLS-1$
		command.add(CLASSPATH);
		command.add("tinyboycov.util.ProcessTimerMethod"); //$NON-NLS-1$

		// ===================================================
		// Construct the process
		// ===================================================
		ProcessBuilder builder = new ProcessBuilder(command);
		builder.redirectError(Redirect.INHERIT);
		builder.redirectOutput(Redirect.INHERIT);
		Process child = builder.start();
		try {
			// first, send over the method in question + args
			OutputStream output = child.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(output);
			oos.writeObject(receiver);
			oos.writeObject(method);
			oos.writeObject(args);
			oos.flush();
			output.flush();
			// second, read the result whilst checking for a timeout
			try (InputStream input = child.getInputStream(); InputStream error = child.getErrorStream()) {
				assert input != null;
				assert error != null;
				boolean success = child.waitFor(timeout, TimeUnit.MILLISECONDS);
				byte[] stdout = readInputStream(input);
				byte[] stderr = readInputStream(error);
				oos.close();
				output.close();
				error.close();
				return new Outcome(success ? Integer.valueOf(child.exitValue()) : null, stdout, stderr);
			}
		} finally {
			// make sure child process is destroyed.
			child.destroy();
		}
	}

	/**
	 * Read all available data from an input stream.
	 *
	 * @param input Stream from which to read.
	 * @return byte array containing data read.
	 * @throws IOException If something goes wrong.
	 */
	private static byte[] readInputStream(InputStream input) throws IOException {
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		while (input.available() > 0) {
			int count = input.read(buffer);
			output.write(buffer, 0, count);
		}
		byte[] out = output.toByteArray();
		assert out != null;
		return out;
	}

	/**
	 * Called when the new JVM is invoked.
	 *
	 * @param args Supplied arguments.
	 * @throws IOException If something goes wrong.
	 */
	public static void main(String[] args) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(System.in);
		int exitCode=0;
		try {
			String receiver = (String) ois.readObject();
			String name = (String) ois.readObject();
			Object[] arguments = (Object[]) ois.readObject();
			// now, find the object
			Class<?>[] paramtypes = new Class[arguments.length];
			int i = 0;
			for (Object arg : arguments) {
				paramtypes[i++] = arg.getClass();
			}
			Class<?> clazz = Class.forName(receiver);
			Object instance = clazz.getDeclaredConstructor().newInstance();
			Method method = clazz.getMethod(name, paramtypes);
			method.invoke(instance, arguments);
		} catch(InvocationTargetException e) {
			e.getCause().printStackTrace();
			exitCode=-1;
		} catch (Exception e) {
			e.printStackTrace();
			exitCode=-2;
		}
		// Finally write the exit code
		System.exit(exitCode);

	}


	/**
	 * Represents an outcome from executing a given method. This helps us determine
	 * what happened.
	 *
	 * @author David J. Pearce
	 *
	 */
	public static class Outcome {
		/**
		 * Exit code (where 0 = success).
		 */
		private final @Nullable Integer exitCode;
		/**
		 * Standard output produced by process.
		 */
		private final byte[] stdout;
		/**
		 * Standard err produced by process.
		 */
		private final byte[] stderr;

		/**
		 * Create a new outcome.
		 *
		 * @param exitCode Exit code (where 0 = success).
		 * @param stdout   Standard output produced.
		 * @param stderr   Standard error produced.
		 */
		public Outcome(@Nullable Integer exitCode, byte[] stdout, byte[] stderr) {
			this.exitCode = exitCode;
			this.stdout = stdout;
			this.stderr = stderr;
		}

		/**
		 * Get exit code produced by process.
		 *
		 * @return exit code, or <code>null</code> if a timeout.
		 */
		public @Nullable Integer exitCode() {
			return this.exitCode;
		}

		/**
		 * Get standard output produced by process.
		 * @return Output in bytes.
		 */
		public byte[] getStdout() {
			return this.stdout;
		}

		/**
		 * Get standard error produced by process.
		 * @return Err in bytes.
		 */
		public byte[] getStderr() {
			return this.stderr;
		}
	}
}
