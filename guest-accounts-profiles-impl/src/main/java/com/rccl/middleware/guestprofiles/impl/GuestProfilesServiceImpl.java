package com.rccl.middleware.guestprofiles.impl;

import akka.Done;
import akka.NotUsed;
import akka.japi.Pair;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.databind.node.TextNode;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.broker.Topic;
import com.lightbend.lagom.javadsl.api.transport.ResponseHeader;
import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.lightbend.lagom.javadsl.broker.TopicProducer;
import com.lightbend.lagom.javadsl.persistence.PersistentEntityRegistry;
import com.lightbend.lagom.javadsl.persistence.ReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import com.lightbend.lagom.javadsl.server.HeaderServiceCall;
import com.rccl.middleware.common.beans.Gender;
import com.rccl.middleware.common.exceptions.MiddlewareTransportException;
import com.rccl.middleware.common.response.ResponseBody;
import com.rccl.middleware.common.validation.MiddlewareValidation;
import com.rccl.middleware.guest.optin.EmailOptins;
import com.rccl.middleware.guest.optin.GuestProfileOptinService;
import com.rccl.middleware.guest.optin.PostalOptins;
import com.rccl.middleware.guestprofiles.GuestProfilesService;
import com.rccl.middleware.guestprofiles.events.GuestTopicEvent;
import com.rccl.middleware.guestprofiles.exceptions.NoSuchProfileException;
import com.rccl.middleware.guestprofiles.exceptions.ProfileAlreadyExistsException;
import com.rccl.middleware.guestprofiles.impl.persistence.Constants;
import com.rccl.middleware.guestprofiles.impl.persistence.ProfileEntity;
import com.rccl.middleware.guestprofiles.impl.persistence.events.ProfileEvent;
import com.rccl.middleware.guestprofiles.impl.persistence.events.ProfileEventProcessor;
import com.rccl.middleware.guestprofiles.impl.persistence.events.ProfileEventTag;
import com.rccl.middleware.guestprofiles.impl.persistence.events.ProfileTopicEventTag;
import com.rccl.middleware.guestprofiles.impl.persistence.events.ProfileTopicEvent;
import com.rccl.middleware.guestprofiles.impl.persistence.commands.ProfileCommand;
import com.rccl.middleware.guestprofiles.models.Address;
import com.rccl.middleware.guestprofiles.models.CaptainsClubLoyaltyTier;
import com.rccl.middleware.guestprofiles.models.CelebrityBlueChipLoyaltyTier;
import com.rccl.middleware.guestprofiles.models.ClubRoyaleLoyaltyTier;
import com.rccl.middleware.guestprofiles.models.CrownAndAnchorSocietyLoyaltyTier;
import com.rccl.middleware.guestprofiles.models.EmergencyContact;
import com.rccl.middleware.guestprofiles.models.Profile;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static com.rccl.middleware.guestprofiles.impl.persistence.Constants.Column;
import static org.apache.commons.lang3.StringUtils.upperCase;

public class GuestProfilesServiceImpl implements GuestProfilesService {
    
    private static final String APPKEY_HEADER = "AppKey";
    
    private static final String DEFAULT_APP_KEY = ConfigFactory.load().getString("default.apigee.appkey");
    
    private static final String EXTERNAL_USER = "external_guest_profile";
    
    private static final String INTERNAL_USER = "internal_guest_profile";

    private final PersistentEntityRegistry registry;

    private final CassandraSession session;
    
    private final GuestProfileOptinService optinsService;
    
    private PreparedStatement writeProfileStatement;
    
    private PreparedStatement updateProfileStatement;
    
    @Inject
    public GuestProfilesServiceImpl(ReadSide readSide,
                                    PersistentEntityRegistry registry,
                                    CassandraSession session,
                                    GuestProfileOptinService optinsService) {
        this.registry = registry;
        this.session = session;
        
        this.optinsService = optinsService;
        
        CompletionStage<PreparedStatement> updatePrepareFuture = session.prepare(Constants.UPDATE_QUERY);
        
        session.prepare(Constants.INSERT_QUERY)
                .thenCombineAsync(updatePrepareFuture, (writePrepare, updatePrepare) -> {
                    this.writeProfileStatement = writePrepare;
                    this.updateProfileStatement = updatePrepare;
                    
                    return Done.getInstance();
                });
        
        registry.register(ProfileEntity.class);
        readSide.register(ProfileEventProcessor.class);
    }
    
