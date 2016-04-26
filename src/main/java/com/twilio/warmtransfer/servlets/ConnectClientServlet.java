package com.twilio.warmtransfer.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.sdk.TwilioRestException;
import com.twilio.warmtransfer.services.ActiveCallsService;
import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ConnectClientServlet extends BaseServlet {

    @Inject
    public ConnectClientServlet(TwilioAuthenticatedActions twilioAuthenticatedActions) {
        super(twilioAuthenticatedActions);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String conferenceId = request.getParameter("CallSid");

            String callback = makeCallbackURI(request, "/conference/connect/agent1");
            twilioAuthenticatedActions.callAgent("agent1", callback);
            ActiveCallsService.saveNewConference("agent1", conferenceId);

            response.setContentType("text/xml");
            response.getWriter().write(generateConnectConference(conferenceId, false, true));
        } catch (TwilioRestException e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling agent1");
        }
    }
}
