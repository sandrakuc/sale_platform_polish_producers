package onlypolish.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

    public boolean isSeller(){
        return Permissions.SELLER.equals(permissions);
    }

    public boolean isClient(){
        return Permissions.CLIENT.equals(permissions);
    }

    public boolean isAdmin(){
        return Permissions.ADMIN.equals(permissions);
    }


}
