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

        if (userMap.containsKey(user.getId())) {
            throw new InsuranceDException("Cannot insert user. Duplicated keys.");
        }

        userMap.put(user.getId(), user);

        return user;
    }

    @Override
    public Optional<AbstractUser> get(int userId) throws InsuranceDException {

        return Optional.ofNullable(userMap.get(userId));

    }

    @Override
    public AbstractUser update(AbstractUser user) throws InsuranceDException {

        if (userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
            return user;
        }

        throw new InsuranceDException("Trying to update a user that doesn't exist.");
    }

}
