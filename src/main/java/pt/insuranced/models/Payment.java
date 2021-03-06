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

    public Payment() {
    }

    public Payment(int id, int paymentNo, Coverage coverage, String payee, Double ammount, PaymentStatusEnum status) {
        this.id = id;
        this.paymentNo = paymentNo;
        this.coverage = coverage;
        this.payee = payee;
        this.ammount = ammount;
        this.status = status;
    }

    public int getPaymentNo() {
        return this.paymentNo;
    }

    public void setPaymentNo(int paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Coverage getCoverage() {
        return this.coverage;
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    public int getId() {
        return this.id;
    }

    public String getPayee() {
        return this.payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public Double getAmmount() {
        return this.ammount;
    }

    public void setAmmount(Double ammount) {
        this.ammount = ammount;
    }

    public PaymentStatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(PaymentStatusEnum status) {
        this.status = status;
    }
}
