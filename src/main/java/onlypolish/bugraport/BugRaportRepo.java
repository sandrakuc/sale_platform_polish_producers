package onlypolish.bugraport;

import org.springframework.data.repository.CrudRepository;

public interface BugRaportRepo extends CrudRepository<BugRaport, Long> {
    BugRaport getById(long id);
}
