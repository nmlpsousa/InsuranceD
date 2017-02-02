package pt.insuranced.persistence.dao.sdk.interfaces;

import pt.insuranced.models.Policy;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.List;

public interface PolicyDao extends GenericDao<Policy> {
    List<Policy> getPoliciesFromUser(int userId) throws InsuranceDException;
}
