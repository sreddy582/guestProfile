package com.rccl.middleware.guestprofiles.impl.persistence.events;

import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;

public class ProfileEventTag {

    private static final int SHARDS = 4;

    public static final AggregateEventTag<ProfileEvent> INSTANCE = AggregateEventTag.of(ProfileEvent.class);


    public static final AggregateEventShards<ProfileEvent> PROFILE_EVENT_TAG =
            AggregateEventTag.sharded(ProfileEvent.class, "ProfileEvent", SHARDS);

}
