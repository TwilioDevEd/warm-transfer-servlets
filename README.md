# Warm Transfer: Transfer support calls from one agent to another using Java Servlets
[![Java Servlet CI](https://github.com/TwilioDevEd/warm-transfer-servlets/actions/workflows/gradle.yml/badge.svg)](https://github.com/TwilioDevEd/warm-transfer-servlets/actions/workflows/gradle.yml)

### Prerequisites

1. [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
   installed in your operative system.

2. A Twilio account with a verified [phone number](https://www.twilio.com/console/phone-numbers/incoming). (Get a
   [free account](https://www.twilio.com/try-twilio?utm_campaign=tutorials&utm_medium=readme)
   here.) If you are using a Twilio Trial Account, you can learn all about it
   [here](https://www.twilio.com/help/faq/twilio-basics/how-does-twilios-free-trial-work).

## Run the application

1. Clone the repository and `cd` into it.

1. The application uses Gradle to manage dependencies.

1. Copy the sample configuration file and edit it to match your configuration.
   ```
   cp .env.example .env
   ```
   
1. Once you have edited the `.env` file, if you are using a UNIX operating system,
   just use the `source` command to load the variables into your environment:

   ```bash
   source .env
   ```

   If you are using a different operating system, make sure that all the
   variables from the .env file are loaded into your environment.

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
   ./gradlew appRun
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

Run at the top-level directory.

```bash
./gradlew test
```

## Meta

* No warranty expressed or implied. Software is as is. Diggity.
* [MIT License](http://www.opensource.org/licenses/mit-license.html)
* Lovingly crafted by Twilio Developer Education.
