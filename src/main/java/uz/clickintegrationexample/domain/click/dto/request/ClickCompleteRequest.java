package uz.clickintegrationexample.domain.click.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClickCompleteRequest {
    private Long clickTransId;

    private Integer serviceId;

    private String merchantTransId;

    private Integer merchantPrepareId;

    private Float amount;

    private Integer action;

    private Integer error;

    private String errorNote;

    private LocalDateTime signTime;

    private String signString;
}
