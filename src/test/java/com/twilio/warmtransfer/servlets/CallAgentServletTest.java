package com.twilio.warmtransfer.servlets;

import com.twilio.warmtransfer.services.ActiveCallsService;
import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class CallAgentServletTest {
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private XpathEngine eng = XMLUnit.newXpathEngine();
    private TwilioAuthenticatedActions mockedAuthenticatedActions;
    private CallAgentServlet callAgentServlet;

    @Before
    public void setup() throws Exception {
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        mockedAuthenticatedActions = mock(TwilioAuthenticatedActions.class);
        callAgentServlet = new CallAgentServlet();
        callAgentServlet.setTwilioAuthenticatedActions(mockedAuthenticatedActions);
    }

    @Test
    public void doPost() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/conference/call/agent2");
        when(mockedAuthenticatedActions.callAgent("agent2", "http:/conference/connect/agent2")).thenReturn("CallSid");
        ActiveCallsService.saveNewConference("agent1", "CallSid");

        callAgentServlet.doPost(request, response);

        verify(mockedAuthenticatedActions).callAgent("agent2", "http:/conference/connect/agent2");
        assertEquals(ActiveCallsService.getConferenceFromAgentID("agent2"), "CallSid");
    }

}