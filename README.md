# Warm Transfer: Transfer support calls from one agent to another using Java Servlets
[![Build Status](https://travis-ci.org/TwilioDevEd/warm-transfer-servlets.svg?branch=master)](https://travis-ci.org/TwilioDevEd/warm-transfer-servlets)

## Run the application

1. Clone the repository and `cd` into it.

1. The application uses Gradle to manage dependencies.

1. Edit the sample configuration file `.env.example` and edit it to match your configuration.

   Once you have edited the `.env.example` file, if you are using a UNIX operating system,
   just use the `source` command to load the variables into your environment:

   ```bash
   $ source .env.example
   ```

   If you are using a different operating system, make sure that all the
   variables from the .env.example file are loaded into your environment.

   You can find your `TWILIO_ACCOUNT_SID` and `TWILIO_AUTH_TOKEN` in your
   [Twilio Account Settings](https://www.twilio.com/user/account/settings).
   You will also need a `TWILIO_NUMBER`, which you may find [here](https://www.twilio.com/user/account/phone-numbers/incoming).

1. Configure Twilio to call your webhooks

   You will also need to configure Twilio to call your application when calls are received on your
   `TWILIO_NUMBER`. The voice URL should look something like this:

   ```
    http://9a159ccf.ngrok.io/conference/connect/client
   ```

   ![Configure Voice](http://howtodocs.s3.amazonaws.com/twilio-number-config-all-med.gif)

1. Run the application using Gradle Gretty plugin.

   ```bash
   $ ./gradlew appRun
   ```

   This will run the embedded Jetty application server that uses port 8080.

## How to Demo

1. Navigate to `https://<ngrok_subdomain>.ngrok.io` in two different
   browser tabs or windows.

   **Notes:**
   * Remember to use your SSL enabled ngrok URL `https`.
   Failing to do this won't allow you to receive incoming calls.
   * The application has been tested with [Chrome](https://www.google.com/chrome/)
   and [Firefox](https://firefox.com). Safari is not supported at the moment.

1. In one window/tab click `Connect as Agent 1` and in the other one click
   `Connect as Agent 2`. Now both agents are waiting for an incoming call.

1. Dial your [Twilio Number](https://www.twilio.com/user/account/phone-numbers/incoming)
   to start a call with `Agent 1`. Your `TWILIO_NUMBER`
   environment variable was set when configuring the application to run.

1. When `Agent 1` answers the call from the client, he/she can dial `Agent 2` in
   by clicking on the `Dial agent 2 in` button.

1. Once `Agent 2` answers the call all three participants will have joined the same
   call. After that, `Agent 1` can drop the call and leave both the client and `Agent 2`
   having a pleasant talk.

### Dependencies

This application uses this Twilio helper library.

* [twilio-java](//github.com/twilio/twilio-java)

### Run the tests

1. Run at the top-level directory.

   ```bash
   $ ./gradlew test
   ```

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
