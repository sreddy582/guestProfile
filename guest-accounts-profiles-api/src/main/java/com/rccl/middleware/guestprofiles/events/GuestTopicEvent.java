package com.rccl.middleware.guestprofiles.events;

import akka.japi.Pair;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import java.util.concurrent.CompletableFuture;
import com.rccl.middleware.guestprofiles.models.Profile;
import lombok.Value;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(GuestTopicEvent.ProfileCreated.class),
        @JsonSubTypes.Type(GuestTopicEvent.ProfileUpdated.class)})


public interface GuestTopicEvent {

    
    @Value
    @JsonTypeName("created")
    final class ProfileCreated implements GuestTopicEvent{

      private final Profile profile;

      @JsonCreator
      public ProfileCreated(Profile profile) {
            this.profile = profile;
        }

    }
    
    @Value
    @JsonTypeName("updated")
    final class ProfileUpdated implements GuestTopicEvent{

    private final Profile profile;

        @JsonCreator
        public ProfileUpdated(Profile profile) {
            this.profile = profile;
        }

    }
    

}


