package pt.insuranced.services;

import mockit.Mock;
import mockit.MockUp;

import org.junit.Test;
import pt.insuranced.models.Claim;
import pt.insuranced.models.ReserveLine;
import pt.insuranced.persistence.dao.ClaimDaoImpl;
import pt.insuranced.sdk.enums.ClaimStatusEnum;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.fail;

public class ClaimServiceTest {

    @Test(expected = InsuranceDException.class)
    public void testReportClaimFail() throws InsuranceDException {
        new MockUp<ClaimDaoImpl>() {
            @Mock
            public Claim insert(Claim object) throws InsuranceDException {
                throw new InsuranceDException("An error occurred while reporting a claim.");
            }
        };
        String jsonInput = "{\"id\":\"0\"}";
        ClaimsService claimsService = new ClaimsService();
        claimsService.reportClaim(jsonInput);

        // If the method does not throw an exception, it means the test failed
        fail();
    }

    @Test
    public void testReportClaimPass() throws InsuranceDException {

        new MockUp<ClaimDaoImpl>() {
            @Mock
            public Claim insert(Claim object) throws InsuranceDException {
                return new Claim(1,
                        "Claim",
                        LocalDate.of(2017, 1, 30),
                        ClaimStatusEnum.DRAFT,
                        new ReserveLine(1, "ReserveLine", 2000.0, 1000.0),
                        1);
            }
        };

        String jsonInput = "{\"id\":\"0\"}";
        ClaimsService claimsService = new ClaimsService();
        String response = claimsService.reportClaim(jsonInput);

        System.out.println(response);
    }
    
    @Test
    public void testGetLastTwoClaimsForUser() throws InsuranceDException {

        new MockUp<ClaimDaoImpl>() {
            @Mock
            public List<Claim> getLastClaimsFromUser(int userId, int numberOfClaims) throws InsuranceDException {
                
            	List<Claim> claimsList = new ArrayList<Claim>();
            	claimsList.add(new Claim(1,
                        "Claim",
                        LocalDate.of(2017, 1, 30),
                        ClaimStatusEnum.DRAFT,
                        new ReserveLine(1, "ReserveLine", 2000.0, 1000.0),
                        1));
            	claimsList.add(new Claim(2,
                        "Claim",
                        LocalDate.of(2017, 2, 1),
                        ClaimStatusEnum.DRAFT,
                        new ReserveLine(1, "ReserveLine", 2000.0, 1000.0),
                        2));
            	claimsList.add(new Claim(3,
                        "Claim",
                        LocalDate.of(2017, 1, 27),
                        ClaimStatusEnum.DRAFT,
                        new ReserveLine(1, "ReserveLine", 2000.0, 1000.0),
                        3));
            	claimsList.add(new Claim(4,
                        "Claim",
                        LocalDate.of(2017, 1, 31),
                        ClaimStatusEnum.DRAFT,
                        new ReserveLine(1, "ReserveLine", 2000.0, 1000.0),
                        4));
                Collections.sort(claimsList, Claim.getCompByDate());
            	
                return new ArrayList<Claim>(claimsList.subList(0, 2));
            }
        };

        String jsonInput = "{\"id\":\"0\"}";
        ClaimsService claimsService = new ClaimsService();
        String response = claimsService.getLastClaimsFromUser(jsonInput);

        System.out.println(response);
    }
}
