package com.twilio.warmtransfer.servlets.guice;

import com.google.inject.servlet.ServletModule;
import com.twilio.warmtransfer.servlets.IndexServlet;
import com.twilio.warmtransfer.servlets.TokenServlet;


public class WarmTransferServletModule extends ServletModule {
    @Override
    public void configureServlets() {
        serve("/").with(IndexServlet.class);
        serve("/token").with(TokenServlet.class);
    }
}
