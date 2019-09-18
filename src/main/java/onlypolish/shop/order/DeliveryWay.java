package onlypolish.shop.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;

@Entity
@Table(name="DELIVERY_WAYS")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryWay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private DeliveryCompany deliveryCompany;

    @Enumerated(EnumType.STRING)
    private PaymentWay paymentWay;

    @DecimalMin(value = "0.01", inclusive = true)
    private double price;

    public boolean isPocztaPolska(){
        return DeliveryCompany.POCZTA_POLSKA.equals(deliveryCompany);
    }

    public boolean isInpost(){
        return DeliveryCompany.INPOST.equals(deliveryCompany);
    }

    public boolean isDpd(){
        return DeliveryCompany.DPD.equals(deliveryCompany);
    }
}
