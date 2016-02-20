package ro.fortsoft.genericdata.dao;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ro.fortsoft.genericdata.utils.exception.HibernateMappingException;
import ro.fortsoft.genericdata.utils.exception.AppException;
import ro.fortsoft.genericdata.utils.exception.RestrictionsUnknownException;
import ro.fortsoft.genericdata.utils.hibernate.CriteriaUtil;
import ro.fortsoft.genericdata.utils.hibernate.HibernateDAOSupport;
import ro.fortsoft.genericdata.utils.hibernate.Initializable;
import ro.fortsoft.genericdata.utils.hibernate.Persistent;
import ro.fortsoft.genericdata.utils.hibernate.ReflectionUtil;
import ro.fortsoft.genericdata.utils.query.BetweenComparator;
import ro.fortsoft.genericdata.utils.query.Pair;
import ro.fortsoft.genericdata.utils.query.QueryParameter;
import ro.fortsoft.genericdata.utils.query.Sorter;
import ro.fortsoft.genericdata.utils.query.ValueRestriction;

@Repository
public class GenericDAOImpl extends HibernateDAOSupport implements GenericDAO{
	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(final Class<T> clazz, final Serializable id) throws AppException {
		checkHibernateMapping(clazz);
		final Criteria criteria = getSession().createCriteria(clazz);
		if (Persistent.class.isAssignableFrom(clazz)) {
			criteria.add(CriteriaUtil.buildPersistentRestriction());
		}
		criteria.add(Restrictions.eq("id", id));
		final T result = (T) criteria.uniqueResult();
		afterGet(result, null);
		return result;
	}

	@Override
	public <T> T getUnique(final Class<T> clazz) throws AppException {
		return getUnique(clazz, null, null);
	}

	@Override
	public <T> T getUnique(final Class<T> clazz, final QueryParameter qp) throws AppException {
		return getUnique(clazz, qp, null);
	}

