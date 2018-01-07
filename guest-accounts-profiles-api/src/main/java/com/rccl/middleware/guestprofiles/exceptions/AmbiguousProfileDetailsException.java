package com.rccl.middleware.guestprofiles.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import com.rccl.middleware.common.exceptions.MiddlewareTransportException;

/**
 * An exception to be thrown when too many profiles match some selection request.
 */
public class AmbiguousProfileDetailsException extends MiddlewareTransportException {
    
    public static final String DEFAULT_MESSAGE = "More than one profile was identified with these details.";
    
    public AmbiguousProfileDetailsException() {
        this(DEFAULT_MESSAGE);
    }
    
    public AmbiguousProfileDetailsException(String errorMessage) {
        super(TransportErrorCode.fromHttp(422), errorMessage);
    }
}
