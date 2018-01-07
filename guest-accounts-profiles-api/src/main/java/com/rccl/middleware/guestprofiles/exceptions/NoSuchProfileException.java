package com.rccl.middleware.guestprofiles.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.rccl.middleware.common.exceptions.MiddlewareTransportException;

/**
 * An exception thrown when attempting to modify a non-existing profile.
 */
public class NoSuchProfileException extends MiddlewareTransportException {
    
    public static final String DEFAULT_MESSAGE = "No such profile was found.";
    
    public NoSuchProfileException() {
        this(DEFAULT_MESSAGE);
    }
    
    public NoSuchProfileException(String errorMessage) {
        super(TransportErrorCode.NotFound, errorMessage);
    }
}
