package com.twilio.warmtransfer.servlets.guice;

import com.google.inject.servlet.ServletModule;
import com.twilio.warmtransfer.servlets.*;


public class WarmTransferServletModule extends ServletModule {
    @Override
    public void configureServlets() {
        serve("/").with(IndexServlet.class);
        serve("/token").with(TokenServlet.class);
        serve("/conference/wait").with(ConferenceWaitServlet.class);
        serve("/conference/connect/client").with(ConnectClientServlet.class);
        serve("/conference/connect/agent*").with(ConnectAgentServlet.class);
    }
}
