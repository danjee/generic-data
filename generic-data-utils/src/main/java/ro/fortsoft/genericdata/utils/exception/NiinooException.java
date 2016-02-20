package ro.fortsoft.genericdata.utils.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Sandu Pana
 *
 */
public class NiinooException extends Exception {

	private static final long serialVersionUID = 5362385187151262405L;

	private String text;

	private Throwable rootCause;
	private boolean wrapper;

	public NiinooException() {
		setText();
	}

	public NiinooException(final String str) {
		super(str);
		text = str;
	}

	public NiinooException(final Throwable ex) {
		super(ex.getMessage());
		text = ex.getMessage();
		final StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
	}

	public NiinooException(final Exception ex) {
		super(ex.getMessage());
		text = ex.getMessage();
		final StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		final String s = sw.toString();
	}

	public NiinooException(final Throwable rootCause, final boolean wrapper) {
		this.rootCause = rootCause;
		this.wrapper = wrapper;
	}

	public Throwable getRootCause() {
		return rootCause;
	}

	public void setRootCause(final Throwable rootCause) {
		this.rootCause = rootCause;
	}

	public boolean isWrapper() {
		return wrapper;
	}

	public void setWrapper(final boolean wrapper) {
		this.wrapper = wrapper;
	}

	public String getText() {
		return text;
	}

	@Override
	public String getMessage() {
		return text;
	}

	private void setText() {
		final StringWriter sw = new StringWriter();
		new Exception().printStackTrace(new PrintWriter(sw));
		final String s = sw.toString();
		int index = s.indexOf("at ");
		index = s.indexOf("at ", index + 10);
		index = s.indexOf("at ", index + 10);
		final String line = s.substring(index + 3, s.indexOf("(", index));
		final String cls = line.substring(0, line.lastIndexOf("."));
		final String meth = line.substring(line.lastIndexOf(".") + 1, line.length());
		text = cls.hashCode() + "|" + meth.hashCode();
	}
}
