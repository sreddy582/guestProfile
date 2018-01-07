package com.rccl.middleware.guestprofiles.impl.persistence;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static com.rccl.middleware.guestprofiles.impl.persistence.Constants.Column;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConstantsTest {
    
    @Test
    public void testConstantsClass() {
        assertEquals(1, Constants.class.getDeclaredConstructors().length);
        assertFalse(Constants.class.getDeclaredConstructors()[0].isAccessible());
        assertTrue(Modifier.isFinal(Constants.class.getModifiers()));
    }
    
    @Test
    public void testConstantsValues() {
        assertEquals("All Constant constants should be tested.", 6, Constants.class.getDeclaredFields().length);
        
        assertEquals("guest_profiles", Constants.PROFILES_KEYSPACE);
        
        assertEquals("profiles", Constants.PROFILES_TABLE);
        
        assertEquals("CREATE TABLE IF NOT EXISTS " + Constants.PROFILES_TABLE + " ("
                + Column.VDS_ID + " text, "
                + Column.ADDRESS_ONE + " text, "
                + Column.ADDRESS_TWO + " text, "
                + Column.ADDRESS_CITY + " text, "
                + Column.ADDRESS_STATE + " text, "
                + Column.ADDRESS_ZIP + " text, "
                + Column.AVATAR + " text, "
                + Column.BIRTH_COUNTRY_CODE + " text, "
                + Column.CITIZENSHIP_COUNTRY_CODE + " text, "
                + Column.RESIDENCY_COUNTRY_CODE + " text, "
                + Column.CAPTAINS_CLUB_LOYALTY_TIER + " text, "
                + Column.CAPTAINS_CLUB_LOYALTY_INDIVIDUAL_POINTS + " int, "
                + Column.CAPTAINS_CLUB_LOYALTY_RELATIONSHIP_POINTS + " int, "
                + Column.CELEBRITY_BLUE_CHIP_LOYALTY_TIER + " text, "
                + Column.CELEBRITY_BLUE_CHIP_LOYALTY_INDIVIDUAL_POINTS + " int, "
                + Column.CELEBRITY_BLUE_CHIP_LOYALTY_RELATIONSHIP_POINTS + " int, "
                + Column.CLUB_ROYALE_LOYALTY_TIER + " text, "
                + Column.CLUB_ROYALE_LOYALTY_INDIVIDUAL_POINTS + " int, "
                + Column.CLUB_ROYALE_LOYALTY_RELATIONSHIP_POINTS + " int, "
                + Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_TIER + " text, "
                + Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_INDIVIDUAL_POINTS + " int, "
                + Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_RELATIONSHIP_POINTS + " int, "
                + Column.EMERGENCY_CONTACT_FIRST_NAME + " text, "
                + Column.EMERGENCY_CONTACT_LAST_NAME + " text, "
                + Column.EMERGENCY_CONTACT_PHONE + " text, "
                + Column.EMERGENCY_CONTACT_RELATIONSHIP + " text, "
                + Column.GENDER + " text, "
                + Column.NICKNAME + " text, "
                + Column.CREATION_USERID + " text, "
                + Column.UPDATE_USERID + " text, "
                + Column.CREATION_TIMESTAMP + " timestamp, "
                + Column.LAST_UPDATED + " timestamp, "
                + "PRIMARY KEY(" + Column.VDS_ID + "))", Constants.CREATE_TABLE_QUERY);
        
        assertEquals("INSERT INTO " + Constants.PROFILES_TABLE + " ("
                + Column.VDS_ID + ", "
                + Column.ADDRESS_ONE + ", "
                + Column.ADDRESS_TWO + ", "
                + Column.ADDRESS_CITY + ", "
                + Column.ADDRESS_STATE + ", "
                + Column.ADDRESS_ZIP + ", "
                + Column.AVATAR + ", "
                + Column.BIRTH_COUNTRY_CODE + ", "
                + Column.CITIZENSHIP_COUNTRY_CODE + ", "
                + Column.RESIDENCY_COUNTRY_CODE + ", "
                + Column.CAPTAINS_CLUB_LOYALTY_TIER + ", "
                + Column.CAPTAINS_CLUB_LOYALTY_INDIVIDUAL_POINTS + ", "
                + Column.CAPTAINS_CLUB_LOYALTY_RELATIONSHIP_POINTS + ", "
                + Column.CELEBRITY_BLUE_CHIP_LOYALTY_TIER + ", "
                + Column.CELEBRITY_BLUE_CHIP_LOYALTY_INDIVIDUAL_POINTS + ", "
                + Column.CELEBRITY_BLUE_CHIP_LOYALTY_RELATIONSHIP_POINTS + ", "
                + Column.CLUB_ROYALE_LOYALTY_TIER + ", "
                + Column.CLUB_ROYALE_LOYALTY_INDIVIDUAL_POINTS + ", "
                + Column.CLUB_ROYALE_LOYALTY_RELATIONSHIP_POINTS + ", "
                + Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_TIER + ", "
                + Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_INDIVIDUAL_POINTS + ", "
                + Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_RELATIONSHIP_POINTS + ", "
                + Column.EMERGENCY_CONTACT_FIRST_NAME + ", "
                + Column.EMERGENCY_CONTACT_LAST_NAME + ", "
                + Column.EMERGENCY_CONTACT_PHONE + ", "
                + Column.EMERGENCY_CONTACT_RELATIONSHIP + ", "
                + Column.GENDER + ", "
                + Column.NICKNAME + ", "
                + Column.CREATION_USERID + ", "
                + Column.UPDATE_USERID + ", "
                + Column.CREATION_TIMESTAMP + ", "
                + Column.LAST_UPDATED + ")"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Constants.INSERT_QUERY);
        
        assertEquals("UPDATE " + Constants.PROFILES_TABLE + " SET "
                + Column.ADDRESS_ONE + "=?, "
                + Column.ADDRESS_TWO + "=?, "
                + Column.ADDRESS_CITY + "=?, "
                + Column.ADDRESS_STATE + "=?, "
                + Column.ADDRESS_ZIP + "=?, "
                + Column.AVATAR + "=?, "
                + Column.BIRTH_COUNTRY_CODE + "=?, "
                + Column.CITIZENSHIP_COUNTRY_CODE + "=?, "
                + Column.RESIDENCY_COUNTRY_CODE + "=?, "
                + Column.CAPTAINS_CLUB_LOYALTY_TIER + "=?, "
                + Column.CAPTAINS_CLUB_LOYALTY_INDIVIDUAL_POINTS + "=?, "
                + Column.CAPTAINS_CLUB_LOYALTY_RELATIONSHIP_POINTS + "=?, "
                + Column.CELEBRITY_BLUE_CHIP_LOYALTY_TIER + "=?, "
                + Column.CELEBRITY_BLUE_CHIP_LOYALTY_INDIVIDUAL_POINTS + "=?, "
                + Column.CELEBRITY_BLUE_CHIP_LOYALTY_RELATIONSHIP_POINTS + "=?, "
                + Column.CLUB_ROYALE_LOYALTY_TIER + "=?, "
                + Column.CLUB_ROYALE_LOYALTY_INDIVIDUAL_POINTS + "=?, "
                + Column.CLUB_ROYALE_LOYALTY_RELATIONSHIP_POINTS + "=?, "
                + Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_TIER + "=?, "
                + Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_INDIVIDUAL_POINTS + "=?, "
                + Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_RELATIONSHIP_POINTS + "=?, "
                + Column.EMERGENCY_CONTACT_FIRST_NAME + "=?, "
                + Column.EMERGENCY_CONTACT_LAST_NAME + "=?, "
                + Column.EMERGENCY_CONTACT_PHONE + "=?, "
                + Column.EMERGENCY_CONTACT_RELATIONSHIP + "=?, "
                + Column.GENDER + "=?, "
                + Column.NICKNAME + "=?, "
                + Column.UPDATE_USERID + "=?, "
                + Column.LAST_UPDATED + "=? "
                + "WHERE " + Column.VDS_ID + "=?", Constants.UPDATE_QUERY);
        
        assertEquals("SELECT * FROM " + Constants.PROFILES_TABLE
                + " WHERE vds_id = ?", Constants.SELECT_QUERY);
    }
    
    @Test
    public void testConstantsFieldsArePublicStaticFinal() {
        for (Field field : Constants.class.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            
            assertTrue(Modifier.isPublic(modifiers));
            assertTrue(Modifier.isStatic(modifiers));
            assertTrue(Modifier.isFinal(modifiers));
        }
    }
    
    @Test
    public void testColumnClass() {
        Class clazz = Constants.Column.class;
        
        assertEquals(1, clazz.getDeclaredConstructors().length);
        assertFalse(clazz.getDeclaredConstructors()[0].isAccessible());
        
        int modifiers = clazz.getModifiers();
        
        assertTrue(Modifier.isPublic(modifiers));
        assertTrue(Modifier.isStatic(modifiers));
        assertTrue(Modifier.isFinal(modifiers));
    }
    
    @Test
    public void testColumnValues() {
        final int EXPECTED_NUMBER_OF_COLUMNS = 32;
        
        assertEquals("All Constants.Column constants should be tested.",
                EXPECTED_NUMBER_OF_COLUMNS, Constants.Column.class.getDeclaredFields().length);
        
        assertEquals("address_one", Column.ADDRESS_ONE);
        
        assertEquals("address_two", Column.ADDRESS_TWO);
        
        assertEquals("address_city", Column.ADDRESS_CITY);
        
        assertEquals("address_state", Column.ADDRESS_STATE);
        
        assertEquals("address_zip", Column.ADDRESS_ZIP);
        
        assertEquals("avatar", Column.AVATAR);
        
        assertEquals("birth_country_code", Column.BIRTH_COUNTRY_CODE);
        
        assertEquals("citizenship_country_code", Column.CITIZENSHIP_COUNTRY_CODE);
        
        assertEquals("captains_club_loyalty_tier", Column.CAPTAINS_CLUB_LOYALTY_TIER);
        
        assertEquals("captains_club_loyalty_individual_points",
                Column.CAPTAINS_CLUB_LOYALTY_INDIVIDUAL_POINTS);
        
        assertEquals("captains_club_loyalty_relationship_points",
                Column.CAPTAINS_CLUB_LOYALTY_RELATIONSHIP_POINTS);
        
        assertEquals("celebrity_blue_chip_loyalty_tier", Column.CELEBRITY_BLUE_CHIP_LOYALTY_TIER);
        
        assertEquals("celebrity_blue_chip_loyalty_individual_points",
                Column.CELEBRITY_BLUE_CHIP_LOYALTY_INDIVIDUAL_POINTS);
        
        assertEquals("celebrity_blue_chip_loyalty_relationship_points",
                Column.CELEBRITY_BLUE_CHIP_LOYALTY_RELATIONSHIP_POINTS);
        
        assertEquals("club_royale_loyalty_tier", Column.CLUB_ROYALE_LOYALTY_TIER);
        
        assertEquals("club_royale_loyalty_individual_points", Column.CLUB_ROYALE_LOYALTY_INDIVIDUAL_POINTS);
        
        assertEquals("club_royale_loyalty_relationship_points",
                Column.CLUB_ROYALE_LOYALTY_RELATIONSHIP_POINTS);
        
        assertEquals("crown_and_anchor_society_loyalty_tier", Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_TIER);
        
        assertEquals("crown_and_anchor_society_loyalty_individual_points",
                Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_INDIVIDUAL_POINTS);
        
        assertEquals("crown_and_anchor_society_loyalty_relationship_points",
                Column.CROWN_AND_ANCHOR_SOCIETY_LOYALTY_RELATIONSHIP_POINTS);
        
        assertEquals("gender", Column.GENDER);
        
        assertEquals("nickname", Column.NICKNAME);
        
        assertEquals("creation_userid", Column.CREATION_USERID);
        
        assertEquals("creation_timestamp", Column.CREATION_TIMESTAMP);
        
        assertEquals("update_userid", Column.UPDATE_USERID);
        
        assertEquals("last_updated", Column.LAST_UPDATED);
        
        assertEquals("residency_country_code", Column.RESIDENCY_COUNTRY_CODE);
        
        assertEquals("vds_id", Column.VDS_ID);
    }
    
    @Test
    public void testAllColumnFieldsArePublicStaticFinal() {
        for (Field field : Constants.Column.class.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            
            assertTrue(Modifier.isPublic(modifiers));
            assertTrue(Modifier.isStatic(modifiers));
            assertTrue(Modifier.isFinal(modifiers));
        }
    }
}