    @Override
    public HeaderServiceCall<Profile, ResponseBody<TextNode>> createProfile() {
        return (requestHeader, profile) -> {
            MiddlewareValidation.validate(profile);
            
            return checkForExistingProfile(profile).thenCompose(answer -> {
                boolean isExistingProfile = answer.first();
                
                if (isExistingProfile) {
                    throw new ProfileAlreadyExistsException();
                }
               registry.refFor(ProfileEntity.class, profile.getVdsId())
                       .ask(new ProfileCommand.CreateProfile(Profile.builder().build()));

                return session.executeWrite(this.generateCreateQuery(profile))
                        .exceptionally(throwable -> {
                            Throwable cause = throwable.getCause();
                            throw new MiddlewareTransportException(TransportErrorCode.fromHttp(500), cause);
                        })
                        .thenApply(done -> {
                            TextNode profileVdsId = TextNode.valueOf(profile.getVdsId());
                            ResponseBody<TextNode> response = ResponseBody.<TextNode>builder()
                                    .status(201)
                                    .payload(profileVdsId)
                                    .build();
                            return Pair.create(ResponseHeader.OK.withStatus(201), response);
                        });
            });
        };
    }
    @Override
    public Topic<GuestTopicEvent> createProfileTopic() {
        System.out.println("am i coming here");
        return TopicProducer.taggedStreamWithOffset(ProfileTopicEventTag.PROFILE_EVENT_TAG.allTags(), (tag, offset) ->
                registry.eventStream(tag, offset)
                        .filter(param -> param.first() instanceof ProfileTopicEvent.ProfileCreated
                                || param.first() instanceof ProfileTopicEvent.ProfileUpdated)
                        .mapAsync(1, eventOffset -> {
                            ProfileTopicEvent event = eventOffset.first();
                            GuestTopicEvent guestTopicEvent;
                            if (event instanceof ProfileTopicEvent.ProfileCreated) {
                                ProfileTopicEvent.ProfileCreated eventInstance = (ProfileTopicEvent.ProfileCreated) event;
                                guestTopicEvent = new GuestTopicEvent.ProfileCreated(eventInstance.getProfile());

                            } else {
                                ProfileTopicEvent.ProfileUpdated eventInstance = (ProfileTopicEvent.ProfileUpdated) event;
                                guestTopicEvent = new GuestTopicEvent.ProfileUpdated(eventInstance.getProfile());
                            }
                            //return new Pair<>(guestTopicEvent, eventOffset.second());
                            return CompletableFuture.completedFuture(new Pair<>(guestTopicEvent, eventOffset.second()));
                        }));
    }


    @Override
    public HeaderServiceCall<Profile, ResponseBody<TextNode>> updateProfile() {
        return (requestHeader, profile) -> {
            MiddlewareValidation.validate(profile);
            return this.executeProfileUpdate(profile, false);
        };
    }
    
    @Override
    public ServiceCall<NotUsed, ResponseBody<Profile>> getProfile(String id) {
        return notUsed -> {
            Profile profileId = Profile.builder().vdsId(id).build();
            
            return checkForExistingProfile(profileId).thenApply(answer -> {
                boolean isExistingProfile = answer.first();
                
                if (!isExistingProfile) {
                    throw new NoSuchProfileException();
                }
                
                Profile profile = answer.second();
                
                return ResponseBody.<Profile>builder().payload(profile).build();
            });
        };
    }
    
    @Override
    public HeaderServiceCall<NotUsed, ResponseBody<String>> performHealthCheck() {
        return (requestHeader, notUsed) -> {
            String quote = "Here's to tall ships. "
                    + "Here's to small ships. "
                    + "Here's to all the ships on the sea. "
                    + "But the best ships are friendships, so here's to you and me!";
            
            ResponseBody<String> response = ResponseBody.<String>builder()
                    .payload(quote)
                    .build();
            
            Pair<ResponseHeader, ResponseBody<String>> pair = Pair.create(ResponseHeader.OK, response);
            
            return CompletableFuture.completedFuture(pair);
        };
    }
    
