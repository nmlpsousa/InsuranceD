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

    private int userId;

    public Policy() {
    }

    public Policy(int id, int policyNo, LocalDate startDate, LocalDate endDate, Double premium, List<Coverable> coverableList, List<Coverage> coverage) {
        this.id = id;
        this.policyNo = policyNo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.premium = premium;
        this.coverableList = coverableList;
        this.coverage = coverage;
    }

    public int getPolicyNo() {
        return this.policyNo;
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

    public List<Coverage> getCoverage() {
        return this.coverage;
    }

    public void setCoverage(List<Coverage> coverage) {
        this.coverage = coverage;
    }

    public int getId() {
        return this.id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
