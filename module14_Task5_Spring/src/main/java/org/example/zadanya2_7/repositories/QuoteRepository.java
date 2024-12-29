package org.example.zadanya2_7.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.example.zadanya2_7.models.Quote;

import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {
    Optional<Quote> findByQuoteidEquals(Integer quoteid);
}
