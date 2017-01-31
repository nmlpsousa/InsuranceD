package pt.insuranced.models;

import java.time.LocalDate;
import java.util.List;

public class Policy {
	private int id;
	
	private int policyNo;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private Double premium;
	
	private List<Coverable> coverableList;
	
	private List<Coverage> coverage;
}
