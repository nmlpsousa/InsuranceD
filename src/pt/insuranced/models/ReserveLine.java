package pt.insuranced.models;

public class ReserveLine {
	private int id;
	
	private String description;
	
	private Double limit;
	
	private Double usedFunds;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getLimit() {
		return limit;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

	public Double getUsedFunds() {
		return usedFunds;
	}

	public void setUsedFunds(Double usedFunds) {
		this.usedFunds = usedFunds;
	}

	public int getId() {
		return id;
	}
}
