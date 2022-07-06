package com.shopapotheke.repository;

import com.shopapotheke.model.RepositoryItem;
import com.shopapotheke.model.RepositoryModel;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Repository {
    private RestTemplate restTemplate;
    private RepositoryModel repository;
    private final String URL = "https://api.github.com/search/repositories?q=created:>2019-01-10&sort=stars&order=desc";

    public Repository() {
        this.restTemplate = new RestTemplate();
        this.repository = restTemplate.getForObject(this.URL, RepositoryModel.class);
    }

    public List<RepositoryItem> findAll() {
        return repository.getItems();
    }
}
