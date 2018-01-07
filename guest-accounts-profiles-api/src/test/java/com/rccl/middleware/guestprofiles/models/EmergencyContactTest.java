package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EmergencyContactTest {
    
    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    
    private static EmergencyContact.EmergencyContactBuilder getInstance() {
        return EmergencyContact.builder()
                .firstName("Brad")
                .lastName("Pitt")
                .phoneNumber("3058673509")
                .relationship("Father");
    }
    
    private static <T> void assertAnnotationValidationFailure(Set<ConstraintViolation<T>> violations, Class expectedClass) {
        assertEquals(1, violations.size());
        
        Class actualClass = violations.iterator().next().getConstraintDescriptor().getAnnotation().annotationType();
        assertEquals(expectedClass, actualClass);
    }
    
    @Test
    public void testNoArgInstance() {
        EmergencyContact ec = EmergencyContact.builder().build();
        
        assertNull(ec.getFirstName());
        assertNull(ec.getLastName());
        assertNull(ec.getPhoneNumber());
    }
    
    @Test
    public void testGetters() {
        String firstName = "Brad";
        String lastName = "Pitt";
        String phone = "3058673509";
        String relationship = "Father";
        
        EmergencyContact ec = EmergencyContact.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phone)
                .relationship(relationship)
                .build();
        
        assertEquals(firstName, ec.getFirstName());
        assertEquals(lastName, ec.getLastName());
        assertEquals(phone, ec.getPhoneNumber());
        assertEquals(relationship, ec.getRelationship());
    }
    
    @Test
    public void testAllPropertiesAreSerialized() {
        EmergencyContact ec = getInstance().build();
        
        ObjectMapper om = new ObjectMapper();
        JsonNode json = om.valueToTree(ec);
        
        int expectedNumberOfProperties = 4;
        
        assertEquals(expectedNumberOfProperties, json.size());
        
        assertTrue(json.has("firstName"));
        assertTrue(json.has("lastName"));
        assertTrue(json.has("phoneNumber"));
        assertTrue(json.has("relationship"));
    }
    
    @Test
    public void testNullPropertiesAreNotSerialized() {
        EmergencyContact ec = getInstance()
                .firstName(null)
                .build();
        
        ObjectMapper om = new ObjectMapper();
        JsonNode json = om.valueToTree(ec);
        
        assertFalse(json.has("firstName"));
    }
    
    @Test
    public void testValidationForName() {
        EmergencyContact validName = getInstance()
                .firstName("Juana")
                .lastName("Pacita")
                .relationship("Mother")
                .build();
        
        Set<ConstraintViolation<EmergencyContact>> violations = VALIDATOR.validate(validName);
        assertTrue(violations.isEmpty());
        
        EmergencyContact longName = getInstance()
                .firstName("JuanaJuanaJuanaJuanaJuanaJuanaJuanaJuanaJuanaJuanaJuanaJuanaJuanaJuana")
                .lastName("Pacita")
                .relationship("Mother")
                .build();
        
        violations = VALIDATOR.validate(longName);
        assertAnnotationValidationFailure(violations, Size.class);
    }
    
    @Test
    public void testValidationForPhone() {
        EmergencyContact validPhone = getInstance()
                .phoneNumber("3058675309")
                .build();
        
        Set<ConstraintViolation<EmergencyContact>> violations = VALIDATOR.validate(validPhone);
        assertTrue(violations.isEmpty());
    
        validPhone = getInstance()
                .phoneNumber("+1 305 867 5309")
                .build();
    
        violations = VALIDATOR.validate(validPhone);
        assertTrue(violations.isEmpty());
        
        EmergencyContact shortPhone = getInstance()
                .phoneNumber("90210")
                .build();
        
        violations = VALIDATOR.validate(shortPhone);
        assertAnnotationValidationFailure(violations, Size.class);
        
        EmergencyContact longPhone = getInstance()
                .phoneNumber("+333234 235 52344 305867530903490")
                .build();
        
        violations = VALIDATOR.validate(longPhone);
        assertAnnotationValidationFailure(violations, Size.class);
        
        EmergencyContact invalidCharactersPhone = getInstance()
                .phoneNumber("30586753A9")
                .build();
        
        violations = VALIDATOR.validate(invalidCharactersPhone);
        assertAnnotationValidationFailure(violations, Pattern.class);
    }
}
