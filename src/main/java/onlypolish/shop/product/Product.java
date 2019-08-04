package onlypolish.shop.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlypolish.shop.Shop;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Table(name="PRODUCTS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product { //robocza wersja

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min=2, max=30)
    private String name;

    @Size(min=2)
    private String description;

    @ManyToOne
    private Shop shop;
}
