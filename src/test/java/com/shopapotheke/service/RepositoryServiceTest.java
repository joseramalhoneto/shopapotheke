package com.shopapotheke.service;

import com.shopapotheke.model.RepositoryItem;
import com.shopapotheke.repository.Repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RepositoryServiceTest {

    private RepositoryItem repositoryItem1, repositoryItem2, repositoryItem3;
    private DateTimeFormatter formatter;
    private LocalDateTime localDateTime;

    @InjectMocks
    private RepositoryService repositoryService;

    @Mock
    private Repository repository;

    public RepositoryServiceTest() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        localDateTime = LocalDateTime.parse("2022-07-04 20:29:10", formatter);
        repositoryItem1  = new RepositoryItem(100L, "Java", localDateTime, 1000);
        repositoryItem2  = new RepositoryItem(200L, "SpringBoot", localDateTime, 2000);
        repositoryItem3  = new RepositoryItem(300L, "Java", localDateTime, 3000);
    }

    @Test
    public void findAllTasks() {
        List<RepositoryItem> list = List.of(repositoryItem1, repositoryItem2, repositoryItem3);

        when(repository.findAll()).thenReturn(list);
        List<RepositoryItem> result = repositoryService.findAllTasks();

        assertEquals(3, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void findAllTasksByDate() {
        List<RepositoryItem> list = List.of(repositoryItem1, repositoryItem2, repositoryItem3);
        when(repository.findAll()).thenReturn(list);
        List<RepositoryItem> repositories = repositoryService.findAllTasksByDate(localDateTime.toString());

        assertThat(repositories).isEqualTo(list);
        assertEquals(100L, repositories.get(0).getId());
        assertEquals(200L, repositories.get(1).getId());
        assertEquals(300L, repositories.get(2).getId());
        assertEquals("Java", repositories.get(0).getLanguage());
        assertEquals("SpringBoot", repositories.get(1).getLanguage());
        assertEquals("Java", repositories.get(2).getLanguage());
        assertEquals(1000, repositories.get(0).getStargazersCount());
        assertEquals(2000, repositories.get(1).getStargazersCount());
        assertEquals(3000, repositories.get(2).getStargazersCount());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void findAllTasksByLanguage() {
        List<RepositoryItem> list = List.of(repositoryItem1, repositoryItem3);
        when(repository.findAll()).thenReturn(list);
        List<RepositoryItem> repositories = repositoryService.findAllTasksByLanguage("Java");

        assertThat(repositories).isEqualTo(list);
        assertEquals(100L, repositories.get(0).getId());
        assertEquals(300L, repositories.get(1).getId());
        assertEquals("Java", repositories.get(0).getLanguage());
        assertEquals("Java", repositories.get(1).getLanguage());
        verify(repository, times(1)).findAll();
    }
}