package com.shopapotheke.service;

import com.shopapotheke.model.RepositoryItem;
import com.shopapotheke.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepositoryService {

    private final Repository repository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RepositoryService(Repository repository) {
        this.repository = repository;
    }

    public List<RepositoryItem> findAllTasks() {
        logger.info("RepositoryService/findAllTasks");
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(RepositoryItem::getStargazersCount))
                .collect(Collectors.toList());
    }

    public List<RepositoryItem> findAllTasks(int topView) {
        logger.info("RepositoryService/findAllTasks/topView");
        return repository.findAll()
                .stream()
                .limit(topView)
                .collect(Collectors.toList());
    }

    public List<RepositoryItem> findAllTasksByDate(String createdAt) {
        logger.info("RepositoryService/findAllTasksByDate");
        return repository.findAll()
                .stream()
                .filter(r -> r.getCreatedAt().toString().equalsIgnoreCase(createdAt))
                .sorted(Comparator.comparingInt(RepositoryItem::getStargazersCount))
                .collect(Collectors.toList());
    }

    public List<RepositoryItem> findAllTasksByLanguage(String language) {
        logger.info("@RepositoryService/findAllTasksByLanguage");
        return repository.findAll()
                .stream()
                .filter(r -> r.getLanguage() != null && r.getLanguage().equalsIgnoreCase(language))
                .collect(Collectors.toList());
    }
}
