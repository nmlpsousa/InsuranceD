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

    public Coverable(int id, int coverableNo, String description, List<Claim> claimList, List<Coverage> coverageList, int policyId) {
        this.id = id;
        this.coverableNo = coverableNo;
        this.description = description;
        this.claimList = claimList;
        this.coverageList = coverageList;
        this.policyId = policyId;
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
        return this.policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public static final class Builder {
        private int id;

        private int coverableNo;

        private String description;

        private List<Claim> claimList;

        private List<Coverage> coverageList;

        private int policyId;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setCoverableNo(int coverableNo) {
            this.coverableNo = coverableNo;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setClaimList(List<Claim> claimList) {
            this.claimList = claimList;
            return this;
        }

        public Builder setCoverageList(List<Coverage> coverageList) {
            this.coverageList = coverageList;
            return this;
        }

        public Builder setPolicyId(int policyId) {
            this.policyId = policyId;
            return this;
        }

        public Coverable build() {
            return new Coverable(this.id, this.coverableNo, this.description, this.claimList, this.coverageList, this.policyId);
        }
    }
}