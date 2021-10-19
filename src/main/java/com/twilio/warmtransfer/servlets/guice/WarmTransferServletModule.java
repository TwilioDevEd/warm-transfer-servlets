package com.twilio.warmtransfer.servlets.guice;

import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.servlet.ServletModule;
import com.twilio.http.TwilioRestClient;
import com.twilio.warmtransfer.servlets.*;
import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;
import io.github.cdimascio.dotenv.Dotenv;


public class WarmTransferServletModule extends ServletModule {
    Dotenv dotenv = Dotenv.load();

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
                dotenv.get("TWILIO_ACCOUNT_SID"), dotenv.get("TWILIO_AUTH_TOKEN"))
                .build();
    }

    @Named("env")
    @Provides
    public Dotenv env() {
        return dotenv;
    }
}
