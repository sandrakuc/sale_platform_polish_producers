package onlypolish.user.applicationform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlypolish.user.User;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="APPLICATION_FORMS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    private Date dateOfEntireToKrs;

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

    private String categories; //todo: map to Category class

    @Size(min=2, max=2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private FormStatus formStatus;

    public boolean isRejected(){
        return FormStatus.REJECTED.equals(formStatus);
    }

    public boolean isAccepted(){
        return FormStatus.ACCEPTED.equals(formStatus);
    }

    public boolean isConsidered(){
        return FormStatus.CONSIDERED.equals(formStatus);
    }

    public boolean isWaitingForMoney(){
        return FormStatus.WAITING_FOR_MONEY.equals(formStatus);
    }

    public boolean isWaiting(){
        return FormStatus.WAITING.equals(formStatus);
    }
}
