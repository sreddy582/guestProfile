package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmergencyContact implements Jsonable {
    
    private static final long serialVersionUID = 1L;
    
    @Size(min = 1, max = 50, message = "The emergency contact first name should be between 1 and 50 characters.")
    private String firstName;
    
    @Size(min = 1, max = 50, message = "The emergency contact last name should be between 1 and 50 characters.")
    private String lastName;
    
    @Size(min = 7, max = 30, message = "The phone number must be at least seven (7) characters"
            + " and maximum of thirty (30) characters.")
    @Pattern(regexp = "[\\s0-9+()-]*", message = "The emergency phone number is invalidly formatted.")
    private String phoneNumber;
    
    private String relationship;
}
