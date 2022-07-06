package com.shopapotheke.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryModel {
    @JsonProperty("total_count")
    private Long totalCount;
    private List<RepositoryItem> items;
}
