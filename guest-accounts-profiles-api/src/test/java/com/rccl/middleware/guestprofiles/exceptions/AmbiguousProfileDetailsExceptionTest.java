package com.rccl.middleware.guestprofiles.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AmbiguousProfileDetailsExceptionTest {
    
    @Test
    public void testNoArgConstructor() {
        AmbiguousProfileDetailsException apde = new AmbiguousProfileDetailsException();
        
        assertEquals(AmbiguousProfileDetailsException.DEFAULT_MESSAGE, apde.getMessage());
        
        assertEquals(TransportErrorCode.fromHttp(422), apde.errorCode());
        assertEquals((Integer) 422, apde.getStatus());
    }
    
    @Test
    public void AllArgsConstructor() {
        String expectedErrorMessage = "Some error message.";
        AmbiguousProfileDetailsException apde = new AmbiguousProfileDetailsException(expectedErrorMessage);
        
        assertEquals(expectedErrorMessage, apde.getMessage());
        
        assertEquals(TransportErrorCode.fromHttp(422), apde.errorCode());
        assertEquals((Integer) 422, apde.getStatus());
    }
}
