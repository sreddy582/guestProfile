package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CelebrityBlueChipLoyaltyTierTest {
    
    @Test
    public void testPearl() {
        CelebrityBlueChipLoyaltyTier pearl = CelebrityBlueChipLoyaltyTier.PEARL;
        
        assertEquals("Pearl", pearl.getFullName());
        assertEquals(0, pearl.getMinimumPointValue());
        assertEquals(2499, pearl.getMaximumPointValue());
    }
    
    @Test
    public void testOnyx() {
        CelebrityBlueChipLoyaltyTier onyx = CelebrityBlueChipLoyaltyTier.ONYX;
        
        assertEquals("Onyx", onyx.getFullName());
        assertEquals(2500, onyx.getMinimumPointValue());
        assertEquals(24999, onyx.getMaximumPointValue());
    }
    
    @Test
    public void testAmethyst() {
        CelebrityBlueChipLoyaltyTier amethyst = CelebrityBlueChipLoyaltyTier.AMETHYST;
        
        assertEquals("Amethyst", amethyst.getFullName());
        assertEquals(25000, amethyst.getMinimumPointValue());
        assertEquals(99999, amethyst.getMaximumPointValue());
    }
    
    @Test
    public void testSapphire() {
        CelebrityBlueChipLoyaltyTier sapphire = CelebrityBlueChipLoyaltyTier.SAPPHIRE;
        
        assertEquals("Sapphire", sapphire.getFullName());
        assertEquals(100000, sapphire.getMinimumPointValue());
        assertEquals(999999, sapphire.getMaximumPointValue());
    }
    
    @Test
    public void testRuby() {
        CelebrityBlueChipLoyaltyTier ruby = CelebrityBlueChipLoyaltyTier.RUBY;
        
        assertEquals("Ruby", ruby.getFullName());
        assertEquals(1000000, ruby.getMinimumPointValue());
        assertEquals(100000000, ruby.getMaximumPointValue());
    }
    
    @Test
    public void testInvalid() {
        CelebrityBlueChipLoyaltyTier invalid = CelebrityBlueChipLoyaltyTier.INVALID;
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
    }
    
    @Test
    public void testFromValue() {
        for (CelebrityBlueChipLoyaltyTier tier : CelebrityBlueChipLoyaltyTier.values()) {
            String lowercaseFullName = tier.getFullName().toLowerCase();
            assertEquals(tier, CelebrityBlueChipLoyaltyTier.fromValue(lowercaseFullName));
            
            String uppercaseFullName = tier.getFullName().toUpperCase();
            assertEquals(tier, CelebrityBlueChipLoyaltyTier.fromValue(uppercaseFullName));
        }
        
        assertNull(CelebrityBlueChipLoyaltyTier.fromValue(null));
        
        CelebrityBlueChipLoyaltyTier invalid = CelebrityBlueChipLoyaltyTier.fromValue("");
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
        
        invalid = CelebrityBlueChipLoyaltyTier.fromValue(" ");
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
    }
    
    @Test
    public void testSerialization() throws IOException {
        final ObjectMapper om = new ObjectMapper();
        
        for (CelebrityBlueChipLoyaltyTier tier : CelebrityBlueChipLoyaltyTier.values()) {
            JsonNode json = om.convertValue(tier.getFullName(), JsonNode.class);
            assertEquals(tier.getFullName(), json.textValue());
        }
    }
    
    @Test
    public void testDeserialization() throws IOException {
        final ObjectMapper om = new ObjectMapper();
        
        for (CelebrityBlueChipLoyaltyTier tier : CelebrityBlueChipLoyaltyTier.values()) {
            ObjectNode json = om.createObjectNode();
            json.set("celebrityBlueChipTier", om.convertValue(tier.getFullName(), JsonNode.class));
            
            String value = json.get("celebrityBlueChipTier").textValue();
            CelebrityBlueChipLoyaltyTier tier2 = om.convertValue(value, CelebrityBlueChipLoyaltyTier.class);
            assertEquals(tier, tier2);
        }
    }
}
