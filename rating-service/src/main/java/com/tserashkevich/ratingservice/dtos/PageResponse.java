package com.tserashkevich.ratingservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PageResponse<T> {
    private final List<T> objectList;
    private final long totalElements;
    private final int totalPages;
}
