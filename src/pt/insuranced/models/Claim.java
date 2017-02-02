package pt.insuranced.models;

import pt.insuranced.sdk.enums.ClaimStatusEnum;

import java.time.LocalDate;

public class Claim {
    private int id;

    private int claimNo;

    private String description;

    private LocalDate incidentDate;

    private ClaimStatusEnum status;

    private ReserveLine reserveLine;

    private int coverableId;

    public Claim() {
    }

    public Claim(int id, int claimNo, String description, LocalDate incidentDate, ClaimStatusEnum status, ReserveLine reserveLine) {
        this.id = id;
        this.claimNo = claimNo;
        this.description = description;
        this.incidentDate = incidentDate;
        this.status = status;
        this.reserveLine = reserveLine;
    }

    public int getClaimNo() {
        return this.claimNo;
    }

    public void setClaimNo(int claimNo) {
        this.claimNo = claimNo;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getIncidentDate() {
        return this.incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public ClaimStatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(ClaimStatusEnum status) {
        this.status = status;
    }

    public ReserveLine getReserveLine() {
        return this.reserveLine;
    }

    public void setReserveLine(ReserveLine reserveLine) {
        this.reserveLine = reserveLine;
    }

    public int getId() {
        return this.id;
    }

    public int getCoverableId() {
        return coverableId;
    }

    public void setCoverableId(int coverableId) {
        this.coverableId = coverableId;
    }
}
