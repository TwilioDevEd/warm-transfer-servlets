package com.twilio.warmtransfer.servlets;


import com.google.inject.Singleton;
import com.twilio.sdk.verbs.Play;
import com.twilio.sdk.verbs.Say;
import com.twilio.sdk.verbs.TwiMLResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ConferenceWaitServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TwiMLResponse twiMLResponse = new TwiMLResponse();
        Say say = new Say("Thank you for calling. Please wait in line for a few seconds. An agent will be with you shortly.");

        Play playMusic = new Play("http://com.twilio.music.classical.s3.amazonaws.com/BusyStrings.mp3");
        playMusic.setLoop(0);
        try {
            twiMLResponse.append(say);
            twiMLResponse.append(playMusic);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
        response.setContentType("text/xml");
        response.getWriter().write(twiMLResponse.toEscapedXML());
    }
}
