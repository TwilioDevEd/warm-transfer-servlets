package com.twilio.warmtransfer.servlets;


import com.google.inject.Singleton;
import com.twilio.warmtransfer.utils.TwilioCapabilityBuilder;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class TokenServlet extends HttpServlet{
    private TwilioCapabilityBuilder capabilityBuilder;

    public TokenServlet(){
        capabilityBuilder = new TwilioCapabilityBuilder();
    }

    public TokenServlet(TwilioCapabilityBuilder capabilityBuilder){
        this.capabilityBuilder = capabilityBuilder;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String agentId = request.getParameter("agentId");

        JSONObject json = new JSONObject(){{
            put("token", capabilityBuilder.getTokenForAgent(agentId));
            put("agentId", agentId);
        }};

        response.setContentType("application/json");
        json.writeJSONString(response.getWriter());
    }
}
