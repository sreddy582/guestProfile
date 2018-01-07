package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum ClubRoyaleLoyaltyTier {
    
    PREMIER("Premier", 0, 3500),
    PRIME("Prime", 3501, 25000),
    SIGNATURE("Signature", 25001, 75000),
    MASTERS("Masters", 75001, 1000000000),
    INVALID("Invalid", -1, -1);
    
    @Getter
    private String fullName;
    
    @Getter
    private int maximumPointValue;
    
    @Getter
    private int minimumPointValue;
    
    ClubRoyaleLoyaltyTier(String fullName, int minimumPointValue, int maximumPointValue) {
        this.fullName = fullName;
        this.minimumPointValue = minimumPointValue;
        this.maximumPointValue = maximumPointValue;
    }
    
    @JsonCreator
    @JsonProperty("clubRoyaleLoyaltyTier")
    public static ClubRoyaleLoyaltyTier fromValue(String value) {
        if (value == null) {
            return null;
        }
    
        if (StringUtils.isBlank(value)) {
            return INVALID;
        }
        
        for (ClubRoyaleLoyaltyTier tier : ClubRoyaleLoyaltyTier.values()) {
            if (tier.fullName.equalsIgnoreCase(value) || tier.toString().equalsIgnoreCase(value)) {
                return tier;
            }
        }
        
        return INVALID;
    }
}
