package com.rccl.middleware.guestprofiles.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProfileAlreadyExistsExceptionTest {
    
    @Test
    public void testNoArgConstructor() {
        ProfileAlreadyExistsException paee = new ProfileAlreadyExistsException();
        
        assertEquals(ProfileAlreadyExistsException.DEFAULT_MESSAGE, paee.getMessage());
        
        assertEquals(TransportErrorCode.fromHttp(409), paee.errorCode());
        assertEquals((Integer) 409, paee.getStatus());
    }
    
    @Test
    public void AllArgsConstructor() {
        String expectedErrorMessage = "Some error message.";
        ProfileAlreadyExistsException paee = new ProfileAlreadyExistsException(expectedErrorMessage);
        
        assertEquals(expectedErrorMessage, paee.getMessage());
        
        assertEquals(TransportErrorCode.fromHttp(409), paee.errorCode());
        assertEquals((Integer) 409, paee.getStatus());
    }
}
