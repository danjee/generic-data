package ro.fortsoft.genericdata.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.fortsoft.genericdata.dao.GenericDAO;
import ro.fortsoft.genericdata.utils.exception.AppException;
import ro.fortsoft.genericdata.utils.query.Pair;
import ro.fortsoft.genericdata.utils.query.QueryParameter;

@Service("genericService")
@Transactional
public class GenericServiceImpl implements GenericService {

	@Autowired
	private GenericDAO genericDAO;
	
	public void setGenericDAO(GenericDAO genericDAO) {
		this.genericDAO = genericDAO;
	}

	@Override
	public <T> List<T> addList(final List<T> list) throws AppException {
		return genericDAO.addList(list);
	}

	@Override
	public <T> T get(final Class<T> objClass, final Serializable id) throws AppException {
		return genericDAO.get(objClass, id);
	}

	@Override
	public <T> T add(final T obj) throws AppException {
		return genericDAO.add(obj);
	}

	@Override
	public <T> void update(final T obj) throws AppException {
		genericDAO.update(obj);
	}

	@Override
	public <T> void updateList(final List<T> list) throws AppException {
		genericDAO.updateList(list);
	}

	@Override
	public <T> void delete(final T obj) throws AppException {
		genericDAO.delete(obj);
	}

	@Override
	public <T> void delete(final Class<T> clazz, final Serializable id) throws AppException {
		genericDAO.delete(clazz, id);
	}

	@Override
	public <T> void deleteBulk(final Class<T> clazz) throws AppException {
		genericDAO.deleteBulk(clazz);
	}

	@Override
	public <T> void deleteBulk(final Class<T> clazz, final QueryParameter qp) throws AppException {
		genericDAO.deleteBulk(clazz, qp);
	}

	@Override
	public <T> void deleteBulk(final Class<T> clazz, final T filter) throws AppException {
		genericDAO.deleteBulk(clazz, filter);
	}

	@Override
	public <T> void deleteBulk(final Class<T> clazz, final QueryParameter qp, final T filter) throws AppException {
		genericDAO.deleteBulk(clazz, qp, filter);
	}

	@Override
	public <T> T getUnique(final Class<T> clazz) throws AppException {
		return genericDAO.getUnique(clazz);
	}

	@Override
	public <T> T getUnique(final Class<T> clazz, final QueryParameter qp) throws AppException {
		return genericDAO.getUnique(clazz, qp);
	}

	@Override
	public <T> T getUnique(final Class<T> clazz, final T filter) throws AppException {
		return genericDAO.getUnique(clazz, filter);
	}

	@Override
	public <T> T getUnique(final Class<T> clazz, final QueryParameter qp, final T filter) throws AppException {
		return genericDAO.getUnique(clazz, qp, filter);
	}

	@Override
	public <T> T getUniqueCached(final Class<T> clazz) throws AppException {
		return genericDAO.getUniqueCached(clazz);
	}

	@Override
	public <T> T getUniqueCached(final Class<T> clazz, final QueryParameter qp) throws AppException {
		return genericDAO.getUniqueCached(clazz, qp);
	}

	@Override
	public <T> T getUniqueCached(final Class<T> clazz, final T filter) throws AppException {
		return genericDAO.getUniqueCached(clazz, filter);
	}

	@Override
	public <T> T getUniqueCached(final Class<T> clazz, final QueryParameter qp, final T filter) throws AppException {
		return genericDAO.getUniqueCached(clazz, qp, filter);
	}

	@Override
	public <T> List<T> getList(final Class<T> clazz) throws AppException {
		return genericDAO.getList(clazz);
	}

	@Override
	public <T> Long getCount(final Class<T> clazz) throws AppException {
		return genericDAO.getCount(clazz);
	}

	@Override
	public <T> List<T> getList(final Class<T> clazz, final QueryParameter qp) throws AppException {
		return genericDAO.getList(clazz, qp);
	}

	@Override
	public <T> Long getCount(final Class<T> clazz, final QueryParameter qp) throws AppException {
		return genericDAO.getCount(clazz, qp);
	}

	@Override
	public <T> List<T> getList(final Class<T> clazz, final T filter) throws AppException {
		return genericDAO.getList(clazz, filter);
	}

