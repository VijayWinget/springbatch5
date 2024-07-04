package com.sb.batch5;

import org.springframework.batch.item.ItemReader;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RestApiReader implements ItemReader<Person> {
    private final WebClient webClient;
    private final String apiUrl;
    private List<Person> personData;
    private AtomicInteger nextPersonIndex;

    public RestApiReader(String apiUrl) {
        this.webClient = WebClient.builder().build();
        this.apiUrl = apiUrl;
        this.nextPersonIndex = new AtomicInteger(0);
    }

    @Override
    public Person read() throws Exception {
        if (personDataIsNotInitialized()) {
            personData = fetchPersonDataFromAPI();
        }

        Person nextPerson = null;

        if (nextPersonIndex.get() < personData.size()) {
            nextPerson = personData.get(nextPersonIndex.getAndIncrement());
        }

        return nextPerson;
    }

    private boolean personDataIsNotInitialized() {
        return this.personData == null;
    }

    private List<Person> fetchPersonDataFromAPI() {
        Person[] persons = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(Person[].class)
                .block();

        return Arrays.asList(persons);
    }
}
