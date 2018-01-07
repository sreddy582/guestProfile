package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AddressTest {
    
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    
    private static Address.AddressBuilder getInstance() {
        return Address.builder()
                .addressOne("1234 Fake Street")
                .addressTwo("#4321")
                .city("Springfield")
                .state("IL")
                .zipCode("33125");
    }
    
    private static <T> void assertAnnotationValidationFailure(Set<ConstraintViolation<T>> violations, Class expectedClass) {
        assertEquals(1, violations.size());
        
        Class actualClass = violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType();
        assertEquals(expectedClass, actualClass);
    }
    
    @Test
    public void testNoArgInstance() {
        Address address = Address.builder().build();
        
        assertNull(address.getAddressOne());
        assertNull(address.getAddressTwo());
        assertNull(address.getCity());
        assertNull(address.getState());
        assertNull(address.getZipCode());
    }
    
    @Test
    public void testGetters() {
        Address.AddressBuilder builder = Address.builder();
        
        String addressOne = "1234 Fake Street";
        String addressTwo = "#4321";
        String city = "Springfield";
        String state = "Illinois";
        String zip = "33125";
        
        Address address = builder
                .addressOne(addressOne)
                .addressTwo(addressTwo)
                .city(city)
                .state(state)
                .zipCode(zip)
                .build();
        
        assertEquals(addressOne, address.getAddressOne());
        assertEquals(addressTwo, address.getAddressTwo());
        assertEquals(city, address.getCity());
        assertEquals(state, address.getState());
        assertEquals(zip, address.getZipCode());
    }
    
    @Test
    public void testAllPropertiesAreSerialized() {
        Address address = getInstance().build();
        
        ObjectMapper om = new ObjectMapper();
        JsonNode json = om.valueToTree(address);
        
        final int expectedNumberOfProperties = 5;
        
        assertEquals(expectedNumberOfProperties, json.size());
        
        assertTrue(json.has("addressOne"));
        assertTrue(json.has("addressTwo"));
        assertTrue(json.has("city"));
        assertTrue(json.has("state"));
        assertTrue(json.has("zipCode"));
    }
    
    @Test
    public void testNullValuesAreNotSerialized() {
        Address address = getInstance()
                .state(null)
                .zipCode(null).build();
        
        ObjectMapper om = new ObjectMapper();
        JsonNode json = om.valueToTree(address);
        
        final int expectedNumberOfProperties = 3;
        
        assertEquals(expectedNumberOfProperties, json.size());
        
        assertTrue(json.has("addressOne"));
        assertTrue(json.has("addressTwo"));
        assertTrue(json.has("city"));
        
        assertFalse(json.has("state"));
        assertFalse(json.has("zipCode"));
    }
    
    @Test
    public void testValidationForAddressOne() {
        Address validAddressOne = getInstance()
                .addressOne("1234 Fake Street")
                .build();
        
        Set<ConstraintViolation<Address>> violations = VALIDATOR.validate(validAddressOne);
        assertTrue(violations.isEmpty());
        
        Address nullAddressOne = getInstance()
                .addressOne(null)
                .build();
        
        violations = VALIDATOR.validate(nullAddressOne);
        assertAnnotationValidationFailure(violations, NotNull.class);
        
        Address blankAddressOne = getInstance()
                .addressOne("")
                .build();
        
        violations = VALIDATOR.validate(blankAddressOne);
        assertAnnotationValidationFailure(violations, Size.class);
        
        Address shortAddressOne = getInstance()
                .addressOne("123")
                .build();
        
        violations = VALIDATOR.validate(shortAddressOne);
        assertAnnotationValidationFailure(violations, Size.class);
        
        Address longAddressOne = getInstance()
                .addressOne("ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD"
                        + "ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD"
                        + "ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD"
                        + "ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD"
                        + "ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD")
                .build();
        
        violations = VALIDATOR.validate(longAddressOne);
        assertAnnotationValidationFailure(violations, Size.class);
    }
    
    @Test
    public void testValidationForAddressTwo() {
        Address validAddressTwo = getInstance()
                .addressTwo("Unit 234")
                .build();
        
        Set<ConstraintViolation<Address>> violations = VALIDATOR.validate(validAddressTwo);
        assertTrue(violations.isEmpty());
        
        Address blankAddressTwo = getInstance()
                .addressTwo("")
                .build();
        
        violations = VALIDATOR.validate(blankAddressTwo);
        assertTrue(violations.isEmpty());
        
        Address longAddressTwo = getInstance()
                .addressTwo("ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD"
                        + "ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD"
                        + "ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD"
                        + "ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD"
                        + "ABCD ABCD ABCD ABCD ABCD ABCD ABCD ABCD")
                .build();
        
        violations = VALIDATOR.validate(longAddressTwo);
        assertAnnotationValidationFailure(violations, Size.class);
    }
    
    @Test
    public void testValidationForCity() {
        Address validCity = getInstance()
                .city("Miami")
                .build();
        
        Set<ConstraintViolation<Address>> violations = VALIDATOR.validate(validCity);
        assertTrue(violations.isEmpty());
        
        Address nullCity = getInstance()
                .city(null)
                .build();
        
        violations = VALIDATOR.validate(nullCity);
        assertAnnotationValidationFailure(violations, NotNull.class);
        
        Address blankCity = getInstance()
                .city("")
                .build();
        
        violations = VALIDATOR.validate(blankCity);
        assertAnnotationValidationFailure(violations, Size.class);
        
        Address shortCity = getInstance()
                .city("A")
                .build();
        
        violations = VALIDATOR.validate(shortCity);
        assertTrue(violations.isEmpty());
        
        Address longCity = getInstance()
                .city("ABCDE ABCDE ABCDE ABCDE ABCDE ABCDE ABCDE ABCDE ABCDE ABCDE ABCDE")
                .build();
        
        violations = VALIDATOR.validate(longCity);
        assertAnnotationValidationFailure(violations, Size.class);
    }
    
    @Test
    public void testValidationForState() {
        Address validState = getInstance()
                .state("FL")
                .build();
        
        Set<ConstraintViolation<Address>> violations = VALIDATOR.validate(validState);
        assertTrue(violations.isEmpty());
        
        Address nullState = getInstance()
                .state(null)
                .build();
        
        violations = VALIDATOR.validate(nullState);
        assertAnnotationValidationFailure(violations, NotNull.class);
        
        Address blankState = getInstance()
                .state("")
                .build();
        
        violations = VALIDATOR.validate(blankState);
        assertAnnotationValidationFailure(violations, Size.class);
        
        Address shortState = getInstance()
                .state("Z")
                .build();
        
        violations = VALIDATOR.validate(shortState);
        assertAnnotationValidationFailure(violations, Size.class);
        
        Address longState = getInstance()
                .state("ABCDE")
                .build();
        
        violations = VALIDATOR.validate(longState);
        assertAnnotationValidationFailure(violations, Size.class);
        
        Address invalidCharactersState = getInstance()
                .state("A9")
                .build();
        
        violations = VALIDATOR.validate(invalidCharactersState);
        assertAnnotationValidationFailure(violations, Pattern.class);
    }
    
    @Test
    public void testValidationForZip() {
        Address validZip = getInstance()
                .zipCode("90210")
                .build();
        
        Set<ConstraintViolation<Address>> violations = VALIDATOR.validate(validZip);
        assertTrue(violations.isEmpty());
        
        Address nullZip = getInstance()
                .zipCode(null)
                .build();
        
        violations = VALIDATOR.validate(nullZip);
        assertAnnotationValidationFailure(violations, NotNull.class);
        
        Address blankZip = getInstance()
                .zipCode("")
                .build();
        
        violations = VALIDATOR.validate(blankZip);
        assertAnnotationValidationFailure(violations, Pattern.class);
        
        Address shortZip = getInstance()
                .zipCode("1234!1234")
                .build();
        
        violations = VALIDATOR.validate(shortZip);
        assertAnnotationValidationFailure(violations, Pattern.class);
        
        Address invalidCharactersZip = getInstance()
                .zipCode("")
                .build();
        
        violations = VALIDATOR.validate(invalidCharactersZip);
        assertAnnotationValidationFailure(violations, Pattern.class);
    }
}
