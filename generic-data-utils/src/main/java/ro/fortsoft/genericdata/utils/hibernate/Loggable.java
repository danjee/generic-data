package ro.fortsoft.genericdata.utils.hibernate;

import java.util.Date;

/**
 * @author daniel.jipa May 3, 2012, 11:28:15 AM
 */
public interface Loggable {

	public Date getCreationDate();

	public void setCreationDate(Date creationDate);

	public Date getLastUpdateDate();

	public void setLastUpdateDate(Date lastUpdateDate);

	public String getCreatedBy();

	public void setCreatedBy(String createdBy);

	public String getLastUpdatedBy();

	public void setLastUpdatedBy(String lastUpdatedBy);
}
