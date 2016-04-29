package com.twilio.warmtransfer.utils;

import com.twilio.sdk.CapabilityToken;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.client.TwilioCapability;
import com.twilio.sdk.resource.instance.Call;

import java.util.HashMap;
import java.util.Map;

public class TwilioAuthenticatedActions {
    private Map<String, String> env;
    private String accountSid;
    private String authToken;
    private String twilioNumber;

    public TwilioAuthenticatedActions() throws RuntimeException {
        this(System.getenv());
    }

    public TwilioAuthenticatedActions(Map<String, String> env) throws RuntimeException {
        this.env = env;
        if (env.containsKey("TWILIO_ACCOUNT_SID") && env.containsKey("TWILIO_AUTH_TOKEN")
                && env.containsKey("TWILIO_NUMBER")) {
            this.accountSid = env.get("TWILIO_ACCOUNT_SID");
            this.authToken = env.get("TWILIO_AUTH_TOKEN");
            this.twilioNumber = env.get("TWILIO_NUMBER");
        } else {
            throw new RuntimeException("TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN and TWILIO_NUMBER must be set on system environment.");
        }
    }

    public String getTokenForAgent(String agentName) throws CapabilityToken.DomainException {
        TwilioCapability capability = new TwilioCapability(accountSid, authToken);
        capability.allowClientIncoming(agentName);
        return capability.generateToken();
    }

    public String callAgent(final String agentId, final String callbackUrl) throws TwilioRestException {
        TwilioRestClient twilioRestClient = new TwilioRestClient(accountSid, authToken);
        Map<String, String> callParams = new HashMap<String, String>() {{
            put("To", "client:" + agentId);
            put("From", twilioNumber);
            put("Url", callbackUrl);
        }};
        Call call = twilioRestClient.getAccount().getCallFactory().create(callParams);
        return call.getSid();
    }
}
