package uz.clickintegrationexample.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.clickintegrationexample.domain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}