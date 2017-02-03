package pt.insuranced.persistence.dao;

import pt.insuranced.models.Payment;
import pt.insuranced.persistence.dao.sdk.interfaces.PaymentDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.util.Optional;

public class PaymentDaoImpl implements PaymentDao {
    @Override
    public Optional<Payment> get(int id) throws InsuranceDException {
        return null;
    }

    @Override
    public Payment insert(Payment payment) throws InsuranceDException {
        return null;
    }

    @Override
    public Payment update(Payment payment) throws InsuranceDException {
        return null;
    }
}
