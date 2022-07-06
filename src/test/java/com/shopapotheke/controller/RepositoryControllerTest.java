package com.shopapotheke.controller;

import com.shopapotheke.model.RepositoryItem;
import com.shopapotheke.service.RepositoryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RepositoryController.class)
public class RepositoryControllerTest {

    private RepositoryItem repositoryItem1, repositoryItem2, repositoryItem3;
    private DateTimeFormatter formatter;
    private LocalDateTime localDateTime;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepositoryService repositoryService;

    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        localDateTime = LocalDateTime.parse("2022-07-04 20:29:10", formatter);
        repositoryItem1  = new RepositoryItem(100L, "Java", localDateTime, 1000);
        repositoryItem2  = new RepositoryItem(200L, "SpringBoot", localDateTime, 2000);
        repositoryItem3  = new RepositoryItem(300L, "Java", localDateTime, 3000);
    }

    @Test
    public void findAllTasks() throws Exception {
        when(repositoryService
                .findAllTasks())
                .thenReturn(List.of(repositoryItem1, repositoryItem2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/repository/find/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(100L))
                .andExpect(jsonPath("$[1].id").value(200L));
    }

    @Test
    public void findAllTasksWithLimit() throws Exception {
        when(repositoryService
                .findAllTasks(2))
                .thenReturn(List.of(repositoryItem1, repositoryItem2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/repository/find/all/topview/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(100L))
                .andExpect(jsonPath("$[1].id").value(200L));
    }

    @Test
    public void findAllTasksByDate() throws Exception {
        when(repositoryService
                .findAllTasksByDate(repositoryItem1.getCreatedAt().toString()))
                .thenReturn(List.of(repositoryItem1));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/repository/find/all/date/" + repositoryItem1.getCreatedAt().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].created_at").value(repositoryItem1.getCreatedAt().toString()));
    }

    @Test
    public void findAllTasksByLanguage() throws Exception {
        when(repositoryService
                .findAllTasksByLanguage("Java"))
                .thenReturn(List.of(repositoryItem1, repositoryItem3));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/repository/find/all/language/Java"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].language").value("Java"))
                .andExpect(jsonPath("$[1].language").value("Java"));
    }
}