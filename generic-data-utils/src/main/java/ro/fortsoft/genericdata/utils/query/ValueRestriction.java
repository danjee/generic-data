package ro.fortsoft.genericdata.utils.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValueRestriction implements Serializable {

	private static final long serialVersionUID = 5594095859718010390L;

	private String field;
	private String comparator;
	private Object value;
	private String sql;
	private List<ValueRestriction> orRestrictions;
	private Boolean isNotRestriction = Boolean.FALSE;

	public ValueRestriction(final String field, final String comparator, final Object value) {
		this.field = field;
		this.comparator = comparator;
		this.value = value;
	}

	public ValueRestriction(final String field, final String comparator, final Object value,
			final Boolean isNotRestriction) {
		this.field = field;
		this.comparator = comparator;
		this.value = value;
		this.isNotRestriction = isNotRestriction;
	}

	public ValueRestriction(final String field, final String comparator) {
		this.field = field;
		this.comparator = comparator;
	}

	public ValueRestriction(final String field, final String comparator, final Boolean isNotRestriction) {
		this.field = field;
		this.comparator = comparator;
		this.isNotRestriction = isNotRestriction;
	}

	public ValueRestriction(final String sql) {
		comparator = QueryParameter.SQL;
		this.sql = sql;
	}

	public ValueRestriction() {
	}

	public String getField() {
		return field;
	}

	public void setField(final String field) {
		this.field = field;
	}

	public String getComparator() {
		return comparator;
	}

	public void setComparator(final String comparator) {
		this.comparator = comparator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(final String sql) {
		this.sql = sql;
	}

	public List<ValueRestriction> getOrRestrictions() {
		if (orRestrictions == null) {
			orRestrictions = new ArrayList<>();
		}
		return orRestrictions;
	}

	public void setOrRestrictions(final List<ValueRestriction> orRestrictions) {
		this.orRestrictions = orRestrictions;
	}

	public Boolean getIsNotRestriction() {
		return isNotRestriction;
	}

	public void setIsNotRestriction(final Boolean isNotRestriction) {
		this.isNotRestriction = isNotRestriction;
	}
}
