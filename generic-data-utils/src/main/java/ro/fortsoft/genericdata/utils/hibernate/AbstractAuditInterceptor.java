package ro.fortsoft.genericdata.utils.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.EmptyInterceptor;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;

/**
 * @author daniel.jipa Nov 22, 2012, 1:15:42 PM
 */
public abstract class AbstractAuditInterceptor extends EmptyInterceptor implements Interceptor {

	private static final long serialVersionUID = 2914362528125673753L;

	private int saveOperations = 0;
	private int updateOperations = 0;

	private static final String CREATED_BY = "createdBy";
	private static final String CREATION_DATE = "creationDate";
	private static final String LAST_UPDATED_BY = "lastUpdatedBy";
	private static final String LAST_UPDATE_DATE = "lastUpdateDate";
	private static final String DELETED = "deleted";

	// update
	@Override
	public boolean onFlushDirty(final Object entity, final Serializable id, final Object[] state,
			final Object[] previousState, final String[] propertyNames, final Type[] types) {
		boolean modified = false;
		final String username = getUsername();
		if (entity instanceof DefaultLoggable) {
			updateOperations++;
			for (int i = 0; i < propertyNames.length; i++) {
				if (LAST_UPDATED_BY.equals(propertyNames[i])) {
					state[i] = username;
					modified = true;
				}
				if (LAST_UPDATE_DATE.equals(propertyNames[i])) {
					state[i] = new Date();
					modified = true;
				}
			}
		}
		return modified;
	}

	// save
	@Override
	public boolean onSave(final Object entity, final Serializable id, final Object[] state,
			final String[] propertyNames, final Type[] types) {
		boolean created = false;
		if (entity instanceof DefaultLoggable) {
			saveOperations++;
			final String username = getUsername();
			for (int i = 0; i < propertyNames.length; i++) {
				if (CREATED_BY.equals(propertyNames[i])) {
					state[i] = username;
					created = true;
				}
				if (CREATION_DATE.equals(propertyNames[i])) {
					state[i] = new Date();
					created = true;
				}
				if (LAST_UPDATED_BY.equals(propertyNames[i])) {
					state[i] = username;
					created = true;
				}
				if (LAST_UPDATE_DATE.equals(propertyNames[i])) {
					state[i] = new Date();
					created = true;
				}
			}
			if (entity instanceof DefaultPersistent) {
				for (int i = 0; i < propertyNames.length; i++) {
					if (DELETED.equals(propertyNames[i])) {
						state[i] = Boolean.FALSE;
						created = true;
					}
				}
			}
		}
		return created;
	}

	@Override
	public void preFlush(final Iterator entities) {
		saveOperations = 0;
		updateOperations = 0;
	}

	@Override
	public void postFlush(final Iterator entities) {
	}

	public abstract String getUsername();

}
