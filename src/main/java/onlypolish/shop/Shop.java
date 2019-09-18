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
import java.util.Objects;

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

    @Enumerated(EnumType.STRING)
    private ShopAndProductStatus status;

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", legalForm='" + legalForm + '\'' +
                ", regon='" + regon + '\'' +
                ", nip='" + nip + '\'' +
                ", krs='" + krs + '\'' +
                ", yearOfStarting='" + yearOfStarting + '\'' +
                ", dateOfEntireToKrs=" + dateOfEntireToKrs +
                ", shopName='" + shopName + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", shopCity='" + shopCity + '\'' +
                ", shopPostalCode='" + shopPostalCode + '\'' +
                ", website='" + website + '\'' +
                ", categories='" + categories + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return id == shop.id &&
                Objects.equals(companyName, shop.companyName) &&
                Objects.equals(legalForm, shop.legalForm) &&
                Objects.equals(regon, shop.regon) &&
                Objects.equals(nip, shop.nip) &&
                Objects.equals(krs, shop.krs) &&
                Objects.equals(yearOfStarting, shop.yearOfStarting) &&
                Objects.equals(dateOfEntireToKrs, shop.dateOfEntireToKrs) &&
                Objects.equals(shopName, shop.shopName) &&
                Objects.equals(shopAddress, shop.shopAddress) &&
                Objects.equals(shopCity, shop.shopCity) &&
                Objects.equals(shopPostalCode, shop.shopPostalCode) &&
                Objects.equals(website, shop.website) &&
                Objects.equals(categories, shop.categories) &&
                Objects.equals(description, shop.description) &&
                status == shop.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, legalForm, regon, nip, krs, yearOfStarting, dateOfEntireToKrs, shopName, shopAddress, shopCity, shopPostalCode, website, categories, description, status);
    }
}
