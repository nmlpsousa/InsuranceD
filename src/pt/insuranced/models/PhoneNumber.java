package pt.insuranced.models;

public class PhoneNumber {
	private String id;
	
	private String prefix;
	
	private int number;
	
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return new StringBuilder(prefix)
				.append(number)
				.toString();
	}
	

}
