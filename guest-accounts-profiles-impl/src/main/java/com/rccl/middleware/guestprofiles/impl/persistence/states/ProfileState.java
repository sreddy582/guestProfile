package com.rccl.middleware.guestprofiles.impl.persistence.states;

import com.lightbend.lagom.serialization.CompressedJsonable;
import com.rccl.middleware.guestprofiles.models.Profile;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProfileState implements CompressedJsonable {
    
    public static final ProfileState EMPTY = ProfileState.builder().build();
    
    private static final long serialVersionUID = 1L;
    
    private Profile profile;
    
    private Long timestamp;
}
