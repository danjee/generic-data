package ro.fortsoft.generic.model;

import java.util.Date;

/**
 * @author daniel.jipa May 3, 2012, 11:28:15 AM
 */
public interface Loggable {

	Date getCreationDate();

	void setCreationDate(Date creationDate);

	Date getLastUpdateDate();

	void setLastUpdateDate(Date lastUpdateDate);

	User getCreatedBy();

	void setCreatedBy(User createdBy);

	User getLastUpdatedBy();

	void setLastUpdatedBy(User lastUpdatedBy);
}
