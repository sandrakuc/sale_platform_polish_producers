package onlypolish.shop.order;

import onlypolish.shop.Shop;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepo extends CrudRepository<Order, Long> {
    List <Order> getByShop(Shop shop);
}
