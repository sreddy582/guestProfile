package com.rccl.middleware.guestprofiles;

import akka.NotUsed;
import com.fasterxml.jackson.databind.node.TextNode;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.rccl.middleware.common.response.ResponseBody;
import com.rccl.middleware.guestprofiles.models.Profile;
import com.rccl.middleware.guestprofiles.events.GuestTopicEvent;

import com.typesafe.config.ConfigFactory;

import static com.lightbend.lagom.javadsl.api.Service.named;
import static com.lightbend.lagom.javadsl.api.Service.restCall;
import static com.lightbend.lagom.javadsl.api.Service.topic;
import static com.lightbend.lagom.javadsl.api.transport.Method.GET;
import static com.lightbend.lagom.javadsl.api.transport.Method.POST;
import static com.lightbend.lagom.javadsl.api.transport.Method.PUT;

public interface GuestProfilesService extends Service {
    String CREATE_PROFILE_KAFKA_TOPIC = ConfigFactory.load().getString("kafka.create-profile.topic.name");

   // String UPDATE_PROFILE_KAFKA_TOPIC = ConfigFactory.load().getString("kafka.update-profile.topic.name");



    ServiceCall<Profile, ResponseBody<TextNode>> createProfile();
    
    ServiceCall<Profile, ResponseBody<TextNode>> updateProfile();
    
    ServiceCall<Profile, ResponseBody<TextNode>> updateProfileInternal();
    
    ServiceCall<NotUsed, ResponseBody<Profile>> getProfile(String vdsId);
    
    ServiceCall<NotUsed, ResponseBody<String>> performHealthCheck();

    Topic<GuestTopicEvent> createProfileTopic();

   // Topic<Profile> updateProfileTopic();


    @Override
    default Descriptor descriptor() {
        return named("guest-profiles")
                .withCalls(
                        restCall(POST, "/guestProfiles", this::createProfile),
                        restCall(POST, "/guestProfiles/", this::createProfile),
                        restCall(GET, "/guestProfiles/health", this::performHealthCheck),
                        restCall(GET, "/guestProfiles/:vdsId", this::getProfile),
                        restCall(PUT, "/guestProfiles", this::updateProfile),
                        restCall(PUT, "/guestProfiles/", this::updateProfile),
                        restCall(GET, "/guestProfiles/:vdsId/internal", this::getProfile),
                        restCall(PUT, "/guestProfiles/internal", this::updateProfileInternal),
                        restCall(PUT, "/guestProfiles/internal/", this::updateProfileInternal)
                )
                .withTopics(
                        topic(CREATE_PROFILE_KAFKA_TOPIC, this::createProfileTopic)
                       // topic(UPDATE_PROFILE_KAFKA_TOPIC, this::updateProfileTopic)
                       )
                .withAutoAcl(true);
    }
}
