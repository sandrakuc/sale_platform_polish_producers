package onlypolish.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long>{
    User getById(Long id);
    User getByLogin(String login);
}
