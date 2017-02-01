package pt.insuranced.persistence.dao;

import pt.insuranced.models.Claim;
import pt.insuranced.persistence.dao.sdk.interfaces.ClaimDao;

import java.util.ArrayList;
import java.util.List;

public class ClaimDaoImpl implements ClaimDao {
    @Override
    public List<Claim> getLastClaimsFromUser(int userId, int numberOfClaims) {
        return new ArrayList<>(0);
    }
}
