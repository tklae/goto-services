package com.gotocon.cdworkshop.http;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ApiHttpResponse {

    private final List<Integer> okStatusCodes = Arrays.asList(new Integer[]{200, 201, 202, 203, 204, 205, 206});
    private String responsePayload;
    private final Integer statusCode;

    public ApiHttpResponse(HttpEntity entity, Integer statusCode){
        this.statusCode = statusCode;
        if(entity == null) {
            this.responsePayload = "";
        } else {
            try {
                this.responsePayload = EntityUtils.toString(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public boolean isOk(){
        return okStatusCodes.contains(this.statusCode);
    }

    public String getPayload() {
        return responsePayload;
    }
}
