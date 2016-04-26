package com.twilio.warmtransfer.servlets;

import com.google.inject.Singleton;
import com.twilio.warmtransfer.services.ActiveCallsService;
import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ConnectAgentServlet extends BaseServlet {
    public ConnectAgentServlet() {
        super(new TwilioAuthenticatedActions());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String agentId = request.getRequestURI().contains("agent1") ? "agent1" : "agent2";
        boolean endOnExit = agentId.equals("agent2");
        String conferenceId = ActiveCallsService.getConferenceFromAgentID(agentId);

        response.setContentType("text/xml");
        response.getWriter().write(generateConnectConference(conferenceId, true, endOnExit));
    }
}
