package ro.sok.niinoo.utils.query;

import java.io.Serializable;

public class Sorter implements Serializable {

	private static final long serialVersionUID = -1719113572858884940L;

	private String field;
	private boolean ascending;
	private boolean ignoreCase = true;

	public Sorter(final String field, final boolean ascending) {
		this.field = field;
		this.ascending = ascending;
	}

	public String getField() {
		return field;
	}

	public void setField(final String field) {
		this.field = field;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(final boolean ascending) {
		this.ascending = ascending;
	}

	@Override
	public String toString() {
		return field + ": " + (ascending ? "asc" : "desc")
				+ (ignoreCase ? " ignoreCase" : "");
	}

	/**
	 * ignoreCase is set by default on true
	 * 
	 * @return
	 */
	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * ignoreCase is set by default on true
	 * 
	 * @return
	 */
	public void setIgnoreCase(final boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}
}
