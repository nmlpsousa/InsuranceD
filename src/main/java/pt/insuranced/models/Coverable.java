package pt.insuranced.models;

import java.util.List;

public class Coverable {

    private long id;

    private String description;

    private List<Claim> claimList;

    private List<Coverage> coverageList;

    private long policyId;

    public Coverable() {
    }

    public Coverable(long id, String description, List<Claim> claimList, List<Coverage> coverageList, long policyId) {
        this.id = id;
        this.description = description;
        this.claimList = claimList;
        this.coverageList = coverageList;
        this.policyId = policyId;
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

    public long getId() {
        return this.id;
    }

    public long getPolicyId() {
        return this.policyId;
    }

    public void setPolicyId(long policyId) {
        this.policyId = policyId;
    }

    public static final class Builder {
        private long id;

        private String description;

        private List<Claim> claimList;

        private List<Coverage> coverageList;

        private long policyId;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setId(long id) {
            this.id = id;
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

        public Builder setPolicyId(long policyId) {
            this.policyId = policyId;
            return this;
        }

        public Coverable build() {
            return new Coverable(this.id, this.description, this.claimList, this.coverageList, this.policyId);
        }
    }
}