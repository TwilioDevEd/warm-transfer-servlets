package com.twilio.warmtransfer.servlets;

import com.google.inject.Singleton;
import com.twilio.warmtransfer.services.ActiveCallsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Singleton
public class CallAgentServlet extends BaseServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String agentId = request.getRequestURI().contains("agent1") ? "agent1" : "agent2";
        String conferenceId = ActiveCallsService.getConferenceFromAgentID("agent1");
        String callbackUrl = makeCallbackURI(request, "/conference/connect/agent2");
        ActiveCallsService.saveNewConference("agent2", conferenceId);
        twilioAuthenticatedActions.callAgent("agent2", callbackUrl);
    }
}