package com.nibado.workshop.springcloud.quoteservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {
    @Autowired
    private QuoteService service;
    @Value("${eureka.instance.metadataMap.instanceId}")
    private String instanceId;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public QuoteResponse get() {
        return new QuoteResponse(service.random(), instanceId);
    }

    @RequestMapping(value = "/genre", method = RequestMethod.GET)
    public List<String> genres() {
        return service.genres();
    }

    @RequestMapping(value = "/genre/{genre}", method = RequestMethod.GET)
    public QuoteService.Quote genre(@PathVariable String genre) {
        return service.random(genre);
    }

    public static class QuoteResponse {
        public final String quote;
        public final String author;
        public final String genre;
        public final String instance;

        public QuoteResponse(QuoteService.Quote quote, String instance) {
            this.quote = quote.quote;
            this.author = quote.author;
            this.genre = quote.genre;
            this.instance = instance;
        }
    }
}
