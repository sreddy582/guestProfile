package com.rccl.middleware.guestprofiles.impl.persistence.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.CompressedJsonable;
import com.lightbend.lagom.serialization.Jsonable;
import com.rccl.middleware.guestprofiles.models.Profile;
import lombok.Builder;
import lombok.Value;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "action", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(ProfileEvent.ProfileCreated.class),
        @JsonSubTypes.Type(ProfileEvent.ProfileUpdated.class)})
public interface ProfileEvent extends Jsonable, AggregateEvent<ProfileEvent> {
    
    @Override
    default AggregateEventTagger<ProfileEvent> aggregateTag() {
        return ProfileEventTag.INSTANCE;
    }


    String getEntityId();
    
    Profile getProfile();
    
    @Value
    @Builder
    @JsonTypeName("created")
    final class ProfileCreated implements ProfileEvent, CompressedJsonable {
    
        private static final long serialVersionUID = 1L;
        
        String entityId;
        
        Profile profile;
    }
    
    @Value
    @Builder
    @JsonTypeName("updated")
    final class ProfileUpdated implements ProfileEvent, CompressedJsonable {
    
        private static final long serialVersionUID = 1L;
        
        String entityId;
        
        Profile profile;
    }
    
    @Value
    @Builder
    @JsonTypeName("updatedInternally")
    final class ProfileUpdatedInternally implements ProfileEvent, CompressedJsonable {
    
        private static final long serialVersionUID = 1L;
        
        String entityId;
        
        Profile profile;
    }
}
