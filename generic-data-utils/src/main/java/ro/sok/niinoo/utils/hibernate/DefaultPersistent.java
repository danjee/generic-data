package ro.sok.niinoo.utils.hibernate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author daniel.jipa Oct 26, 2012, 11:38:32 PM
 */

@MappedSuperclass
public abstract class DefaultPersistent extends DefaultLoggable implements Persistent {

	private static final long serialVersionUID = -6939142680842739719L;

	@Column(name = "DELETED")
	private Boolean deleted;

	@Override
	public Boolean getDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(final Boolean deleted) {
		this.deleted = deleted;
	}

}
