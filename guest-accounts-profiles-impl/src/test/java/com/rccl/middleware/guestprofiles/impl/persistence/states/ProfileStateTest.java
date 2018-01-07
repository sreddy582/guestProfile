package com.rccl.middleware.guestprofiles.impl.persistence.states;

import com.lightbend.lagom.serialization.CompressedJsonable;
import com.rccl.middleware.guestprofiles.models.Profile;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ProfileStateTest {
    
    @Test
    public void testIsCompressedJsonable() {
        assertTrue(CompressedJsonable.class.isAssignableFrom(ProfileState.class));
    }
    
    @Test
    public void testConstructor() {
        Profile profile = Profile.builder().build();
        
        Long timestamp = 8675309L;
        
        ProfileState ps = ProfileState.builder().profile(profile).timestamp(timestamp).build();
        
        Assert.assertEquals(profile, ps.getProfile());
        assertEquals(timestamp, ps.getTimestamp());
    }
    
    @Test
    public void testBuilderWithNoArgsGiven() {
        ProfileState ps = ProfileState.builder().build();
        
        assertNull(ps.getProfile());
        assertNull(ps.getTimestamp());
    }
    
    @Test
    public void testEmptyInstanceConstant() {
        ProfileState ps = ProfileState.EMPTY;
        
        assertNull(ps.getProfile());
        assertNull(ps.getTimestamp());
    }
}
