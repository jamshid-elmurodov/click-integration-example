package uz.clickintegrationexample.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.clickintegrationexample.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}