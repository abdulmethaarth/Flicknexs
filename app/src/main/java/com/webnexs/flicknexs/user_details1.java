package com.webnexs.flicknexs;

import java.util.HashMap;
import java.util.Map;

class user_details1 {

    private String email;
    private String plan;
    private String username;
    private String stripetoken;
    private String password;
    private String skip;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPlan()
    {
        return plan;
    }

    public void setPlan(String plan)
    {
        this.plan=plan;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username=username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password=password;
    }

    public String getStripetoken()
    {
        return stripetoken;

    }

    public void setStripetoken(String stripetoken)
    {
        this.stripetoken=stripetoken;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
