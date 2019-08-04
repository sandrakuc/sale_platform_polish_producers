package onlypolish.user.opinion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlypolish.user.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="OPINIONS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
//todo: fill and extend
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User author;

    @Size(min=2, max = 2000)
    private String content;

    private Date date;
}
