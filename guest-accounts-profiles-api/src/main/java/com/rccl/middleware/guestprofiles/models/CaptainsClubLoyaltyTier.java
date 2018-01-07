package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum CaptainsClubLoyaltyTier {
    
    PREVIEW("Preview", "X", 0, 0),
    CLASSIC("Classic", "C", 1, 149),
    SELECT("Select", "S", 150, 299),
    ELITE("Elite", "E", 300, 749),
    ELITE_PLUS("Elite Plus", "Y", 750, 2999),
    ZENITH("Zenith", "Z", 3000, 100000),
    INVALID("Invalid", "Invalid", -1, -1);
    
    @Getter
    private String code;
    
    @Getter
    private String fullName;
    
    @Getter
    private int maximumPointValue;
    
    @Getter
    private int minimumPointValue;
    
    CaptainsClubLoyaltyTier(String fullName, String code, int minimumPointValue, int maximumPointValue) {
        this.fullName = fullName;
        this.code = code;
        this.maximumPointValue = maximumPointValue;
        this.minimumPointValue = minimumPointValue;
    }
    
    @JsonCreator
    @JsonProperty("captainsClubLoyaltyTier")
    public static CaptainsClubLoyaltyTier fromValue(String value) {
        if (value == null) {
            return null;
        }
    
        if (StringUtils.isBlank(value)) {
            return INVALID;
        }
        
        for (CaptainsClubLoyaltyTier current : CaptainsClubLoyaltyTier.values()) {
            if (current.fullName.equalsIgnoreCase(value)
                    || current.toString().equalsIgnoreCase(value)
                    || current.code.equalsIgnoreCase(value)) {
                return current;
            }
        }
        
        return INVALID;
    }
}
