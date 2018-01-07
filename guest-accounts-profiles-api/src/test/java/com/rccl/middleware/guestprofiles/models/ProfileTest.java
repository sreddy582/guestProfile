package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.rccl.middleware.common.beans.Gender;
import com.rccl.middleware.common.validation.validator.ValidGender;
import com.rccl.middleware.guestprofiles.validation.LoyaltyTier;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ProfileTest {
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    
    private static <T> void assertAnnotationValidationFailure(Set<ConstraintViolation<T>> violations, Class expectedClass) {
        assertEquals(1, violations.size());
        
        Class actualClass = violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType();
        assertEquals(expectedClass, actualClass);
    }
    
    @Test
    public void testConstructor() {
        Profile p = Profile.builder().build();
        
        assertNull(p.getAddress());
        assertNull(p.getAvatar());
        assertNull(p.getBirthCountryCode());
        assertNull(p.getCitizenshipCountryCode());
        assertNull(p.getCaptainsClubLoyaltyTier());
        assertNull(p.getCaptainsClubLoyaltyIndividualPoints());
        assertNull(p.getCaptainsClubLoyaltyRelationshipPoints());
        assertNull(p.getCelebrityBlueChipLoyaltyTier());
        assertNull(p.getCelebrityBlueChipLoyaltyIndividualPoints());
        assertNull(p.getCelebrityBlueChipLoyaltyRelationshipPoints());
        assertNull(p.getClubRoyaleLoyaltyTier());
        assertNull(p.getClubRoyaleLoyaltyIndividualPoints());
        assertNull(p.getClubRoyaleLoyaltyRelationshipPoints());
        assertNull(p.getCrownAndAnchorSocietyLoyaltyTier());
        assertNull(p.getCrownAndAnchorSocietyLoyaltyIndividualPoints());
        assertNull(p.getCrownAndAnchorSocietyLoyaltyRelationshipPoints());
        assertNull(p.getEmergencyContact());
        assertNull(p.getGender());
        assertNull(p.getNickname());
        assertNull(p.getVdsId());
    }
    
    @Test
    public void testDeserialization() {
        ObjectNode sampleJson = getSampleJson();
        
        Profile profile = OBJECT_MAPPER.convertValue(sampleJson, Profile.class);
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(profile);
        
        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void testSerialization() {
        getSampleJson();
    }
    
    @Test
    public void testValidationForAvatar() {
        Profile validAvatar = getInstance()
                .avatar("http://avatars.rccl.com/some/image.jpg")
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validAvatar);
        assertTrue(violations.isEmpty());
        
        Profile nullAvatar = getInstance()
                .avatar(null)
                .build();
        
        violations = VALIDATOR.validate(nullAvatar);
        assertTrue(violations.isEmpty());
        
        Profile blankAvatar = getInstance()
                .avatar("")
                .build();
        
        violations = VALIDATOR.validate(blankAvatar);
        assertAnnotationValidationFailure(violations, Pattern.class);
        
        Profile invalidAvatar = getInstance()
                .avatar("com/some/image.jpg")
                .build();
        
        violations = VALIDATOR.validate(invalidAvatar);
        assertAnnotationValidationFailure(violations, Pattern.class);
    }
    
    @Test
    public void testValidationForBirthCountryCode() {
        Profile validBirthCountryCode = getInstance()
                .birthCountryCode("USA")
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validBirthCountryCode);
        assertTrue(violations.isEmpty());
        
        Profile nullBirthCountryCode = getInstance()
                .birthCountryCode(null)
                .build();
        
        violations = VALIDATOR.validate(nullBirthCountryCode);
        assertTrue(violations.isEmpty());
        
        Profile blankBirthCountryCode = getInstance()
                .birthCountryCode("")
                .build();
        
        violations = VALIDATOR.validate(blankBirthCountryCode);
        assertAnnotationValidationFailure(violations, Pattern.class);
        
        Profile shortBirthCountryCode = getInstance()
                .birthCountryCode("U")
                .build();
        
        violations = VALIDATOR.validate(shortBirthCountryCode);
        assertAnnotationValidationFailure(violations, Pattern.class);
        
        Profile longBirthCountryCode = getInstance()
                .birthCountryCode("USAA")
                .build();
        
        violations = VALIDATOR.validate(longBirthCountryCode);
        assertAnnotationValidationFailure(violations, Pattern.class);
        
        Profile invalidCharactersBirthCountryCode = getInstance()
                .birthCountryCode("US@")
                .build();
        
        violations = VALIDATOR.validate(invalidCharactersBirthCountryCode);
        assertAnnotationValidationFailure(violations, Pattern.class);
    }
    
    @Test
    public void testValidationForCitizenshipCountryCode() {
        Profile validCitizenshipCountryCode = getInstance()
                .citizenshipCountryCode("USA")
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validCitizenshipCountryCode);
        assertTrue(violations.isEmpty());
        
        Profile nullCitizenshipCountryCode = getInstance()
                .citizenshipCountryCode(null)
                .build();
        
        violations = VALIDATOR.validate(nullCitizenshipCountryCode);
        assertTrue(violations.isEmpty());
        
        Profile blankCitizenshipCountryCode = getInstance()
                .citizenshipCountryCode("")
                .build();
        
        violations = VALIDATOR.validate(blankCitizenshipCountryCode);
        assertAnnotationValidationFailure(violations, Pattern.class);
        
        Profile shortCitizenshipCountryCode = getInstance()
                .citizenshipCountryCode("U")
                .build();
        
        violations = VALIDATOR.validate(shortCitizenshipCountryCode);
        assertAnnotationValidationFailure(violations, Pattern.class);
        
        Profile longCitizenshipCountryCode = getInstance()
                .citizenshipCountryCode("USAA")
                .build();
        
        violations = VALIDATOR.validate(longCitizenshipCountryCode);
        assertAnnotationValidationFailure(violations, Pattern.class);
        
        Profile invalidCharactersCitizenshipCountryCode = getInstance()
                .citizenshipCountryCode("US@")
                .build();
        
        violations = VALIDATOR.validate(invalidCharactersCitizenshipCountryCode);
        assertAnnotationValidationFailure(violations, Pattern.class);
    }
    
    @Test
    public void testValidationForCaptainsClubLoyaltyTier() {
        Profile validCaptainsClubLoyaltyTier = getInstance()
                .captainsClubLoyaltyTier(CaptainsClubLoyaltyTier.CLASSIC)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validCaptainsClubLoyaltyTier);
        assertTrue(violations.isEmpty());
        
        Profile nullCaptainsClubLoyaltyTier = getInstance()
                .captainsClubLoyaltyTier(null)
                .build();
        
        violations = VALIDATOR.validate(nullCaptainsClubLoyaltyTier);
        assertTrue(violations.isEmpty());
        
        Profile invalidCaptainsClubLoyaltyTier = getInstance()
                .captainsClubLoyaltyTier(CaptainsClubLoyaltyTier.INVALID)
                .build();
        
        violations = VALIDATOR.validate(invalidCaptainsClubLoyaltyTier);
        assertAnnotationValidationFailure(violations, LoyaltyTier.class);
    }
    
    @Test
    public void testValidationForCaptainsClubLoyaltyIndividualPoints() {
        Profile validCaptainsClubLoyaltyIndividualPoints = getInstance()
                .captainsClubLoyaltyIndividualPoints(69)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validCaptainsClubLoyaltyIndividualPoints);
        assertTrue(violations.isEmpty());
        
        Profile nullCaptainsClubLoyaltyIndividualPoints = getInstance()
                .captainsClubLoyaltyIndividualPoints(null)
                .build();
        
        violations = VALIDATOR.validate(nullCaptainsClubLoyaltyIndividualPoints);
        assertTrue(violations.isEmpty());
        
        Profile invalidCaptainsClubLoyaltyIndividualPoints = getInstance()
                .captainsClubLoyaltyIndividualPoints(-1)
                .build();
        
        violations = VALIDATOR.validate(invalidCaptainsClubLoyaltyIndividualPoints);
        assertAnnotationValidationFailure(violations, Min.class);
    }
    
    @Test
    public void testValidationForCaptainsClubLoyaltyRelationshipPoints() {
        Profile validCaptainsClubLoyaltyRelationshipPoints = getInstance()
                .captainsClubLoyaltyRelationshipPoints(69)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validCaptainsClubLoyaltyRelationshipPoints);
        assertTrue(violations.isEmpty());
        
        Profile nullCaptainsClubLoyaltyRelationshipPoints = getInstance()
                .captainsClubLoyaltyRelationshipPoints(null)
                .build();
        
        violations = VALIDATOR.validate(nullCaptainsClubLoyaltyRelationshipPoints);
        assertTrue(violations.isEmpty());
        
        Profile invalidCaptainsClubLoyaltyRelationshipPoints = getInstance()
                .captainsClubLoyaltyRelationshipPoints(-1)
                .build();
        
        violations = VALIDATOR.validate(invalidCaptainsClubLoyaltyRelationshipPoints);
        assertAnnotationValidationFailure(violations, Min.class);
    }
    
    @Test
    public void testValidationForCelebrityBlueChipLoyaltyTier() {
        Profile validCelebrityBlueChipLoyaltyTier = getInstance()
                .celebrityBlueChipLoyaltyTier(CelebrityBlueChipLoyaltyTier.AMETHYST)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validCelebrityBlueChipLoyaltyTier);
        assertTrue(violations.isEmpty());
        
        Profile nullCelebrityBlueChipLoyaltyTier = getInstance()
                .celebrityBlueChipLoyaltyTier(null)
                .build();
        
        violations = VALIDATOR.validate(nullCelebrityBlueChipLoyaltyTier);
        assertTrue(violations.isEmpty());
        
        Profile invalidCelebrityBlueChipLoyaltyTier = getInstance()
                .celebrityBlueChipLoyaltyTier(CelebrityBlueChipLoyaltyTier.INVALID)
                .build();
        
        violations = VALIDATOR.validate(invalidCelebrityBlueChipLoyaltyTier);
        assertAnnotationValidationFailure(violations, LoyaltyTier.class);
    }
    
    @Test
    public void testValidationForCelebrityBlueChipLoyaltyIndividualPoints() {
        Profile validCelebrityBlueChipLoyaltyIndividualPoints = getInstance()
                .celebrityBlueChipLoyaltyIndividualPoints(69)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validCelebrityBlueChipLoyaltyIndividualPoints);
        assertTrue(violations.isEmpty());
        
        Profile nullCelebrityBlueChipLoyaltyIndividualPoints = getInstance()
                .celebrityBlueChipLoyaltyIndividualPoints(null)
                .build();
        
        violations = VALIDATOR.validate(nullCelebrityBlueChipLoyaltyIndividualPoints);
        assertTrue(violations.isEmpty());
        
        Profile invalidCelebrityBlueChipLoyaltyIndividualPoints = getInstance()
                .celebrityBlueChipLoyaltyIndividualPoints(-1)
                .build();
        
        violations = VALIDATOR.validate(invalidCelebrityBlueChipLoyaltyIndividualPoints);
        assertAnnotationValidationFailure(violations, Min.class);
    }
    
    @Test
    public void testValidationForCelebrityBlueChipLoyaltyRelationshipPoints() {
        Profile validCelebrityBlueChipLoyaltyRelationshipPoints = getInstance()
                .celebrityBlueChipLoyaltyRelationshipPoints(69)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validCelebrityBlueChipLoyaltyRelationshipPoints);
        assertTrue(violations.isEmpty());
        
        Profile nullCelebrityBlueChipLoyaltyRelationshipPoints = getInstance()
                .celebrityBlueChipLoyaltyRelationshipPoints(null)
                .build();
        
        violations = VALIDATOR.validate(nullCelebrityBlueChipLoyaltyRelationshipPoints);
        assertTrue(violations.isEmpty());
        
        Profile invalidCelebrityBlueChipLoyaltyRelationshipPoints = getInstance()
                .celebrityBlueChipLoyaltyRelationshipPoints(-1)
                .build();
        
        violations = VALIDATOR.validate(invalidCelebrityBlueChipLoyaltyRelationshipPoints);
        assertAnnotationValidationFailure(violations, Min.class);
    }
    
    @Test
    public void testValidationForClubRoyaleLoyaltyTier() {
        Profile validClubRoyaleLoyaltyTier = getInstance()
                .clubRoyaleLoyaltyTier(ClubRoyaleLoyaltyTier.MASTERS)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validClubRoyaleLoyaltyTier);
        assertTrue(violations.isEmpty());
        
        Profile nullClubRoyaleLoyaltyTier = getInstance()
                .clubRoyaleLoyaltyTier(null)
                .build();
        
        violations = VALIDATOR.validate(nullClubRoyaleLoyaltyTier);
        assertTrue(violations.isEmpty());
        
        Profile invalidClubRoyaleLoyaltyTier = getInstance()
                .clubRoyaleLoyaltyTier(ClubRoyaleLoyaltyTier.INVALID)
                .build();
        
        violations = VALIDATOR.validate(invalidClubRoyaleLoyaltyTier);
        assertAnnotationValidationFailure(violations, LoyaltyTier.class);
    }
    
    @Test
    public void testValidationForClubRoyaleLoyaltyIndividualPoints() {
        Profile validClubRoyaleLoyaltyIndividualPoints = getInstance()
                .clubRoyaleLoyaltyIndividualPoints(69)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validClubRoyaleLoyaltyIndividualPoints);
        assertTrue(violations.isEmpty());
        
        Profile nullClubRoyaleLoyaltyIndividualPoints = getInstance()
                .clubRoyaleLoyaltyIndividualPoints(null)
                .build();
        
        violations = VALIDATOR.validate(nullClubRoyaleLoyaltyIndividualPoints);
        assertTrue(violations.isEmpty());
        
        Profile invalidClubRoyaleLoyaltyIndividualPoints = getInstance()
                .clubRoyaleLoyaltyIndividualPoints(-1)
                .build();
        
        violations = VALIDATOR.validate(invalidClubRoyaleLoyaltyIndividualPoints);
        assertAnnotationValidationFailure(violations, Min.class);
    }
    
    @Test
    public void testValidationForClubRoyaleLoyaltyRelationshipPoints() {
        Profile validClubRoyaleLoyaltyRelationshipPoints = getInstance()
                .clubRoyaleLoyaltyRelationshipPoints(69)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validClubRoyaleLoyaltyRelationshipPoints);
        assertTrue(violations.isEmpty());
        
        Profile nullClubRoyaleLoyaltyRelationshipPoints = getInstance()
                .clubRoyaleLoyaltyRelationshipPoints(null)
                .build();
        
        violations = VALIDATOR.validate(nullClubRoyaleLoyaltyRelationshipPoints);
        assertTrue(violations.isEmpty());
        
        Profile invalidClubRoyaleLoyaltyRelationshipPoints = getInstance()
                .clubRoyaleLoyaltyRelationshipPoints(-1)
                .build();
        
        violations = VALIDATOR.validate(invalidClubRoyaleLoyaltyRelationshipPoints);
        assertAnnotationValidationFailure(violations, Min.class);
    }
    
    @Test
    public void testValidationForCrownAndAnchorSocietyLoyaltyTier() {
        Profile validCrownAndAnchorSocietyLoyaltyTier = getInstance()
                .crownAndAnchorSocietyLoyaltyTier(CrownAndAnchorSocietyLoyaltyTier.DIAMOND)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validCrownAndAnchorSocietyLoyaltyTier);
        assertTrue(violations.isEmpty());
        
        Profile nullCrownAndAnchorSocietyLoyaltyTier = getInstance()
                .crownAndAnchorSocietyLoyaltyTier(null)
                .build();
        
        violations = VALIDATOR.validate(nullCrownAndAnchorSocietyLoyaltyTier);
        assertTrue(violations.isEmpty());
        
        Profile invalidCrownAndAnchorSocietyLoyaltyTier = getInstance()
                .crownAndAnchorSocietyLoyaltyTier(CrownAndAnchorSocietyLoyaltyTier.INVALID)
                .build();
        
        violations = VALIDATOR.validate(invalidCrownAndAnchorSocietyLoyaltyTier);
        assertAnnotationValidationFailure(violations, LoyaltyTier.class);
    }
    
    @Test
    public void testValidationForCrownAndAnchorSocietyLoyaltyIndividualPoints() {
        Profile validCrownAndAnchorSocietyLoyaltyIndividualPoints = getInstance()
                .crownAndAnchorSocietyLoyaltyIndividualPoints(69)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validCrownAndAnchorSocietyLoyaltyIndividualPoints);
        assertTrue(violations.isEmpty());
        
        Profile nullCrownAndAnchorSocietyLoyaltyIndividualPoints = getInstance()
                .crownAndAnchorSocietyLoyaltyIndividualPoints(null)
                .build();
        
        violations = VALIDATOR.validate(nullCrownAndAnchorSocietyLoyaltyIndividualPoints);
        assertTrue(violations.isEmpty());
        
        Profile invalidCrownAndAnchorSocietyLoyaltyIndividualPoints = getInstance()
                .crownAndAnchorSocietyLoyaltyIndividualPoints(-1)
                .build();
        
        violations = VALIDATOR.validate(invalidCrownAndAnchorSocietyLoyaltyIndividualPoints);
        assertAnnotationValidationFailure(violations, Min.class);
    }
    
    @Test
    public void testValidationForCrownAndAnchorSocietyLoyaltyRelationshipPoints() {
        Profile validCrownAndAnchorSocietyLoyaltyRelationshipPoints = getInstance()
                .crownAndAnchorSocietyLoyaltyRelationshipPoints(69)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validCrownAndAnchorSocietyLoyaltyRelationshipPoints);
        assertTrue(violations.isEmpty());
        
        Profile nullCrownAndAnchorSocietyLoyaltyRelationshipPoints = getInstance()
                .crownAndAnchorSocietyLoyaltyRelationshipPoints(null)
                .build();
        
        violations = VALIDATOR.validate(nullCrownAndAnchorSocietyLoyaltyRelationshipPoints);
        assertTrue(violations.isEmpty());
        
        Profile invalidCrownAndAnchorSocietyLoyaltyRelationshipPoints = getInstance()
                .crownAndAnchorSocietyLoyaltyRelationshipPoints(-1)
                .build();
        
        violations = VALIDATOR.validate(invalidCrownAndAnchorSocietyLoyaltyRelationshipPoints);
        assertAnnotationValidationFailure(violations, Min.class);
    }
    
    @Test
    public void testValidationForGender() {
        Profile validGender = getInstance()
                .gender(Gender.M)
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validGender);
        assertTrue(violations.isEmpty());
        
        Profile nullGender = getInstance()
                .gender(Gender.M)
                .build();
        
        violations = VALIDATOR.validate(nullGender);
        assertTrue(violations.isEmpty());
        
        Profile invalidGender = getInstance()
                .gender(Gender.get("juana"))
                .build();
        
        violations = VALIDATOR.validate(invalidGender);
        assertAnnotationValidationFailure(violations, ValidGender.class);
    }
    
    @Test
    public void testValidationForVdsId() {
        Profile validVdsId = getInstance()
                .vdsId("G1234567")
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validVdsId);
        assertTrue(violations.isEmpty());
        
        Profile invalidVdsId = getInstance()
                .vdsId("G")
                .build();
        
        violations = VALIDATOR.validate(invalidVdsId);
        assertAnnotationValidationFailure(violations, Pattern.class);
        
        Profile nullVdsId = getInstance()
                .vdsId(null)
                .build();
        
        violations = VALIDATOR.validate(nullVdsId);
        assertAnnotationValidationFailure(violations, NotBlank.class);
        
        Profile blankVdsId = getInstance()
                .vdsId("")
                .build();
        
        violations = VALIDATOR.validate(blankVdsId);
        assertAnnotationValidationFailure(violations, NotBlank.class);
        
        Profile longVdsId = getInstance()
                .vdsId("G123456789123456789")
                .build();
        
        violations = VALIDATOR.validate(longVdsId);
        assertAnnotationValidationFailure(violations, Size.class);
    }
    
    @Test
    public void testValidationForNickname() {
        Profile validNickname = getInstance()
                .nickname("Food")
                .build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(validNickname);
        assertTrue(violations.isEmpty());
        
        Profile nullNickname = getInstance()
                .nickname(null)
                .build();
        
        violations = VALIDATOR.validate(nullNickname);
        assertTrue(violations.isEmpty());
        
        Profile invalidNickname = getInstance()
                .nickname("@")
                .build();
        
        violations = VALIDATOR.validate(invalidNickname);
        assertAnnotationValidationFailure(violations, Pattern.class);
        
        Profile longNickname = getInstance()
                .nickname("A Really Long Nickname For Some Reason")
                .build();
        
        violations = VALIDATOR.validate(longNickname);
        assertAnnotationValidationFailure(violations, Pattern.class);
    }
    
    @Test
    public void testValidationForComplexPropertiesWhenPresent() {
        Address address = Address.builder().build();
        Profile invalidAddressFields = getInstance().address(address).build();
        
        Set<ConstraintViolation<Profile>> violations = VALIDATOR.validate(invalidAddressFields);
        assertFalse(violations.isEmpty());
        
        EmergencyContact emergencyContact = EmergencyContact.builder().phoneNumber("abc").build();
        Profile invalidEmergencyContactFields = getInstance().emergencyContact(emergencyContact).build();
        
        violations = VALIDATOR.validate(invalidEmergencyContactFields);
        assertFalse(violations.isEmpty());
    }
    
    private ObjectNode getSampleJson(Profile... profile) {
        Profile profileToSerialize = (profile != null && profile.length > 0) ? profile[0] : getInstance().build();
        
        return OBJECT_MAPPER.convertValue(profileToSerialize, ObjectNode.class);
    }
    
    private Profile.ProfileBuilder getInstance() {
        Address address = Address.builder()
                .addressOne("1234 Fake Street")
                .addressTwo("#4321")
                .city("Springfield")
                .state("IL")
                .zipCode("33125")
                .residencyCountryCode("USA")
                .build();
        
        EmergencyContact emergencyContact = EmergencyContact.builder()
                .firstName("Brad")
                .lastName("Pitt")
                .phoneNumber("3058673509")
                .relationship("Father")
                .build();
        
        return Profile.builder()
                .address(address)
                .avatar("http://avatars.rccl.com/some/image.jpg")
                .birthCountryCode("USA")
                .captainsClubLoyaltyTier(CaptainsClubLoyaltyTier.CLASSIC)
                .clubRoyaleLoyaltyTier(ClubRoyaleLoyaltyTier.MASTERS)
                .celebrityBlueChipLoyaltyTier(CelebrityBlueChipLoyaltyTier.AMETHYST)
                .crownAndAnchorSocietyLoyaltyTier(CrownAndAnchorSocietyLoyaltyTier.DIAMOND)
                .citizenshipCountryCode("USA")
                .emergencyContact(emergencyContact)
                .gender(Gender.F)
                .vdsId("G1234567")
                .nickname("Juanita");
    }
}
