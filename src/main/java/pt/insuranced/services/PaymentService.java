package pt.insuranced.services;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.insuranced.models.Payment;
import pt.insuranced.persistence.dao.PaymentDaoImpl;
import pt.insuranced.persistence.dao.sdk.interfaces.PaymentDao;
import pt.insuranced.sdk.exceptions.InsuranceDException;

public class PaymentService {
	private static final ObjectMapper OBJECT_MAPPER;
	
	private PaymentDao paymentDao;
	
    static {
        OBJECT_MAPPER = new ObjectMapper();
    }
    
    public PaymentService() {
    	this.paymentDao = new PaymentDaoImpl();
    }
    
    public String getPaymentDetails(String jsonInput) throws InsuranceDException {
        try {
            Payment payment = OBJECT_MAPPER.readValue(jsonInput, Payment.class);
            int paymentId = payment.getId();

            Optional<Payment> retrievedPaymentOptional = this.paymentDao.get(paymentId);
            if (!retrievedPaymentOptional.isPresent()) {
                throw new InsuranceDException("The payment details do not exist.");
            }
            
            Payment retrievedPayment = retrievedPaymentOptional.get();
            return OBJECT_MAPPER.writeValueAsString(retrievedPayment);
        } catch (IOException e) {
            throw new InsuranceDException("An error occurred while trying to retrieve the client details.", e);
        }
    }
    
    public String insertPayment(String jsonInput) throws InsuranceDException {
    	
    	try {
    		Payment newPayment = OBJECT_MAPPER.readValue(jsonInput, Payment.class);
    		//TODO: Validate if payment is valid.
    		Boolean paymentIsValid = isPaymentValid(newPayment, 
    				                                payment -> StringUtils.isNotEmpty(payment.getPayee()) 
    				                                && (payment.getAmmount() > 0));
    		if (!paymentIsValid) {
                throw new InsuranceDException("The payment is invalid.");
            }
    		Payment insertedPayment = this.paymentDao.insert(newPayment);
            return OBJECT_MAPPER.writeValueAsString(insertedPayment);
    		
    	}
    	catch (IOException e) {
    		throw new InsuranceDException("An error occurred while trying to insert a payment.", e);
    	}
    }
    
    private static Boolean isPaymentValid(Payment payment, Predicate<Payment> predicate) {
        return predicate.test(payment);
    }

}
