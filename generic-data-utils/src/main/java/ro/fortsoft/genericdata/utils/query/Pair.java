package ro.fortsoft.genericdata.utils.query;

import java.io.Serializable;

public class Pair<K, V> implements Serializable {

	private static final long serialVersionUID = 6888452733947143748L;

	private K id;

	private V value;

	public Pair() {

	}

	public Pair(final K id) {
		this.id = id;
	}

	public Pair(final K id, final V value) {
		this.id = id;
		this.value = value;
	}

	@Override
	public String toString() {
		String value = "";
		if (this.value != null) {
			value = this.value.toString();
		}
		return value;
	}

	public String toStringExtended() {

		String idStr = "null";
		if (id != null) {
			idStr = id.toString();
		}
		String valStr = "null";
		if (value != null) {
			valStr = value.toString();
		}
		return "Pair[" + idStr + ";" + valStr + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		result = (prime * result) + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pair<K, V> other = (Pair<K, V>) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	public K getId() {
		return id;
	}

	public void setId(final K id) {
		this.id = id;
	}

	public V getValue() {
		return value;
	}

	public void setValue(final V value) {
		this.value = value;
	}

}
