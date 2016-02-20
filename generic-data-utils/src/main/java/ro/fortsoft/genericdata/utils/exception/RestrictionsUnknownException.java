package ro.fortsoft.genericdata.utils.exception;

public class RestrictionsUnknownException extends IllegalArgumentException {

	private static final long serialVersionUID = -4333547587244830573L;

	public RestrictionsUnknownException(final Throwable cause) {
		super(cause);
	}

	public RestrictionsUnknownException(final String errorMsg) {
		super(errorMsg);
	}

}
