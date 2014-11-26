package ro.fortsoft.generic.model;

/**
 * @author daniel.jipa May 3, 2012, 11:38:01 AM
 */
public interface Entity extends Persistent {

	String getName();

	void setName(String name);

	String getDescription();

	void setDescription(String description);

}
