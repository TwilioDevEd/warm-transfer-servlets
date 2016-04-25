package com.twilio.warmtransfer.servlets;


import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ConferenceWaitServletTest {
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private HttpServletRequest request;
    private XpathEngine eng = XMLUnit.newXpathEngine();

    @Before
    public void setup() throws Exception {
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
        request = mock(HttpServletRequest.class);
    }

    @Test
    public void doPostGeneratesSayAndPlayVerbs() throws Exception {
        new ConferenceWaitServlet().doPost(request, response);

        Document doc = XMLUnit.buildControlDocument(stringWriter.toString());

        assertThat(eng.evaluate("/Response/Say/text()", doc), CoreMatchers.containsString("Thank you for calling"));
        assertThat(eng.evaluate("/Response/Play/text()", doc), CoreMatchers.containsString("mp3"));
    }
}