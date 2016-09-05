package com.twilio.warmtransfer.servlets;

import com.twilio.twiml.TwiMLException;
import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;
import com.twilio.warmtransfer.utils.TwimlBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;


public class BaseServlet extends HttpServlet {
    TwilioAuthenticatedActions twilioAuthenticatedActions;

    BaseServlet(TwilioAuthenticatedActions twilioAuthenticatedActions) {
        this.twilioAuthenticatedActions = twilioAuthenticatedActions;
    }

    String generateConnectConference(String conferenceId, boolean startOnEnter, boolean endOnExit) throws RuntimeException {
        try {
            return new TwimlBuilder().generateConnectConference(conferenceId, startOnEnter, endOnExit).toEscapedXML();
        } catch (TwiMLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating conference.");
        }
    }

    String makeCallbackURI(HttpServletRequest request, String path) {
        try {
            return new URL("http",
                    request.getServerName(),
                    -1,
                    path).toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "";
        }
    }
}