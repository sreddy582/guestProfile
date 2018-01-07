package com.rccl.middleware.guestprofiles.impl;

import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import com.rccl.middleware.guestprofiles.GuestProfilesService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.startServer;
import static org.junit.Assert.assertNotNull;
import static play.inject.Bindings.bind;

public class GuestProfilesServiceImplTest {
    
    private static volatile ServiceTest.TestServer testServer;
    
    private static GuestProfilesService service;
    
    @BeforeClass
    public static void beforeClass() {
        final ServiceTest.Setup setup = defaultSetup()
                .withCassandra(true)
                .configureBuilder(builder -> builder.overrides(
                        bind(GuestProfilesService.class).to(GuestProfilesServiceImpl.class)
                ));
        
        testServer = startServer(setup);
        service = testServer.client(GuestProfilesService.class);
    }
    
    @AfterClass
    public static void afterClass() {
        if (testServer != null) {
            service = null;
            
            testServer.stop();
            testServer = null;
        }
    }
    
    @Test
    public void test() {
        assertNotNull(service);
    }
}
