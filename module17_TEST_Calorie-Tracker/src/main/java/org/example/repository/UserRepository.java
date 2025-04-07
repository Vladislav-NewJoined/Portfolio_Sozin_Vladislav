package org.example.repository;

import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Удаляет всех пользователей из таблицы ПОЛЬЗОВАТЕЛИ.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM User")
    void deleteAllUsers();

    /**
     * Сбрасывает счётчик ID в таблице ПОЛЬЗОВАТЕЛИ, чтобы новые строки начинались с ID = 1.
     */
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE ПОЛЬЗОВАТЕЛИ ALTER COLUMN ID RESTART WITH 1", nativeQuery = true)
    void resetIdSequence();
}
