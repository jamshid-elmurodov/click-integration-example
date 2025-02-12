package uz.clickintegrationexample.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.clickintegrationexample.domain.enums.OrderStatus;

@Entity(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public boolean isComplete() {
        return status == OrderStatus.COMPLETE;
    }

    public boolean isCancel() {
        return status == OrderStatus.CANCEL;
    }
}
