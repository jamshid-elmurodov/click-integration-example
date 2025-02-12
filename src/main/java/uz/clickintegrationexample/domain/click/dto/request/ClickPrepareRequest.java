package uz.clickintegrationexample.domain.click.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClickPrepareRequest {
    @JsonProperty("click_trans_id")
    private Long clickTransId;

    @JsonProperty("service_id")
    private Integer serviceId;

    @JsonProperty("merchant_trans_id")
    private String merchantTransId;

    private Float amount;

    private Integer action;

    private Integer error;

    @JsonProperty("error_note")
    private String errorNote;

    @JsonProperty("sign_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signTime;

    @JsonProperty("sign_string")
    private String signString;
}
