package com.twilio.warmtransfer.utils;


import com.twilio.sdk.verbs.*;

public class TwimlBuilder {
    private TwiMLResponse twiMLResponse = new TwiMLResponse();

    public TwimlBuilder() {
        this.twiMLResponse = new TwiMLResponse();
    }

    public TwimlBuilder generateWait() throws TwiMLException {
        Say say = new Say("Thank you for calling. Please wait in line for a few seconds. An agent will be with you shortly.");
        Play playMusic = new Play("http://com.twilio.music.classical.s3.amazonaws.com/BusyStrings.mp3");
        playMusic.setLoop(0);
        twiMLResponse.append(say);
        twiMLResponse.append(playMusic);
        return this;
    }

    public TwimlBuilder generateConnectConference(String conferenceId, boolean startOnEnter, boolean endOnExit) throws TwiMLException {
        Conference conferenceVerb = new Conference(conferenceId);
        conferenceVerb.setStartConferenceOnEnter(startOnEnter);
        conferenceVerb.setEndConferenceOnExit(endOnExit);
        conferenceVerb.setWaitUrl("/conference/wait");
        conferenceVerb.setWaitMethod("POST");
        twiMLResponse.append(new Dial()).append(conferenceVerb);
        return this;
    }

    public String toEscapedXML() {
        return this.twiMLResponse.toEscapedXML();
    }
}
