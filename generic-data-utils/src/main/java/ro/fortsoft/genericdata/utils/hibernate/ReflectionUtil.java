package ro.fortsoft.genericdata.utils.hibernate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.StringUtils;

public class ReflectionUtil {

	public static Field[] getEntityFields(final Class<?> clazz) {
		final Field[] fields = clazz.getDeclaredFields();
		for (final Field field : fields) {
			if (!Modifier.isPublic(field.getModifiers())) {
				field.setAccessible(true);
			}
		}
		return fields;
	}

	public static List<Field> getEntityLazyFields(final Class<?> clazz) {
		final Field[] fields = clazz.getDeclaredFields();
		final List<Field> retList = new ArrayList<Field>();
		for (final Field field : fields) {
			if (!Modifier.isPublic(field.getModifiers())) {
				field.setAccessible(true);
			}
			if (field.isAnnotationPresent(OneToMany.class) && FetchType.LAZY
					.equals(field.getAnnotation(OneToMany.class).fetch())
					|| field.isAnnotationPresent(ManyToOne.class) && FetchType.LAZY
					.equals(field.getAnnotation(ManyToOne.class)
							.fetch())
					|| field.isAnnotationPresent(ManyToMany.class) && FetchType.LAZY
					.equals(field.getAnnotation(ManyToMany.class)
							.fetch())
					|| field.isAnnotationPresent(OneToOne.class) && FetchType.LAZY
					.equals(field.getAnnotation(OneToOne.class).fetch())) {
				retList.add(field);
			}
		}
		return retList;
	}

	public static Field getLazyField(final Class<?> clazz,
			final String fieldName) {
		final List<Field> lazyFields = getEntityLazyFields(clazz);
		for (final Field field : lazyFields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}

	private static Field getEntityIdField(final Class<?> clazz) {

		final Field[] fields = clazz.getDeclaredFields();
		for (final Field field : fields) {
			if (!Modifier.isPublic(field.getModifiers())) {
				field.setAccessible(true);
			}
			if (field.getAnnotation(Id.class) != null) {
				return field;
			}
		}
		return null;
	}

	public static final String getEntityIdFieldName(final Class<?> clazz) {
		final Field field = getEntityIdField(clazz);
		if (field != null) {
			return field.getName();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static final <K extends Serializable, V> K getEntityIdFieldValue(
			final Class<V> clazz, final V obj) {
		final Field field = getEntityIdField(clazz);
		if (field != null) {
			try {
				return (K) field.get(obj);
			} catch (final IllegalArgumentException e) {
				e.printStackTrace();
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T getObjectProperty(
			final Object obj, final String propName) {

		Class targetClass = obj.getClass();
		// Keep backing up the inheritance hierarchy.
		do {
			final Field[] fields = targetClass.getDeclaredFields();
			for (final Field field : fields) {
				if (!Modifier.isPublic(field.getModifiers())) {
					field.setAccessible(true);
				}
				if (field.getName().equals(propName)) {
					try {
						return (T) field.get(obj);
					} catch (final IllegalArgumentException e) {
						e.printStackTrace();
					} catch (final IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null && targetClass != Object.class);

		return null;
	}

	public static <T> T setObjectProperty(final T obj, final Object value,
			final String propName) {

		Class targetClass = obj.getClass();
		// Keep backing up the inheritance hierarchy.
		do {
			final Method[] methods = targetClass.getDeclaredMethods();
			for (final Method method : methods) {
				if (Modifier.isPublic(method.getModifiers())) {
					final String name = "set" + StringUtils.capitalize(propName);
					if (method.getName().equals(name)) {
						try {
							// System.err.println("name=" + name + ",value="
							// + value);
							method.invoke(obj, value);
							break;
						} catch (final Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null && targetClass != Object.class);

		return obj;
	}

	public static <T> T setObjectProperties(final T obj, final Object[] values,
			final List<String> props) {

		Class targetClass = obj.getClass();
		// Keep backing up the inheritance hierarchy.
		do {

			for (int i = 0; i < props.size(); i++) {
				final String propName = props.get(i);
				final String name = "set" + StringUtils.capitalize(propName);
				final Object val = values[i];
				final Field[] fields = targetClass.getDeclaredFields();
				for (final Field field : fields) {
					if (!Modifier.isPublic(field.getModifiers())) {
						field.setAccessible(true);
					}
					if (field.getName().equals(propName)) {
						try {
							final Method method = targetClass.getDeclaredMethod(name,
									field.getType());
							method.invoke(obj, val);
						} catch (final Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null && targetClass != Object.class);

		return obj;
	}

	@SuppressWarnings("unchecked")
	public static <J extends Serializable, K extends Serializable> List<J> getTransformedList(
			final Collection<K> source, final String field) {
		final List<J> retval = new ArrayList<J>();
		final Iterator<K> iterator = source.iterator();
		while (iterator.hasNext()) {
			final K k = iterator.next();
			final Serializable j = getObjectProperty(k, field);
			retval.add((J) j);
		}
		return retval;
	}


	public static Object invokeMethod(final Method method, final Object target) {
		return invokeMethod(method, target, new Object[0]);
	}

	public static Object invokeMethod(final Method method, final Object target,
			final Object[] args) {
		try {
			return method.invoke(target, args);
		} catch (final Exception ex) {
			ex.printStackTrace();
			throw new IllegalStateException("Should never get here");
		}
	}
}
