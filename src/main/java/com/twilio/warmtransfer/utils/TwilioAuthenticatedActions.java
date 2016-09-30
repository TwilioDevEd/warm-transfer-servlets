package com.twilio.warmtransfer.utils;


import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.twilio.http.TwilioRestClient;
import com.twilio.jwt.client.ClientCapability;
import com.twilio.jwt.client.IncomingClientScope;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.util.Map;

import static java.util.Arrays.asList;


public class TwilioAuthenticatedActions {
    private Map<String, String> env;
    private String accountSid;
    private String authToken;
    private String twilioNumber;
    private TwilioRestClient twilioRestClient;

    @Inject
    public TwilioAuthenticatedActions(TwilioRestClient twilioRestClient,
            @Named("env") Map<String, String> env) {
        this(env);
        this.twilioRestClient = twilioRestClient;
    }

    TwilioAuthenticatedActions(Map<String, String> env) throws RuntimeException {
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

    public String getTokenForAgent(String agentName) {
        ClientCapability clientCapability = new ClientCapability.Builder(accountSid, authToken)
                .scopes(asList(new IncomingClientScope(agentName)))
                .build();

        return clientCapability.toJwt();
    }

    public String callAgent(final String agentId, final String callbackUrl) {
        Call call = Call.creator(new PhoneNumber("client:" + agentId),
                new PhoneNumber(twilioNumber),
                URI.create(callbackUrl)).create(twilioRestClient);
        return call.getSid();
    }
}
