package com.twilio.warmtransfer.utils;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@PowerMockIgnore("javax.crypto.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest(TwilioAuthenticatedActions.class)
public class TwilioAuthenticatedActionsTest {

    @Test(expected = RuntimeException.class)
    public void raisesIfNoVariablesForEnv() throws Exception {
        // File does not exist, no entries in dotenv
        Dotenv dotenv =  new DotenvBuilder().filename(".env.test").ignoreIfMissing().load();
        new TwilioAuthenticatedActions(null, dotenv);
    }

    @Test
    public void usesAccountSidAndTokenForTwilioCapability() throws Exception {
        Dotenv dotenv =  new DotenvBuilder().filename(".env.example").load();
        String sid = new TwilioAuthenticatedActions(null, dotenv).getTokenForAgent("test");
        assertThat(sid, is(notNullValue()));
    }
}