    @Override
    public HeaderServiceCall<Profile, ResponseBody<TextNode>> updateProfileInternal() {
        return (requestHeader, profile) -> {
            MiddlewareValidation.validate(profile);
            MiddlewareValidation.validateWithGroups(profile.getEmailOptins(), EmailOptins.DefaultChecks.class);
            MiddlewareValidation.validateWithGroups(profile.getPostalOptins(), PostalOptins.DefaultChecks.class);
            
            return this
                    .executeProfileUpdate(profile, true)
                    .thenCompose(pair -> {
                        
                        String appKey = requestHeader.getHeader(APPKEY_HEADER).orElse(DEFAULT_APP_KEY);
                        
                        CompletionStage<ResponseBody> emailOptinStage = null;
                        if (profile.getEmailOptins() != null
                                && StringUtils.isNotBlank(profile.getEmailOptins().getEmail())
                                && !CollectionUtils.isEmpty(profile.getEmailOptins().getOptins())) {
                            emailOptinStage = optinsService
                                    .updateEmailOptinsInternal(profile.getEmailOptins().getEmail())
                                    .handleRequestHeader(rh -> rh.withHeader(APPKEY_HEADER, appKey))
                                    .invoke(profile.getEmailOptins())
                                    .exceptionally(throwable -> {
                                        throw new MiddlewareTransportException(
                                                TransportErrorCode.InternalServerError, throwable.getCause());
                                    });
                        }
                        
                        CompletionStage<ResponseBody> postalOptinsStage = null;
                        if (profile.getPostalOptins() != null
                                && !CollectionUtils.isEmpty(profile.getPostalOptins().getOptins())) {
                            postalOptinsStage = optinsService
                                    .updatePostalOptinsInternal(profile.getVdsId())
                                    .handleRequestHeader(rh -> rh.withHeader(APPKEY_HEADER, appKey))
                                    .invoke(profile.getPostalOptins())
                                    .exceptionally(throwable -> {
                                        throw new MiddlewareTransportException(
                                                TransportErrorCode.InternalServerError, throwable.getCause());
                                    });
                        }
                        
                        if (emailOptinStage != null && postalOptinsStage != null) {
                            return emailOptinStage
                                    .thenCombine(postalOptinsStage, (email, postal) -> pair);
                        } else if (emailOptinStage != null) {
                            return emailOptinStage.thenApply(responseBody -> pair);
                        } else if (postalOptinsStage != null) {
                            return postalOptinsStage.thenApply(responseBody -> pair);
                        }
                        
                        return CompletableFuture.completedFuture(pair);
                    });
        };
    }
    
    /**
     * Triggers a Cassandra update process to a record based on the given {@link Profile} model values.
     * At the same time, checks if the data requested is not yet existing, then does an insert instead.
     *
     * @param profile    the {@link Profile} request object.
     * @param isInternal determines if the service call is coming from an external app or internal system.
     * @return {@link CompletionStage}<{@link Pair}<{@link ResponseHeader}, {@link ResponseBody}<{@link TextNode}>>>
     */
    private CompletionStage<Pair<ResponseHeader, ResponseBody<TextNode>>> executeProfileUpdate(Profile profile,
                                                                                               boolean isInternal) {
        return checkForExistingProfile(profile).thenCompose(answer -> {
            boolean isExistingProfile = answer.first();
            
            BoundStatement boundStatement;
            if (!isExistingProfile) {
                boundStatement = generateCreateQuery(profile);
            } else {
                boundStatement = generateUpdateQuery(profile, isInternal);
            }
            
            return session.executeWrite(boundStatement)
                    .exceptionally(throwable -> {
                        Throwable cause = throwable.getCause();
                        throw new MiddlewareTransportException(TransportErrorCode.fromHttp(500), cause);
                    })
                    .thenApply(done -> {
                        TextNode profileVdsId = TextNode.valueOf(profile.getVdsId());
                        ResponseBody<TextNode> response = ResponseBody.<TextNode>builder()
                                .payload(profileVdsId)
                                .build();
                        return Pair.create(ResponseHeader.OK, response);
                    });
        });
    }
    
