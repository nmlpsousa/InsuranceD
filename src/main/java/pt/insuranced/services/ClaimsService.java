package pt.insuranced.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pt.insuranced.models.Claim;
import pt.insuranced.persistence.dao.ClaimDaoImpl;
import pt.insuranced.persistence.dao.sdk.interfaces.ClaimDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.io.IOException;
import java.util.List;

public class ClaimsService {

    public String reportClaim(String jsonInput) throws InsuranceDException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            Claim claim = objectMapper.readValue(jsonInput, Claim.class);

            ClaimDao claimDao = new ClaimDaoImpl();
            Claim newClaim = claimDao.insert(claim);

            return objectMapper.writeValueAsString(newClaim);

        } catch (IOException e) {
            throw new InsuranceDException("An error occurred while reporting a claim.", e);
        }
    }

    //public List<Claim> getLastClaimsFromUser(int userId, int numberOfClaims)
    public String getLastClaimsFromUser(String jsonInput) throws InsuranceDException {

        try {

            ObjectMapper objectMapper = new ObjectMapper();

            ClaimDao claimDao = new ClaimDaoImpl();
            List<Claim> claimList = claimDao.getLastClaimsFromUser(0, 0);

            return objectMapper.writeValueAsString(claimList);

        } catch (JsonProcessingException e) {
            throw new InsuranceDException("An error occurred while reporting a claim.", e);
        }
    }
}
