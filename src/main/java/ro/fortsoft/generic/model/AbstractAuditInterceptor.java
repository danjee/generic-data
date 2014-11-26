package ro.fortsoft.generic.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

/**
 * @author daniel.jipa Nov 22, 2012, 1:15:42 PM
 */
public abstract class AbstractAuditInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 2914362528125673753L;

	private static final String ACTIVE = "active";
	private static final String DELETED = "deleted";

	private static final String CREATED_BY = "createdBy";
	private static final String CREATION_DATE = "creationDate";
	private static final String LAST_UPDATED_BY = "lastUpdatedBy";
	private static final String LAST_UPDATE_DATE = "lastUpdateDate";

	@Override
	public boolean onFlushDirty(final Object entity, final Serializable id,
			final Object[] state, final Object[] previousState,
			final String[] propertyNames, final Type[] types) {
		boolean modified = false;
		if (entity instanceof DefaultLoggable) {
			for (int i = 0; i < propertyNames.length; i++) {
				if (LAST_UPDATED_BY.equals(propertyNames[i])) {
					state[i] = getLoggedUser();
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

	@Override
	public boolean onSave(final Object entity, final Serializable id,
			final Object[] state, final String[] propertyNames,
			final Type[] types) {
		boolean created = false;
		if (entity instanceof DefaultLoggable) {
			for (int i = 0; i < propertyNames.length; i++) {
				if (ACTIVE.equals(propertyNames[i])) {
					state[i] = Boolean.TRUE;
					created = true;
				}
				if (DELETED.equals(propertyNames[i])) {
					state[i] = Boolean.FALSE;
					created = true;
				}
				if (CREATED_BY.equals(propertyNames[i])) {
					state[i] = getLoggedUser();
					created = true;
				}
				if (CREATION_DATE.equals(propertyNames[i])) {
					state[i] = new Date();
					created = true;
				}
				if (LAST_UPDATED_BY.equals(propertyNames[i])) {
					state[i] = getLoggedUser();
					created = true;
				}
				if (LAST_UPDATE_DATE.equals(propertyNames[i])) {
					state[i] = new Date();
					created = true;
				}
			}
		}
		return created;
	}

	@Override
	public void preFlush(final Iterator entities) {
	}

	@Override
	public void postFlush(final Iterator entities) {
	}

	public abstract User getLoggedUser();

}
