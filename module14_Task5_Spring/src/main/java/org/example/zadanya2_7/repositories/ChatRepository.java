package org.example.zadanya2_7.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.zadanya2_7.models.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
