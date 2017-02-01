package pt.insuranced.sdk.enums;

public enum UserStatusEnum {
    ACTIVE(1, "Active"),
    INACTIVE(2, "Inactive"),
    PENDING(3, "Pending");
	
	private final int code;
	private final String userStatus;
	
	private UserStatusEnum(int code, String userStatus){
        this.code = code;
		this.userStatus = userStatus;
    }
	
	public int getCode() {
		return this.code;
	}
	
	public String getUserStatus() {
        return this.userStatus;
    }
}