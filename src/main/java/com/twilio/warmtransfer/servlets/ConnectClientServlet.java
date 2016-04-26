package com.twilio.warmtransfer.servlets;

import com.google.inject.Singleton;
import com.twilio.warmtransfer.services.ActiveCallsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ConnectClientServlet extends BaseServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String conferenceId = request.getParameter("CallSid");

        String callback = makeCallbackURI(request, "/conference/connect/agent1");

        twilioAuthenticatedActions.callAgent("agent1", callback);
        ActiveCallsService.saveNewConference("agent1", conferenceId);

        response.setContentType("text/xml");
        response.getWriter().write(generateConnectConference(conferenceId, false, true));
    }
}
