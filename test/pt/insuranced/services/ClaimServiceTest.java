import java.time.LocalDate;
import java.util.Optional;

import mockit.Mock;
import mockit.MockUp;
import pt.insuranced.models.AbstractUser;
import pt.insuranced.models.Claim;
import pt.insuranced.models.Client;
import pt.insuranced.models.Password;
import pt.insuranced.models.ReserveLine;
import pt.insuranced.persistence.dao.ClaimDaoImpl;
import pt.insuranced.sdk.enums.ClaimStatusEnum;
import pt.insuranced.sdk.enums.UserStatusEnum;
import pt.insuranced.sdk.enums.UserTypeEnum;
import pt.insuranced.sdk.exceptions.InsuranceDException;
import pt.insuranced.services.ClaimsService;

public class ClaimServiceTest {

	@Test(expected = InsuranceDException.class)
    public void testReportClaimFail() throws InsuranceDException {
        new MockUp<ClaimDaoImpl>() {
            @Mock
            public Claim create(Claim claim) {
                return new InsuranceDException("An error occurred while reporting a claim.", e);
            }
        };
        String jsonInput = "{\"id\":\"0\"}";
        ClaimsService claimsService = new ClaimsService();
        String response = claimsService.reportClaim(jsonInput);

        // If the method does not throw an exception, it means the test failed
        fail();
    }
	
	@Test
    public void testReportClaimPass() throws InsuranceDException {
		
		new MockUp<ClaimDaoImpl>() {
            @Mock
            public Claim create(Claim claim) {
                return new Claim(1,
                				 1,
                				 "Claim",
                				 LocalDate.of(2017, 1, 30),
                				 ClaimStatusEnum.DRAFT,
                				 new ReserveLine(1, "ReserveLine", 2000.0, 1000.0);
            }
        };
        
        String jsonInput = "{\"id\":\"0\"}";
        ClaimsService claimsService = new ClaimsService();
        String response = claimsService.reportClaim(jsonInput);

        System.out.println(response);
        
        // If the method does not throw an exception, it means the test failed
        fail();
    }
	
}
