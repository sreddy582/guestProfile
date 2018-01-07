package com.rccl.middleware.guestprofiles.impl.persistence.events;

import akka.Done;
import com.lightbend.lagom.javadsl.persistence.AggregateEventTag;
import com.lightbend.lagom.javadsl.persistence.ReadSideProcessor;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraReadSide;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import com.rccl.middleware.guestprofiles.impl.persistence.Constants;
import org.pcollections.PSequence;
import org.pcollections.TreePVector;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class ProfileEventProcessor extends ReadSideProcessor<ProfileEvent> {
    
    private final CassandraReadSide readSide;
    private final CassandraSession session;
    
    @Inject
    public ProfileEventProcessor(final CassandraReadSide readSide, final CassandraSession session) {
        this.session = session;
        this.readSide = readSide;
    }
    
    @Override
    public PSequence<AggregateEventTag<ProfileEvent>> aggregateTags() {
        return TreePVector.singleton(ProfileEventTag.INSTANCE);
    }
    
    @Override
    public ReadSideHandler<ProfileEvent> buildHandler() {
        return readSide.<ProfileEvent>builder(Constants.PROFILES_TABLE + "_offset")
                .setGlobalPrepare(this::createTableOnStartup)
                .build();
    }
    
    private CompletionStage<Done> createTableOnStartup() {
        return session.executeCreateTable(Constants.CREATE_TABLE_QUERY);
    }
}
