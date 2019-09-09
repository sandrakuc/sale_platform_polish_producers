package onlypolish.shop;

import onlypolish.user.User;
import org.springframework.data.repository.CrudRepository;

public interface ShopRepo extends CrudRepository<Shop, Long> {
    Shop getById(long id);
    Shop getByUser(User user);
}
