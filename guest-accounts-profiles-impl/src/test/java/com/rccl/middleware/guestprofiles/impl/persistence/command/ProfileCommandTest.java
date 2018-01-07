package com.rccl.middleware.guestprofiles.impl.persistence.command;

import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.rccl.middleware.guestprofiles.impl.persistence.commands.ProfileCommand;
import com.rccl.middleware.guestprofiles.models.Profile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ProfileCommandTest {
    
    @Test
    public void testCreateProfile() {
        assertTrue(PersistentEntity.ReplyType.class.isAssignableFrom(ProfileCommand.CreateProfile.class));
        
        ProfileCommand.CreateProfile cmd = ProfileCommand.CreateProfile.builder().build();
        assertNull(cmd.getProfile());
        
        Profile profile = Profile.builder().build();
        cmd = ProfileCommand.CreateProfile.builder().profile(profile).build();
        assertEquals(profile, cmd.getProfile());
    }
    
    @Test
    public void testUpdateProfile() {
        assertTrue(PersistentEntity.ReplyType.class.isAssignableFrom(ProfileCommand.UpdateProfile.class));
        
        ProfileCommand.UpdateProfile cmd = ProfileCommand.UpdateProfile.builder().build();
        assertNull(cmd.getProfile());
        
        Profile profile = Profile.builder().build();
        cmd = ProfileCommand.UpdateProfile.builder().profile(profile).build();
        assertEquals(profile, cmd.getProfile());
    }
}
