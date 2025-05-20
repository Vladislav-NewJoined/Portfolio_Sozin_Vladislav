package task14_5_1.zadanye8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import task14_5_1.zadanye8.models.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByChatIdEquals(long chatId);

}
