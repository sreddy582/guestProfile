package com.rccl.middleware.guestprofiles.exceptions;

import com.lightbend.lagom.javadsl.api.transport.TransportErrorCode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NoSuchProfileExceptionTest {
    
    @Test
    public void testNoArgConstructor() {
        NoSuchProfileException nspe = new NoSuchProfileException();
        
        assertEquals(NoSuchProfileException.DEFAULT_MESSAGE, nspe.getMessage());
        
        assertEquals(TransportErrorCode.NotFound, nspe.errorCode());
        assertEquals((Integer) TransportErrorCode.NotFound.http(), nspe.getStatus());
    }
    
    @Test
    public void AllArgsConstructor() {
        String expectedErrorMessage = "Some error message.";
        NoSuchProfileException nspe = new NoSuchProfileException(expectedErrorMessage);
        
        assertEquals(expectedErrorMessage, nspe.getMessage());
        
        assertEquals(TransportErrorCode.NotFound, nspe.errorCode());
        assertEquals((Integer) TransportErrorCode.NotFound.http(), nspe.getStatus());
    }
}
