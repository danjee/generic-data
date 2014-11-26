package ro.fortsoft.generic.dao;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import ro.fortsoft.generic.param.QueryParameter;

public class JpaGenericDao implements GenericDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public <T> T get(Class<T> clazz, Serializable id) {
		return entityManager.find(clazz, id);
	}

	@Override
	public <T> T getUnique(Class<T> clazz) {
		return entityManager.createQuery("from " + clazz.getName(), clazz).getSingleResult();
	}

	@Override
	public <T> T getUnique(Class<T> clazz, QueryParameter qp) {
		return getUnique(clazz, qp, null);
	}

	@Override
	public <T> T getUnique(Class<T> clazz, T filter) {
		return getUnique(clazz, null, filter);
	}

	@Override
	public <T> T getUnique(Class<T> clazz, QueryParameter qp, T filter) {
		// TODO Auto-generated method stub
		return null;
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
		return getList(clazz,null, filter);
	}

	@Override
	public <T> long getCount(Class<T> clazz, T filter) {
		return getCount(clazz, null, filter);
	}

	@Override
	public <T> Collection<T> getList(Class<T> clazz, QueryParameter qp, T filter) {
		final String jpql = createQuery(clazz, qp, filter, false);
		return entityManager.createQuery(jpql, clazz).getResultList();
	}

	@Override
	public <T> long getCount(Class<T> clazz, QueryParameter qp, T filter) {
		final String jpql = createQuery(clazz, qp, filter, true);
		return entityManager.createQuery(jpql, Long.class).getSingleResult();
	}


	private <T> String createQuery(Class<T> clazz, QueryParameter qp, T filter, boolean count){
		final StringBuilder jpql = new StringBuilder();
		jpql.append("select ");
		if (count){
			jpql.append("count(");
		}
		jpql.append("x");
		if (count){
			jpql.append(")");
		}
		jpql.append(" from ");
		jpql.append(clazz.getSimpleName());
		jpql.append(" x ");
		return jpql.toString();
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
