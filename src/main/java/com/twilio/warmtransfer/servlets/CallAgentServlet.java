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
public class CallAgentServlet extends BaseServlet {

    @Inject
    public CallAgentServlet(TwilioAuthenticatedActions twilioAuthenticatedActions) {
        super(twilioAuthenticatedActions);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String agentId = "agent2";
        try {
            String conferenceId = ActiveCallsService.getConferenceFromAgentID("agent1");
            String callbackUrl = makeCallbackURI(request, "/conference/connect/" + agentId);
            twilioAuthenticatedActions.callAgent(agentId, callbackUrl);
            ActiveCallsService.saveNewConference(agentId, conferenceId);
        } catch (TwilioRestException e) {
            e.printStackTrace();
            throw new RuntimeException("Error calling " + agentId);
        }
    }
}