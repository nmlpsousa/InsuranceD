package pt.insuranced.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Test;
import pt.insuranced.models.Payment;
import pt.insuranced.persistence.dao.PaymentDaoImpl;
import pt.insuranced.sdk.enums.PaymentStatusEnum;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class PaymentServiceTest {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    @Test(expected = InsuranceDException.class)
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

        String jsonInput = "{\"id\":1,\"paymentNo\":1,\"coverage\":null,\"payee\":\"Gus\",\"ammount\":100.0,\"status\":\"CLOSED\"}";
        PaymentService paymentService = new PaymentService();
        paymentService.insertPayment(jsonInput);

        fail("The test should have thrown an exception, since the payment is invalid.");
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

        String jsonInput = "{\"id\":1,\"paymentNo\":1,\"coverage\":null,\"payee\":\"Gus\",\"ammount\":100.0,\"status\":\"OPEN\"}";
        PaymentService paymentService = new PaymentService();
        String response = paymentService.insertPayment(jsonInput);

        try {
            Payment paymentResponse = OBJECT_MAPPER.readValue(response, Payment.class);

            assertNotNull(paymentResponse);
            assertEquals(PaymentStatusEnum.OPEN, paymentResponse.getStatus());
            assertEquals((Double) 100.0, paymentResponse.getAmmount());
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }
}
