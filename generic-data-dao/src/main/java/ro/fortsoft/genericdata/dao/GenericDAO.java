package ro.fortsoft.genericdata.dao;

import java.io.Serializable;
import java.util.List;

import ro.fortsoft.genericdata.utils.exception.NiinooException;
import ro.fortsoft.genericdata.utils.query.Pair;
import ro.fortsoft.genericdata.utils.query.QueryParameter;

public interface GenericDAO {

	<T> void saveOrUpdate(T obj) throws NiinooException;

	<T> T get(Class<T> objClass, Serializable id) throws NiinooException;

	<T> T add(T obj) throws NiinooException;

	<T> List<T> addList(List<T> list) throws NiinooException;

	<T> void update(T obj) throws NiinooException;

	<T> void updateList(List<T> list) throws NiinooException;

	<T> void delete(T obj) throws NiinooException;

	<T> void delete(Class<T> clazz, Serializable id) throws NiinooException;

	<T> void deleteBulk(Class<T> clazz) throws NiinooException;

	<T> void deleteBulk(Class<T> clazz, QueryParameter qp) throws NiinooException;

	<T> void deleteBulk(Class<T> clazz, T filter) throws NiinooException;

	<T> void deleteBulk(Class<T> clazz, QueryParameter qp, T filter) throws NiinooException;

	<T> T getUnique(Class<T> clazz) throws NiinooException;

	<T> T getUnique(Class<T> clazz, QueryParameter qp) throws NiinooException;

	<T> T getUnique(Class<T> clazz, T filter) throws NiinooException;

	<T> T getUnique(Class<T> clazz, QueryParameter qp, T filter) throws NiinooException;

	<T> T getUniqueCached(Class<T> clazz) throws NiinooException;

	<T> T getUniqueCached(Class<T> clazz, QueryParameter qp) throws NiinooException;

	<T> T getUniqueCached(Class<T> clazz, T filter) throws NiinooException;

	<T> T getUniqueCached(Class<T> clazz, QueryParameter qp, T filter) throws NiinooException;

	<T> List<T> getList(Class<T> clazz) throws NiinooException;

	<T> Long getCount(Class<T> clazz) throws NiinooException;

	<T> List<T> getList(Class<T> clazz, QueryParameter qp) throws NiinooException;

	<T> Long getCount(Class<T> clazz, QueryParameter qp) throws NiinooException;

	<T> List<T> getList(Class<T> clazz, T filter) throws NiinooException;

	<T> Long getCount(Class<T> clazz, T filter) throws NiinooException;

	<T> List<T> getList(Class<T> clazz, QueryParameter qp, T filter) throws NiinooException;

	<T> Long getCount(Class<T> clazz, QueryParameter qp, T filter) throws NiinooException;

	<T> List<T> getListCached(Class<T> clazz) throws NiinooException;

	<T> Long getCountCached(Class<T> clazz) throws NiinooException;

	<T> List<T> getListCached(Class<T> clazz, QueryParameter qp) throws NiinooException;

	<T> Long getCountCached(Class<T> clazz, QueryParameter qp) throws NiinooException;

	<T> List<T> getListCached(Class<T> clazz, T filter) throws NiinooException;

	<T> Long getCountCached(Class<T> clazz, T filter) throws NiinooException;

	<T> List<T> getListCached(Class<T> clazz, QueryParameter qp, T filter) throws NiinooException;

	<T> Long getCountCached(Class<T> clazz, QueryParameter qp, T filter) throws NiinooException;

	<T> List<Pair<Long, String>> getLiteList(Class<T> clazz, String idPropName, String displayPropName)
			throws NiinooException;

	<T> List<Pair<Long, String>> getLiteList(Class<T> clazz, String idPropName, String displayPropName,
			QueryParameter qp) throws NiinooException;

	<T> List<Pair<Long, String>> getLiteList(Class<T> clazz, String idPropName, String displayPropName,
			QueryParameter qp, T filter) throws NiinooException;

	<T> List<Pair<Long, String>> getLiteListCached(Class<T> clazz, String idPropName, String displayPropName)
			throws NiinooException;

	<T> List<Pair<Long, String>> getLiteListCached(Class<T> clazz, String idPropName, String displayPropName,
			QueryParameter qp) throws NiinooException;

	<T> List<Pair<Long, String>> getLiteListCached(Class<T> clazz, String idPropName, String displayPropName,
			QueryParameter qp, T filter) throws NiinooException;

	<T, V> List<V> getPropertyValues(Class<T> clazz, QueryParameter qp, T filter, String propName, Class<V> returnClass)
			throws NiinooException;

	<T, V> List<V> getPropertyValuesDistinct(Class<T> clazz, QueryParameter qp, T filter, String propName,
			Class<V> returnClass) throws NiinooException;

	<T, V> V getPropertyValue(Class<T> clazz, Serializable id, String propName, Class<V> returnClass)
			throws NiinooException;

	<T, V> List<V> getPropertyValuesCached(Class<T> clazz, QueryParameter qp, T filter, String propName,
			Class<V> returnClass) throws NiinooException;

	<T, V> List<V> getPropertyValuesCachedDistinct(Class<T> clazz, QueryParameter qp, T filter, String propName,
			Class<V> returnClass) throws NiinooException;

	<T, V> V getPropertyValueCached(Class<T> clazz, Serializable id, String propName, Class<V> returnClass)
			throws NiinooException;

	void flushSession();

	<T> void evict(T obj);

	<T> void evictWithRefresh(T obj);

	<T> void refresh(T obj);

	void clearSession();

	<T> T merge(T obj);
	
}
