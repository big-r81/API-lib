package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class FullReceipt
{
	private Receipt receipt;
	private Collection<AccountTransaction> accountTransaction;
	private Collection<Payment> payment;
	private Collection<Sale> sale;

	public FullReceipt(final Builder builder)
	{
		this.receipt = builder.receipt;
		this.accountTransaction = builder.accountTransaction;
		this.payment = builder.payment;
		this.sale = builder.sale;

	}

	public static class Builder
	{
		private Receipt receipt;
		private Collection<AccountTransaction> accountTransaction;
		private Collection<Payment> payment;
		private Collection<Sale> sale;


		public Builder()
		{

		}

		public Builder receipt(final Receipt receipt)
		{
			this.receipt = receipt;
			return this;
		}

		public Builder accountTransaction(final AccountTransaction accountTransaction)
		{
			this.accountTransaction.add(accountTransaction);
			return this;
		}

		public Builder accountTransaction(final Collection<AccountTransaction> col)
		{
			this.accountTransaction = col;
			return this;
		}

		public Builder payment(final Payment payment)
		{
			this.payment.add(payment);
			return this;
		}

		public Builder payment(final Collection<Payment> col)
		{
			this.payment = col;
			return this;
		}

		public Builder sale(final Sale sale)
		{
			this.sale.add(sale);
			return this;
		}

		public Builder sale(final Collection<Sale> col)
		{
			this.sale = col;
			return this;
		}


		public FullReceipt build()
		{
			return new FullReceipt(this);
		}
	}

	public static FullReceipt fromJSON(JSONObject obj) throws JSONException
	{
		if (obj.has("result") && obj.getString("result") != null)
			obj = obj.getJSONObject("result");


		final FullReceipt fullReceipt = new FullReceipt.Builder().build();

		// receipt
		final JSONObject jReceipt = obj.getJSONObject("receipt");
		final Receipt receipt = Receipt.fromJSON(jReceipt);

		final POS pos = new POS.Builder(null).uuid(jReceipt.getString("pos"))
			.number(jReceipt.getString("posNr"))
			.build();
		receipt.setPos(pos);

		final OrganizationalUnit organizationalUnit = new OrganizationalUnit.Builder(null).uuid(
			jReceipt.getString("organizationalUnit"))
			.number(jReceipt.getString("organizationalUnitNr"))
			.build();
		receipt.setOrganizationalUnit(organizationalUnit);

		if (!obj.isNull("customer"))
		{
			final Customer customer = new Customer.Builder().uuid(jReceipt.getString("customer"))
				.number(jReceipt.getString("customerNr"))
				.zipCode("customerZip")
				.build();
			receipt.setCustomer(customer);
		}


		fullReceipt.setReceipt(receipt);

		// accountTransaction
		if (!obj.isNull("accountTransactions"))
		{
			final JSONArray jAAccountTransaction = obj.getJSONArray("accountTransactions");
			for (int i = 0; i <= jAAccountTransaction.length() - 1; i++)
			{
				final JSONObject jAccountTransaction = jAAccountTransaction.getJSONObject(i);
				final AccountTransaction accountTransaction = AccountTransaction.fromJSON(jAccountTransaction);
				final Account account = new Account.Builder().uuid(
					jAccountTransaction.getString("account"))
					.number(jAccountTransaction.getString("accountNr"))
					.name(jAccountTransaction.getString("accountDescription"))
					.build();
				accountTransaction.setAccount(account);
				fullReceipt.addAccountTransaction(accountTransaction);
			}
		}

		// payment
		if (!obj.isNull("payments"))
		{
			final JSONArray jAPayment = obj.getJSONArray("payments");
			for (int i = 0; i <= jAPayment.length() - 1; i++)
			{
				final JSONObject jPayment = jAPayment.getJSONObject(i);
				final Payment payment = Payment.fromJSON(jPayment);
				final Currency currency = new Currency.Builder(null).uuid(
					jPayment.getString("currency"))
					.key(jPayment.getString("currencyKey"))
					.build();
				payment.setCurrency(currency);
				final PaymentMethods paymentMethods = new PaymentMethods.Builder(
					jPayment.getString("paymentMethodName")).uuid(
					jPayment.getString("paymentMethod"))
					.number(jPayment.getString("paymentMethodNr"))
					.build();
				payment.setPaymentMethod(paymentMethods);
				fullReceipt.addPayment(payment);
			}
		}

		// sale
		if (!obj.isNull("sales"))
		{
			final JSONArray jASale = obj.getJSONArray("sales");
			for (int i = 0; i <= jASale.length() - 1; i++)
			{
				final JSONObject jSale = jASale.getJSONObject(i);
				final Sale sale = Sale.fromJSON(jSale);
				final Product product = new Product.Builder(null).number(
					jSale.getString("articleNr"))
					.codes(new Product_Code(jSale.getString("articleEAN"), new BigDecimal(1)))
					.uuid(jSale.getString("article"))
					.build();
				sale.setArticle(product);
				fullReceipt.addSale(sale);
			}
		}

		return fullReceipt;


	}

	public Receipt getReceipt()
	{
		return receipt;
	}

	public void setReceipt(final Receipt receipt)
	{
		this.receipt = receipt;
	}

	public Collection<AccountTransaction> getAccountTransaction()
	{
		return accountTransaction;
	}

	public void setAccountTransaction(final Collection<AccountTransaction> accountTransaction)
	{
		this.accountTransaction = accountTransaction;
	}

	public void addAccountTransaction(final AccountTransaction accountTransaction)
	{
		if (this.accountTransaction == null)
			this.accountTransaction = new ArrayList<AccountTransaction>();
		this.accountTransaction.add(accountTransaction);
	}

	public Collection<Payment> getPayment()
	{
		return payment;
	}

	public void setPayment(final Collection<Payment> payment)
	{
		this.payment = payment;
	}

	public void addPayment(final Payment payment)
	{
		if (this.payment == null)
			this.payment = new ArrayList<Payment>();
		this.payment.add(payment);
	}

	public Collection<Sale> getSale()
	{
		return sale;
	}

	public void setSale(final Collection<Sale> sale)
	{
		this.sale = sale;
	}

	public void addSale(final Sale sale)
	{
		if (this.sale == null)
			this.sale = new ArrayList<Sale>();
		this.sale.add(sale);
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
		result = prime * result + ((this.receipt == null) ? 0 : this.receipt.hashCode());
		result = prime * result +
			((this.accountTransaction == null) ? 0 : this.accountTransaction.hashCode());
		result = prime * result + ((this.payment == null) ? 0 : this.payment.hashCode());
		result = prime * result + ((this.sale == null) ? 0 : this.sale.hashCode());

		return result;
	}

}