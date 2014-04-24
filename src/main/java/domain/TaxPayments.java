package domain;


public class TaxPayments {
	private String salesTax;
	private double currentTaxRate;
	private double amount;
	
	public TaxPayments(String salesTax, double currentTaxRate,double amount)
	{
		super();
		this.salesTax = salesTax;
		this.currentTaxRate = currentTaxRate;
		this.amount = amount;
	}

	public String getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(String salesTax) {
		this.salesTax = salesTax;
	}

	public double getCurrentTaxRate() {
		return currentTaxRate;
	}

	public void setCurrentTaxRate(double currentTaxRate) {
		this.currentTaxRate = currentTaxRate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}