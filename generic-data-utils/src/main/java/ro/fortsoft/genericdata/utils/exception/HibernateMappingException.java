package ro.fortsoft.genericdata.utils.exception;

public class HibernateMappingException extends RuntimeException {

	private static final long serialVersionUID = 7661633102446392164L;

	public HibernateMappingException(final Throwable cause) {
		super(cause);
	}

	public HibernateMappingException(final String errorMsg) {
		super(errorMsg);
	}
}
