package onlypolish.securityalert;

import org.springframework.data.repository.CrudRepository;

public interface SecurityAlertRepo extends CrudRepository<SecurityAlert, Long> {
    SecurityAlert getById(long id);
}
