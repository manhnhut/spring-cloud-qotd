package com.nibado.workshop.springcloud.quoteservice;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class QuoteService {
    private static final Random RANDOM = new Random();
    private Set<String> genres;
    private List<Quote> quotes;

    public QuoteService() {
        genres = new HashSet<>();
        quotes = new ArrayList<>();
    }

    @PostConstruct
    public void load() throws IOException {
        BufferedReader ins = new BufferedReader(new InputStreamReader(QuoteService.class.getResourceAsStream("/quotes_all.csv")));

        String line;
        while((line = ins.readLine()) != null) {
            String[] parts = line.split(";");
            if(parts.length != 3) {
                continue;
            }

            genres.add(parts[2]);

            Quote quote = new Quote(parts[1], parts[0], parts[2]);

            quotes.add(quote);
        }
    }

    public Quote random() {
        return randomFrom(quotes);
    }

    public Quote random(String genre) {
        List<Quote> byGenre = quotes.stream().filter(quote -> quote.genre.equalsIgnoreCase(genre)).collect(toList());

        return randomFrom(byGenre);
    }

    private Quote randomFrom(List<Quote> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    public List<String> genres() {
        return genres.stream().sorted().collect(toList());
    }

    public static class Quote {
        public final String author;
        public final String quote;
        public final String genre;

        public Quote(String author, String quote, String genre) {
            this.author = author;
            this.quote = quote;
            this.genre = genre;
        }
    }
}
