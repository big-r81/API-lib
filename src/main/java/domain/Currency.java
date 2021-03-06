package domain;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class Currency extends AbstractNameAndNumberApiObject<Currency>
{
	public static class Builder extends Init<Builder>
	{

		@Override
		protected Builder self()
		{
			return this;
		}

	}
	protected static abstract class Init<T extends Init<T>> extends
	AbstractNameAndNumberApiObject.Init<T>
	{
		private String symbol = null;
		private String key = null;
		private String centName = null;

		@Override
		public Currency build()
		{
			return new Currency(this);
		}

		public T centName(final String value)
		{
			centName = value;
			return self();
		}

		public T key(final String value)
		{
			key = value;
			return self();
		}

		public T symbol(final String value)
		{
			symbol = value;
			return self();
		}
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -4139497347198731346L;
	private String symbol;

	private String key;

	private String centName;

	private Currency(final Init<?> init)
	{
		super(init);
		symbol = init.symbol;
		key = init.key;
		centName = init.centName;
	}

// public JSONObject toJSON()
// {
// final JSONObject obj = new JSONObject();
// try
// {

//
// return obj;
// }
// catch (final JSONException e)
// {
// e.printStackTrace();
// return null;
// }
// }
//
// public static Currency fromJSON(JSONObject obj) throws JSONException
// {
// if (obj.has("result") && nullStringToNull(obj,"result") != null)
// obj = obj.getJSONObject("result");
// final Currency cur = new Currency.Builder(nullStringToNull(obj,"name")).deleted(
// obj.getBoolean("deleted"))
// .revision(nullStringToNull(obj,"revision"))
// .uuid(nullStringToNull(obj,"uuid"))
// .number(nullStringToNull(obj,"number"))
// .symbol(nullStringToNull(obj,"symbol"))
// .key(nullStringToNull(obj,"key"))
// .centName(nullStringToNull(obj,"centName"))
// .build();
// return cur;
// }

	@Override
	public boolean equals(final Object obj)
	{

		return obj.hashCode() == this.hashCode();
	}

	public String getCentName()
	{
		return centName;
	}

	public String getKey()
	{
		return key;
	}

	public String getSymbol()
	{
		return symbol;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = super.hashCode(result);
		result = prime * result + ((this.key == null) ? 0 : this.key.hashCode());
		result = prime * result + ((this.symbol == null) ? 0 : this.symbol.hashCode());
		result = prime * result + ((this.centName == null) ? 0 : this.centName.hashCode());

		return result;
	}

	public void setCentName(final String centName)
	{
		this.centName = centName;
	}

	public void setKey(final String key)
	{
		this.key = key;
	}

	public void setSymbol(final String symbol)
	{
		this.symbol = symbol;
	}

	@Override
	public JSONObject toJSON() throws JSONException
	{
		JSONObject obj = new JSONObject();
		obj = super.appendJSON(obj);

		obj.put("symbol", symbol);
		obj.put("key", key);
		obj.put("centName", centName);

		return obj;
	}

	public static Currency fromJSON(JSONObject obj) throws JSONException
	{
		if (obj.has("result") && nullStringToNull(obj, "result") != null)
		{
			obj = obj.getJSONObject("result");
		}
		final Currency cur = new Currency.Builder().name(nullStringToNull(obj, "name"))
			.deleted(obj.getBoolean("deleted"))
			.revision(obj.getLong("revision"))
			.id(nullStringToNull(obj, "uuid"))
			.number(nullStringToNull(obj, "number"))
			.symbol(nullStringToNull(obj, "symbol"))
			.key(nullStringToNull(obj, "key"))
			.centName(nullStringToNull(obj, "centName"))
			.build();
		return cur;
	}
}
