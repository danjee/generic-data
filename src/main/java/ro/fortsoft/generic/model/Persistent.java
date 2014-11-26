package ro.fortsoft.generic.model;

/**
 * @author daniel.jipa Oct 26, 2012, 11:36:12 PM
 */
public interface Persistent extends Identifiable {
	 Boolean getActive();

	 void setActive(Boolean active);

	 Boolean getDeleted();

	 void setDeleted(Boolean deleted);
}
