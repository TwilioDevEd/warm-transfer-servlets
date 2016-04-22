package com.twilio.warmtransfer.utils;

import com.twilio.sdk.CapabilityToken;
import com.twilio.sdk.client.TwilioCapability;

import java.util.Map;
import java.util.concurrent.Exchanger;


public class TwilioCapabilityBuilder {
    private Map<String, String> env;
    private String accountSid;
    private String authToken;

    public TwilioCapabilityBuilder() throws RuntimeException {
        this(System.getenv());
    }

    public TwilioCapabilityBuilder(Map<String, String> env) throws RuntimeException {
        this.env = env;
        if (env.containsKey("TWILIO_ACCOUNT_SID") && env.containsKey("TWILIO_AUTH_TOKEN")){
            this.accountSid = env.get("TWILIO_ACCOUNT_SID");
            this.authToken = env.get("TWILIO_AUTH_TOKEN");
        }else{
            throw new RuntimeException("TWILIO_ACCOUNT_SID and TWILIO_AUTH_TOKEN must be set on system environment.");
        }
    }

    public String getTokenForAgent(String agentName) throws RuntimeException {
        TwilioCapability capability = new TwilioCapability(accountSid, authToken);
        capability.allowClientIncoming(agentName);
        try {
            return capability.generateToken();
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("Error generating token");
        }

    }
}
