package uz.clickintegrationexample.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import uz.clickintegrationexample.domain.click.dto.request.ClickCompleteRequest;
import uz.clickintegrationexample.domain.click.dto.request.ClickPrepareRequest;
import uz.clickintegrationexample.domain.click.dto.response.ClickCompleteResponse;
import uz.clickintegrationexample.domain.click.dto.response.ClickPrepareResponse;
import uz.clickintegrationexample.domain.click.exception.ClickException;
import uz.clickintegrationexample.domain.click.utils.ClickUtil;
import uz.clickintegrationexample.service.ClickTransactionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/click")
@Slf4j
public class ClickController {
    private final ClickTransactionService clickTransactionService;

    @PostMapping(value = "/prepare", consumes = "application/x-www-form-urlencoded")
    public ClickPrepareResponse prepare(
            @RequestParam("click_trans_id") Long clickTransId,
            @RequestParam("service_id") Integer serviceId,
            @RequestParam("merchant_trans_id") String merchantTransId,
            @RequestParam("amount") Float amount,
            @RequestParam("action") Integer action,
            @RequestParam("error") Integer error,
            @RequestParam("error_note") String errorNote,
            @RequestParam("sign_time") String signTime,
            @RequestParam("sign_string") String signString
    ) {
        log.info("Prepare request");

        ClickPrepareRequest build = ClickPrepareRequest.builder()
                .clickTransId(clickTransId)
                .serviceId(serviceId)
                .merchantTransId(merchantTransId)
                .amount(amount)
                .action(action)
                .error(error)
                .errorNote(errorNote)
                .signTime(ClickUtil.parseSignTime(signTime))
                .signString(signString)
                .build();

        ClickUtil.authorizeSignString(build);

        return clickTransactionService.prepare(build);
    }

    @PostMapping(value = "/complete", consumes = "application/x-www-form-urlencoded")
    public ClickCompleteResponse complete(
            @RequestParam("click_trans_id") Long clickTransId,
            @RequestParam("service_id") Integer serviceId,
            @RequestParam("merchant_trans_id") String merchantTransId,
            @RequestParam(value = "merchant_prepare_id", required = false) Integer merchantPrepareId,
            @RequestParam("amount") Float amount,
            @RequestParam("action") Integer action,
            @RequestParam("error") Integer error,
            @RequestParam("error_note") String errorNote,
            @RequestParam("sign_time") String signTime,
            @RequestParam("sign_string") String signString
    ) {
        log.info("Complete request");

        ClickCompleteRequest build = ClickCompleteRequest.builder()
                .clickTransId(clickTransId)
                .serviceId(serviceId)
                .merchantTransId(merchantTransId)
                .merchantPrepareId(merchantPrepareId)
                .amount(amount)
                .action(action)
                .error(error)
                .errorNote(errorNote)
                .signTime(ClickUtil.parseSignTime(signTime))
                .signString(signString)
                .build();

        ClickUtil.authorizeSignString(build);

        return clickTransactionService.complete(build);
    }

    @GetMapping
    public String test() {
        return ClickUtil.generatePaymentUrl(1L, 1000f);
    }

    @ExceptionHandler(ClickException.class)
    public ClickCompleteResponse handleException(ClickException e) {
        log.error("Error occurred: {}", e.getCode().getErrorNote());

        return ClickCompleteResponse.builder()
                .error(e.getCode().getCode())
                .errorNote(e.getCode().getErrorNote())
                .build();
    }
}
