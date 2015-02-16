package domain;

import java.math.BigDecimal;


public class TaxPayments
{
	private String salesTax;
	private BigDecimal currentTaxRate;
	private BigDecimal amount;

	public TaxPayments(final String salesTax, final BigDecimal currentTaxRate,
		final BigDecimal amount)
	{
		super();
		this.salesTax = salesTax;
		this.currentTaxRate = currentTaxRate;
		this.amount = amount;
	}

	public String getSalesTax()
	{
		return salesTax;
	}

	public void setSalesTax(final String salesTax)
	{
		this.salesTax = salesTax;
	}

	public BigDecimal getCurrentTaxRate()
	{
		return currentTaxRate;
	}

	public void setCurrentTaxRate(final BigDecimal currentTaxRate)
	{
		this.currentTaxRate = currentTaxRate;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(final BigDecimal amount)
	{
		this.amount = amount;
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

		result = prime * result + ((this.salesTax == null) ? 0 : this.salesTax.hashCode());


		return result;
	}


}
