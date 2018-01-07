package com.rccl.middleware.guestprofiles.impl.persistence.events;


import com.lightbend.lagom.javadsl.persistence.AggregateEvent;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTagger;
import com.lightbend.lagom.serialization.Jsonable;
import com.rccl.middleware.guestprofiles.models.Profile;
import lombok.Value;
import org.codehaus.jackson.annotate.JsonCreator;




public interface ProfileTopicEvent extends Jsonable, AggregateEvent<ProfileTopicEvent> {
    


    @Override
    default AggregateEventTagger<ProfileTopicEvent> aggregateTag() {
        return ProfileTopicEventTag.PROFILE_EVENT_TAG;
    }

    @Value
     final class ProfileCreated implements ProfileTopicEvent {
    
        private static final long serialVersionUID = 1L;

        public final  Profile profile;

        @JsonCreator
        public ProfileCreated(Profile profile) {
            this.profile = profile;
        }
    }
    
    @Value
    final class ProfileUpdated implements ProfileTopicEvent {
    
        private static final long serialVersionUID = 1L;
        
       Profile profile;
        @JsonCreator
        public ProfileUpdated(Profile profile) {
            this.profile = profile;
        }
    }

}
