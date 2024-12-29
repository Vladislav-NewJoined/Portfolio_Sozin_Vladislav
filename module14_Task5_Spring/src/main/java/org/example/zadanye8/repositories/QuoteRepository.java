    package org.example.zadanye8.repositories;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.example.zadanye8.models.Quote;

    import java.util.Optional;

    public interface QuoteRepository extends JpaRepository<Quote, Integer> {
        Optional<Quote> findByQuoteidEquals(Integer quoteid);
    }

