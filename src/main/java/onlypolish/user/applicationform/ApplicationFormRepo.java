package onlypolish.user.applicationform;

import org.springframework.data.repository.CrudRepository;

public interface ApplicationFormRepo extends CrudRepository<ApplicationForm, Long> {
    ApplicationForm getById(long id);
}
