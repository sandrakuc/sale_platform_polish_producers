package onlypolish.shop;

import org.springframework.data.repository.CrudRepository;

public interface ShopRepo extends CrudRepository<Shop, Long> {
    Shop getById(long id);
}
