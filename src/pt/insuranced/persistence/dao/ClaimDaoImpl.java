package pt.insuranced.persistence.dao;

import pt.insuranced.models.Claim;
import pt.insuranced.persistence.dao.sdk.interfaces.ClaimDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClaimDaoImpl implements ClaimDao {
    @Override
    public List<Claim> getLastClaimsFromUser(int userId, int numberOfClaims) {
        return new ArrayList<>(0);
    }

	@Override
	public Optional<Claim> get(int id) throws InsuranceDException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Claim insert(Claim object) throws InsuranceDException {
		return object;
	}

	@Override
	public Claim update(Claim object) throws InsuranceDException {
		return object;
	}
}
