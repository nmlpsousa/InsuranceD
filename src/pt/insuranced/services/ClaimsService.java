package pt.insuranced.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import pt.insuranced.models.Claim;
import pt.insuranced.persistence.dao.ClaimDaoImpl;
import pt.insuranced.persistence.dao.sdk.interfaces.ClaimDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.io.IOException;

public class ClaimsService {

    public String reportClaim(String jsonInput) throws InsuranceDException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Claim claim = objectMapper.readValue(jsonInput, Claim.class);

            ClaimDao claimDao = new ClaimDaoImpl();
            Claim newClaim = claimDao.insert(claim);

            return objectMapper.writeValueAsString(newClaim);

        } catch (IOException e) {
            throw new InsuranceDException("An error occurred while reporting a claim.", e);
        }
    }
}
