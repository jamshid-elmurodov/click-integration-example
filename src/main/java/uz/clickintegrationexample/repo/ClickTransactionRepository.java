package uz.clickintegrationexample.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.clickintegrationexample.domain.entity.ClickTransaction;

public interface ClickTransactionRepository extends JpaRepository<ClickTransaction, Long> {
}