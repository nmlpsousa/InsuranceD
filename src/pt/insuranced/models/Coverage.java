package pt.insuranced.models;

public class Coverage {
	private int id;
	
	private int coverageCode;
	
	private Double limit;
	
	private Double premium;
	
	private String description;

	public int getCoverageCode() {
		return coverageCode;
	}

	public void setCoverageCode(int coverageCode) {
		this.coverageCode = coverageCode;
	}

	public Double getLimit() {
		return limit;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}
}
