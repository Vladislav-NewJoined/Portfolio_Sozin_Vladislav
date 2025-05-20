package task14_5_1.zadanya2_7.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import task14_5_1.zadanya2_7.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
