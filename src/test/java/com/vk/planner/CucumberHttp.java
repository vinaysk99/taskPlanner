package com.vk.planner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class CucumberHttp {

    protected String httpResponseBody;

    private final HttpClient httpClient = HttpClientBuilder.create().build();

    private String hostUrl = "http://localhost:8080";

    protected int putHttpRequestAndGetStatusCode(final String endpointurl, final String jsonpath) {
        final HttpPut httpPut = new HttpPut(hostUrl + endpointurl);
        httpPut.addHeader("Content-Type", "application/json");
        try {
            String message = FileUtils.readFileToString(new ClassPathResource(jsonpath).getFile(), "UTF-8");
            httpPut.setEntity(new StringEntity(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpResponse httpResponse = executeHttpRequestAndGetResponse(httpPut);
        httpResponseBody = getBodyFromHttpResponse(httpResponse);
        return httpResponse.getStatusLine().getStatusCode();
    }

    protected int postHttpRequestAndGetStatusCode(final String endpointurl, final String jsonpath) {
        final HttpPost httpPost = new HttpPost(hostUrl + endpointurl);
        httpPost.addHeader("Content-Type", "application/json");
        try {
            String message = FileUtils.readFileToString(new ClassPathResource(jsonpath).getFile(), "UTF-8");
            httpPost.setEntity(new StringEntity(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpResponse httpResponse = executeHttpRequestAndGetResponse(httpPost);
        httpResponseBody = getBodyFromHttpResponse(httpResponse);
        return httpResponse.getStatusLine().getStatusCode();
    }

    protected int deleteHttpRequest(final String endPointUrl) {
        final HttpDelete httpDelete = new HttpDelete(hostUrl + endPointUrl);
        HttpResponse httpResponse = executeHttpRequestAndGetResponse(httpDelete);
        return httpResponse.getStatusLine().getStatusCode();
    }

    protected int getHttpRequest(final String endpointurl) {
        final HttpGet httpGet = new HttpGet(hostUrl + endpointurl);
        HttpResponse httpResponse = executeHttpRequestAndGetResponse(httpGet);
        httpResponseBody = getBodyFromHttpResponse(httpResponse);
        return httpResponse.getStatusLine().getStatusCode();
    }

    private HttpResponse executeHttpRequestAndGetResponse(HttpRequestBase httpRequest) {
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    protected String getBodyFromHttpResponse(HttpResponse httpResponse) {
        try {
            return IOUtils.toString(httpResponse.getEntity().getContent(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String getStringFromJsonPath(String expectedOutputJSONPath) {
        try {
            return FileUtils.readFileToString(new ClassPathResource(expectedOutputJSONPath).getFile(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
