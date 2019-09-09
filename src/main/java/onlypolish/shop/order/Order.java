package onlypolish.shop.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import onlypolish.shop.Shop;
import onlypolish.shop.product.Product;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="ORDERS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    private List<Product> products;

    @ManyToOne
    private Shop shop;

    @DecimalMin(value = "0.01", inclusive = true)
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentWay paymentWay;

    @ManyToOne
    private DeliveryWay deliveryWay;

    @Size(min=2, max = 30)
    private String userName;

    @Size(min=2, max = 30)
    private String userSurname;

    @Size(min=2, max = 30)
    private String userLogin;

    @Size(min=2, max = 30)
    private String userEmail;

    @Size(min=2, max = 30)
    private String userPhone;

    @Size(min=2, max = 30)
    private String userAddress;

    @Size(min=2, max = 30)
    private String userPostalCode;

    @Size(min=2, max = 30)
    private String userCity;

    @Size(min=2, max = 30)
    private String invoiceAddress;

    @Size(min=2, max = 30)
    private String invoicePostalCode;

    @Size(min=2, max = 30)
    private String invoiceCity;

    @Size(min=2, max = 30)
    private String receiptAddress;

    @Size(min=2, max = 30)
    private String receiptPostalCode;

    @Size(min=2, max = 30)
    private String receiptCity;

    @Enumerated(EnumType.STRING)
    private OrderSatus satus;

}
