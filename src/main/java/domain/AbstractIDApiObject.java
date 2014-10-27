package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import domain.interfaces.HasId;
import domain.interfaces.HasJSON;

public abstract class AbstractIDApiObject<T extends HasId> implements HasId, Serializable, HasJSON<T>
{

	public static abstract class Builder extends Init<Builder>
	{

		@Override
		public abstract AbstractIDApiObject<?> build();

		@Override
		protected Builder self()
		{
			return this;
		}
	}

	protected static abstract class Init<T extends Init<T>>
	{
		private String id;

		private Long revision;

		private Boolean deleted;

		public abstract AbstractIDApiObject<?> build();

		public T deleted(final Boolean value)
		{
			deleted = value;
			return self();
		}

		public T id(final String value)
		{
			id = value;
			return self();
		}

		public T revision(final Long value)
		{
			this.revision = value;
			return self();
		}

		protected abstract T self();
	}

	protected static final SimpleDateFormat inputDf = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ssXXX");

	private static final long serialVersionUID = 2033325648556071101L;

	private String id;

	private Long revision;

	private Boolean deleted;

	public AbstractIDApiObject(final Init<?> init)
	{
		super();
		this.id = init.id;
		this.revision = init.revision;
		this.deleted = init.deleted;
	}

	@Override
	public String getId()
	{
		return id;
	}


	@Override
	public Long getRevision()
	{
		return revision;
	}

	protected int hashCode(int result)
	{
		final int prime = 31;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.revision == null) ? 0 : this.revision.hashCode());
		result = prime * result + ((this.deleted == null) ? 0 : this.deleted.hashCode());

		return result;
	}

	@Override
	public Boolean isDeleted()
	{
		return deleted;
	}

	@Override
	public void setDeleted(final Boolean deleted)
	{
		this.deleted = deleted;
	}

	@Override
	public void setId(final String id)
	{
		this.id = id;
	}

	@Override
	public void setRevision(final Long revision)
	{
		this.revision = revision;
	}

	public abstract JSONObject toJSON() throws JSONException;

	protected StringBuilder toString(final StringBuilder builder)
	{
		if (id != null)
		{
			builder.append("id=");
			builder.append(this.id);
		}
		if (deleted != null)
		{
			builder.append("_deleted=");
			builder.append(this.deleted);
		}
		if (revision != null)
		{
			builder.append("_rev=");
			builder.append(this.revision);
		}
		return builder;
	}

	@Override
	public void writeJSON(final JSONObject obj) throws JSONException
	{
		obj.put("uuid", getId());
		obj.put("deleted", isDeleted());
		obj.put("revision", getRevision());
	}
}