package pt.insuranced.models;

import java.util.List;

public class Coverable {
	
	private int id;

	private int coverableNo;
	
	private String description;
	
	private List<Claim> claimList;
	
	private List<Coverage> coverageList;

	public int getCoverableNo() {
		return coverableNo;
	}

	public void setCoverableNo(int coverableNo) {
		this.coverableNo = coverableNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Claim> getClaimList() {
		return claimList;
	}

	public void setClaimList(List<Claim> claimList) {
		this.claimList = claimList;
	}

	public List<Coverage> getCoverageList() {
		return coverageList;
	}

	public void setCoverageList(List<Coverage> coverageList) {
		this.coverageList = coverageList;
	}

	public int getId() {
		return id;
	}
}
