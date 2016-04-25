package com.twilio.warmtransfer.servlets;

import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class TokenServletTest {
    private HttpServletResponse response;
    private TwilioAuthenticatedActions mockedCapability;
    private TokenServlet tokenServlet;
    private StringWriter stringWriter;

    @Before
    public void setup() throws Exception {
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        mockedCapability = mock(TwilioAuthenticatedActions.class);
        tokenServlet = new TokenServlet(mockedCapability);
    }
    @Test
    public void doPost() throws Exception {
        when(mockedCapability.getTokenForAgent("agent1")).thenReturn("token");

        HttpServletRequest servletRequest = MockedHttpServletRequestFactory.getMockedRequestWithParameters(new HashMap() {{
            put("agentId", "agent1");
        }});
        tokenServlet.doPost(servletRequest, response);

        verify(mockedCapability).getTokenForAgent("agent1");
        Map<String, String> parsedJson = (Map<String, String>) new JSONParser().parse(stringWriter.toString());
        assertEquals("token", parsedJson.get("token"));
        assertEquals("agent1", parsedJson.get("agentId"));
    }

}