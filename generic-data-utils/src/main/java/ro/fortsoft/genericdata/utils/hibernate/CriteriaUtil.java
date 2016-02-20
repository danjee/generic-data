package ro.sok.niinoo.utils.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public final class CriteriaUtil {

	private CriteriaUtil() {
	}

	public static Criterion buildPersistentRestriction() {
		final Criterion deleted = Restrictions.or(Restrictions.isNull("deleted"),
				Restrictions.eq("deleted", Boolean.FALSE));
		return deleted;
	}

}
