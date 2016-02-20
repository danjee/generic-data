package ro.sok.niinoo.utils.hibernate;

import java.io.Serializable;

/**
 * @author daniel.jipa Oct 13, 2012, 4:48:06 PM
 */
public interface Identifiable extends Serializable {
	public Serializable getId();
}
