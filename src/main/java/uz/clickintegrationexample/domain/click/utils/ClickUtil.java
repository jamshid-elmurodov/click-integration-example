package uz.clickintegrationexample.domain.click.utils;

import uz.clickintegrationexample.domain.click.dto.request.ClickCompleteRequest;
import uz.clickintegrationexample.domain.click.dto.request.ClickPrepareRequest;
import uz.clickintegrationexample.domain.click.enums.ClickResponseCode;
import uz.clickintegrationexample.domain.click.exception.ClickException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClickUtil {
    public static final Integer PREPARE_ACTION = 0;
    public static final Integer COMPLETE_ACTION = 1;

    private static String MERCHANT_ID = "YOUR_MERCHANT_ID";
    private static String SERVICE_ID = "YOUR_SERVICE_ID";
    private static String SECRET_KEY = "YOUR_SECRET_KEY";

    //bu to'lov muvaffaqiyatli amalga oshirilganda keyin click userni shunga yo'naltiradi
    private static String RETURN_URL = "YOUR_RETURN_URL";

    /**
     * user to'lov qilishi uchun url generatsiya qiladi
     */
    public static String generatePaymentUrl(Long orderId, Float amount){
        return String.format(
                "https://my.click.uz/services/pay?service_id=%s&merchant_id=%s&transaction_param=%d&amount=%s&return_url=%s",
                SERVICE_ID, MERCHANT_ID, orderId, amount + "", RETURN_URL
        );
    }

    public static void authorizeSignString(ClickPrepareRequest request){
        String signString = String.format("%s%s%s%s%s%s%s",
                request.getClickTransId(),
                SERVICE_ID,
                SECRET_KEY,
                request.getMerchantTransId(),
                request.getAmount(),
                request.getAction(),
                request.getSignTime()
        );

        String md5 = getMd5(signString);

        if (!md5.equals(request.getSignString())){
            throw new ClickException(ClickResponseCode.SIGN_CHECK_FAILED);
        }
    }

    public static void authorizeSignString(ClickCompleteRequest request){
        String signString = String.format("%s%s%s%s%s%s%s%s",
                request.getClickTransId(),
                SERVICE_ID,
                SECRET_KEY,
                request.getMerchantTransId(),
                request.getMerchantPrepareId(),
                request.getAmount(),
                request.getAction(),
                request.getSignTime()
        );

        String md5 = getMd5(signString);

        if (!md5.equals(request.getSignString())){
            throw new ClickException(ClickResponseCode.SIGN_CHECK_FAILED);
        }
    }

    private static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(no.toString(16));

            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }

            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDateTime parseSignTime(String signTime) {
        return LocalDateTime.parse(signTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
