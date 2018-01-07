package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum CelebrityBlueChipLoyaltyTier {
    
    PEARL("Pearl", 0, 2499),
    ONYX("Onyx", 2500, 24999),
    AMETHYST("Amethyst", 25000, 99999),
    SAPPHIRE("Sapphire", 100000, 999999),
    RUBY("Ruby", 1000000, 100000000),
    INVALID("Invalid", -1, -1);
    
    @Getter
    private String fullName;
    
    @Getter
    private int maximumPointValue;
    
    @Getter
    private int minimumPointValue;
    
    CelebrityBlueChipLoyaltyTier(String fullName, int minimumPointValue, int maximumPointValue) {
        this.fullName = fullName;
        this.minimumPointValue = minimumPointValue;
        this.maximumPointValue = maximumPointValue;
    }
    
    @JsonCreator
    @JsonProperty("celebrityBlueChipTier")
    public static CelebrityBlueChipLoyaltyTier fromValue(String value) {
        if (value == null) {
            return null;
        }
    
        if (StringUtils.isBlank(value)) {
            return INVALID;
        }
        
        for (CelebrityBlueChipLoyaltyTier tier : CelebrityBlueChipLoyaltyTier.values()) {
            if (tier.fullName.equalsIgnoreCase(value) || tier.toString().equalsIgnoreCase(value)) {
                return tier;
            }
        }
        
        return INVALID;
    }
}
