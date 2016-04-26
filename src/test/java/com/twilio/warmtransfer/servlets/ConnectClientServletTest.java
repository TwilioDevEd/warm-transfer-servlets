package com.twilio.warmtransfer.servlets;

import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ConnectClientServletTest {
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private XpathEngine eng = XMLUnit.newXpathEngine();
    private TwilioAuthenticatedActions mockedAuthenticatedActions;
    private ConnectClientServlet connectClientServlet;

    @Before
    public void setup() throws Exception {
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        mockedAuthenticatedActions = mock(TwilioAuthenticatedActions.class);
        connectClientServlet = new ConnectClientServlet(mockedAuthenticatedActions);
    }

    @Test
    public void doPostGeneratesDialAndConferenceVerbs() throws Exception {
        when(mockedAuthenticatedActions.callAgent("agent1", "CallSid")).thenReturn("CallSid");

        HttpServletRequest servletRequest = MockedHttpServletRequestFactory.getMockedRequestWithParameters(new HashMap() {{
            put("CallSid", "CallSid");
        }});
        connectClientServlet.doPost(servletRequest, response);

        Document doc = XMLUnit.buildControlDocument(stringWriter.toString());

        Node conferenceNode = eng.getMatchingNodes("/Response/Dial/Conference", doc).item(0);
        assertNotNull(conferenceNode);
        assertEquals(conferenceNode.getTextContent(), "CallSid");
        assertAttributeHasValue(conferenceNode, "endConferenceOnExit", "true");
        assertAttributeHasValue(conferenceNode, "startConferenceOnEnter", "false");
        assertAttributeHasValue(conferenceNode, "waitUrl", "/conference/wait");
    }

    private void assertAttributeHasValue(Node node, String name, String value){
        assertNotNull(node.getAttributes().getNamedItem(name));
        assertEquals(node.getAttributes().getNamedItem(name).getNodeValue(), value);

    }
}