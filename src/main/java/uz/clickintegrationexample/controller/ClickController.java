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

    @PostMapping("/prepare")
    public ClickPrepareResponse prepare(@RequestBody ClickPrepareRequest request) {
        log.info("Prepare request");

        ClickUtil.authorizeSignString(request);

        return clickTransactionService.prepare(request);
    }

    @PostMapping("/complete")
    public ClickCompleteResponse complete(@RequestBody ClickCompleteRequest request) {
        log.info("Complete request");

        ClickUtil.authorizeSignString(request);

        return clickTransactionService.complete(request);
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
