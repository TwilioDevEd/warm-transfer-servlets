package com.twilio.warmtransfer.servlets;


import com.google.inject.Singleton;
import com.twilio.warmtransfer.utils.TwimlBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ConferenceWaitServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/xml");
            response.getWriter().write(new TwimlBuilder().generateWait().toEscapedXML());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
    }
}
