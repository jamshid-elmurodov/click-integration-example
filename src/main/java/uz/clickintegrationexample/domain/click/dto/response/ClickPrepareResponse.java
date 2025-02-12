package uz.clickintegrationexample.domain.click.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import uz.clickintegrationexample.domain.click.dto.request.ClickPrepareRequest;
import uz.clickintegrationexample.domain.click.enums.ClickResponseCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClickPrepareResponse {
    @JsonProperty("click_trans_id")
    private Long clickTransId;

    @JsonProperty("merchant_trans_id")
    private String merchantTransId;

    @JsonProperty("merchant_prepare_id")
    private Long merchantPrepareId;

    private Integer error;

    @JsonProperty("error_note")
    private String errorNote;

    public static ClickPrepareResponse from(ClickPrepareRequest request, Long merchantPrepareId) {
        return ClickPrepareResponse.builder()
                .clickTransId(request.getClickTransId())
                .merchantTransId(request.getMerchantTransId())
                .merchantPrepareId(merchantPrepareId)
                .error(ClickResponseCode.SUCCESS.getCode())
                .errorNote(ClickResponseCode.SUCCESS.getErrorNote())
                .build();
    }
}
