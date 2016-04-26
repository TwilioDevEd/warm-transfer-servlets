package com.twilio.warmtransfer.servlets;

import com.google.inject.Singleton;
import com.twilio.sdk.verbs.Conference;
import com.twilio.sdk.verbs.Dial;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Singleton
public class ConnectAgentServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String agentId = request.getRequestURI().contains("agent1")? "agent1" : "agent2";
        boolean endOnExit = agentId.equals("agent2");
        String conferenceId = ActiveCallsService.getConferenceFromAgentID(agentId);

        response.setContentType("text/xml");
        response.getWriter().write(generateConnectConference(conferenceId, endOnExit));
    }

    private String generateConnectConference(String conferenceId, boolean endOnExit) throws RuntimeException {
        TwiMLResponse twiMLResponse = new TwiMLResponse();
        Conference conferenceVerb = new Conference(conferenceId);
        conferenceVerb.setStartConferenceOnEnter(true);
        conferenceVerb.setEndConferenceOnExit(endOnExit);
        conferenceVerb.setWaitUrl("/conference/wait");
        try {
            twiMLResponse.append(new Dial()).append(conferenceVerb);
        } catch (TwiMLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating conference.");
        }
        return twiMLResponse.toEscapedXML();
    }
}
