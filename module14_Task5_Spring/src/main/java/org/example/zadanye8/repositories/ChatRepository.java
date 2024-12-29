package org.example.zadanye8.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.zadanye8.models.Chat;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByChatIdEquals(long chatId);

}
