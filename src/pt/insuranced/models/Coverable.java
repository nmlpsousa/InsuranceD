package pt.insuranced.models;

import java.util.List;

public class Coverable {

    private int id;

    private int coverableNo;

    private String description;

    private List<Claim> claimList;

    private List<Coverage> coverageList;
    
    private int policyId;

    public Coverable() {
    }

    public Coverable(int id, int coverableNo, String description, List<Claim> claimList, List<Coverage> coverageList) {
        this.id = id;
        this.coverableNo = coverableNo;
        this.description = description;
        this.claimList = claimList;
        this.coverageList = coverageList;
    }

    public int getCoverableNo() {
        return this.coverableNo;
    }

    public void setCoverableNo(int coverableNo) {
        this.coverableNo = coverableNo;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Claim> getClaimList() {
        return this.claimList;
    }

    public void setClaimList(List<Claim> claimList) {
        this.claimList = claimList;
    }

    public List<Coverage> getCoverageList() {
        return this.coverageList;
    }

    public void setCoverageList(List<Coverage> coverageList) {
        this.coverageList = coverageList;
    }

    public int getId() {
        return this.id;
    }

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}
}
