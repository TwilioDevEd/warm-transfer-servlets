package com.twilio.warmtransfer.servlets;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twilio.warmtransfer.utils.TwilioAuthenticatedActions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class IndexServlet extends BaseServlet {

    @Inject
    public IndexServlet(TwilioAuthenticatedActions twilioAuthenticatedActions) {
        super(twilioAuthenticatedActions);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
