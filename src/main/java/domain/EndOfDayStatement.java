package domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class EndOfDayStatement extends AbstractIDApiObject<EndOfDayStatement>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4420456052325768842L;

	private List<EndOfDayAccountSummary> accountSummaries;

	private List<EndOfDayCashierSummary> cashierSummaries;

	private List<EndOfDayCommoditygroupSummary> commodityGroupSummaries;

	private List<EndOfDayCustomergroupSummary> customerGroupSummaries;

	private Date finishTime;

	private List<EndOfDayPaymentSummary> paymentSummaries;

	private long receiptCount;

	private List<EndOfDayTaxSummary> taxSummaries;

	private long zCount;

	private POS pos;

	protected static abstract class Init<T extends Init<T>> extends AbstractIDApiObject.Init<T>
	{
		private List<EndOfDayAccountSummary> accountSummaries = new ArrayList<EndOfDayAccountSummary>();

		private List<EndOfDayCashierSummary> cashierSummaries = new ArrayList<EndOfDayCashierSummary>();

		private List<EndOfDayCommoditygroupSummary> commodityGroupSummaries = new ArrayList<EndOfDayCommoditygroupSummary>();

		private List<EndOfDayCustomergroupSummary> customerGroupSummaries = new ArrayList<EndOfDayCustomergroupSummary>();

		private Date finishTime;

		private List<EndOfDayPaymentSummary> paymentSummaries = new ArrayList<EndOfDayPaymentSummary>();

		private long receiptCount;

		private List<EndOfDayTaxSummary> taxSummaries = new ArrayList<EndOfDayTaxSummary>();

		private long zCount;

		private POS pos;

		public T accountSummaries(final List<EndOfDayAccountSummary> value)
		{
			this.accountSummaries = value;
			return self();
		}

		public T cashierSummaries(final List<EndOfDayCashierSummary> value)
		{
			this.cashierSummaries = value;
			return self();
		}

		public T commodityGroupSummaries(final List<EndOfDayCommoditygroupSummary> value)
		{
			this.commodityGroupSummaries = value;
			return self();
		}

		public T customerGroupSummaries(final List<EndOfDayCustomergroupSummary> value)
		{
			this.customerGroupSummaries = value;
			return self();
		}

		public T finishTime(final Date value)
		{
			this.finishTime = value;
			return self();
		}

		public T paymentSummaries(final List<EndOfDayPaymentSummary> value)
		{
			this.paymentSummaries = value;
			return self();
		}

		public T receiptCount(final long value)
		{
			this.receiptCount = value;
			return self();
		}

		public T taxSummaries(final List<EndOfDayTaxSummary> value)
		{
			this.taxSummaries = value;
			return self();
		}

		public T zCount(final long value)
		{
			this.zCount = value;
			return self();
		}

		public T pos(final POS value)
		{
			this.pos = value;
			return self();
		}

		@Override
		public EndOfDayStatement build()
		{
			return new EndOfDayStatement(this);
		}
	}

	public static class Builder extends Init<Builder>
	{

		@Override
		protected Builder self()
		{
			return this;
		}

	}

	public EndOfDayStatement(final Init<?> init)
	{
		super(init);

		accountSummaries = init.accountSummaries;

		cashierSummaries = init.cashierSummaries;

		commodityGroupSummaries = init.commodityGroupSummaries;

		customerGroupSummaries = init.customerGroupSummaries;

		finishTime = init.finishTime;

		paymentSummaries = init.paymentSummaries;

		receiptCount = init.receiptCount;

		taxSummaries = init.taxSummaries;

		zCount = init.zCount;

		pos = init.pos;
	}

