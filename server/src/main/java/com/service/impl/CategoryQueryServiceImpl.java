package com.service.impl;

import com.entity.Category;
import com.mapper.CategoryMapper;
import com.service.CategoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findByType(Integer type) {
        return categoryMapper.findByType(type);
    }
}
