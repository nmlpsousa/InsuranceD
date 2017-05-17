package pt.insuranced.persistence.dao;

import pt.insuranced.models.Coverage;
import pt.insuranced.persistence.dao.sdk.interfaces.CoverageDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.Optional;

public class CoverageDaoImpl implements CoverageDao {
    @Override
    public Optional<Coverage> get(long id) throws InsuranceDException {
        return null;
    }

    @Override
    public Coverage insert(Coverage coverage) throws InsuranceDException {
        return null;
    }

    @Override
    public Coverage update(Coverage coverage) throws InsuranceDException {
        return null;
    }
}
