package pt.insuranced.persistence.dao.sdk.interfaces;

import pt.insuranced.models.Policy;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.List;
import java.util.Optional;

public interface PolicyDao {
    Optional<Policy> getPolicy(int policyId) throws InsuranceDException;

    Policy insertPolicy(int userId, Policy policy) throws InsuranceDException;

    Policy updatePolicy(Policy policy) throws InsuranceDException;

    List<Policy> getPoliciesFromUser(int userId) throws InsuranceDException;
}
