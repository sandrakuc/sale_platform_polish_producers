package onlypolish.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="BLACK_LIST")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlackList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=2, max = 30)
    private String userName;

    @Size(min=2, max = 30)
    private String userSurname;

    @Size(min=2, max = 30)
    private String userLogin;

    @Size(min=2, max = 30)
    private String userEmail;

    @Enumerated(EnumType.STRING)
    private Punishment punishment;

    private Date date;
}
