package ro.sok.niinoo.generic.service;

import java.io.Serializable;
import java.util.List;

import ro.sok.niinoo.utils.exception.NiinooException;
import ro.sok.niinoo.utils.query.Pair;
import ro.sok.niinoo.utils.query.QueryParameter;

public interface GenericService {

	<T> void saveOrUpdate(T obj) throws NiinooException;

	<T> T get(final Class<T> objClass, final Serializable id) throws NiinooException;

	<T> T add(final T obj) throws NiinooException;

	<T> List<T> addList(List<T> list) throws NiinooException;

	<T> void update(final T obj) throws NiinooException;

	<T> void updateList(final List<T> list) throws NiinooException;

	<T> void delete(final T obj) throws NiinooException;

	<T> void delete(final Class<T> clazz, final Serializable id) throws NiinooException;

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

	<T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName) throws NiinooException;

	<T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp) throws NiinooException;

	<T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp, final T filter) throws NiinooException;

	<T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName) throws NiinooException;

	<T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp) throws NiinooException;

	<T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp, final T filter) throws NiinooException;

	<T, V> List<V> getPropertyValues(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) throws NiinooException;

	<T, V> List<V> getPropertyValuesDistinct(Class<T> clazz, QueryParameter qp, T filter, String propName,
			Class<V> returnClass) throws NiinooException;

	<T, V> V getPropertyValue(final Class<T> clazz, final Serializable id, final String propName, Class<V> returnClass)
			throws NiinooException;

	<T, V> List<V> getPropertyValuesCached(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) throws NiinooException;

	<T, V> List<V> getPropertyValuesCachedDistinct(Class<T> clazz, QueryParameter qp, T filter, String propName,
			Class<V> returnClass) throws NiinooException;

	<T, V> V getPropertyValueCached(final Class<T> clazz, final Serializable id, final String propName,
			Class<V> returnClass) throws NiinooException;
}
