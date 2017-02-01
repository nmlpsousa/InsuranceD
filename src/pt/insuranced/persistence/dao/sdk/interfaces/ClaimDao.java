package pt.insuranced.persistence.dao.sdk.interfaces;

import pt.insuranced.models.Claim;

import java.util.List;

public interface ClaimDao {
    List<Claim> getLastClaimsFromUser(int userId, int numberOfClaims);
}
