package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum CrownAndAnchorSocietyLoyaltyTier {
    
    PRE_GOLD("Pre-Gold", "R", 0, 0),
    GOLD("Gold", "G", 1, 29),
    PLATINUM("Platinum", "P", 30, 54),
    EMERALD("Emerald", "M", 55, 79),
    DIAMOND("Diamond", "D", 80, 174),
    DIAMOND_PLUS("Diamond Plus", "L", 175, 699),
    PINNACLE_CLUB("Pinnacle Club", "I", 700, 10000),
    INVALID("Invalid", "Invalid", -1, -1);
    
    @Getter
    private String code;
    
    @Getter
    private String fullName;
    
    @Getter
    private int maximumPointValue;
    
    @Getter
    private int minimumPointValue;
    
    CrownAndAnchorSocietyLoyaltyTier(String fullName, String code, int minimumPointValue, int maximumPointValue) {
        this.code = code;
        this.fullName = fullName;
        this.maximumPointValue = maximumPointValue;
        this.minimumPointValue = minimumPointValue;
    }
    
    @JsonCreator
    @JsonProperty("crownAndAnchorSocietyLoyaltyTier")
    public static CrownAndAnchorSocietyLoyaltyTier fromValue(String value) {
        if (value == null) {
            return null;
        }
        
        if (StringUtils.isBlank(value)) {
            return INVALID;
        }
        
        for (CrownAndAnchorSocietyLoyaltyTier current : CrownAndAnchorSocietyLoyaltyTier.values()) {
            if (current.fullName.equalsIgnoreCase(value)
                    || current.toString().equalsIgnoreCase(value)
                    || current.code.equalsIgnoreCase(value)) {
                return current;
            }
        }
        
        return INVALID;
    }
}
