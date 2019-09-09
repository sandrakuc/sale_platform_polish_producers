package onlypolish.shop.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="DELIVERY_POINTS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private DeliveryCompany deliveryCompany;

    @Size(min = 2, max = 30)
    private String adress;

    @Pattern(regexp = "^[\\d]{2}-[\\d]{3}$")
    private String postalCode;

    @Size(min = 2, max = 30)
    private String city;

    private String characteristicPoint;
}
