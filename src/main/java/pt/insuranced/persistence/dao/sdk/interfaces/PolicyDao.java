package pt.insuranced.persistence.dao.sdk.interfaces;

import pt.insuranced.models.Policy;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.List;

public interface PolicyDao extends GenericDao<Policy> {
    /**
     * Returns a list of {@code Policy} from the provided user, without fields from other tables (such as Coverables or Coverages)
     * @param userId the user ID
     * @return a list of Policy objects
     * @throws InsuranceDException if an exception occurs while performing this action
     */
    List<Policy> getPoliciesFromUser(int userId) throws InsuranceDException;
}