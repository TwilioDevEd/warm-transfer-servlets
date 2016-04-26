package com.twilio.warmtransfer.servlets;


import com.google.inject.Singleton;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class TokenServlet extends BaseServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String agentId = request.getParameter("agentId");

        JSONObject json = new JSONObject() {{
            put("token", twilioAuthenticatedActions.getTokenForAgent(agentId));
            put("agentId", agentId);
        }};

        response.setContentType("application/json");
        json.writeJSONString(response.getWriter());
    }
}
