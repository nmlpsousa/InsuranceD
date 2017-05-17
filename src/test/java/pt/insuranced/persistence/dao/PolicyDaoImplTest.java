package pt.insuranced.persistence.dao;

import java.time.LocalDate;

import org.junit.Ignore;
import org.junit.Test;

import pt.insuranced.models.Policy;
import pt.insuranced.persistence.dao.sdk.interfaces.PolicyDao;

public class PolicyDaoImplTest {

	@Test
	@Ignore
    public void testInsert() throws Exception {
        
		Policy policy = new Policy();
		policy.setUserId(1);
		policy.setStartDate(LocalDate.now());
		policy.setEndDate(LocalDate.now());
		policy.setPremium(1000.0);

        PolicyDao policyDao = new PolicyDaoImpl();
        policyDao.insert(policy);
    }
	
	@Test
	public void testGet() throws Exception {

        PolicyDao policyDao = new PolicyDaoImpl();
        policyDao.get(1);
    }
}
