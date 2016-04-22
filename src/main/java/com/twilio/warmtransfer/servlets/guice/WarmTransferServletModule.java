package com.twilio.warmtransfer.servlets.guice;

import com.google.inject.servlet.ServletModule;
import com.twilio.warmtransfer.servlets.IndexServlet;


public class WarmTransferServletModule extends ServletModule {
    @Override
    public void configureServlets() {
        serve("/").with(IndexServlet.class);
    }
}
