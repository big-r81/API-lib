package domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class Price
{
	private static final SimpleDateFormat inputDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
	private Pricelist priceList;
	private Date validFrom;
	private BigDecimal value;
	private String articleCode;
	private OrganizationalUnit organizationalUnit;

	public Price(final OrganizationalUnit organizationalUnit, final BigDecimal value)
	{
		super();
		this.value = value;
		this.organizationalUnit = organizationalUnit;
	}

	/**
	 * old contructor
	 *
	 * @param priceList
	 * @param validFrom
	 * @param value
	 */
	public Price(final Pricelist priceList, final Date validFrom, final BigDecimal value)
	{
		super();
		this.priceList = priceList;
		this.validFrom = validFrom;
		this.value = value;
	}

	public Price(final String articleCode, final Pricelist pricelist, final BigDecimal value)
	{
		super();
		this.value = value;
		this.articleCode = articleCode;
		this.priceList = pricelist;
	}

	@Override
	public boolean equals(final Object obj)
	{

		return obj.hashCode() == this.hashCode();
	}

	public String getArticleCode()
	{
		return articleCode;
	}

	public OrganizationalUnit getOrganizationalUnit()
	{
		return organizationalUnit;
	}

	public Pricelist getPriceList()
	{
		return priceList;
	}

	public Date getValidFrom()
	{
		return validFrom;
	}

	public BigDecimal getValue()
	{
		return value;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;

		result = prime * result + ((this.priceList == null) ? 0 : this.priceList.hashCode());
		result = prime * result + ((this.validFrom == null) ? 0 : this.validFrom.hashCode());
		result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());


		return result;
	}

	public void setArticleCode(final String articleCode)
	{
		this.articleCode = articleCode;
	}

	public void setOrganizationalUnit(final OrganizationalUnit organizationalUnit)
	{
		this.organizationalUnit = organizationalUnit;
	}

	public void setPriceList(final Pricelist priceList)
	{
		this.priceList = priceList;
	}

	public void setValidFrom(final Date validFrom)
	{
		this.validFrom = validFrom;
	}

	public void setValue(final BigDecimal value)
	{
		this.value = value;
	}

	// TODO replace new Date(0) with null after release 30.01.15
	public JSONObject toJSON()
	{

		final JSONObject obj = new JSONObject();

		try
		{
			if (priceList != null)
			{
				obj.put("priceList", priceList.getId());
			}

			if (validFrom != null)
			{
				obj.put("validFrom", inputDf.format(validFrom));
			}

			obj.put("value", value);

			if (organizationalUnit != null)
			{
				obj.put("organizationalUnit", organizationalUnit.getId());
			}

			obj.put("articleCode", articleCode);

			return obj;

		}
		catch (final JSONException e)
		{

			e.printStackTrace();

			return null;

		}
	}

}
