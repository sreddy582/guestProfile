package com.rccl.middleware.guestprofiles.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.rccl.middleware.common.exceptions.MiddlewareTransportException;

/**
 * An exception to be thrown when attempting to create a profile that already exists.
 */
public class ProfileAlreadyExistsException extends MiddlewareTransportException {
    
    public static final String DEFAULT_MESSAGE = "An existing profile was encountered.";
    
    public ProfileAlreadyExistsException() {
        this(DEFAULT_MESSAGE);
    }
    
    public ProfileAlreadyExistsException(String errorMessage) {
        super(TransportErrorCode.fromHttp(409), errorMessage);
    }
}
