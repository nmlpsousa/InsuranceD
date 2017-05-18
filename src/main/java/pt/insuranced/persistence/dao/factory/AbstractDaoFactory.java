package pt.insuranced.persistence.dao.factory;

import pt.insuranced.persistence.dao.sdk.interfaces.GenericDao;

public interface AbstractDaoFactory {
    GenericDao getDao(String type);
}
