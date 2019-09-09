package onlypolish.shop.product;

import onlypolish.shop.Shop;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository<Product, Long> {
    List<Product> getByShop(Shop shop);
}
