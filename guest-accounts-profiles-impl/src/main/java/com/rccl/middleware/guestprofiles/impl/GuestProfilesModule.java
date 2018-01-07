package com.rccl.middleware.guestprofiles.impl;

import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import com.rccl.middleware.guest.optin.GuestProfileOptinService;
import com.rccl.middleware.guestprofiles.GuestProfilesService;

public class GuestProfilesModule extends AbstractModule implements ServiceGuiceSupport {
    
    @Override
    protected void configure() {
        bindService(GuestProfilesService.class, GuestProfilesServiceImpl.class);
        bindClient(GuestProfileOptinService.class);
    }
}
