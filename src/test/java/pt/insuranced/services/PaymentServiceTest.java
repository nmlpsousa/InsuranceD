package pt.insuranced.services;

import java.time.LocalDate;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;
import pt.insuranced.models.Claim;
import pt.insuranced.models.Payment;
import pt.insuranced.models.ReserveLine;
import pt.insuranced.persistence.dao.ClaimDaoImpl;
import pt.insuranced.persistence.dao.PaymentDaoImpl;
import pt.insuranced.sdk.enums.ClaimStatusEnum;
import pt.insuranced.sdk.enums.PaymentStatusEnum;
import pt.insuranced.sdk.exceptions.InsuranceDException;

public class PaymentServiceTest {
	
	@Test
    public void testSubmitPaymentFail() throws InsuranceDException {

        new MockUp<PaymentDaoImpl>() {
            @Mock
            public Payment insert(Payment object) throws InsuranceDException {
                return new Payment(1,
                        1,
                        null,
                        "Gus",
                        100.0,
                        PaymentStatusEnum.CLOSED
                        );
            }
        };

        String jsonInput = "{\"id\":\"0\"}";
        PaymentService paymentService = new PaymentService();
        String response = paymentService.insertPayment(jsonInput);

        System.out.println(response);
    }
	
	@Test
    public void testSubmitPaymentPass() throws InsuranceDException {

        new MockUp<PaymentDaoImpl>() {
            @Mock
            public Payment insert(Payment object) throws InsuranceDException {
                return new Payment(1,
                        1,
                        null,
                        "Gus",
                        100.0,
                        PaymentStatusEnum.OPEN
                        );
            }
        };

        String jsonInput = "{\"id\":\"0\"}";
        PaymentService paymentService = new PaymentService();
        String response = paymentService.insertPayment(jsonInput);

        System.out.println(response);
    }

}