// public static EndOfDayStatement fromJSON(JSONObject obj) throws JSONException, ParseException
// {
//
// if (obj.has("result") && obj.getString("result") != null)
// obj = obj.getJSONObject("result");
//
// final List<EndOfDayAccountSummary> endOfDayAccountSummaries = new
// ArrayList<EndOfDayAccountSummary>();
// EndOfDayAccountSummary endOfDayAccountSummary = null;
//
// final List<EndOfDayCashierSummary> cashierSummaries = new ArrayList<EndOfDayCashierSummary>();
// EndOfDayCashierSummary cashierSummary = null;
//
// final List<EndOfDayCommoditygroupSummary> commoditygroupSummaries = new
// ArrayList<EndOfDayCommoditygroupSummary>();
// EndOfDayCommoditygroupSummary commoditygroupSummary = null;
//
// final List<EndOfDayCustomergroupSummary> customergroupSummaries = new
// ArrayList<EndOfDayCustomergroupSummary>();
// EndOfDayCustomergroupSummary customergroupSummary = null;
//
// final List<EndOfDayPaymentSummary> paymentSummaries = new ArrayList<EndOfDayPaymentSummary>();
// EndOfDayPaymentSummary paymentSummary = null;
//
// final List<EndOfDayTaxSummary> taxSummaries = new ArrayList<EndOfDayTaxSummary>();
// EndOfDayTaxSummary taxSummary = null;
//
// // AccountSummaries
// final JSONArray jEndOfDayAccountSummaries = obj.getJSONArray("accountSummaries");
// if (jEndOfDayAccountSummaries.length() != 0)
// for (int i = 0; i < jEndOfDayAccountSummaries.length(); i++)
// {
// final JSONObject jEndOfDayAccountSummary = jEndOfDayAccountSummaries.getJSONObject(i);
// endOfDayAccountSummary = EndOfDayAccountSummary.fromJSON(jEndOfDayAccountSummary);
// if (endOfDayAccountSummary != null)
// endOfDayAccountSummaries.add(endOfDayAccountSummary);
// }
//
// // CashierSummaries
// final JSONArray jEndOfDayCashierSummaries = obj.getJSONArray("cashierSummaries");
// if (jEndOfDayCashierSummaries.length() != 0)
// for (int i = 0; i < jEndOfDayAccountSummaries.length(); i++)
// {
// final JSONObject jEndOfDayCashierSummary = jEndOfDayCashierSummaries.getJSONObject(i);
// cashierSummary = EndOfDayCashierSummary.fromJSON(jEndOfDayCashierSummary);
// if (cashierSummary != null)
// cashierSummaries.add(cashierSummary);
// }
//
// // CommodityGroup
// final JSONArray jEndOfDayCommoditygroupSummaries = obj.getJSONArray("commodityGroupSummaries");
// if (jEndOfDayCommoditygroupSummaries.length() != 0)
// for (int i = 0; i < jEndOfDayCommoditygroupSummaries.length(); i++)
// {
// final JSONObject jEndOfDayCommoditygroupSummary =
// jEndOfDayCommoditygroupSummaries.getJSONObject(i);
// commoditygroupSummary = EndOfDayCommoditygroupSummary.fromJSON(jEndOfDayCommoditygroupSummary);
// if (endOfDayAccountSummary != null)
// commoditygroupSummaries.add(commoditygroupSummary);
// }
//
// // CustomerGroup
// final JSONArray jEndOfDayCustomergroupSummaries = obj.getJSONArray("customerGroupSummaries");
// if (jEndOfDayCustomergroupSummaries.length() != 0)
// for (int i = 0; i < jEndOfDayCustomergroupSummaries.length(); i++)
// {
// final JSONObject jEndOfDayCustomergroupSummary =
// jEndOfDayCustomergroupSummaries.getJSONObject(i);
// customergroupSummary = EndOfDayCustomergroupSummary.fromJSON(jEndOfDayCustomergroupSummary);
// if (customergroupSummary != null)
// customergroupSummaries.add(customergroupSummary);
// }
//
//
// // Payment
// final JSONArray jEndOfDayPaymentSummaries = obj.getJSONArray("paymentSummaries");
// if (jEndOfDayPaymentSummaries.length() != 0)
// for (int i = 0; i < jEndOfDayPaymentSummaries.length(); i++)
// {
// final JSONObject jEndOfDayPaymentSummary = jEndOfDayPaymentSummaries.getJSONObject(i);
// paymentSummary = EndOfDayPaymentSummary.fromJSON(jEndOfDayPaymentSummary);
// if (paymentSummary != null)
// paymentSummaries.add(paymentSummary);
// }
//
//
// // Tax
// final JSONArray jEndOfDayTaxSummaries = obj.getJSONArray("taxSummaries");
// if (jEndOfDayTaxSummaries.length() != 0)
// for (int i = 0; i < jEndOfDayTaxSummaries.length(); i++)
// {
// final JSONObject jEndOfDayTaxSummary = jEndOfDayTaxSummaries.getJSONObject(i);
// taxSummary = EndOfDayTaxSummary.fromJSON(jEndOfDayTaxSummary);
// if (taxSummary != null)
// taxSummaries.add(taxSummary);
// }
//
//
// // POS
// final POS pos = new POS.Builder(null).uuid(obj.getString("pos")).build();
//
//
// final EndOfDayStatement endOfDayStatement = new EndOfDayStatement.Builder().deleted(
// obj.getBoolean("deleted"))
// .revision(obj.getLong("revision"))
// .uuid(obj.getString("uuid"))
// .finishTime(inputDf.parse(obj.getString("finishTime")))
// .pos(pos)
// .receiptCount(obj.getLong("receiptCount"))
// .zCount(obj.getLong("zCount"))
// .accountSummaries(endOfDayAccountSummaries)
// .cashierSummaries(cashierSummaries)
// .commodityGroupSummaries(commoditygroupSummaries)
// .customerGroupSummaries(customergroupSummaries)
// .paymentSummaries(paymentSummaries)
// .taxSummaries(taxSummaries)
// .build();
//
// return endOfDayStatement;
//
// }

	public Date getFinishTime()
	{
		return finishTime;
	}

	public void ListFinishTime(final Date finishTime)
	{
		this.finishTime = finishTime;
	}

	public long getReceiptCount()
	{
		return receiptCount;
	}

	public void ListReceiptCount(final long receiptCount)
	{
		this.receiptCount = receiptCount;
	}

	public long getzCount()
	{
		return zCount;
	}

	public void ListzCount(final long zCount)
	{
		this.zCount = zCount;
	}

	public POS getPos()
	{
		return pos;
	}

	public void ListPos(final POS pos)
	{
		this.pos = pos;
	}


	public List<EndOfDayAccountSummary> getAccountSummaries()
	{
		return accountSummaries;
	}

	public List<EndOfDayCashierSummary> getCashierSummaries()
	{
		return cashierSummaries;
	}

	public List<EndOfDayCommoditygroupSummary> getCommodityGroupSummaries()
	{
		return commodityGroupSummaries;
	}

	public List<EndOfDayCustomergroupSummary> getCustomerGroupSummaries()
	{
		return customerGroupSummaries;
	}

	public List<EndOfDayPaymentSummary> getPaymentSummaries()
	{
		return paymentSummaries;
	}

	public List<EndOfDayTaxSummary> getTaxSummaries()
	{
		return taxSummaries;
	}

	public void setAccountSummaries(final List<EndOfDayAccountSummary> accountSummaries)
	{
		this.accountSummaries = accountSummaries;
	}

	public void setCashierSummaries(final List<EndOfDayCashierSummary> cashierSummaries)
	{
		this.cashierSummaries = cashierSummaries;
	}

	public void setCommodityGroupSummaries(
		final List<EndOfDayCommoditygroupSummary> commodityGroupSummaries)
	{
		this.commodityGroupSummaries = commodityGroupSummaries;
	}

	public void setCustomerGroupSummaries(
		final List<EndOfDayCustomergroupSummary> customerGroupSummaries)
	{
		this.customerGroupSummaries = customerGroupSummaries;
	}

	public void setFinishTime(final Date finishTime)
	{
		this.finishTime = finishTime;
	}

	public void setPaymentSummaries(final List<EndOfDayPaymentSummary> paymentSummaries)
	{
		this.paymentSummaries = paymentSummaries;
	}

	public void setReceiptCount(final long receiptCount)
	{
		this.receiptCount = receiptCount;
	}

	public void setTaxSummaries(final List<EndOfDayTaxSummary> taxSummaries)
	{
		this.taxSummaries = taxSummaries;
	}

	public void setzCount(final long zCount)
	{
		this.zCount = zCount;
	}

	public void setPos(final POS pos)
	{
		this.pos = pos;
	}

	@Override
	public boolean equals(final Object obj)
	{

		return obj.hashCode() == this.hashCode();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;

		result = super.hashCode(result);
		result = prime * result +
			((this.accountSummaries == null) ? 0 : this.accountSummaries.hashCode());
		result = prime * result +
			((this.cashierSummaries == null) ? 0 : this.cashierSummaries.hashCode());
		result = prime * result +
			((this.commodityGroupSummaries == null) ? 0 : this.commodityGroupSummaries.hashCode());
		result = prime * result +
			((this.customerGroupSummaries == null) ? 0 : this.customerGroupSummaries.hashCode());
		result = prime * result + ((this.finishTime == null) ? 0 : this.finishTime.hashCode());
		result = prime * result +
			((this.paymentSummaries == null) ? 0 : this.paymentSummaries.hashCode());
		result = prime * result + ((this.receiptCount == 0) ? 0 : 1);
		result = prime * result + ((this.taxSummaries == null) ? 0 : this.taxSummaries.hashCode());
		result = prime * result +
			((this.accountSummaries == null) ? 0 : this.accountSummaries.hashCode());
		result = prime * result +
			((this.accountSummaries == null) ? 0 : this.accountSummaries.hashCode());
		result = prime * result +
			((this.accountSummaries == null) ? 0 : this.accountSummaries.hashCode());


		return result;
	}

	@Override
	public JSONObject toJSON() throws JSONException
	{
		final JSONObject obj = new JSONObject();
		writeJSON(obj);
		return obj;
	}

	@Override
	public void writeJSON(final JSONObject obj) throws JSONException
	{
		super.writeJSON(obj);

	}

	public static EndOfDayStatement fromJSON(JSONObject obj) throws JSONException, ParseException
	{
		if (obj.has("result") && obj.getString("result") != null)
			obj = obj.getJSONObject("result");

		final List<EndOfDayAccountSummary> endOfDayAccountSummaries = new ArrayList<EndOfDayAccountSummary>();
		EndOfDayAccountSummary endOfDayAccountSummary = null;

		final List<EndOfDayCashierSummary> cashierSummaries = new ArrayList<EndOfDayCashierSummary>();
		EndOfDayCashierSummary cashierSummary = null;

		final List<EndOfDayCommoditygroupSummary> commoditygroupSummaries = new ArrayList<EndOfDayCommoditygroupSummary>();
		EndOfDayCommoditygroupSummary commoditygroupSummary = null;

		final List<EndOfDayCustomergroupSummary> customergroupSummaries = new ArrayList<EndOfDayCustomergroupSummary>();
		EndOfDayCustomergroupSummary customergroupSummary = null;

		final List<EndOfDayPaymentSummary> paymentSummaries = new ArrayList<EndOfDayPaymentSummary>();
		EndOfDayPaymentSummary paymentSummary = null;

		final List<EndOfDayTaxSummary> taxSummaries = new ArrayList<EndOfDayTaxSummary>();
		EndOfDayTaxSummary taxSummary = null;

		// AccountSummaries
		final JSONArray jEndOfDayAccountSummaries = obj.getJSONArray("accountSummaries");
		if (jEndOfDayAccountSummaries.length() != 0)
			for (int i = 0; i < jEndOfDayAccountSummaries.length(); i++)
			{
				final JSONObject jEndOfDayAccountSummary = jEndOfDayAccountSummaries.getJSONObject(i);
				endOfDayAccountSummary = EndOfDayAccountSummary.fromJSON(jEndOfDayAccountSummary);
				if (endOfDayAccountSummary != null)
					endOfDayAccountSummaries.add(endOfDayAccountSummary);
			}

		// CashierSummaries
		final JSONArray jEndOfDayCashierSummaries = obj.getJSONArray("cashierSummaries");
		if (jEndOfDayCashierSummaries.length() != 0)
			for (int i = 0; i < jEndOfDayAccountSummaries.length(); i++)
			{
				final JSONObject jEndOfDayCashierSummary = jEndOfDayCashierSummaries.getJSONObject(i);
				cashierSummary = EndOfDayCashierSummary.fromJSON(jEndOfDayCashierSummary);
				if (cashierSummary != null)
					cashierSummaries.add(cashierSummary);
			}

		// CommodityGroup
		final JSONArray jEndOfDayCommoditygroupSummaries = obj.getJSONArray("commodityGroupSummaries");
		if (jEndOfDayCommoditygroupSummaries.length() != 0)
			for (int i = 0; i < jEndOfDayCommoditygroupSummaries.length(); i++)
			{
				final JSONObject jEndOfDayCommoditygroupSummary = jEndOfDayCommoditygroupSummaries.getJSONObject(i);
				commoditygroupSummary = EndOfDayCommoditygroupSummary.fromJSON(jEndOfDayCommoditygroupSummary);
				if (endOfDayAccountSummary != null)
					commoditygroupSummaries.add(commoditygroupSummary);
			}

		// CustomerGroup
		final JSONArray jEndOfDayCustomergroupSummaries = obj.getJSONArray("customerGroupSummaries");
		if (jEndOfDayCustomergroupSummaries.length() != 0)
			for (int i = 0; i < jEndOfDayCustomergroupSummaries.length(); i++)
			{
				final JSONObject jEndOfDayCustomergroupSummary = jEndOfDayCustomergroupSummaries.getJSONObject(i);
				customergroupSummary = EndOfDayCustomergroupSummary.fromJSON(jEndOfDayCustomergroupSummary);
				if (customergroupSummary != null)
					customergroupSummaries.add(customergroupSummary);
			}


		// Payment
		final JSONArray jEndOfDayPaymentSummaries = obj.getJSONArray("paymentSummaries");
		if (jEndOfDayPaymentSummaries.length() != 0)
			for (int i = 0; i < jEndOfDayPaymentSummaries.length(); i++)
			{
				final JSONObject jEndOfDayPaymentSummary = jEndOfDayPaymentSummaries.getJSONObject(i);
				paymentSummary = EndOfDayPaymentSummary.fromJSON(jEndOfDayPaymentSummary);
				if (paymentSummary != null)
					paymentSummaries.add(paymentSummary);
			}


		// Tax
		final JSONArray jEndOfDayTaxSummaries = obj.getJSONArray("taxSummaries");
		if (jEndOfDayTaxSummaries.length() != 0)
			for (int i = 0; i < jEndOfDayTaxSummaries.length(); i++)
			{
				final JSONObject jEndOfDayTaxSummary = jEndOfDayTaxSummaries.getJSONObject(i);
				taxSummary = EndOfDayTaxSummary.fromJSON(jEndOfDayTaxSummary);
				if (taxSummary != null)
					taxSummaries.add(taxSummary);
			}


		// POS
		final POS pos = new POS.Builder().id(obj.getString("pos")).build();


		final EndOfDayStatement endOfDayStatement = new EndOfDayStatement.Builder().deleted(
			obj.getBoolean("deleted"))
			.revision(obj.getLong("revision"))
			.id(obj.getString("uuid"))
			.finishTime(inputDf.parse(obj.getString("finishTime")))
			.pos(pos)
			.receiptCount(obj.getLong("receiptCount"))
			.zCount(obj.getLong("zCount"))
			.accountSummaries(endOfDayAccountSummaries)
			.cashierSummaries(cashierSummaries)
			.commodityGroupSummaries(commoditygroupSummaries)
			.customerGroupSummaries(customergroupSummaries)
			.paymentSummaries(paymentSummaries)
			.taxSummaries(taxSummaries)
			.build();

		return endOfDayStatement;
	}
}
