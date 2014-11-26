package ro.fortsoft.generic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author daniel.jipa May 3, 2012, 11:39:05 AM
 */
@MappedSuperclass
public abstract class DefaultEntity extends DefaultPersistent implements Entity {

	private static final long serialVersionUID = 8384174412873639825L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "DefaultEntity [name=" + name + ", description=" + description
				+ "]";
	}
}
