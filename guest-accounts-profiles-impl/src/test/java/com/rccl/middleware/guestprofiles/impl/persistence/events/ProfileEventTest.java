package com.rccl.middleware.guestprofiles.impl.persistence.events;

import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import com.rccl.middleware.guestprofiles.models.Profile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ProfileEventTest {
    
    @Test
    public void testInheritance() {
        assertTrue(AggregateEvent.class.isAssignableFrom(ProfileEvent.class));
        assertTrue(Jsonable.class.isAssignableFrom(ProfileEvent.class));
    }
    
    @Test
    public void testProfileCreated() {
        assertTrue(ProfileEvent.class.isAssignableFrom(ProfileEvent.ProfileCreated.class));
        assertTrue(CompressedJsonable.class.isAssignableFrom(ProfileEvent.ProfileCreated.class));
        
        ProfileEvent.ProfileCreated event = ProfileEvent.ProfileCreated.builder().build();
        
        assertNull(event.getEntityId());
        assertNull(event.getProfile());
        
        assertEquals(ProfileEventTag.INSTANCE, event.aggregateTag());
        
        String entityId = "7aea2e43-17ab-448a-a561-2b6ba3fec25d";
        Profile profile = Profile.builder().build();
        ProfileEvent.ProfileCreated eventWithValues = ProfileEvent.ProfileCreated
                .builder()
                .profile(profile)
                .entityId(entityId)
                .build();
        
        assertEquals(entityId, eventWithValues.getEntityId());
        assertEquals(profile, eventWithValues.getProfile());
        
        assertEquals(ProfileEventTag.INSTANCE, event.aggregateTag());
    }
    
    @Test
    public void testProfileUpdated() {
        assertTrue(ProfileEvent.class.isAssignableFrom(ProfileEvent.ProfileCreated.class));
        assertTrue(CompressedJsonable.class.isAssignableFrom(ProfileEvent.ProfileCreated.class));
        
        ProfileEvent.ProfileUpdated event = ProfileEvent.ProfileUpdated.builder().build();
        
        assertNull(event.getEntityId());
        assertNull(event.getProfile());
        
        assertEquals(ProfileEventTag.INSTANCE, event.aggregateTag());
        
        String entityId = "7aea2e43-17ab-448a-a561-2b6ba3fec25d";
        Profile profile = Profile.builder().build();
        ProfileEvent.ProfileUpdated eventWithValues = ProfileEvent.ProfileUpdated
                .builder()
                .profile(profile)
                .entityId(entityId)
                .build();
        
        assertEquals(entityId, eventWithValues.getEntityId());
        assertEquals(profile, eventWithValues.getProfile());
        
        assertEquals(ProfileEventTag.INSTANCE, event.aggregateTag());
    }
}
