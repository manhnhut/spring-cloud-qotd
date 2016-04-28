package com.nibado.workshop.springcloud.webapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class QuoteController {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/quote", method = RequestMethod.GET)
    public Quote get() {
        return restTemplate.getForObject("http://cloud-quote/quote/", Quote.class);
    }

    private static class Quote {
        public final String quote;
        public final String author;
        public final String genre;
        public final String instance;

        public Quote(@JsonProperty("author") String author, @JsonProperty("quote") String quote, @JsonProperty("genre") String genre, @JsonProperty("instance") String instance) {
            this.quote = quote;
            this.genre = genre;
            this.author = author;
            this.instance = instance;
        }
    }
}
