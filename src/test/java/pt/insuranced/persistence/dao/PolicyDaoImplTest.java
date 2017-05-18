package pt.insuranced.persistence.dao;

import org.junit.Test;
import pt.insuranced.models.Policy;
import pt.insuranced.persistence.dao.sdk.interfaces.PolicyDao;

import java.time.LocalDate;
import java.util.Optional;

public class PolicyDaoImplTest {

    @Test
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

    @Test
    public void testUpdate() throws Exception {

        PolicyDao policyDao = new PolicyDaoImpl();

        Optional<Policy> pol = policyDao.get(1);

        if (pol.isPresent()) {
            Policy policy = pol.get();
            policy.setPremium(2000.0);
            policy.setEndDate(LocalDate.now().plusYears(1));

            policyDao.update(policy);
        }
    }

    @Test
    public void testGetPoliciesFromUser() throws Exception {

        PolicyDao policyDao = new PolicyDaoImpl();
        policyDao.getPoliciesFromUser(1);
    }
}
