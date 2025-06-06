package task14_4_1.zadanye3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task14_4_1.zadanye3.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByNameContaining(String name);

}
