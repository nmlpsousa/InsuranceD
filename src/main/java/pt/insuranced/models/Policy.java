package pt.insuranced.models;

import java.time.LocalDate;
import java.util.List;

public class Policy {
    private long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Double premium;

    private List<Coverable> coverableList;

    private List<Coverage> coverageList;

    private long userId;

    public Policy() {
    }

    public Policy(long id, LocalDate startDate, LocalDate endDate, Double premium,
    			  List<Coverable> coverableList, List<Coverage> coverageList, long userId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.premium = premium;
        this.coverableList = coverableList;
        this.coverageList = coverageList;
        this.userId = userId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getPremium() {
        return this.premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public List<Coverable> getCoverableList() {
        return this.coverableList;
    }

    public void setCoverableList(List<Coverable> coverableList) {
        this.coverableList = coverableList;
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

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static final class Builder {
        private long id;

        private LocalDate startDate;

        private LocalDate endDate;

        private Double premium;

        private List<Coverable> coverableList;

        private List<Coverage> coverageList;

        private long userId;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setPremium(Double premium) {
            this.premium = premium;
            return this;
        }

        public Builder setCoverableList(List<Coverable> coverableList) {
            this.coverableList = coverableList;
            return this;
        }

        public Builder setCoverageList(List<Coverage> coverageList) {
            this.coverageList = coverageList;
            return this;
        }

        public Builder setUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public Policy build() {
            return new Policy(this.id, this.startDate, this.endDate,
            		          this.premium, this.coverableList, this.coverageList, this.userId);
        }
    }
}