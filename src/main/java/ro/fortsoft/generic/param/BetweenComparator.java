package ro.fortsoft.generic.param;

import java.io.Serializable;

public class BetweenComparator implements Serializable {

	private static final long serialVersionUID = -3394770178531130475L;

	public BetweenComparator(final Object lhs, final Object rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	private Object lhs;

	private Object rhs;

	public Object getLhs() {
		return lhs;
	}

	public void setLhs(final Object lhs) {
		this.lhs = lhs;
	}

	public Object getRhs() {
		return rhs;
	}

	public void setRhs(final Object rhs) {
		this.rhs = rhs;
	}
}