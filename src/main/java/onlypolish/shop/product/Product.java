package onlypolish.shop.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlypolish.shop.Shop;
import onlypolish.shop.ShopAndProductStatus;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
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

    private String category; //todo: build categories

    @DecimalMin(value = "0.01", inclusive = true)
    private double price;

    @Min(0)
    private int howManyAvailable;

    @Size(min=2)
    private String description;

    @ManyToOne
    private Shop shop;

    @Enumerated(EnumType.STRING)
    private ShopAndProductStatus status;
}