	@Override
	public <T> Long getCount(final Class<T> clazz, final T filter) throws AppException {
		return genericDAO.getCount(clazz, filter);
	}

	@Override
	public <T> List<T> getList(final Class<T> clazz, final QueryParameter qp, final T filter) throws AppException {
		return genericDAO.getList(clazz, qp, filter);
	}

	@Override
	public <T> Long getCount(final Class<T> clazz, final QueryParameter qp, final T filter) throws AppException {
		return genericDAO.getCount(clazz, qp, filter);
	}

	@Override
	public <T> List<T> getListCached(final Class<T> clazz) throws AppException {
		return genericDAO.getListCached(clazz);
	}

	@Override
	public <T> Long getCountCached(final Class<T> clazz) throws AppException {
		return genericDAO.getCountCached(clazz);
	}

	@Override
	public <T> List<T> getListCached(final Class<T> clazz, final QueryParameter qp) throws AppException {
		return genericDAO.getListCached(clazz, qp);
	}

	@Override
	public <T> Long getCountCached(final Class<T> clazz, final QueryParameter qp) throws AppException {
		return genericDAO.getCountCached(clazz, qp);
	}

	@Override
	public <T> List<T> getListCached(final Class<T> clazz, final T filter) throws AppException {
		return genericDAO.getListCached(clazz, filter);
	}

	@Override
	public <T> Long getCountCached(final Class<T> clazz, final T filter) throws AppException {
		return genericDAO.getCountCached(clazz, filter);
	}

	@Override
	public <T> List<T> getListCached(final Class<T> clazz, final QueryParameter qp, final T filter)
			throws AppException {
		return genericDAO.getListCached(clazz, qp, filter);
	}

	@Override
	public <T> Long getCountCached(final Class<T> clazz, final QueryParameter qp, final T filter)
			throws AppException {
		return genericDAO.getCountCached(clazz, qp, filter);
	}

	@Override
	public <T, V> List<V> getPropertyValues(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) throws AppException {
		return genericDAO.getPropertyValues(clazz, qp, filter, propName, returnClass);
	}

	@Override
	public <T, V> List<V> getPropertyValuesCachedDistinct(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) throws AppException {
		return genericDAO.getPropertyValuesCachedDistinct(clazz, qp, filter, propName, returnClass);
	};

	@Override
	public <T, V> V getPropertyValue(final Class<T> clazz, final Serializable id, final String propName,
			final Class<V> returnClass) throws AppException {
		return genericDAO.getPropertyValue(clazz, id, propName, returnClass);
	}

	@Override
	public <T, V> List<V> getPropertyValuesCached(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) throws AppException {
		return genericDAO.getPropertyValuesCached(clazz, qp, filter, propName, returnClass);
	}

	@Override
	public <T, V> List<V> getPropertyValuesDistinct(final Class<T> clazz, final QueryParameter qp, final T filter,
			final String propName, final Class<V> returnClass) throws AppException {
		return genericDAO.getPropertyValuesCachedDistinct(clazz, qp, filter, propName, returnClass);
	};

	@Override
	public <T, V> V getPropertyValueCached(final Class<T> clazz, final Serializable id, final String propName,
			final Class<V> returnClass) throws AppException {
		return genericDAO.getPropertyValueCached(clazz, id, propName, returnClass);
	}

	@Override
	public <T> void saveOrUpdate(final T obj) throws AppException {
		genericDAO.saveOrUpdate(obj);
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName) throws AppException {
		return genericDAO.getLiteList(clazz, idPropName, displayPropName);
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp) throws AppException {
		return genericDAO.getLiteList(clazz, idPropName, displayPropName, qp);
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteList(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp, final T filter) throws AppException {
		return genericDAO.getLiteList(clazz, idPropName, displayPropName, qp, filter);
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName) throws AppException {
		return genericDAO.getLiteListCached(clazz, idPropName, displayPropName);
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp) throws AppException {
		return genericDAO.getLiteListCached(clazz, idPropName, displayPropName, qp);
	}

	@Override
	public <T> List<Pair<Long, String>> getLiteListCached(final Class<T> clazz, final String idPropName,
			final String displayPropName, final QueryParameter qp, final T filter) throws AppException {
		return genericDAO.getLiteListCached(clazz, idPropName, displayPropName, qp, filter);
	}
}
