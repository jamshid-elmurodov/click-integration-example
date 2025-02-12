package uz.clickintegrationexample.domain.click.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClickResponseCode {
    SUCCESS(0, "Success"),
    SIGN_CHECK_FAILED(-1, "SIGN CHECK FAILED!"),
    INVALID_AMOUNT(-2, "Incorrect parameter amount"),
    ACTION_NOT_FOUND(-3, "Action not found"),
    ALREADY_PAID(-4, "Already paid"),
    ORDER_NOT_FOUND(-5, "Order does not exist"),
    TRANSACTION_NOT_FOUND(-6, "Transaction does not exist"),
    BAD_REQUEST(-7, "Error in request from click"),
    TRANSACTION_CANCELLED(-8, "Transaction cancelled");

    private final int code;
    private final String errorNote;
}