    private CompletionStage<Pair<Boolean, Profile>> checkForExistingProfile(Profile profile) {
        return session.selectAll(Constants.SELECT_QUERY, profile.getVdsId()).thenApply(rows -> {
            Boolean isExistingProfile = !rows.isEmpty();
            Profile.ProfileBuilder builder = Profile.builder();
            
            if (isExistingProfile) {
                Row row = rows.get(0);
                
                Address address = Address.builder()
                        .addressOne(row.getString(Column.ADDRESS_ONE))
                        .addressTwo(row.getString(Column.ADDRESS_TWO))
                        .city(row.getString(Column.ADDRESS_CITY))
                        .state(row.getString(Column.ADDRESS_STATE))
                        .zipCode(row.getString(Column.ADDRESS_ZIP))
                        .residencyCountryCode(row.getString(Column.RESIDENCY_COUNTRY_CODE))
                        .build();
                builder.address(address);
                
                String avatar = row.getString(Column.AVATAR);
                builder.avatar(avatar);
                
                builder.birthCountryCode(row.getString(Column.BIRTH_COUNTRY_CODE));
                
                builder.citizenshipCountryCode(row.getString(Column.CITIZENSHIP_COUNTRY_CODE));
                
                String captainsClubLoyaltyTier = row.getString(Constants.Column.CAPTAINS_CLUB_LOYALTY_TIER);
                Integer captainsClubIndividualPoints = row.getInt(Column.CAPTAINS_CLUB_LOYALTY_INDIVIDUAL_POINTS);
                Integer captainsClubRelationshipPoints = row.getInt(Column.CAPTAINS_CLUB_LOYALTY_RELATIONSHIP_POINTS);
                
                builder.captainsClubLoyaltyTier(CaptainsClubLoyaltyTier.fromValue(captainsClubLoyaltyTier))
                        .captainsClubLoyaltyIndividualPoints(captainsClubIndividualPoints)
                        .captainsClubLoyaltyRelationshipPoints(captainsClubRelationshipPoints);
                
                String blueChipLoyaltyTier = row.getString(Column.CELEBRITY_BLUE_CHIP_LOYALTY_TIER);
                Integer blueChipIndividualPoints = row.getInt(Column.CELEBRITY_BLUE_CHIP_LOYALTY_INDIVIDUAL_POINTS);
                Integer blueChipRelationshipPoints = row.getInt(Column.CELEBRITY_BLUE_CHIP_LOYALTY_RELATIONSHIP_POINTS);
                
                builder.celebrityBlueChipLoyaltyTier(CelebrityBlueChipLoyaltyTier.fromValue(blueChipLoyaltyTier))
                        .celebrityBlueChipLoyaltyIndividualPoints(blueChipIndividualPoints)
                        .celebrityBlueChipLoyaltyRelationshipPoints(blueChipRelationshipPoints);
                
                String clubRoyaleLoyaltyTier = row.getString(Column.CLUB_ROYALE_LOYALTY_TIER);
                Integer clubRoyaleIndividualPoints = row.getInt(Column.CLUB_ROYALE_LOYALTY_INDIVIDUAL_POINTS);
                Integer clubRoyaleRelationshipPoints = row.getInt(Column.CLUB_ROYALE_LOYALTY_RELATIONSHIP_POINTS);
                
                builder.clubRoyaleLoyaltyTier(ClubRoyaleLoyaltyTier.fromValue(clubRoyaleLoyaltyTier))
                        .clubRoyaleLoyaltyIndividualPoints(clubRoyaleIndividualPoints)
                        .clubRoyaleLoyaltyRelationshipPoints(clubRoyaleRelationshipPoints);
                
                String crownAndAnchorLoyaltyTier = row.getString(Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_TIER);
                Integer crownAndAnchorIndividualPoints =
                        row.getInt(Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_INDIVIDUAL_POINTS);
                Integer crownAndAnchorRelationshipPoints =
                        row.getInt(Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_RELATIONSHIP_POINTS);
                
                builder.crownAndAnchorSocietyLoyaltyTier(CrownAndAnchorSocietyLoyaltyTier
                        .fromValue(crownAndAnchorLoyaltyTier))
                        .crownAndAnchorSocietyLoyaltyIndividualPoints(crownAndAnchorIndividualPoints)
                        .crownAndAnchorSocietyLoyaltyRelationshipPoints(crownAndAnchorRelationshipPoints);
                
                EmergencyContact emergencyContact = EmergencyContact.builder()
                        .firstName(row.getString(Column.EMERGENCY_CONTACT_FIRST_NAME))
                        .lastName(row.getString(Column.EMERGENCY_CONTACT_LAST_NAME))
                        .phoneNumber(row.getString(Column.EMERGENCY_CONTACT_PHONE))
                        .relationship(row.getString(Column.EMERGENCY_CONTACT_RELATIONSHIP))
                        .build();
                builder.emergencyContact(emergencyContact);
                
                String gender = row.getString(Constants.Column.GENDER);
                builder.gender(Gender.get(gender));
                
                String nickname = row.getString(Constants.Column.NICKNAME);
                builder.nickname(nickname);
                
                String vdsId = row.getString(Column.VDS_ID);
                builder.vdsId(vdsId);
            }
            
            return Pair.create(isExistingProfile, builder.build());
        });
    }
    
