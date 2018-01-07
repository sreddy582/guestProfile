package com.rccl.middleware.guestprofiles.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lightbend.lagom.serialization.Jsonable;
import com.rccl.middleware.common.beans.Gender;
import com.rccl.middleware.common.validation.validator.ValidGender;
import com.rccl.middleware.guest.optin.EmailOptins;
import com.rccl.middleware.guest.optin.PostalOptins;
import com.rccl.middleware.guestprofiles.validation.LoyaltyTier;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profile implements Jsonable {
    
    private static final long serialVersionUID = 1L;
    
    @Valid
    private Address address;
    
    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            message = "A valid avatar URL path is required, if being provided.")
    private String avatar;
    
    @Pattern(regexp = "[A-Za-z]{3}", message = "The birth country code "
            + "is required to be the three-character country code.")
    private String birthCountryCode;
    
    @Pattern(regexp = "[A-Za-z]{3}", message = "The citizenship country code "
            + "is required to be the three-character country code.")
    private String citizenshipCountryCode;
    
    @LoyaltyTier(message = "The Captain's Club Loyalty tier value is invalid.")
    private CaptainsClubLoyaltyTier captainsClubLoyaltyTier;
    
    @Min(value = 0, message = "The Captain's Club Loyalty individual points must be positive.")
    private Integer captainsClubLoyaltyIndividualPoints;
    
    @Min(value = 0, message = "The Captain's Club Loyalty relationship points must be positive.")
    private Integer captainsClubLoyaltyRelationshipPoints;
    
    @LoyaltyTier(message = "The Celebrity Blue Chip Loyalty tier value is invalid.")
    private CelebrityBlueChipLoyaltyTier celebrityBlueChipLoyaltyTier;
    
    @Min(value = 0, message = "The Celebrity Blue Chip Loyalty individual points must be positive.")
    private Integer celebrityBlueChipLoyaltyIndividualPoints;
    
    @Min(value = 0, message = "The Celebrity Blue Chip Loyalty relationship points must be positive.")
    private Integer celebrityBlueChipLoyaltyRelationshipPoints;
    
    @LoyaltyTier(message = "The Club Royale Loyalty tier value is invalid.")
    private ClubRoyaleLoyaltyTier clubRoyaleLoyaltyTier;
    
    @Min(value = 0, message = "The Club Royale Loyalty individual points must be positive.")
    private Integer clubRoyaleLoyaltyIndividualPoints;
    
    @Min(value = 0, message = "The Club Royale Loyalty relationship points must be positive.")
    private Integer clubRoyaleLoyaltyRelationshipPoints;
    
    @LoyaltyTier(message = "The Crown and Anchor Society Loyalty tier value is invalid.")
    private CrownAndAnchorSocietyLoyaltyTier crownAndAnchorSocietyLoyaltyTier;
    
    @Min(value = 0, message = "The Crown and Anchor Society Loyalty individual points must be positive.")
    private Integer crownAndAnchorSocietyLoyaltyIndividualPoints;
    
    @Min(value = 0, message = "The Crown and Anchor Society Loyalty relationship points must be positive.")
    private Integer crownAndAnchorSocietyLoyaltyRelationshipPoints;
    
    @Valid
    private EmergencyContact emergencyContact;
    
    @ValidGender
    private Gender gender;
    
    @Pattern(regexp = "^[A-Za-z0-9]+[A-Za-z0-9\\s]{0,19}$",
            message = "If provided, the nickname must be at least one alphanumeric character.")
    @Size(min = 1, max = 50, message = "The nickname must be at least one (1) character"
            + " and maximum of fifty (50) characters.")
    private String nickname;
    
    @NotBlank(message = "A VDS ID is required.")
    @Pattern(regexp = "^(([GEC])\\d+)*$", message = "The VDS ID is invalidly formatted.")
    @Size(max = 9, message = "The VDS ID can have a maximum of 9 characters.")
    private String vdsId;
    
    @Valid
    private EmailOptins emailOptins;
    
    @Valid
    private PostalOptins postalOptins;
}
