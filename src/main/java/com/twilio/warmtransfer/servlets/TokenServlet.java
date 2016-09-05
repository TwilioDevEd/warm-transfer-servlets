package com.twilio.warmtransfer.servlets;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class TokenServlet extends BaseServlet {

    @Inject
    public TokenServlet(TwilioAuthenticatedActions twilioAuthenticatedActions) {
        super(twilioAuthenticatedActions);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String agentId = request.getParameter("agentId");
        final String tokenForAgent;
        tokenForAgent = twilioAuthenticatedActions.getTokenForAgent(agentId);
        JSONObject json = new JSONObject() {{
            put("token", tokenForAgent);
            put("agentId", agentId);
        }};

        response.setContentType("application/json");
        json.write(response.getWriter());
    }
}
