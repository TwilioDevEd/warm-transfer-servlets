package com.twilio.warmtransfer.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@PowerMockIgnore("javax.crypto.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(TwilioAuthenticatedActions.class)
public class TwilioAuthenticatedActionsTest {

    @Test(expected = RuntimeException.class)
    public void raisesIfNoAccountSidSetForEnv() throws Exception {
        Map<String, String> env = new HashMap() {{
            put("TWILIO_AUTH_TOKEN", "token");
        }};
        new TwilioAuthenticatedActions(null, env);
    }

    @Test(expected = RuntimeException.class)
    public void raisesIfNoAuthTokenSetForEnv() throws Exception {
        Map<String, String> env = new HashMap() {{
            put("TWILIO_ACCOUNT_SID", "sid");
        }};
        new TwilioAuthenticatedActions(null, env);
    }

    @Test
    public void usesAccountSidAndTokenForTwilioCapability() throws Exception {
        String sid = new TwilioAuthenticatedActions(null, new HashMap<String, String>() {{
            put("TWILIO_ACCOUNT_SID", "sid");
            put("TWILIO_AUTH_TOKEN", "token");
            put("TWILIO_NUMBER", "+1959595");
        }}).getTokenForAgent("test");
        assertThat(sid, is(notNullValue()));
    }
}