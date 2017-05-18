package pt.insuranced.persistence.dao;

import pt.insuranced.models.AbstractUser;
import pt.insuranced.persistence.dao.sdk.interfaces.UserDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static Map<Integer, AbstractUser> userMap = new HashMap<>();

    @Override
    public AbstractUser insert(AbstractUser user) throws InsuranceDException {
        return null;
    }

    @Override
    public Optional<AbstractUser> get(long userId) throws InsuranceDException {
        return null;
    }

    @Override
    public AbstractUser update(AbstractUser user) throws InsuranceDException {
        return null;
    }

}
