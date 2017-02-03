package pt.insuranced.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Before;
import org.junit.Test;
import pt.insuranced.models.Coverable;
import pt.insuranced.models.Coverage;
import pt.insuranced.models.Policy;
import pt.insuranced.persistence.dao.PolicyDaoImpl;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class PolicyServiceTest {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private static Policy exampleSimplePolicy;

    private static Policy examplePolicyWithDetails;

    @Before
    public void setUp() {
        exampleSimplePolicy = Policy.Builder.newBuilder()
                .setId(0)
                .setPolicyNo(100001)
                .setStartDate(LocalDate.of(2016, 6, 1))
                .setEndDate(LocalDate.of(2016, 5, 31))
                .setPremium(521.79)
                .build();

        Coverage coverage1 = Coverage.Builder.newBuilder()
                .setId(2)
                .setCoverageCode(76)
                .setDescription("Glass Coverage")
                .setLimit(400.0)
                .setPremium(16.73)
                .build();
        Coverage coverage2 = Coverage.Builder.newBuilder()
                .setId(7)
                .setCoverageCode(43)
                .setDescription("Vandalism")
                .setLimit(250.0)
                .setPremium(21.98)
                .build();

        Coverage coverage3 = Coverage.Builder.newBuilder()
                .setId(21)
                .setCoverageCode(32)
                .setDescription("Personal Liability")
                .setLimit(6_000_000.0)
                .setPremium(76.01)
                .build();

        Coverable coverable1 = Coverable.Builder.newBuilder()
                .setId(0)
                .setCoverableNo(320)
                .setDescription("A house in the middle of the street")
                .setPolicyId(0)
                .setCoverageList(Arrays.asList(coverage1, coverage2))
                .build();

        examplePolicyWithDetails = Policy.Builder.newBuilder()
                .setId(0)
                .setPolicyNo(100001)
                .setStartDate(LocalDate.of(2016, 6, 1))
                .setEndDate(LocalDate.of(2016, 5, 31))
                .setPremium(521.79)
                .setCoverableList(Arrays.asList(coverable1))
                .setCoverageList(Arrays.asList(coverage3))
                .build();
    }

    @Test
    public void testGetPolicyDetailsSuccess() throws InsuranceDException {
        new MockUp<PolicyDaoImpl>() {
            @Mock
            public Optional<Policy> getExtendedPolicyInformation(int policyId) throws InsuranceDException {
                return Optional.of(examplePolicyWithDetails);
            }
        };

        String jsonInput = "{\"id\":\"0\"}";
        PolicyService policyService = new PolicyService();
        String response = policyService.getPolicyDetails(jsonInput);

        try {
            Policy retrievedPolicy = OBJECT_MAPPER.readValue(response, Policy.class);
            assertNotNull(retrievedPolicy);
            assertEquals(1, retrievedPolicy.getCoverageList().size());
            assertEquals(1, retrievedPolicy.getCoverableList().size());
            assertEquals(2, retrievedPolicy.getCoverableList().get(0).getCoverageList().size());
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }

    @Test(expected = InsuranceDException.class)
    public void testGetPolicyDetailsNonexistent() throws InsuranceDException {
        new MockUp<PolicyDaoImpl>() {
            @Mock
            public Optional<Policy> getExtendedPolicyInformation(int policyId) throws InsuranceDException {
                return Optional.empty();
            }
        };

        String jsonInput = "{\"id\":\"0\"}";
        PolicyService policyService = new PolicyService();
        String response = policyService.getPolicyDetails(jsonInput);

        fail("The test should have thrown an InsuranceDException, because the policy did not exist.");
    }
}