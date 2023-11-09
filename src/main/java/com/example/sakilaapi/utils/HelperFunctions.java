package com.example.sakilaapi.utils;

import com.example.sakilaapi.dto.ApiResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class HelperFunctions {
    public static <T, E> ApiResponse<T> getApiResponse(int pageNo, int pageSize, List<T> content, Page<E> films) {
        ApiResponse<T> filmDtoApiResponse = new ApiResponse<>();
        filmDtoApiResponse.setContent(content);
        filmDtoApiResponse.setPageNo(pageNo);
        filmDtoApiResponse.setPageSize(pageSize);
        filmDtoApiResponse.setTotalElements(films.getTotalElements());
        filmDtoApiResponse.setTotalPages(films.getTotalPages());
        filmDtoApiResponse.setLast(films.isLast());
        return filmDtoApiResponse;
    }
}
