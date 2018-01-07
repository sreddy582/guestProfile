package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CrownAndAnchorSocietyLoyaltyTierTest {
    
    @Test
    public void testPreGold() {
        CrownAndAnchorSocietyLoyaltyTier preGold = CrownAndAnchorSocietyLoyaltyTier.PRE_GOLD;
        
        assertEquals("Pre-Gold", preGold.getFullName());
        assertEquals("R", preGold.getCode());
        assertEquals(0, preGold.getMinimumPointValue());
        assertEquals(0, preGold.getMaximumPointValue());
    }
    
    @Test
    public void testGold() {
        CrownAndAnchorSocietyLoyaltyTier gold = CrownAndAnchorSocietyLoyaltyTier.GOLD;
        
        assertEquals("Gold", gold.getFullName());
        assertEquals("G", gold.getCode());
        assertEquals(1, gold.getMinimumPointValue());
        assertEquals(29, gold.getMaximumPointValue());
    }
    
    @Test
    public void testPlatinum() {
        CrownAndAnchorSocietyLoyaltyTier platinum = CrownAndAnchorSocietyLoyaltyTier.PLATINUM;
        
        assertEquals("Platinum", platinum.getFullName());
        assertEquals("P", platinum.getCode());
        assertEquals(30, platinum.getMinimumPointValue());
        assertEquals(54, platinum.getMaximumPointValue());
    }
    
    @Test
    public void testEmerald() {
        CrownAndAnchorSocietyLoyaltyTier emerald = CrownAndAnchorSocietyLoyaltyTier.EMERALD;
        
        assertEquals("Emerald", emerald.getFullName());
        assertEquals("M", emerald.getCode());
        assertEquals(55, emerald.getMinimumPointValue());
        assertEquals(79, emerald.getMaximumPointValue());
    }
    
    @Test
    public void testDiamond() {
        CrownAndAnchorSocietyLoyaltyTier diamond = CrownAndAnchorSocietyLoyaltyTier.DIAMOND;
        
        assertEquals("Diamond", diamond.getFullName());
        assertEquals("D", diamond.getCode());
        assertEquals(80, diamond.getMinimumPointValue());
        assertEquals(174, diamond.getMaximumPointValue());
    }
    
    @Test
    public void testDiamondPlus() {
        CrownAndAnchorSocietyLoyaltyTier diamondPlus = CrownAndAnchorSocietyLoyaltyTier.DIAMOND_PLUS;
        
        assertEquals("Diamond Plus", diamondPlus.getFullName());
        assertEquals("L", diamondPlus.getCode());
        assertEquals(175, diamondPlus.getMinimumPointValue());
        assertEquals(699, diamondPlus.getMaximumPointValue());
    }
    
    @Test
    public void testPinnacleClub() {
        CrownAndAnchorSocietyLoyaltyTier pinnacleClub = CrownAndAnchorSocietyLoyaltyTier.PINNACLE_CLUB;
        
        assertEquals("Pinnacle Club", pinnacleClub.getFullName());
        assertEquals("I", pinnacleClub.getCode());
        assertEquals(700, pinnacleClub.getMinimumPointValue());
        assertEquals(10000, pinnacleClub.getMaximumPointValue());
    }
    
    @Test
    public void testInvalid() {
        CrownAndAnchorSocietyLoyaltyTier invalid = CrownAndAnchorSocietyLoyaltyTier.INVALID;
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals("Invalid", invalid.getCode());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
    }
    
    @Test
    public void testFromValue() {
        for (CrownAndAnchorSocietyLoyaltyTier tier : CrownAndAnchorSocietyLoyaltyTier.values()) {
            String lowercaseFullName = tier.getFullName().toLowerCase();
            assertEquals(tier, CrownAndAnchorSocietyLoyaltyTier.fromValue(lowercaseFullName));
            
            String uppercaseFullName = tier.getFullName().toUpperCase();
            assertEquals(tier, CrownAndAnchorSocietyLoyaltyTier.fromValue(uppercaseFullName));
        }
        
        assertNull(CrownAndAnchorSocietyLoyaltyTier.fromValue(null));
        
        CrownAndAnchorSocietyLoyaltyTier invalid = CrownAndAnchorSocietyLoyaltyTier.fromValue("");
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals("Invalid", invalid.getCode());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
        
        invalid = CrownAndAnchorSocietyLoyaltyTier.fromValue(" ");
        
        assertEquals("Invalid", invalid.getFullName());
        assertEquals("Invalid", invalid.getCode());
        assertEquals(-1, invalid.getMinimumPointValue());
        assertEquals(-1, invalid.getMaximumPointValue());
    }
    
    @Test
    public void testSerialization() {
        final ObjectMapper om = new ObjectMapper();
        
        for (CrownAndAnchorSocietyLoyaltyTier tier : CrownAndAnchorSocietyLoyaltyTier.values()) {
            JsonNode json = om.convertValue(tier.getFullName(), JsonNode.class);
            assertEquals(tier.getFullName(), json.textValue());
        }
    }
    
    @Test
    public void testDeserialization() {
        final ObjectMapper om = new ObjectMapper();
        
        for (CrownAndAnchorSocietyLoyaltyTier tier : CrownAndAnchorSocietyLoyaltyTier.values()) {
            ObjectNode json = om.createObjectNode();
            json.set("crownAndAnchorSocietyLoyaltyTier", om.convertValue(tier.getFullName(), JsonNode.class));
            
            String value = json.get("crownAndAnchorSocietyLoyaltyTier").textValue();
            CrownAndAnchorSocietyLoyaltyTier tier2 = om.convertValue(value, CrownAndAnchorSocietyLoyaltyTier.class);
            assertEquals(tier, tier2);
        }
    }
}
