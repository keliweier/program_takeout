package com.service;

import com.dto.CategoryDTO;
import com.dto.CategoryPageQueryDTO;
import com.entity.Category;
import com.result.PageResult;

import java.util.List;

public interface CategoryService {
    PageResult findAllByPage(CategoryPageQueryDTO categoryPageQueryDTO);

    void saveCategory(CategoryDTO categoryDTO);

    void updateStatus(Integer status, Long id);

    void updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    List<Category> findByType(Integer type);
}
