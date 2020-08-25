package com.vk.planner.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class AbstractControllerTest {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper mapper;

    private String header;

    protected TypeReference typeReference;

    protected String baseEndpoint;

    public void setUp() {
        mapper = new ObjectMapper();
        header = "Basic " + Base64.getEncoder().encodeToString("vk:jsr".getBytes());
    }

    protected Object getObjectFromFile(String filePath) {
        Resource resource = new ClassPathResource(filePath);
        Object object = null;
        try {
            object = mapper.readValue(resource.getFile(), typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    protected List<Object> getObjectsFromFile(String filePath) {
        Resource resource = new ClassPathResource(filePath);
        List<Object> objects = null;
        try {
            objects = mapper.readValue(resource.getFile(), new TypeReference<List<Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }

    protected Object sendPostRequest(final String inputFile, final HttpStatus httpStatus) {
        Object objectToReturn = null;
        try {
            Object object = getObjectFromFile(inputFile);
            ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post(baseEndpoint)
                    .header("Authorization", header)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(object)))
                    .andExpect(status().is(httpStatus.value()));

            if (httpStatus.is2xxSuccessful()) {
                String response = resultActions.andReturn().getResponse().getContentAsString();
                objectToReturn = mapper.readValue(response, typeReference);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectToReturn;
    }

    protected Object sendPostRequest(final String inputFile) {
        return sendPostRequest(inputFile, HttpStatus.OK);
    }

    protected Boolean sendPutRequest(final Long id, final Object object, final HttpStatus httpStatus) {
        try {
            mvc.perform(MockMvcRequestBuilders.put(baseEndpoint + "/" + id)
                    .header("Authorization", header)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(object)))
                    .andExpect(status().is(httpStatus.value()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    protected Boolean sendPutRequest(final Long id, final Object object) {
        return sendPutRequest(id, object, HttpStatus.OK);
    }

    protected Object sendGetRequest(final Long id, final HttpStatus httpStatus) {
        Object object = null;
        try {
            ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get(baseEndpoint + "/" + id)
                    .header("Authorization", header))
                    .andExpect(status().is(httpStatus.value()));

            if (httpStatus.is2xxSuccessful()) {
                String response = resultActions.andReturn().getResponse().getContentAsString();
                object = mapper.readValue(response, typeReference);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    protected Object sendGetRequest(final Long id) {
        return sendGetRequest(id, HttpStatus.OK);
    }

    protected List<Object> sendGetAllRequest() {
        List<Object> objects= null;
        try {
            String response = mvc.perform(MockMvcRequestBuilders.get(baseEndpoint)
                    .header("Authorization", header))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            objects = mapper.readValue(response, new TypeReference<List<Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    protected List<Object> sendGetAllRequestOfAType(final String type) {
        List<Object> objects= null;
        try {
            String response = mvc.perform(MockMvcRequestBuilders.get(baseEndpoint + "?type="+type)
                    .header("Authorization", header))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            objects = mapper.readValue(response, new TypeReference<List<Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objects;
    }

    protected Boolean sendDeleteRequest(final Long id, final HttpStatus httpStatus) {
        try {
            mvc.perform(MockMvcRequestBuilders.delete(baseEndpoint + "/" + id)
                    .header("Authorization", header)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(httpStatus.value()));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    protected Boolean sendDeleteRequest(final Long id) {
        return sendDeleteRequest(id, HttpStatus.OK);
    }
}
