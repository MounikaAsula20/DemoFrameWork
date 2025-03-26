package api_tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.RestAssuredUtilities;

public class PatchEndpointTest {

    private static final String POST_URL = "https://api.restful-api.dev/objects";
    private static final String GET_URL = "https://api.restful-api.dev/objects/";
    private String generatedId; // Dynamically generated ID for the resource

    @BeforeClass

    public void setup() {
        System.out.println("Setting up preconditions...");

        // Create a JSON body for the POST request
        String body = "{\"name\": \"Apple iPad Air\", \"data\": { \"Generation\": \"7th\", \"Price\": \"519.99\", \"Capacity\": \"125 GB\" }}";

        try {
            // Send POST request to create the resource
            String response = RestAssuredUtilities.sendPostRequestUsingHttpUrlConnection(body, POST_URL);
            System.out.println("POST Response: " + response);

            // Extract the generated ID from the response
            if (response.contains("\"id\":\"")) {
                generatedId = response.split("\"id\":\"")[1].split("\"")[0];
                System.out.println("Generated ID: " + generatedId);
            } else {
                Assert.fail("Response does not contain 'id'. Response: " + response);
            }

            // Validate that the ID is not null or empty
            Assert.assertNotNull(generatedId, "Generated ID is null.");
            Assert.assertFalse(generatedId.isEmpty(), "Generated ID is empty.");

        } catch (Exception e) {
            System.err.println("Error during setup: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Failed to create resource during setup.");
        }
    }

    @DataProvider(name = "PatchDataProvider")
    public Object[][] patchDataProvider() {
        return new Object[][]{
            {"{\"name\": \"Apple iPad Air pro plus\"}", 200}, // Valid field update
            {"{\"data\": {\"Price\": 600.00}}", 200}, // Update nested price field
            {"{}", 404}, // Empty
            {"{\"gender\": \"female\"}", 404}, // Unsupported field
            {"{\"data\": {\"Capacity\": 12  }}", 200}, // Boundary test for large 
            {"{\"data\": {\"Capacity\": null}}", 200}, // Valid null value update
            {"{\"data\": {\"Price\": \"mounika\"}}", 400}, // Invalid type for Price
            
        };
    }

    @Test(priority = 2,dataProvider = "PatchDataProvider")

    
    public void testPatchEndpoint(String jsonBody, int expectedStatusCode) {
        System.out.println("Running PATCH test...");
        System.out.println("Request Body: " + jsonBody);

        // Construct PATCH URL using the dynamically generated ID
        String patchUrl = "https://api.restful-api.dev/objects/" + generatedId;

        // Log the PATCH URL for debugging
        System.out.println("PATCH URL: " + patchUrl);

        // Send PATCH request
        Response patchResponse = RestAssuredUtilities.sendPatchRequest(patchUrl, jsonBody);

        // Log full response
        System.out.println("PATCH Response Status: " + patchResponse.getStatusCode());
        System.out.println("Full PATCH Response: " + patchResponse.getBody().asString());

        // Validate response status
        Assert.assertEquals(patchResponse.getStatusCode(), expectedStatusCode, "Unexpected status code.");

       
        
    }		  
  
    @Test(priority = 1)
    public void testPreconditions() {
        System.out.println("Checking preconditions...");

        // Ensure the resource exists using GET
        String getUrl = GET_URL + generatedId;
        Response getResponse = RestAssuredUtilities.sendGetRequest(getUrl);

        // Log GET response
        System.out.println("GET Response Status: " + getResponse.getStatusCode());
        System.out.println("GET Response Body: " + getResponse.getBody().asString());

        // Assert resource existence
        Assert.assertEquals(getResponse.getStatusCode(), 200, "Resource does not exist.");
    }

    @Test(priority = 3)
 
    public void testSecurity() {
        System.out.println("Running security test...");

        // Send PATCH request without required headers
        String patchUrl = "https://api.restful-api.dev/objects/" + generatedId; // Correct PATCH URL
        Response unauthorizedResponse = RestAssured.given()
                .header("Content-Type", "application/json")
                .body("{\"name\": \"Unauthorized Test\"}")
                .when()
                .patch(patchUrl)
                .then()
                .extract()
                .response();

        // Log response
        System.out.println("Unauthorized PATCH Response Status: " + unauthorizedResponse.getStatusCode());
        System.out.println("Unauthorized PATCH Response Body: " + unauthorizedResponse.getBody().asString());

        // Assert unauthorized request response
        Assert.assertEquals(unauthorizedResponse.getStatusCode(), 401, "Expected 401 Unauthorized response.");
    }}