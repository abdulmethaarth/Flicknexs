package com.webnexs.flicknexs;

import java.util.HashMap;
import java.util.Map;

class user_details {

    private String id;
    private String role;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
