package pt.insuranced.persistence.dao;

import pt.insuranced.models.Coverable;
import pt.insuranced.persistence.dao.sdk.interfaces.CoverableDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.Optional;

public class CoverableDaoImpl implements CoverableDao {
    @Override
    public Optional<Coverable> get(long id) throws InsuranceDException {
        return null;
    }

    @Override
    public Coverable insert(Coverable coverable) throws InsuranceDException {
        return null;
    }

    @Override
    public Coverable update(Coverable coverable) throws InsuranceDException {
        return null;
    }
}
