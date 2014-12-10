package ro.fortsoft.generic.dao;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ro.fortsoft.generic.param.QueryParameter;
import ro.fortsoft.generic.param.ValueRestriction;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class JpaGenericDao implements GenericDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public <T> T get(Class<T> clazz, Serializable id) {
		return entityManager.find(clazz, id);
	}

	@Override
	public <T> T getUnique(Class<T> clazz) {
		return entityManager.createQuery("from " + clazz.getName(), clazz)
				.getSingleResult();
	}

	@Override
	public <T> T getUnique(Class<T> clazz, QueryParameter qp) {
		return getUnique(clazz, qp, null);
	}

	@Override
	public <T> T getUnique(Class<T> clazz, T filter) {
		return getUnique(clazz, null, filter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getUnique(Class<T> clazz, QueryParameter qp, T filter) {
		final Query query = createQuery(clazz, qp, filter, false);
		return (T) query.getSingleResult();
	}

	@Override
	public <T> Collection<T> getList(Class<T> clazz) {
		return getList(clazz, null, null);
	}

	@Override
	public <T> long getCount(Class<T> clazz) {
		return getCount(clazz, null, null);
	}

	@Override
	public <T> Collection<T> getList(Class<T> clazz, QueryParameter qp) {
		return getList(clazz, qp, null);
	}

	@Override
	public <T> long getCount(Class<T> clazz, QueryParameter qp) {
		return getCount(clazz, qp, null);
	}

	@Override
	public <T> Collection<T> getList(Class<T> clazz, T filter) {
		return getList(clazz, null, filter);
	}

	@Override
	public <T> long getCount(Class<T> clazz, T filter) {
		return getCount(clazz, null, filter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Collection<T> getList(Class<T> clazz, QueryParameter qp, T filter) {
		final Query query = createQuery(clazz, qp, filter, false);
		return query.getResultList();
	}

	@Override
	public <T> long getCount(Class<T> clazz, QueryParameter qp, T filter) {
		final Query query = createQuery(clazz, qp, filter, true);
		return (Long) query.getSingleResult();
	}

	private <T> Query createQuery(Class<T> clazz, QueryParameter qp, T filter,
			boolean count) {
		final StringBuilder jpql = new StringBuilder();
		jpql.append("select ");
		if (count) {
			jpql.append("count(");
		}
		jpql.append("x");
		if (count) {
			jpql.append(")");
		}
		jpql.append(" from ");
		jpql.append(clazz.getSimpleName());
		jpql.append(" x ");
		jpql.append(" where 1 = 1");
		if (qp != null) {
			for (final ValueRestriction restriction : qp.getRestrictions()) {
				jpql.append(" and ").append(restriction.getField())
				.append(getOperator(restriction.getComparator()))
				.append(":").append(restriction.getField());
			}
		}
		final Query query = entityManager.createQuery(jpql.toString(),
				count ? Long.class : clazz);
		if (qp != null) {
			for (final ValueRestriction restriction : qp.getRestrictions()) {
				query.setParameter(restriction.getField(),
						restriction.getValue());
			}
		}
		return query;
	}

	private String getOperator(String comparator) {
		switch (comparator) {
		case QueryParameter.EQ: return "=";
		case QueryParameter.NE: return "!=";
		case QueryParameter.GT: return ">";
		case QueryParameter.LT: return "<";
		case QueryParameter.LE: return "<=";
		case QueryParameter.GE: return ">=";
		}
		return null;
	}

	@Override
	public <T> T save(T object) {
		entityManager.persist(object);
		return object;
	}

	@Override
	public <T> void update(T object) {
		entityManager.merge(object);
	}

	@Override
	public <T> void delete(T object) {
		entityManager.remove(object);
	}

	@Override
	public <T> void delete(Class<T> clazz, Serializable id) {
		final T object = entityManager.find(clazz, id);
		entityManager.remove(object);
	}

	@Override
	public void flushSession() {
		entityManager.flush();
	}

	@Override
	public <T> void evict(T obj) {
		entityManager.detach(obj);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
