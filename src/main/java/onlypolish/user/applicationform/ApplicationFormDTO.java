package onlypolish.user.applicationform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationFormDTO {
    @Pattern(regexp = "^(.+)@(.+)$")
    @Size(min = 2, max = 30)
    private String email;

    @Pattern(regexp = "^[\\d]{9}")
    private String phoneNumber;

    @Size(min = 2, max = 30)
    private String userName;

    @Size(min = 2, max = 30)
    private String userSurname;

    @Size(min = 2, max = 30)
    private String userAddress;

    @Pattern(regexp = "^[\\d]{2}-[\\d]{3}$")
    private String userPostalCode;

    @Size(min = 2, max = 30)
    private String city;

    private String coowners;

    @Size(min = 2, max = 30)
    private String companyName;

    @Size(min = 2, max = 30)
    private String legalForm;

    @Pattern(regexp = "^[\\d]{9}")
    private String regon;

    @Pattern(regexp = "^[\\d]{10}")
    private String nip;

    @Pattern(regexp = "^[\\d]{10}")
    private String krs;

    @Pattern(regexp = "^[\\d]{4}")
    private String yearOfStarting;

    private String dateOfEntireToKrs;

    @Size(min=2, max=30)
    private String shopName;

    @Size(min=2, max=30)
    private String shopAddress;

    @Size(min=2, max=30)
    private String shopCity;

    @Pattern(regexp = "^[\\d]{2}-[\\d]{3}$")
    private String shopPostalCode;

    @Size(min=2, max=30)
    private String website;

    private String categories;

    @Size(min=2, max=2000)
    private String description;
}
