package onlypolish.securityalert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlypolish.user.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="SECURITY_ALERTS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @ManyToOne
    protected User informer;

    @ManyToOne
    protected User guilty;

    //automaticly savings current date
    protected Date date;

    @Size(min = 2, max=2000)
    protected String description;

    //automaticly savings current url
    protected String url;

    @Enumerated(EnumType.STRING)
    protected AlertStatus alertStatus;

    @Enumerated(EnumType.STRING)
    protected AlertType alertType;

    public boolean isWaiting(){
        return AlertStatus.WAITING.equals(alertStatus);
    }

    public boolean isAccepted(){
        return AlertStatus.ACCEPTED.equals(alertStatus);
    }

    public boolean isNotMadeInPolandSecurityAlert(){
        return AlertType.NOT_MADE_IN_POLAND_SECURITY_ALERT.equals(alertType);
    }

    public boolean isCheatSecurityAlert(){
        return AlertType.CHEAT_SECURITY_ALERT.equals(alertType);
    }

    public boolean isOffensiveOpinionSecurityAlert(){
        return AlertType.OFFENSIVE_OPINION_SECURITY_ALERT.equals(alertType);
    }

    //to expansion
}
