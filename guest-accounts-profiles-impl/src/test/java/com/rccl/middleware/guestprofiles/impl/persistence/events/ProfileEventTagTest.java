package com.rccl.middleware.guestprofiles.impl.persistence.events;

import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProfileEventTagTest {
    
    @Test
    public void testInstantConstant() {
        assertTrue(AggregateEventTagger.class.isAssignableFrom(ProfileEventTag.INSTANCE.getClass()));
        assertNotNull(ProfileEventTag.INSTANCE);
    }
}