    /**
     * Sets all available {@link Profile} attributes into a {@link BoundStatement}
     * in preparation for write execution.
     *
     * @param p the {@link Profile} model.
     * @return {@link BoundStatement}
     */
    private BoundStatement generateCreateQuery(Profile p) {
        // fallback when the service starts up and prepared statement is still not set.
        if (writeProfileStatement == null) {
            writeProfileStatement = session.prepare(Constants.INSERT_QUERY).toCompletableFuture().join();
        }
        
        BoundStatement bs = writeProfileStatement.bind();
        
        if (p.getAddress() != null) {
            Address a = p.getAddress();
            
            bs.setString(Column.ADDRESS_ONE, a.getAddressOne())
                    .setString(Column.ADDRESS_TWO, a.getAddressTwo())
                    .setString(Column.ADDRESS_CITY, a.getCity())
                    .setString(Column.ADDRESS_STATE, a.getState())
                    .setString(Column.ADDRESS_ZIP, a.getZipCode())
                    .setString(Column.RESIDENCY_COUNTRY_CODE, a.getResidencyCountryCode());
        }
        
        bs.setString(Column.AVATAR, p.getAvatar())
                .setString(Column.BIRTH_COUNTRY_CODE, p.getBirthCountryCode())
                .setString(Column.CITIZENSHIP_COUNTRY_CODE, p.getCitizenshipCountryCode());
        
        if (p.getCaptainsClubLoyaltyTier() != null) {
            bs.setString(Column.CAPTAINS_CLUB_LOYALTY_TIER, p.getCaptainsClubLoyaltyTier().getFullName());
        }
        
        if (p.getCaptainsClubLoyaltyIndividualPoints() != null) {
            bs.setInt(Column.CAPTAINS_CLUB_LOYALTY_INDIVIDUAL_POINTS, p.getCaptainsClubLoyaltyIndividualPoints());
        }
        
        if (p.getCaptainsClubLoyaltyRelationshipPoints() != null) {
            bs.setInt(Column.CAPTAINS_CLUB_LOYALTY_RELATIONSHIP_POINTS, p.getCaptainsClubLoyaltyRelationshipPoints());
        }
        
        if (p.getCelebrityBlueChipLoyaltyTier() != null) {
            bs.setString(Column.CELEBRITY_BLUE_CHIP_LOYALTY_TIER, p.getCelebrityBlueChipLoyaltyTier().getFullName());
        }
        
        if (p.getCelebrityBlueChipLoyaltyIndividualPoints() != null) {
            bs.setInt(Column.CELEBRITY_BLUE_CHIP_LOYALTY_INDIVIDUAL_POINTS,
                    p.getCelebrityBlueChipLoyaltyIndividualPoints());
        }
        
        if (p.getCelebrityBlueChipLoyaltyRelationshipPoints() != null) {
            bs.setInt(Column.CELEBRITY_BLUE_CHIP_LOYALTY_RELATIONSHIP_POINTS,
                    p.getCelebrityBlueChipLoyaltyRelationshipPoints());
        }
        
        if (p.getClubRoyaleLoyaltyTier() != null) {
            bs.setString(Column.CLUB_ROYALE_LOYALTY_TIER, p.getClubRoyaleLoyaltyTier().getFullName());
        }
        
        if (p.getClubRoyaleLoyaltyIndividualPoints() != null) {
            bs.setInt(Column.CLUB_ROYALE_LOYALTY_INDIVIDUAL_POINTS, p.getClubRoyaleLoyaltyIndividualPoints());
        }
        
        if (p.getClubRoyaleLoyaltyRelationshipPoints() != null) {
            bs.setInt(Column.CLUB_ROYALE_LOYALTY_RELATIONSHIP_POINTS, p.getClubRoyaleLoyaltyRelationshipPoints());
        }
        
        if (p.getCrownAndAnchorSocietyLoyaltyTier() != null) {
            bs.setString(Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_TIER,
                    p.getCrownAndAnchorSocietyLoyaltyTier().getFullName());
        }
        
        if (p.getCrownAndAnchorSocietyLoyaltyIndividualPoints() != null) {
            bs.setInt(Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_INDIVIDUAL_POINTS,
                    p.getCrownAndAnchorSocietyLoyaltyIndividualPoints());
        }
        
        if (p.getCrownAndAnchorSocietyLoyaltyRelationshipPoints() != null) {
            bs.setInt(Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_RELATIONSHIP_POINTS,
                    p.getCrownAndAnchorSocietyLoyaltyRelationshipPoints());
        }
        
        if (p.getEmergencyContact() != null) {
            EmergencyContact ec = p.getEmergencyContact();
            
            bs.setString(Column.EMERGENCY_CONTACT_FIRST_NAME, ec.getFirstName())
                    .setString(Column.EMERGENCY_CONTACT_LAST_NAME, ec.getLastName())
                    .setString(Column.EMERGENCY_CONTACT_PHONE, ec.getPhoneNumber())
                    .setString(Column.EMERGENCY_CONTACT_RELATIONSHIP, ec.getRelationship());
        }
        
        if (p.getGender() != null) {
            bs.setString(Column.GENDER, p.getGender().getValue());
        }
        
        bs.setTimestamp(Column.CREATION_TIMESTAMP, DateTime.now().toDate())
                .setString(Column.CREATION_USERID, EXTERNAL_USER)
                .setTimestamp(Column.LAST_UPDATED, DateTime.now().toDate())
                .setString(Column.UPDATE_USERID, EXTERNAL_USER)
                .setString(Column.NICKNAME, p.getNickname())
                .setString(Column.VDS_ID, p.getVdsId());
        
        return bs;
    }
    
