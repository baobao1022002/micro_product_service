package consumer.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private String id;
    private String customerId;
    private OrderStatus status;
    private Integer totalAmount;

}
