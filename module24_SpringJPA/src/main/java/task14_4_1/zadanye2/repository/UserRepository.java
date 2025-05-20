package task14_4_1.zadanye2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task14_4_1.zadanye2.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByNameContaining(String name);

}