	@Override
	public <T> T getUnique(final Class<T> clazz, final T filter) throws AppException {
		return getUnique(clazz, null, filter);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getUnique(final Class<T> clazz, final QueryParameter qp, final T filter) throws AppException {
		final T o = (T) getCriteria(clazz, qp, filter, false).uniqueResult();
		afterGet(o, qp);
		return o;
	}

	@Override
	public <T> T getUniqueCached(final Class<T> clazz) {
		return getUniqueCached(clazz, null, null);
	}

	@Override
	public <T> T getUniqueCached(final Class<T> clazz, final QueryParameter qp) {
		return getUniqueCached(clazz, qp, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getUniqueCached(final Class<T> clazz, final T filter) {
		final Criteria criteria = getCriteria(clazz, null, filter, false).setCacheable(true);
		return (T) criteria.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getUniqueCached(final Class<T> clazz, final QueryParameter qp, final T filter) {
		final Criteria criteria = getCriteria(clazz, qp, filter, false).setCacheable(true);
		return (T) criteria.uniqueResult();

	}

	@Override
	public <T> void saveOrUpdate(final T obj) {
		checkHibernateMapping(obj.getClass());
		getSession().saveOrUpdate(obj);
	}

	@Override
	public <T> T add(final T obj) {
		checkHibernateMapping(obj.getClass());
		getSession().save(obj);
		return obj;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> addList(final List<T> list) {
		Class<T> clazz = null;
		if (!list.isEmpty()) {
			clazz = (Class<T>) list.get(0).getClass();
		} else {
			return null;
		}
		checkHibernateMapping(clazz);
		for (final T obj : list) {
			getSession().save(obj);
		}
		return list;
	}

	@Override
	public <T> void update(final T obj) {
		checkHibernateMapping(obj.getClass());
		getSession().update(obj);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> void updateList(final List<T> list) {
		Class<T> clazz = null;
		if (!list.isEmpty()) {
			clazz = (Class<T>) list.get(0).getClass();
		} else {
			return;
		}
		checkHibernateMapping(clazz);
		for (T obj : list){
			getSession().update(obj);
		}
	}

	@Override
	public <T> void delete(final T obj) {
		checkHibernateMapping(obj.getClass());
		if (obj instanceof Persistent) {
			((Persistent) obj).setDeleted(Boolean.TRUE);
			getSession().update(obj);
		} else {
			getSession().delete(getSession().merge(obj));
		}
	}

	@Override
	public <T> void delete(final Class<T> objClass, final Serializable id) {
		if (id == null) {
			return;
		}
		final T obj = (T) getSession().get(objClass, id);
		delete(obj);
	}

	@Override
	public <T> void deleteBulk(final Class<T> clazz) throws AppException {
		deleteBulk(clazz, null, null);
	}

	@Override
	public <T> void deleteBulk(final Class<T> clazz, final QueryParameter qp) throws AppException {
		deleteBulk(clazz, qp, null);
	}

	@Override
	public <T> void deleteBulk(final Class<T> clazz, final T filter) throws AppException {
		deleteBulk(clazz, null, filter);
	}

	@Override
	public <T> void deleteBulk(final Class<T> clazz, final QueryParameter qp, final T filter)
			throws AppException {
		checkHibernateMapping(clazz);
		for (final T obj : getList(clazz, qp, filter)) {
			delete(obj);
		}
	}

	@Override
	public <T> List<T> getList(final Class<T> clazz) throws AppException {
		return getList(clazz, null, null);
	}

	@Override
	public <T> Long getCount(final Class<T> clazz) {
		return getCount(clazz, null, null);
	}

	@Override
	public <T> List<T> getList(final Class<T> clazz, final QueryParameter qp) throws AppException {
		return getList(clazz, qp, null);
	}

	@Override
	public <T> Long getCount(final Class<T> clazz, final QueryParameter qp) {
		return getCount(clazz, qp, null);
	}

	@Override
	public <T> List<T> getList(final Class<T> clazz, final T filter) throws AppException {
		return getList(clazz, null, filter);
	}

	@Override
	public <T> Long getCount(final Class<T> clazz, final T filter) {
		return getCount(clazz, null, filter);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(final Class<T> clazz, final QueryParameter qp, final T filter)
			throws AppException {
		final Criteria criteria = getCriteria(clazz, qp, filter, false);
		if (qp != null) {
			if (qp.isDistinctRootEntity()) {
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			}
			if (qp.getLazyFieldsToInitialize() != null && !qp.getLazyFieldsToInitialize().isEmpty()) {
				final List<T> retval = new ArrayList<T>(criteria.list());
				for (final T o : retval) {
					afterGet(o, qp);
				}
				return retval;
			}else if (Initializable.class.isAssignableFrom(clazz)){
				final List<T> retval = new ArrayList<T>(criteria.list());
				for (final T o : retval) {
					afterGet(o, qp);
				}
				return retval;
			}
		}
		return criteria.list();
	}

	@Override
	public <T> Long getCount(final Class<T> clazz, final QueryParameter qp, final T filter) {
		return ((Number)getCriteria(clazz, qp, filter, true).uniqueResult()).longValue();
	}

	@Override
	public <T> List<T> getListCached(final Class<T> clazz) {
		return getListCached(clazz, null, null);
	}

	@Override
	public <T> Long getCountCached(final Class<T> clazz) {
		return getCountCached(clazz, null, null);
	}

	@Override
	public <T> List<T> getListCached(final Class<T> clazz, final QueryParameter qp) {
		return getListCached(clazz, qp, null);
	}

	@Override
	public <T> Long getCountCached(final Class<T> clazz, final QueryParameter qp) {
		return getCountCached(clazz, qp, null);
	}

	@Override
	public <T> List<T> getListCached(final Class<T> clazz, final T filter) {
		return getListCached(clazz, null, filter);
	}

	@Override
	public <T> Long getCountCached(final Class<T> clazz, final T filter) {
		return getCountCached(clazz, null, filter);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> getListCached(final Class<T> clazz, final QueryParameter qp, final T filter) {
		final Criteria criteria = getCriteria(clazz, qp, filter, false).setCacheable(true);
		return criteria.list();
	}

	@Override
	public <T> Long getCountCached(final Class<T> clazz, final QueryParameter qp, final T filter) {
		final Criteria criteria = getCriteria(clazz, qp, filter, true).setCacheable(true);
		return ((Number) criteria.uniqueResult()).longValue();
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName) {
		return getLiteListBase(clazz, idPropName, displayPropName, null, null, false);
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp) {
		return getLiteListBase(clazz, idPropName, displayPropName, qp, null, false);
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp, final T filter) {
		return getLiteListBase(clazz, idPropName, displayPropName, qp, filter, false);
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName) {
		return getLiteListBase(clazz, idPropName, displayPropName, null, null, true);
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp) {
		return getLiteListBase(clazz, idPropName, displayPropName, qp, null, true);
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp, final T filter) {
		return getLiteListBase(clazz, idPropName, displayPropName, qp, filter, true);
	}

	@SuppressWarnings("unchecked")
	private <T> List<Pair<Long, String>> getLiteListBase(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp, final T filter, final boolean cacheable) {
		final List<Pair<Long, String>> retval =  new ArrayList<Pair<Long, String>>();
		final Criteria criteria = getLiteCriteria(clazz, idPropName, displayPropName, qp, filter)
				.setCacheable(cacheable);
		retval.addAll(criteria.list());
		return retval;
	}

	private <T> Criteria getLiteCriteria(final Class<T> clazz, final String idPropName, final String displayPropName,
			final QueryParameter qp, final T filter) {
		return getCriteria(clazz, qp, filter, false).setProjection(Projections.projectionList()
				.add(Projections.property(idPropName)).add(Projections.property(displayPropName)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, V> List<V> getPropertyValues(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) {
		return getCriteria(clazz, qp, filter, false).setProjection(Projections.property(propName)).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, V> List<V> getPropertyValuesDistinct(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) {
		final ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.distinct(Projections.property(propName)));

		return getCriteria(clazz, qp, filter, false).setProjection(projectionList).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, V> V getPropertyValue(final Class<T> clazz, final Serializable id, final String propName,
			final Class<V> returnClass) {
		final Criteria criteria = getCriteria(clazz, null, null, false);
		return (V) criteria.add(Restrictions.idEq(id)).setProjection(Projections.property(propName)).uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, V> List<V> getPropertyValuesCached(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) {
		final Criteria criteria = getCriteria(clazz, qp, filter, false).setProjection(Projections.property(propName))
				.setCacheable(true);
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, V> List<V> getPropertyValuesCachedDistinct(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) {
		final ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.distinct(Projections.property(propName)));
		final Criteria criteria = getCriteria(clazz, qp, filter, false).setProjection(projectionList)
				.setCacheable(true);
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T, V> V getPropertyValueCached(final Class<T> clazz, final Serializable id, final String propName,
			final Class<V> returnClass) {
		final Criteria criteria = getCriteria(clazz, null, null, false).add(Restrictions.idEq(id))
				.setProjection(Projections.property(propName)).setCacheable(true);
		return (V) criteria.uniqueResult();
	}

	private <T> List<Criterion> getCriteriaRestrictions(final Class<T> clazz, final QueryParameter qp, final T filter) {
		final List<Criterion> list = new ArrayList<Criterion>();
		if (Persistent.class.isAssignableFrom(clazz) && (qp == null || qp.isWithPersistentRestrictions())) {
			list.add(CriteriaUtil.buildPersistentRestriction());
		}
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
				list.add(Example.create(filter));
			}
		}
		if (qp != null) {
			for (final ValueRestriction restriction : qp.getRestrictions()) {
				Criterion criterion = getRestrictionCriterion(restriction);
				if (restriction.getIsNotRestriction() != null && restriction.getIsNotRestriction()) {
					criterion = Restrictions.not(criterion);
				}
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
		checkHibernateMapping(clazz);

		final Criteria criteria = getSession().createCriteria(clazz);

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
		if (restriction == null || restriction.getComparator() == null) {
			throw new RestrictionsUnknownException("Restriction not defined");
		}
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
				return null;
				// throw new IllegalArgumentException(
				// "Using BetweenComparator without left/right objects. ");
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
		} else if (QueryParameter.OR_RESTRICTIONS.equals(restriction.getComparator())
				&& restriction.getOrRestrictions() != null) {
			final List<Criterion> orCriterias = new ArrayList<Criterion>();
			for (final ValueRestriction vr : restriction.getOrRestrictions()) {
				if (vr.getIsNotRestriction() != null && vr.getIsNotRestriction()) {
					orCriterias.add(Restrictions.not(getRestrictionCriterion(vr)));
				} else {
					orCriterias.add(getRestrictionCriterion(vr));
				}
			}
			return Restrictions.or(orCriterias.toArray(new Criterion[orCriterias.size()]));
		}
		throw new RestrictionsUnknownException("Restriction not defined");
	}

	@Override
	public void flushSession() {
		final Session sess = getSession();
		if (sess.isDirty()) {
			sess.flush();
		}
	}

	@Override
	public void clearSession() {
		getSession().clear();
	}

	@Override
	public <T> void evict(final T obj) {
		getSession().evict(obj);
	}

	@Override
	public <T> void evictWithRefresh(final T obj) {
		final Session sess = getSession();
		sess.evict(obj);
		sess.refresh(obj);
	}

	@Override
	public <T> void refresh(final T obj) {
		final Session sess = getSession();
		sess.refresh(obj);
	}

	@Override
	public <T> T merge(final T obj) {
		final Session sess = getSession();
		return (T) sess.merge(obj);
	}

	@Override
	public Session getSession() {
		return getSessionFactory().getCurrentSession();
	}

	private <T> void checkHibernateMapping(final Class<T> clazz) {
		if (getSessionFactory().getClassMetadata(clazz) == null) {
			throw new HibernateMappingException("Hibernate mapping fault. Check to see if  " + clazz.getName()
			+ " was mapped in the persistence context");
		}
	}

	// FEATURE improve on this to get the cached one also
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> void afterGet(final T o, final QueryParameter qp) throws AppException {
		if (o == null){
			return;
		}
		final Class clazz = o.getClass();
		if (Initializable.class.isAssignableFrom(clazz)) {
			try {
				final Method met = clazz.getMethod("initialize", null);
				met.invoke(o, null);
			} catch (final Exception e) {
				throw new AppException(e);
			}
		}
		if (qp != null && !qp.getLazyFieldsToInitialize().isEmpty()) {
			final List<String> listInit = qp.getLazyFieldsToInitialize();
			for (final String propToInit : listInit) {
				try {
					if (ReflectionUtil.getLazyField(clazz, propToInit) != null) {
						Hibernate.initialize(ReflectionUtil.getObjectProperty(o, propToInit));
					}
				} catch (final Exception e) {
					continue;
				}
			}
		}
	}
}
