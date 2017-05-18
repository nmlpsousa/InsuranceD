package pt.insuranced.persistence.dao;

import pt.insuranced.models.ReserveLine;
import pt.insuranced.persistence.dao.sdk.interfaces.ReserveLineDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.Optional;

public class ReserveLineDaoImpl implements ReserveLineDao {

    @Override
    public Optional<ReserveLine> get(long id) throws InsuranceDException {
        return null;
    }

    @Override
    public ReserveLine insert(ReserveLine object) throws InsuranceDException {
        return null;
    }

    @Override
    public ReserveLine update(ReserveLine object) throws InsuranceDException {
        return null;
    }

}
