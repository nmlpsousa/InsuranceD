package pt.insuranced.persistence.dao.sdk.interfaces;

import pt.insuranced.models.Client;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.Collection;

public interface ClientDao extends GenericDao<Client> {
    void bulkInsert(Collection<Client> clients) throws InsuranceDException;
}
