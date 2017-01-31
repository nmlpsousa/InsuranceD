package pt.insuranced.models;

import java.time.LocalDate;

import pt.insuranced.sdk.enums.ClaimStatusEnum;

public class Claim {
	private int id;
	
	private int claimNo;
	
	private String description;
	
	private LocalDate incidentDate;
	
	private ClaimStatusEnum status;
	
	private ReserveLine reserveLine;

	public int getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(int claimNo) {
		this.claimNo = claimNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getIncidentDate() {
		return incidentDate;
	}

	public void setIncidentDate(LocalDate incidentDate) {
		this.incidentDate = incidentDate;
	}

	public ClaimStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ClaimStatusEnum status) {
		this.status = status;
	}

	public ReserveLine getReserveLine() {
		return reserveLine;
	}

	public void setReserveLine(ReserveLine reserveLine) {
		this.reserveLine = reserveLine;
	}

	public int getId() {
		return id;
	}
}
