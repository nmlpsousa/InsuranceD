package pt.insuranced.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pt.insuranced.models.Client;
import pt.insuranced.models.Policy;
import pt.insuranced.persistence.dao.PolicyDaoImpl;
import pt.insuranced.persistence.dao.sdk.interfaces.PolicyDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class PolicyService {
    private static final ObjectMapper OBJECT_MAPPER;

    private PolicyDao policyDao;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public PolicyService() {
        this.policyDao = new PolicyDaoImpl();
    }

    /**
     * Gets a list of Policies information from a user
     *
     * @param jsonInput the JSON string containing the user ID
     * @return a JSON string containing the list of Policies of the user
     * @throws InsuranceDException if an error occurs while retrieving data from the DAO
     */
    public String getPoliciesFromUser(String jsonInput) throws InsuranceDException {
        try {
            Client client = OBJECT_MAPPER.readValue(jsonInput, Client.class);
            Long userId = client.getId();

            List<Policy> policyList = this.policyDao.getPoliciesFromUser(userId);
            return OBJECT_MAPPER.writeValueAsString(policyList);
        } catch (IOException exception) {
            throw new InsuranceDException("An error occurred while trying to retrieve the client details.", exception);
        }
    }

    /**
     * Gets detailed Policy info, including Coverable and Coverage details.
     *
     * @param jsonInput the JSON string containing the policy ID
     * @return a JSON string containing the detailed Policy information
     * @throws InsuranceDException if an error occurs while retrieving data from the DAO, or the policy does not exist
     */
    public String getPolicyDetails(String jsonInput) throws InsuranceDException {
        try {
            Policy policy = OBJECT_MAPPER.readValue(jsonInput, Policy.class);
            long policyId = policy.getId();

            Optional<Policy> policyDetailsOptional = this.policyDao.getExtendedPolicyInformation(policyId);
            if (!policyDetailsOptional.isPresent()) {
                throw new InsuranceDException("The policy does not exist.");
            }
            Policy policyDetails = policyDetailsOptional.get();
            return OBJECT_MAPPER.writeValueAsString(policyDetails);
        } catch (IOException exception) {
            throw new InsuranceDException("An error occurred while trying to retrieve the client details.", exception);
        }
    }
}
