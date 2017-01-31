package pt.insuranced.models;

import java.time.LocalDate;
import java.util.List;

import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;

public class User {
	private int id;
	
	private String username;
	
	private Password password;
	
	private PersonalIdentification personalIdentification;
	
	private List<Password> oldPasswords;	
	
	private LocalDate lastPasswordChangeDate;
	
	private UserTypeEnum userType;
	
	private UserStatusEnum userStatus;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	public PersonalIdentification getPersonalIdentification() {
		return personalIdentification;
	}

	public void setPersonalIdentification(PersonalIdentification personalIdentification) {
		this.personalIdentification = personalIdentification;
	}

	public List<Password> getOldPasswords() {
		return oldPasswords;
	}

	public void setOldPasswords(List<Password> oldPasswords) {
		this.oldPasswords = oldPasswords;
	}

	public LocalDate getLastPasswordChangeDate() {
		return lastPasswordChangeDate;
	}

	public void setLastPasswordChangeDate(LocalDate lastPasswordChangeDate) {
		this.lastPasswordChangeDate = lastPasswordChangeDate;
	}

	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}

	public UserStatusEnum getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatusEnum userStatus) {
		this.userStatus = userStatus;
	}

	public int getId() {
		return id;
	}	
}
