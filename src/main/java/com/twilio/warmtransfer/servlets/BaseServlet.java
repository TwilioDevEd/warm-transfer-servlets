package com.twilio.warmtransfer.servlets;

import com.twilio.sdk.verbs.Conference;
import com.twilio.sdk.verbs.Dial;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;


public class BaseServlet extends HttpServlet {
    TwilioAuthenticatedActions twilioAuthenticatedActions;

    void setTwilioAuthenticatedActions(TwilioAuthenticatedActions twilioAuthenticatedActions) {
        this.twilioAuthenticatedActions = twilioAuthenticatedActions;
    }

    String generateConnectConference(String conferenceId, boolean startOnEnter, boolean endOnExit) throws RuntimeException {
        TwiMLResponse twiMLResponse = new TwiMLResponse();
        Conference conferenceVerb = new Conference(conferenceId);
        conferenceVerb.setStartConferenceOnEnter(startOnEnter);
        conferenceVerb.setEndConferenceOnExit(endOnExit);
        conferenceVerb.setWaitUrl("/conference/wait");
        conferenceVerb.setWaitMethod("POST");
        try {
            twiMLResponse.append(new Dial()).append(conferenceVerb);
        } catch (TwiMLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating conference.");
        }
        return twiMLResponse.toEscapedXML();
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
