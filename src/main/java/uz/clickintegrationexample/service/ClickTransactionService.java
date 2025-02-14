package uz.clickintegrationexample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.clickintegrationexample.domain.click.dto.request.ClickCompleteRequest;
import uz.clickintegrationexample.domain.click.dto.request.ClickPrepareRequest;
import uz.clickintegrationexample.domain.click.dto.response.ClickCompleteResponse;
import uz.clickintegrationexample.domain.click.dto.response.ClickPrepareResponse;
import uz.clickintegrationexample.domain.click.enums.ClickResponseCode;
import uz.clickintegrationexample.domain.click.exception.ClickException;
import uz.clickintegrationexample.domain.click.utils.ClickUtil;
import uz.clickintegrationexample.domain.entity.ClickTransaction;
import uz.clickintegrationexample.domain.entity.Order;
import uz.clickintegrationexample.domain.enums.OrderStatus;
import uz.clickintegrationexample.repo.ClickTransactionRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClickTransactionService {
    private final ClickTransactionRepository clickTransactionRepository;
    private final OrderService orderService;

    public ClickPrepareResponse prepare(ClickPrepareRequest request){
        if (!Objects.equals(request.getAction(), ClickUtil.PREPARE_ACTION)){
            throw new ClickException(ClickResponseCode.ACTION_NOT_FOUND);
        }

        Order order = getOrderOrThrowExc(request.getMerchantTransId());

        if (order.isComplete()){
            throw new ClickException(ClickResponseCode.ALREADY_PAID);
        }

        if (order.isCancel()){
            throw new ClickException(ClickResponseCode.TRANSACTION_CANCELLED);
        }

        if (!Objects.equals(request.getAmount(), order.getProduct().getPrice())){
            throw new ClickException(ClickResponseCode.INVALID_AMOUNT);
        }

        ClickTransaction save = clickTransactionRepository.save(ClickTransaction.builder()
                .id(request.getClickTransId())
                .amount(request.getAmount())
                .prepareTime(LocalDateTime.now())
                .orderId(order.getId())
                .status(OrderStatus.PREPARE)
                .build());

        return ClickPrepareResponse.from(request, save.getId());
    }

    public ClickCompleteResponse complete(ClickCompleteRequest request){
        if (!Objects.equals(request.getAction(), ClickUtil.COMPLETE_ACTION)){
            throw new ClickException(ClickResponseCode.ACTION_NOT_FOUND);
        }

        ClickTransaction clickTransaction = getTransactionOrThrowExc(request.getMerchantPrepareId());
        Order order = getOrderOrThrowExc(clickTransaction.getOrderId());

        if (clickTransaction.isComplete()){
            throw new ClickException(ClickResponseCode.ALREADY_PAID);
        }

        if (clickTransaction.isCancel()){
            throw new ClickException(ClickResponseCode.TRANSACTION_CANCELLED);
        }

        if (request.getError() != ClickResponseCode.SUCCESS.getCode()) {
            clickTransaction.setStatus(OrderStatus.CANCEL);
            clickTransactionRepository.save(clickTransaction);
            throw new ClickException(ClickResponseCode.TRANSACTION_CANCELLED);
        }

        clickTransaction.setStatus(OrderStatus.COMPLETE);
        clickTransactionRepository.save(clickTransaction);

        // bu yerda transaction amalga oshganda, sizning logikangiz bo'ladi
        order.setStatus(OrderStatus.COMPLETE);
        orderService.save(order);

        return ClickCompleteResponse.from(request);
    }

    private ClickTransaction getTransactionOrThrowExc(Long transactionId) {
        return clickTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new ClickException(ClickResponseCode.TRANSACTION_NOT_FOUND));
    }

    private Order getOrderOrThrowExc(Long orderId) {
        Order order = orderService.findById(orderId);

        if (Objects.isNull(order)) {
            throw new ClickException(ClickResponseCode.ORDER_NOT_FOUND);
        }

        return order;
    }
}
