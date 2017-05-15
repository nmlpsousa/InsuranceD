package pt.insuranced.persistence.dao.sdk.interfaces;

import pt.insuranced.models.Client;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.Optional;

public interface ClientDao {
    Optional<Client> get(int id) throws InsuranceDException;

    Client insert(Client client) throws InsuranceDException;

    Client update(Client client) throws InsuranceDException;
}
