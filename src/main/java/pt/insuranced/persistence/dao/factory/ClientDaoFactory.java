package pt.insuranced.persistence.dao.factory;

import pt.insuranced.persistence.dao.ClientDaoImpl;
import pt.insuranced.persistence.dao.sdk.interfaces.ClientDao;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ClientDaoFactory implements AbstractDaoFactory {
    @Override
    public ClientDao getDao(String type) {
        if ("postgres".equals(type)) {
            return new ClientDaoImpl();
        }

        throw new NotImplementedException();
    }
}
