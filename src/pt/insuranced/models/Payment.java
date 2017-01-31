package pt.insuranced.models;

import pt.insuranced.sdk.enums.PaymentStatusEnum;

public class Payment {
	private int id;
	
	private int paymentNo;
	
	private Coverage coverage;
	
	// TODO possible improvement, having its own structure in the DB
	private String payee;
	
	private Double ammount;
	
	private PaymentStatusEnum status;

	public int getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(int paymentNo) {
		this.paymentNo = paymentNo;
	}

	public Coverage getCoverage() {
		return coverage;
	}

	public void setCoverage(Coverage coverage) {
		this.coverage = coverage;
	}

	public int getId() {
		return id;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public Double getAmmount() {
		return ammount;
	}

	public void setAmmount(Double ammount) {
		this.ammount = ammount;
	}

	public PaymentStatusEnum getStatus() {
		return status;
	}

	public void setStatus(PaymentStatusEnum status) {
		this.status = status;
	}
}
