package com.rccl.middleware.guestprofiles.impl.persistence;

public final class Constants {
    
    public static final String PROFILES_KEYSPACE = "guest_profiles";
    
    public static final String PROFILES_TABLE = "profiles";
    
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + PROFILES_TABLE + " ("
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
            + "PRIMARY KEY(" + Column.VDS_ID + "))";
    
    public static final String INSERT_QUERY = "INSERT INTO " + PROFILES_TABLE + " ("
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
            + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    public static final String UPDATE_QUERY = "UPDATE " + PROFILES_TABLE + " SET "
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
            + "WHERE " + Column.VDS_ID + "=?";
    
    public static final String SELECT_QUERY = "SELECT * FROM " + PROFILES_TABLE + " WHERE " + Column.VDS_ID + " = ?";
    
    private Constants() {
        // No-op.
    }
    
    public static final class Column {
        
        public static final String ADDRESS_ONE = "address_one";
        
        public static final String ADDRESS_TWO = "address_two";
        
        public static final String ADDRESS_CITY = "address_city";
        
        public static final String ADDRESS_STATE = "address_state";
        
        public static final String ADDRESS_ZIP = "address_zip";
        
        public static final String AVATAR = "avatar";
        
        public static final String BIRTH_COUNTRY_CODE = "birth_country_code";
        
        public static final String CITIZENSHIP_COUNTRY_CODE = "citizenship_country_code";
        
        public static final String CAPTAINS_CLUB_LOYALTY_TIER = "captains_club_loyalty_tier";
        
        public static final String CAPTAINS_CLUB_LOYALTY_INDIVIDUAL_POINTS = "captains_club_loyalty_individual_points";
        
        public static final String CAPTAINS_CLUB_LOYALTY_RELATIONSHIP_POINTS =
                "captains_club_loyalty_relationship_points";
        
        public static final String CELEBRITY_BLUE_CHIP_LOYALTY_TIER = "celebrity_blue_chip_loyalty_tier";
        
        public static final String CELEBRITY_BLUE_CHIP_LOYALTY_INDIVIDUAL_POINTS =
                "celebrity_blue_chip_loyalty_individual_points";
        
        public static final String CELEBRITY_BLUE_CHIP_LOYALTY_RELATIONSHIP_POINTS =
                "celebrity_blue_chip_loyalty_relationship_points";
        
        public static final String CLUB_ROYALE_LOYALTY_TIER = "club_royale_loyalty_tier";
        
        public static final String CLUB_ROYALE_LOYALTY_INDIVIDUAL_POINTS = "club_royale_loyalty_individual_points";
        
        public static final String CLUB_ROYALE_LOYALTY_RELATIONSHIP_POINTS = "club_royale_loyalty_relationship_points";
        
        public static final String CROWN_AND_ANCHOR_SOCIETY_LOYALTY_TIER = "crown_and_anchor_society_loyalty_tier";
        
        public static final String CROWN_AND_ANCHOR_SOCIETY_LOYALTY_INDIVIDUAL_POINTS =
                "crown_and_anchor_society_loyalty_individual_points";
        
        public static final String CROWN_AND_ANCHOR_SOCIETY_LOYALTY_RELATIONSHIP_POINTS =
                "crown_and_anchor_society_loyalty_relationship_points";
        
        public static final String CREATION_USERID = "creation_userid";
        
        public static final String CREATION_TIMESTAMP = "creation_timestamp";
        
        public static final String EMERGENCY_CONTACT_FIRST_NAME = "emergency_contact_first_name";
        
        public static final String EMERGENCY_CONTACT_LAST_NAME = "emergency_contact_last_name";
        
        public static final String EMERGENCY_CONTACT_PHONE = "emergency_contact_phone";
        
        public static final String EMERGENCY_CONTACT_RELATIONSHIP = "emergency_contact_relationship";
        
        public static final String GENDER = "gender";
        
        public static final String LAST_UPDATED = "last_updated";
        
        public static final String NICKNAME = "nickname";
        
        public static final String RESIDENCY_COUNTRY_CODE = "residency_country_code";
        
        public static final String UPDATE_USERID = "update_userid";
        
        public static final String VDS_ID = "vds_id";
        
        private Column() {
            // No-op.
        }
    }
}
