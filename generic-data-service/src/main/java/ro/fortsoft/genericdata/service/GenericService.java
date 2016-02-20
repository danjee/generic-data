package ro.fortsoft.genericdata.service;

import java.io.Serializable;
import java.util.List;

import ro.fortsoft.genericdata.utils.exception.AppException;
import ro.fortsoft.genericdata.utils.query.Pair;
import ro.fortsoft.genericdata.utils.query.QueryParameter;

public interface GenericService {

	<T> void saveOrUpdate(T obj) throws AppException;

	<T> T get(final Class<T> objClass, final Serializable id) throws AppException;

	<T> T add(final T obj) throws AppException;

	<T> List<T> addList(List<T> list) throws AppException;

	<T> void update(final T obj) throws AppException;

	<T> void updateList(final List<T> list) throws AppException;

	<T> void delete(final T obj) throws AppException;

	<T> void delete(final Class<T> clazz, final Serializable id) throws AppException;

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

	<T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName) throws AppException;

	<T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp) throws AppException;

	<T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp, final T filter) throws AppException;

	<T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName) throws AppException;

	<T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp) throws AppException;

	<T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp, final T filter) throws AppException;

	<T, V> List<V> getPropertyValues(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) throws AppException;

	<T, V> List<V> getPropertyValuesDistinct(Class<T> clazz, QueryParameter qp, T filter, String propName,
			Class<V> returnClass) throws AppException;

	<T, V> V getPropertyValue(final Class<T> clazz, final Serializable id, final String propName, Class<V> returnClass)
			throws AppException;

	<T, V> List<V> getPropertyValuesCached(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) throws AppException;

	<T, V> List<V> getPropertyValuesCachedDistinct(Class<T> clazz, QueryParameter qp, T filter, String propName,
			Class<V> returnClass) throws AppException;

	<T, V> V getPropertyValueCached(final Class<T> clazz, final Serializable id, final String propName,
			Class<V> returnClass) throws AppException;
}
