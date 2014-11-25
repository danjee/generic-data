package ro.fortsoft.generic.dao;

import java.io.Serializable;
import java.util.Collection;

import ro.fortsoft.generic.param.QueryParameter;

public interface GenericDao {

	<T> T get(Class<T> clazz, Serializable id);

	<T> T getUnique(Class<T> clazz);

	<T> T getUnique(Class<T> clazz, QueryParameter qp);

	<T> T getUnique(Class<T> clazz, T filter);

	<T> T getUnique(Class<T> clazz, QueryParameter qp, T filter);

	<T> Collection<T> getList(Class<T> clazz);

	<T> long getCount(Class<T> clazz);

	<T> Collection<T> getList(Class<T> clazz, QueryParameter qp);

	<T> long getCount(Class<T> clazz, QueryParameter qp);

	<T> Collection<T> getList(Class<T> clazz, T filter);

	<T> long getCount(Class<T> clazz, T filter);

	<T> Collection<T> getList(Class<T> clazz, QueryParameter qp, T filter);

	<T> long getCount(Class<T> clazz, QueryParameter qp, T filter);

	<T> T save(T object);

	<T> void update(T object);

	<T> void delete(T object);

	<T> void delete(Class<T> clazz, Serializable id);

	void flushSession();

	<T> void evict(T obj);
}
