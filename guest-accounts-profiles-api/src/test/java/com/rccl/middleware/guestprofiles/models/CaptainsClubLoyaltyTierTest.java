package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CaptainsClubLoyaltyTierTest {
    
    @Test
    public void testPreview() {
        CaptainsClubLoyaltyTier preview = CaptainsClubLoyaltyTier.PREVIEW;
        
        assertEquals("Preview", preview.getFullName());
        assertEquals("X", preview.getCode());
        assertEquals(0, preview.getMinimumPointValue());
        assertEquals(0, preview.getMaximumPointValue());
    }
    
    @Test
    public void testClassic() {
        CaptainsClubLoyaltyTier classic = CaptainsClubLoyaltyTier.CLASSIC;
        
        assertEquals("Classic", classic.getFullName());
        assertEquals("C", classic.getCode());
        assertEquals(1, classic.getMinimumPointValue());
        assertEquals(149, classic.getMaximumPointValue());
    }
    
    @Test
    public void testSelect() {
        CaptainsClubLoyaltyTier select = CaptainsClubLoyaltyTier.SELECT;
        
        assertEquals("Select", select.getFullName());
        assertEquals("S", select.getCode());
        assertEquals(150, select.getMinimumPointValue());
        assertEquals(299, select.getMaximumPointValue());
    }
    
    @Test
    public void testElite() {
        CaptainsClubLoyaltyTier elite = CaptainsClubLoyaltyTier.ELITE;
        
        assertEquals("Elite", elite.getFullName());
        assertEquals("E", elite.getCode());
        assertEquals(300, elite.getMinimumPointValue());
        assertEquals(749, elite.getMaximumPointValue());
    }
    
    @Test
    public void testElitePlus() {
        CaptainsClubLoyaltyTier elitePlus = CaptainsClubLoyaltyTier.ELITE_PLUS;
        
        assertEquals("Elite Plus", elitePlus.getFullName());
        assertEquals("Y", elitePlus.getCode());
        assertEquals(750, elitePlus.getMinimumPointValue());
        assertEquals(2999, elitePlus.getMaximumPointValue());
    }
    
    @Test
    public void testZenith() {
        CaptainsClubLoyaltyTier zenith = CaptainsClubLoyaltyTier.ZENITH;
        
        assertEquals("Zenith", zenith.getFullName());
        assertEquals("Z", zenith.getCode());
        assertEquals(3000, zenith.getMinimumPointValue());
        assertEquals(100000, zenith.getMaximumPointValue());
    }
    
    @Test
    public void testInvalid() {
        CaptainsClubLoyaltyTier invalid = CaptainsClubLoyaltyTier.INVALID;
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals("Invalid", invalid.getCode());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
    }
    
    @Test
    public void testFromValue() {
        for (CaptainsClubLoyaltyTier tier : CaptainsClubLoyaltyTier.values()) {
            String lowercaseFullName = tier.getFullName().toLowerCase();
            assertEquals(tier, CaptainsClubLoyaltyTier.fromValue(lowercaseFullName));
            
            String uppercaseFullName = tier.getFullName().toUpperCase();
            assertEquals(tier, CaptainsClubLoyaltyTier.fromValue(uppercaseFullName));
        }
        
        assertNull(CaptainsClubLoyaltyTier.fromValue(null));
        
        CaptainsClubLoyaltyTier invalid = CaptainsClubLoyaltyTier.fromValue("");
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals("Invalid", invalid.getCode());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
        
        invalid = CaptainsClubLoyaltyTier.fromValue(" ");
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals("Invalid", invalid.getCode());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
    }
    
    @Test
    public void testSerialization() throws IOException {
        final ObjectMapper om = new ObjectMapper();
        
        for (CaptainsClubLoyaltyTier tier : CaptainsClubLoyaltyTier.values()) {
            JsonNode json = om.convertValue(tier.getFullName(), JsonNode.class);
            assertEquals(tier.getFullName(), json.textValue());
        }
    }
    
    @Test
    public void testDeserialization() throws IOException {
        final ObjectMapper om = new ObjectMapper();
        
        for (CaptainsClubLoyaltyTier tier : CaptainsClubLoyaltyTier.values()) {
            ObjectNode json = om.createObjectNode();
            json.set("captainsClubLoyaltyTier", om.convertValue(tier.getFullName(), JsonNode.class));
            
            String value = json.get("captainsClubLoyaltyTier").textValue();
            CaptainsClubLoyaltyTier tier2 = om.convertValue(value, CaptainsClubLoyaltyTier.class);
            assertEquals(tier, tier2);
        }
    }
}
