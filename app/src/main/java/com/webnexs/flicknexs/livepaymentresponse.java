package com.webnexs.flicknexs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class livepaymentresponse {

    private String status;
    private String message;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private List<user_details> user_details;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
