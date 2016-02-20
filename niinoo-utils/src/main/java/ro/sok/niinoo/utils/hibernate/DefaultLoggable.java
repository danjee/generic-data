package ro.sok.niinoo.utils.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author daniel.jipa Apr 30, 2012, 6:28:05 PM
 */

@MappedSuperclass
public abstract class DefaultLoggable implements Loggable, Identifiable {

	private static final long serialVersionUID = -6415849527788577288L;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_DATE")
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
	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	@Override
	public void setLastUpdatedBy(final String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setLogging(final String user) {
		createdBy = user;
		creationDate = new Date();
		lastUpdatedBy = user;
		lastUpdateDate = new Date();
	}

	public void updateLogging(final String user) {
		lastUpdatedBy = user;
		lastUpdateDate = new Date();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (getId() == null ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
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

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
