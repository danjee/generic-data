package ro.sok.niinoo.utils.hibernate;

/**
 * @author daniel.jipa May 3, 2012, 11:38:01 AM
 */
public interface Nomenclator extends Persistent {

	public String getName();

	public void setName(String name);

	public String getDescription();

	public void setDescription(String description);

}
