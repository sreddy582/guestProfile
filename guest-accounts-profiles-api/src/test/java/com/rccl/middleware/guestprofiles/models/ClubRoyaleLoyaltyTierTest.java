package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ClubRoyaleLoyaltyTierTest {
    
    @Test
    public void testPremier() {
        ClubRoyaleLoyaltyTier premier = ClubRoyaleLoyaltyTier.PREMIER;
        
        assertEquals("Premier", premier.getFullName());
        assertEquals(0, premier.getMinimumPointValue());
        assertEquals(3500, premier.getMaximumPointValue());
    }
    
    @Test
    public void testPrime() {
        ClubRoyaleLoyaltyTier prime = ClubRoyaleLoyaltyTier.PRIME;
        
        assertEquals("Prime", prime.getFullName());
        assertEquals(3501, prime.getMinimumPointValue());
        assertEquals(25000, prime.getMaximumPointValue());
    }
    
    @Test
    public void testSignature() {
        ClubRoyaleLoyaltyTier signature = ClubRoyaleLoyaltyTier.SIGNATURE;
        
        assertEquals("Signature", signature.getFullName());
        assertEquals(25001, signature.getMinimumPointValue());
        assertEquals(75000, signature.getMaximumPointValue());
    }
    
    @Test
    public void testMasters() {
        ClubRoyaleLoyaltyTier masters = ClubRoyaleLoyaltyTier.MASTERS;
        
        assertEquals("Masters", masters.getFullName());
        assertEquals(75001, masters.getMinimumPointValue());
        assertEquals(1000000000, masters.getMaximumPointValue());
    }
    
    @Test
    public void testInvalid() {
        ClubRoyaleLoyaltyTier invalid = ClubRoyaleLoyaltyTier.INVALID;
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
    }
    
    @Test
    public void testFromValue() {
        for (ClubRoyaleLoyaltyTier tier : ClubRoyaleLoyaltyTier.values()) {
            String lowercaseFullName = tier.getFullName().toLowerCase();
            assertEquals(tier, ClubRoyaleLoyaltyTier.fromValue(lowercaseFullName));
            
            String uppercaseFullName = tier.getFullName().toUpperCase();
            assertEquals(tier, ClubRoyaleLoyaltyTier.fromValue(uppercaseFullName));
        }
        
        assertNull(ClubRoyaleLoyaltyTier.fromValue(null));
        
        ClubRoyaleLoyaltyTier invalid = ClubRoyaleLoyaltyTier.fromValue("");
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
        
        invalid = ClubRoyaleLoyaltyTier.fromValue(" ");
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
    }
    
    @Test
    public void testSerialization() throws IOException {
        final ObjectMapper om = new ObjectMapper();
        
        for (ClubRoyaleLoyaltyTier tier : ClubRoyaleLoyaltyTier.values()) {
            JsonNode json = om.convertValue(tier.getFullName(), JsonNode.class);
            assertEquals(tier.getFullName(), json.textValue());
        }
    }
    
    @Test
    public void testDeserialization() throws IOException {
        final ObjectMapper om = new ObjectMapper();
        
        for (ClubRoyaleLoyaltyTier tier : ClubRoyaleLoyaltyTier.values()) {
            ObjectNode json = om.createObjectNode();
            json.set("clubRoyaleLoyaltyTier", om.convertValue(tier.getFullName(), JsonNode.class));
            
            String value = json.get("clubRoyaleLoyaltyTier").textValue();
            ClubRoyaleLoyaltyTier tier2 = om.convertValue(value, ClubRoyaleLoyaltyTier.class);
            assertEquals(tier, tier2);
        }
    }
}