    /**
     * Sets all available {@link Profile} attributes into a {@link BoundStatement}
     * in preparation for update execution.
     *
     * @param p          the {@link Profile} model.
     * @param isInternal determines if the request is coming from external or internal source.
     * @return {@link BoundStatement}
     */
    private BoundStatement generateUpdateQuery(Profile p, boolean isInternal) {
        // fallback when the service starts up and prepared statement is still not set.
        if (updateProfileStatement == null) {
            updateProfileStatement = session.prepare(Constants.UPDATE_QUERY).toCompletableFuture().join();
        }
        
        BoundStatement bs = updateProfileStatement.bind();
        
        if (p.getAddress() != null) {
            Address address = p.getAddress();
            
            if (address.getAddressOne() != null) {
                bs.setString(Column.ADDRESS_ONE, address.getAddressOne());
            }
            
            if (address.getAddressTwo() != null) {
                bs.setString(Column.ADDRESS_TWO, address.getAddressTwo());
            }
            
            if (address.getCity() != null) {
                bs.setString(Column.ADDRESS_CITY, address.getCity());
            }
            
            if (address.getState() != null) {
                bs.setString(Column.ADDRESS_STATE, address.getState());
            }
            
            if (address.getZipCode() != null) {
                bs.setString(Column.ADDRESS_ZIP, address.getZipCode());
            }
            
            if (address.getResidencyCountryCode() != null) {
                bs.setString(Column.RESIDENCY_COUNTRY_CODE, upperCase(address.getResidencyCountryCode()));
            }
            
        }
        
        if (p.getAvatar() != null) {
            bs.setString(Column.AVATAR, p.getAvatar());
        }
        
        if (p.getBirthCountryCode() != null) {
            bs.setString(Column.BIRTH_COUNTRY_CODE, upperCase(p.getBirthCountryCode()));
        }
        
        if (p.getCitizenshipCountryCode() != null) {
            bs.setString(Column.CITIZENSHIP_COUNTRY_CODE, upperCase(p.getCitizenshipCountryCode()));
        }
        
        if (p.getCaptainsClubLoyaltyTier() != null) {
            bs.setString(Column.CAPTAINS_CLUB_LOYALTY_TIER, p.getCaptainsClubLoyaltyTier().getFullName());
        }
        
        if (p.getCaptainsClubLoyaltyIndividualPoints() != null) {
            bs.setInt(Column.CAPTAINS_CLUB_LOYALTY_INDIVIDUAL_POINTS, p.getCaptainsClubLoyaltyIndividualPoints());
        }
        
        if (p.getCaptainsClubLoyaltyRelationshipPoints() != null) {
            bs.setInt(Column.CAPTAINS_CLUB_LOYALTY_RELATIONSHIP_POINTS, p.getCaptainsClubLoyaltyRelationshipPoints());
        }
        
        if (p.getCelebrityBlueChipLoyaltyTier() != null) {
            bs.setString(Column.CELEBRITY_BLUE_CHIP_LOYALTY_TIER, p.getCelebrityBlueChipLoyaltyTier().getFullName());
        }
        
        if (p.getCelebrityBlueChipLoyaltyIndividualPoints() != null) {
            bs.setInt(Column.CELEBRITY_BLUE_CHIP_LOYALTY_INDIVIDUAL_POINTS,
                    p.getCelebrityBlueChipLoyaltyIndividualPoints());
        }
        
        if (p.getCelebrityBlueChipLoyaltyRelationshipPoints() != null) {
            bs.setInt(Column.CELEBRITY_BLUE_CHIP_LOYALTY_RELATIONSHIP_POINTS,
                    p.getCelebrityBlueChipLoyaltyRelationshipPoints());
        }
        
        if (p.getClubRoyaleLoyaltyTier() != null) {
            bs.setString(Column.CLUB_ROYALE_LOYALTY_TIER, p.getClubRoyaleLoyaltyTier().getFullName());
        }
        
        if (p.getClubRoyaleLoyaltyIndividualPoints() != null) {
            bs.setInt(Column.CLUB_ROYALE_LOYALTY_INDIVIDUAL_POINTS, p.getClubRoyaleLoyaltyIndividualPoints());
        }
        
        if (p.getClubRoyaleLoyaltyRelationshipPoints() != null) {
            bs.setInt(Column.CLUB_ROYALE_LOYALTY_RELATIONSHIP_POINTS, p.getClubRoyaleLoyaltyRelationshipPoints());
        }
        
        if (p.getCrownAndAnchorSocietyLoyaltyTier() != null) {
            bs.setString(Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_TIER,
                    p.getCrownAndAnchorSocietyLoyaltyTier().getFullName());
        }
        
        if (p.getCrownAndAnchorSocietyLoyaltyIndividualPoints() != null) {
            bs.setInt(Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_INDIVIDUAL_POINTS,
                    p.getCrownAndAnchorSocietyLoyaltyIndividualPoints());
        }
        
        if (p.getCrownAndAnchorSocietyLoyaltyRelationshipPoints() != null) {
            bs.setInt(Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_RELATIONSHIP_POINTS,
                    p.getCrownAndAnchorSocietyLoyaltyRelationshipPoints());
        }
        
        if (p.getEmergencyContact() != null) {
            EmergencyContact ec = p.getEmergencyContact();
            
            if (StringUtils.isNotBlank(ec.getFirstName())) {
                bs.setString(Column.EMERGENCY_CONTACT_FIRST_NAME, ec.getFirstName());
            }
            
            if (StringUtils.isNotBlank(ec.getLastName())) {
                bs.setString(Column.EMERGENCY_CONTACT_LAST_NAME, ec.getLastName());
            }
            
            if (StringUtils.isNotBlank(ec.getPhoneNumber())) {
                bs.setString(Column.EMERGENCY_CONTACT_PHONE, ec.getPhoneNumber());
            }
            
            if (StringUtils.isNotBlank(ec.getRelationship())) {
                bs.setString(Column.EMERGENCY_CONTACT_RELATIONSHIP, ec.getRelationship());
            }
        }
        
        if (p.getGender() != null) {
            bs.setString(Column.GENDER, p.getGender().getValue());
        }
        
        if (p.getNickname() != null) {
            bs.setString(Column.NICKNAME, p.getNickname());
        }
        
        bs.setTimestamp(Column.LAST_UPDATED, DateTime.now().toDate())
                .setString(Column.UPDATE_USERID, isInternal ? INTERNAL_USER : EXTERNAL_USER)
                .setString(Column.VDS_ID, p.getVdsId());
        
        return bs;
    }
}
