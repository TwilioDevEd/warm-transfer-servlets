package com.twilio.warmtransfer.utils;

import com.twilio.sdk.client.TwilioCapability;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;


@RunWith(PowerMockRunner.class)
@PrepareForTest(TwilioAuthenticatedActions.class)
public class TwilioAuthenticatedActionsTest {

    @Test(expected = RuntimeException.class)
    public void raisesIfNoAccountSidSetForEnv() throws Exception {
        Map<String, String> env = new HashMap(){{
            put("TWILIO_AUTH_TOKEN", "token");
        }};
        new TwilioAuthenticatedActions(env);
    }

    @Test(expected = RuntimeException.class)
    public void raisesIfNoAuthTokenSetForEnv() throws Exception {
        Map<String, String> env = new HashMap(){{
            put("TWILIO_ACCOUNT_SID", "sid");
        }};
        new TwilioAuthenticatedActions(env);
    }

    @Test
    public void usesAccountSidAndTokenForTwilioCapability() throws Exception {
        TwilioCapability mockedCapability = mock(TwilioCapability.class);
        whenNew(TwilioCapability.class).withArguments("sid", "token").thenReturn(mockedCapability);

        Map<String, String> env = new HashMap(){{
            put("TWILIO_ACCOUNT_SID", "sid");
            put("TWILIO_AUTH_TOKEN", "token");
            put("TWILIO_NUMBER", "+1959595");
        }};
        new TwilioAuthenticatedActions(env).getTokenForAgent("test");

        verify(mockedCapability).allowClientIncoming("test");
        verify(mockedCapability).generateToken();
        verifyNew(TwilioCapability.class).withArguments("sid", "token");
    }
}