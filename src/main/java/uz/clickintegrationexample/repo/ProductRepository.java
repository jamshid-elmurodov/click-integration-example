package uz.clickintegrationexample.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.clickintegrationexample.domain.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}