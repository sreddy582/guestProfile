package com.rccl.middleware.guestprofiles.impl;

public enum GuestProfileEventStatus {

    CREATE("create"),
    UPDATE("update");

    String value;

    GuestProfileEventStatus(String value) {
        this.value = value;
    }
    
    public String value() {
        return this.value;
    }
}
