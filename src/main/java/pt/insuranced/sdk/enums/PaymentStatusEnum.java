package pt.insuranced.sdk.enums;

public enum PaymentStatusEnum {
    OPEN(1, "Open"),
    CLOSED(2, "Closed");

    private final int code;

    private final String paymentStatus;

    private PaymentStatusEnum(int code, String paymentStatus) {
        this.code = code;
        this.paymentStatus = paymentStatus;
    }

    public int getCode() {
        return this.code;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }
}
