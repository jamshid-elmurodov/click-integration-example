package uz.clickintegrationexample.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import uz.clickintegrationexample.domain.enums.OrderStatus;

import java.time.LocalDateTime;

@Entity(name = "click_transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClickTransaction {
    @Id
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    private Float amount;

    @Column(name = "prepare_time")
    private LocalDateTime prepareTime;

    @Column(name = "complete_time")
    private LocalDateTime completeTime;

    @Column(name = "cancel_time")
    private LocalDateTime cancelTime;

    private Integer error;

    @Column(name = "error_note")
    private String errorNote;

    private OrderStatus status;
}
