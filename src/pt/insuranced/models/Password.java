package pt.insuranced.models;

public class Password {
	private int id;
	
	private String hashedPassword;

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public int getId() {
		return id;
	}
	
	
}
