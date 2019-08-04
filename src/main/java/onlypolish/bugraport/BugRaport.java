package onlypolish.bugraport;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlypolish.user.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="BUG_RAPORTS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugRaport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User informer;

    @Size(min=2, max=2000)
    private String description;

    //automaticly savings current date
    private Date date;

    @Enumerated(EnumType.STRING)
    private RaportStatus status;

    public boolean isWaiting(){
        return RaportStatus.WAITING.equals(status);
    }

    public boolean isRepaired(){
        return RaportStatus.REPAIRED.equals(status);
    }

    public boolean isToRepair(){
        return RaportStatus.TO_REPAIR.equals(status);
    }

}
