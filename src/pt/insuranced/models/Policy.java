package pt.insuranced.models;

import java.time.LocalDate;
import java.util.List;

public class Policy {
	private int id;
	
	private int policyNo;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private Double premium;
	
	private List<Coverable> coverableList;
	
	private List<Coverage> coverage;

	public int getPolicyNo() {
		return policyNo;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

	public List<Coverable> getCoverableList() {
		return coverableList;
	}

	public void setCoverableList(List<Coverable> coverableList) {
		this.coverableList = coverableList;
	}

	public List<Coverage> getCoverage() {
		return coverage;
	}

	public void setCoverage(List<Coverage> coverage) {
		this.coverage = coverage;
	}

	public int getId() {
		return id;
	}
}
