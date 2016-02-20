package ro.fortsoft.genericdata.utils.hibernate;

/**
 * @author daniel.jipa Oct 26, 2012, 11:36:12 PM
 */
public interface Persistent extends Identifiable {

	public Boolean getDeleted();

	public void setDeleted(Boolean deleted);
}
