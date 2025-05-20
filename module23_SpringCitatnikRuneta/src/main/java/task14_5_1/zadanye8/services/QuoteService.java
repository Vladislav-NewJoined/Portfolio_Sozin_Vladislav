package task14_5_1.zadanye8.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import task14_5_1.zadanye8.models.Quote;
import task14_5_1.zadanye8.repositories.QuoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@Service
public class QuoteService {
    @Autowired
    BashParser parser;

    @Autowired
    QuoteRepository repository;

    public List<Quote> getPage(int page) {
        List<Quote> ret = new ArrayList<>();
        Map<Integer, String> map = parser.getPage(page);
        for (var entry : map.entrySet()) {
            var rawQuote = new Quote();
            rawQuote.setQuoteid(entry.getKey());
            rawQuote.setText(entry.getValue());
            var existed = repository.findByQuoteidEquals(rawQuote.getQuoteid());
            if (existed.isEmpty())
                ret.add(repository.save(rawQuote));
            else
                ret.add(existed.get());
        }
        return ret;
    }

    public Quote getById(int id) {
        var existingQuote = repository.findByQuoteidEquals(id);
        if (existingQuote.isPresent())
            return existingQuote.get();
            var quoteEntry = parser.getById(id);
            if (quoteEntry == null) return null;
            var newQuote = new Quote();
            newQuote.setQuoteid(quoteEntry.getKey());
            newQuote.setText(quoteEntry.getValue());
            return repository.save(newQuote);
    }

    public Quote getRandom() {
        var quoteEntry = parser.getRandom(); // Теперь возвращает Map.Entry<Integer, String>
        if (quoteEntry == null) return null;

        var existingQuote = repository.findByQuoteidEquals(quoteEntry.getKey());
        if (existingQuote.isPresent())
            return existingQuote.get();

        var newQuote = new Quote();
        newQuote.setQuoteid(quoteEntry.getKey());
        newQuote.setText(quoteEntry.getValue());
        return repository.save(newQuote);
    }
}
