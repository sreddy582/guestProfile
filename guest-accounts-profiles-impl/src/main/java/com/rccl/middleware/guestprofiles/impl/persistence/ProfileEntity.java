package com.rccl.middleware.guestprofiles.impl.persistence;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.PersistentEntity;
import com.rccl.middleware.guestprofiles.impl.persistence.commands.ProfileCommand;
import com.rccl.middleware.guestprofiles.impl.persistence.events.ProfileEvent;
import com.rccl.middleware.guestprofiles.impl.persistence.states.ProfileState;

import java.time.Instant;
import java.util.Optional;

public class ProfileEntity extends PersistentEntity<ProfileCommand, ProfileEvent, ProfileState> {
    
    @SuppressWarnings("unchecked")
    @Override
    public Behavior initialBehavior(Optional<ProfileState> snapshotState) {
        ProfileState initialState = snapshotState.orElse(ProfileState.EMPTY);
        
        BehaviorBuilder behaviorBuilder = super.newBehaviorBuilder(initialState);
        
        behaviorBuilder.setCommandHandler(ProfileCommand.CreateProfile.class, (cmd, ctx) -> {
            ProfileEvent.ProfileCreated pc = ProfileEvent.ProfileCreated.builder()
                    .profile(cmd.getProfile())
                    .entityId(entityId())
                    .build();
            
            return ctx.thenPersist(pc, (evt) -> ctx.reply(Done.getInstance()));
        });
        
        behaviorBuilder.setEventHandler(ProfileEvent.ProfileCreated.class, evt ->
                ProfileState.builder()
                        .profile(evt.getProfile())
                        .timestamp(Instant.now().toEpochMilli())
                        .build()
        );
        
        behaviorBuilder.setCommandHandler(ProfileCommand.UpdateProfile.class, (cmd, ctx) -> {
            ProfileEvent.ProfileUpdated pc = ProfileEvent.ProfileUpdated.builder()
                    .profile(cmd.getProfile())
                    .entityId(entityId())
                    .build();
            
            return ctx.thenPersist(pc, (evt) -> ctx.reply(Done.getInstance()));
        });
        
        behaviorBuilder.setEventHandler(ProfileEvent.ProfileUpdated.class, evt ->
                ProfileState.builder()
                        .profile(evt.getProfile())
                        .timestamp(Instant.now().toEpochMilli())
                        .build()
        );
        
        behaviorBuilder.setCommandHandler(ProfileCommand.UpdateProfileInternal.class, (cmd, ctx) -> {
            ProfileEvent.ProfileUpdatedInternally pc = ProfileEvent.ProfileUpdatedInternally.builder()
                    .profile(cmd.getProfile())
                    .entityId(entityId())
                    .build();
            
            return ctx.thenPersist(pc, (evt) -> ctx.reply(Done.getInstance()));
        });
        
        return behaviorBuilder.build();
    }
}
