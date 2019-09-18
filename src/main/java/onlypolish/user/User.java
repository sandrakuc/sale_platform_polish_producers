package onlypolish.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlypolish.shop.Shop;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name="USERS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 2, max = 30)
    private String login;

    @Size(min = 16, max = 30)
    private String password;

    @Pattern(regexp = "^(.+)@(.+)$")
    @Size(min = 2, max = 30)
    private String email;

    @Pattern(regexp = "^[\\d]{9}")
    private String phoneNumber;

    @Size(min = 2, max = 30)
    private String name;

    @Size(min = 2, max = 30)
    private String surname;

    @Size(min = 2, max = 30)
    private String address;

    @Pattern(regexp = "^[\\d]{2}-[\\d]{3}$")
    private String postalCode;

    @Size(min = 2, max = 30)
    private String city;

    @Enumerated(EnumType.STRING)
    private Permissions permissions;

    @OneToOne
    private Shop shop;

    public boolean isSeller(){
        return Permissions.SELLER.equals(permissions);
    }

    public boolean isClient(){
        return Permissions.CLIENT.equals(permissions);
    }

    public boolean isAdmin(){
        return Permissions.ADMIN.equals(permissions);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", permissions=" + permissions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phoneNumber, user.phoneNumber) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(address, user.address) &&
                Objects.equals(postalCode, user.postalCode) &&
                Objects.equals(city, user.city) &&
                permissions == user.permissions;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, phoneNumber, name, surname, address, postalCode, city, permissions);
    }

    public boolean hasShop() {
        return shop != null;
    }

}
