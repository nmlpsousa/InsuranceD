package pt.insuranced.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mockit.Mock;
import mockit.MockUp;
import org.junit.BeforeClass;
import org.junit.Test;
import pt.insuranced.models.Coverable;
import pt.insuranced.models.Coverage;
import pt.insuranced.models.Policy;
import pt.insuranced.persistence.dao.PolicyDaoImpl;
import pt.insuranced.sdk.exceptions.InsuranceDException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PolicyServiceTest {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    private static Policy examplePolicyWithDetails;

    private static List<Policy> simplePolicyList;

    @BeforeClass
    public static void setUp() {
        Policy exampleSimplePolicy1 = Policy.Builder.newBuilder()
                .setId(0)
                .setStartDate(LocalDate.of(2016, 6, 1))
                .setEndDate(LocalDate.of(2017, 5, 31))
                .setPremium(521.79)
                .build();

        Policy exampleSimplePolicy2 = Policy.Builder.newBuilder()
                .setId(1)
                .setStartDate(LocalDate.of(2016, 4, 1))
                .setEndDate(LocalDate.of(2017, 3, 31))
                .setPremium(323.59)
                .build();

        simplePolicyList = new ArrayList<>(2);
        simplePolicyList.add(exampleSimplePolicy1);
        simplePolicyList.add(exampleSimplePolicy2);

        Coverage coverage1 = Coverage.Builder.newBuilder()
                .setId(2)
                .setCoverableId(76)
                .setDescription("Glass Coverage")
                .setLimit(400.0)
                .setPremium(16.73)
                .build();
        Coverage coverage2 = Coverage.Builder.newBuilder()
                .setId(7)
                .setCoverableId(43)
                .setDescription("Vandalism")
                .setLimit(250.0)
                .setPremium(21.98)
                .build();

        Coverage coverage3 = Coverage.Builder.newBuilder()
                .setId(21)
                .setCoverableId(32)
                .setDescription("Personal Liability")
                .setLimit(6_000_000.0)
                .setPremium(76.01)
                .build();

        Coverable coverable1 = Coverable.Builder.newBuilder()
                .setId(0)
                .setDescription("A house in the middle of the street")
                .setPolicyId(0)
                .setCoverageList(Arrays.asList(coverage1, coverage2))
                .build();

        examplePolicyWithDetails = Policy.Builder.newBuilder()
                .setId(0)
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
        policyService.getPolicyDetails(jsonInput);

        fail("The test should have thrown an InsuranceDException, because the policy did not exist.");
    }

    @Test
    public void testGetPoliciesFromUserSuccess() throws InsuranceDException {
        new MockUp<PolicyDaoImpl>() {
            @Mock
            public List<Policy> getPoliciesFromUser(int userId) throws InsuranceDException {
                return new ArrayList<>(simplePolicyList);
            }
        };

        String jsonInput = "{\"id\":\"0\"}";
        PolicyService policyService = new PolicyService();
        String response = policyService.getPoliciesFromUser(jsonInput);

        try {
            Policy[] policyArrayResponse = OBJECT_MAPPER.readValue(response, Policy[].class);
            List<Policy> policyListResponse = Arrays.asList(policyArrayResponse);

            assertNotNull(policyListResponse);
            assertEquals(2, policyListResponse.size());
            assertTrue(policyListResponse.get(0).getCoverableList() == null || policyListResponse.get(0).getCoverableList().isEmpty());
            assertTrue(policyListResponse.get(0).getCoverageList() == null || policyListResponse.get(0).getCoverageList().isEmpty());
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }

    @Test(expected = InsuranceDException.class)
    public void testGetPoliciesFromUserNonexistent() throws InsuranceDException {
        new MockUp<PolicyDaoImpl>() {
            @Mock
            public List<Policy> getPoliciesFromUser(int userId) throws InsuranceDException {
                throw new InsuranceDException("The client does not exist.");
            }
        };

        String jsonInput = "{\"id\":\"0\"}";
        PolicyService policyService = new PolicyService();
        policyService.getPoliciesFromUser(jsonInput);

        fail("The test should have thrown an InsuranceDException, because the user does not exist.");
    }

    @Test
    public void testGetPoliciesFromUserNoPolicies() throws InsuranceDException {
        new MockUp<PolicyDaoImpl>() {
            @Mock
            public List<Policy> getPoliciesFromUser(int userId) throws InsuranceDException {
                return new ArrayList<>(0);
            }
        };

        String jsonInput = "{\"id\":\"0\"}";
        PolicyService policyService = new PolicyService();
        String response = policyService.getPoliciesFromUser(jsonInput);

        try {
            Policy[] policyArrayResponse = OBJECT_MAPPER.readValue(response, Policy[].class);
            List<Policy> policyListResponse = Arrays.asList(policyArrayResponse);

            assertNotNull(policyListResponse);
            assertEquals(0, policyListResponse.size());
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }
}