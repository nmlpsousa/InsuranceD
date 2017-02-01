package pt.insuranced.persistence.dao;

import pt.insuranced.models.Policy;
import pt.insuranced.persistence.dao.sdk.interfaces.PolicyDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PolicyDaoImpl implements PolicyDao {
    private static Map<Integer, Policy> policyMap = new HashMap<>(10);

    @Override
    public Optional<Policy> getPolicy(int policyId) throws InsuranceDException {
        return Optional.ofNullable(policyMap.get(policyId));
    }

    @Override
    public Policy insertPolicy(int userId, Policy policy) throws InsuranceDException {
        if (policyMap.containsKey(policy.getId())) {
            throw new InsuranceDException("Unable to insert the policy: the key already exists.");
        }

        policyMap.put(policy.getId(), policy);
        return policy;
    }

    @Override
    public Policy updatePolicy(Policy policy) throws InsuranceDException {
        if (!policyMap.containsKey(policy.getId())) {
            throw new InsuranceDException("Unable to update the policy: the policy does not exist.");
        }

        policyMap.put(policy.getId(), policy);
        return policy;
    }

    @Override
    public List<Policy> getPoliciesFromUser(int userId) throws InsuranceDException {
        return Collections.emptyList();
    }
}
