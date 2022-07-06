package com.shopapotheke.controller;

import com.shopapotheke.model.RepositoryItem;
import com.shopapotheke.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/repository")
public class RepositoryController {

    private final RepositoryService repositoryService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    //A list of the most popular repositories, sorted by number of stars
    @GetMapping("/find/all")
    public ResponseEntity<List<RepositoryItem>> findAllTasks(){
        logger.info("@GetMapping/RepositoryController/findAllTasks");
        return new ResponseEntity<>(
                repositoryService.findAllTasks(),
                HttpStatus.OK
        );
    }

    //An option to be able to view the top 10, 50, 100 repositories should be available.
    @GetMapping("/find/all/topview/{topView}")
    public ResponseEntity<List<RepositoryItem>> findAllTasks(@PathVariable("topView") int topView) {
        logger.info("@GetMapping/RepositoryController/findAllTasks/topView");
        return new ResponseEntity<>(
                repositoryService.findAllTasks(topView),
                HttpStatus.OK
        );
    }

    //Given a date, the most popular repositories created from this date onwards should be returned
    @GetMapping("/find/all/date/{createdAt}")
    public ResponseEntity<List<RepositoryItem>> findAllTasksByDate(@PathVariable("createdAt") String createdAt) {
        logger.info("@GetMapping/RepositoryController/findAllTasksByDate");
        return new ResponseEntity<>(
                repositoryService.findAllTasksByDate(createdAt),
                HttpStatus.OK
        );
    }

    //A filter for the programming language would be a great addition to have
    @GetMapping("/find/all/language/{language}")
    public ResponseEntity<List<RepositoryItem>> findAllTasksByLanguage(@PathVariable("language") String language) {
        logger.info("@GetMapping/RepositoryController/findAllTasksByLanguage");
        return new ResponseEntity<>(
                repositoryService.findAllTasksByLanguage(language),
                HttpStatus.OK
        );
    }

}
