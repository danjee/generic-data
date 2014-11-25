package ro.fortsoft.generic.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import ro.fortsoft.generic.param.BetweenComparator;
import ro.fortsoft.generic.param.QueryParameter;
import ro.fortsoft.generic.param.Sorter;
import ro.fortsoft.generic.param.ValueRestriction;

public class HibernateGenericDao implements GenericDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz, Serializable id) {
		final Criteria criteria = getCriteria(clazz, null, null, false);
		criteria.add(Restrictions.eq("id", id));
		return (T) criteria.uniqueResult();
	}

	public <T> T getUnique(Class<T> clazz) {
		return getUnique(clazz, null, null);
	}

	public <T> T getUnique(Class<T> clazz, QueryParameter qp) {
		return getUnique(clazz, qp, null);
	}

	public <T> T getUnique(Class<T> clazz, T filter) {
		return getUnique(clazz, null, filter);
	}

	public <T> T getUnique(Class<T> clazz, QueryParameter qp, T filter) {
		final T o = (T) getCriteria(clazz, qp, filter, false).uniqueResult();
		return o;
	}

	public <T> Collection<T> getList(Class<T> clazz) {
		return getList(clazz, null, null);
	}

	public <T> long getCount(Class<T> clazz) {
		return getCount(clazz, null, null);
	}

	public <T> Collection<T> getList(Class<T> clazz, QueryParameter qp) {
		return getList(clazz, qp, null);
	}

	public <T> long getCount(Class<T> clazz, QueryParameter qp) {
		return getCount(clazz, qp, null);
	}

	public <T> Collection<T> getList(Class<T> clazz, T filter) {
		return getList(clazz, null, filter);
	}

	public <T> long getCount(Class<T> clazz, T filter) {
		return getCount(clazz, null, filter);
	}

	public <T> Collection<T> getList(Class<T> clazz, QueryParameter qp, T filter) {
		return getCriteria(clazz, qp, filter, false).list();
	}

	public <T> long getCount(Class<T> clazz, QueryParameter qp, T filter) {
		return ((Number) getCriteria(clazz, qp, filter, true).uniqueResult()).longValue();
	}

	public <T> T save(T object) {
		final Serializable id = sessionFactory.getCurrentSession().save(object);
		return (T) sessionFactory.getCurrentSession().get(object.getClass(), id);
	}

	public <T> void update(T object) {
		sessionFactory.getCurrentSession().update(object);
	}

	public <T> void delete(T object) {
		sessionFactory.getCurrentSession().delete(object);
	}

	public <T> void delete(Class<T> clazz, Serializable id) {
		final Session session = sessionFactory.getCurrentSession();
		sessionFactory.getCurrentSession().delete(session.get(clazz, id));
	}

	public void flushSession() {
		sessionFactory.getCurrentSession().flush();
	}

	public <T> void evict(T obj) {
		sessionFactory.getCurrentSession().evict(obj);
	}

	private <T> List<Criterion> getCriteriaRestrictions(final Class<T> clazz, final QueryParameter qp, final T filter) {
		final List<Criterion> list = new ArrayList<Criterion>();

		if (filter != null) {
			if (qp != null) {
				Example example = Example.create(filter);
				if (qp.getFilterState() == QueryParameter.FILTER_STATE_IGNORECASE_ENABLELIKE) {
					list.add(example.enableLike(getMatchMode(qp)).ignoreCase());
				} else if (qp.getFilterState() == QueryParameter.FILTER_STATE_IGNORECASE_NOENABLELIKE) {
					list.add(example.ignoreCase());
				} else if (qp.getFilterState() == QueryParameter.FILTER_STATE_NOIGNORECASE_ENABLELIKE) {
					list.add(example.enableLike(getMatchMode(qp)));
				} else {
					list.add(example);
				}
				if ((qp.getQueryType() & QueryParameter.QUERY_TYPE_WITHESCAPECHARACTER) != 0) {
					example.setEscapeCharacter(QueryParameter.ESCAPE_CHARACTER);
				}
				if ((qp.getQueryType() & QueryParameter.QUERY_TYPE_EXACT) != 0) {
					example = example.excludeNone();
					for (final String property : qp.getExcludedProperties()) {
						example = example.excludeProperty(property);
					}
				}
			} else {
				list.add(Example.create(filter).enableLike(MatchMode.EXACT).ignoreCase());
			}
		}
		if (qp != null) {
			for (final ValueRestriction restriction : qp.getRestrictions()) {
				final Criterion criterion = getRestrictionCriterion(restriction);
				if (criterion != null) {
					list.add(criterion);
				}
			}
		}
		return list;
	}

	private MatchMode getMatchMode(final QueryParameter qp) {
		if (qp != null) {
			switch (qp.getMatchMode()) {
			case QueryParameter.MATCH_MODE_ANYWHERE:
				return MatchMode.ANYWHERE;
			case QueryParameter.MATCH_MODE_START:
				return MatchMode.START;
			case QueryParameter.MATCH_MODE_END:
				return MatchMode.END;
			default:
				return MatchMode.EXACT;
			}
		} else {
			return MatchMode.EXACT;
		}

	}


	private <T> Criteria getCriteria(final Class<T> clazz, final QueryParameter qp, final T filter,
			final boolean isCount) {

		final Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);

		final List<Criterion> lstCriterions = getCriteriaRestrictions(clazz, qp, filter);
		for (final Criterion c : lstCriterions) {
			criteria.add(c);
		}

		if (qp != null) {
			if (!isCount) {
				for (final Sorter sorter : qp.getSorters()) {
					if (sorter.isAscending()) {
						Order order = Order.asc(sorter.getField());
						if (sorter.isIgnoreCase()) {
							order = order.ignoreCase();
						}
						criteria.addOrder(order);
					} else {
						Order order = Order.desc(sorter.getField());
						if (sorter.isIgnoreCase()) {
							order = order.ignoreCase();
						}
						criteria.addOrder(order);
					}
				}
				if (qp.getFirst() != null) {
					criteria.setFirstResult(qp.getFirst().intValue());
				}
				if (qp.getCount() != null) {
					criteria.setMaxResults(qp.getCount().intValue());
				}
			}
			if (!qp.getProjectionProperties().isEmpty()) {
				final ProjectionList projectionList = Projections.projectionList();
				for (final String property : qp.getProjectionProperties()) {
					projectionList.add(Projections.property(property));
				}
				if (qp.isWithDistinctProjection()) {
					criteria.setProjection(Projections.distinct(projectionList));
				} else {
					criteria.setProjection(projectionList);
				}
			}
		}
		if (isCount) {
			if (qp != null && qp.getPropertyCountBy() != null) {
				criteria.setProjection(Projections.count(qp.getPropertyCountBy()));
			} else {
				criteria.setProjection(Projections.rowCount());
			}
		}

		return criteria;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Criterion getRestrictionCriterion(final ValueRestriction restriction) {
		if (restriction.getComparator().equals(QueryParameter.EQ)) {
			return Restrictions.eq(restriction.getField(), restriction.getValue());
		} else if (restriction.getComparator().equals(QueryParameter.NE)) {
			return Restrictions.ne(restriction.getField(), restriction.getValue());
		} else if (restriction.getComparator().equals(QueryParameter.GE)) {
			return Restrictions.ge(restriction.getField(), restriction.getValue());
		} else if (restriction.getComparator().equals(QueryParameter.GT)) {
			return Restrictions.gt(restriction.getField(), restriction.getValue());
		} else if (restriction.getComparator().equals(QueryParameter.LT)) {
			return Restrictions.lt(restriction.getField(), restriction.getValue());
		} else if (restriction.getComparator().equals(QueryParameter.LE)) {
			return Restrictions.le(restriction.getField(), restriction.getValue());
		} else if (restriction.getComparator().equals(QueryParameter.BETWEEN)) {
			final BetweenComparator comparator = (BetweenComparator) restriction.getValue();
			final Object right = comparator.getRhs();
			final Object left = comparator.getLhs();
			if (right != null && left != null) {
				return Restrictions.between(restriction.getField(), comparator.getLhs(), comparator.getRhs());
			} else {
				if (left != null) {
					return Restrictions.ge(restriction.getField(), left);
				}
				if (right != null) {
					return Restrictions.le(restriction.getField(), right);
				}
				throw new IllegalArgumentException(
						"Using BetweenComparator without left/right objects. ");
			}
		} else if (restriction.getComparator().equals(QueryParameter.IN)) {
			List<Serializable> arguments = null;
			if (restriction.getValue() instanceof ArrayList) {
				arguments = (ArrayList) restriction.getValue();
			} else {
				throw new IllegalArgumentException("Argument IN must be of type ArrayList");
			}
			if (arguments.isEmpty()) {
				return Restrictions.sqlRestriction("1=1");
			}
			if (arguments.size() > 1000) {
				Criterion criterionIn = Restrictions.sqlRestriction("1<>1");
				final List<Serializable> inList = new ArrayList<Serializable>();
				for (int i = 0; i < arguments.size(); i++) {
					inList.add(arguments.get(i));
					if (inList.size() == 1000) {
						criterionIn = Restrictions.or(criterionIn, Restrictions.in(restriction.getField(), inList));
						inList.clear();
					}
				}
				if (!inList.isEmpty()) {
					criterionIn = Restrictions.or(criterionIn, Restrictions.in(restriction.getField(), inList));
				}
				return criterionIn;
			} else {
				return Restrictions.in(restriction.getField(), arguments);
			}
		} else if (restriction.getComparator().equals(QueryParameter.NOT_IN)) {
			List<Serializable> arguments = null;
			if (restriction.getValue() instanceof ArrayList) {
				arguments = (ArrayList) restriction.getValue();
			} else {
				throw new IllegalArgumentException("Argument IN must be of type ArrayList");
			}
			if (arguments.size() > 1000) {
				Criterion criterionIn = Restrictions.sqlRestriction("1<>1");
				final List<Serializable> inList = new ArrayList<Serializable>();
				for (int i = 0; i < arguments.size(); i++) {
					inList.add(arguments.get(i));
					if (inList.size() == 1000) {
						criterionIn = Restrictions.or(criterionIn, Restrictions.in(restriction.getField(), inList));
						inList.clear();
					}
				}
				if (!inList.isEmpty()) {
					criterionIn = Restrictions.or(criterionIn, Restrictions.in(restriction.getField(), inList));
				}
				final Criterion criterionNotIn = Restrictions.not(criterionIn);
				return criterionNotIn;
			} else {
				return Restrictions.not(Restrictions.in(restriction.getField(), arguments));
			}
		} else if (restriction.getComparator().equals(QueryParameter.IS_NULL)) {
			return Restrictions.isNull(restriction.getField());
		} else if (restriction.getComparator().equals(QueryParameter.IS_NOT_NULL)) {
			return Restrictions.isNotNull(restriction.getField());
		} else if (restriction.getComparator().equals(QueryParameter.LIKE)) {
			return Restrictions.like(restriction.getField(), restriction.getValue());
		} else if (restriction.getComparator().equals(QueryParameter.LIKE_EXACT)) {
			return Restrictions.like(restriction.getField(), (String) restriction.getValue(), MatchMode.EXACT);
		} else if (restriction.getComparator().equals(QueryParameter.LIKE_START)) {
			return Restrictions.like(restriction.getField(), (String) restriction.getValue(), MatchMode.START);
		} else if (restriction.getComparator().equals(QueryParameter.LIKE_END)) {
			return Restrictions.like(restriction.getField(), (String) restriction.getValue(), MatchMode.END);
		} else if (restriction.getComparator().equals(QueryParameter.LIKE_ANYWHERE)) {
			return Restrictions.like(restriction.getField(), (String) restriction.getValue(), MatchMode.ANYWHERE);
		} else if (restriction.getComparator().equals(QueryParameter.ILIKE)) {
			return Restrictions.ilike(restriction.getField(), restriction.getValue());
		} else if (restriction.getComparator().equals(QueryParameter.ILIKE_EXACT)) {
			return Restrictions.ilike(restriction.getField(), (String) restriction.getValue(), MatchMode.EXACT);
		} else if (restriction.getComparator().equals(QueryParameter.ILIKE_START)) {
			return Restrictions.ilike(restriction.getField(), (String) restriction.getValue(), MatchMode.START);
		} else if (restriction.getComparator().equals(QueryParameter.ILIKE_END)) {
			return Restrictions.ilike(restriction.getField(), (String) restriction.getValue(), MatchMode.END);
		} else if (restriction.getComparator().equals(QueryParameter.ILIKE_ANYWHERE)) {
			return Restrictions.ilike(restriction.getField(), (String) restriction.getValue(), MatchMode.ANYWHERE);
		} else if (restriction.getComparator().equals(QueryParameter.SQL)) {
			return Restrictions.sqlRestriction(restriction.getSql());
		} else if (restriction.getOrRestrictions() != null) {
			final List<Criterion> orCriterias = new ArrayList<Criterion>();
			for (final ValueRestriction vr : restriction.getOrRestrictions()) {
				orCriterias.add(getRestrictionCriterion(vr));
			}
			return Restrictions.or(orCriterias.toArray(new Criterion[orCriterias.size()]));
		}
		throw new IllegalArgumentException("Restriction not defined");
	}

}
