package ro.fortsoft.generic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author daniel.jipa Apr 30, 2012, 6:28:05 PM
 */

@MappedSuperclass
public abstract class DefaultLoggable implements Loggable, Identifiable {

	private static final long serialVersionUID = -6415849527788577288L;

	@ManyToOne
	@JoinColumn(name = "CREATED_BY", nullable = true)
	@Fetch(FetchMode.JOIN)
	private User createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE", nullable = true)
	private Date creationDate;

	@ManyToOne
	@JoinColumn(name = "LAST_UPDATED_BY", nullable = true)
	@Fetch(FetchMode.JOIN)
	private User lastUpdatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_DATE", nullable = true)
	private Date lastUpdateDate;

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	@Override
	public void setLastUpdateDate(final Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public User getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public User getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	@Override
	public void setLastUpdatedBy(User lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setLogging(final User user) {
		createdBy = user;
		creationDate = new Date();
		lastUpdatedBy = user;
		lastUpdateDate = new Date();
	}

	public void updateLogging(final User user) {
		lastUpdatedBy = user;
		lastUpdateDate = new Date();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (getId() == null ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DefaultLoggable other = (DefaultLoggable) obj;
		if (getId() == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}
}