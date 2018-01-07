package com.rccl.middleware.guestprofiles.impl.persistence.commands;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.lightbend.lagom.serialization.Jsonable;
import com.rccl.middleware.guestprofiles.models.Profile;
import lombok.Builder;
import lombok.Value;
import org.codehaus.jackson.annotate.JsonCreator;

public interface ProfileCommand extends Jsonable {
    
    @Value
    @Builder
    final class CreateProfile implements ProfileCommand, PersistentEntity.ReplyType<Done> {
        
        private static final long serialVersionUID = 1L;
        
        final Profile profile;

        @JsonCreator
        public CreateProfile(Profile profile) { this.profile = profile; }
    }

    
    @Value
    @Builder
    final class UpdateProfile implements ProfileCommand, PersistentEntity.ReplyType<Done> {
    
        private static final long serialVersionUID = 1L;
        
        private Profile profile;
    }
    
    @Value
    @Builder
    final class UpdateProfileInternal implements ProfileCommand, PersistentEntity.ReplyType<Done> {
    
        private static final long serialVersionUID = 1L;
        
        private Profile profile;
    }
}
