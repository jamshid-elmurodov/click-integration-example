package uz.clickintegrationexample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.clickintegrationexample.domain.entity.Order;
import uz.clickintegrationexample.repo.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Order findById(Long aLong) {
        return orderRepository.findById(aLong).orElse(null);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }
}
