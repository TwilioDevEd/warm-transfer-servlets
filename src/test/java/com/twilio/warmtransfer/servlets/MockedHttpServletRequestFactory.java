package com.twilio.warmtransfer.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockedHttpServletRequestFactory {
    public static HttpServletRequest getMockedRequestWithParameters(Map<String, String> parameters) {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);

        for (String key : parameters.keySet()) {
            when(servletRequest.getParameter(key)).thenReturn(parameters.get(key));
        }

        when(servletRequest.getSession(anyBoolean())).thenReturn(mock(HttpSession.class));
        return servletRequest;
    }
}