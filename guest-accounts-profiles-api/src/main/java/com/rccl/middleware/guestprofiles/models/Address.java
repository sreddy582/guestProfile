package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightbend.lagom.serialization.Jsonable;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address implements Jsonable {
    
    private static final long serialVersionUID = 1L;
    
    @NotNull(message = "The address one is required.")
    @Size(min = 5, max = 200, message = "The address one must be at least 5 characters and at most 200.")
    private String addressOne;
    
    @Size(max = 100, message = "The address two must be at most 100 characters.")
    private String addressTwo;
    
    @NotNull(message = "The address city is required.")
    @Size(min = 1, max = 50, message = "The address city must be least 1 character and at most 50.")
    private String city;
    
    @NotNull(message = "The address state is required.")
    @Pattern(regexp = "[A-Za-z]*", message = "The address state can only be alphabet characters")
    @Size(min = 2, max = 2, message = "The address state must be 2 characters.")
    private String state;
    
    @NotNull(message = "The address zip is required.")
    @Size(max = 10, message = "The zip code can only have a max of 10 characters.")
    @Pattern(regexp = "^[a-zA-Z0-9-\\s]+$", message = "The address zip code is in invalid format.")
    private String zipCode;
    
    @Pattern(regexp = "[A-Za-z]{3}", message = "The residency country code "
            + "is required to be the three-character country code.")
    private String residencyCountryCode;
}
