package com.vk.planner.bdd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.planner.domain.Plan;
import io.cucumber.java.en.Given;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CoreStepDefinitions extends CucumberHttp {

    private ObjectMapper mapper = new ObjectMapper();

    @Given("^A HTTP DELETE request (.*?) on (.*?) returning status code (.*?)")
    public void makeHTTPDeleteRequest(String testcase, String endPoint, Integer responseCode) {
        httpResponseBody = null;
        int statusCode = deleteHttpRequest(endPoint + "?ids=0,1");
        assertThat(statusCode, is(responseCode));
        System.out.println(testcase + " - Successfully run");
    }

    @Given("^A HTTP PUT request to (.*?) for id (.*?) with body (.*?) on (.*?) returning status code (.*?)$")
    public void makeHTTPPutRequest(String testcase, String id, String requestBodyPath, String endPoint, Integer responseCode) {
        httpResponseBody = null;
        int statusCode = putHttpRequestAndGetStatusCode(endPoint + "/" + id, requestBodyPath);
        assertThat(statusCode, is(responseCode));
        System.out.println(testcase + " - Successfully run");
    }

    @Given("^A HTTP POST request to (.*?) with body (.*?) on (.*?) returning status code (.*?)$")
    public void makeHTTPPostRequest(String testcase, String requestBodyPath, String endPoint, Integer responseCode) {
        httpResponseBody = null;
        int statusCode = postHttpRequestAndGetStatusCode(endPoint, requestBodyPath);
        assertThat(statusCode, is(responseCode));
        System.out.println(testcase + " - Successfully run");
    }

    @Given("^A HTTP GET request (.*?) to get all plans on (.*?) returning status code (.*?)$")
    public void makeHTTPPostRequest(String testcase, String endPoint, Integer responseCode) {
        httpResponseBody = null;
        int statusCode = getHttpRequest(endPoint);
        assertThat(statusCode, is(responseCode));
        System.out.println(testcase + " - Successfully run");
    }

    @Given("^A HTTP GET request (.*?) to get plan for id (.*?) on (.*?) returning status code (.*?)$")
    public void makeHTTPGetRequest(String testcase, String id, String endPoint, Integer responseCode) {
        httpResponseBody = null;
        int statusCode = getHttpRequest(endPoint + "/" + id);
        assertThat(statusCode, is(responseCode));
        System.out.println(testcase + " - Successfully run");
    }

    @Given("^Validate HTTP Response List (.*?)$")
    public void validateHttpResponseList(String expectedOutputJSONPath) {
        String expectedResponse = getStringFromJsonPath(expectedOutputJSONPath);
        try {
            List<Plan> plansExpected = mapper.readValue(expectedResponse, new TypeReference<List<Plan>>() {});
            List<Plan> plansActual = mapper.readValue(httpResponseBody, new TypeReference<List<Plan>>() {});
            System.out.println("Expected Response : " + expectedResponse);
            System.out.println("Actual Response : " + httpResponseBody);
            assertThat(plansExpected, is(plansActual));
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }

    @Given("^Validate HTTP Response object (.*?)$")
    public void validateHttpResponse(String expectedOutputJSONPath) {
        String expectedResponse = getStringFromJsonPath(expectedOutputJSONPath);
        try {
            Plan planExpected = mapper.readValue(expectedResponse, Plan.class);
            Plan planActual = mapper.readValue(httpResponseBody, Plan.class);
            assertThat(planExpected, is(planActual));
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }
    }
}
