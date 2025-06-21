package com.service;

import com.entity.Category;

import java.util.List;

public interface CategoryQueryService {
    List<Category> findByType(Integer type);
}
