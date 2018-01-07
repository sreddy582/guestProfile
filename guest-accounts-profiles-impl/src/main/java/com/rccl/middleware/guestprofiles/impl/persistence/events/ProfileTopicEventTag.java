package com.rccl.middleware.guestprofiles.impl.persistence.events;

import com.lightbend.lagom.javadsl.persistence.AggregateEventShards;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;

public class ProfileTopicEventTag {

    private static final int SHARDS = 4;


    public static final AggregateEventShards<ProfileTopicEvent> PROFILE_EVENT_TAG =
            AggregateEventTag.sharded(ProfileTopicEvent.class, "ProfileTopicEvent", SHARDS);

}
