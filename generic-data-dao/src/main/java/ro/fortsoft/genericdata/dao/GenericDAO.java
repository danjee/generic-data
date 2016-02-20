package ro.fortsoft.genericdata.dao;

import java.io.Serializable;
import java.util.List;

import ro.fortsoft.genericdata.utils.exception.AppException;
import ro.fortsoft.genericdata.utils.query.Pair;
import ro.fortsoft.genericdata.utils.query.QueryParameter;

public interface GenericDAO {

	<T> void saveOrUpdate(T obj) throws AppException;

	<T> T get(Class<T> objClass, Serializable id) throws AppException;

	<T> T add(T obj) throws AppException;

	<T> List<T> addList(List<T> list) throws AppException;

	<T> void update(T obj) throws AppException;

	<T> void updateList(List<T> list) throws AppException;

	<T> void delete(T obj) throws AppException;

	<T> void delete(Class<T> clazz, Serializable id) throws AppException;

	<T> void deleteBulk(Class<T> clazz) throws AppException;

	<T> void deleteBulk(Class<T> clazz, QueryParameter qp) throws AppException;

	<T> void deleteBulk(Class<T> clazz, T filter) throws AppException;

	<T> void deleteBulk(Class<T> clazz, QueryParameter qp, T filter) throws AppException;

	<T> T getUnique(Class<T> clazz) throws AppException;

	<T> T getUnique(Class<T> clazz, QueryParameter qp) throws AppException;

	<T> T getUnique(Class<T> clazz, T filter) throws AppException;

	<T> T getUnique(Class<T> clazz, QueryParameter qp, T filter) throws AppException;

	<T> T getUniqueCached(Class<T> clazz) throws AppException;

	<T> T getUniqueCached(Class<T> clazz, QueryParameter qp) throws AppException;

	<T> T getUniqueCached(Class<T> clazz, T filter) throws AppException;

	<T> T getUniqueCached(Class<T> clazz, QueryParameter qp, T filter) throws AppException;

	<T> List<T> getList(Class<T> clazz) throws AppException;

	<T> Long getCount(Class<T> clazz) throws AppException;

	<T> List<T> getList(Class<T> clazz, QueryParameter qp) throws AppException;

	<T> Long getCount(Class<T> clazz, QueryParameter qp) throws AppException;

	<T> List<T> getList(Class<T> clazz, T filter) throws AppException;

	<T> Long getCount(Class<T> clazz, T filter) throws AppException;

	<T> List<T> getList(Class<T> clazz, QueryParameter qp, T filter) throws AppException;

	<T> Long getCount(Class<T> clazz, QueryParameter qp, T filter) throws AppException;

	<T> List<T> getListCached(Class<T> clazz) throws AppException;

	<T> Long getCountCached(Class<T> clazz) throws AppException;

	<T> List<T> getListCached(Class<T> clazz, QueryParameter qp) throws AppException;

	<T> Long getCountCached(Class<T> clazz, QueryParameter qp) throws AppException;

	<T> List<T> getListCached(Class<T> clazz, T filter) throws AppException;

	<T> Long getCountCached(Class<T> clazz, T filter) throws AppException;

	<T> List<T> getListCached(Class<T> clazz, QueryParameter qp, T filter) throws AppException;

	<T> Long getCountCached(Class<T> clazz, QueryParameter qp, T filter) throws AppException;

	<T> List<Pair<Long, String>> getLiteList(Class<T> clazz, String idPropName, String displayPropName)
			throws AppException;

	<T> List<Pair<Long, String>> getLiteList(Class<T> clazz, String idPropName, String displayPropName,
			QueryParameter qp) throws AppException;

	<T> List<Pair<Long, String>> getLiteList(Class<T> clazz, String idPropName, String displayPropName,
			QueryParameter qp, T filter) throws AppException;

	<T> List<Pair<Long, String>> getLiteListCached(Class<T> clazz, String idPropName, String displayPropName)
			throws AppException;

	<T> List<Pair<Long, String>> getLiteListCached(Class<T> clazz, String idPropName, String displayPropName,
			QueryParameter qp) throws AppException;

	<T> List<Pair<Long, String>> getLiteListCached(Class<T> clazz, String idPropName, String displayPropName,
			QueryParameter qp, T filter) throws AppException;

	<T, V> List<V> getPropertyValues(Class<T> clazz, QueryParameter qp, T filter, String propName, Class<V> returnClass)
			throws AppException;

	<T, V> List<V> getPropertyValuesDistinct(Class<T> clazz, QueryParameter qp, T filter, String propName,
			Class<V> returnClass) throws AppException;

	<T, V> V getPropertyValue(Class<T> clazz, Serializable id, String propName, Class<V> returnClass)
			throws AppException;

	<T, V> List<V> getPropertyValuesCached(Class<T> clazz, QueryParameter qp, T filter, String propName,
			Class<V> returnClass) throws AppException;

	<T, V> List<V> getPropertyValuesCachedDistinct(Class<T> clazz, QueryParameter qp, T filter, String propName,
			Class<V> returnClass) throws AppException;

	<T, V> V getPropertyValueCached(Class<T> clazz, Serializable id, String propName, Class<V> returnClass)
			throws AppException;

	void flushSession();

	<T> void evict(T obj);

	<T> void evictWithRefresh(T obj);

	<T> void refresh(T obj);

	void clearSession();

	<T> T merge(T obj);
	
}
