package com.twilio.warmtransfer.servlets.guice;

import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.servlet.ServletModule;
import com.twilio.http.TwilioRestClient;
import com.twilio.warmtransfer.servlets.CallAgentServlet;
import com.twilio.warmtransfer.servlets.ConferenceWaitServlet;
import com.twilio.warmtransfer.servlets.ConnectAgentServlet;
import com.twilio.warmtransfer.servlets.ConnectClientServlet;
import com.twilio.warmtransfer.servlets.IndexServlet;
import com.twilio.warmtransfer.servlets.TokenServlet;
import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;

import java.util.Map;

import static java.lang.System.getenv;


public class WarmTransferServletModule extends ServletModule {
    @Override
    public void configureServlets() {
        serve("/").with(IndexServlet.class);
        serve("/token").with(TokenServlet.class);
        serve("/conference/wait").with(ConferenceWaitServlet.class);
        serve("/conference/connect/client").with(ConnectClientServlet.class);
        serve("/conference/connect/agent*").with(ConnectAgentServlet.class);
        serve("/conference/call/agent2").with(CallAgentServlet.class);

        bind(TwilioAuthenticatedActions.class);
    }

    @Provides
    public TwilioRestClient twilioRestClient() {
        return new TwilioRestClient.Builder(
                getenv("TWILIO_ACCOUNT_SID"), getenv("TWILIO_AUTH_TOKEN"))
                .build();
    }

    @Named("env")
    @Provides
    public Map<String, String> env() {
        return System.getenv();
    }
}
