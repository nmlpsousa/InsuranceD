package pt.insuranced.persistence.dao.sdk.interfaces;

import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.Optional;

public interface GenericDao<T> {
    Optional<T> get(int id) throws InsuranceDException;

    T insert(T object) throws InsuranceDException;

    T update(T object) throws InsuranceDException;
}
