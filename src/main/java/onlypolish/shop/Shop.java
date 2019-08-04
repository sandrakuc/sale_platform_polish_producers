package onlypolish.shop;

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
@Table(name="SHOPS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

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

    private String categories; //todo: map to category class

    @Size(min=2, max=2000)
    private String description;
}
