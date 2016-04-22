package com.twilio.warmtransfer.utils;

import com.twilio.sdk.client.TwilioCapability;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.whenNew;


@RunWith(PowerMockRunner.class)
@PrepareForTest(TwilioCapabilityBuilder.class)
public class TwilioCapabilityBuilderTest {

    @Test(expected = Exception.class)
    public void raisesIfNoAccountSidSetForEnv() throws Exception {
        Map<String, String> env = new HashMap(){{
            put("TWILIO_AUTH_TOKEN", "token");
        }};
        new TwilioCapabilityBuilder(env);
    }

    @Test(expected = Exception.class)
    public void raisesIfNoAuthTokenSetForEnv() throws Exception {
        Map<String, String> env = new HashMap(){{
            put("TWILIO_ACCOUNT_SID", "sid");
        }};
        new TwilioCapabilityBuilder(env);
    }

    @Test
    public void usesAccountSidAndTokenForTwilioCapability() throws Exception {
        TwilioCapability mockedCapability = mock(TwilioCapability.class);
        whenNew(TwilioCapability.class).withArguments("sid", "token").thenReturn(mockedCapability);

        Map<String, String> env = new HashMap(){{
            put("TWILIO_ACCOUNT_SID", "sid");
            put("TWILIO_AUTH_TOKEN", "token");
        }};
        new TwilioCapabilityBuilder(env).getTokenForAgent("test");

        verify(mockedCapability).allowClientIncoming("test");
        verify(mockedCapability).generateToken();
        verifyNew(TwilioCapability.class).withArguments("sid", "token");
    }
}