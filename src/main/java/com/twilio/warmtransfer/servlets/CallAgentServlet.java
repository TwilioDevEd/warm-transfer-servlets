package com.twilio.warmtransfer.servlets;

import com.google.inject.Singleton;
import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


@Singleton
public class CallAgentServlet extends HttpServlet {

    private TwilioAuthenticatedActions twilioAuthenticatedActions;

    public CallAgentServlet(){
        this.twilioAuthenticatedActions = new TwilioAuthenticatedActions();
    }

    public CallAgentServlet(TwilioAuthenticatedActions twilioAuthenticatedActions){
        this.twilioAuthenticatedActions = twilioAuthenticatedActions;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String agentId = request.getRequestURI().contains("agent1")? "agent1" : "agent2";
        String conferenceId = ActiveCallsService.getConferenceFromAgentID("agent1");
        String callbackUrl = makeCallbackURI(request, "/conference/connect/agent2");
        ActiveCallsService.saveNewConference("agent2", conferenceId);
        twilioAuthenticatedActions.callAgent("agent2", callbackUrl);
    }

    private String makeCallbackURI(HttpServletRequest request, String path){
        try {
            return new URL("http", request.getServerName(), -1, path).toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Application error while formatting URL");
        }
    }
}