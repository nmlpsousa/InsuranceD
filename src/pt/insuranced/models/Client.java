package pt.insuranced.models;

import java.util.List;

public class Client extends User {
	private List<Policy> policyList;

	public List<Policy> getPolicyList() {
		return policyList;
	}

	public void setPolicyList(List<Policy> policyList) {
		this.policyList = policyList;
	}
}
