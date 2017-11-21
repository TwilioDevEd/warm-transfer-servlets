package com.twilio.warmtransfer.utils;

import com.twilio.http.HttpMethod;
import com.twilio.twiml.TwiMLException;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Conference;
import com.twilio.twiml.voice.Dial;
import com.twilio.twiml.voice.Play;
import com.twilio.twiml.voice.Say;

public class TwimlBuilder {
    private VoiceResponse.Builder builder;

    public TwimlBuilder() {
        this.builder = new VoiceResponse.Builder();
    }

    public TwimlBuilder generateWait() throws TwiMLException {
        Say say = new Say.Builder("Thank you for calling. Please wait in line for a few seconds. An agent will be with you shortly.")
                .build();
        Play play = new Play.Builder("http://com.twilio.music.classical.s3.amazonaws.com/BusyStrings.mp3")
                .loop(0)
                .build();
        builder.say(say);
        builder.play(play);
        return this;
    }

    public TwimlBuilder generateConnectConference(String conferenceId, boolean startOnEnter, boolean endOnExit) throws TwiMLException {
        Dial dial = new Dial.Builder()
                .conference(new Conference.Builder(conferenceId)
                        .startConferenceOnEnter(startOnEnter)
                        .endConferenceOnExit(endOnExit)
                        .waitUrl("/conference/wait")
                        .waitMethod(HttpMethod.POST)
                        .build())
                .build();
        builder.dial(dial);
        return this;
    }

    public String toEscapedXML() {
        try {
            return this.builder.build().toXml();
        } catch (TwiMLException e) {
            throw new RuntimeException(e);
        }
    }
}
