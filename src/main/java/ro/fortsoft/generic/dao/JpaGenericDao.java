package ro.fortsoft.generic.dao;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import ro.fortsoft.generic.param.QueryParameter;

public class JpaGenericDao implements GenericDao {

	@Autowired
	private EntityManager entityManager;

	public <T> T get(Class<T> clazz, Serializable id) {
		return entityManager.find(clazz, id);
	}

	public <T> T getUnique(Class<T> clazz) {
		return entityManager.createQuery("from " + clazz.getName(), clazz).getSingleResult();
	}

	public <T> T getUnique(Class<T> clazz, QueryParameter qp) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getUnique(Class<T> clazz, T filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T getUnique(Class<T> clazz, QueryParameter qp, T filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> Collection<T> getList(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> long getCount(Class<T> clazz) {
		// TODO Auto-generated method stub
		return 0;
	}

	public <T> Collection<T> getList(Class<T> clazz, QueryParameter qp) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> long getCount(Class<T> clazz, QueryParameter qp) {
		// TODO Auto-generated method stub
		return 0;
	}

	public <T> Collection<T> getList(Class<T> clazz, T filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> long getCount(Class<T> clazz, T filter) {
		// TODO Auto-generated method stub
		return 0;
	}

	public <T> Collection<T> getList(Class<T> clazz, QueryParameter qp, T filter) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> long getCount(Class<T> clazz, QueryParameter qp, T filter) {
		// TODO Auto-generated method stub
		return 0;
	}

	private <T> String createQuery(QueryParameter qp, T filter){
		return null;
	}


	public <T> T save(T object) {
		entityManager.persist(object);
		return object;
	}

	public <T> void update(T object) {
		entityManager.merge(object);
	}

	public <T> void delete(T object) {
		entityManager.remove(object);
	}

	public <T> void delete(Class<T> clazz, Serializable id) {
		final T object = entityManager.find(clazz, id);
		entityManager.remove(object);
	}

	public void flushSession() {
		entityManager.flush();
	}

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
