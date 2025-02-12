package uz.clickintegrationexample.domain.click.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import uz.clickintegrationexample.domain.click.enums.ClickResponseCode;

@Getter
@RequiredArgsConstructor
public class ClickException extends RuntimeException {
    private final ClickResponseCode code;
}
