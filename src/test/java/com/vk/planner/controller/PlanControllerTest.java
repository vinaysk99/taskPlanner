package com.vk.planner.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.vk.planner.domain.Plan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PlanControllerTest extends AbstractControllerTest {

    @BeforeEach
    public void setUp() {
        super.setUp();
        typeReference = new TypeReference<Plan>() {};
        baseEndpoint = "/v1/plans";
    }

    @Test
    public void shouldCreateNewPlanAndGetOnId() {
        // create and verify
        Plan plan = (Plan) sendPostRequest("tdd/plan1.json");
        assertNotNull(plan.getId());

        // get and assert
        Plan planFetched = (Plan) sendGetRequest(plan.getId());
        assertThat(planFetched, is(plan));

        sendDeleteRequest(plan.getId());
    }

    @Test
    public void shouldCreateNewPlanAndUpdateDesc() {
        // create and verify
        Plan plan = (Plan) sendPostRequest("tdd/plan2.json");
        assertNotNull(plan.getId());

        // update and assert
        String newDesc = "New Desc";
        plan.setDescription(newDesc);
        Boolean aBoolean = sendPutRequest(plan.getId(), plan);
        assertThat(aBoolean, is(Boolean.TRUE));

        sendDeleteRequest(plan.getId());
    }

    @Test
    public void shouldCreateNewPlanAndDelete() {
        // create and verify
        Plan plan = (Plan) sendPostRequest("tdd/plan3.json");
        assertNotNull(plan.getId());

        // delete and assert
        Boolean deleted = sendDeleteRequest(plan.getId());
        assertThat(deleted, is(Boolean.TRUE));

        sendGetRequest(plan.getId(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldGetAllPlans() {
        sendPostRequest("tdd/plan3.json");
        sendPostRequest("tdd/plan4.json");
        sendPostRequest("tdd/plan5.json");
        // get all and assert
        List<Plan> plans = (List<Plan>) (List) sendGetAllRequest();
        assertNotNull(plans);

        List<Object> walkPlans = sendGetAllRequestOfAType("Walk");
        assertNotNull(walkPlans);
    }

    @Test
    public void shouldThrowExceptionFetchingPlanThatDoesntExist() {
        sendGetRequest(9L, HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldThrowExceptionDeletingPlanThatDoesntExist() {
        sendDeleteRequest(9L, HttpStatus.NOT_FOUND);
    }
}